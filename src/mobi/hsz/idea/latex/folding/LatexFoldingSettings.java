package mobi.hsz.idea.latex.folding;

import com.intellij.codeInsight.folding.CodeFoldingSettings;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@State(
        name = "LatexFoldingSettings",
        storages = {@Storage("latex_folding_settings.xml")}
)
public class LatexFoldingSettings implements PersistentStateComponent<LatexFoldingSettings>, ExportableComponent {
    private boolean ADD_FOLDING_FOR_ALL_BLOCKS = false;

    public static LatexFoldingSettings getInstance() {
        return ServiceManager.getService(LatexFoldingSettings.class);
    }

    public LatexFoldingSettings getState() {
        return this;
    }

    public void loadState(LatexFoldingSettings latexFoldingSettings) {
        XmlSerializerUtil.copyBean(latexFoldingSettings, this);
    }

    @NotNull
    public File[] getExportFiles() {
        return new File[]{PathManager.getOptionsFile("latex_folding_settings")};
    }

    @NotNull
    public String getPresentableName() {
        return "Latex Folding Settings";
    }

    public boolean isFoldingForAllBlocks() {
        return ADD_FOLDING_FOR_ALL_BLOCKS;
    }

    public boolean getFoldingForAllBlocks() { return ADD_FOLDING_FOR_ALL_BLOCKS; }
    public void setFoldingForAllBlocks(boolean value) {
        ADD_FOLDING_FOR_ALL_BLOCKS = value;
    }

}