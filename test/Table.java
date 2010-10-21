/**
 * Diese Klasse baut Komponenten in einem Panel Tabellenartig auf
 * Dabei k�nnen verschiedene Komponenten eingef�gt werden.
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package test;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Table extends JPanel {

	private int x, y; // Anzahl der Spalten und Zeilen
	private int hgap, vgap;
	private ArrayList<ArrayList<JComponent>> components;
	private ArrayList<Integer> cellWidth;
	private ArrayList<Integer> cellHeight;

	public Table(int hgap, int vgap) {
		super();
		x = 0;
		y = 0;
		this.hgap = hgap;
		this.vgap = vgap;
		components = new ArrayList<ArrayList<JComponent>>();
		cellWidth = new ArrayList<Integer>();
		cellHeight = new ArrayList<Integer>();
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
		// eventuell �berz�hlige Spalten auff�llen
		for (int i = this.x; i <= x; i++) {
			ArrayList<JComponent> list = new ArrayList<JComponent>();
			for(int j=0; j<=y; j++) {
				list.add(null);
			}			
			components.add(list);
		}
		this.x = Math.max(x, this.x);
		// evtl. �berz�hlige Zellen in allen Spalten einf�gen
		for (int i = 0; i <= this.x; i++) {
			for (int j = this.y; j <= y; j++) {
				components.get(i).add(null);
			}
		}
		this.y = Math.max(y, this.y);
		// Element das derzeit dort ist l�schen und dann neues einf�gen
		components.get(x).remove(y);
		components.get(x).add(y, comp);
		System.out.println("x: " + this.x + " - y: " + this.y + " - Text: " + ((JLabel)comp).getText());
		// Tabelle neu malen
		createTable();
	}

	private void createTable() {
		// Alle elemente L�schen
		this.removeAll();
		// Gr��te Abma�e f�r die Zellengr��en herausfinden
		Dimension compSize;
		int max;
		//Zellenbreiten
		cellWidth.clear();
		for (int i = 0; i <= x; i++) {
			max = 0;
			for (int j = 0; j <= y; j++) {
				if (components.get(i).get(j) != null) {
					max = Math.max(max, (int) components.get(i).get(j).getPreferredSize().getWidth());
				}
			}
			cellWidth.add(max);
		}
		//Zellenh�hen
		cellHeight.clear();
		for (int i = 0; i <= y; i++) {
			max = 0;
			for (int j = 0; j <= x; j++) {
				if (components.get(j).get(i) != null) {
					//Zellenbreiten
					max = Math.max(max, (int) components.get(j).get(i).getPreferredSize().getHeight());
				}
			}
			cellHeight.add(max);
		}
		// Formlayout aufbauen
		String cols = "";
		for (int i = 0; i <= x; i++) {
			cols += (cellWidth.get(i)+hgap) + "px, ";
		}
		cols = cols.substring(0, cols.length() - 2);
		String rows = "";
		for (int i = 0; i <= y; i++) {
			rows += (cellHeight.get(i)+vgap) + "px, ";
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
		//Gr��e dieser Komponente setzen
		int width = ((x+1)*hgap);
		for(int i=0; i<=x; i++) {
			width += cellWidth.get(i);
		}
		int height = ((y+1)*vgap);
		for(int i=0; i<=y; i++) {
			height += cellHeight.get(i);
		}
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
	}

}
