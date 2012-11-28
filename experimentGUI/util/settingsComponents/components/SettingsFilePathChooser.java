package experimentGUI.util.settingsComponents.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import experimentGUI.util.settingsComponents.SettingsComponent;

public class SettingsFilePathChooser  extends SettingsComponent {
	private int mode = JFileChooser.FILES_ONLY;
	private static final long serialVersionUID = 1L;
	private JLabel caption;
	private JTextField textField;
	private JButton pathButton;

	public SettingsFilePathChooser() {
		setLayout(new BorderLayout());
		caption = new JLabel();
		add(caption, BorderLayout.NORTH);
		textField = new JTextField();
//		textField.getDocument().addDocumentListener(new DocumentListener() {
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
		add(textField,BorderLayout.CENTER);
		textField.setColumns(20);
		pathButton = new JButton("Search");
		add(pathButton,BorderLayout.EAST);
		pathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File userDir = new File(".");
				JFileChooser fc = new JFileChooser(userDir);
				fc.setFileSelectionMode(mode);
				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String selectedPath;
					try {
						selectedPath = fc.getSelectedFile()
								.getCanonicalPath();
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
					String currentPath;
					try {
						currentPath = userDir.getCanonicalPath();
					} catch (IOException e) {
						e.printStackTrace();
						return;
					}
//					System.out.println(""+(selectedPath.startsWith(currentPath)));
					if (selectedPath.startsWith(currentPath)) {
						selectedPath = selectedPath.substring(currentPath
								.length() + 1);
						JOptionPane.showMessageDialog(null, "Notice: The performed action has created a path that is relative to the current working directory. If your XML file is not directly located in the current directory, you might have to change the generated path.");
					}
					textField.setText(selectedPath.replace('\\','/'));
				}
			}
		});
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public void setCaption(String cap) {
		caption.setText(cap);
	}

	@Override
	public void loadValue() {
		textField.setText(getTreeNode().getValue());
	}

	@Override
	public void saveValue() {
		getTreeNode().setValue(textField.getText());
	}
}
