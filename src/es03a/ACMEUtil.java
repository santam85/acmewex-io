package es03a;
import java.util.Arrays;

/**Classe contenente metodi statici di utilità varia
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 * */
public class ACMEUtil {
	/**Ordina gli elementi di un array
	 * 
	 * @param list array da ordinare
	 * */
	static void sortId(String[] list){
		Arrays.sort(list);
	}
	/**Ordina gli elementi di un array da una determinata posizione iniziale a una determinata posizione finale 
	 * 
	 * @param list array da ordinare
	 * */
	static void sortId(String[] list,int inizio,int fine){
		Arrays.sort(list,inizio,fine);
	}
}
