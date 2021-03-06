package experimentGUI.experimentEditor.tabbedPane.editorTabs;

/**
 * A Dialog to adjust some settings.
 * One is a path an the others are predefined (Settings.java) and could be true oder false
 *
 * @author Markus Köppen, Andreas Hasselberg
 */

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import experimentGUI.Constants;
import experimentGUI.PluginList;
import experimentGUI.experimentEditor.tabbedPane.ExperimentEditorTab;
import experimentGUI.experimentEditor.tabbedPane.ExperimentEditorTabbedPane;
import experimentGUI.util.VerticalLayout;
import experimentGUI.util.questionTreeNode.QuestionTreeNode;
import experimentGUI.util.settingsComponents.SettingsComponent;
import experimentGUI.util.settingsComponents.SettingsComponentDescription;
import experimentGUI.util.settingsComponents.components.SettingsCheckBox;
import experimentGUI.util.settingsComponents.components.SettingsTextField;

/**
 * A tab for changing settings for nodes. Settings are defined by plugins, some are hard-coded.
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SettingsEditorPanel extends ExperimentEditorTab {
	private HashMap<QuestionTreeNode, JScrollPane> scrollPanes = new HashMap<QuestionTreeNode,JScrollPane>();
	private HashMap<QuestionTreeNode, ArrayList<SettingsComponent>> settingsComponents = new HashMap<QuestionTreeNode, ArrayList<SettingsComponent>>();
	private QuestionTreeNode selected;

	/**
	 * Constructor
	 */
	public SettingsEditorPanel() {
		setLayout(new BorderLayout());
		this.setOpaque(false);
	}

	/**
	 * Loads the settings and saved options for the specified node into the tab, called by EditorTabbedPane
	 */
	@Override
	public void activate(QuestionTreeNode s) {
		selected=s;
		this.removeAll();
		this.updateUI();
		if (selected!=null) {
			JScrollPane scrollPane = scrollPanes.get(selected);
			if (scrollPane==null) //the tree node has not been displayed before
			{
				JPanel settingsPanel = new JPanel();
				settingsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				settingsPanel.setLayout(new VerticalLayout(5,VerticalLayout.LEFT,VerticalLayout.TOP));

				settingsComponents.put(s, new ArrayList<SettingsComponent>());
				
				if (selected.isExperiment()) 
				{
					SettingsComponent compExpCode = new SettingsComponentDescription(SettingsTextField.class, 
							Constants.KEY_EXPERIMENT_CODE, "Experiment-Code: ").build(selected);
					settingsPanel.add(compExpCode);
					settingsComponents.get(s).add(compExpCode);
					
					SettingsComponent compShowTimer = new SettingsComponentDescription(SettingsCheckBox.class,
							Constants.KEY_SHOW_TIMER, "Show timer during the experiment").build(selected);
					settingsPanel.add(compShowTimer);
					settingsComponents.get(s).add(compShowTimer);
					
				}
				if (selected.isCategory()) 
				{
					SettingsComponent compAttCss = new SettingsComponentDescription(SettingsCheckBox.class,
							Constants.KEY_DONOTSHOWCONTENT, "Do not show content").build(selected);
					settingsPanel.add(compAttCss);
					settingsComponents.get(s).add(compAttCss);
					
					SettingsComponent compShowTimer = new SettingsComponentDescription(SettingsCheckBox.class,
							Constants.KEY_QUESTIONSWITCHING, "Enable forward & backward page navigation").build(selected);
					settingsPanel.add(compShowTimer);
					settingsComponents.get(s).add(compShowTimer);
					
				}
				SettingsComponentDescription desc =PluginList.getSettingsComponentDescription(selected);
				if (desc != null) {
					do {
						SettingsComponent c = desc.build(selected);
						settingsComponents.get(s).add(c);
						settingsPanel.add(c);
					} while ((desc = desc.getNextComponentDescription()) != null);
				}
				scrollPane = new JScrollPane(settingsPanel);
				scrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
				scrollPane.getVerticalScrollBar().setUnitIncrement(16);
				ExperimentEditorTabbedPane.recursiveSetOpaque(scrollPane);
				scrollPanes.put(selected, scrollPane);
			}
			add(scrollPane, BorderLayout.CENTER);
		}
	}

	/**
	 * saves all changes made, called by EditorTabbedPane
	 */
	@Override
	public void save() {
		if (selected!=null) {
			ArrayList<SettingsComponent> comps = settingsComponents.get(selected);
			if (comps!=null) {
				for (SettingsComponent c : comps) {
					c.saveValue();
				}
			}
		}
	}
}