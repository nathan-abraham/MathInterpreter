package src;

import src.nodes.*;
import src.errors.*;
import src.errors.Error;
import java.util.Arrays;

public class Parser {
    public TokenCollection tkc;
    public Token currentToken;
    public Token prevToken;
    public int tIdx = -1;
    public Position pos;
    public int errorCount = 0;

    public Parser(TokenCollection t) {
        tkc = t;
        advance();
    }

    public Token advance() {
        tIdx++;
        if (tIdx < tkc.tokens.size()) {
            currentToken = tkc.tokens.get(tIdx);
        }
        return currentToken;
    }

    public ParseResult parse() {
        ParseResult result = assign();
        if (result.error == null && !currentToken.type.equals(Lexer.TT_EOF)) {
            return result.failure(new InvalidSyntaxError(currentToken.posStart, currentToken.posEnd,
                    "Expected \'+\', \'-\', \'*\', or \'^\'"));
        }

        return result;
    }

    public ParseResult assign() {
        ParseResult result = new ParseResult();
        if (currentToken.matches(Lexer.TT_KEYWORD, "var")) {
            advance();
            if (!currentToken.type.equals(Lexer.TT_IDENTIFIER)) {
                return result.failure(
                        new InvalidSyntaxError(currentToken.posStart, currentToken.posEnd, "Expected identifier"));
            }

            Token varName = currentToken;
            advance();

            if (!currentToken.type.equals(Lexer.TT_EQ)) {
                return result
                        .failure(new InvalidSyntaxError(currentToken.posStart, currentToken.posEnd, "Expected \'=\'"));
            }

            advance();
            ParseResult expr = assign();
            result.register(expr);
            if (result.error != null)
                return result;
            return result.success(new VarAssignNode(varName, expr.node));
        }
        ParseResult expr = result.register(compExpr());
        if (result.error != null)
            return result;
        if (currentToken.matches(Lexer.TT_KEYWORD, "and") || currentToken.matches(Lexer.TT_KEYWORD, "or")) {
            Token opTok = currentToken;
            advance();
            ParseResult tempCompExpr = result.register(compExpr());
            if (result.error != null)
                return result;
            return result.success(new LogOpNode(expr.node, opTok, tempCompExpr.node));
        }
        return result.success(expr.node);
    }

    public ParseResult compExpr() {
        ParseResult result = new ParseResult();
        if (currentToken.matches(Lexer.TT_KEYWORD, "not")) {
            advance();
            ParseResult tempCompExpr = result.register(compExpr());
            if (result.error != null)
                return result;
            return result.success(new NotNode(tempCompExpr.node));
        }
        ParseResult expr = result.register(expr());
        if (result.error != null)
            return result;
        if (Arrays.asList(Lexer.compExprOps).contains(currentToken.type)) {
            Token opTok = currentToken;
            advance();
            ParseResult tempExpr = result.register(expr());
            if (result.error != null)
                return result;
            return result.success(new CompNode(expr.node, opTok, tempExpr.node));
        }
        return result.success(expr.node);
    }

    public ParseResult expr() {
        ParseResult result = new ParseResult();
        Node term = result.register(term()).node;
        if (result.error != null) {
            return result;
        }
        while (currentToken != null
                && (currentToken.type.equals(Lexer.TT_PLUS) || currentToken.type.equals(Lexer.TT_MINUS))) {
            if (currentToken.type.equals(Lexer.TT_PLUS)) {
                advance();
                ParseResult tempTerm = result.register(term());
                if (result.error != null)
                    return result;

                term = new AddNode(term, tempTerm.node);
            } else if (currentToken.type.equals(Lexer.TT_MINUS)) {
                advance();
                ParseResult tempTerm = result.register(term());
                if (result.error != null)
                    return result;

                term = new SubNode(term, tempTerm.node);
            }
        }
        return result.success(term);
    }

    public ParseResult term() {
        ParseResult result = new ParseResult();
        Node factor = result.register(factor()).node;
        if (result.error != null)
            return result;

        while (currentToken != null
                && (currentToken.type.equals(Lexer.TT_MUL) || currentToken.type.equals(Lexer.TT_DIV))) {
            if (currentToken.type.equals(Lexer.TT_MUL)) {
                advance();
                ParseResult tempTerm = result.register(factor());
                if (result.error != null) {
                    return result;
                }
                factor = new MulNode(factor, tempTerm.node);
            } else if (currentToken.type.equals(Lexer.TT_DIV)) {
                advance();
                ParseResult tempTerm = result.register(factor());
                if (result.error != null) {
                    return result;
                }
                factor = new DivNode(factor, tempTerm.node);
            }
        }
        return result.success(factor);
    }

    public ParseResult factor() {
        ParseResult result = new ParseResult();
        if (currentToken.type.equals(Lexer.TT_PLUS)) {
            advance();
            ParseResult factor = result.register(factor());
            if (result.error != null)
                return result;
            return result.success(new PlusNode(factor.node));
        } else if (currentToken.type.equals(Lexer.TT_MINUS)) {
            advance();
            ParseResult factor = result.register(factor());
            if (result.error != null)
                return result;
            return result.success(new MinusNode(factor.node));
        }
        return power();
    }

    public ParseResult power() {
        ParseResult result = new ParseResult();
        ParseResult factor = result.register(atom());
        if (result.error != null)
            return result;
        if (currentToken.type.equals(Lexer.TT_POWER)) {
            advance();
            ParseResult tempTerm = result.register(factor());
            if (result.error != null) {
                return result;
            }
            return result.success(new PowerNode(factor.node, tempTerm.node));
        }
        return factor;
    }

    public ParseResult atom() {
        ParseResult result = new ParseResult();
        Token token = currentToken;
        if (token.type.equals(Lexer.TT_LPAREN)) {
            advance();
            ParseResult expr = result.register(assign());
            if (result.error != null) {
                return result;
            }

            if (!currentToken.type.equals(Lexer.TT_RPAREN)) {
                return result
                        .failure(new InvalidSyntaxError(currentToken.posStart, currentToken.posEnd, "Expected \')\'"));
            }
            advance();
            return result.success(expr.node);
        } else if (token.type.equals(Lexer.TT_INT) || token.type.equals(Lexer.TT_FLOAT)) {
            advance();
            return result.success(new NumberNode(token.value));
        } else if (currentToken.type.equals(Lexer.TT_IDENTIFIER)) {
            advance();
            return result.success(new VarAccessNode(token));
        }

        return result.failure(new InvalidSyntaxError(currentToken.posStart, currentToken.posEnd,
                "Expected int, float, identifier, \'+\', \'-\', or \'(\'"));
    }
}
