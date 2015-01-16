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
ARGUMENT        = [\w ]+
COMMENT         = %.*
SPECIAL         = [\S]
CRLF            = [\s\r\n]+

%state IN_ENTRY

%%
<YYINITIAL> {
    {WHITE_SPACE}+      { return WHITE_SPACE; }

    "{"                 { return LBRACE; }
    "}"                 { return RBRACE; }
    "["                 { return LBRACKET; }
    "]"                 { return RBRACKET; }
    "("                 { return LPAREN; }
    ")"                 { return RPAREN; }
    ","                 { return COMMA; }
    ":"                 { return COLON; }
    "*"                 { return ASTERISK; }
    "\\\\"              { return LINE_BREAK; }

    {INSTRUCTION}       { return INSTRUCTION; }
    {ARGUMENT}          { return ARGUMENT; }
    {COMMENT}           { return COMMENT; }
    {SPECIAL}           { return SPECIAL; }
    {CRLF}              { return CRLF; }

    [^]                 { yybegin(YYINITIAL); return BAD_CHARACTER; }
} // <YYINITIAL>
