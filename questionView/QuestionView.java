/**
 * Diese Klasse f�gt verschiedene Elemente in ein Panel ein.
 * Dabei wird ein vertikales BoxLayout als Grundstruktur verwendet.
 * Die Elemente welche hinzugef�gt werden, werden in einer extra ArrayList gespeichert.
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package questionView;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.View;

public class QuestionView extends JPanel {

	public static final boolean HORIZONTAL = true;
	public static final boolean VERTICAL = false;

	private ArrayList<PanelContainer> rows;

	/**
	 * Standarkonstruktor. Erstellt ein Panel mit Vertikalem BoxLayout.
	 */
	public QuestionView() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setAlignmentY(TOP_ALIGNMENT);
		rows = new ArrayList<PanelContainer>();
	}

	/**
	 * F�gt eine "Reihe" hinzu, sprich f�gt dem Box Layout ein PanelContainer
	 * hinzu.
	 */
	public void addRow() {
		PanelContainer pc = new PanelContainer();
		pc.setLayout(new WrapLayout(FlowLayout.LEFT));
		pc.setAlignmentX(LEFT_ALIGNMENT);
		pc.setAlignmentY(TOP_ALIGNMENT);
		rows.add(pc);
		this.add(pc);
	}

	/**
	 * Gibt eine spezielle Reihe (PanelContainer) zur�ck.
	 * 
	 * @param i
	 *            index der Reihe
	 * @return PanelContainer an der Position i
	 */
	public PanelContainer getRow(int i) {
		return rows.get(i);
	}

	/**
	 * Gibt die Anzahl der bisher existierenden Reihen (PanelContainer) zur�ck
	 * 
	 * @return Anzahl der PanelContainer
	 */
	public int getRowCount() {
		return rows.size();
	}

	/**
	 * Entfernt eine Komponenten aus einem PanelContainer
	 * 
	 * @param col
	 *            Index der Komponente im PanelContainer
	 * @param row
	 *            Index des PanelContainer
	 * @return true wenn Element gel�scht, false wenn nicht existierende
	 *         Position
	 */
	public boolean removeComponent(int col, int row) {
		if (row < rows.size()) {
			return rows.get(row).removeComponent(col);
		}
		return false;
	}

	/**
	 * F�gt eine Tabelle an der vorgegebenen Position ein Wenn die Zeile noch
	 * nicht existiert wird sie neu angelegt. Wenn die spalte gr��er ist als die
	 * bisherige anzahl der Elemente wird es am Ende angef�gt.
	 * 
	 * @param col
	 *            Index der Spalte
	 * @param row
	 *            Index der Zeile
	 * @param t
	 *            Tabelle die eingef�gt werden soll
	 */
	public void addTable(int col, int row, Table t) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		Table table = t;
		table.setAlignmentX(LEFT_ALIGNMENT);
		table.setAlignmentY(TOP_ALIGNMENT);
		if (col < pc.getComponentCount()) {
			pc.add(table, col);
			pc.addComponent(table, col);
		} else {
			pc.add(table);
			pc.addComponent(table);
		}
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt eine Tabelle am Ende der Zeile ein. Wenn die Zeile noch nicht
	 * existiert wird sie neu angelegt
	 * 
	 * @param row
	 *            Index der Zeile
	 * @param t
	 *            Tabelle die eingef�gt werden soll
	 */
	public void addTable(int row, Table t) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		Table table = t;
		table.setAlignmentX(LEFT_ALIGNMENT);
		table.setAlignmentY(TOP_ALIGNMENT);
		pc.add(table);
		pc.addComponent(table);
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt ein Label an der vorgegebenen Position ein Wenn die Zeile noch nicht
	 * existiert wird sie neu angelegt. Wenn die spalte gr��er ist als die
	 * bisherige anzahl der Elemente wird es am Ende angef�gt.
	 * 
	 * @param col
	 *            Index der Spalte
	 * @param row
	 *            Index der Zeile
	 * @param caption
	 *            Beschriftung des Labels
	 */
	public void addLabel(int col, int row, String caption) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);

		String[] captionElements = caption.split(" ");
		for (String labelCaption : captionElements) {
			JLabel label = new JLabel(labelCaption);
			label.setAlignmentX(LEFT_ALIGNMENT);
			label.setAlignmentY(TOP_ALIGNMENT);
			if (col < pc.getComponentCount()) {
				pc.add(label, col);
				pc.addComponent(label, col);
			} else {
				pc.add(label);
				pc.addComponent(label);
			}
		}
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt ein Label am Ende der Zeile ein. Wenn die Zeile noch nicht existiert
	 * wird sie neu angelegt
	 * 
	 * @param row
	 *            Index der Zeile
	 * @param caption
	 *            Beschriftung des Labels
	 */
	public void addLabel(int row, String caption) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);

		String[] captionElements = caption.split(" ");
		for (String labelCaption : captionElements) {
			JLabel label = new JLabel(labelCaption);
			label.setAlignmentX(LEFT_ALIGNMENT);
			label.setAlignmentY(TOP_ALIGNMENT);
			pc.add(label);
			pc.addComponent(label);
		}
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt RadioButtons an der vorgegebenen Position ein Wenn die Zeile noch
	 * nicht existiert wird sie neu angelegt. Wenn die spalte gr��er ist als die
	 * bisherige anzahl der Elemente wird es am Ende angef�gt.
	 * 
	 * @param col
	 *            Index der Spalte
	 * @param row
	 *            Index der Zeile
	 * @param captions
	 *            Jede caption ist die Beschriftung eines RadioButtons
	 * @param alignment
	 *            Ausrichtung Horizontal wenn true, false wenn Vertikal
	 */
	public void addRadioButtons(int col, int row, String[] captions,
			boolean alignment) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		RadioButtons buttons = new RadioButtons(captions, alignment);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		buttons.setAlignmentY(TOP_ALIGNMENT);
		if (col < pc.getComponentCount()) {
			pc.add(buttons, col);
			pc.addComponent(buttons, col);
		} else {
			pc.add(buttons);
			pc.addComponent(buttons);
		}
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt RadioButtons am Ende der Zeile ein. Wenn die Zeile noch nicht
	 * existiert wird sie neu angelegt
	 * 
	 * @param row
	 *            Index der Zeile
	 * @param captions
	 *            Jede caption ist die Beschriftung eines RadioButtons
	 * @param alignment
	 *            Ausrichtung Horizontal wenn true, false wenn Vertikal
	 */
	public void addRadioButtons(int row, String[] captions, boolean alignment) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		RadioButtons buttons = new RadioButtons(captions, alignment);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		buttons.setAlignmentY(TOP_ALIGNMENT);
		pc.add(buttons);
		pc.addComponent(buttons);
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt ein TextFeld an der vorgegebenen Position ein Wenn die Zeile noch
	 * nicht existiert wird sie neu angelegt. Wenn die spalte gr��er ist als die
	 * bisherige anzahl der Elemente wird es am Ende angef�gt.
	 * 
	 * @param col
	 *            Index der Spalte
	 * @param row
	 *            Index der Zeile
	 * @param columns
	 *            Vorgegebene Gr��e des Textfeldes
	 */
	public void addTextField(int col, int row, int columns) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		JTextField textField = new JTextField("", columns);
		textField.setMaximumSize(textField.getPreferredSize());
		textField.setAlignmentX(LEFT_ALIGNMENT);
		textField.setAlignmentY(TOP_ALIGNMENT);
		if (col < pc.getComponentCount()) {
			pc.add(textField, col);
			pc.addComponent(textField, col);
		} else {
			pc.add(textField);
			pc.addComponent(textField);
		}
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt ein TextField am Ende der Zeile ein. Wenn die Zeile noch nicht
	 * existiert wird sie neu angelegt
	 * 
	 * @param row
	 *            Index der Zeile
	 * @param colums
	 *            Breite des Textfeldes
	 */
	public void addTextField(int row, int columns) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		JTextField textField = new JTextField("", columns);
		textField.setMaximumSize(textField.getPreferredSize());
		textField.setAlignmentX(LEFT_ALIGNMENT);
		textField.setAlignmentY(TOP_ALIGNMENT);
		pc.add(textField);
		pc.addComponent(textField);
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt ein Divider (leeres JPanel) an der vorgegebenen Position ein Wenn
	 * die Zeile noch nicht existiert wird sie neu angelegt. Wenn die spalte
	 * gr��er ist als die bisherige anzahl der Elemente wird es am Ende
	 * angef�gt.
	 * 
	 * @param col
	 *            Index der Spalte
	 * @param row
	 *            Index der Zeile
	 * @param size
	 *            Gr��e des Dividers
	 * @param alignment
	 *            true wenn horizontal, false wenn vertikal
	 */
	public void addDivider(int col, int row, int size, boolean alignment) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		JPanel panel = new JPanel();
		if (alignment == HORIZONTAL) {
			panel.setPreferredSize(new Dimension(size, 1));
			panel.setMinimumSize(new Dimension(size, 1));
			panel.setMaximumSize(new Dimension(size, 1));
		} else {
			panel.setPreferredSize(new Dimension(1, size));
			panel.setMinimumSize(new Dimension(1, size));
			panel.setMaximumSize(new Dimension(1, size));
		}
		if (col < pc.getComponentCount()) {
			pc.add(panel, col);
			pc.addComponent(panel, col);
		} else {
			pc.add(panel);
			pc.addComponent(panel);
		}
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}

	/**
	 * F�gt ein Divider am Ende der Zeile ein. Wenn die Zeile noch nicht
	 * existiert wird sie neu angelegt
	 * 
	 * @param row
	 *            Index der Zeile
	 * @param size
	 *            Gr��e des Dividers
	 * @param alignment
	 *            true wenn horizontal, false wenn vertikal
	 */
	public void addDivider(int row, int size, boolean alignment) {
		// evtl. neue Reihen einf�gen
		while (getRowCount() <= row) {
			addRow();
		}
		// Componente hinzuf�gen
		PanelContainer pc = rows.get(row);
		JPanel panel = new JPanel();
		if (alignment == HORIZONTAL) {
			panel.setPreferredSize(new Dimension(size, 1));
			panel.setMinimumSize(new Dimension(size, 1));
			panel.setMaximumSize(new Dimension(size, 1));
		} else {
			panel.setPreferredSize(new Dimension(1, size));
			panel.setMinimumSize(new Dimension(1, size));
			panel.setMaximumSize(new Dimension(1, size));
		}
		pc.add(panel);
		pc.addComponent(panel);
		pc.setMaximumSize(pc.getPreferredSize());
		updateUI();
	}
}
