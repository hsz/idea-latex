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

EOL             = "\r"|"\n"|"\r\n"
LINE_WS         = [\ \t\f]
WHITE_SPACE     = ({LINE_WS}|{EOL})+

INSTRUCTION     = \\[a-zA-Z]+
ARGUMENT        = [^\ \n\r\t\f]+
COMMENT         = %.*
SPECIAL         = [\S]
CRLF            = [\s\r\n]+

%state IN_GROUP

%%
<YYINITIAL> {
    {WHITE_SPACE}+      { return WHITE_SPACE; }

    "{"                 { yypushback(1); yybegin(IN_GROUP); }
    "["                 { yypushback(1); yybegin(IN_GROUP); }
    "("                 { yypushback(1); yybegin(IN_GROUP); }

    ","                 { return COMMA; }
    ":"                 { return COLON; }
    "*"                 { return ASTERISK; }
    "\\\\"              { return LINE_BREAK; }

    {INSTRUCTION}       { return INSTRUCTION; }
    {COMMENT}           { return COMMENT; }
    {SPECIAL}           { return SPECIAL; }
    {CRLF}              { return CRLF; }

    [^]                 { yybegin(YYINITIAL); return BAD_CHARACTER; }
} // <YYINITIAL>

<IN_GROUP> {
    {WHITE_SPACE}+      { yybegin(YYINITIAL); return CRLF; }

    "{"                 { return LBRACE; }
    "["                 { return LBRACKET; }
    "("                 { return LPAREN; }
    "}"                 { return RBRACE; }
    "]"                 { return RBRACKET; }
    ")"                 { return RPAREN; }

    {ARGUMENT}          { return ARGUMENT; }
}
