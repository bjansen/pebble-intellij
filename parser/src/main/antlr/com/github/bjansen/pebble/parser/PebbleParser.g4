parser grammar PebbleParser;

@header {
package com.github.bjansen.pebble.parser;
}

// Use this when working with the IntelliJ plugin
//options { tokenVocab=PebbleLexer; }

// Use this when working with Gradle
options { tokenVocab='com/github/bjansen/pebble/parser/PebbleLexer'; }

pebbleTemplate
    : (
        CONTENT
        | printDirective
        | commentDirective
        | tagDirective
    )*
    ;

printDirective
    : PRINT_OPEN expression filters PRINT_CLOSE
    ;

commentDirective
    : COMMENT
    ;

tagDirective
    : verbatimTag
    | genericTag
    ;

verbatimTag
    : VERBATIM_TAG_OPEN VERBATIM_BODY
    ;

genericTag
    : TAG_OPEN tagName expression? TAG_CLOSE
    ;

tagName
    : ID_NAME
    ;

expression
    : expression OP_ASSIGN expression
    | unary_op expression
    | parenthesized_expression
    | array_expression
    | map_expression
    | expression OR expression
    | expression AND expression
    | expression ((IS test)|(CONTAINS expression))
    | expression comparison_op expression
    | expression OP_TERNARY expression OP_COLON expression
    | expression additive_op expression
    | expression multiplicative_op expression
    | expression OP_PIPE expression
    | expression OP_CONCAT expression
    | expression OP_RANGE expression
    | expression IN expression
    | expression WITH expression
    | member_expression
    ;

parenthesized_expression
    : LPAREN expression RPAREN
    ;

array_expression
    : LBRACKET (expression (COMMA expression)*)? RBRACKET
    ;

map_expression
    : LBRACE (map_element (COMMA map_element)*)? RBRACE
    ;

map_element
    : string_literal OP_COLON expression
    ;

member_expression
    : function_call_expression
    | qualified_expression
    | term
    ;


qualified_expression
    : identifier (OP_MEMBER (function_call_expression | identifier))+
    ;

function_call_expression
    : identifier function_parameters
    ;

function_parameters
    : LPAREN (expression (COMMA expression)*)? RPAREN
    ;

term
    : boolean_literal
    | NULL
    | NONE
    | string_literal
    | numeric_literal
    | identifier
    ;

test
    : NOT? (NULL | identifier)
    ;

unary_op
    : OP_PLUS
    | OP_MINUS
    | NOT
    ;

multiplicative_op
    : OP_DIV
    | OP_MULT
    | OP_MOD
    ;

additive_op
    : OP_PLUS
    | OP_MINUS
    ;

comparison_op
    : OP_EQ
    | EQUALS
    | OP_NEQ
    | OP_LE
    | OP_LT
    | OP_GE
    | OP_GT
    ;

boolean_literal
    : TRUE
    | FALSE
    ;

string_literal
    : STRING
    | SINGLE_QUOTED_STRING
    ;

numeric_literal
    : NUMERIC
    ;

identifier
    : ID_NAME
    ;

filters
    : (OP_PIPE function_call_expression)*
    ;
