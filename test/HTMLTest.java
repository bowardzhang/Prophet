package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class HTMLTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private RSyntaxTextArea textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HTMLTest frame = new HTMLTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HTMLTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JButton button = new JButton("New button");
		contentPane.add(button, BorderLayout.SOUTH);

		textPane = new RSyntaxTextArea();
		textPane.setText("<form id=\"formname\">"
				+ "frage 1 test<br><input name=\"vorname\" type=\"text\" size=\"5\">"
				+ "<br><br><input name ='nextQuestion' type='submit' value='Weiter'>" + "<form>");
		contentPane.add(textPane, BorderLayout.CENTER);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				HTMLEditorKit editKit = new HTMLEditorKit();
				HTMLDocument htmlDoc = (HTMLDocument) editKit.createDefaultDocument();
				StringReader reader = new StringReader(textPane.getText());
				try {
					editKit.read(reader, htmlDoc, 0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				HTMLDocument htmlDoc = (HTMLDocument) textPane.getDocument();

				for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.INPUT); iterator.isValid(); iterator
						.next()) {
					AttributeSet attributes = iterator.getAttributes();
					String srcString = (String) attributes.getAttribute(HTML.Attribute.NAME);
					System.out.println(srcString);
				}
				for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.SELECT); iterator.isValid(); iterator
						.next()) {
					AttributeSet attributes = iterator.getAttributes();
					String srcString = (String) attributes.getAttribute(HTML.Attribute.NAME);
					System.out.println(srcString);
				}
				for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.TEXTAREA); iterator.isValid(); iterator
						.next()) {
					AttributeSet attributes = iterator.getAttributes();
					String srcString = (String) attributes.getAttribute(HTML.Attribute.NAME);
					System.out.println(srcString);
				}
			}
		});
	}

}
