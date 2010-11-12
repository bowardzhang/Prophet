package experimentEditor.tabbedPane.settingsEditor.settingsComponents;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import experimentEditor.tabbedPane.settingsEditor.SettingsComponent;


@SuppressWarnings("serial")
public class SettingsTextField extends SettingsComponent{	
	private JLabel caption;
	private JTextField textField; 
	
	public SettingsTextField() {
		setLayout(new BorderLayout());
		caption = new JLabel();
		add(caption, BorderLayout.NORTH);
		textField = new JTextField();
		add(textField, BorderLayout.CENTER);
		textField.addActionListener(getDefaultActionListener());
	}

	public void setValue(String value) {
		textField.setText(value);
	}
	public String getValue() {
		return textField.getText();
	}
	
	public void setCaption(String cap) {
		caption.setText(cap);
	}
	public String getCaption() {
		return caption.getText();
	}
}