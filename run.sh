#! /bin/sh


# javac ./src/Interpreter.java ./src/Lexer.java ./src/Main.java ./src/MyNumber.java ./src/Parser.java ./src/ParseResult.java ./src/Position.java ./src/RetValue.java ./src/SymbolTable.java ./src/Token.java ./src/TokenCollection.java ./src/errors/Error.java ./src/errors/ExpectedCharError.java ./src/errors/IllegalCharError.java ./src/errors/InvalidSyntaxError.java ./src/nodes/AddNode.java ./src/nodes/DivNode.java ./src/nodes/MinusNode.java ./src/nodes/MulNode.java ./src/nodes/Node.java ./src/nodes/NumberNode.java ./src/nodes/PlusNode.java ./src/nodes/PowerNode.java ./src/nodes/SubNode.java ./src/nodes/VarAccessNode.java ./src/nodes/VarAssignNode.java -d ./bin 

# cd bin
# java src.Main
# cd ..
# cd src

javac @filenames.txt -d bin
cd bin
java src.Main
cd ..
cd src
