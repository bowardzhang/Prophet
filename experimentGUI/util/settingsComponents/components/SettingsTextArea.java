package experimentGUI.util.settingsComponents.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import experimentGUI.util.settingsComponents.SettingsComponent;

public class SettingsTextArea extends SettingsComponent {
	private static final long serialVersionUID = 1L;
	private JLabel caption;
	private JTextArea textArea;

	public SettingsTextArea() {
		setPreferredSize(new Dimension(500, 150));
		setLayout(new BorderLayout());
		caption = new JLabel();
		add(caption, BorderLayout.NORTH);
		textArea = new JTextArea();
		add(new JScrollPane(textArea), BorderLayout.CENTER);
//		textArea.getDocument().addDocumentListener(new DocumentListener() {
//
//			@Override
//			public void changedUpdate(DocumentEvent arg0) {
//				saveValue();
//			}
//
//			@Override
//			public void insertUpdate(DocumentEvent arg0) {
//				saveValue();
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent arg0) {
//				saveValue();
//			}
//		});
	}

	@Override
	public void setCaption(String cap) {
		caption.setText(cap);
	}
	
	@Override
	public String getCaption() {
		return caption.getText();
	}

	@Override
	public void loadValue() {
		textArea.setText(getTreeNode().getValue());
	}

	@Override
	public void saveValue() {
		getTreeNode().setValue(textArea.getText());
	}
}
