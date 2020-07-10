package guiPanels;
import java.awt.*;
import javax.swing.JPanel;
import gui.draw;

public class guiLoadingBar extends JPanel implements Runnable {
	
	private int percentage=0;
	
	public void inc(int increase) {
		/**incrementa la percentuale di una quantità increase presa in input*/
		if(increase>=0) {
			/**Se l'incremento fa "sforare" la barra, lo setto automaticamente a 100*/
			if(percentage+increase<=100)
				percentage+=increase;
			else percentage=100;
		}
	}
	public void setTo(int newLoadState) {
		/**Fa assumere alla percentuale il valore newLoadState, se questo è maggiore dello stato precedente*/
		if(newLoadState>=percentage) {
			/**Se l'incremento fa "sforare" la barra, lo setto automaticamente a 100*/
			if(newLoadState<=100)
				percentage=newLoadState;
			else 
				percentage=100;
		}
	}
	public void showLoad() {
		/**Mostra lo stato della barra di caricamento (la ridipinge)*/
		revalidate();
		repaint();
		/**Si ferma per 100 millisecondi, perché il cambiamento sia visibile*/
		try {
			Thread.sleep(100);
		}
		catch(InterruptedException exc)
		{
			/**Questo catch si attiva se il programma viene interrotto durante lo sleep della barra di caricamento.
			 * Segnalo il problema all'utente.*/
			System.out.println("Il programma è stato chiuso durante lo svolgimento.\nAlcuni file potrebbero non essere stati elaborati correttamente.");
		}
		
	}
	
	public guiLoadingBar() {
	}
	
	public void run() {
		/**Mostro lo stato del caricamento finché la barra non è piena*/
		while(percentage<100) {
			showLoad();
		}
	}
	
	public void paint (Graphics g) {
		/**Coloro il pannello.*/
		super.paint(g);
		
		/**Coloro lo sfondo*/
		setBackground(new Color(200,200,200));
		
		int barW = 400;
		int barH = 75;
		int percW = barW/2;
		int percH = barH/2;
		int spaceW = 50;
		int spaceH = 35;
		
		
		/**Barre reggenti dei due pannellini*/
		draw.BorderedPanel(g, "empty", "bronze", spaceW, spaceH+percH/2, 2*spaceW+barW, spaceH+percH/2+barH/2);
		draw.Bar(g,"gold", spaceW/2, 0, spaceW/2,  spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		draw.Bar(g,"gold", (int)(3.5*spaceW+barW), 0, (int)(3.5*spaceW+barW),  spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		draw.Bar(g,"metal", spaceW/2, spaceH+percH/2+(percH/2+spaceH+barH/2)/2, spaceW, spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		draw.Bar(g,"metal", 3*spaceW+barW, spaceH+percH/2+(percH/2+spaceH+barH/2)/2, (int)(3.5*spaceW+barW), spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
	
		
		/**Pannello principale (quello con i blocchi) e pannello che mostra la percentuale raggiunta*/
		draw.BorderedPanel(g,"whiteScreen", "metal",2*spaceW, 2*spaceH+percH, barW, barH);
		draw.BorderedPanel(g,"whiteScreen", "metal",3*spaceW, spaceH, percW, percH);
			
		/**Blocchi*/
		g.setColor(new Color(0,170,0));
		for(int i=0;i<percentage;i+=5)
			g.fillRect((int)(2.5*spaceW+((spaceW)*i/14)), (int)(1.5f*spaceH+percH+barH/2f), spaceW/5, spaceH);
		
		/**Testo nel pannello di percentuale*/
		g.setColor(new Color(40,40,40));
		g.drawString(("Loading... "+percentage+"%"), (int)(3.3f*spaceW), spaceH+percH/2+5);
	}
}
