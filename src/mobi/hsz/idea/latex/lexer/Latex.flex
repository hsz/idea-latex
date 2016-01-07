package mobi.hsz.idea.latex.lexer;

import java.io.File;
import java.util.List;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import static mobi.hsz.idea.latex.psi.LatexTypes.*;
import static com.intellij.psi.TokenType.*;
%%

%{
  public LatexLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class LatexLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL                 = "\r"|"\n"|"\r\n"
LINE_WS             = [\ \t\f]
WHITE_SPACE         = ({LINE_WS}|{EOL})+

IDENTIFIER          = \\[:letter:]+
COMMENT             = %.*
ARG                 = [^\(\)\{\}\[\]\\,]
TEXT                = [^\(\)\{\}\[\]\\\%\ \t\f\r\n]|"\\\%"|("\\"{SPECIAL})
SPECIAL             = "$"|"&"|"#"|"_"|"~"|"^"|"\\"
%state IN_ARGUMENT

%%

// brackets
<YYINITIAL> "("             { yybegin(IN_ARGUMENT); return LPAREN; }
<YYINITIAL> ")"             { return RPAREN; }
<YYINITIAL> "["             { yybegin(IN_ARGUMENT); return LBRACKET; }
<YYINITIAL> "]"             { return RBRACKET; }
<YYINITIAL> "{"             { yybegin(IN_ARGUMENT); return LBRACE; }
<YYINITIAL> "}"             { return RBRACE; }

<IN_ARGUMENT> {
    {ARG}+                  { return ARG; }
    ","                     { return COMMA; }
    .                       { yypushback(1); yybegin(YYINITIAL); }
}

// special characters
<YYINITIAL> ":"             { return COLON; }
<YYINITIAL> "*"             { return ASTERISK; }
<YYINITIAL> "\\\\"          { return LINE_BREAK; }
<YYINITIAL> {SPECIAL}       { return SPECIAL; }
<YYINITIAL> "\\".           { return SPECIAL; }

<YYINITIAL> {WHITE_SPACE}+  { return WHITE_SPACE; }
<YYINITIAL> "\\begin"       { return IDENTIFIER_BEGIN; }
<YYINITIAL> "\\end"         { return IDENTIFIER_END; }
<YYINITIAL> {IDENTIFIER}    { return IDENTIFIER; }
<YYINITIAL> {COMMENT}       { return COMMENT; }
<YYINITIAL> {TEXT}+         { return ARG; }
