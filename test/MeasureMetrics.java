/**
 * Klasse zum Messen von Softwaremetriken eines Textes
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package test;

import java.util.*;

public class MeasureMetrics {

	/**
	 * Gibt die Anzahl der Codezeilen zur�ck
	 * 
	 * @param text
	 *            Text dessen Zeilen gez�hlt werden sollen
	 * @return Anzahl der Zeilen
	 */
	public int getLOC(String text) {
		return text.length() - text.replaceAll("\n", "").length();
	}

	/**
	 * Gibt die Anzahl der echten Codezeilen zur�ck
	 * 
	 * @param text
	 *            Text dessen codezeilen gez�hlt werden sollen
	 * @return Anzahl der Codezeilen
	 */
	public int getLOCOnly(String text) {
		int lineAmount = 0;
		String code = removeComments(text);
		// Alle leeren Zeilen l�schen (die nur aus leerzeichen und tabs
		// bestehen)
		String[] lines = code.split("\n");
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lines[i].replaceAll(" ", "");
			lines[i] = lines[i].replaceAll("\t", "");
			lines[i] = lines[i].replaceAll("\f", "");
			lines[i] = lines[i].replaceAll("\r", "");
			if (lines[i].length() != 0) {
				lineAmount++;
			}
		}
		return lineAmount;
	}
	
	/**
	 * Liefert die McCabe Metrik zur�ck --> Anzahl der bin�ren Verzweigungen + 1
	 * @param text Text dessen McCabe Metrik bestimmt werden soll
	 * @return Anzahl der bin�ren Verzweigungen+1
	 */
	public int getCyclomaticComplexity(String text) {
		int forks = 0;
		String code = removeComments(text);
		code = removeStringText(code);
		int codeLength = code.length();
		//alle if's z�hlen
		code = code.replaceAll("if", "");
		forks += (codeLength - code.length())/2;
		//alle else z�hlen
		codeLength = code.length();
		code = code.replaceAll("else", "");
		forks += (codeLength - code.length())/4;
		//alle case z�hlen
		codeLength = code.length();
		code = code.replaceAll("case", "");
		forks += (codeLength - code.length())/4;
		//alle default z�hlen
		codeLength = code.length();
		code = code.replaceAll("default", "");
		forks += (codeLength - code.length())/7;		
		return forks+1;
	}
	
	/**
	 * Entfernt alle Texte die in Anf�hrungszeichen stehen
	 * @param text
	 * @return
	 */
	private String removeStringText(String text) {
		String code = text;
		int index = code.indexOf("\"");
		String part1 = "";
		String part2 = "";		
		while(index != -1) {
			part1 = code.substring(0, index-1);
			part2 = code.substring(index+1);
			//n�chstes Anf�hrungszeichen suchen vor welchem kein \ steht
			index = part2.indexOf("\"");
			if(index==-1) {
				return part1;
			} else {
				while(index-1 == part2.indexOf("\\\"")) {
					part2 = part2.substring(index+1);
					index = part2.indexOf("\"");
				}
				part2 = part2.substring(index+1);
			}
			code = part1 + part2;
		}		
		return code;
	}
	
	/**
	 * L�scht alle Kommentare aus einem Text
	 * @param text Text der ges�ubert werden soll
	 * @return Text ohne Kommentare
	 */
	private String removeComments(String text) {
		String code = text;
		// alle einzeiligen Kommentare l�schen (damit man danach ohne diese zu
		// beachten die mehrzeiligen l�schen kann)
		String part1 = "";
		String part2 = "";
		int linebreakIndex;
		int commentIndex = code.indexOf("//");
		while (commentIndex != -1) {
			part1 = code.substring(0, commentIndex - 1);
			part2 = code.substring(commentIndex + 2);
			linebreakIndex = part2.indexOf("\n");
			if (linebreakIndex == -1) {
				code = part1;
			} else {
				code = part1 + part2.substring(linebreakIndex + 1);
			}
		}
		// alle mehrzeiligen Kommentare l�schen
		int commentEndIndex;
		commentIndex = code.indexOf("/*");
		while (commentIndex != -1) {
			part1 = code.substring(0, commentIndex - 1);
			part2 = code.substring(commentIndex + 2);
			commentEndIndex = part2.indexOf("*/");
			if (commentEndIndex == -1) {
				code = part1;
			} else {
				code = part1 + part2.substring(commentEndIndex + 2);
			}
		}
		return code;
	}

}
