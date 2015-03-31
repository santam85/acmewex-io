package es03a;

/**Classe per testare i metodi di ACMEDate*/
public class TestDate {

	/**metodo che lancia il test*/
	public static void main(String[] args) {
		//creazione appello
		Appello session=new Appello("Geom","Scritto","Via Venezia",new ACMEDate("2004,1,3"),new ACMEDate("2004,1,2"),3,"massimo.cicognani", new UsersDb());
		//creazione data
		ACMEDate data=new ACMEDate("2004,1,2");
		//prova del short string
		System.out.println("short string data "+data.toShortString());
		//creazione date per prova after e before
		ACMEDate date1=new ACMEDate("2004,1,1");
		ACMEDate date2=new ACMEDate("2004,1,4");
		System.out.println("sh string date1 "+date1.toShortString());
		System.out.println("sh string date2 "+date2.toShortString());
		System.out.println("current after date1 "+data.after(date1));
		System.out.println("current before date2 "+data.before(date2));
		//prova del metodo isBetween
		System.out.println("is btw: "+session.isBetween(date1,date2));
	}
}
