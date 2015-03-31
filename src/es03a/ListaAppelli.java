package es03a;

import java.util.HashMap;

public class ListaAppelli implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//campo privato di classe HashMap che contiene gli appelli inseriti
	public HashMap<String, Appello> map = new HashMap<String, Appello>();
	//	campo privato  String[] che contiene l'elenco ordinato di tutti i nomi(chiavi) degli appelli presenti
	public String[] keySet= new String[0];
}
