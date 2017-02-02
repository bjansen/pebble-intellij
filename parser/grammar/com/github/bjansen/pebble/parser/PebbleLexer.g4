lexer grammar PebbleLexer;

PRINT_OPEN
    : '{{' -> pushMode(IN_TAG)
    ;

COMMENT
    : '{#' .*? '#}'
    ;


VERBATIM_TAG_OPEN
    : '{%' [ \t]* 'verbatim' [ \t]* '%}' -> pushMode(VERBATIM)
    ;

TAG_OPEN
    : '{%' -> pushMode(IN_TAG)
    ;

CONTENT
    : ~'{'+
    ;

ERRCHAR
	:	.	-> channel(HIDDEN)
	;

mode VERBATIM;

VERBATIM_BODY
    : .*? VERBATIM_TAG_END -> popMode
    ;

VERBATIM_TAG_END
    : '{%' [ \t]* 'endverbatim' [ \t]* '%}'
    ;

mode IN_TAG;

TAG_CLOSE
    : '%}' -> popMode
    ;

PRINT_CLOSE
    : '}}' -> popMode
    ;

NOT
    : 'not'
    ;

NULL
    : 'null'
    ;

NONE
    : 'none'
    ;

OP_ASSIGN
    : '='
    ;

OP_TERNARY
    : '?'
    ;

OP_COLON
    : ':'
    ;

OP_PIPE
    : '|'
    ;

OP_CONCAT
    : '~'
    ;

OP_RANGE
    : '..'
    ;

OP_MEMBER
    : '.'
    ;

LBRACE
    : '{'
    ;

RBRACE
    : '}'
    ;

LBRACKET
    : '['
    ;

RBRACKET
    : ']'
    ;

LPAREN
    : '('
    ;

RPAREN
    : ')'
    ;

COMMA
    : ','
    ;

OR
    : 'or'
    ;

AND
    : 'and'
    ;

IS
    : 'is'
    ;

IN
    : 'in'
    ;

CONTAINS
    : 'contains'
    ;

OP_PLUS
    : '+'
    ;

OP_MINUS
    : '-'
    ;

OP_DIV
    : '/'
    ;

OP_MULT
    : '*'
    ;

OP_MOD
    : '%'
    ;

OP_EQ
    : '=='
    ;

EQUALS
    : 'equals'
    ;

OP_NEQ
    : '!='
    ;

OP_LE
    : '<='
    ;

OP_LT
    : '<'
    ;

OP_GE
    : '>='
    ;

OP_GT
    : '>'
    ;

TRUE
    : 'true'
    ;

FALSE
    : 'false'
    ;

WITH
    : 'with'
    ;

STRING
    : '"' ~["]* '"'?
    ;

SINGLE_QUOTED_STRING
    : '\'' ~[']* '\''?
    ;

NUMERIC
    : [0-9]+(.[0-9]+)?
    ;

ID_NAME
    : [a-zA-Z][0-9a-zA-Z]*
    ;


WHITESPACE
    : [ \t\n\r]+ -> channel(HIDDEN)
    ;

ERRCHAR2
	:	.	-> channel(HIDDEN)
	;
