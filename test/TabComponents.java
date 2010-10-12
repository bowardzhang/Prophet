package test;

import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import EditorTabbedPane.SearchBar;

public class TabComponents {
	private static LinkedList<JPanel> panels;
	private static LinkedList<JTextArea> textareas;
	private static LinkedList<SearchBar> searchfields;
	private static LinkedList<String> filenames;

	public TabComponents() {
		panels = new LinkedList<JPanel>();
		textareas = new LinkedList<JTextArea>();
		searchfields = new LinkedList<SearchBar>();
		filenames = new LinkedList<String>();
	}

	/*
	 * F�gt jeweils eine Komponentenfamilie hinzu
	 * 
	 * @return gibt an die wievielte Familie hinzugef�gt wurde
	 */
	public int addComponentFamily(String filename) {
		panels.add(new JPanel());
		textareas.add(new JTextArea());
		searchfields.add(new SearchBar(textareas.getLast()));
		filenames.add(filename);
		return panels.size() - 1;
	}

	/*
	 * L�scht eine Komponentenfamilie
	 * 
	 * @param i Gibt an, welche Komponentenfamilie gel�scht werden soll
	 */
	public void removeComponentFamily(int i) {
		panels.remove(i);
		textareas.remove(i);
		searchfields.remove(i);
		filenames.remove(i);
	}

	/*
	 * Gibt ein Panel einer bestimmten Familie zur�ck
	 * 
	 * @return Das besagte JPanel
	 */
	public JPanel getPanel(int i) {
		return panels.get(i);
	}

	/*
	 * Gibt ein TextArea einer bestimmten Familie zur�ck
	 * 
	 * @return Das besagte JTextArea
	 */
	public JTextArea getTextArea(int i) {
		return textareas.get(i);
	}

	/*
	 * Gibt ein Suchfeld einer bestimmten Familie zur�ck
	 * 
	 * @return Das besagte Search
	 */
	public SearchBar getSearchField(int i) {
		return searchfields.get(i);
	}

	/*
	 * Gibt den Familienindex anhand eines Dateipfades zur�ck
	 * 
	 * @param filename Dateiname anhand wessen der Familienindex bestimmt wird
	 */
	public int getFamIndex(String filename) {
		return filenames.indexOf(filename);
	}
}
