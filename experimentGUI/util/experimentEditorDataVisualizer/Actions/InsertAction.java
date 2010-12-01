package experimentGUI.util.experimentEditorDataVisualizer.Actions;

public class InsertAction implements Action{
	
	private String actionCommand;
	private int startTime;
	private String path;
	private int offset;
	private String value;
	
	public InsertAction(String action, int start, String path, int offset, String value) {
		this.actionCommand = action;
		this.startTime = start;
		this.offset = offset;
		this.value = value;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public int getStartTime() {
		return startTime;
	}
	
	public String getPath() {
		return path;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public String getValue() {
		return value;
	}

	public int compareTo(Integer start) {
		return startTime-start;
	}
}