package experimentGUI.experimentViewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;

import experimentGUI.Constants;
import experimentGUI.PluginList;
import experimentGUI.util.QuestionViewPane;
import experimentGUI.util.questionTreeNode.QuestionTreeNode;
import experimentGUI.util.questionTreeNode.QuestionTreeXMLHandler;

/**
 * This class shows the html files (questions) creates the navigation and
 * navigates everything...
 *
 * @author Markus Köppen, Andreas Hasselberg
 *
 */
public class ExperimentViewer extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	// the textpanes (one for each question)
	public QuestionViewPane currentViewPane;
	private HashMap<QuestionTreeNode, QuestionViewPane> textPanes;
	// time objects
	private boolean showtimer = false;
	private JPanel timePanel;
	private HashMap<QuestionTreeNode, ClockLabel> times;
	private ClockLabel totalTime;
	// nodes of the question tree
	private QuestionTreeNode tree;
	private QuestionTreeNode currentNode;

	private File saveDir;

	private HashSet<QuestionTreeNode> enteredNodes;

	private boolean ignoreDenyNextNode = false;
	private boolean exitExperiment = false;
	private boolean experimentNotRunning = true;

	ActionListener myActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			if (command.equals(Constants.KEY_BACKWARD)) {
				previousNode();
			} else if (command.equals(Constants.KEY_FORWARD)) {
				nextNode();
			}
		}
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					String laf = UIManager.getSystemLookAndFeelClassName();
					UIManager.setLookAndFeel(laf);
					ExperimentViewer frame = new ExperimentViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * With the call of the Constructor the data is loaded and everything is
	 * initialized. The first question is showed.
	 *
	 * @param path
	 *            path of the xml file with the data
	 * @param cqlp
	 *            the categorieQuestionListsPanel where the overview is shown
	 */
	public ExperimentViewer() {
		setTitle("Experiment");
		this.setSize(800, 600);
		setLocationRelativeTo(null);

		String fileName = Constants.DEFAULT_FILE;
		if (!(new File(fileName).exists())) {
			fileName = JOptionPane.showInputDialog("Please specify the name of the experiment:");
			if (fileName == null) {
				System.exit(0);
			}
			if (!fileName.endsWith(".xml")) {
				fileName += ".xml";
			}
		}
		try {
			boolean isInDir = new File(fileName).getCanonicalFile().getParentFile()
					.equals(new File(".").getCanonicalFile());
			if (!isInDir) {
				JOptionPane.showMessageDialog(this, "The experiment is not found in the current folder.");
				System.exit(0);
			}
			QuestionTreeNode myTree = QuestionTreeXMLHandler.loadXMLTree(fileName);
			if(myTree!=null) {
				tree=myTree;
			} else {
				JOptionPane.showMessageDialog(this, "No valid experiment data.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "The experiment is not found.");
			System.exit(0);
		}

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (experimentNotRunning) {
					System.exit(0);
				} else if (JOptionPane.showConfirmDialog(null, "The experiment is not complete. Exit anyway?", "Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					exitExperiment = true;
					currentViewPane.clickSubmit();
				}
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		this.setContentPane(contentPane);
		PluginList.experimentViewerRun(this);
		// Starte Experiment
		QuestionTreeNode superRoot = new QuestionTreeNode();
		superRoot.add(tree);
		tree.setParent(null);
		currentNode = superRoot;
		textPanes = new HashMap<QuestionTreeNode, QuestionViewPane>();
		times = new HashMap<QuestionTreeNode, ClockLabel>();
		totalTime = new ClockLabel("Total time");
		timePanel = new JPanel();
		enteredNodes = new HashSet<QuestionTreeNode>();
		nextNode();
	}

	private boolean nextNode() {
		if (denyNextNode()) {
			return false;
		}
		pauseClock();
		if (currentNode == tree) // the root experiment node is selected
		{
			String subject = currentNode.getAnswer(Constants.KEY_SUBJECT);
			if (subject == null || subject.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please input the user ID!", "Error!",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			String experiment = currentNode.getAttributeValue(Constants.KEY_EXPERIMENT_CODE);
			if (experiment == null) {
				experiment = "default";
			}
			saveDir = new File(experiment + "_" + subject);
			if (saveDir.exists()) {
				int i = 1;
				while (saveDir.exists()) {
					saveDir = new File(experiment + "_" + subject + "_" + i);
					i++;
				}
			}
			
			showtimer = Boolean.parseBoolean(currentNode
					.getAttributeValue(Constants.KEY_SHOW_TIMER));
			
			totalTime.start();
			experimentNotRunning=false;
		}

		//step down if we may enter and there are children, else step aside if there is a sibling, else step up
		if (!denyEnterNode() && currentNode.getChildCount() != 0) {
			currentNode = (QuestionTreeNode) currentNode.getFirstChild();
		} else {
			exitNode();
			while (currentNode.getNextSibling() == null) {
				currentNode = (QuestionTreeNode) currentNode.getParent();
				if (currentNode == null) {
					return false;
				}
				exitNode();
			}
			currentNode = (QuestionTreeNode) currentNode.getNextSibling();
		}

		//check if found node is visitable
		if (denyEnterNode()) {
			return nextNode();
		} else {
			boolean doNotShowContent = Boolean.parseBoolean(currentNode
					.getAttributeValue(Constants.KEY_DONOTSHOWCONTENT));
			if (doNotShowContent) {
				enterNode();
				return nextNode();
			} else {
				refresh();
				enterNode();
				return true;
			}
		}
	}

	private boolean previousNode() {
		if (denyNextNode()) {
			return false;
		}
		pauseClock();
		if (currentNode.isQuestion()) {
			QuestionTreeNode tempNode = currentNode;
			while ((QuestionTreeNode) tempNode.getPreviousSibling() != null) {
				tempNode = (QuestionTreeNode) tempNode.getPreviousSibling();
				if (!denyEnterNode()) {
					exitNode();
					currentNode = tempNode;
					refresh();
					enterNode();
					return true;
				}
			}
		}
		return false;
	}

	private boolean denyEnterNode() {
		return exitExperiment || denyEnterNode(currentNode);
	}
	public static boolean denyEnterNode(QuestionTreeNode node) {
		return PluginList.denyEnterNode(node);
	}

	private void enterNode() {
		enteredNodes.add(currentNode);
		PluginList.enterNode(currentNode);
		currentViewPane.grabFocus();
	}

	private boolean denyNextNode() {
		if (ignoreDenyNextNode || exitExperiment) {
			return false;
		}
		String message = PluginList.denyNextNode(currentNode);
		if (message!=null) {
			if (message.length()>0) {
				JOptionPane.showMessageDialog(this, message);
			}
			return true;
		}
		return false;
	}

	private void exitNode() {
		if (enteredNodes.contains(currentNode)) {
			PluginList.exitNode(currentNode);
			if (currentNode.isExperiment()) {
				endQuestionnaire();
			}
			enteredNodes.remove(currentNode);
		}
	}

	private void pauseClock() {
		ClockLabel clock = times.get(currentNode);
		if (clock != null) {
			clock.pause();
		}
	}

	private void refresh() {
		this.setEnabled(false);

		if (currentViewPane != null) {
			contentPane.remove(currentViewPane);
		}

		if (currentNode == null) {
			return;
		}
		currentViewPane = textPanes.get(currentNode);
		if (currentViewPane == null) {
			currentViewPane = new QuestionViewPane(currentNode);
			currentViewPane.setActionListener(myActionListener);
			textPanes.put(currentNode, currentViewPane);
		}
		contentPane.add(currentViewPane, BorderLayout.CENTER);

		ClockLabel clock = times.get(currentNode);
		if (clock == null) {
			if (currentNode.isExperiment()) {
				clock = new ClockLabel(currentNode, null);
			} else {
				clock = new ClockLabel(currentNode, "Current");
			}
			times.put(currentNode, clock);
			clock.start();
		} else {
			clock.resume();
		}
		
		if (showtimer) 
		{
			contentPane.remove(timePanel);
			timePanel.removeAll();
			
			timePanel.add(clock);
			timePanel.add(totalTime);
			contentPane.add(timePanel, BorderLayout.SOUTH);
		}
		
		contentPane.updateUI();
		this.setEnabled(true);
	}

	/**
	 * method which is called when the last question is answered
	 */
	private void endQuestionnaire() {
		contentPane.removeAll();
		contentPane.updateUI();
		JTextPane output = new JTextPane();
		output.setEditable(false);
		output.setEditorKit(new HTMLEditorKit());
		String endMessage = "Survey complete.";
		String outputString = "<p>" + endMessage + "</p>";
		QuestionTreeXMLHandler.saveXMLAnswerTree(tree,
				saveDir.getPath() + System.getProperty("file.separator") + Constants.FILE_ANSWERS);
		outputString += PluginList.finishExperiment();
		output.setText(outputString);
		output.setCaretPosition(0);
		contentPane.add(output, BorderLayout.CENTER);
		experimentNotRunning=true;
	}

	public QuestionTreeNode getTree() {
		return tree;
	}

	public File getSaveDir() {
		return saveDir;
	}

	public void forceNext(boolean hard) {
		ignoreDenyNextNode = hard;
		currentViewPane.clickSubmit();
		ignoreDenyNextNode = false;
	}
	public void saveCurrentAnswers() {
		currentViewPane.saveCurrentAnswersToNode();
	}

	public JPanel getContentPanel() {
		return contentPane;
	}
	public boolean getExperimentNotRunning() {
		return experimentNotRunning;
	}
}
