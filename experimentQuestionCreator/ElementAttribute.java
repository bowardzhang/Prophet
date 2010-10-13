/**
 * Klasse welche zu jeden Inhalt (beliebiger Datentyp) einen Identifizierungsnamen bereith�lt.
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package experimentQuestionCreator;

public class ElementAttribute<Type> {

	String name;	//Name f�r das Datum
	Type content;	//Datum
	
	/**
	 * Konstruktor welcher ein ElementAttribute erschafft
	 * @param name Name f�r das Datum
	 * @param content Datum
	 */
	public ElementAttribute(String name, Type content) {
		this.name = name;
		this.content = content;
	}
	
	/**
	 * Liefert den Namen zur�ck
	 * @return Name als Datum
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Liefert das Datum zur�ck
	 * @return Datum als sein Datentyp
	 */
	public Type getContent() {
		return content;
	}
}
