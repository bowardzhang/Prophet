/**
 * Erstellt ein Panel, welchem die einzelnen W�rter der caption als einzelne JLabels hinzuge�fgt werden.
 */

package questionView;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordWrapLabel extends JPanel {

	String caption;

	/**
	 * Standartkonstruktor, welcher ein Panel mit einem Text (JLabels) erstellt
	 * 
	 * @param s
	 *            Text welcher dargestellt werden soll
	 */
	public WordWrapLabel(String s) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		//this.add(new JLabel("<html>" + s + "</html>"), BorderLayout.CENTER);
		setText(s);
	}

	/**
	 * Setzt den Text (JLabel's) des Panels neu
	 * 
	 * @param s
	 *            Text, welcher gesetzt werden soll
	 */
	public void setText(String s) {
		this.removeAll();
		caption = s;
		String[] captionElements = s.split(" ");
		for (String labelCaption : captionElements) {
			add(new JLabel(labelCaption + " "));
		}

//		ArrayList<JLabel> labels = new ArrayList<JLabel>();
//		double panelWidth = this.getSize().getWidth();
//		JLabel label = new JLabel("");
//		String part1 = s;
//		String part2 = "";
//		if()
//			do {
//				
//				//Solange durchf�hren, wie LabelText zu gro�, oder der 2. Teil nicht leer 
//			} while (panelWidth < label.getGraphics().getFontMetrics()
//					.getStringBounds(part1, 0, part1.length(), getGraphics())
//					.getWidth()
//					|| !part2.equals(""));
//			int whitespacePos = s.lastIndexOf(" ");
	}

	/**
	 * Liefert den gesamten Text des Panels zur�ck
	 * 
	 * @return Text der einzelnen JLabels
	 */
	public String getText() {
		return caption;
	}

}
