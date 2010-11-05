/**
 * Diese Klasse baut Komponenten in einem Panel Tabellenartig auf
 * Dabei k�nnen verschiedene Komponenten eingef�gt werden.
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package questionView;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class Table extends JPanel {

	private int x, y; // Anzahl der Spalten und Zeilen
	private int hgap, vgap; // Anzahl der L�cken zwischen den Spalten
	// Speichert intern alle Komponenten
	private ArrayList<ArrayList<JComponent>> components;
	private ArrayList<RadioButtons> radioButtonRows;

	/**
	 * Konstruktor der ein neues Tabellenobjekt erstellt. In diesem k�nnen dann
	 * Elemente eingef�gt oder ersetzt werden.
	 * 
	 * @param hgap
	 *            Horizontale L�cke zwischen zwei Zellen
	 * @param vgap
	 *            Vertikale L�cke zwischen zwei Zellen
	 */
	public Table(int hgap, int vgap) {
		super();
		x = -1;
		y = -1;
		this.hgap = hgap;
		this.vgap = vgap;
		components = new ArrayList<ArrayList<JComponent>>();
		radioButtonRows = new ArrayList<RadioButtons>();
	}

	/**
	 * F�gt ein Komponent zur Tabelle hinzu
	 * 
	 * @param comp
	 *            Komponente die hinzugef�gt werden soll
	 * @param x
	 *            Spalte
	 * @param y
	 *            Zeile
	 */
	public void addComponent(JComponent comp, int x, int y) {
		// Zuerst wird das Element in die verschachtelte ArrayList eingef�gt
		// dazu muss getestet werden ob sie den jetzigen Bereich �berschreitet
		for (int i = this.x; i < x; i++) {
			ArrayList<JComponent> list = new ArrayList<JComponent>();
			for (int j = -1; j < this.y; j++) {
				list.add(null);
			}
			components.add(list);
		}
		this.x = Math.max(x, this.x);
		// evtl. �berz�hlige Zellen in allen Spalten einf�gen
		for (int i = 0; i <= this.x; i++) {
			for (int j = this.y; j < y; j++) {
				components.get(i).add(null);
			}
		}
		this.y = Math.max(y, this.y);
		// Element das derzeit dort ist l�schen und dann neues einf�gen
		components.get(x).remove(y);
		components.get(x).add(y, comp);
		// Tabelle neu malen
		createTable();
	}

	/**
	 * F�gt alle Komponenten mit FormLayout in Tabellenform in das Panel ein
	 */
	private void createTable() {
		// Alle elemente L�schen
		this.removeAll();
		// Gr��te Abma�e f�r die Zellengr��en herausfinden
		Dimension compSize;
		int max;
		// Zellenbreiten
		String cols = "";
		int width = ((x + 1) * hgap);
		for (int i = 0; i <= x; i++) {
			max = 0;
			for (int j = 0; j <= y; j++) {
				if (components.get(i).get(j) != null) {
					max = Math.max(max, (int) components.get(i).get(j)
							.getPreferredSize().getWidth());
				}
			}
			cols += (max + hgap) + "px, ";
			width += max;
		}
		cols = cols.substring(0, cols.length() - 2);
		// Zellenh�hen
		String rows = "";
		int height = ((y + 1) * vgap);
		for (int i = 0; i <= y; i++) {
			max = 0;
			for (int j = 0; j <= x; j++) {
				if (components.get(j).get(i) != null) {
					// Zellenbreiten
					max = Math.max(max, (int) components.get(j).get(i)
							.getPreferredSize().getHeight());
				}
			}
			rows += (max + vgap) + "px, ";
			height += max;
		}
		rows = rows.substring(0, rows.length() - 2);
		FormLayout formLayout = new FormLayout(cols, rows);
		// Componenten hinzuf�gen
		this.setLayout(formLayout);
		CellConstraints cc = new CellConstraints();
		for (int i = 0; i <= x; i++) {
			for (int j = 0; j <= y; j++) {
				if (components.get(i).get(j) != null) {
					this.add(components.get(i).get(j), cc.xy(i + 1, j + 1,
							CellConstraints.LEFT, CellConstraints.TOP));
				}
			}
		}
//		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
	}
	
	/**
	 * F�gt eine von zusammenh�ngenden RadioButtons hinzu.
	 * wird ab eine Zelle x,y mit dem einf�gen begonnen, existieren diese Zellen noch nciht (oder Teile)
	 * werden sie neu erstellt.
	 * Existieren sie bereits mit Inhalt, so wird dieser �berschrieben.
	 * Die RadioButtonReihe wird in einer ExtraLokalen ArrayList gespeichert um sp�ter
	 * einfach auf sie zugreifen zu k�nnen.
	 * @param captions Beschriftungen der RadioButtons
	 * @param x Spalteneinf�geposition
	 * @param y Zeileneinf�geposition
	 */
	public void addRadioRow(String[] captions, int x, int y) {
		radioButtonRows.add(new RadioButtons(captions));
		JRadioButton[] buttons = radioButtonRows.get(radioButtonRows.size()-1).getRadioButtons();
		for(int i=0; i< buttons.length; i++) {
			this.addComponent(buttons[i], x, y+i);
		}
	}

	/**
	 * Gibt Die Anzahl der Spalten der Tabelle zur�ck
	 * @return Anzahl der Spalten
	 */
	public int getX() {
		return x + 1;
	}

	/**
	 * Setzt die Anzahl der Spalten neu (schneidet ab oder f�gt hinzu)
	 * 
	 * @param x
	 *            neue Anzahl der Spalten
	 */
	public void setX(int x) {
		// Spalten abschneiden
		for (int i = this.x; i > x; i--) {
			components.remove(i);
		}
		// Spalten hinzuf�gen
		for (int i = this.x; i <= x; i++) {
			ArrayList<JComponent> list = new ArrayList<JComponent>();
			for (int j = 0; j <= y; j++) {
				list.add(null);
			}
			components.add(list);
		}
		this.x = x;
	}

	/**
	 * Gibt die Anzahl der Zeilen der Tabelle zur�ck
	 * @return Anzahl der Zeilen
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setzt die Anzahl der Zeilen neu (schneidet ab oder f�gt hinzu)
	 * 
	 * @param y neue anzahl der Zeilen
	 */
	public void setY(int y) {
		for (int i = 0; i <= x; i++) {
			//Zeilen abschneiden
			for (int j = this.y; j > y; j--) {
				components.get(i).remove(j);
			}
			//Zeilen hinzuf�gen
			for (int j = this.y; j <= y; j++) {
				components.get(i).add(null);
			}
		}
		this.y = y;
	}

	/**
	 * Gibt den Horizontalen abstand zwischen Zellen zur�ck
	 * @return Horizontaler abstand in Pixeln
	 */
	public int getHgap() {
		return hgap;
	}

	/**
	 * Setzt den horizontalen Abstand zwischen Zellen
	 * Baut dazu auch die Tabelle neu auf.
	 * @param hgap Horizontaler abstand in Pixeln
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
		createTable();
	}

	/**
	 * Liefert den vertikalen Abstand zwischen Zellen zur�ck
	 * @return Vertikaler abstand in Pixeln
	 */
	public int getVgap() {
		return vgap;
	}

	/**
	 * Setzt den Vertikalen abstand zwischen Zellen
	 * Baut dazu auch die Tabelle neu auf.
	 * @param vgap Vertikaler abstand in Pixeln
	 */
	public void setVgap(int vgap) {
		this.vgap = vgap;
		createTable();
	}
}
