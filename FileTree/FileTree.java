package FileTree;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;



/**
 * Display a file system in a JTree view
 * 
 * @author Andreas Hasselberg, Markus Koeppen
 */
@SuppressWarnings("serial")
public class FileTree extends JPanel {	
	private JTree tree;
	private File dir, fireFile;
	private Vector<FileListener> fileListeners;
	
	public FileTree(File d) {
		dir=d;
		setLayout(new BorderLayout());
		tree = new JTree(new FileTreeModel(dir));
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
		         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
		         if ((e.getClickCount() == 2) && (selPath!=null) && ((FileTreeNode)selPath.getLastPathComponent()).getFile().isFile()) {
		        	 fireFile = ((FileTreeNode)selPath.getLastPathComponent()).getFile();
		        	 fireEvent();
		         }
			}
		});
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setViewportView(tree);
		add(BorderLayout.CENTER, scrollpane);
	}
	
	/*
	 * Vorbereitungen zum Casten eines ActionEvents
	 */
	public void addFileListener(FileListener l) {
		if (fileListeners == null)
			fileListeners = new Vector<FileListener>();
		fileListeners.addElement(l);
	}
	public void removeFileListener(FileListener l) {
		if (fileListeners != null)
			fileListeners.removeElement(l);
	}
	private void fireEvent() {
		if (fileListeners == null)
			return;
		FileEvent event = new FileEvent(this, FileEvent.FILE_OPENED, fireFile);
		for (Enumeration<FileListener> e = fileListeners.elements(); e.hasMoreElements(); ) 
            ((FileListener)e.nextElement()).fileOpened(event);
	}
}