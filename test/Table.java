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
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class Table extends JPanel{
	
	private int x, y;	//Anzahl der Spalten und Zeilen
	private ArrayList<ArrayList<JComponent>> components;
	private int cellSizeX, cellSizeY;
	
	public Table() {
		super();
		x = 0;
		y = 0;
		components = new ArrayList<ArrayList<JComponent>>();
		cellSizeX = 0;
		cellSizeY = 0;
	}
	
	/**
	 * F�gt ein Komponent zur Tabelle hinzu
	 * @param comp Komponente die hinzugef�gt werden soll
	 * @param x Spalte
	 * @param y Zeile
	 */
	public void addComponent(JComponent comp, int x, int y) {
		//Zuerst wird das Element in die verschachtelte ArrayList eingef�gt
		//dazu muss getestet werden ob sie den jetzigen Bereich �berschreitet
		if(x>this.x) {
			//�berz�hlige Spalten auff�llen
			for(int i = this.x; i <= x; i++) {
				components.add(new ArrayList<JComponent>());
			}
			//�berz�hlige Zellen in allen Spalten einf�gen
			for(int i=0; i<x; i++) {
				for(int j=components.get(x).size(); j<y; j++) {
					components.get(x).add(null);					
				}
			}
			//Element einf�gen
			components.get(x).add(comp);
			this.x = x;
			this.y = Math.max(y, this.y);
			return;
		}
		if(y> this.y) {
			//�berz�hlige Zellen in der Spalte einf�gen
			for(int i=0; i<y; i++) {
				components.get(x).add(null);
			}
			//Element einf�gen
			components.get(x).add(comp);	
			this.y = y;
			return;
		}
		//Ansonsten Element das derzeit dort ist l�schen und dann neues einf�gen
		components.get(x).remove(y);
		components.get(x).add(y, comp);
		//Tabelle neu malen
		createTable();
	}
	
	private void createTable() {
		//Alle elemente L�schen
		this.removeAll();
		//Gr��te Abma�e f�r die Zellengr��e herausfinden
		Dimension compSize;
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(components.get(x).get(y) != null) {
					compSize = components.get(x).get(y).getSize();
					cellSizeX = Math.max(cellSizeX, (int)compSize.getWidth());
					cellSizeY = Math.max(cellSizeY, (int)compSize.getHeight());
				}
			}
		}
		//Formlayout aufbauen und komponenten hinzuf�gen
		String cols = "";
		for(int i=0; i<=x; i++) {
			cols += cellSizeX + "px, ";
		}
		cols.substring(0, cols.length()-2);
		String rows = "";
		for(int i=0; i<=y; i++) {
			rows += cellSizeY + "px, ";
		}
		rows.substring(0, rows.length()-2);
		FormLayout formLayout = new FormLayout(cols, rows);
		this.setLayout(formLayout);
		CellConstraints cc = new CellConstraints();
		//Componenten hinzuf�gen
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(components.get(x).get(y) != null) {
					this.add(components.get(x).get(y), cc.xy(i, j, CellConstraints.LEFT, CellConstraints.TOP));
				}
			}
		}		
	}
	
}
