package functionsAndStructs;
import javax.swing.*;

import guiPanels.guiHome;

public class main
{
	
	public static void main(String args[])
	{
		/** crea un nuovo JFrame inizialmente invisibile
		 * con titolo "File Splitter" */
		JFrame f = new JFrame("File Splitter");
		
		/** creo un nuovo pannello, che rappresenterà la schermata principale */
		guiHome pan = new guiHome();
		/** Creo un margine vuoto intorno al pannello, perché abbia un aspetto migliore*/
		pan.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
		/** aggiungo il pannello al frame */
		f.add(pan);	
		
		/**L'applicazione si chiuderà una volta chiusa la schermata home*/
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**Modifico le dimensioni della finestra*/
		f.setBounds(570,200,250,250);
		
		/** Rendo visibile il JFrame*/
		f.setVisible(true);
	}
}