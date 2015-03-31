package es03a;

import java.util.*;

/**Questa classe rappresenta una data
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */

public class ACMEDate implements java.io.Serializable {
	private static final long serialVersionUID = 8L;
	/**campo intero contenente il valore dell'anno*/
	public int year;
	/**campo intero contenente il valore del mese*/
	public int month;
	/**campo intero contenete il valore del giorno*/
	public int date;
	//campo privato che riferisce al calendario
	private Calendar cal;
	/**Costruisce la data attuale*/
	public ACMEDate() {
		//istanza al calendario
		cal = Calendar.getInstance();
		//recupero dei campi anno, mese e giorno e assegnamento
		year = cal.get(Calendar.YEAR);
		//il campo MONTH indica la posizione del mese (da 0 a 11)
		month = cal.get(Calendar.MONTH) + 1;
		date = cal.get(Calendar.DATE);
	}
	/**Costruisce la data indicata dalla stringa
	 * 
	 * @param s stringa che indica la data con formattazione "AAAA,MM,GG"
	 * */
	public ACMEDate(String s) {
		//istanza al calendario
		cal = Calendar.getInstance();
		//tokenizzazione della stringa e passaggio al calendario dei singoli valori convertiti a oggetti di tipo Integer
		StringTokenizer tok = new StringTokenizer(s, ",");
		cal.set(Integer.parseInt(tok.nextToken()), Integer.parseInt(tok.nextToken()) - 1, Integer.parseInt(tok.nextToken()), 23, 59, 59);
		//recupero dei campi anno, mese e giorno e assegnamento
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		date = cal.get(Calendar.DATE);
	}
	/**Restituisce una rappresentazione testuale della data
	 * 
	 * @return data in formato "AAAA,MM,GG"
	 * */
	public String toShortString() {
		return cal.get(Calendar.YEAR) + "," + (cal.get(Calendar.MONTH) + 1)+ "," + cal.get(Calendar.DATE);
	}
	/**Controlla che la data sia successiva a un altra data
	 * 
	 * @param date data rispetto cui fare il controllo
	 * @return true se la data � successiva alla data del campo date, false altrimenti 
	 * */
	public boolean after(ACMEDate date) {
		//creazione di un calendario
		Calendar comp = Calendar.getInstance();
		//set del calendario con i valori di date
		comp.set(date.year, date.month - 1, date.date);
		//invocazione del metodo after per i calendari
		return (cal.after(comp));
	}
	/**Controlla che la data sia precedente a un altra data
	 * 
	 * @param date data rispetto cui fare il controllo
	 * @return true se la data � precedente alla data del campo date, false altrimenti 
	 * */
	public boolean before(ACMEDate date) {
		//creazione di un calendario
		Calendar comp = Calendar.getInstance();
		//set del calendario con i valori di date
		comp.set(date.year, date.month - 1, date.date);
		//invocazione del metodo before per i calendari
		return (cal.before(comp));
	}
}
