package guiPanels;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import fileObjects.*;
import functionsAndStructs.*;
import gui.*;

import java.util.*;

public class guiQueueParSelect extends JPanel implements ActionListener {
	private Vector<parameterStruct> struct;
	
	
	public guiQueueParSelect(Vector<file> newQueue) {
		struct = new Vector<parameterStruct>(newQueue.size());
		/**Inizializza il vettore di parameterStruct*/
		for(int i=0;i<newQueue.size();i++) {
			struct.add(new parameterStruct(newQueue.get(i)));
			/**Per ogni elemento in coda, setto lo stesso actionlistener*/
			struct.get(i).setListener(this);
			/**Creo il pannello dove selezionare i parametri*/
			struct.get(i).setPanel(i==0);
		}
		
	}
	public guiQueueParSelect() {
		struct = new Vector<parameterStruct>();
		/**Inizializza il vettore di parameterStruct, lasciato vuoto di default*/
	}
	
	public Vector<file> getQueue(){
		/**Restituisce in output la coda di file*/
		Vector<file>queue = new Vector<file>();
		for(parameterStruct singlet:struct)
			queue.add(singlet.getFile());
		return queue;
	}
	public void setQueue(Vector<file> newQueue){
		/**Sostituisce la coda salvata con una nuova, da input*/
		struct = new Vector<parameterStruct>(newQueue.size());
		/**Inizializza il vettore di parameterStruct*/
		for(int i = 0; i<newQueue.size();i++)
		struct.set(i, new parameterStruct(newQueue.get(i)));
	}
	public Vector<Integer> getParameters(){
		/**Restituisce in output il vettore di parametri*/
		Vector<Integer> par = new Vector<Integer>();
		for(parameterStruct singlet:struct)
			par.add(singlet.getParameter());
		return par;
	}
	
	public void make() {
		/**Creo il GridBagLayout, in modo da poter disporre i componenti del panel.
		 * I componenti saranno disposti in tante righe quanto il numero di file, e un numero indefinito di colonne*/
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		
		int i=0;
		for(parameterStruct singlet:struct)
		{
			/**Lo aggiungo*/
			this.add(singlet.getPanel());
			/**Tengo il conto del numero di pannelli presenti*/
			i++;
		}
		
		/**Alla fine della coda di file, creo un JButton che mi permetta di aggiungerne di nuovi*/
		JButton newF = new JButton("Aggiungi un nuovo file");
		newF.setBorder(new guiBorder("metal", 10));
		newF.setName("newF");
		
		newF.addActionListener(this);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = i;
		c.gridy = 0;
		add(newF, c);
			
		/**Creo un JButton, sarà un bottone di conferma*/
		JButton confirm = new JButton("Conferma i metodi di selezione e i parametri");
		confirm.setBorder(new guiBorder("bronze", 10));
		confirm.setName("confirm");
		
		confirm.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.gridwidth = i+1;
		c.gridx = 0;
		c.gridy = 4;
		add(confirm, c);
	}
	
	public void actionPerformed(ActionEvent e){
		/**Un bottone è stato premuto. Sopriamo quale era con getActionCommand*/
		String source = ((JButton) e.getSource()).getName();
		
		if (source .equals("confirm")) {
			/**La formazione è stata confermata.
			 * applichiamo le dovute modifiche alla coda di file e all'array di parametri*/
		
			/**Per ogni file in coda...*/
			for(parameterStruct singlet:struct) {
				String chosen = singlet.getChosenOne();
				if (chosen.contains("b1")) {
					/**Il file diventerà un filedim*/
					singlet.setFile("filedim");
				}
				else if (chosen.contains("b2")) {
					/**Il file diventerà un fileparts*/
					singlet.setFile("fileparts");
				}
				else if (chosen.contains("b3")) {
					/**Il file diventerà un filecrypt*/
					singlet.setFile("filecrypt");
				}
				else {
					/**Il file diventerà un filezip*/
					singlet.setFile("filezip");
				}
			}
			/**Infine, una volta aggiornati tutti i parametri, avvio la divisione*/
			System.out.println("");
			splitOrJoin.execute("splitB",getQueue(), getParameters());
		}
		else if (source.equals("erase")){
			/**Occorre eliminare un file dalla coda*/
			/**Rintracciamo l'elemento da eliminare*/
			parameterStruct elem = findResponsible(e);

			/**Rimuovo l'elemento dalla coda*/
			struct.remove(elem);
			
			/**E lascio che il garbage collector lo elimini*/
			;
			
			/**Sistemo la larghezza del pannello*/
			setPreferredSize(new Dimension(250*(struct.size()+1),420));
			/**Aggiorno la schermata*/
			removeAll();
			make();
			revalidate();
			repaint();
		}
		else if (source.equals("forward")){
			/**Occorre spostare un file verso la testa della coda*/
			/**Rintracciamo l'elemento da spostare*/
			parameterStruct elem = findResponsible(e);
			int index = struct.indexOf(elem);
			if (index != 0){
				/**Se il file non è già il primo della coda,
				 * lo sposto di un'ulteriore posizione in avanti*/
				parameterStruct temp = new parameterStruct();
				temp = struct.get(index-1);
				struct.set(index-1, elem);
				struct.set(index,  temp);
				
				/**Aggiorno la schermata (non c'è bisogno di ridimensionare)*/
				removeAll();
				make();
				revalidate();
				repaint();
			}
		}
		else if (source.equals("backward")){
			/**Occorre spostare un file verso il fondo della coda*/
			/**Rintracciamo l'elemento da spostare*/
			parameterStruct elem = findResponsible(e);
			int index = struct.indexOf(elem);
			if (index != struct.size()-1){
				/**Se il file non è già il primo della coda,
				 * lo sposto di un'ulteriore posizione in avanti*/
				parameterStruct temp = new parameterStruct();
				temp = struct.get(index+1);
				struct.set(index+1, elem);
				struct.set(index,  temp);
			}
			/**Aggiorno la schermata (non c'è bisogno di ridimensionare)*/
			removeAll();
			make();
			revalidate();
			repaint();
		}
		else if (source.equals("newF")) {
			/**Occorre aggiungere uno o più file alla coda*/
			/**Utilizziamo ancora il JFileChooser per avere un vettore di file*/
			Vector<file> newQueue = commonFunctions.getChosenFiles();
			/**Memorizzo qual è la lunghezza del vettore prima dell'aggiunta dei nuovi elementi*/
			int oldMax = struct.size();
			
			/**Aggiungiamo i nuovi file alla vecchia struct*/
			for(int i=0;i<newQueue.size();i++) {
				struct.add(new parameterStruct(newQueue.get(i)));
				/**Per ogni elemento in coda, setto lo stesso actionlistener*/
				struct.get(oldMax+i).setListener(this);
				/**Creo il pannello dove selezionare i parametri*/
				struct.get(oldMax+i).setPanel(oldMax+i==0);
			}
			
			/**Sistemo la larghezza del pannello*/
			setPreferredSize(new Dimension(250*(struct.size()+1),420));
			/**Aggiorno la schermata*/
			removeAll();
			make();
			revalidate();
			repaint();
		}
	}
	
	private parameterStruct findResponsible(ActionEvent e) {
		/**Rintracciamo il buttonGroup che ha generato la azione:
		 * - per prima cosa e.getSource() troverà quale bottone è stato premuto;
		 * - poi .getModel ne troverà il modello;
		 * - infine, da quest'ultimo, getGroup ricaverà il buttongroup.*/
		ButtonGroup group = ((DefaultButtonModel)((JButton) e.getSource()).getModel()).getGroup();
		
		/**In assenza di metodi più veloci, confronto tutti i buttongroup che ho in cerca di quello giusto*/
		for(parameterStruct singlet:struct) {
			if (singlet.getQEdit() == group) {
				return singlet;
			}
		}
		return null;
	}
	
	public void paint (Graphics g) {
		super.paint(g);
	}
	@Override
	  public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/**Crea le barre reggenti*/
		for(int i=0;i<struct.size()+1;i++)
			draw.Bar(g, "bronze",150+248*i,0 ,150+248*i, 800);
	  }
}