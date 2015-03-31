package es03a;
import es03a.ACMEWexExceptions.*;

/**Classe realizzata per testare i principali metodi degli oggetti appello
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class TestAppello {
	/**metodo main che lancia il test*/
	public static void main(String[] args) {
		//creazione database utenti
		UsersDb utenti=new UsersDb();
		//inserimento studenti
		utenti.insert(new Studente("Marco","Santarelli","marco","0000217387"));
		utenti.insert(new Studente("Matteo","Desanti","sibilla","0000221419"));
		utenti.insert(new Studente("Giovanni","Alai","galai","0000221569"));
		//inserimento docenti
		utenti.insert(new Docente("Massimo","Cicognani","cico"));
		//creazione data di oggi
		ACMEDate data=new ACMEDate();
		//stampa data
		System.out.println(""+data.toShortString());
		//creazione appello analisi con data di chiusura superata
		Appello analisila=new Appello("AnalisiLA","Scritto","Via Venezia",new ACMEDate("2005,6,30"),new ACMEDate("2005,6,20"),3,"massimo.cicognani", utenti);
		//iscrizione e cattura dell'eccezione data
		try{
			System.out.println("iscrizione Marco "+analisila.iscrivi("0000217387"));
		}catch(MaxIscrittiException ex){
			System.out.println("Massimo iscritti raggiunto! "+ex);
		}catch(ExpiredDateException ex){
			System.out.println("Data Superata! "+ex);
		}
		//creazione appello analisi con data di chiusura superata
		Appello analisilb=new Appello("AnalisiLB","Orale","Via Venezia",new ACMEDate("2005,9,30"),new ACMEDate("2005,9,20"),0,"massimo.cicognani", utenti);
		//iscrizione e cattura dell'eccezione iscritti
		try{
			System.out.println("iscrizione Marco "+analisilb.iscrivi("0000217387"));
		}catch(MaxIscrittiException ex){
			System.out.println("Massimo iscritti raggiunto! "+ex);
		}catch(ExpiredDateException ex){
			System.out.println("Data Superata! "+ex);
		}
		//creazione appello geometria
		Appello geometria=new Appello("Geom","Scritto","Via Venezia",new ACMEDate("2005,9,30"),new ACMEDate("2005,9,29"),3,"massimo.cicognani", utenti);
		//iscrizione senza problemi
		try{
			System.out.println("iscrizione Marco "+geometria.iscrivi("0000217387"));
		}catch(MaxIscrittiException ex){
			System.out.println("Massimo iscritti raggiunto! "+ex);
		}catch(ExpiredDateException ex){
			System.out.println("Data Superata! "+ex);
		}
		try{
			System.out.println("iscrizione Giovanni "+geometria.iscrivi("0000221569"));
		}catch(MaxIscrittiException ex){
			System.out.println("Massimo iscritti raggiunto! "+ex);
		}catch(ExpiredDateException ex){
			System.out.println("Data Superata! "+ex);
		}
		//stampa iscritti
		System.out.println("Iscritti all'appello di geom.");
		for(int i=0;i<geometria.getIscritti().length;i++){
			System.out.println(i+1+": "+geometria.getIscritti()[i]);
		}
		//assegnamento voto a Giovanni
		System.out.println("Assegnamento voto a Giovanni");
		//in ordine di matricola è il secondo perciò pos=1
		geometria.setVoto(1,30);
		//rimozione Marco dagli iscritti
		System.out.println("Esito rimozione di Marco "+geometria.rimuovi("0000217387"));
		//stampa iscritti
		System.out.println("Iscritti, dopo la rimozione:");
		for(int i=0;i<geometria.getIscritti().length;i++){
			System.out.println(i+1+": "+geometria.getIscritti()[i]);
		}
		//rimozione Giovanni
		System.out.println("Esito rimozione di Giovanni "+geometria.rimuovi("0000221569"));
		//stampa iscritti
		System.out.println("Iscritti ad elenco vuoto:");
		for(int i=0;i<geometria.getIscritti().length;i++){
			System.out.println(i+1+": "+geometria.getIscritti()[i]);
		}
	}
}
