package functionsAndStructs;
import java.util.Vector;

import java.io.File;
import javax.swing.*;

import fileObjects.*;
import functionsAndStructs.commonFunctions;
import guiPanels.*;
import gui.guiLabel;

import java.awt.event.*;
import java.awt.*;

import java.lang.InterruptedException;
import java.io.FileNotFoundException;

public class splitOrJoin implements ActionListener
{	
	public void actionPerformed(ActionEvent e){
		/**Faccio scegliere all'utente i file desiderati, e li metto in una coda*/
		Vector<file> l = commonFunctions.getChosenFiles();	
		/**Se l'utente ha chiuso il JFileChooser senza scegliere nessun file non faccio nulla, altrimenti...*/
		if(l.size()!=0) {
			/**A questo punto, vediamo cosa l'utente ha detto di fare ai file*/
			/**Per farlo, ricavo il nome del JButton che è stato premuto*/
			Object source = ((JButton) e.getSource()).getName();
			
			if (source.equals("splitB")) {
				/**Occorre dividere i file.
				 * Non sappiamo i parametri, quindi li chiediamo all'utente
				 * tramite una finestra apposita.*/
				guiQueueParSelect parSelector = new guiQueueParSelect(l);
				showParSelect(parSelector, l.size());
				
				parSelector.make();
				
				/**Il parSelector si occuperà autonomamente di far avviare la divisione*/
			}
			else {
				/**Occorre effetuare il join su file*/
				/**Non serve specificare parametri, basta avviare la divisione*/
				execute("joinB",l, new Vector<>());
			}	
		}
	}
		
	private static void createAndShowFrames(guiQueue gq, guiLoadingBar lb) {
		/**Crea e mostra le due finestre della coda di file e della barra di caricamento*/
		
		/**Iniziamo dalla coda di file*/
		/**Crea la finestra che visualizza e mantiene la coda di file*/
		JFrame queueFrame = new JFrame("Coda dei file");
		queueFrame.setLayout(new GridLayout(1,1));
		gq.setPreferredSize(new Dimension(150+170*gq.getQueue().size(), 170));
		
		/**Metto guiQueue in un JScrollPane, per questioni di visibilità*/
		JScrollPane scrollQueue = new JScrollPane(gq);
		
		/**Aggiusto i parametri della scroll bar*/
		scrollQueue.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollQueue.setPreferredSize(new Dimension((gq.getQueue().size()>=3)?(600):(150+170*gq.getQueue().size()), 195));
		
		/**Rendo visibile il pannello della coda di file...*/
		queueFrame.add(scrollQueue);
		queueFrame.pack();
		queueFrame.setVisible(true);
		queueFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		/**Ora faccio lo stesso con la barra di caricamento.*/
		/**Creo una nuova JFrame*/
		JFrame loadFrame = new JFrame("Caricamento...");
		loadFrame.setBounds(0,260,600,230);
		
		/** Aggiungo la barra di caricamento alla JFrame*/
		loadFrame.add(lb);
		
		/**La rendo visibile*/
		loadFrame.setVisible(true);
		loadFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	private void showParSelect(guiQueueParSelect parSelector, int size) {
		parSelector.setPreferredSize(new Dimension(250*(size+1),420));
		
		/**Metto guiQueueParSelect in un JScrollPane*/
		JScrollPane scrollSelector = new JScrollPane(parSelector);
		scrollSelector.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollSelector.setPreferredSize(new Dimension(690,1000));
		
		
		/**Rendo visibile la finestra mettendola in un nuovo JFrame*/
		JFrame selectorFrame = new JFrame("Seleziona i parametri");
		selectorFrame.setBounds(670,0,690,700);

		selectorFrame.add(scrollSelector);
		selectorFrame.setVisible(true);
	}
	
	public static void execute(String source, Vector<file> l, Vector<Integer> parameters) {
		/**Creo una guiQueue e una barra di caricamento.
		 * Verranno entrambe usate per seguire agevolmente il procedimento*/
		guiQueue gq = new guiQueue(l);
		guiLoadingBar lb = new guiLoadingBar();	
		
		createAndShowFrames(gq, lb);
		/**Per ogni file in coda...*/
		for(int i=0;i<l.size();i++) {
			/**Prendo il file in posizione I*/
			file f=l.get(i);
			if(source.equals("splitB")) {
				/**Avvio la divisione*/
				try {
				f.split(parameters.get(i));
				}
				catch(FileNotFoundException fileNotFound) {
					System.out.println("Filenotfound");
				}
			}else{
				/**Controllo se il file in questione è un filecrypt  un filezip*/
				if(f.getFile().getName().substring(0, 1).equals("c")) {
					File temp = f.getFile();
					f=new filecrypt(temp);
				}
				else if(f.getFile().getName().substring(0, 1).equals("z")) {
					File temp = f.getFile();
					f=new filezip(temp);
				}
				/**Avvio la riunificazione*/
				f.join(f.getFile());
			};
			
			/**Mostro i progressi tramite barra di caricamento e coda*/
			gq.showQueue();
			try {
				lb.setTo(100/l.size()*(i+1));
				lb.showLoad();
			}
			catch(InterruptedException exc)
			{
				/**Questo catch si attiva se il programma viene interrotto durante lo sleep della barra di caricamento.
				 * Segnalo il problema all'utente.*/
				System.out.println("Il programma è stato chiuso durante lo svolgimento.\nAlcuni file potrebbero non essere stati elaborati correttamente.");
			}
		}
		/**L'operazione è finita, mando un messaggio all'utente*/
		JFrame f = new JFrame();
		guiLabel finished = new guiLabel("Operazione completata.", "gold",JLabel.CENTER);
		f.add(finished);
		f.setBounds(570,300,250,125);
		/**Metto il frame al centro*/
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//E mando un altro messaggio su stdout, per essere sicuri
		System.out.println("Operazione completata.");
	}
}