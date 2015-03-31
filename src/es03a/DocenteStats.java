package es03a;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
import javax.swing.JPanel;

/**Classe che rappresenta un pannello (estendendo la classe JPanel) dove disegnare l'andamento dei voti di un appello nel tempo
 * 
 * @author Giovanni Alai, Matteo Desanti, Marco Santarelli
 * @version 1.0
 * */
@SuppressWarnings("serial")
public class DocenteStats extends JPanel{
	/**Disegna l'andamento del tempo dei voti di un appello
	 * 
	 * @param g oggetto di classe Graphics che viene disegnato
	 * */
	public void paintComponent(Graphics g){
		//richiamo del metodo padre
		super.paintComponent(g);
		//colore di sfondo bianco
		setBackground(Color.white);
		//cambiamento del font per il titolo
		Font oldFont=g.getFont();
		g.setFont(new Font("Arial",Font.PLAIN,30));
		g.drawString("Andamento risultati di "+View.esameStat, 100,35);
		//settaggio vecchio font
		g.setFont(oldFont);
		//colore nero per gli assi
		g.setColor(Color.black);
		//disegno assi cartesiani di lunghezza variabile in base al frame
		g.drawLine(23,this.getHeight()-23,23,5);
		g.drawLine(23,this.getHeight()-23,this.getWidth()-5,this.getHeight()-23);
		//inserimento numeri ordinata e retta per ogni valore
		g.drawString("0",12,this.getHeight()-18);
		for(int i=20;i<621;i+=20){
			//colore grigio e disegno rette
			g.setColor(Color.lightGray);
			g.drawLine(25,this.getHeight()-23-i,this.getWidth()-5,this.getHeight()-23-i);
			//colore nero e inserimento valori
			g.setColor(Color.black);
			g.drawLine(21,this.getHeight()-23-i,25,this.getHeight()-23-i);
			if(i/20<10){
				g.drawString(""+(i/20),12,this.getHeight()-18-i);
			}else if(i/20==31){
				g.drawString("30L",1,this.getHeight()-18-i);
			}else{
				g.drawString(""+(i/20),5,this.getHeight()-18-i);
			}
		}
		//creazione dell'HashMap contenente le date degli appelli e la relativa media
		HashMap<String,Float> appelloDate=View.appelliScelti.get(View.esameStat);
		//creazione dell'array per contenere le date
		String[] date=new String[appelloDate.size()];
		//estrazione dalla mappa delle chiavi in set e passaggio ad array
		date=appelloDate.keySet().toArray(date);
		//ordinamento dell'array
		Arrays.sort(date);
		//x iniziale
		int oldX=35;
		//y iniziale pari al primo voto arrotondato moltiplicato per la distanza tra un valore e l'altro dell'ordinata
		int oldY=Math.round(this.getHeight()-23-(20*appelloDate.get(date[0])));
		//inserimento della prima data sull'ascissa
		g.drawString(date[0],oldX-23,this.getHeight()-3);
		//tracciamento della retta tratteggiata azzurra fino al valore della media 
		for (int j=this.getHeight()-25; j>oldY; j-=5){
			g.setColor(Color.cyan);
			g.drawLine(oldX,j,oldX,j-5);
			j-=5;
		}
		//inserimento di un cerchio rosso in corrispondenza della prima media
		g.setColor(Color.red);
		g.fillOval(oldX-2,oldY-2,4,4);
		//inserimento del valore numerico della media nel grafico sopra al cerchio
		g.drawString((((""+(appelloDate.get(date[0]))).length()>4)?(""+appelloDate.get(date[0])).substring(0,4):""+appelloDate.get(date[0])),oldX-6,oldY-5);
		//calcolo dell'incremento di pixel tra due valori dell'ascissa in base alla lunghezza del frame e al numero degli appelli da inserire 
		int incremento=(this.getWidth()-28)/date.length;
		//inserimento degli appelli successivi
		for(int i=1;i<date.length;i++){
			//colore blu per la retta dell'andamento
			g.setColor(Color.blue);
			//disegno della retta dal valore precedente a quello attuale
			g.drawLine(oldX,oldY,oldX+incremento,Math.round(this.getHeight()-23-(20*appelloDate.get(date[i]))));
			//colore nero
			g.setColor(Color.black);
			//disegno dell'indicatore sull'ascissa
			g.drawLine(oldX+incremento,this.getHeight()-21,oldX+incremento,this.getHeight()-25);
			//scrittura data
			g.drawString(date[i],oldX+incremento-23,this.getHeight()-3);
			//incremento di x
			oldX+=incremento;
			//calcolo di y
			oldY=Math.round(this.getHeight()-23-(20*appelloDate.get(date[i])));
			//tracciamento della retta tratteggiata azzurra fino al valore della media
			for (int j=this.getHeight()-25; j>oldY; j-=5){
				g.setColor(Color.cyan);
				g.drawLine(oldX,j,oldX,j-5);
				j-=5;
			}
			//inserimento del cerchio rosso in corrispondenza del valore della media
			g.setColor(Color.red);
			g.fillOval(oldX-2,oldY-2,4,4);
			//inserimento del valore numerico della media nel grafico sopra al cerchio
			g.drawString((""+appelloDate.get(date[i])).substring(0,4),oldX-6,oldY-5);
		}
	}
}
