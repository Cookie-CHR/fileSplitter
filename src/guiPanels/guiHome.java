package guiPanels;
import java.awt.*;
import javax.swing.*;

import functionsAndStructs.splitOrJoin;
import gui.*;

public class guiHome extends JPanel {
	
	public guiHome() {
		/**Creo il GridLayout, in modo da poter disporre i componenti del panel.
		 * I componenti saranno disposti in una colonna e un numero indefinito di righe*/
		GridLayout gl = new GridLayout(0,1);
		gl.setVgap(10);
		setLayout(gl);

		/**Creo e aggiungo il titolo*/
		guiLabel titolo = new guiLabel("File Splitter","gold",JLabel.CENTER);

		add(titolo);
		
		/**Creo due bottoni, "splitB" e "joinB"*/
		JButton splitB = new JButton("Dividi i file");
		splitB.setBorder(new guiBorder("metal", 10));
		JButton joinB = new JButton("Riunisci i file");
		joinB.setBorder(new guiBorder("metal", 10));
		
		/**Imposto loro un nome, per poterli identificare più facilmente
		 * al di fuori della funzione (es. dall'ascoltatore)*/
		splitB.setName("splitB");
		joinB.setName("joinB");
		
		/** Associo loro un ascoltatore*/
		splitB.addActionListener(new splitOrJoin());
		joinB.addActionListener(new splitOrJoin());
		
		/**li aggiungo al pannello guiHome.*/
		add (splitB);
		add (joinB);
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		/**Colora lo sfondo di grigio chiaro*/
		setBackground(new Color(200,200,200));	
	}
	
	@Override
	  public void paintComponent(Graphics g) {			
		/**Crea due barre che "reggono" i bottoni e la label*/
		draw.Bar(g, "bronze",75,0 ,75, 800);
		draw.Bar(g, "bronze",this.getBounds().width -75,0 ,this.getBounds().width -75, 800);
	  }
}