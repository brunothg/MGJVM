package de.bno.mgjvm.grafik;

public interface ExecutionListener {

	/**
	 * Beginn der Ausführung. Vorbereitungen treffen und alles sperren, das zu
	 * Laufzeit nicht verwedet werden können soll
	 */
	public void startExecution();

	/**
	 * Ende der Ausführung. Durch den Benutzer ausgelößt. Beendet die Ausführung
	 * an beliebiger Stelle. Aufräumen sollte jetzt geschehen.
	 */
	public void stopExecution();

	/**
	 * Führe einen einzigen Schritt aus.
	 */
	public void executeOneStep();

	/**
	 * Führe das Programm bis zum Ende aus
	 */
	public void execute();
}
