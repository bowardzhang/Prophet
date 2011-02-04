package experimentGUI.experimentEditor.tabbedPane.editorTabs.contentEditorToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import experimentGUI.util.Pair;


@SuppressWarnings("serial")
public class FontSizeBox extends JComboBox implements ActionListener{
	
	private RSyntaxTextArea editArea;
	private ArrayList<Pair<String, String>> fontSizes;
	
	public FontSizeBox(RSyntaxTextArea editArea) {
		super();
		this.editArea = editArea;
		fontSizes = new ArrayList<Pair<String, String>>();
		fontSizes.add(new Pair<String, String>("-3", "-3"));
		fontSizes.add(new Pair<String, String>("-2", "-2"));
		fontSizes.add(new Pair<String, String>("-1", "-1"));
		fontSizes.add(new Pair<String, String>("+1", "+1"));
		fontSizes.add(new Pair<String, String>("+2", "+2"));
		fontSizes.add(new Pair<String, String>("+3", "+3"));
		
		this.addItem("Schriftgr��e");
		for(int i=0; i<fontSizes.size(); i++) {
			this.addItem(fontSizes.get(i).getKey());
		}
		this.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (this.getSelectedIndex() != 0) {
			String size = fontSizes.get(this.getSelectedIndex()-1).getValue();
			String text = editArea.getSelectedText();
			text = text==null ? "" : text;
			editArea.replaceSelection("<font size=\""+size+"\">"+text+"</font>");
			this.setSelectedIndex(0);
		}		
	}

}