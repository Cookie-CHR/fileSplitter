package guiPanels;
import java.awt.*;
import javax.swing.*;
import gui.draw;

import fileObjects.file;

import java.util.*;

public class guiQueue extends JPanel {
	ArrayList<file> queue;
	
	public guiQueue(ArrayList<file> newQueue) {
		/**Inizializza una nuova coda*/
		queue=newQueue;
	}
	public guiQueue() {
		/**Inizializza una nuova coda, lasciata vuota di default*/
		queue=new ArrayList<file>();
	}
	public ArrayList<file> getQueue(){
		/**Restituisce in output la coda di file*/
		return queue;
	}
	public void setQueue(ArrayList<file> newQueue){
		/**Sostituisce la coda salvata con una nuova, da input*/
		queue=newQueue;
	}
	public void add(file f) {
		/**Aggiunge un elemento al fondo della coda*/
		queue.add(f);
	}
	public void next() {
		/**Rimuove un elemento dalla testa della coda*/
		queue.remove(1);
	}
	
	public void showQueue() {
		/**Ridisegna la coda (da chiamare in caso di aggiornamenti alla stessa).*/
		revalidate();
		repaint();
	}
	public void showQueue(ArrayList<file> newQueue) {
		/**Disegna la coda passata in input*/
		queue=newQueue;
		repaint();
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		/**Funzione che disegna la coda*/
		
		/**Coloro lo sfondo*/
		setBackground(new Color(200,200,200));
		
		int barW = 200;
		int spaceW = 75;
		int spaceH = 30;
		
		/**Disegno l'inizio delle barre reggenti sulla sinistra*/
		draw.Bar(g, "bronze", spaceW/2, 0, spaceW/2, 3*spaceH);
		draw.Bar(g, "bronze", spaceW/2, 3*spaceH, spaceW, 3*spaceH);
		draw.Bar(g, "bronze", spaceW, 2*spaceH, 2*spaceW, 2*spaceH);
		draw.Bar(g, "bronze", spaceW, 4*spaceH, 2*spaceW, 4*spaceH);
		draw.Bar(g, "metal", spaceW, 2*spaceH, spaceW, 4*spaceH);
		
		g.setColor(new Color(50,50,50));		
		
		int i=0;
		for(file elem:queue) 
		{
			/**Disegno le due (porzioni di) barre che sorreggeranno i file*/
			draw.Bar(g, "bronze", 2*spaceW+i, 2*spaceH, spaceW+i+barW, 2*spaceH);
			draw.Bar(g, "bronze", 2*spaceW+i, 4*spaceH, spaceW+i+barW, 4*spaceH);
			
			/**Disegno tutti i file, in ordine di apparizione
			 * Il file in testa alla coda apparirà con un bordo dorato, per maggiore visibilità.*/

			draw.FileIcon(g,"whiteScreen", (i==0)?("gold"):("metal"), 3*spaceW/2+i, spaceH, elem.getFile().getName());
        	i+=2*spaceW;
		}
		i-=2*spaceW;
		
		/**Finiti i file, chiudo le barre reggenti a destra*/
		draw.Bar(g, "bronze", spaceW+i+barW, 3*spaceH, 3*spaceW/2+i+barW, 3*spaceH);
		draw.Bar(g, "metal", spaceW+i+barW, 2*spaceH, spaceW+i+barW, 4*spaceH);
		draw.Bar(g, "bronze", 3*spaceW/2+i+barW, 3*spaceH, 3*spaceW/2+i+barW, 0);
	}
}