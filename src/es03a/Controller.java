package es03a;

import java.util.*;
import java.io.*;


/**
 * Main class che contiene le funzionalità principali dell'applicazione
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class Controller {
	//	campo statico privato per tener traccia degli appelli
	private static ListaAppelli sessions = new ListaAppelli();
	//campo statico privato di classe UsersDb che contiene gli utenti registrati
	private static UsersDb usersDb = new UsersDb();
	/**campo statico di tipo IUser che rappresenta l'utente loggato*/
	public static IUser userLogged;
	//campo privato statico String  che contiene la password dell'utente loggato
	private static String userLoggedPsw;
	
	/**Permette di ottenere un oggetto Appello fornendo la posizione della sua chiave in appelliKeySet
	 * 
	 * @param pos posizione della chiave dell'appello in appelliKeySet
	 * @return appello cercato altrimenti null se non esiste
	 * */
	public static Appello getAppello(int pos) {
		/*Si utilizza la get delle HashMap*/
		return sessions.map.get(sessions.keySet[pos]);
	}
	/**Restituisce il numero degli appelli presenti
	 * 
	 * @return valore intero pari al numero degli appelli
	 * */
	public static int getNumAppelli() {
		/*si restituisce la lunghezza dell'array appelliKeySet */
		return sessions.keySet.length;
	}
	/**Salva i dati dell'applicazione
	 * 
	 *  @return true in caso di salvataggio riuscito, false altrimenti
	 *  */
	public static boolean saveAll() {
		boolean result;
		//Salvataggio database utenti
		try{
			FileOutputStream fos = new FileOutputStream("Save/users.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(usersDb); 
			oos.flush();
			oos.close();
			result=true;
		}catch(Exception ex){
			result=false;
		}
		//Salvataggio database appelli
		try{
			FileOutputStream fos = new FileOutputStream("Save/appelli.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(sessions); 
			oos.flush();
			oos.close();
			result=true;
		}catch(Exception ex){
			result=false;
		}
		System.out.println("Saved: "+result);
		return result;
	}
	/**Carica i dati dell'applicazione
	 * 
	 *  @return true in caso di caricamento riuscito, false altrimenti
	 *  */
	public static boolean restore() {
		boolean result;
		//Caricamento database utenti
		try{
			FileInputStream fis = new FileInputStream("Save/users.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			usersDb = (UsersDb) ois.readObject();
			ois.close();
			result=true;
		}catch(Exception ex){
			System.out.println("Restored1: "+ex);
			result=false;
		}
		//Caricamento database appelli
		try{
			FileInputStream fis = new FileInputStream("Save/appelli.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			sessions = (ListaAppelli) ois.readObject();
			ois.close();
			result=true;
		}catch(Exception ex){
			System.out.println("Restored2: "+ex);
			result=false;
		}
		System.out.println("Restored: "+result);
		return result;
	}
	/**Controlla la procedura di login 
	 * 
	 * @param id id inserita dall'utente
	 * @param psw password inserita dall'utente
	 * @return true in caso di login effettuato con successo, false altrimenti
	 * */
	public static boolean logInCheck(String id, String psw) {
		//controllo dell'esistenza dell'utente
		if (usersDb.containsId(id)){
			//controllo che la password inserita e quella dell'utente coincidano
			if (usersDb.getUser(id).parsePsw(psw)) {
				//copia dell'utente loggato dal database e assegnaemnto della psw a userLoggedPsw
				userLogged = usersDb.getUser(id);
				userLoggedPsw = psw;
				return true;
			}
		}
		return false;
	}
	/**Restituisce il nome di un utente in base all'id
	 * 
	 * @param id stringa che rappresenta l'id dell'utente
	 * @return Stringa formata da nome e cognome dell'utente cercato
	 * */
	public static String getUserName(String id){
		return usersDb.getUser(id).getNome()+" "+usersDb.getUser(id).getCognome();
	}
	/**Registra un nuovo Docente o Admin
	 * 
	 *  @param tipo valore intero che identifica il tipo di utente: 1=Docente, 2=Admin
	 *  @param nome nome dell'utente da registrare
	 *  @param cognome cognome dell'utente da registrare
	 *  @param psw password dell'utente da registrare
	 *  @return true in caso di registrazione riuscita, false altrimenti
	 *  */
	public static boolean registerUser(int tipo, String nome, String cognome,String psw) {
		//selezione del giusto tipo di utente da registrare
		switch (tipo) {
		case 1:
			//inserimento del nuovo Docente nel database
			return usersDb.insert(new Docente(nome, cognome, psw));
		case 2:
			//inserimento del nuovo Admin nel database
			return usersDb.insert(new Admin(nome, cognome, psw));
		default:
			return false;
		}
	}
	/**Registra un nuovo Studente
	 * 
	 * @param matricola matricola identificativa dello studente
	 * @param nome nome dello studente da registrare
	 * @param cognome cognome dello studente da registrare
	 * @param psw password dello studente da registrare
	 * @return true in caso di registrazione riuscita, false altrimenti
	 * */
	public static boolean registerUser(String matricola, String nome, String cognome, String psw) {
		//inserimento del nuovo studente nel database
		return usersDb.insert(new Studente(nome, cognome, psw, matricola));
	}
	/**Modifica i dati di uno studente 
	 * 
	 * @param nome nuovo nome dello studente
	 * @param cognome nuovo cognome dello studente
	 * @param psw nuova password dello studente
	 * @param matricola nuova matricola dello studente
	 * @return true in caso di modifiche apportate, false altrimenti
	 * */
	public static boolean changeStudentData(String nome, String cognome, String psw, String matricola) {
		//controllo che i campi inseriti non siano vuoti
		if (nome.equals("") || cognome.equals("") || psw.equals("") || matricola.equals("")) {
			return false;
		}
		//recupero dello studente castando userLogged
		Studente tmp = (Studente) userLogged;
		//rimozione dal database del vecchio utente
		usersDb.remove(userLogged.getId());
		//settaggio dei nuovi campi su tmp e della password
		tmp.setMatricola(matricola);
		tmp.setNome(nome);
		tmp.setCognome(cognome);
		tmp.setPsw(userLoggedPsw, psw);
		userLoggedPsw = psw;
		//inserimento dell'utente modificato
		usersDb.insert(tmp);
		//aggiornamento dello userLogged e ritorno
		userLogged = tmp;
		return true;
	}
	/**Modifica i dati un Docente o Admin
	 * 
	 * @param nome nuovo nome dell'utente
	 * @param cognome nuovo cognome dell'utente
	 * @param psw nuova password dell'utente
	 * @param type intero che identifica il tipo di utente
	 * @return true in caso di modifiche apportate, false altrimenti
	 * */
	public static boolean changeUserData(String nome, String cognome, String psw,int type) {
		//controllo che i campi inseriti non siano vuoti
		if (nome.equals("") || cognome.equals("") || psw.equals("")) {
			return false;
		}
		//creazione del IUser temporaneo e cast in base al tipo di utente
		IUser tmp=null;
		if (userLogged.getType()==1){
			tmp = (Docente) userLogged;
		} else {
			tmp = (Admin) userLogged;
		}
		//rimozione dal database del vecchio utente
		usersDb.remove(userLogged.getId());
		//settaggio dei nuovi campi su tmp e della password
		tmp.setNome(nome);
		tmp.setCognome(cognome);
		tmp.setPsw(userLoggedPsw, psw);
		userLoggedPsw = psw;
		//inserimento dell'utente modificato
		usersDb.insert(tmp);
		//aggiornamento dello userLogged e ritorno
		userLogged = tmp;
		return true;
	}
	/**Apre un nuovo appello
	 * 
	 * @param nome nome dell'appello
	 * @param tipo tipologia di appello: orale,scritto,pratico
	 * @param luogo luogo dove si svolgerà l'appello
	 * @param inizio data di inizio dell'appello
	 * @param chiusura data chiusura iscrizioni
	 * @param maxIscritti numero massimo degli studenti ammessi
	 * @return true in caso di inserimento eseguito, false altrimenti
	 * */
	public static boolean openSession(String nome, String tipo, String luogo,ACMEDate inizio, ACMEDate chiusura, int maxIscritti) {
		/*blocco if che controlla i valori critici dei campi e viene superato solo se:
		 * 1) il numero massimo degli iscritti è diverso da 0
		 * 2) la data di inizio è successiva al giorno di chiusura e al giorno attuale
		 * 3) la data di chiusura iscrizioni deve essere successiva al giorno attuale
		 * 4) i campi nome e luogo non siano vuoti
		 * altrimenti il metodo ritorna false
		 * */
		if (maxIscritti==0||inizio.before(chiusura) || inizio.before(new ACMEDate()) || chiusura.before(new ACMEDate()) || nome.equals("") || luogo.equals("")) {
			return false;
		}
		//controllo della presenza di un altro appello con stessa data di inizio
		for(int i=0; i<getNumAppelli(); i++){
			if (getAppello(i).getInizio().toShortString().equals(inizio.toShortString())){
				return false;
			}
		}
		//controllo che non esista un appello con stesso nome,tipo e data di inzio
		if (!sessions.map.containsKey(nome + tipo + inizio.toShortString())){
			//inserimento dell'appello nella lista
			sessions.map.put(nome + tipo + inizio.toShortString(), new Appello(nome, tipo, luogo, inizio, chiusura, maxIscritti, userLogged.getId(), usersDb));
			//aggiornamento di sessions.keySet
			String[] tmp = new String[sessions.keySet.length + 1];
			for (int i = 0; i < tmp.length - 1; i++) {
				tmp[i] = sessions.keySet[i];
			}	
			tmp[tmp.length - 1] = nome + tipo + inizio.toShortString();
			sessions.keySet = tmp;
			//ordinamento di sessions.keySet
			ACMEUtil.sortId(sessions.keySet);
			return true;
		} else return false;
	}
	/**Modifica un appello esistente
	 * 
	 * @param newNome nuovo nome dell'appello
	 * @param newTipo nuova tipologia dell'appello
	 * @param newLuogo nuovo luogo dove si svolgerà l'appello
	 * @param newInizio nuova data di inizio dell'appello
	 * @param newChiusura nuova data chiusura iscrizioni
	 * @param newMaxIscritti nuovo numero massimo degli studenti ammessi, non deve essere inferiore al numero degli attuali iscritti
	 * @return true se la modifica ha successo, false altrimenti
	 *  */
	public static boolean editSession(int pos, String newNome, String newTipo, String newLuogo, ACMEDate newInizio, ACMEDate newChiusura, int newMaxIscritti) {
		//controllo presenza di un altro esame nella stessa data diverso da quello da modificare
		for(int i=0; i<getNumAppelli(); i++){
			if (getAppello(i).getInizio().toShortString().equals(newInizio.toShortString()) && !getAppello(i).getNome().equals(newNome) && !getAppello(i).getTipo().equals(newTipo)){
				return false;
			}
		}
		//ricostruzione del nome file
		String fileName=sessions.map.get(sessions.keySet[pos]).getNome()+sessions.map.get(sessions.keySet[pos]).getTipo()+sessions.map.get(sessions.keySet[pos]).getInizio().toShortString();
		File dir=new File("");
		//percorso della cartella di salvataggio
		String dirPath=dir.getAbsolutePath();
		//percorso completo del file
		String filePath=dirPath+File.separator+"Save"+File.separator+fileName;
		File old=new File(filePath);
		//cancellazione del file
		old.delete();
		//copia dell'appello prima della modifica
		Appello tmp = sessions.map.get(sessions.keySet[pos]);
		//rimozione dell'appello dalla lista
		sessions.map.remove(sessions.keySet[pos]);
		//controllo dell'assenza di un appello con i nuovi dati e controllo dell'assenza di campi vuoti
		if (!sessions.map.containsKey(newNome + newTipo + newInizio.toShortString()) && !newNome.equals("") && !newLuogo.equals("")){
			//settaggio dei nuovi dati
			tmp.setNome(newNome);
			tmp.setTipo(newTipo);
			tmp.setLuogo(newLuogo);
			tmp.setInizio(newInizio);
			tmp.setChiusura(newChiusura);
			//controllo che il numero massimo di iscritti sia maggiore al numero di studenti già iscritti all'appello
			if (newMaxIscritti>tmp.getIscritti().length){
				tmp.setMaxStudenti(newMaxIscritti);
			//altrimenti il nuovo numero di iscritti è settato al valore degli iscritti già presenti
			}else{
				tmp.setMaxStudenti(tmp.getIscritti().length);
			}
			//inserimento dell'appello modificato
			sessions.map.put(newNome + newTipo + newInizio.toShortString(), tmp);
			//reinserimento dell'appello in sessions.keySet
			sessions.keySet[pos] = newNome + newTipo + newInizio.toShortString();
			//ordinamento di sessions.keySet
			ACMEUtil.sortId(sessions.keySet);
			return true;
		}
		//inserimento dell'appello modificato
		sessions.map.put(tmp.getNome()+tmp.getTipo()+tmp.getInizio().toShortString(), tmp);
		sessions.keySet[pos] =tmp.getNome()+tmp.getTipo()+tmp.getInizio().toShortString();
		//ordinamento di sessions.keySet
		ACMEUtil.sortId(sessions.keySet);
		return false;
	}
	/**Rimuove un appello
	 * 
	 * @param pos posizione dell'appello in sessions.keySet
	 * */
	public static void removeSession(int pos) {
		//ricostruzione del nome file
		String fileName=sessions.map.get(sessions.keySet[pos]).getNome()+sessions.map.get(sessions.keySet[pos]).getTipo()+sessions.map.get(sessions.keySet[pos]).getInizio().toShortString();
		File dir=new File("");
		//percorso della cartella di salvataggio
		String dirPath=dir.getAbsolutePath();
		//percorso completo del file
		String filePath=dirPath+File.separator+"Save"+File.separator+fileName;
		File old=new File(filePath);
		//cancellazione del file
		old.delete();
		//rimozione dell'appello
		sessions.map.remove(sessions.keySet[pos]);
		//aggiornamento di sessions.keySet
		sessions.keySet = new String[sessions.map.size()];
		Set<String> tmp = sessions.map.keySet();
		Iterator<String> it = tmp.iterator();
		int i = 0;
		while (it.hasNext()) {
			sessions.keySet[i] = it.next();
			i++;
		}
		//ordinamento di sessions.keySet
		ACMEUtil.sortId(sessions.keySet);
	}
	/**Restituisce una array contente tutti gli appelli
	 * 
	 * @return array di tutti gli appelli
	 * */
	public static Appello[] getAppelli(){
		//creazione di un array di appello di lunghezza opportuna
		Appello[] lista=new Appello[sessions.map.size()];
		String[] tmp=new String[sessions.map.size()];
		//recupero delle chiavi e inserimento nell'array
		tmp=sessions.map.keySet().toArray(tmp);
		//ordinamento dell'array
		ACMEUtil.sortId(tmp);
		//recupero degli appelli
		for(int i=0;i<tmp.length;i++){
			lista[i]=sessions.map.get(tmp[i]);
		}
		return lista;
	}
	/**Lancia l'applicazione**/
	public static void main(String[] args) {
		//caricamento dei dati
		restore();
		//registrazione del listener
		View.setListener(new Listener());
		//inizializzazione del View
		View.initialize();
		//stampa del login
		View.paintLogIn();
	}
}
