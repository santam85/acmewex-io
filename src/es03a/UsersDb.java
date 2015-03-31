package es03a;
import java.util.*;



/**Questa classe rappresenta un database di utenti
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class UsersDb implements java.io.Serializable{
	private static final long serialVersionUID = 6L;
	//campo privato dove vengono memorizzati gli oggetti IUser (Studente,Docente e Admin) 
	private HashMap<String,IUser> database;
	/**Costruisce un database vuoto*/
	public UsersDb(){
		database=new HashMap<String,IUser>();
	}
	/**Controlla la presenza nel database di un specifico id (e perciò di un utente)
	 * 
	 * @param id identificatore da cercare
	 * @return true nel caso sia presente, false altrimenti
	 * */
	public boolean containsId(String id){
		//si utilizza il containsKey delle HashMap
		return database.containsKey(id);
	}
	/**Inserisce un IUser nel database
	 * 
	 * @param user utente da inserire
	 * @return true se l'inserimento è effettuato, false se l'utente è già presente nel database
	 * */
	public boolean insert(IUser user){
		if(!database.containsKey(user.getId())){
			database.put(user.getId(),user);
			return true;
		}
		return false;
	}
	/**Rimuove dal datadase l'utente che corrisponde all'id fornitogli
	 * 
	 * @param id identificatore dell'utente da rimuovere
	 * @return true se la rimozione è effettuata, false se l'utente non è presente (e perciò non può essere rimosso)
	 * */
	public boolean remove(String id){
		if(!database.containsKey(id)){
			database.remove(id);
			return true;
		}
		return false;
	}
	/**Recupera lo IUser con identificatore id
	 * 
	 * @param id identificatore dell'utente richiesto
	 * @return il riferimento all'utente
	 * */
	public IUser getUser(String id){
		return database.get(id);
	}
}
