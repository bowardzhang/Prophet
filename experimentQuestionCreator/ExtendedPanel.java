/**
 * Extended Panel dient zur Handhabungshilfe im CardLayout
 * Neben dem eigentlichen Panel enth�lt es auch eine ID-Verwaltung f�r das Layout
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package experimentQuestionCreator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ExtendedPanel extends JPanel implements QuestionElementListener {

	private int id; // ID dieses ExtendedPanel
	private String headline; // �berschrift f�r Frage die das Panel
								// repr�sentiert

	private JLabel headlineLabel;
	// Panel welches alle Komponenten der Frage beinhalten wird
	private JPanel questionHolder;
	private BoxLayout boxLayout;

	// statische ArrayList, welche die Ids aller ExtendedPanels beherbergt
	static ArrayList<Integer> ids = new ArrayList<Integer>();

	// Liste mit allen QuestionElementen auf diesem Panel
	private LinkedList<QuestionElement> elements = new LinkedList<QuestionElement>();

	public static final Font HEADLINEFONT = new Font("monospaced", Font.BOLD,
			14);

	/**
	 * Konstruktor der das ExtendedPanel initialisiert
	 * 
	 * @param id
	 *            Die Id, welche das Panel im CardLayout haben wird
	 * @param name
	 *            �berschrift des Panel
	 */
	public ExtendedPanel(int id, String name) {
		super();

		ids.add(id);
		this.id = id;
		this.headline = name;

		initialise();
	}

	/**
	 * Initialisiert das Start-Layout
	 */
	private void initialise() {
		setLayout(new BorderLayout());

		JPanel headlinePanel = new JPanel();
		headlinePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		headlineLabel = new JLabel(headline);
		headlineLabel.setFont(HEADLINEFONT);
		headlinePanel.add(headlineLabel);
		add(headlinePanel, BorderLayout.NORTH);

		questionHolder = new JPanel();
		add(new JScrollPane(questionHolder), BorderLayout.CENTER);
		boxLayout = new BoxLayout(questionHolder, BoxLayout.Y_AXIS);
		questionHolder.setLayout(boxLayout);
	}

	/**
	 * F�gt eine neue Komponente mit Standartgr��e hinzu
	 * 
	 * @param text
	 *            Inhalt/Beschriftung
	 * @param selection
	 *            Komponententyp
	 */
	public void addComponent(String text, int selection) {
		QuestionElement qEle = new QuestionElement(text, selection);
		qEle.addQuestionElementListener(this);
		elements.add(qEle);
		qEle.setAlignmentX(Component.LEFT_ALIGNMENT);
		questionHolder.add(qEle);
		questionHolder.updateUI();
	}

	/**
	 * F�gt eine neue Komponente mit speziellen Eigenschaften hinzu
	 * 
	 * @param text
	 *            Inhalt/Beschriftung
	 * @param selection
	 *            Komponententyp
	 * @param answer
	 *            vorgegebene Antwort
	 * @param menu
	 *            Gibt an, ob ein Editor-Menu f�r diese Komponente mit erzeugt
	 *            werden soll
	 * @param size
	 *            Gr��e
	 * @param border
	 *            Gibt an ob die Komponente umrandet sein soll
	 */
	public void addComponent(String text, String answer, int selection,
			boolean menu, Dimension size, boolean border) {
		QuestionElement qEle = new QuestionElement(text, answer, selection,
				menu, size, border);
		qEle.addQuestionElementListener(this);
		elements.add(qEle);
		qEle.setAlignmentX(Component.LEFT_ALIGNMENT);
		questionHolder.add(qEle);
		questionHolder.updateUI();
	}

	/**
	 * L�scht eine Komponente
	 * 
	 * @param qe
	 *            Komponente, welche entfernt werden soll
	 */
	public void removeComponent(QuestionElement qe) {
		elements.remove(qe);
		questionHolder.remove(qe);
		questionHolder.updateUI();
	}

	/**
	 * Ordnet die Elemente nach der Reihenfolge in der Liste an und aktualisiert
	 * damit die Sicht
	 */
	private void updateView() {
		for (QuestionElement ele : elements) {
			questionHolder.remove(ele);
		}
		for (QuestionElement ele : elements) {
			questionHolder.add(ele);
		}
		questionHolder.updateUI();
	}

	/**
	 * Gibt die n�chste freie Id zur�ck
	 * 
	 * @return Id, welche frei ist
	 */
	public static int nextFreeId() {
		int i = 0;
		while (ids.contains(i)) {
			i++;
		}
		return i;
	}

	/**
	 * L�scht diese Id
	 */
	public void removeId() {
		ids.remove((Integer) id);
	}

	/**
	 * L�scht alle Ids
	 */
	public static void removeIds() {
		ids.clear();
	}

	/**
	 * Gibt die Id dieses ExtendedPanel zur�ck
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gibt die einzelnen Komponenten dieser Frage zur�ck
	 * 
	 * @return LinkedList, welche die Fragekomponenten enth�lt
	 */
	public LinkedList<QuestionElement> getElements() {
		return elements;
	}

	/**
	 * Setzt eine neue �berschrift
	 * 
	 * @param headline
	 *            �berschrift die gesetzt wird
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
		headlineLabel.setText(headline);
	}

	/**
	 * Listener f�r die QuestionElements
	 */
	@Override
	public void questionElementClose(QuestionElementEvent e) {
		removeComponent(e.getQuestionElement());
	}

	@Override
	public void questionElementUp(QuestionElementEvent e) {
		int i = elements.indexOf(e.getQuestionElement());
		if (i - 1 >= 0) {
			elements.remove(i);
			elements.add(i - 1, e.getQuestionElement());
			updateView();
		}
	}

	@Override
	public void questionElementDown(QuestionElementEvent e) {
		int i = elements.indexOf(e.getQuestionElement());
		if (i + 1 < elements.size()) {
			elements.remove(i);
			elements.add(i + 1, e.getQuestionElement());
			updateView();
		}
	}
}
