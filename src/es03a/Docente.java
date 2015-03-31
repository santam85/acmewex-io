package es03a;

/**Classe estensione di User che rappresenta un utente di tipo Docente
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class Docente extends User {
	private static final long serialVersionUID = 5L;
	/**Costruisce un docente passandogli nome, cognome e password
	 * 
	 * @param nome nome dello docente
	 * @param cognome cognome dello docente
	 * @param psw password dello docente
	 * */
	public Docente(String nome,String cognome,String psw){
		//costruttore della classe padre
		super(nome,cognome,psw);
		//assegnamento dell'id
		reAssignId();		
	}
	/**Assegna all'id la stringa del nome minuscola seguita dal cognome minuscolo separate da "."*/
	public void reAssignId(){
		id=this.getNome().toLowerCase()+"."+this.getCognome().toLowerCase();
	}
	/**Recupera il tipo di utente
	 * 
	 * @return valore intero che indica il tipo di utente (0:studente, 1:docente, 2:admin)
	 * */
	public int getType(){
		return 1;
	}
}
