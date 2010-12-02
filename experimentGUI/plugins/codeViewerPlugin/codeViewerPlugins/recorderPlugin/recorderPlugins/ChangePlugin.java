package experimentGUI.plugins.codeViewerPlugin.codeViewerPlugins.recorderPlugin.recorderPlugins;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import experimentGUI.experimentEditor.tabbedPane.settingsEditorPanel.SettingsComponentDescription;
import experimentGUI.experimentEditor.tabbedPane.settingsEditorPanel.SettingsPluginComponentDescription;
import experimentGUI.experimentEditor.tabbedPane.settingsEditorPanel.settingsComponents.SettingsTextField;
import experimentGUI.plugins.codeViewerPlugin.CodeViewer;
import experimentGUI.plugins.codeViewerPlugin.codeViewerPlugins.recorderPlugin.RecorderPluginInterface;
import experimentGUI.plugins.codeViewerPlugin.tabbedPane.EditorPanel;
import experimentGUI.util.loggingTreeNode.LoggingTreeNode;
import experimentGUI.util.questionTreeNode.QuestionTreeNode;

public class ChangePlugin implements RecorderPluginInterface {
	public final static String KEY = "change";
	public final static String KEY_JOIN = "join";
	public final static String KEY_JOIN_TIME = "jointime";
	public final static String TYPE_INSERT = "insert";
	public final static String TYPE_REMOVE = "remove";
	public final static String ATTRIBUTE_OFFSET = "offset";
	public final static String ATTRIBUTE_LENGTH = "length";
	public final static String ATTRIBUTE_CONTENT = "content";
	
	boolean enabled;
	boolean join;
	long joinTime;

	LoggingTreeNode currentNode;
	Document currentDocument;
	
	DocumentListener myListener;

	@Override
	public SettingsComponentDescription getSettingsComponentDescription() {
		SettingsPluginComponentDescription resultDesc = new SettingsPluginComponentDescription(KEY, "Quelltext�nderungen");
		SettingsPluginComponentDescription joinDesc = new SettingsPluginComponentDescription(KEY_JOIN, "�nderungen zusammenfassen");
		SettingsComponentDescription joinTimeDesc = new SettingsComponentDescription(SettingsTextField.class,KEY_JOIN_TIME, "Grenzzeit (ms, z.B. 1000)");
		joinDesc.addSubComponent(joinTimeDesc);
		resultDesc.addSubComponent(joinDesc);
		return resultDesc;
	}

	@Override
	public void onFrameCreate(QuestionTreeNode selected, CodeViewer viewer,
			LoggingTreeNode newNode) {
		enabled = Boolean.parseBoolean(selected.getAttributeValue(KEY));
		if (enabled) {
			join = Boolean.parseBoolean(selected.getAttribute(KEY).getAttributeValue(KEY_JOIN));
			if (join) {
				try {
					joinTime = Long.parseLong(selected.getAttribute(KEY).getAttribute(KEY_JOIN).getAttributeValue(KEY_JOIN_TIME));
				} catch (Exception e) {
					joinTime=0;
				}
				if (joinTime==0) {
					join=false;
				}
			}
			currentNode = null;
			currentDocument=null;
			myListener = new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					int offset = arg0.getOffset();
					int length = arg0.getLength();
					String content = "";
					try {
						content = arg0.getDocument().getText(offset, length);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
					if (join) {
						LoggingTreeNode lastNode = (LoggingTreeNode)currentNode.getLastChild();
						if (lastNode!=null) {
							//Knoten zusammenf�hren?
							boolean wasInsert = lastNode.getType().equals(TYPE_INSERT);
							if (wasInsert) {
								long timeDiff = System.currentTimeMillis()-Long.parseLong(lastNode.getAttribute(LoggingTreeNode.ATTRIBUTE_TIME));
								int lastEndOffset = Integer.parseInt(lastNode.getAttribute(ATTRIBUTE_OFFSET))+Integer.parseInt(lastNode.getAttribute(ATTRIBUTE_LENGTH));
								if (timeDiff<joinTime && lastEndOffset==offset) {
									int newLength=Integer.parseInt(lastNode.getAttribute(ATTRIBUTE_LENGTH))+length;
									String newContent = lastNode.getAttribute(ATTRIBUTE_CONTENT)+content;
									lastNode.setAttribute(ATTRIBUTE_LENGTH, ""+newLength);
									lastNode.setAttribute(ATTRIBUTE_CONTENT, newContent);
									lastNode.setAttribute(LoggingTreeNode.ATTRIBUTE_TIME, ""+System.currentTimeMillis());
									return;
								}		
							}
						}
					}
					LoggingTreeNode node = new LoggingTreeNode(TYPE_INSERT);
					node.setAttribute(ATTRIBUTE_OFFSET, ""+offset);
					node.setAttribute(ATTRIBUTE_LENGTH, ""+length);
					node.setAttribute(ATTRIBUTE_CONTENT, ""+content);
					currentNode.add(node);
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					int offset = arg0.getOffset();
					int length = arg0.getLength();
					if (join) {
						LoggingTreeNode lastNode = (LoggingTreeNode)currentNode.getLastChild();
						if (lastNode!=null) {
							System.out.println("lastNode!=null");
							//Knoten zusammenf�hren?
							boolean wasRemove = lastNode.getType().equals(TYPE_REMOVE);
							if (wasRemove) {
								System.out.println("was remove");
								long timeDiff = System.currentTimeMillis()-Long.parseLong(lastNode.getAttribute(LoggingTreeNode.ATTRIBUTE_TIME));
								int lastOffset = Integer.parseInt(lastNode.getAttribute(ATTRIBUTE_OFFSET));
								int lastLength = Integer.parseInt(lastNode.getAttribute(ATTRIBUTE_LENGTH));
								boolean wasBackspace = offset+length==lastOffset;
								boolean wasDelete = lastOffset==offset;
								System.out.println(wasBackspace ? "wasBackspace" : wasDelete ? "wasDelete" : "was gar nix");
								if (timeDiff<joinTime && (wasBackspace || wasDelete)) {
									int newLength=lastLength+length;
									lastNode.setAttribute(ATTRIBUTE_OFFSET, ""+offset);
									lastNode.setAttribute(ATTRIBUTE_LENGTH, ""+newLength);
									lastNode.setAttribute(LoggingTreeNode.ATTRIBUTE_TIME, ""+System.currentTimeMillis());
									return;
								}
							}
						}
					}
					LoggingTreeNode node = new LoggingTreeNode(TYPE_REMOVE);
					node.setAttribute(ATTRIBUTE_OFFSET, ""+offset);
					node.setAttribute(ATTRIBUTE_LENGTH, ""+length);
					currentNode.add(node);
				}				
			};
		}
	}

	@Override
	public void onNodeChange(LoggingTreeNode newNode, EditorPanel newTab) {
		if (enabled) {
			if (currentDocument!=null) {
				currentDocument.removeDocumentListener(myListener);
				currentDocument=null;
			}
			currentNode=newNode;
			if (newTab!=null) {
				currentDocument = newTab.getTextArea().getDocument();
				currentDocument.addDocumentListener(myListener);
			}
		}
	}

	@Override
	public String getKey() {
		return KEY;
	}

}
