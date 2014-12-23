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

CRLF            = "\r"|"\n"|"\r\n"
LINE_WS         = [\ \t\f]
WHITE_SPACE     = ({LINE_WS}*{CRLF}+)+

INSTRUCTION     = \\[a-zA-Z]+
COMMENT         = %[^\r\n]*

%state IN_ENTRY

%%
<YYINITIAL> {
    {WHITE_SPACE}+      { yybegin(YYINITIAL); return CRLF; }

    {INSTRUCTION}       { return INSTRUCTION; }
    {COMMENT}           { return COMMENT; }

    [^]                 { return BAD_CHARACTER; }
}
