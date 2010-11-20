package plugins.codeViewer.codeViewerPlugins.UndoRedo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class UndoRedo implements DocumentListener, KeyListener{

	Document document;
	ArrayList<Change> changes;
	String lastText;
	
	int pos;
	boolean undoRedoChange;
	
	public UndoRedo(Document document) {
		changes = new ArrayList<Change>();
		pos = 0;
		undoRedoChange = false;
		this.document = document;
		document.addDocumentListener(this);
		
		try {
			document.getText(0, document.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void undo() {
		if(pos-1 >= 0) {
			undoRedoChange = true;
			pos--;
			Change change = changes.get(pos);
			String text = "";
			switch(change.getType()) {
			case Change.INSERT:
				text = document.getText(0, change.getOffset()) + text.substring(change.getOffset()+change.getChange().length());
				break;
			case Change.REMOVE:
				text = new JTextPane().getText(0, change.getOffset()) + change.getChange() + text.substring(change.getOffset());
				break;
			}
			textPane.setText(text);
			undoRedoChange = false;
		}
	}
	
	public void redo() {
		if(pos+1 <= changes.size()) {
			undoRedoChange = true;
			Change change = changes.get(pos++);
			String text = textPane.getText();
			switch(change.getType()) {
			case Change.INSERT:
				text = text.substring(0, change.getOffset()) + change.getChange() + text.substring(change.getOffset());
				break;
			case Change.REMOVE:
				text = text.substring(0, change.getOffset()) + text.substring(change.getOffset() + change.getChange().length());
				break;
			}
			textPane.setText(text);	
			undoRedoChange = false;			
		}
	}	
	
	@Override
	public void changedUpdate(DocumentEvent de) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent de) {
		if(!undoRedoChange) {
			Change change = new Change();
			change.setOffset(de.getOffset());
			change.setChange(textPane.getText().substring(de.getOffset(), de.getOffset()+de.getLength()));
			change.setType(Change.INSERT);
			pos++;
			while(pos < changes.size()) {
				changes.remove(pos);				
			}
			changes.add(change);
			pos=changes.size();
			
			try {
				lastText = textPane.getDocument().getText(0, textPane.getDocument().getLength());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void removeUpdate(DocumentEvent de) {
		if(!undoRedoChange) {
			Change change = new Change();
			change.setOffset(de.getOffset());
			change.setChange(lastText.substring(de.getOffset(), de.getOffset()+de.getLength()));
			change.setType(Change.REMOVE);
			pos++;
			while(pos < changes.size()) {
				changes.remove(pos);				
			}
			changes.add(change);
			pos=changes.size();
			
			lastText = textPane.getText();
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
			undo();
		} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
			redo();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
