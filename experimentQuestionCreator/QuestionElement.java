package experimentQuestionCreator;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QuestionElement extends JPanel {

	// Vector der alle Listener beherbergt
	Vector<QuestionElementListener> questionElementListeners;

	// Konstanten, f�r die jeweiligen Komponenten stehen
	public static final int LABEL = 0;
	public static final int TEXTFIELD = 1;
	public static final int TEXTAREA = 2;
	public static final int COMBOBOX = 3;
	public static final int CHECKBOX = 4;
	public static final int RADIOBUTTON = 5;

	// Gr��e des Menus - Konstant, gilt f�r alle --> static
	private static Dimension menuSize;

	private JPanel menuPanel; // Panel f�r menu der Komponente
	private JPanel contentPanel;// Panel f�r Inhalt der Komponente
	private JLabel close;

	private int selection;// ausgew�hlte Komponente
	private String text;

	// Variablen f�r Drag and Drop
	private boolean dragged;
	private Point oldPos;
	
	//Komponente die erstellt werden wird
	private JComponent comp;

	/**
	 * Konstruktor f�r das erstellen einer Komponente mit Standartgr��e
	 * 
	 * @param text
	 *            Inhalt/Beschriftung der Komponente
	 * @param selection
	 *            Komponententyp
	 */
	public QuestionElement(String text, int selection) {
		super();
		this.selection = selection;
		this.text = text;

		dragged = false;
		oldPos = new Point();

		super.setLayout(new FlowLayout(FlowLayout.LEFT));
		createMenu();
		createContent(text, selection);
		add(menuPanel);
		add(contentPanel);

		addDragAndDrop();

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}

	/**
	 * Konstruktor f�r das Erstellen einer Komponente mir bestimmter Gr��e
	 * 
	 * @param text
	 *            Inhalt/Beschriftung der Komponente
	 * @param selection
	 *            Komponententyp
	 * @param size
	 *            Gr��e der Komponente
	 */
	public QuestionElement(String text, int selection, Dimension size) {
		super();
		this.selection = selection;
		this.text = text;

		dragged = false;
		oldPos = new Point();

		super.setLayout(new FlowLayout(FlowLayout.LEFT));
		createMenu();
		createContent(text, selection, size);
		add(menuPanel);
		add(contentPanel);

		addDragAndDrop();

		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}

	/**
	 * Methode die das Menu zur Komponente erstellt
	 */
	private void createMenu() {
		menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// zur�ck-Button und Listener
		JButton up = new JButton("<");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fireEvent(QuestionElementEvent.QELEUP);
			}
		});
		// vor-Button und Listener
		JButton down = new JButton(">");
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fireEvent(QuestionElementEvent.QELEDOWN);
			}
		});
		// schliessen-Label und Listener
		close = new JLabel("x");
		close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				fireEvent(QuestionElementEvent.QELECLOSED);
			}
		});

		menuPanel.add(up);
		menuPanel.add(down);
		menuPanel.add(close);

		// Menu-Gr��e einmalig messen
		if (menuSize == null) {
			menuSize = menuPanel.getPreferredSize();
		} else {
			menuPanel.setPreferredSize(menuSize);
			menuPanel.setMaximumSize(menuSize);
			menuPanel.setMinimumSize(menuSize);
		}
	}

	/**
	 * Aktualisiert die Gr��e der Komponente
	 * 
	 * @param change
	 *            Dimension um die sich die Gr��e �ndern soll
	 */
	private void updateSize(Dimension change) {
		Dimension oldD = getPreferredSize();
		Dimension newD = new Dimension(
				(int) (oldD.getWidth() + change.getWidth()),
				(int) (oldD.getHeight() + change.getHeight()));
		setPreferredSize(newD);
		setMaximumSize(newD);
		setMinimumSize(newD);
		updateUI();
	}

	/**
	 * Methode um die eigentliche Komponente zu erstellen
	 * 
	 * @param text
	 *            Inhalt/Beschriftung der Komponente
	 * @param selection
	 *            Komponententyp
	 */
	private void createContent(String text, int selection) {
		createContentHelp(text, selection);
		// n�tige Gr��e des gesamten Panels (mit Menu) berechnen
		Dimension componentSize = contentPanel.getPreferredSize();
		Dimension absoluteSize = new Dimension((int) (componentSize.getWidth()
				+ menuSize.getWidth() + 100), (int) (Math.max(
				componentSize.getHeight(), menuSize.getHeight()) + 25));
		// Gr��e setzen
		super.setPreferredSize(absoluteSize);
		super.setMinimumSize(absoluteSize);
		super.setMaximumSize(absoluteSize);
	}

	/**
	 * Methode um eigentlich Komponente mit spezieller Gr��e zu erstellen
	 * 
	 * @param text
	 *            Inhalt/Beschriftung der Komponente
	 * @param selection
	 *            Komponententyp
	 * @param size
	 *            Gr��e der Komponente
	 */
	private void createContent(String text, int selection, Dimension size) {
		createContentHelp(text, selection);
		// Gr��e der Komponente setzen
		contentPanel.setPreferredSize(size);
		contentPanel.setMinimumSize(size);
		contentPanel.setMaximumSize(size);
		// n�tige Gr��e des gesamten Panels (mit Menu) berechnen
		Dimension componentSize = contentPanel.getPreferredSize();
		Dimension absoluteSize = new Dimension((int) (componentSize.getWidth()
				+ menuSize.getWidth() + 100), (int) (Math.max(
				componentSize.getHeight(), menuSize.getHeight()) + 25));
		// Gr��e setzen
		super.setPreferredSize(absoluteSize);
		super.setMinimumSize(absoluteSize);
		super.setMaximumSize(absoluteSize);
	}

	/**
	 * Hilfsfunktion zur CreateContent-Methode, da diese �berladen ist und der
	 * Code �bersichtlicher gehalten werden soll
	 */
	private void createContentHelp(String text, int selection) {
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Text splitten f�r Componenten wie JRadioButton
		String[] texte = text.split("\n");

		// Komponente erstellen
		switch (selection) {
		case LABEL:
			comp = new JLabel(text);
			break;
		case TEXTFIELD:
			comp = new JTextField(text, 10);
			break;
		case TEXTAREA:
			comp = new JTextArea(text);
			break;
		case COMBOBOX:
			comp = new JComboBox(texte);
			break;
		case CHECKBOX:
			comp = new JPanel();
			comp.setLayout(new GridLayout(texte.length, 1));
			for (int i = 0; i < texte.length; i++) {
				JCheckBox chkbox = new JCheckBox(texte[i]);
				comp.add(chkbox);
			}
			break;
		case RADIOBUTTON:
			comp = new JPanel();
			comp.setLayout(new GridLayout(texte.length, 1));
			ButtonGroup radioGroup = new ButtonGroup();
			for (int i = 0; i < texte.length; i++) {
				JRadioButton radiobtn = new JRadioButton(texte[i]);
				comp.add(radiobtn);
				radioGroup.add(radiobtn);
			}
			break;
		default:
			break;
		}
		contentPanel.add(comp, BorderLayout.CENTER);
	}

	/**
	 * Liefert den Komponententyp als Integer-Zur�ck
	 * 
	 * @return (0=JLabel; 1=JTextField; 2=JTextArea; 3=JComboBox; 4=JCheckBox;
	 *         5=JRadioButton)
	 */
	public int getSelection() {
		return selection;
	}

	/**
	 * Liefert den Inhalt/Beschriftung der Komponente zur�ck
	 * 
	 * @return Inhalt/Beschriftung der Komponente
	 */
	public String getText() {
		return text;
	}

	/**
	 * Liefert Gr��e der Komponente (ohne Menu) zur�ck
	 * 
	 * @return
	 */
	public Dimension getElementSize() {
		return contentPanel.getPreferredSize();
	}

	/**
	 * Erstellt Listener f�r das Drag and Drop des contentPanels
	 */
	public void addDragAndDrop() {
		contentPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				dragged = true;
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			}

		});
		contentPanel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent me) {
				if (dragged) {
					Dimension oldSize = contentPanel.getPreferredSize();
					Point newPos = me.getPoint();
					Dimension change = new Dimension(
							(int) (newPos.getX() - oldPos.getX()),
							(int) (newPos.getY() - oldPos.getY()));
					Dimension newSize = new Dimension(
							(int) (oldSize.getWidth() + change.getWidth()),
							(int) (oldSize.getHeight() + change.getHeight()));
					contentPanel.setPreferredSize(newSize);
					contentPanel.setMaximumSize(newSize);
					contentPanel.setMinimumSize(newSize);
					updateSize(change);
					dragged = false;
				}
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent me) {
				oldPos = me.getPoint();
			}
		});
	}

	/**
	 * Methode f�r das Hinzuf�gen eines QuestionElementListener
	 * 
	 * @param listener
	 */
	public void addQuestionElementListener(QuestionElementListener listener) {
		if (questionElementListeners == null)
			questionElementListeners = new Vector<QuestionElementListener>();
		questionElementListeners.addElement(listener);
	}

	/**
	 * Methode f�r das Entfernen eines QuestionElement Listener
	 * 
	 * @param listener
	 */
	public void removeQuestionelementListener(QuestionElementListener listener) {
		if (questionElementListeners != null)
			questionElementListeners.removeElement(listener);
	}

	/**
	 * Methode die das Event startet
	 * 
	 * @param eventType
	 *            Event, welches gestartet werden soll
	 */
	private void fireEvent(int eventType) {
		if (questionElementListeners == null)
			return;
		QuestionElementEvent event;
		// Event Erstellen
		switch (eventType) {
		case QuestionElementEvent.QELECLOSED:
			event = new QuestionElementEvent(this,
					QuestionElementEvent.QELECLOSED, this);
			break;
		case QuestionElementEvent.QELEUP:
			event = new QuestionElementEvent(this, QuestionElementEvent.QELEUP,
					this);
			break;
		case QuestionElementEvent.QELEDOWN:
			event = new QuestionElementEvent(this, QuestionElementEvent.QELEUP,
					this);
			break;
		default:
			return;
		}
		// Listener
		for (Enumeration<QuestionElementListener> e = questionElementListeners
				.elements(); e.hasMoreElements();) {
			switch (eventType) {
			case QuestionElementEvent.QELECLOSED:
				((QuestionElementListener) e.nextElement())
						.questionElementClose(event);
				break;
			case QuestionElementEvent.QELEUP:
				((QuestionElementListener) e.nextElement())
						.questionElementUp(event);
				break;
			case QuestionElementEvent.QELEDOWN:
				((QuestionElementListener) e.nextElement())
						.questionElementDown(event);
				break;
			default:
				return;
			}
		}
	}
}
