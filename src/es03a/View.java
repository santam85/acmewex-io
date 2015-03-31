package es03a;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

/**Classe attraverso la quale si stampano a video le schermate dell'applicazione
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 */
public class View {
	//campo che mantiene il riferimanto al listener registrato per questa classe
	static private Listener listener;
	/**campo utilizzato per le statistiche degli appelli. La prima HashMap contiene come chiavi nome e tipo dell'esame ed è associata
	 * alla seconda che contiene le date degli appelli con relativa media*/
	static public HashMap<String,HashMap<String,Float>> appelliScelti;
	/**stringa per memorizzare il nome dell'esame nelle statistiche del docente*/
	static public String esameStat;
	/**campo utilizato per riferire a particolari raggruppamenti di appelli*/
	static public Appello[] includedSessions=new Appello[]{};
	/**campo utilizzato durante la modifica di un appello*/
	static public Appello modifyingSession;
	/**frame su cui si stampano le schermate*/
	static public JFrame main=new JFrame("ACMEWex");
	/**stringa che indica la schermata attuale*/
	static public String location=""; 
	/**pannello della finestra*/
	static public JPanel panel=new JPanel();
	/**label utilizzata nelle schermate*/
	static public JLabel risultato, nomeDocente, grayLabel;
	/**label utilizzato per dare messaggi all'utente durante il funzionamento dell'applicazione*/
	static public JLabel console=new JLabel();
	/**label utilizzata nell'apertura e modifica degli appelli per riportare il numero massimo di iscritti selezionato con il JSlider*/
	static public JLabel sliderLabel=new JLabel();
	/**bottone utilizzato nelle schermate*/
	static public JButton logIn,registrazione,registra,reset,logInMenu,mainMenu,modificaDatiPersonali,accettaModificheUser,iscrizioneAppello,search,
	risultatoAppello,carriera,statisticheStudente,logOff,uscita,nuovoAppello,iscriviti,rimuoviti,inserisciDocenteAdmin,stampaSuFile,visualizzaStats,
	modificaAppello,accettaModificheAppello,rimuoviAppello,iscrittiAppello,inserisciRisultati,statisticheAppello,inserisci;
	/**campo di testo utilizzato per inserimento di dati*/
	static public JTextField id,nome,cognome,matricola,luogo,nomeFile;
	/**campo per l'inserimento della password*/
	static public JPasswordField psw,confPsw;
	/**radiobutton utilizzato nelle schermate*/
	static public JRadioButton studente,prof,admin,inizioA,fineA,inizioB,fineB;
	/**raggruppamento per radiobutton*/
	static public ButtonGroup radio,inizioGroup,fineGroup;
	/**campo per esporre informazioni utilizzato nelle schermate*/
	static public JComboBox<String> tipo,inizioAaaa,inizioMm,inizioGg,fineAaaa,fineMm,fineGg,appelli,voto;
	/**componente per l'inserimento del numero massimo di iscritti*/
	static public JSlider maxIscritti;
	/**campo che contiene una lista di stringhe*/
	static public JList<String> iscritti,appelliCarriera;
	/**pannello scrollabile (scorrevole)*/
	static public JScrollPane scrollPanel;
	
	/**registra il listener della classe
	 * 
	 * @param list listener da registrare
	 * */
	static public void setListener(Listener list){
		listener=list;
	}
	/**Inizializza la schermata definendone le caratteristiche principali (dimensione, posizione del frame, eventuale massimizzazione)*/
	static public void initialize(){
		//disposizione del frame
		main.setBounds(80,60,1024,768);
		//recupero della dimensione del dispositivo grafico (schermo)
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		//controllo che la risoluzione dello schermo sia 1024x768 pixel
		if(gs.getDisplayMode().getWidth()==1024){
			//massimizza la finestra in entrambe le dimensioni
			main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		//aggiunta del pannello
		main.getContentPane().add(panel);
		//registrazione del listener
		main.addWindowListener(listener);
		//colore di sfondo bianco
		panel.setBackground(Color.white);
		//frame visibile
		main.setVisible(true);
		//label realizzare una barra di stato
		grayLabel=new JLabel();
		//labe grigia opaca
		grayLabel.setOpaque(true);
		//set del colore della barra di stato
		grayLabel.setBackground(new Color(230,230,230));
	}
	/**Refresh della schermata che rimuove gli elementi e la prepara per essere modificata*/
	static public void refresh(){
		//rimozione componenti del pannello
		panel.removeAll();
		//aggiunta della console (per i messaggi della barra di stato)
		panel.add(console);
		//nessun layout impostato
		panel.setLayout(null);
		//inserimento label grigia
		panel.add(grayLabel);
		//disposizione e dimensionamento della console e della label grigia
		console.setBounds(10,panel.getHeight()-20,panel.getWidth(),20);
		grayLabel.setBounds(0,panel.getHeight()-20,panel.getWidth(),50);
		//update del pannello
		panel.updateUI();
	}
	/**Visualizza la schermata di log-in*/
	static public void paintLogIn(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintLogIn";
		//settaggio del colore del messaggio delle console e stampa messaggio di benvenuto
		console.setForeground(Color.black);
		console.setText(" Benvenuto in ACMEWex!!!!");
		//creazione campo id e password
		id=new JTextField();
		psw=new JPasswordField();
		//creazione label
		JLabel idLabel=new JLabel("Id");
		JLabel pswLabel=new JLabel("Password");
		//creazione pulsanti
		logIn=new JButton("Log-In");
		registrazione=new JButton("Registrazione");
		//aggiunta dei componenti al pannello
		panel.add(id);
		panel.add(psw);
		panel.add(idLabel);
		panel.add(pswLabel);
		panel.add(logIn);
		panel.add(registrazione);
		//disposizione e dimensionamento dei componenti
		id.setBounds(435,300,192,20);
		psw.setBounds(435,323,192,20);
		idLabel.setBounds(415,300,15,20);
		pswLabel.setBounds(350,323,80,20);
		registrazione.setBounds(435,355,120,20);
		logIn.setBounds(557,355,70,20);
		//setting dell'allinamento destro per i campi id e password
		idLabel.setHorizontalAlignment(JTextField.RIGHT);
		pswLabel.setHorizontalAlignment(JTextField.RIGHT);
		//focus sull'id field
		id.requestFocus();
		//registrazione del listener
		registrazione.addActionListener(listener);
		logIn.addActionListener(listener);
	}
	/**Visualizza la schermata di registrazione per studente*/
	static public void paintRegisterStudente(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintRegisterStudente";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);
		console.setText("");
		//creazione campi per inserimento dati
		nome=new JTextField();
		cognome=new JTextField();
		psw=new JPasswordField();
		confPsw=new JPasswordField();
		matricola=new JTextField();
		//creazione label
		JLabel nomeLabel=new JLabel("Nome");
		JLabel cognomeLabel=new JLabel("Cognome");
		JLabel pswLabel=new JLabel("Password");
		JLabel confPswLabel=new JLabel("Conferma Password");
		JLabel matricolaLabel=new JLabel("Matricola");
		//creazione pulsanti
		registra=new JButton("Registra");
		reset=new JButton("Reset");
		logInMenu=new JButton("Indietro");
		//aggiunta dei componenti al pannello
		panel.add(nome);
		panel.add(cognome);
		panel.add(psw);
		panel.add(confPsw);
		panel.add(matricola);
		panel.add(nomeLabel);
		panel.add(cognomeLabel);
		panel.add(pswLabel);
		panel.add(confPswLabel);
		panel.add(matricolaLabel);
		panel.add(registra);
		panel.add(reset);
		panel.add(logInMenu);
		//disposizione e dimensionamento dei componenti
		nome.setBounds(350,270,244,20);
		cognome.setBounds(350,293,244,20);
		psw.setBounds(350,316,244,20);
		confPsw.setBounds(350,339,244,20);
		matricola.setBounds(350,362,244,20);
		nomeLabel.setBounds(596,270,200,20);
		cognomeLabel.setBounds(596,293,200,20);
		pswLabel.setBounds(596,316,200,20);
		confPswLabel.setBounds(596,339,200,20);
		matricolaLabel.setBounds(596,362,200,20);
		logInMenu.setBounds(350,390,85,20);
		reset.setBounds(437,390,70,20);
		registra.setBounds(509,390,85,20);
		//focus sul field nome
		nome.requestFocus();
		//registrazione del listener
		logInMenu.addActionListener(listener);
		reset.addActionListener(listener);
		registra.addActionListener(listener);
	}
	/**Visualizza il menu dello studente loggato*/
	static public void paintMenuStudente(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintMenuStudente";
		//reset colore e messaggio di benvenuto con nome dell'utente
		console.setForeground(Color.black);
		console.setText("Benvenuto "+Controller.userLogged.getNome()+" "+Controller.userLogged.getCognome());
		//creazione pulsanti
		modificaDatiPersonali=new JButton("Modifica Dati Personali");
		iscrizioneAppello=new JButton("Iscriviti/Rimuoviti");
		risultatoAppello=new JButton("Trova Risultato Appello");
		carriera=new JButton("Visualizza Appelli e Media");
		statisticheStudente=new JButton("Visualizza Statistiche");
		logOff=new JButton("Log-Off");
		uscita=new JButton("Uscita");
		//aggiunta dei componenti al pannello
		panel.add(modificaDatiPersonali);
		panel.add(iscrizioneAppello);
		panel.add(risultatoAppello);
		panel.add(carriera);
		panel.add(statisticheStudente);
		panel.add(logOff);
		panel.add(uscita);
		//disposizione e dimensionamento dei componenti
		modificaDatiPersonali.setBounds(412,200,200,20);
		iscrizioneAppello.setBounds(412,223,200,20);
		risultatoAppello.setBounds(412,246,200,20);
		carriera.setBounds(412,269,200,20);
		statisticheStudente.setBounds(412,292,200,20);
		logOff.setBounds(412,348,200,20);
		uscita.setBounds(412,371,200,20);
		//registrazione del listener
		modificaDatiPersonali.addActionListener(listener);
		iscrizioneAppello.addActionListener(listener);
		risultatoAppello.addActionListener(listener);
		carriera.addActionListener(listener);
		statisticheStudente.addActionListener(listener);
		logOff.addActionListener(listener);
		uscita.addActionListener(listener);
	}
	/**Visualizza il menu dell'admin loggato*/
	static public void paintMenuAdmin(){
		//refresh della finestra
		refresh();
		//inizializazione della location
		location="paintMenuAdmin";
		//reset colore e messaggio di benvenuto con nome dell'utente
		console.setForeground(Color.black);
		console.setText("Benvenuto "+Controller.userLogged.getNome()+" "+Controller.userLogged.getCognome());
		//creazione pulsanti
		modificaDatiPersonali=new JButton("Modifica Dati Personali");
		inserisciDocenteAdmin=new JButton("Inserisci Docente/Admin");
		logOff=new JButton("Log-Off");
		uscita=new JButton("Uscita");
		//aggiunta dei componenti al pannello
		panel.add(modificaDatiPersonali);
		panel.add(inserisciDocenteAdmin);
		panel.add(logOff);
		panel.add(uscita);
		//disposizione e dimensionamento dei componenti
		modificaDatiPersonali.setBounds(412,250,200,20);
		inserisciDocenteAdmin.setBounds(412,273,200,20);
		logOff.setBounds(412,348,200,20);
		uscita.setBounds(412,371,200,20);
		//registrazione del listener
		modificaDatiPersonali.addActionListener(listener);
		inserisciDocenteAdmin.addActionListener(listener);
		logOff.addActionListener(listener);
		uscita.addActionListener(listener);
	}
	/**Visualizza il menu del docente loggato*/
	static public void paintMenuDocente(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintMenuDocente";
		//reset colore e messaggio di benvenuto con nome dell'utente
		console.setForeground(Color.black);
		console.setText("Benvenuto "+Controller.userLogged.getNome()+" "+Controller.userLogged.getCognome());
		//creazione pulsanti
		modificaDatiPersonali=new JButton("Modifica Dati Personali");
		nuovoAppello=new JButton("Apri Nuovo Appello");
		modificaAppello=new JButton("Modifica / Rimuovi Appello");
		iscrittiAppello=new JButton("Visualizza Iscritti");
		inserisciRisultati=new JButton("Inserisci/Modifica Risultati");
		statisticheAppello=new JButton("Visualizza Statistiche");
		logOff=new JButton("Log-Off");
		uscita=new JButton("Uscita");
		//aggiunta dei componenti al pannello
		panel.add(modificaDatiPersonali);
		panel.add(nuovoAppello);
		panel.add(modificaAppello);
		panel.add(iscrittiAppello);
		panel.add(inserisciRisultati);
		panel.add(statisticheAppello);
		panel.add(logOff);
		panel.add(uscita);
		//disposizione e dimensionamento dei componenti
		modificaDatiPersonali.setBounds(412,200,200,20);
		nuovoAppello.setBounds(412,223,200,20);
		modificaAppello.setBounds(412,246,200,20);
		iscrittiAppello.setBounds(412,269,200,20);
		inserisciRisultati.setBounds(412,292,200,20);
		statisticheAppello.setBounds(412,315,200,20);
		logOff.setBounds(412,348,200,20);
		uscita.setBounds(412,371,200,20);
		//registrazione del listener
		modificaDatiPersonali.addActionListener(listener);
		nuovoAppello.addActionListener(listener);
		modificaAppello.addActionListener(listener);
		iscrittiAppello.addActionListener(listener);
		inserisciRisultati.addActionListener(listener);
		statisticheAppello.addActionListener(listener);
		logOff.addActionListener(listener);
		uscita.addActionListener(listener);
		
	}
	/**Visualizza la schermata per la registrzione di admin e docenti*/
	static public void paintRegisterAdmin(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintRegisterAdmin";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);
		console.setText("");
		//creazione campi di testo e per le password
		nome=new JTextField();
		cognome=new JTextField();
		psw=new JPasswordField();
		confPsw=new JPasswordField();
		//creazione label
		JLabel nomeLabel=new JLabel("Nome");
		JLabel cognomeLabel=new JLabel("Cognome");
		JLabel pswLabel=new JLabel("Password");
		JLabel confPswLabel=new JLabel("Conferma Password");
		//creazione radio button
		prof=new JRadioButton("Docente",true);
		admin=new JRadioButton("Admin");
		//creazione button group e inserimento dei due radio button
		radio=new ButtonGroup();
		radio.add(admin);
		radio.add(prof);
		//creazione pulsanti
		registra=new JButton("Registra");
		reset=new JButton("Reset");
		mainMenu=new JButton("Menu Principale");
		//aggiunta dei componenti al pannello
		panel.add(nome);
		panel.add(cognome);
		panel.add(psw);
		panel.add(confPsw);
		panel.add(nomeLabel);
		panel.add(cognomeLabel);
		panel.add(pswLabel);
		panel.add(confPswLabel);
		panel.add(prof);
		panel.add(admin);
		panel.add(registra);
		panel.add(reset);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		nome.setBounds(350,270,244,20);
		cognome.setBounds(350,293,244,20);
		psw.setBounds(350,316,244,20);
		confPsw.setBounds(350,339,244,20);
		nomeLabel.setBounds(596,270,200,20);
		cognomeLabel.setBounds(596,293,200,20);
		pswLabel.setBounds(596,316,200,20);
		confPswLabel.setBounds(596,339,200,20);
		prof.setBounds(350,235,100,20);
		admin.setBounds(450,235,100,20);
		mainMenu.setBounds(422,423,173,20);
		reset.setBounds(422,390,85,20);
		registra.setBounds(509,390,85,20);
		//focus sul campo nome
		nome.requestFocus();
		//registrazione del listener
		prof.addActionListener(listener);
		admin.addActionListener(listener);
		mainMenu.addActionListener(listener);
		reset.addActionListener(listener);
		registra.addActionListener(listener);
	}
	/**Visualizza la schermata di creazione appello*/
	static public void paintOpenSession(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintOpenSession";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);		
		console.setText("");
		//creazione campi di testo per nome e luogo
		nome=new JTextField();
		luogo=new JTextField();
		//creazione ComboBox per tipo di esame
		tipo=new JComboBox<String>();
		//inserimento di elementi al ComboBox
		tipo.addItem("Scritto");
		tipo.addItem("Orale");
		tipo.addItem("Pratico");
		//creazione ComboBox per data di inizio appello
		inizioAaaa=new JComboBox<String>();
		inizioMm=new JComboBox<String>();
		inizioGg=new JComboBox<String>();
		//creazione data attuale
		ACMEDate dataAttuale = new ACMEDate();
		//inserimento anni al ComboBox relativo
		inizioAaaa.addItem(""+(dataAttuale.year));
		inizioAaaa.addItem(""+(dataAttuale.year+1));
		inizioAaaa.addItem(""+(dataAttuale.year+2));
		inizioAaaa.addItem(""+(dataAttuale.year+3));
		//inserimento mesi al ComboBox relativo
		inizioMm.addItem("Gennaio");
		inizioMm.addItem("Febbraio");
		inizioMm.addItem("Marzo");
		inizioMm.addItem("Aprile");
		inizioMm.addItem("Maggio");
		inizioMm.addItem("Giugno");
		inizioMm.addItem("Luglio");
		inizioMm.addItem("Agosto");
		inizioMm.addItem("Settembre");
		inizioMm.addItem("Ottobre");
		inizioMm.addItem("Novembre");
		inizioMm.addItem("Dicembre");
		//inserimento giorni al ComboBox relativo
		for (int i=1; i<32; i++){
			inizioGg.addItem( (i<=9)?"0"+i:""+i );
		}
		//creazione ComboBox per data chiusura iscrizioni
		fineAaaa=new JComboBox<String>();
		fineMm=new JComboBox<String>();
		fineGg=new JComboBox<String>();
		//inserimento anni al ComboBox relativo
		fineAaaa.addItem(""+(dataAttuale.year));
		fineAaaa.addItem(""+(dataAttuale.year+1));
		fineAaaa.addItem(""+(dataAttuale.year+2));
		fineAaaa.addItem(""+(dataAttuale.year+3));
		//inserimento mesi al ComboBox relativo
		fineMm.addItem("Gennaio");
		fineMm.addItem("Febbraio");
		fineMm.addItem("Marzo");
		fineMm.addItem("Aprile");
		fineMm.addItem("Maggio");
		fineMm.addItem("Giugno");
		fineMm.addItem("Luglio");
		fineMm.addItem("Agosto");
		fineMm.addItem("Settembre");
		fineMm.addItem("Ottobre");
		fineMm.addItem("Novembre");
		fineMm.addItem("Dicembre");
		//inserimento giorni al ComboBox relativo
		for (int i=1; i<32; i++){
			fineGg.addItem( (i<=9)?"0"+i:""+i );
		}
		//selezione del giorno attuale
		inizioAaaa.setSelectedItem(dataAttuale.year);
		inizioMm.setSelectedIndex(dataAttuale.month-1);
		inizioGg.setSelectedIndex(dataAttuale.date-1);
		fineAaaa.setSelectedItem(dataAttuale.year);
		fineMm.setSelectedIndex(dataAttuale.month-1);
		fineGg.setSelectedIndex(dataAttuale.date-1);
		//creazione slider per inserimento numero iscritti
		maxIscritti=new JSlider();
		//settaggio del valore minimo e massimo
		maxIscritti.setMinimum(0);
		maxIscritti.setMaximum(300);
		//selezione valore iniziale
		maxIscritti.setValue(0);
		//impostazioni slider (aumento con click)
		maxIscritti.setSnapToTicks(false);
		maxIscritti.setMajorTickSpacing(10);
		maxIscritti.setMinorTickSpacing(1);
		//creazione label
		sliderLabel=new JLabel(""+0);
		JLabel nomeLabel=new JLabel("Nome");
		JLabel tipoLabel=new JLabel("Tipo");
		JLabel luogoLabel=new JLabel("Luogo");
		JLabel inizioLabel=new JLabel("Data Esame");
		JLabel fineLabel=new JLabel("Chiusura Iscrizioni");
		JLabel maxIscrittiLabel=new JLabel("Numero Massimo Iscritti");
		//creazione pulsanti
		inserisci=new JButton("Inserisci");
		reset=new JButton("Reset");
		mainMenu=new JButton("Menu Principale");
		//aggiunta dei componenti al pannello
		panel.add(nome);
		panel.add(tipo);
		panel.add(luogo);
		panel.add(inizioAaaa);
		panel.add(inizioMm);
		panel.add(inizioGg);
		panel.add(fineAaaa);
		panel.add(fineMm);
		panel.add(fineGg);
		panel.add(maxIscritti);
		panel.add(sliderLabel);
		panel.add(nomeLabel);
		panel.add(tipoLabel);
		panel.add(luogoLabel);
		panel.add(inizioLabel);
		panel.add(fineLabel);
		panel.add(maxIscrittiLabel);
		panel.add(inserisci);
		panel.add(reset);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		nome.setBounds(350,200,244,20);
		tipo.setBounds(350,223,244,20);
		luogo.setBounds(350,246,244,20);
		inizioAaaa.setBounds(350,269,70,20);
		inizioMm.setBounds(423,269,120,20);
		inizioGg.setBounds(546,269,48,20);
		fineAaaa.setBounds(350,292,70,20);
		fineMm.setBounds(423,292,120,20);
		fineGg.setBounds(546,292,48,20);
		nomeLabel.setBounds(596,200,200,20);
		tipoLabel.setBounds(596,223,200,20);
		luogoLabel.setBounds(596,246,200,20);
		inizioLabel.setBounds(596,269,200,20);
		fineLabel.setBounds(596,292,200,20);
		maxIscritti.setBounds(350,315,200,20);
		maxIscrittiLabel.setBounds(596,315,200,20);
		sliderLabel.setBounds(553,315,41,20);
		mainMenu.setBounds(437,371,158,20);
		reset.setBounds(437,338,70,20);
		inserisci.setBounds(509,338,85,20);
		//focus sul field nome
		nome.requestFocus();
		//registrazione del listener
		mainMenu.addActionListener(listener);
		reset.addActionListener(listener);
		inserisci.addActionListener(listener);
		inizioAaaa.addActionListener(listener);
		inizioMm.addActionListener(listener);
		fineAaaa.addActionListener(listener);
		fineMm.addActionListener(listener);
		maxIscritti.addChangeListener(listener);
	}
	/**Visualizza la schermata per la modifica dell'appello*/
	static public void paintEditSession(){
		//refresh della finestra
		refresh();
		//stampa del menu di creazione appello (poiché si assomigliano molto si risfruttano i componenti)
		paintOpenSession();
		//inizializzazione della location
		location="paintEditSession";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);	
		console.setText("");
		//creazione label
		nomeDocente=new JLabel();
		//creazione ComboBox per gli appelli
		appelli=new JComboBox<String>();
		//creazione pulsanti
		accettaModificheAppello=new JButton("Modifica");
		rimuoviAppello=new JButton("Rimuovi");
		//rimozione pulsante inserisci dal pannello
		panel.remove(inserisci);
		//aggiunta dei componenti al pannello
		panel.add(nomeDocente);
		panel.add(appelli);
		panel.add(accettaModificheAppello);
		panel.add(rimuoviAppello);
		//disposizione e dimensionamento dei componenti
		accettaModificheAppello.setBounds(509,338,85,20);
		nomeDocente.setBounds(350,177,158,20);
		rimuoviAppello.setBounds(437,361,158,20);
		mainMenu.setBounds(437,394,158,20);
		appelli.setBounds(350,154,400,20);
		//inserimentoa appelli nel ComboBox
		for(int i=0;i<Controller.getNumAppelli();i++){
			appelli.addItem(Controller.getAppello(i).toString());
		}
		//registrazione del listener
		appelli.addActionListener(listener);
		accettaModificheAppello.addActionListener(listener);
		rimuoviAppello.addActionListener(listener);
	}
	/**Visualizza la schermata per l'iscrizione a un appello*/
	static public void paintIscriviAppello(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintIscriviAppello";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);	
		console.setText("");
		//creazione label
		nomeDocente=new JLabel();
		JLabel inizioLabel=new JLabel("Da giorno:");
		JLabel fineLabel=new JLabel("A giorno:");
		//creazione ComboBox per elenco appelli
		appelli=new JComboBox<String>();
		//creazione pulsanti
		iscriviti=new JButton("Iscriviti");
		rimuoviti=new JButton("Rimuoviti");
		mainMenu=new JButton("Menu Principale");
		//inserimento degli appelli nel ComboBox
		includedSessions=new Appello[Controller.getNumAppelli()];
		for(int i=0;i<Controller.getNumAppelli();i++){
			includedSessions[i]=Controller.getAppello(i);
			appelli.addItem(includedSessions[i].toString());
		}
		//creazione radio button
		inizioA=new JRadioButton("");
		inizioB=new JRadioButton("Oggi",true);
		fineA=new JRadioButton("");
		fineB=new JRadioButton("Senza Limite",true);
		//creazione button group e inserimento dei rispettivi radio button
		inizioGroup=new ButtonGroup();
		fineGroup=new ButtonGroup();		
		inizioGroup.add(inizioA);
		inizioGroup.add(inizioB);
		fineGroup.add(fineA);
		fineGroup.add(fineB);
		//creazione data attuale
		ACMEDate dataAttuale = new ACMEDate();
		//creazione ComboBox per la data di inizio ricerca
		inizioAaaa=new JComboBox<String>();
		inizioMm=new JComboBox<String>();
		inizioGg=new JComboBox<String>();
		//inserimento anni
		inizioAaaa.addItem(""+(dataAttuale.year));
		inizioAaaa.addItem(""+(dataAttuale.year+1));
		inizioAaaa.addItem(""+(dataAttuale.year+2));
		inizioAaaa.addItem(""+(dataAttuale.year+3));
		//inserimento mesi
		inizioMm.addItem("Gennaio");
		inizioMm.addItem("Febbraio");
		inizioMm.addItem("Marzo");
		inizioMm.addItem("Aprile");
		inizioMm.addItem("Maggio");
		inizioMm.addItem("Giugno");
		inizioMm.addItem("Luglio");
		inizioMm.addItem("Agosto");
		inizioMm.addItem("Settembre");
		inizioMm.addItem("Ottobre");
		inizioMm.addItem("Novembre");
		inizioMm.addItem("Dicembre");
		//inserimento giorni
		for (int i=1; i<32; i++){
			inizioGg.addItem( (i<=9)?"0"+i:""+i );
		}
		//creazione ComboBox per la data di fine ricerca
		fineAaaa=new JComboBox<String>();
		fineMm=new JComboBox<String>();
		fineGg=new JComboBox<String>();
		//inserimento anni
		fineAaaa.addItem(""+(dataAttuale.year));
		fineAaaa.addItem(""+(dataAttuale.year+1));
		fineAaaa.addItem(""+(dataAttuale.year+2));
		fineAaaa.addItem(""+(dataAttuale.year+3));
		//inserimento mesi
		fineMm.addItem("Gennaio");
		fineMm.addItem("Febbraio");
		fineMm.addItem("Marzo");
		fineMm.addItem("Aprile");
		fineMm.addItem("Maggio");
		fineMm.addItem("Giugno");
		fineMm.addItem("Luglio");
		fineMm.addItem("Agosto");
		fineMm.addItem("Settembre");
		fineMm.addItem("Ottobre");
		fineMm.addItem("Novembre");
		fineMm.addItem("Dicembre");
		//inserimento giorni
		for (int i=1; i<32; i++){
			fineGg.addItem( (i<=9)?"0"+i:""+i );
		}
		//aggiunta dei componenti al pannello
		panel.add(nomeDocente);
		panel.add(inizioAaaa);
		panel.add(inizioMm);
		panel.add(inizioGg);
		panel.add(fineAaaa);
		panel.add(fineMm);
		panel.add(fineGg);
		panel.add(inizioLabel);
		panel.add(fineLabel);
		panel.add(inizioA);
		panel.add(fineA);
		panel.add(inizioB);
		panel.add(fineB);
		panel.add(iscriviti);
		panel.add(rimuoviti);
		panel.add(appelli);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		inizioAaaa.setBounds(343,210,70,20);
		inizioMm.setBounds(416,210,120,20);
		inizioGg.setBounds(539,210,48,20);
		fineAaaa.setBounds(343,263,70,20);
		fineMm.setBounds(416,263,120,20);
		fineGg.setBounds(539,263,48,20);
		inizioA.setBounds(320,210,20,20);
		fineA.setBounds(320,263,20,20);
		inizioB.setBounds(600,210,100,20);
		fineB.setBounds(600,263,100,20);
		nomeDocente.setBounds(442,323,250,20);
		inizioLabel.setBounds(343,187,70,20);
		fineLabel.setBounds(343,240,70,20);
		appelli.setBounds(320,300,400,20);
		iscriviti.setBounds(442,363,158,20);
		rimuoviti.setBounds(442,387,158,20);
		mainMenu.setBounds(442,420,158,20);
		//disabilitazione combo box della data
		inizioAaaa.setEnabled(false);
		inizioMm.setEnabled(false);
		inizioGg.setEnabled(false);
		fineAaaa.setEnabled(false);
		fineMm.setEnabled(false);
		fineGg.setEnabled(false);
		//registrazione del listener
		inizioA.addActionListener(listener);
		inizioB.addActionListener(listener);
		fineA.addActionListener(listener);
		fineB.addActionListener(listener);
		iscriviti.addActionListener(listener);
		mainMenu.addActionListener(listener);
		appelli.addActionListener(listener);
		rimuoviti.addActionListener(listener);
		inizioAaaa.addActionListener(listener);
		inizioMm.addActionListener(listener);
		fineAaaa.addActionListener(listener);
		fineMm.addActionListener(listener);
		inizioGg.addActionListener(listener);
		fineGg.addActionListener(listener);
	}
	/**Visualizza schermata per la modifica dei dati personali*/
	static public void paintModificaDati(){
		//refresh della finestra
		refresh();
		//inizializzazione della location
		location="paintModificaDati";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);	
		console.setText("");
		//creazione campi per inserimento dati
		nome=new JTextField(""+Controller.userLogged.getNome());
		cognome=new JTextField(""+Controller.userLogged.getCognome());
		psw=new JPasswordField();
		confPsw=new JPasswordField();
		//creazione pulsanti
		modificaDatiPersonali=new JButton("Ripristina");
		accettaModificheUser=new JButton("Accetta Modifiche");
		mainMenu=new JButton("Menu Principale");
		//creazione label
		JLabel nomeLabel=new JLabel("Nome");
		JLabel cognomeLabel=new JLabel("Cognome");
		JLabel pswLabel=new JLabel("Password");
		JLabel confPswLabel=new JLabel("Conferma Password");
		//aggiunta dei componenti al pannello
		panel.add(nome);
		panel.add(cognome);
		panel.add(psw);
		panel.add(confPsw);
		panel.add(modificaDatiPersonali);
		panel.add(nomeLabel);
		panel.add(cognomeLabel);
		panel.add(pswLabel);
		panel.add(confPswLabel);
		panel.add(accettaModificheUser);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		nome.setBounds(350,200,244,20);
		cognome.setBounds(350,223,244,20);
		psw.setBounds(350,246,244,20);
		confPsw.setBounds(350,269,244,20);
		nomeLabel.setBounds(596,200,200,20);
		cognomeLabel.setBounds(596,223,200,20);
		pswLabel.setBounds(596,246,200,20);
		confPswLabel.setBounds(596,269,200,20);
		modificaDatiPersonali.setBounds(420,317,174,20);
		accettaModificheUser.setBounds(420,340,174,20);
		mainMenu.setBounds(420,373,174,20);
		//recupero del tipo di utente
		int userType=Controller.userLogged.getType();
		//controllo che l'utente loggato sia di tipo studente
		if (userType==0){
			//creazione e inserimento del campo matricola
			matricola=new JTextField(""+Controller.userLogged.getId());
			JLabel matricolaLabel=new JLabel("Matricola");
			panel.add(matricolaLabel);
			panel.add(matricola);
			matricolaLabel.setBounds(596,292,200,20);
			matricola.setBounds(350,292,244,20);
		}
		//registrazione del listener
		accettaModificheUser.addActionListener(listener);
		mainMenu.addActionListener(listener);
		modificaDatiPersonali.addActionListener(listener);
	}
	/**Crea un ComboBox con gli appelli e una lista per scrivere gli iscritti*/
	public static void paintListaIscritti(){
		//creazione ComboBox per gli appelli
		appelli=new JComboBox<String>();
		//creazione pulsante main menu
		mainMenu=new JButton("Menu Principale");
		//inserimento appelli nel ComboBox
		for(int i=0;i<Controller.getNumAppelli();i++){
			appelli.addItem(Controller.getAppello(i).toString());
		}
		//creazione array di iscritti dell'esame selezionato
		String[] tmp=new String[0];
		if(View.appelli.getItemCount()!=0){
			tmp=Controller.getAppello(View.appelli.getSelectedIndex()).getIscritti();
		}
		//creazione della lista degli iscritti
		iscritti=new JList<String>(tmp);
		//set font della lista
		iscritti.setFont(new Font("Courier New",Font.PLAIN,15));
		//inserimento della lista in uno scrollpane
		scrollPanel=new JScrollPane(iscritti);
		//aggiunta dei componenti al pannello
		panel.add(appelli);
		panel.add(scrollPanel);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		scrollPanel.setBounds(250,133,500,400);
		appelli.setBounds(250,100,500,20);
		mainMenu.setBounds(418,600,158,20);
		//registrazione del listener
		mainMenu.addActionListener(listener);
		appelli.addActionListener(listener);
	}
	/**Visualizza la schermata per la stampa degli iscritti agli appelli*/
	public static void paintVisualizzaIscritti(){
		//refresh della schermata
		refresh();
		//inizializzazione della location
		location="paintVisualizzaIscritti";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);	
		console.setText("");
		//stampa lista iscritti
		paintListaIscritti();
		//creazione campo nome file
		nomeFile=new JTextField("\"Nome file\".txt");
		//creazione pulsante
		stampaSuFile=new JButton("Stampa Su File");
		//creazione label e setting dell'allineamento
		JLabel nomePercorso=new JLabel("Percorso e Nome");
		nomePercorso.setHorizontalAlignment(JLabel.RIGHT);
		//disposizione e dimensionamento dei componenti
		nomePercorso.setBounds(250,536,100,20);
		nomeFile.setBounds(353,536,270,20);
		stampaSuFile.setBounds(626,536,124,20);
		//aggiunta dei componenti al pannello
		panel.add(nomePercorso);
		panel.add(nomeFile);
		panel.add(stampaSuFile);
		//registrazione del listener
		stampaSuFile.addActionListener(listener);
	}
	/**Visualizza la schermata per inserire i voti*/
	public static void paintInserisciVoto(){
		//refresh della schermata
		refresh();
		//inizializzazione della location
		location="paintInserisciVoto";
		//inserimento della lista iscritti
		paintListaIscritti();
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);	
		console.setText("");
		//creazione label e setting dell'allineamento
		JLabel votoConseguito=new JLabel("Voto Conseguito");
		votoConseguito.setHorizontalAlignment(JLabel.RIGHT);
		//creazione pulsante inserisci disabilitato
		inserisci=new JButton("Inserisci");
		inserisci.setEnabled(false);
		//creazione ComboBox per i voti
		voto=new JComboBox<String>();
		//inserimento voti nel ComboBox
		voto.addItem("E.n.s.");
		for (int i=0; i<31; i++){
			voto.addItem(""+i);
		}
		voto.addItem("30 e lode");
		//aggiunta dei componenti al pannello
		panel.add(votoConseguito);
		panel.add(voto);
		panel.add(inserisci);
		//disposizione e dimensionamento dei componenti
		votoConseguito.setBounds(424,536,100,20);
		voto.setBounds(527,536,100,20);
		inserisci.setBounds(630,536,120,20);
		//registrazione del listener
		inserisci.addActionListener(listener);
		iscritti.addListSelectionListener(listener);
	}
	/**Visualizza la schermata per la ricerca dei voti degli appelli*/
	public static void paintTrovaRisultati(){
		//refresh della schermata
		refresh();
		//inizializzazione della location
		location="paintTrovaRisultati";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);	
		console.setText("");
		//creazione ComboBox per gli appelli
		appelli=new JComboBox<String>();
		//creazione pulsante main menu
		mainMenu=new JButton("Menu Principale");
		//creazione label risultato
		risultato=new JLabel("");
		//aggiunta dei componenti al pannello
		panel.add(appelli);
		panel.add(risultato);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		appelli.setBounds(320,300,400,20);
		risultato.setBounds(440,323,200,20);
		mainMenu.setBounds(440,397,160,20);
		//registrazione del listener
		mainMenu.addActionListener(listener);
		appelli.addActionListener(listener);
	}
	/**Visualizza la schermata con gli appelli superati e la media dello studente*/
	public static void paintCarriera(){
		//refresh della schermata
		refresh();
		//creazione pulsante main menu
		mainMenu=new JButton("Menu Principale");
		//inizializzazione della location
		location="paintCarriera";
		//reset del messaggio in console e del colore
		console.setForeground(Color.black);
		console.setText("");
		//creazione arry degli appelli
		Appello[] appelli=Controller.getAppelli();
		String[] tmp;
		int j=0;
		//ricerca del numero di appelli superati
		for(int i=0;i<appelli.length;i++){
			if (appelli[i].isIscritto(Controller.userLogged.getId()) && appelli[i].getVoto(Controller.userLogged.getId())>17 ){
				j++;
			}
		}
		float somma=0;
		//creazione di un array di stringhe di opportuna lunghezza per contenere le caratteristiche dell'appello e il voto conseguito
		tmp=new String[j];
		j=0;
		for(int i=0;i<appelli.length;i++){
			//controllo della presenza tra gli iscritti e voto superiore a 17
			if (appelli[i].isIscritto(Controller.userLogged.getId()) && appelli[i].getVoto(Controller.userLogged.getId())>17 ){
				//creazione prima stringa (informazioni sull'esame)
				String tmp1=appelli[i].getNome()+"-"+appelli[i].getTipo()+", Data: "+appelli[i].getInizio().toShortString();
				//creazione spazio tra una stringa e l'altra per permettere un allineamento
				String gap="";
				for(int k=0;k<51-tmp1.length();k++){
					gap+=" ";
				}
				//inserimento delle due stringhe seguite dal voto
				tmp[j]=tmp1+gap+appelli[i].getVoto(Controller.userLogged.getId());
				//incremento j
				j++;
				//calcolo della somma dei voti
				somma+=appelli[i].getVoto(Controller.userLogged.getId());
			}
		}
		//stringa della media
		String voto=""+(somma/j);
		//in caso di valore inesistente si inserisce 0
		if(voto.equals("NaN")){
			voto=""+0;
		}
		//creazione di una stringa di soli 5 caratteri
		if(voto.length()>5){
			voto=voto.substring(0,5);
		}
		//creazione label con media
		JLabel media=new JLabel("Media attuale: "+voto);
		//creazione lista contenente l'array creato
		appelliCarriera=new JList<String>(tmp);
		//set del font per la lista
		appelliCarriera.setFont(new Font("Courier New",Font.PLAIN,15));
		//inserimento della lista nello scrollpane
		scrollPanel=new JScrollPane(View.appelliCarriera);
		//aggiunta dei componenti al pannello
		panel.add(scrollPanel);
		panel.add(media);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		media.setBounds(418,536,200,20);
		scrollPanel.setBounds(250,133,500,400);
		mainMenu.setBounds(418,600,158,20);
		//registrazione del listener
		mainMenu.addActionListener(listener);
		
	}
	/**Visualizza una nuova finestra con l'andamento dei voti conseguiti dallo studente*/
	public static void paintStatisticheStudente(){
		//creazione nuovo frame
		JFrame stats=new JFrame("Statistiche carriera");
		//disposizione del frame
		stats.setBounds(140,120,920,720);
		//aggiunta del pannello delle statistiche
		stats.getContentPane().add(new StudentStats());
		//frame visibile
		stats.setVisible(true);
	}
	/**Visualizza la schermata dalla quale selezionare un appello per vederne l'andamento del tempo*/
	public static void paintStatisticheAppello(){
		//refresh della schermata
		refresh();
		//creazione ComboBox per gli appelli
		appelli=new JComboBox<String>();
		//creazione pulsanti
		mainMenu=new JButton("Menu Principale");
		visualizzaStats=new JButton("Visualizza Statistiche");
		//aggiunta dei componenti al pannello
		panel.add(appelli);
		panel.add(visualizzaStats);
		panel.add(mainMenu);
		//disposizione e dimensionamento dei componenti
		appelli.setBounds(320,300,400,20);
		visualizzaStats.setBounds(440,364,160,20);
		mainMenu.setBounds(440,397,160,20);
		//registrazione del listener
		mainMenu.addActionListener(listener);
		visualizzaStats.addActionListener(listener);	
	}
}

