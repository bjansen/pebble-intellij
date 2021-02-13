lexer grammar PebbleLexer;

@members {

private java.util.Queue<Token> queue = new java.util.LinkedList<Token>();
private boolean inStringInterpolation = false;
private int openingBracesCount = 0;

protected Token customNextToken() {
    return super.nextToken();
}

@Override
public Token nextToken() {

    if (!queue.isEmpty()) {
        return queue.poll();
    }

    Token next = customNextToken();

    if (next.getType() != CONTENT) {
        return next;
    }

    StringBuilder builder = new StringBuilder();
    Token startToken = next;

    while (next.getType() == CONTENT) {
        builder.append(next.getText());
        next = customNextToken();
    }

    // The `next` will _not_ be a CONTENT-token, store it in
    // the queue to return the next time!
    queue.offer(next);

    CommonToken token = new CommonToken(startToken);
    token.setStopIndex(startToken.getStartIndex() + builder.length() - 1);

    if (!token.getText().isEmpty() && token.getText().trim().isEmpty()) {
        // treat CONTENT that is all whitespace as WHITESPACE to make the formatter work correctly
        token.setType(WHITESPACE);
    }

    return token;
}

@Override
public void setInputStream(IntStream input) {
    queue.clear();
    super.setInputStream(input);
}

}

PRINT_OPEN
    : ('{{-' | '{{') -> pushMode(IN_TAG)
    ;

COMMENT
    : '{#' .*? '#}'
    ;


VERBATIM_TAG_OPEN
    : '{%' [ \t]* 'verbatim' [ \t]* '%}' -> pushMode(VERBATIM)
    ;

TAG_OPEN
    : ('{%-' |'{%') -> pushMode(IN_TAG)
    ;

CONTENT
    : .
    ;

mode VERBATIM;

VERBATIM_TAG_END
    : ('{%' [ \t]* 'endverbatim' [ \t]* '%}') -> popMode
    ;

VERBATIM_BODY
    : . -> type(CONTENT)
    ;


mode IN_TAG;

TAG_CLOSE
    : ('-%}' | '%}') -> popMode
    ;

PRINT_CLOSE
    : ('-}}' | ('}}' {openingBracesCount == 0 && !inStringInterpolation}?)) -> popMode
    ;

// "hard" keywords, can never be legal identifiers
AND
    : 'and'
    ;

CONTAINS
    : 'contains'
    ;

EQUALS
    : 'equals'
    ;

IS
    : 'is'
    ;

NOT
    : 'not'
    ;

OR
    : 'or'
    ;

// "soft" keywords, can be legal identifiers under certains circumstances
AS
    : 'as'
    ;

FROM
    : 'from'
    ;

IN
    : 'in'
    ;

IMPORT
    : 'import'
    ;

WITH
    : 'with'
    ;

// reserved keywords, can be legal identifiers under certains circumstances
TRUE
    : 'true'
    ;

FALSE
    : 'false'
    ;

NULL
    : 'null'
    ;

NONE
    : 'none'
    ;

// Others
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
    {
        if (inStringInterpolation) {
            openingBracesCount++;
        }
    }
    ;

RBRACE
    : '}'
    {
        if (inStringInterpolation) {
            if (openingBracesCount > 0) {
                // We are probably closing a map expression or something similar
                openingBracesCount--;
            } else {
                // We are closing the string interpolation
                inStringInterpolation = false;
                setType(INTERPOLATED_STRING_STOP);
                popMode();
            }
        }
    }
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

DOUBLE_QUOTED_PLAIN_STRING
    : '"' ~[#"\\]* ('\\'.~[#"\\]*)* '"'
    ;

STRING_START
    : '"' -> pushMode(IN_STRING)
    ;

SINGLE_QUOTED_STRING
    : '\'' ~['\\]* ('\\'.~['\\]*)* '\''?
    ;

LONG
    : [0-9]+ 'l'
    ;

NUMERIC
    : [0-9]+(.[0-9]+)?
    ;

ID_NAME
    : [\p{Letter}_][\p{Letter}\p{Digit}_]*
    ;


WHITESPACE
    : [ \t\n\r]+ -> channel(HIDDEN)
    ;

ERRCHAR2
    : . -> channel(HIDDEN)
    ;

mode IN_STRING;

STRING_END
    : '"' -> popMode
    ;

INTERPOLATED_STRING_START
    : '#{' {inStringInterpolation = true;} -> pushMode(IN_TAG)
    ;

TEXT
    : ('\\"' | ~["#] | ('#' {_input.LA(1) != '{'}?))+
    ;

INTERPOLATED_STRING_STOP
    : '}'
    ;
