package experimentGUI.util.experimentEditorDataVisualizer.Actions;

import java.io.File;

import experimentGUI.plugins.codeViewerPlugin.CodeViewer;
import experimentGUI.plugins.codeViewerPlugin.fileTree.FileEvent;

public class FileAction implements Action{
	
	public static final String ACTION_COMMAND = "FileAction";
	private long startTime;
	private long endTime;
	private String path;
	
	public FileAction(long start, long end, String path) {
		this.startTime = start;
		this.endTime = end;
		this.path = path;
	}
	
	public void execute(CodeViewer codeViewer) {
			File fireFile = new File(path);
			//codeViewer.fileEventOccured(new FileEvent(null, FileEvent.FILE_OPENED, fireFile));		
	}

	public String getActionCommand() {
		return ACTION_COMMAND;
	}

	public long getStartTime() {
		return startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public String getPath() {
		return path;
	}

	public int compareTo(Integer start) {
		return (int) startTime-start;
	}
}
