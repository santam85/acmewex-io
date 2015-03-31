package es03a;

import java.util.*;

import es03a.ACMEWexExceptions.*;
/**Questa classe rappresenta un appello di un esame universitario
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 * */
public class Appello implements java.io.Serializable {
	private static final long serialVersionUID = 2L;
	//campo privato che contiene le chiavi degli iscritti (String) e vi associa il voto ottenuto (Integer)
	private HashMap<String,Integer> votiIscritti;
	//campi privati Stringhe che contengono nome, tipo, luogo dell'appello e id del docente che l'ha creato
	private String nome, tipo, luogo, idDocente;
	//campi privati di tipo ACMEDate contenenti le date di inizio esame e chiusura iscrizioni
	private ACMEDate inizio, chiusuraIscrizioni;
	//campo privato di tipo primitivo intero contenente il numero massimo degli iscritti
	private int maxIscritti;
	//campo privato che contiene il database degli utenti
	private UsersDb databaseUtenti;
	/**Costruisce l'appello con le caratteristiche passategli
	 * 
	 * @param nome nome dell'appello
	 * @param tipo tipologia dell'appello: orale,pratico,scritto
	 * @param luogo luogo in cui si terrà l'appello
	 * @param inizio data dell'inizio dell'esame
	 * @param chiusura data di chiusura delle iscrizioni
	 * @param maxStudenti numero massimo di iscritti acettati
	 * @param idDocente identificatore del docente che crea l'appello
	 * @param databaseUtenti oggetto di tipo UsersDb che contiene tutti gli utenti
	 * */	
	public Appello(String nome,String tipo, String luogo, ACMEDate inizio, ACMEDate chiusura, int maxStudenti, String idDocente, UsersDb databaseUtenti){
		//assegnamento dei valori passati al costruttore ai campi privati
		this.nome=nome;
		this.tipo=tipo;
		this.luogo=luogo;
		this.inizio=inizio;
		chiusuraIscrizioni=chiusura;
		this.maxIscritti=maxStudenti;
		this.idDocente=idDocente;
		this.databaseUtenti=databaseUtenti;
		//creazione della HahMap al momento vuota (non ci sono ne iscritti ne voti)
		votiIscritti=new HashMap<String,Integer>();
	}
	/**Controlla la presenza di uno studente tra gli iscritti all'appello
	 * 
	 * @param id identificatore dello studente da cercare
	 * @return true se lo studente è presente tra gli iscritti, false altrimenti
	 * */
	public boolean isIscritto(String id){
		//si utilizza il metodo containsKey delle HashMap
		return votiIscritti.containsKey(id);
	}
	/**Setta il nome dell'appello
	 * 
	 * @param nome nuovo nome da dare all'appello
	 * */
	public void setNome(String nome){
		this.nome=nome;
	}
	/**Recupera il nome dell'appello
	 * 
	 * @return una stringa contente il nome attuale dell'appello
	 * */
	public String getNome(){
		return nome;
	}
	/**Setta il tipo dell'appello
	 * 
	 * @param tipo nuovo tipologia dell'appello
	 * */
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	/**Recuperare il tipo dell'appello
	 * 
	 * @return una stringa contente la tipologia attuale dell'appello
	 * */
	public String getTipo(){
		return tipo;
	}
	/**Setta il luogo dell'appello
	 * 
	 * @param luogo nuovo luogo dove si terrà l'appello
	 * */
	public void setLuogo(String luogo){
		this.luogo=luogo;
	}
	/**Recupera il luogo dell'appello
	 * 
	 * @return una stringa contente il luogo attuale dove si terrà l'appello
	 * */
	public String getLuogo(){
		return luogo;
	}
	/**Setta la data di inizio dell'appello
	 * 
	 * @param inizio nuova data (ACMEDate) di inizio dell'appello
	 * */
	public void setInizio(ACMEDate inizio){
		this.inizio=inizio;
	}
	/**Recupera la data di inizio dell'appello
	 * 
	 * @return un oggetto ACMEDate rappresentante la data di inizio dell'appello
	 * */
	public ACMEDate getInizio(){
		return inizio;
	}
	/**Setta la data di chiusura iscrizioni dell'appello
	 * 
	 * @param chiusuraIscrizioni nuova data (ACMEDate) di chiusura iscrizioni dell'appello
	 * */
	public void setChiusura(ACMEDate chiusuraIscrizioni){
		this.chiusuraIscrizioni=chiusuraIscrizioni;		
	}
	/**Recupera la data di chiusura iscrizioni dell'appello
	 * 
	 * @return un oggetto ACMEDate rappresentante la data di chiusura iscrizioni dell'appello
	 * */
	public ACMEDate getChiusura(){
		return chiusuraIscrizioni;
	}
	/**Setta il numero massimo degli iscritti all'appello
	 * 
	 * @param maxStudenti intero positivo che rappresenta il nuovo numero massimo di studenti iscritti
	 * */
	public void setMaxStudenti(int maxStudenti){
		this.maxIscritti=maxStudenti;
	}
	/**Recupera il numero massimo degli iscritti dell'appello
	 * 
	 * @return valore intero primitivo dei massimi studenti ammessi a iscriversi
	 * */
	public int getMaxStudenti(){
		return maxIscritti;
	}
	/**Iscrive uno studente all'appello
	 * 
	 * @param id identificatore dello studente nel database utenti
	 * @exception MaxIscrittiException eccezione generata se si è raggiunto il numero massimo di iscritti
	 * @exception ExpiredDateException eccezione generata nel caso l'iscrizione venga eseguita a data chiusura iscrizioni superata
	 * @return true in caso di iscrizione riuscita, false altrimenti
	 * */
	public boolean iscrivi(String id) throws MaxIscrittiException,ExpiredDateException{
		//controllo del raggiungimento del limite iscritti
		if (votiIscritti.size()==maxIscritti){
			throw new MaxIscrittiException();
		}
		//controllo del superameto della data di chiusura
		if(new ACMEDate().after(chiusuraIscrizioni)){
			throw new ExpiredDateException();
		}
		//controllo dell'assenza del'utente tra gli iscritti all'appello
		if(!votiIscritti.containsKey(id)){
			//inserimento dell'utente con voto pari a -1 (esame non sostenuto)
			votiIscritti.put(id,-1);
			return true;
		}
		return false;
	}
	/**Rimuove un iscritto dall'appello
	 * 
	 * @param id identificatore dello studente nel database utenti
	 * @return true in caso di rimozione riuscita, false altrimenti
	 * */
	public boolean rimuovi(String id){
		try{
			//rimozione dalla HashMap
			votiIscritti.remove(id);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	/**Recupera l'id del docente che ha aperto l'appello
	 * 
	 * @return stringa dell'id del docente
	 * */
	public String getDocente(){
		return idDocente;
	}
	/**Recupera il voto di uno studente passando l'id
	 * 
	 * @param id identificatore dello studente nel database utenti
	 * @return valore primitivo intero rappresentante il voto conseguito (-1 in caso di esame non sostenuto)
	 * */
	public int getVoto(String id){
		return votiIscritti.get(id).intValue();
	}
	/**Setta il voto di uno studente fornendo la sua posizione nella lista iscritti e il voto
	 * 
	 * @param pos posizione dello studente nella lista degli iscritti
	 * @param voto voto conseguito nel range -1 --> 31
	 * */
	public void setVoto(int pos,int voto) {
		//generazione della lista iscritti
		String[] tmp=this.getIscritti();
		if(pos<0 || pos >= tmp.length)
			return;
		
		//recupero dell'id dello studente nella posizione indicata da pos
		StringTokenizer tok=new StringTokenizer(tmp[pos]," ");
		String id=tok.nextToken();
		//inserimento del voto in votiIscritti
		votiIscritti.put(id,voto);
	}
	/**Restituisce l'elenco degli iscritti all'appello
	 * 
	 * @return array di stringhe contenente per ogni posizione id, nome e cognome dell'iscritto in ordine secondo l'id (matricola)
	 * */
	public String[] getIscritti(){
		//creazione di un array della grandezza di votiIscritti
		String[] tmp=new String[votiIscritti.size()];
		//creazione del set degli id e dell'iteratore per il set
		Set<String> ids=votiIscritti.keySet();
		Iterator<String> it=ids.iterator();
		int i=0;
		while(it.hasNext()){
			String id=it.next();
			//generazione della stringa con id, nome e cognome
			String tmp1=id+" "+databaseUtenti.getUser(id).getNome()+" "+databaseUtenti.getUser(id).getCognome();
			//creazione di una stringa vuota di lunghezza opportuna per ottenere un incolonnamento in caso di visualizzazione a video
			String gap="";
			for(int j=0;j<45-tmp1.length();j++){
				gap+=" ";
			}
			//unione delle due stringe di cui sopra e inserimento del voto (E.n.s. in caso di voto -1,30 e Lode in caso di 31)
			tmp[i]=tmp1+gap+((votiIscritti.get(id)==-1)?"E.n.s.":(votiIscritti.get(id)==31)?"30 e Lode":votiIscritti.get(id));
			i++;
		}
		//ordinamento dell'array e ritorno dello stesso
		ACMEUtil.sortId(tmp);
		return tmp;
	}
	/**Controlla che l'esame abbia inizio in un intervallo tra due date
	 * 
	 * @param data1 prima data del periodo da controllare (antecedente alla seconda)
	 * @param data2 seconda data del periodo da controllare (successiva alla prima)
	 * @return true in caso la data sia tra quelle specificate o al più pari a data2, false altrimenti (anche in caso inizio=data1)
	 * */
	public boolean isBetween(ACMEDate data1,ACMEDate data2){
		return data1.before(this.inizio)&& data2.after(this.inizio);
	}
	/**Controlla che l'appello sia finito(superato cronologicamente)
	 * 
	 * @return true nel casp la data di inizio sia passata, false altrimenti
	 * */
	public boolean isEnded(){
		return new ACMEDate().after(this.inizio);
	}
	/**Dà una rappresentazione testuale dell'appello
	 * 
	 * @return stringa contenente i dati principali dell'appello
	 * */
	public String toString(){
		return nome+"-"+tipo+", Data: "+inizio.toShortString()+", Chiusura iscrizioni: "+chiusuraIscrizioni.toShortString() ;
	}
	/**Calcola la media dei risultati dell'appello
	 * 
	 * @return valore primitivo float della media di tutti i voti conseguiti all'esame
	 * */
	public float getMedia(){
		//creazione del set degli iscritti e iteratore
		Set<String> ids=votiIscritti.keySet();
		Iterator<String> it=ids.iterator();
		float somma=0;
		int studenti=0;
		while(it.hasNext()){
			//recupero del voto
			int voto=votiIscritti.get(it.next());
			//controllo che l'esame sia stato sostenuto
			if (voto!=-1){
				//aggiornamento di somma e del numero totale degli studenti che hanno sostenuto l'appello
				somma+=voto;
				studenti++;
			}
		}
		//return di 0 nel caso nessuno abbia sostenuto l'esame altrimenti return del rapporto di somma e studenti
		return (studenti==0)?0:somma/studenti;
	}
}
