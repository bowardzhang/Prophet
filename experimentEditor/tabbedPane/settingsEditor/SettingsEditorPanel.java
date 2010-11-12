package experimentEditor.tabbedPane.settingsEditor;

/**
 * A Dialog to adjust some settings.
 * One is a path an the others are predefined (Settings.java) and could be true oder false
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

import java.awt.event.ActionListener;

import plugins.ExperimentPlugin;
import plugins.ExperimentPlugins;
import util.QuestionTreeNode;
import util.VerticalFlowLayout;
import experimentEditor.tabbedPane.ExperimentEditorTab;


@SuppressWarnings("serial")
public class SettingsEditorPanel extends ExperimentEditorTab {
	private QuestionTreeNode selected;

	/**
	 * Constructor which defines the appearance and do the initialisation of the
	 * variables etc.
	 * 
	 * @param id
	 *            String identifier of this Dialog
	 */
	public SettingsEditorPanel() {
		setLayout(new VerticalFlowLayout());
	}
	
	public void setSelected(QuestionTreeNode sel) {
		selected=sel;
		activate();
	}
	public void activate() {
		removeAll();
		updateUI();
		
		if (selected!=null) {
			// adding checkboxes for the given possible settings in Settings.java
			for (ExperimentPlugin plugin : ExperimentPlugins.getPlugins()) {
				SettingsComponent settingsOption = plugin.getSettingsComponentFactory(selected).build();
				if (settingsOption!=null) {
					add(settingsOption);
				}						
			}
		}
	}
}