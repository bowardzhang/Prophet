/**
 * Klasse welche zum mitschneiden von Nutzerdaten dient
 * 
 * @author Markus K�ppen, Andreas Hasselberg
 */

package EditorTabbedPane;

import java.util.ArrayList;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import test.Watch;

public class UserDataRecorder {

	private ArrayList<FileTime> fileTimes;
	private ArrayList<Watch> clocks;
	private JTabbedPane tabbedPane;
	private int lastClock;

	public UserDataRecorder() {
	}
	
	/**
	 * Methode um den Zeitnehmer an einer TabbedPane zu setzen
	 * @param tabbedPane die TabbedPane, welche �berwacht werden soll
	 */
	public void setFileTime(JTabbedPane tabbedPane) {
		fileTimes = new ArrayList<FileTime>();
		clocks = new ArrayList<Watch>();
		this.tabbedPane = tabbedPane;
		startTimeMeasure();
	}
	
	/**
	 * Startet das Zeitmessen und verwaltet es
	 */
	private void startTimeMeasure() {
		// derzeitige Tabs holen, falls versp�tet gestartet
		for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
			addFileTime(i);
		}
		
		lastClock = tabbedPane.getSelectedIndex();

		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// letzte Clock pausieren und neue starten
				String title = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
				int index = tabbedPane.getSelectedIndex();
				for(int i = 0; i < fileTimes.size(); i++) {
					if(title.equals(fileTimes.get(i).getFile())) {
						System.out.println("�bereinstimmung an: " + i);
						index = i;
					}
				}
				
//				int i = tabbedPane.getSelectedIndex();
				if(lastClock>=0) {
					clocks.get(lastClock).pause();
					fileTimes.get(lastClock).setTime(
							clocks.get(lastClock).getTime());
				}
				
				lastClock = index;

				if (index >= fileTimes.size()) {
					addFileTime(index);
				}
				clocks.get(index).resume();
			}
		});
		
		// derzeitige Uhrzeit starten
		if(lastClock>=0) {
			clocks.get(lastClock).resume();
		}
	}

	/**
	 * Gibt die aktuelle Zeitmessung zur�ck
	 * @return ArrayList mit den Namen und Zeiten
	 */
	public ArrayList<FileTime> getFileTime() {
		//letzte Zeit noch aktualisieren
		if(lastClock>=0) {
			fileTimes.get(lastClock).setTime(clocks.get(lastClock).getTime());
		}
		return fileTimes;
	}
	
	/**
	 * Hilfsfunktion, welche einen Tab zur Zeitmessung hinzuf�gt
	 * @param index Welcher Tab hinzugef�gt werden soll
	 */
	private void addFileTime(int index) {
		fileTimes.add(new FileTime(tabbedPane.getTitleAt(index), 0));
		clocks.add(new Watch());
		clocks.get(index).start();
		clocks.get(index).pause();		
	}
	
	/**
	 * Klasse zur Kapselung eines Dateinamen mit einer Zeit
	 * @author Markus K�ppen, Andreas Hasselberg
	 *
	 */
	public class FileTime {
		String file;
		long time;
		
		/**
		 * Leer-Konstruktor
		 */
		public FileTime() {
			file = "";
			time = 0;
		}
		
		/**
		 * Konstruktor mit Startwerten
		 * @param file Dateiname
		 * @param time Bisherige Zeit
		 */
		public FileTime(String file, long time) {
			this.file = file;
			this.time = time;
		}
		
		/**
		 * Gibt den Inhalt als String zur�ck
		 * @return String, welcher den Inhalt repr�sentiert
		 */
		public String toString() {
			return file + ": " + time;
		}
		
		/**
		 * Liefert den Dateinamen zur�ck
		 * @return Dateiname
		 */
		public String getFile() {
			return file;
		}

		/**
		 * liefert die aktuelle Zeit zur�ck
		 * @return Zeit
		 */
		public long getTime() {
			return time;
		}

		/**
		 * Setzt die Zeit auf einen bestimmten wert
		 * @param time Wert auf den die Zeit gesetzt werden soll
		 */
		public void setTime(long time) {
			this.time = time;
		}
	}
}
