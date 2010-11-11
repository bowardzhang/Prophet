/**
 * Event f�r ein QuestionElement
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package test.experimentQuestionCreator;

import java.awt.AWTEvent;

public class QuestionElementEvent extends AWTEvent {
	QuestionElement questionElement;	//Element welches das Event ausgel�st hat
	public static final int QELECLOSED = RESERVED_ID_MAX + 1; 	//Event f�r das Schliessen
	public static final int QELEUP = RESERVED_ID_MAX + 2;		//Vorw�rts-Event
	public static final int QELEDOWN = RESERVED_ID_MAX + 3;		//R�ckw�rts-Event
	
	/**
	 * Konstruktor f�r ein neues Event
	 * @param source 
	 * @param id
	 * @param qe
	 */
	QuestionElementEvent(QuestionElement source, int id, QuestionElement qe) {
		super(source, id);
		this.questionElement = qe;
	}
	
	/**
	 * Liefert das Element zur�ck, welches das event ausgel�st hat
	 * @return QuestionElement, welches das Event ausl�ste
	 */
	public QuestionElement getQuestionElement() {
		return questionElement;
	}

}