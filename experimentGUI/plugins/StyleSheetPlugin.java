package experimentGUI.plugins;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.text.html.HTMLEditorKit;

import experimentGUI.PluginInterface;
import experimentGUI.experimentViewer.ExperimentViewer;
import experimentGUI.util.questionTreeNode.QuestionTreeNode;
import experimentGUI.util.settingsComponents.SettingsComponentDescription;
import experimentGUI.util.settingsComponents.SettingsPluginComponentDescription;
import experimentGUI.util.settingsComponents.components.SettingsTextField;

public class StyleSheetPlugin implements PluginInterface {
	
	private static final String KEY = "stylesheet";
	private static final String KEY_CSS_FILE = "css_file";

	private String cssFile = "";
	

	@Override
	public SettingsComponentDescription getSettingsComponentDescription(QuestionTreeNode node) 
	{
		if (node.isExperiment()) {
			SettingsPluginComponentDescription result = new SettingsPluginComponentDescription(KEY,
					"Attach style sheet", true);
			result.addSubComponent(new SettingsComponentDescription(SettingsTextField.class, KEY_CSS_FILE,
					"CSS file name (in the current folder):"));
			return result;
		}
		return null;
	}

	@Override
	public void experimentViewerRun(ExperimentViewer experimentViewer) {
		if (cssFile != "")
		{
			try {    
				URL base = Thread.currentThread().getContextClassLoader().getResource("");
				URL css = new URL(base + cssFile);
				//System.out.println(HTML_HEAD);//css.toExternalForm()
				HTMLEditorKit kit = experimentViewer.currentViewPane.editorKit;
				kit.getStyleSheet().importStyleSheet(css);
			} catch( MalformedURLException ex ) {
				}
		}
	}


	@Override
	public boolean denyEnterNode(QuestionTreeNode node) {
		return false;
	}


	@Override
	public void enterNode(QuestionTreeNode node) {
		if (node.isExperiment()) {
			cssFile = node.getAttribute(KEY).getAttributeValue(KEY_CSS_FILE);
		}
	}

	@Override
	public void exitNode(QuestionTreeNode node) {
	}

	@Override
	public String finishExperiment() {
		return null;
	}

	@Override
	public String denyNextNode(QuestionTreeNode currentNode) {
		return null;
	}
}
