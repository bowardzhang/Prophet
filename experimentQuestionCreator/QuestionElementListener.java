/**
 * Interface f�r den QuestionElementListener
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package experimentQuestionCreator;


public interface QuestionElementListener {
	/**
	 * Methode f�r das Schliessen
	 * @param e
	 */
	void questionElementClose(QuestionElementEvent e);
	
	/**
	 * Methode f�r das Vorr�cken
	 * @param e
	 */
	void questionElementUp(QuestionElementEvent e);
	
	/**
	 * Methode f�r das Zur�ckfallen
	 * @param e
	 */
	void questionElementDown(QuestionElementEvent e);
}
