package experimentGUI.plugins.codeViewerPlugin.tabbedPane;

import java.awt.Component;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import experimentGUI.plugins.codeViewerPlugin.CodeViewerPluginInterface;
import experimentGUI.plugins.codeViewerPlugin.CodeViewerPluginList;
import experimentGUI.util.questionTreeNode.QuestionTreeNode;

@SuppressWarnings("serial")
public class EditorTabbedPane extends JTabbedPane {
	private QuestionTreeNode selected;
	private File showDir, saveDir;

	
	public EditorTabbedPane(QuestionTreeNode selected, File showDir, File saveDir) {
		super(JTabbedPane.TOP);
		this.selected = selected;
		this.showDir=showDir;
		this.saveDir=saveDir;
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	public void openFile(String path) {
		EditorPanel e = getEditorPanel(path);
		if (e!=null) {
			this.setSelectedComponent(e);
			e.grabFocus();
			return;
		}
		File file = new File(saveDir.getPath()+path);
		if (!file.exists()) {
			file = new File(showDir.getPath()+path);
		}
		EditorPanel myPanel = new EditorPanel(file, path, selected);
		add(file.getName(), myPanel);
		setSelectedIndex(indexOfComponent(myPanel));
		this.setTabComponentAt(this.getTabCount() - 1, new ButtonTabComponent(this, myPanel));
		myPanel.grabFocus();
	}
	
	public void closeFile(String path) {
		closeEditorPanel(getEditorPanel(path));
	}
	public void closeEditorPanel(EditorPanel editorPanel) {
		for (CodeViewerPluginInterface plugin : CodeViewerPluginList.getPlugins()) {
			plugin.onEditorPanelClose(editorPanel);
		}
		this.remove(editorPanel);
	}

	public EditorPanel getEditorPanel(String path) {
		for (int i = 0; i < getTabCount(); i++) {
			Component myComp = getComponentAt(i);
			if ((myComp instanceof EditorPanel) && ((EditorPanel) myComp).getFilePath().equals(path)) {
				return (EditorPanel)myComp;
			}
		}
		return null;
	}

	public File getShowDir() {
		return showDir;
	}

	public File getSaveDir() {
		return saveDir;
	}
}
