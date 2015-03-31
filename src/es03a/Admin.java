package es03a;

/**Classe estensione di User che rappresenta un utente di tipo Admin
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class Admin extends User{
	private static final long serialVersionUID = 7L;
	/**Costruisce un admin passandogli nome, cognome e password
	 * 
	 * @param nome nome dello admin
	 * @param cognome cognome dello admin
	 * @param psw password dello admin
	 * */
	public Admin(String nome,String cognome,String psw){
		//costruttore della classe padre
		super(nome,cognome,psw);
		//assegnamento dell'id
		reAssignId();		
	}
	/**Assegna all'id la stringa "admin." seguita dal cognome minuscolo*/
	public void reAssignId(){
		id="admin."+this.getCognome().toLowerCase();
	}
	/**Recupera il tipo di utente
	 * 
	 * @return valore intero che indica il tipo di utente (0:studente, 1:docente, 2:admin)
	 * */
	public int getType(){
		return 2;
	}
}
