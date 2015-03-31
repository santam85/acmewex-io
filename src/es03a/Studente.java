package es03a;

/**Classe estensione di  User che rappresenta un utente di tipo Studente
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 * */
public class Studente extends User {
	private static final long serialVersionUID = 4L;
	//campo dove si mantiene la matricola (che per lo studente è anche l'id)
	private String matricola;
	/**Costruisce uno studente passandogli nome, cognome, password e matricola
	 * 
	 * @param nome nome dello studente
	 * @param cognome cognome dello studente
	 * @param psw password dello studente
	 * @param matricola matricola dello studente
	 * */
	public Studente(String nome,String cognome,String psw,String matricola){
		//costruttore della classe padre
		super(nome,cognome,psw);
		//assegnamento della matricola
		this.matricola=matricola;
		//reassegnamento dell'id
		reAssignId();		
	}
	/**Recupera la matricola dello studente
	 * 
	 * @return la stringa contenente la matricola
	 * */
	public String getMatricola(){
		return matricola;
	}
	/**Setta la matricola dello studente
	 * 
	 * @param matricola nuova matricola dello studente
	 * */
	public void setMatricola(String matricola){
		this.matricola=matricola;
		//reassegnamento dell'id
		reAssignId();
	}
	/**Assegna al campo id il valore della matricola*/
	public void reAssignId(){
		id=matricola;
	}
	/**Recupera il tipo di utente
	 * 
	 * @return valore intero che indica il tipo di utente (0:studente, 1:docente, 2:admin)
	 * */
	public int getType(){
		return 0;
	}
}
