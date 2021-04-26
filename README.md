# About
Simple math shell written in java that evaluates math expressions. Currently, it only has suport for adding, subtracting, multiplying, dividing, grouping (with parentheses), and exponents. Also has support for storing math expressions in variables and basic boolean logic (and, or, not, etc.). 

# Usage

## Math Operators
The following mathematical operators are supported: ```+, -, /, *, ^``` (for addition, subtraction, division, multiplication, exponents, respectively). ```()``` can be used to change the order of operations.

## Variables
A variable can be declared using the following syntax: ```var name = <some expression>```
Variables can be accessed using their name (and nothing else).
To change the value of the variable, use the the same syntax as declaring a variable.

## Logical Operators
The and operator can be used using the keyword ```and```
The or operator can be used using the keyword ```or```
The not operator can be used using the keyword ```not```

## Control Flow
The following is the syntax for an if statement: ```if <some boolean expression> then <some expression>```
Else if and else statments are also supported: ```if <some boolean expression> then <some expression> elif <some boolean expression> then <some expression> else <some expression>```

## Additional Notes
The language does not actually have a boolean data type but rather uses a 1 for "true" and 0 for false. Therfore, a the number ```1``` or an expression that evaluates to ```1``` can be used as a boolean expression.
