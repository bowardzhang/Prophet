package experimentGUI.experimentEditor.tabbedPane.editorTabs.contentEditorToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import experimentGUI.util.Pair;


@SuppressWarnings("serial")
public class FormularBox extends JComboBox implements ActionListener {

	private RSyntaxTextArea editArea;
	private ArrayList<String> forms;

	public FormularBox(RSyntaxTextArea editArea) {
		super();
		this.editArea = editArea;
		forms = new ArrayList<String>();
		forms.add("Textfield"); // index 1
		forms.add("TextArea"); // index 2
		forms.add("List"); // index 3
		forms.add("Combobox"); // index 4
		forms.add("Radiobutton"); // index 5
		forms.add("Checkbox"); //index 6

		this.addItem("Forms");
		for (int i = 0; i < forms.size(); i++) {
			this.addItem(forms.get(i));
		}
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		switch (this.getSelectedIndex()) {
		case 0:
			return;
		case 1: // texfield
			String textFieldName = JOptionPane.showInputDialog(null,
					"Name of the Textfield:", "Textfield", 1);
			if (textFieldName != null) {
				editArea.replaceSelection("<input type=\"text\" name=\""
						+ textFieldName + "\">");
			}
			break;
		case 2: // textarea
			String textAreaName = JOptionPane.showInputDialog(this,
					"Name of the Textarea:", "Textarea", 1);
			if (textAreaName != null) {
				editArea.replaceSelection("<textarea name=\"" + textAreaName
						+ "\" cols=\"50\" rows=\"10\"></textarea>");
			}
			break;
		case 3: // list
			Pair<String, String> listInfos = MultilineDialogs
					.showMultilineInputDialog("List Information");
			if (listInfos != null) {
				String[] listEntrys = listInfos.getValue().split(
						System.getProperty("line.separator"));
				String list = "";
				for (int i = 0; i < listEntrys.length; i++) {
					list += System.getProperty("line.separator")
							+ "<option value=\"" + listEntrys[i] + "\">"
							+ listEntrys[i] + "</option>";
				}
				editArea.replaceSelection("<select name=\""
						+ listInfos.getKey() + "\" size=\"3\" multiple>" + list
						+ System.getProperty("line.separator") + "</select>");
			}
			break;
		case 4: // Combobox
			Pair<String, String> comboInfos = MultilineDialogs
					.showMultilineInputDialog("List Information");
			if (comboInfos != null) {
				String[] comboEntrys = comboInfos.getValue().split(
						System.getProperty("line.separator"));
				String combos = "";
				for (int i = 0; i < comboEntrys.length; i++) {
					combos += System.getProperty("line.separator")
							+ "<option value=\"" + comboEntrys[i] + "\">"
							+ comboEntrys[i] + "</option>";
				}
				editArea.replaceSelection("<select name=\""
						+ comboInfos.getKey() + "\">" + combos
						+ System.getProperty("line.separator") + "</select>");
			}
			break;
		case 5: // RadioButton
			Pair<String, String> radioInfos = MultilineDialogs
					.showMultilineInputDialog("List Information");
			if (radioInfos != null) {
				String[] radioEntrys = radioInfos.getValue().split(
						System.getProperty("line.separator"));
				String radios = "";
				for (int i = 0; i < radioEntrys.length; i++) {
					radios += "<input type=\"radio\" name=\""
							+ radioInfos.getKey() + "\" value=\""
							+ radioEntrys[i] + "\">" + radioEntrys[i] + "<br>"
							+ System.getProperty("line.separator");
				}
				editArea.replaceSelection(radios);
			}
			break;
		case 6: // CheckBox
			Pair<String, String> checkInfos = MultilineDialogs
					.showMultilineInputDialog("List Information");
			if (checkInfos != null) {
				String[] checkEntrys = checkInfos.getValue().split(
						System.getProperty("line.separator"));
				String checks = "";
				for (int i = 0; i < checkEntrys.length; i++) {
					checks += "<input type=\"checkbox\" name=\""
							+ checkInfos.getKey() + "\" value=\""
							+ checkEntrys[i] + "\">" + checkEntrys[i] + "<br>"
							+ System.getProperty("line.separator");
				}
				editArea.replaceSelection(checks);
			}
			break;
		}
		this.setSelectedIndex(0);
	}

}