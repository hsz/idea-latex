package mobi.hsz.idea.latex.folding;

import com.intellij.application.options.editor.CodeFoldingOptionsProvider;
import com.intellij.openapi.options.BeanConfigurable;
import mobi.hsz.idea.latex.LatexBundle;
import mobi.hsz.idea.latex.folding.LatexFoldingSettings;

public class LatexFoldingOptionsProvider extends BeanConfigurable<LatexFoldingSettings> implements CodeFoldingOptionsProvider {

    public LatexFoldingOptionsProvider() {
        super(LatexFoldingSettings.getInstance());
        checkBox("FoldingForAllBlocks", LatexBundle.message("foldingSettings.add.folding.for.all.blocks"));
    }
}