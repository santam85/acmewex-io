package es03a;

/**Interfaccia che definisce i metodi comuni agli utenti dell'applicazione
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 * */
public interface IUser {
	
	/**Recupera il nome dell'utente
	 * 
	 * @return stringa del nome dell'utente
	 * */
	public String getNome();
	/**Setta il nome dell'utente
	 * 
	 * @param nome nuovo nome dell'utente
	 * */
	public void setNome(String nome);
	/**Recupera il cognome dell'utente
	 * 
	 * @return stringa del cognome dell'utente
	 * */
	public String getCognome();
	/**Setta il cognome dell'utente
	 * 
	 * @param cognome nuovo cognome dell'utente
	 * */
	public void setCognome(String cognome);
	/**metodo per recuperare l'id dell'utente
	 * 
	 * @return stringa dell'id dell'utente
	 * */
	public String getId();
	/**Ridefinisce l'id dell'utente in seguito a variazioni dei dati personali*/
	public void reAssignId();
	/**Controlla che la password fornita sia uguale a quella dell'utente
	 * 
	 * @param psw password da controllare
	 * @return true se la password fornita è uguale a quella dell'utente, diversamente false
	 * */
	public boolean parsePsw(String psw);
	/**Setta la password dell'utente fornendo la vecchia (controllo di sicurezza) e la nuova
	 * 
	 * @param oldPsw vecchia password dell'utente
	 * @param newPsw nuova password con cui sostituire la vecchia
	 * @return true in caso di set avvenuto con successo, false altrimenti
	 * */
	public boolean setPsw(String oldPsw, String newPsw);
	/**Fornisce una rappresentazione testuale dell'utente (override del toString della classe Object)
	 * 
	 * @return stringa contenente i dati dell'utente
	 * */
	public String toString();
	/**Recupera il tipo di utente
	 * 
	 * @return valore primitivo intero che rappresenta la tipologia dell'utente
	 * */
	public int getType();

}