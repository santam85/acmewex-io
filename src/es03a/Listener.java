package es03a;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

import es03a.ACMEWexExceptions.*;

/**A questa classe vengono notificati gli eventi generati sul View e a seconda di questi si invocano metodi del controller e si gestiscono dati e grafica
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class Listener implements WindowListener,ActionListener,ChangeListener,ListSelectionListener{
	/**Metodo che gestisce tutti gli eventi di tipo ActionEvent generati dal View
	 * 
	 * @param ev ActionEvent generato
	 * */
	public void actionPerformed(ActionEvent ev){
		/*Ad ognuno dei seguenti if corrisponde un possibile evnto generato da un oggetto del View
		Il riconoscimento dell'oggetto è fatto tramite comando getSource che restituisce il riferimento dell'oggeto in questione.
		Nel caso di componenti (bottoni in particolare) utilizzati in più punti si controlla anche la schermata a cui appartiene
		il componente tramite la stringa location.*/
		//sorgente dell'evento bottone registrazione
		if(ev.getSource()==View.registrazione){
			//stampa della schermata di registrazione
			View.paintRegisterStudente();
		}
		//sorgente dell'evento bottone log-in
		if(ev.getActionCommand()=="Log-In"){
			//recupero della password da JpasswordField
			char[] tmp=View.psw.getPassword();
			//ricostruzione della password come stringa
			String tmpPsw="";
			for(int i=0;i<tmp.length;i++){
				tmpPsw+=tmp[i];
			}
			//invocazione del metodo logInCheck passando il TextField id e la password ricostruita precedentemente
			boolean loggedIn=Controller.logInCheck(View.id.getText(),tmpPsw);
			//se il login ha successo
			if (loggedIn) {
				//stampa su console di eclipse dell'avvenuto log-in
				System.out.println("Log In Ok");
				//visualizzazione della schermata in base all'utente
				switch (Controller.userLogged.getType()) {
				//caso 0:menu studente
				case 0: View.paintMenuStudente();
						break;
                //caso 1:menu docente
				case 1: View.paintMenuDocente();
						break;
                //caso 2:menu admin
				case 2: View.paintMenuAdmin();
						break;
				}
			}else{
				//reset dei campi id e psw in caso di errato log-in
				View.id.setText("");
				View.psw.setText("");
				//messaggio di errore su console applicazione 
				View.console.setForeground(Color.red);
				View.console.setText("Errore nel log-in. Reinserisci id e password.");
			}
		}
		//sorgente dell'evento bottone logInMenu
		if(ev.getSource()==View.logInMenu){
			//visualizzazione della schermata di log-in
			View.paintLogIn();
		}
		//sorgente dell'evento bottone reset
		if(ev.getSource()==View.reset){
			//reset dei messaggi in console applicazione
			View.console.setText("");
			//recupero dei componenti del pannello
			Component[] tmp=View.panel.getComponents();
			//reset dei campi JTextField e JComboBox, abilitazione dei JButton, set a 0 del valore della JSlider
			for(int i=0;i<tmp.length;i++){
				if(tmp[i].getClass() == JTextField.class || tmp[i].getClass() == JPasswordField.class){
					((JTextField)tmp[i]).setText("");
				}
				if(tmp[i].getClass() == JComboBox.class && ((JComboBox<?>)tmp[i]).getItemCount()!=0){
					((JComboBox<?>)tmp[i]).setSelectedIndex(0);
				}
				if(tmp[i].getClass() == JButton.class){
					tmp[i].setEnabled(true);
				}
				if(tmp[i].getClass()== JSlider.class){
					((JSlider)tmp[i]).setValue(0);
				}
				//nelle schermate Modifica Appello, Apertura Appello e Iscrizione all'appello si resettano le date alla data attuale
				ACMEDate curr=new ACMEDate();
				if(View.location.equals("paintEditSession")||View.location.equals("paintOpenSession")||View.location.equals("paintIscriviAppello")){
					//set del item delle JComboBox rappresentanti le date
					View.inizioAaaa.setSelectedItem(curr.year);
					View.inizioMm.setSelectedIndex(curr.month-1);
					View.inizioGg.setSelectedIndex(curr.date-1);
					View.fineAaaa.setSelectedItem(curr.year);
					View.fineMm.setSelectedIndex(curr.month-1);
					View.fineGg.setSelectedIndex(curr.date-1);
				}
			}
		}
		//sorgente dell'evento bottone registra della schermata Registrazione studente
		if(ev.getSource()==View.registra && View.location.equals("paintRegisterStudente")){
			//controllo dell'equivalenza tra le due password con metodo statico equals degli Array
			if(Arrays.equals(View.psw.getPassword(),View.confPsw.getPassword())){
				boolean txtTyped=true;
				//creazione di una lista di JTextField
				LinkedList<JTextField> fields=new LinkedList<JTextField>();
				//aggiunta alla lista dei 4 JTextField
				fields.add(View.nome);
				fields.add(View.cognome);
				fields.add(View.psw);
				fields.add(View.matricola);
				//creazione dell'iteratore per la lista
				Iterator<JTextField> it=fields.iterator();
				while(it.hasNext()){
					//recupero dei contenuti dei TextField
					JTextField tmp=it.next();
					//controllo che il contenuto non sia vuoto
					txtTyped &= !tmp.getText().equals("");
				}
				if (txtTyped){
					//ricostruzione della password inserita
					char[] tmp=View.psw.getPassword();
					String tmpPsw="";
					for(int i=0;i<tmp.length;i++){
						tmpPsw+=tmp[i];
					}
					//registrazione Studente
					if(Controller.registerUser(View.matricola.getText(),View.nome.getText(),View.cognome.getText(),tmpPsw)){
						//messaggio in console di registrazione effettuata
						View.console.setForeground(Color.black);
						View.console.setText("Registrazione effettuata.");
					}else{
						//messaggio in console di registrazione fallita
						View.console.setForeground(Color.red);
						View.console.setText("Registrazione fallita.");
					}
					//disabilitazione del tasto registra per impedire più pressioni
					View.registra.setEnabled(false);
				}else{
					//messaggio di errore per mancanza di dati
					View.console.setForeground(Color.red);
					View.console.setText("Registrazione non effettuata. Riempi tutti i campi.");
				}
			} else {
				//messaggio di errore per non corrispondenza delle passwords
				View.console.setForeground(Color.red);
				View.console.setText("Registrazione non effettuata. Le password non corrispondono.");
			}
		}
		//sorgente dell'evento bottone modificaDatiPersonali
		if (ev.getSource()==View.modificaDatiPersonali){
			//visualizzazione del menu modifica dati personali
			View.paintModificaDati();
		}
		//sorgente dell'evento bottone accettaModificheUser
		if (ev.getSource()==View.accettaModificheUser){
			boolean changeResult=false;
			boolean pswConfirmed=true;
			//controllo dell'equivalenza delle due password inserite
			for(int i=0;i<View.psw.getPassword().length;i++){
				pswConfirmed &=View.psw.getPassword()[i]==View.confPsw.getPassword()[i];
			}
			if (pswConfirmed){
				//costruzione della stringa password
				char[] tmp=View.psw.getPassword();
				String tmpPsw="";
				for(int i=0;i<tmp.length;i++){
					tmpPsw+=tmp[i];
				}
				//controllo del tipo di utente
				if (Controller.userLogged.getType()==0) {
					//invocazione metodo per modificare i dati di uno studente
					changeResult=Controller.changeStudentData(View.nome.getText(),View.cognome.getText(),tmpPsw,View.matricola.getText());
				} else {
					//invocazione metodo per modificare i dati di un docente o admin
					changeResult=Controller.changeUserData(View.nome.getText(),View.cognome.getText(),tmpPsw,Controller.userLogged.getType());
				}
				if (changeResult){
					//messaggio in console di modifica effettuata
					View.console.setForeground(Color.black);
					View.console.setText("Modifica effettuata.");
				} else {
					//messaggio in console di modifica fallita
					View.console.setForeground(Color.red);
					View.console.setText("Modifica non effettuata. Inserisci tutti i campi.");
				}
			} else {
				//messaggio di errore in seguito alla non corrispondenza dei campi passwords
				View.console.setForeground(Color.red);
				View.console.setText("Modifica non effettuata. I campi password non corrispondono.");
			}
		}
		//sorgente dell'evento bottone mainMenu
		if (ev.getSource()==View.mainMenu){
			//visualizzazione del menu principale dipendentemente dal tipo di utente loggato
			//per tipo 0 menu studente
			if (Controller.userLogged.getType()==0) {
				View.paintMenuStudente();  
			//per tipo 1 menu docente
			} else if (Controller.userLogged.getType()==1){
				View.paintMenuDocente();
			//altrimenti menu admin
			} else {
				View.paintMenuAdmin();
			}
		}
		//sorgente dell'evento bottone log-off
		if (ev.getSource()==View.logOff){
			//visualizzazione della schermata di log-in
			View.paintLogIn();
		}
		//sorgente dell'evento bottone uscita
		if (ev.getSource()==View.uscita){
			//stampa del messaggio Save in console e salvataggio dei dati
			System.out.println("Save");
			Controller.saveAll();
			//terminazione applicazione
			System.exit(0);
		}
		//sorgente dell'evento selezione di un elemento della JComboBox contenente gli appelli nella schermata per l'iscrizione agli appelli
		if(View.location.equals("paintIscriviAppello") && ev.getSource()==View.appelli){
			//cancellazione messaggio in console
			View.console.setText("");
			//controllo che vi siano elementi selezionati della lista (getSelectedIndex restituisce -1 in caso di nessun elemento)
			if (View.appelli.getSelectedIndex()!=-1||View.appelli.getItemCount()!=0){
				//scrittura nella Label del nome del docente dell'appello
				View.nomeDocente.setText("Docente: "+Controller.getUserName(Controller.getAppello(View.appelli.getSelectedIndex()).getDocente()));
				//alla selezione di un elemento si risale all'appello scelto in base alla posizione dentro l'array includedSessions e su questo si invoca isIscritto per controllare se è già presente tra gli iscritti
				if(!(View.appelli.getSelectedIndex()==-1) && View.includedSessions[View.appelli.getSelectedIndex()].isIscritto(Controller.userLogged.getId())){
					//se lo studente è già iscritto si disabilita il tasto iscriviti e si abilita rimuoviti
					View.iscriviti.setEnabled(false);
					View.rimuoviti.setEnabled(true);
				}else{
					//se lo studente non è iscritto si disabilita il tasto rimuoviti e si abilita iscriviti
					View.iscriviti.setEnabled(true);
					View.rimuoviti.setEnabled(false);
				}
			} else {
				//nome docente vuoto
				View.nomeDocente.setText("");
				View.iscriviti.setEnabled(false);
				View.rimuoviti.setEnabled(false);
			}
		}
		//sorgente dell'evento selezione nel JComboBox dell'anno o del mese
		//La parte di codice seguente si occupa del controllo sulla data tenendo conto anche degli anni bisestili
		if (ev.getSource()==View.inizioAaaa || ev.getSource()==View.inizioMm || ev.getSource()==View.fineAaaa || ev.getSource()==View.fineMm ){
			//conversione della stringa dell'anno in int
			int inizioA=(Integer.parseInt(""+View.inizioAaaa.getSelectedItem()));
			//recupero dei valori di mese e giorno
			//all'indice recuperato del mese si somma 1 per ottenere il numero giusto (tra 1 e 12 altrimenti sarebbe tra 0 e 11)
			int inizioM=View.inizioMm.getSelectedIndex()+1;
			//per il giorno non c'è la necessita di risalire alla data esatta
			int inizioG=View.inizioGg.getSelectedIndex();
			//rimozione dei giorni superiori a 28
			for(int i=View.inizioGg.getItemCount();i>28;i--){
				View.inizioGg.removeItem(""+i);
			}
			//selezione mese di febbraio
			if(inizioM==2){
				//controllo anno bisestile
				if (inizioA%4==0){
					//inserimento 29mo giorno
					View.inizioGg.addItem("29");
				}
			//selezione mesi da 30 giorni (aprile, giugno, settembre e novembre)	
			}else if ( inizioM==4 || inizioM==6 || inizioM==9 || inizioM==11){
				//inserimento 29mo e 30mo giorno
				for (int i=29; i<31; i++){
					View.inizioGg.addItem(""+i);
				}
			//selezione mesi da 31 giorni (i restanti)
			}else {
				//inserimento 29mo, 30mo e 31mo giorno
				for (int i=29; i<32; i++){
					View.inizioGg.addItem(""+i);
				}
			}
			//selezione del giorno, nel caso passando da una data all'altra il giorno non fosse più selezionabile si reimposta a 1
			//esempio selezionando 2005,gennaio,31 e poi scegliendo il mese febbraio il campo giorno torna a 1 (non esistendo il 31 giorno in febbraio
			View.inizioGg.setSelectedIndex((inizioG>View.inizioGg.getItemCount()-1)?0:inizioG);
			//si ripete lo stesso procedimento di cui sopra per la seconda data
			int fineA=(Integer.parseInt(""+View.fineAaaa.getSelectedItem()));
			int fineM=View.fineMm.getSelectedIndex()+1;
			int fineG=View.fineGg.getSelectedIndex();
			for(int i=View.fineGg.getItemCount();i>28;i--){
				View.fineGg.removeItem(""+i);
			}
			if(fineM==2){
				if (fineA%4==0){
					View.fineGg.addItem("29");
				}			
			}else if ( fineM==4 || fineM==6 || fineM==9 || fineM==11){
				for (int i=29; i<31; i++){
					View.fineGg.addItem(""+i);
				}
			}else {
				for (int i=29; i<32; i++){
					View.fineGg.addItem(""+i);
				}
			}
			View.fineGg.setSelectedIndex((fineG>View.fineGg.getItemCount())?1:fineG);		
		} 
		//sorgente dell'evento bottone iscrizioneAppello
		if (ev.getSource()==View.iscrizioneAppello){
			//visualizzazione menu iscrizione appello
			View.paintIscriviAppello();
			//controllo che vi siano elementi selezionati della lista (getSelectedIndex restituisce -1 in caso di nessun elemento)
			if (View.appelli.getSelectedIndex()!=-1||View.appelli.getItemCount()!=0){
				//scrittura nella Label del nome del docente dell'appello
				View.nomeDocente.setText("Docente: "+Controller.getUserName(Controller.getAppello(View.appelli.getSelectedIndex()).getDocente()));
				//alla selezione di un elemento si risale all'appello scelto in base alla posizione dentro l'array includedSessions e su questo si invoca isIscritto per controllare se è già presente tra gli iscritti
				if(!(View.appelli.getSelectedIndex()==-1) && View.includedSessions[View.appelli.getSelectedIndex()].isIscritto(Controller.userLogged.getId())){
					//se lo studente è già iscritto si disabilita il tasto iscriviti e si abilita rimuoviti
					View.iscriviti.setEnabled(false);
					View.rimuoviti.setEnabled(true);
				}else{
					//se lo studente non è iscritto si disabilita il tasto rimuoviti e si abilita iscriviti
					View.iscriviti.setEnabled(true);
					View.rimuoviti.setEnabled(false);
				}
			} else {
				//nome docente vuoto
				View.nomeDocente.setText("");
				View.iscriviti.setEnabled(false);
				View.rimuoviti.setEnabled(false);
			}
		}
		//sorgente dell'evento selezione di un RadioButton o cambiamento della data all'interno della schermata iscrizione all'appello
		if (View.location.equals("paintIscriviAppello") && (ev.getSource()==View.inizioA ||ev.getSource()==View.fineA || ev.getSource()==View.inizioB ||ev.getSource()==View.fineB ||ev.getSource()==View.inizioAaaa ||ev.getSource()==View.inizioMm||ev.getSource()==View.fineAaaa ||ev.getSource()==View.fineMm||ev.getSource()==View.fineGg ||ev.getSource()==View.inizioGg)){
			ACMEDate inizio;
			ACMEDate fine;
			//controllo della selezione nel primo gruppo di RadioButton
			if (View.inizioA.isSelected()){
				//abilitazione dei JComboBox della data di inizio ricerca
				View.inizioAaaa.setEnabled(true);
				View.inizioMm.setEnabled(true);
				View.inizioGg.setEnabled(true);
				//creazione della data di inizio ricerca
				inizio=new ACMEDate(View.inizioAaaa.getSelectedItem()+","+ (View.inizioMm.getSelectedIndex()+1) +","+View.inizioGg.getSelectedItem());
			} else {
				//disabilitazione dei JComboBox della data di inizio ricerca
				View.inizioAaaa.setEnabled(false);
				View.inizioMm.setEnabled(false);
				View.inizioGg.setEnabled(false);
				//creazione della data attuale per inizio ricerca
				inizio=new ACMEDate();
			}
			//controllo della selezione nel secondo gruppo di RadioButton
			if (View.fineA.isSelected()){
				//abilitazione dei JComboBox della data di fine ricerca
				View.fineAaaa.setEnabled(true);
				View.fineMm.setEnabled(true);
				View.fineGg.setEnabled(true);
				//creazione della data di fine ricerca
				fine=new ACMEDate(View.fineAaaa.getSelectedItem()+","+ (View.fineMm.getSelectedIndex()+1) +","+View.fineGg.getSelectedItem());
			} else {
				//disabilitazione dei JComboBox della data di fine ricerca
				View.fineAaaa.setEnabled(false);
				View.fineMm.setEnabled(false);
				View.fineGg.setEnabled(false);
				//creazione della data di fine ricerca (si è scelto come data lontana il 1 gennaio tra 5 anni)
				fine=new ACMEDate(new ACMEDate().year+5+",1,1");
			}
			//rimozione degli appelli dalla lista per preparasi al reinserimento (solo se non è già vuota)
			if(!(View.appelli.getComponentCount()<1)){
				View.appelli.removeAllItems();
			}
			//recupero di tutti gli appelli
			Appello[] tmp=Controller.getAppelli();
			int j=0;
			//creazione dell'array atto a contenere gli appelli ricercati tra le due date (grande al più quanto tutti gli appelli esistenti)
			View.includedSessions=new Appello[tmp.length];
			//scorrimento dell'elenco degli appelli
			for(int i=0;i<tmp.length;i++){
				//controllo se l'inizio dell'appello si trova tra le date specificate
				if (tmp[i].isBetween(inizio,fine)){
					//aggiunta dell'appello a includedSessions
					View.includedSessions[j]=tmp[i];
					//incremento del puntatore
					j++;
					//aggiunta dell'appello alla lista
					View.appelli.addItem(tmp[i].toString());
				}
			}
			//update del panel
			View.panel.updateUI();
		}
		//sorgente dell'evento bottone iscriviti
		if (ev.getSource()==View.iscriviti){
			try{
				//esecuzione dell'iscrizione in blocco try per via del possibile lancio di eccezioni
				if(View.includedSessions[View.appelli.getSelectedIndex()].iscrivi(Controller.userLogged.getId())){
					//messaggio di registrazione riuscita
					View.console.setForeground(Color.black);
					View.console.setText("Registrazione riuscita");
					//abilitazione del tasto rimuoviti e disabilitazione di iscriviti
					View.iscriviti.setEnabled(false);
					View.rimuoviti.setEnabled(true);
				}else{
					//messaggio di registrazione fallita
					View.console.setForeground(Color.red);
					View.console.setText("Registrazione Fallita");
				}
			//cattura di MaxIscrittiException stampa messaggio di errore
			}catch(MaxIscrittiException ex){
				View.console.setForeground(Color.red);
				View.console.setText("Registrazione Fallita, numero massimo iscritti raggiunto");
			//cattura di ExpiredDateException stampa messaggio di errore
			}catch(ExpiredDateException ex){
				View.console.setForeground(Color.red);
				View.console.setText("Registrazione Fallita, iscrizioni chiuse");
			}
		}
		//sorgente dell'evento bottone rimuoviti
		if (ev.getSource()==View.rimuoviti){
			//rimozione dall'appello
			View.includedSessions[View.appelli.getSelectedIndex()].rimuovi(Controller.userLogged.getId());
			//messaggio di rimozione riuscita
			View.console.setForeground(Color.black);
			View.console.setText("Rimozione riuscita");
			//abilitazione del tasto iscriviti e disabilitazione di rimuoviti
			View.iscriviti.setEnabled(true);
			View.rimuoviti.setEnabled(false);
		}
		//sorgente dell'evento bottone carriera
		if(ev.getSource()==View.carriera){
			//visualizzazione schermata carriera (elenco degli esami con voto)
			View.paintCarriera();
		}
		//sorgente dell'evento bottone risultatoAppello
		if (ev.getSource()==View.risultatoAppello){
			//visualizzazione schermata per ricerca risultati dello studente
			View.paintTrovaRisultati();
			View.risultato.setForeground(Color.black);
			//generazione della lista degli appelli esistente
			for(int i=0;i<Controller.getNumAppelli();i++){
				View.appelli.addItem(Controller.getAppello(i).toString());
			}
			//controllo della presenza dello studente tra gli iscritti all'esame
			if(View.appelli.getItemCount()==0){
				View.risultato.setText("");
				View.console.setForeground(Color.red);
				View.console.setText("Nessun appello presente");
			}else if(Controller.getAppello(View.appelli.getSelectedIndex()).isIscritto(Controller.userLogged.getId())){
				//recupero del voto conseguito dallo studente nell'esame selezionato
				int votoTmp=Controller.getAppello(View.appelli.getSelectedIndex()).getVoto(Controller.userLogged.getId());
				//stampa del voto, in caso di -1 si visualizza esame non sostenuto (E.n.s.), in caso di 31 si visualizza 30 e lode
				View.risultato.setText("Voto Conseguito: "+ ((votoTmp==-1)?"E.n.s." : (votoTmp==31)? "30 e Lode" : ""+votoTmp ));
			}else{
				//messaggio di assenza tra gli iscritti
				View.risultato.setForeground(Color.red);
				View.risultato.setText("Non iscritto all'appello");
			}
		}
		//sorgente dell'evento selezione di un elemento della JComboBox contenente gli appelli nella schermata di ricerca risultati dello studente
		if (ev.getSource()==View.appelli && View.location.equals("paintTrovaRisultati")){
			if(Controller.getNumAppelli()!=0){
				View.risultato.setForeground(Color.black);
				//controllo della presenza dello studente tra gli iscritti all'esame
				if(Controller.getAppello(View.appelli.getSelectedIndex()).isIscritto(Controller.userLogged.getId())){
					//recupero del voto conseguito dallo studente nell'esame selezionato
					int votoTmp=Controller.getAppello(View.appelli.getSelectedIndex()).getVoto(Controller.userLogged.getId());
					//stampa del voto, in caso di -1 si visualizza esame non sostenuto (E.n.s.), in caso di 31 si visualizza 30 e lode
					View.risultato.setText("Voto Conseguito: "+ ((votoTmp==-1)?"E.n.s." : (votoTmp==31)? "30 e Lode" : ""+votoTmp ));
				}else{
					//messaggio di assenza tra gli iscritti
					View.risultato.setForeground(Color.red);
					View.risultato.setText("Non iscritto all'appello");
				}
			}
		}
		//sorgente dell'evento bottone statisticheStudente
		if (ev.getSource()==View.statisticheStudente){
			//visualizzazione schermata statistiche stuedente (andamento voti)
			View.paintStatisticheStudente();
		}
		//sorgente dell'evento bottone inserisciDocenteAdmin
		if (ev.getSource()==View.inserisciDocenteAdmin){
			//visualizzazione schermata registrazione admin e docente
			View.paintRegisterAdmin();
		}
		//sorgente dell'evento bottone registra della schermata Registrazione dell'admin
		if(ev.getSource()==View.registra && View.location.equals("paintRegisterAdmin")){
			//controllo dell'equivalenza tra le due password con metodo statico equals degli Array
			if(Arrays.equals(View.psw.getPassword(),View.confPsw.getPassword())){
				boolean txtTyped=true;
				//creazione di una lista di JTextField
				LinkedList<JTextField> fields=new LinkedList<JTextField>();
				//aggiunta alla lista dei 3 JTextField
				fields.add(View.nome);
				fields.add(View.cognome);
				fields.add(View.psw);
				//creazione dell'iteratore per la lista
				Iterator<JTextField> it=fields.iterator();
				while(it.hasNext()){
					//recupero dei contenuti dei TextField
					JTextField tmp=it.next();
					//controllo che il contenuto non sia vuoto
					txtTyped &= !tmp.getText().equals("");
				}
				if (txtTyped){
					//ricostruzione della password inserita
					char[] tmp=View.psw.getPassword();
					String tmpPsw="";
					for(int i=0;i<tmp.length;i++){
						tmpPsw+=tmp[i];
					}
					//controllo del RadioButton selezionato
					if(View.prof.isSelected()){
						//registrazione Docente
						if(Controller.registerUser(1,View.nome.getText(),View.cognome.getText(),tmpPsw)){
							//messaggio di registrazione effettuata
							View.console.setForeground(Color.black);
							View.console.setText("Registrazione effettuata.");
						}else{
							//messaggio di registrazione fallita
							View.console.setForeground(Color.red);
							View.console.setText("Registrazione fallita.");
						}
						//disabilitazione del tasto registra
						View.registra.setEnabled(false);
					}else{
						//registrazione Admin
						if(Controller.registerUser(2,View.nome.getText(),View.cognome.getText(),tmpPsw)){
							//messaggio di registrazione effettuata
							View.console.setForeground(Color.black);
							View.console.setText("Registrazione effettuata.");
						}else{
							//messaggio di registrazione fallita
							View.console.setForeground(Color.red);
							View.console.setText("Registrazione fallita.");
						}
						//disabilitazione del tasto registra
						View.registra.setEnabled(false);
					}
				}else{
					//messaggio di errore per mancanza di dati
					View.console.setForeground(Color.red);
					View.console.setText("Registrazione non effettuata. Riempi tutti i campi.");
				}
			} else {
				//messaggio di errore per non corrispondenza delle passwords
				View.console.setForeground(Color.red);
				View.console.setText("Registrazione non effettuata. Le password non corrispondono.");
			}
		}
		//sorgente dell'evento bottone nuovoAppello
		if (ev.getSource()==View.nuovoAppello){
			//visualizzazione schermata per creazione appello
			View.paintOpenSession();
		}
		//sorgente dell'evento bottone inserisci della schermata Nuovo Appello
		if (ev.getSource()==View.inserisci && View.location.equals("paintOpenSession")){
			//creazione delle date recuperando gli item selezionati nei JComboBox
			ACMEDate inizio=new ACMEDate(View.inizioAaaa.getSelectedItem()+","+(View.inizioMm.getSelectedIndex()+1)+","+View.inizioGg.getSelectedItem()); 
			ACMEDate fine=new ACMEDate(View.fineAaaa.getSelectedItem()+","+(View.fineMm.getSelectedIndex()+1)+","+View.fineGg.getSelectedItem());
			//creazione appello
			boolean	sessionOpened=Controller.openSession(View.nome.getText(),""+View.tipo.getSelectedItem(),View.luogo.getText(),inizio,fine,View.maxIscritti.getValue());
			if(sessionOpened){
				//messaggio di creazione riuscita
				View.console.setForeground(Color.black);
				View.console.setText("Inserimento Appello Riuscito");
				//disabilitazione del tasto inserisci per impedire pressioni multiple
				View.inserisci.setEnabled(false);
			}else{
				//messaggio di inserimento fallito
				View.console.setForeground(Color.red);
				View.console.setText("Inserimento Appello Fallito, controllare i dati immessi o la presenza di un altro appello nella stessa data.");
			}
		}
		//sorgente dell'evento bottone modificaAppello
		if(ev.getSource()==View.modificaAppello){
			//visualizzazione della schermata di modifica
			View.paintEditSession();
			//reset testo della console
			View.console.setText("");
			//controllo che vi siano appelli selezionati
			if (!(View.appelli.getSelectedIndex()==-1)){
				//recupero e stampa a video del docente dell'appello selezionato
				View.nomeDocente.setText("Docente: "+Controller.getUserName(Controller.getAppello(View.appelli.getSelectedIndex()).getDocente()));
			} else {
				//nome docente vuoto
				View.nomeDocente.setText("");
			}
		}
		//sorgente dell'evento bottone accettaModificheAppello
		if(ev.getSource()==View.accettaModificheAppello){
			if(View.appelli.getSelectedIndex()!=-1){
				//controllo che il docente che intende modificare l'appello sia lo stesso che l'ha creato
				if ( Controller.getAppello(View.appelli.getSelectedIndex()).getDocente().equals(Controller.userLogged.getId())){
					//modifica dei dati della sessione di appello
					boolean edited=Controller.editSession(View.appelli.getSelectedIndex(),View.nome.getText(),""+View.tipo.getSelectedItem(),View.luogo.getText(),new ACMEDate(View.inizioAaaa.getSelectedItem()+","+(View.inizioMm.getSelectedIndex()+1)+","+View.inizioGg.getSelectedItem()),new ACMEDate(View.fineAaaa.getSelectedItem()+","+(View.fineMm.getSelectedIndex()+1)+","+View.fineGg.getSelectedItem()),View.maxIscritti.getValue());
					//disabilitazione del testo accetta modifiche
					View.accettaModificheAppello.setEnabled(false);
					//ristampa della schermata per visualizzare le mofiche
					View.paintEditSession();
					//controllo del risultato dell'operazione di modifica
					if(edited){
						//messaggio di modifica riuscita
						View.console.setForeground(Color.black);
						View.console.setText("Modifica effettuata.");
					}else{
						//messaggio di modifica fallita
						View.console.setForeground(Color.red);
						View.console.setText("Controllare i dati e riprovare.");
					}
				} else {
					//messaggio di impossibilità di modificare l'appello
					View.console.setForeground(Color.red);
					View.console.setText("non è possibile modificare appelli di altri docenti.");
				}
			}else{
				View.console.setForeground(Color.red);
				View.console.setText("Nessun appello scelto o database vuoto");
			}
		}
		//sorgente dell'evento selezione di un elemento della JComboBox contenente gli appelli nella schermata per la modifica degli appelli
		if(View.location.equals("paintEditSession") && ev.getSource()==View.appelli){
			//reset della console
			View.console.setText("");
			//controllo della presenza di appelli
			if(!(View.appelli.getItemCount()==0)){
				//copia del riferimento dell'appello da modificare
				View.modifyingSession=Controller.getAppello(View.appelli.getSelectedIndex());
				//settaggio dei campi di modifica con le caratteristiche attuali dell'appello
				View.maxIscritti.setValue(View.modifyingSession.getMaxStudenti());
				View.nome.setText(View.modifyingSession.getNome());
				View.tipo.setSelectedItem(View.modifyingSession.getTipo());
				View.luogo.setText(View.modifyingSession.getLuogo());
				View.inizioAaaa.setSelectedItem(""+(View.modifyingSession.getInizio().year));
				View.inizioMm.setSelectedIndex(View.modifyingSession.getInizio().month-1);
				View.inizioGg.setSelectedIndex(View.modifyingSession.getInizio().date-1);
				View.fineAaaa.setSelectedItem(""+(View.modifyingSession.getChiusura().year));
				View.fineMm.setSelectedIndex(View.modifyingSession.getChiusura().month-1);
				View.fineGg.setSelectedIndex(View.modifyingSession.getChiusura().date-1);
				View.nomeDocente.setText("Docente: "+Controller.getUserName(View.modifyingSession.getDocente()));
			}else {
				//altrimenti i campi si lasciano vuoti cosi come anche il nome docente
				View.nomeDocente.setText("");
			}
		}
		//sorgente dell'evento bottone rimuovi 
		if (ev.getSource()==View.rimuoviAppello) {
			if(View.appelli.getSelectedIndex()!=-1){
				//controllo che il docente che intende cancellare l'appello sia lo stesso che l'ha creato
				if ( Controller.getAppello(View.appelli.getSelectedIndex()).getDocente().equals(Controller.userLogged.getId())){
					//rimozione dell'appello
					Controller.removeSession(View.appelli.getSelectedIndex());
					//revisualizzazione della schermata (aggiornata)
					View.paintEditSession();
					//messaggio di rimozione riuscita
					View.console.setForeground(Color.blue);
					View.console.setText("Appello Rimosso");
				}else{
					//messaggio di impossibilità di cancellare l'appello
					View.console.setForeground(Color.red);
					View.console.setText("non è possibile cancellare appelli di altri docenti.");
				}
			}else{
				View.console.setForeground(Color.red);
				View.console.setText("Nessun appello scelto o database vuoto");
			}
		}
		//sorgente dell'evento bottone iscrittiAppello
		if(ev.getSource()==View.iscrittiAppello){
			//visualizzazione schermata di visualizzazione iscritti
			View.paintVisualizzaIscritti();
		}
		//sorgente dell'evento selezione di un elemento della JComboBox contenente gli appelli nella schermata di visualizzazione iscritti o dell'inserimento del voto
		if((View.location.equals("paintVisualizzaIscritti")||View.location.equals("paintInserisciVoto") )&& ev.getSource()==View.appelli){
			//reset del messaggio in console
			View.console.setText("");
			if(View.appelli.getSelectedIndex()!=-1){
				//recupero degli iscritti dell'appello selezionato
				String[] tmp=Controller.getAppello(View.appelli.getSelectedIndex()).getIscritti();
				//inserimento degli iscritti nella JList
				View.iscritti.setListData(tmp);
				//settaggio del font per la JList (avendo i caratteri dimensione fissa si ottiene un effetto di incolonnamento)
				View.iscritti.setFont(new Font("Courier New",Font.PLAIN,15));
				//nella schermata di visualizzazione iscritti
				if(View.location.equals("paintVisualizzaIscritti")){
					//inserimento testo nel JTextField per il salvataggio della lista
					View.nomeFile.setText("\"Nome file\".txt");
				}
				//update del pannello
				View.panel.updateUI();
			}
		}
		//sorgente dell'evento bottone stampaSuFile
		if (ev.getSource()==View.stampaSuFile){
			//recupero del nome da dare al file dal campo di testo
			String tmp=View.nomeFile.getText();
			String[] tmpList=Controller.getAppello(View.appelli.getSelectedIndex()).getIscritti();
			FileWriter fw = null;
			try{
				//salvataggio su file della lista iscritti
				fw = new FileWriter(tmp);
				for(int i=0;i<tmpList.length;i++){
					fw.write(tmpList[i]+"\n");
				}
				//messaggio di salvataggio riuscito
				View.console.setText("Salvataggio su File Riuscito.");
			}catch (Exception ex) {
				//messaggio di salvataggio fallito
				View.console.setText("Salvataggio su File Fallito.");
			}finally{
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//sorgente dell'evento bottone inserisciRisultati
		if (ev.getSource()==View.inserisciRisultati){
			//visualizzazione schermata inserimento voto del docente
			View.paintInserisciVoto();
		}
		//sorgente dell'evento bottone inserisci della schermata inserimento voto del docente
		if (ev.getSource()==View.inserisci && View.location.equals("paintInserisciVoto")){
			//recupero dell'indece dell'elemento selezionato nella JComboBox degli appelli
			int pos=View.appelli.getSelectedIndex();
			//controllo della conclusione dell'appello (data superata)
			if (!Controller.getAppello(pos).isEnded()){
				//messaggio di errore in console
				View.console.setText("Non  è possibile inserire voti a esami non ancora sostenuti.");
			} else {
				//recupero degli iscritti (posizioni) selezionati dalla lista
				int[] sel=View.iscritti.getSelectedIndices();
				for (int i=0; i<sel.length; i++){
					//set del voto degli alunni selezionati
					Controller.getAppello(pos).setVoto(sel[i],(View.voto.getSelectedItem().equals("E.n.s.")) ? -1 :(View.voto.getSelectedItem().equals("30 e lode")) ? 31 : Integer.parseInt((String)View.voto.getSelectedItem()));
				}
				//stampa degli iscritti (aggiornamento lista)
				String[] tmp=Controller.getAppello(View.appelli.getSelectedIndex()).getIscritti();
				View.iscritti.setListData(tmp);
				View.iscritti.setFont(new Font("Courier New",Font.PLAIN,15));
				//update del pannello
				View.panel.updateUI();
				//messaggio in console voto assegnato
				View.console.setText("Voto assegnato.");
			}
		}
		//sorgente dell'evento bottone statisticheAppello
		if (ev.getSource()==View.statisticheAppello){
			//visualizzazione statistiche di un esame
			View.paintStatisticheAppello();
			//gli appelli sono divisi nella HashMap per nome+tipo, ad ognuno è abbinata una seconda HashMap con identificatore la data dell'esame e oggetto la media dei voti
			View.appelliScelti=new HashMap<String,HashMap<String,Float>>();
			for(int i=0;i<Controller.getNumAppelli();i++){
				//controllo che l'esame sia del docente loggato
				if(Controller.getAppello(i).getDocente().equals(Controller.userLogged.getId())){
					//controllo che appelliscelti non contenga già il nome e il tipo di appello
					if(!View.appelliScelti.containsKey(Controller.getAppello(i).getNome()+" "+Controller.getAppello(i).getTipo())){
						//inserimento in appelliScelti di nome+tipo
						View.appelliScelti.put(Controller.getAppello(i).getNome()+" "+Controller.getAppello(i).getTipo(),new HashMap<String,Float>());
						//inserimento della data e del voto nella seconda HahMap
						View.appelliScelti.get(Controller.getAppello(i).getNome()+" "+Controller.getAppello(i).getTipo()).put(Controller.getAppello(i).getInizio().toShortString(),Controller.getAppello(i).getMedia());
						//inserimento esame (nome+tipo) nella JComboBox
						View.appelli.addItem(Controller.getAppello(i).getNome()+" "+Controller.getAppello(i).getTipo());
					}else{
						//inserimento della data e del voto nella seconda HahMap identificata da nome+tipo
						View.appelliScelti.get(Controller.getAppello(i).getNome()+" "+Controller.getAppello(i).getTipo()).put(Controller.getAppello(i).getInizio().toShortString(),Controller.getAppello(i).getMedia());
					}
				}
			}
		}
		//sorgente dell'evento bottone visualizzaStats
		if (ev.getSource()==View.visualizzaStats){
			if(View.appelli.getSelectedIndex()!=-1){ 
				//recupero del nome dell'appello selezionato (viene invocato il metodo toString dell'appello)
				View.esameStat=""+View.appelli.getSelectedItem();
				//creazione del nuovo frame per contenere il grafico delle statistiche
				JFrame stats=new JFrame("Statistiche "+View.esameStat);
				//posizionamento della finestra
				stats.setBounds(140,120,960,720);
				//inserimento del panel
				stats.getContentPane().add(new DocenteStats());
				//visibile
				stats.setVisible(true);
			}else{
				View.console.setForeground(Color.red);
				View.console.setText("Nessun appello selezionato, verificare la propria scelta e la presenza di appelli nel database");
			}
		}
	}
	/**Metodo che ascolta l'evento corrispondente alla chiusura del frame
	 * 
	 * @param e WindowEvent generato
	 * */
	public void windowClosing(WindowEvent e){
		//stampa del messaggio Save in console e salvataggio dei dati
		Controller.saveAll();
		//terminazione applicazione
		System.exit(0);
	}
	/**Metodo che ascolta il cambiamento di stato del componente JSlider
	 * 
	 * @param ev ChangeEvent generato
	 * */
	public void stateChanged(ChangeEvent ev) {
		//controllo della sorgente (JSlider del View)
		if(ev.getSource()==View.maxIscritti){
			//set della label recuperando il valore del JSlider
			View.sliderLabel.setText(""+View.maxIscritti.getValue());
		}
	}
	/**Metodo che ascolta il cambiamento di scelta del componente JList
	 * 
	 * @param arg0 ListSelectionEvent generato alla selezione di un elemnto di una JList
	 * */
	public void valueChanged(ListSelectionEvent arg0) {
		//controllo della presenza di elementi nella lista
		if(View.iscritti.getSelectedIndices().length==0){
			//disabilitazione del bottone inserisci nel caso non vi siano elementi nella lista
			View.inserisci.setEnabled(false);
		}else{
			//abilitazione del bottone inserisci nel caso siano presenti elementi nella lista
			View.inserisci.setEnabled(true);
		}
	}
	/**Metodo per l'ascolto di WindowEvent di tipo windowClosed. è implementato con corpo vuoto perché non utilizzato*/
	public void windowClosed(WindowEvent e){}
	/**Metodo per l'ascolto di WindowEvent di tipo windowOpened. è implementato con corpo vuoto perché non utilizzato*/
	public void windowOpened(WindowEvent e){}
	/**Metodo per l'ascolto di WindowEvent di tipo windowIconified. è implementato con corpo vuoto perché non utilizzato*/
	public void windowIconified(WindowEvent e){}
	/**Metodo per l'ascolto di WindowEvent di tipo windowDeiconified. è implementato con corpo vuoto perché non utilizzato*/
	public void windowDeiconified(WindowEvent e){}
	/**Metodo per l'ascolto di WindowEvent di tipo windowActivated. è implementato con corpo vuoto perché non utilizzato*/
	public void windowActivated(WindowEvent e){}
	/**Metodo per l'ascolto di WindowEvent di tipo windowDeactivated. è implementato con corpo vuoto perché non utilizzato*/
	public void windowDeactivated(WindowEvent e){}	
}
