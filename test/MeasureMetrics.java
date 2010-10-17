/**
 * Klasse zum Messen von Softwaremetriken eines Textes
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package test;

public class MeasureMetrics {
	
	/**
	 * Gibt die Anzahl der codezeilen zur�ck
	 * @param text Text dessen Zeilen gez�hlt werden sollen
	 * @return Anzahl der Codezeilen
	 */
	public int getLOC(String text) {
		return text.length() - text.replaceAll("\n", "").length();
	}

}
