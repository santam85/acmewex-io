/**
 * 
 */
package es03a;

/**Questa classe rappresenta un utente
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class User implements IUser,java.io.Serializable{
	private static final long serialVersionUID = 3L;
	//campi privati dove si mantengono nome, cognome e password dell'utente
	private String nome,cognome,psw;
	/**campo contenente l'id dell'utente*/
	protected String id;
	/**Costruisce l'utente settando i campi con le stringhe passategli
	 * 
	 * @param nome nome dell'utente
	 * @param cognome cognome dell'utente
	 * @param psw password dell'utente
	 * */
	public User(String nome,String cognome,String psw){
		this.nome=nome;
		this.cognome=cognome;
		this.psw=psw;
		//assegnamento dell'id
		reAssignId();
	}
	/**Assegna l'id in base al tipo di utente (il metodo � override dalle classi Studente, Docente e Admin)*/
	public void reAssignId(){
		id="guest.user";
	}
	/**Recupera il nome dell'utente
	 * 
	 * @return stringa del nome dell'utente
	 * */
	public String getNome(){
		return nome;
	}
	/**Setta il nome dell'utente
	 * 
	 * @param nome nuovo nome dell'utente
	 * */
	public void setNome(String nome){
		this.nome=nome;
		//reassegnamento dell'id
		reAssignId();
	}
	/**Recupera il cognome dell'utente
	 * 
	 * @return stringa del cognome dell'utente
	 * */
	public String getCognome(){
		return cognome;
	}
	/**Setta il cognome dell'utente
	 * 
	 * @param cognome nuovo cognome dell'utente
	 * */
	public void setCognome(String cognome){
		this.cognome=cognome;
		//reassegnamento dell'id
		reAssignId();
	}
	/**metodo per recuperare l'id dell'utente
	 * 
	 * @return stringa dell'id dell'utente
	 * */
	public String getId(){
		return id;
	}
	/**Controlla che la password fornita sia uguale a quella dell'utente
	 * 
	 * @param psw password da controllare
	 * @return true se la password fornita � uguale a quella dell'utente, diversamente false
	 * */
	public boolean parsePsw(String psw){
		return this.psw.equals(psw);
	}
	/**Setta la password dell'utente fornendo la vecchia (controllo di sicurezza) e la nuova
	 * 
	 * @param oldPsw vecchia password dell'utente
	 * @param newPsw nuova password con cui sostituire la vecchia
	 * @return true in caso di set avvenuto con successo, false altrimenti
	 * */
	public boolean setPsw(String oldPsw,String newPsw){
		//controllo della vecchia password
		if(psw.equals(oldPsw)){
			//set della nuova
			psw=newPsw;
			return true;
		}
		return false;
	}
	/**Fornisce una rappresentazione testuale dell'utente (override del toString della classe Object)
	 * 
	 * @return stringa contenente i dati dell'utente separati da �
	 * */
	public String toString(){
		return id+"�"+psw+"�"+nome+"�"+cognome;
	}
	/**Recupera il tipo di utente (il metodo � override dalle classi Studente, Docente, Admin)
	 * 
	 * @return valore primitivo intero che rappresenta la tipologia dell'utente
	 * */
	public int getType(){
		return 4;
	}

}
