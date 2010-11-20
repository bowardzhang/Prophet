package plugins.questionLists;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import plugins.ExperimentPlugin;
import util.QuestionTreeNode;
import experimentEditor.ExperimentEditor;
import experimentEditor.tabbedPane.settingsEditor.SettingsComponentDescription;
import experimentViewer.ExperimentViewer;
import experimentViewer.HTMLFileView;


public class QuestionListPlugin implements ExperimentPlugin {

	@Override
	public List<SettingsComponentDescription> getSettingsComponentDescriptions(
			QuestionTreeNode node) {
		return null;
	}

	@Override
	public void experimentEditorRun(ExperimentEditor experimentEditor) {
	}

	@Override
	public void experimentViewerRun(ExperimentViewer experimentViewer) {
		QuestionListPanel overview = new QuestionListPanel(experimentViewer.getTree());
		overview.setPreferredSize(new Dimension(150, 2));
		experimentViewer.getContentPane().add(overview, BorderLayout.WEST);
	}

	@Override
	public void enterNode(QuestionTreeNode node, HTMLFileView htmlFileView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNode(QuestionTreeNode node, HTMLFileView htmlFileView) {
		// TODO Auto-generated method stub
		
	}

}
