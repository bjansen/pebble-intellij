package com.github.bjansen.intellij.pebble;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import static com.github.bjansen.intellij.pebble.psi.PebbleTypes.*;
import com.intellij.util.containers.Stack;

%%

%{
  public _PebbleLexer() {
    this((java.io.Reader)null);
  }

  public IElementType checkContent() {
      if (!yytext().toString().equals("")) {
          if (yytext().toString().trim().length() == 0) {
              return TokenType.WHITE_SPACE;
          } else {
              return CONTENT;
          }
      }
      return null;
  }


  private Stack<Integer> stack = new Stack<>();

  public void yypushstate(int newState) {
      stack.push(yystate());
      yybegin(newState);
  }

  public void yypopstate() {
      yybegin(stack.pop());
  }

  public void yycleanstates() {
      while(!stack.isEmpty()) {
          yybegin(stack.pop());
      }
  }

  private boolean isVerbatim = false;
  private boolean isFirstNameInTag = false;
%}

%public
%class _PebbleLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+
NAME=[a-zA-Z_][a-zA-Z0-9_]*
STRING="\""[^\"]*"\""?
SINGLE_QUOTED_STRING="'"[^']*"'"?
NUMBER=[0-9]+(\.[0-9]+)?
COMMENT=("{#" [^#] {COMMENT_TAIL} ) | "{#"
COMMENT_TAIL=([^#]* (#+ [^#}])?)* ("#" | "#"+"}")?

%state IN_TAG
%state IN_EXPR
%state IN_VERBATIM

%%
<YYINITIAL> {
  "{%"                   { yypushstate(IN_TAG); isFirstNameInTag = true; return TAG_OPEN; }
  "{{"                   { yypushstate(IN_EXPR); return VAR_OPEN; }
  {COMMENT}              { return COMMENT; }

  [^{]+                  { return CONTENT; }
  [^]                    { return CONTENT; }
}

<IN_TAG> {
  "verbatim"             { if (isFirstNameInTag) {
                             isVerbatim = true;
                           }
                           return ID_NAME;
                         }
  "%}"                   { yypopstate();
                           if (isVerbatim) {
                              yypushstate(IN_VERBATIM);
                           }
                           return TAG_CLOSE;
                         }
}
<IN_EXPR> {
  "}}"                   { yypopstate(); return VAR_CLOSE; }
}
<IN_VERBATIM> {
  "{%"/\s* "endverbatim" \s* "%}"
                         { yypopstate();
                           yypushstate(IN_TAG);
                           isVerbatim = false;
                           return TAG_OPEN;
                         }
  [^{]+                  { return CONTENT; }
  [^]                    { return CONTENT; }
}
<IN_TAG,IN_EXPR> {
  {WHITE_SPACE}          { return com.intellij.psi.TokenType.WHITE_SPACE; }
  "true"                 { return TRUE; }
  "false"                { return FALSE; }
  "null"                 { return NULL; }

  "equals"               { return EQUALS; }
  "contains"             { return CONTAINS; }

  "and"                  { return AND; }
  "or"                   { return OR; }
  "not"                  { return NOT; }
  "is"                   { return IS; }
  "|"                    { return OP_PIPE; }
  "=="                   { return OP_EQ; }
  "="                    { return OP_ASSIGN; }
  "<="                   { return OP_LE; }
  "<"                    { return OP_LT; }
  ">="                   { return OP_GE; }
  ">"                    { return OP_GT; }
  "!="                   { return OP_NEQ; }
  "+"                    { return OP_PLUS; }
  "-"                    { return OP_MINUS; }
  "/"                    { return OP_DIV; }
  "*"                    { return OP_MULT; }
  "%"                    { return OP_MOD; }
  "("                    { return LPAREN; }
  ")"                    { return RPAREN; }
  "{"                    { return LBRACE; }
  "}"                    { return RBRACE; }
  "["                    { return LBRACKET; }
  "]"                    { return RBRACKET; }
  ","                    { return COMMA; }
  {STRING}               { return STRING; }
  {SINGLE_QUOTED_STRING} { return SINGLE_QUOTED_STRING; }
  {NAME}                 { isFirstNameInTag = false; return ID_NAME; }
  {NUMBER}               { return NUMERIC; }
  [^]                    { return TokenType.BAD_CHARACTER; }
}
