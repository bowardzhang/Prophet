package experimentEditor.tabbedPane.contentEditor.ToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;

import experimentEditor.tabbedPane.contentEditor.EditArea;

import util.StringTuple;

@SuppressWarnings("serial")
public class FontSizeBox extends JComboBox implements ActionListener{
	
	private EditArea editArea;
	private ArrayList<StringTuple> fontSizes;
	
	public FontSizeBox(EditArea editArea) {
		super();
		this.editArea = editArea;
		fontSizes = new ArrayList<StringTuple>();
		fontSizes.add(new StringTuple("-3", "-3"));
		fontSizes.add(new StringTuple("-2", "-2"));
		fontSizes.add(new StringTuple("-1", "-1"));
		fontSizes.add(new StringTuple("+1", "+1"));
		fontSizes.add(new StringTuple("+2", "+2"));
		fontSizes.add(new StringTuple("+3", "+3"));
		
		this.addItem("Schriftgröße");
		for(int i=0; i<fontSizes.size(); i++) {
			this.addItem(fontSizes.get(i).getKey());
		}
		this.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (this.getSelectedIndex() != 0) {
			editArea.setEmbedding("<font size=\""+fontSizes.get(this.getSelectedIndex()-1).getValue()+"\">", "</font>");
			this.setSelectedIndex(0);
		}		
	}

}