package functionsAndStructs;

import java.awt.*;
import javax.swing.*;

import fileObjects.*;
import gui.guiBorder;
import gui.guiLabel;

import java.awt.event.*;

public class parameterStruct
{
	private file queueFile;
	private int p;
	private ButtonGroup choice = new ButtonGroup();
	private JTextField txt = new JTextField();
	private ButtonGroup qEdit = new ButtonGroup();
	private JPanel structPanel = new JPanel();
	private ActionListener listener;
	
	
	public parameterStruct(file newFile) {
		queueFile = newFile;
		txt.setEditable(true);   
	}
	
	public parameterStruct() {
		queueFile = new file();
		txt.setEditable(true);   
	}
	
	public file getFile() {return queueFile;}
	public int getParameter() {convert(); return p;}
	public ButtonGroup getChoice() {return choice;}
	public JTextField getTxt() {return txt;}
	public ButtonGroup getQEdit() {return qEdit;}
	public JPanel getPanel() {return structPanel;}
	
	public void setListener(ActionListener newL) {listener = newL;}
	
	public void setFile(String type) {
		switch(type) {
			case "filedim": queueFile=new filedim(queueFile.getFile()); break;
			case "fileparts": queueFile=new fileparts(queueFile.getFile()); break;
			case "filecrypt": queueFile=new filecrypt(queueFile.getFile()); break;
			case "filezip": queueFile=new filezip(queueFile.getFile()); break;
			default: break;
			}
	}
	
	public void setPanel(boolean first) {
		
		GridBagLayout gbl = new GridBagLayout();
		structPanel.setLayout(gbl);
		structPanel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		
		/**Creo e aggiungo il nome del file all'inizio della colonna*/
		guiLabel nomeFile = new guiLabel(queueFile.getFile().getName(),(first)?("gold"):("metal"), JLabel.CENTER);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.ipady = 20;
		structPanel.add(nomeFile,c);
		
		/**Creo un pannello in cui mettere tutti i JRadioButton che creerò in seguito*/
		/**Ho fornito, come commento monolineare, anche il codice per l'aggiunta del bottone per il filecrypt*/
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(0,1,10,10));
		radioPanel.setBackground(Color.white);
		radioPanel.setBorder(new guiBorder((first)?("gold"):("metal"), 10));
		
		/**Inserisco l'optionPanel sotto al nome del file*/
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		structPanel.add(radioPanel,c);
		
		/**Creo quattro JRadioButton
		 * I JRadioButton rappresenteranno la modalità di divisione*/
		JRadioButton b1 = new JRadioButton("Dividi data la dimensione");
		JRadioButton b2 = new JRadioButton("Dividi dato il numero parti");
		//JRadioButton b3 = new JRadioButton("Dividi e crypta");
		JRadioButton b4 = new JRadioButton("Dividi e comprimi");
		
		b1.setActionCommand("b1");
		b2.setActionCommand("b2");
		//b3.setActionCommand("b3");
		b4.setActionCommand("b4");
		
		/**Faccio sì che un bottone sia già preselezionato, per evitare valori nulli*/
		b1.setSelected(true);
		
		/**Li aggiungo ad uno stesso ButtonGroup*/
		choice.add(b1); 
		choice.add(b2); 
		//choice.add(b3);
		choice.add(b4);
		 
		/**Metto i radio button nel radioPanel apposito*/
		radioPanel.add(b1);
		radioPanel.add(b2);
		//radioPanel.add(b3);
		radioPanel.add(b4);
		
		/**Aggiungo anche una textbox in cui inserire il parametro*/
		txt.setBorder(new guiBorder((first)?("gold"):("metal"), 10));
		
		txt.setName(queueFile.getFile().getName());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;

		c.ipady = 30;
		structPanel.add(txt,c);
		
		/**Creo un pannello in cui mettere tutte le opzioni strettamente collegate alla modifica degli elementi in coda*/
		JPanel queueEditPanel = new JPanel();
		queueEditPanel.setLayout(new GridLayout(1,0,10,0));
		queueEditPanel.setBackground(Color.white);
		queueEditPanel.setBorder(new guiBorder((first)?("gold"):("metal"), 10));
		
		/**Sistemo i gridBag Constraints e inserisco il queueEditPanel sotto al RadioPanel*/
		c.gridx = 0;
		c.gridy = 3;
		structPanel.add(queueEditPanel,c);
		
		/**Creo tre JButton
		 * - il pulsante forward farà spostare il relativo file verso la testa della coda di una posizione
		 * - il pulsante erase eliminerà il file dalla coda
		 * - il pulsante backward farà spostare il relativo file verso il fondo della coda di una posizione*/
		JButton forward = new JButton("←");
		JButton erase = new JButton("X");
		JButton backward = new JButton("→");
		
		/**Dò loro un nome*/
		erase.setName("erase");
		forward.setName("forward");
		backward.setName("backward");
		
		/**Li aggiungo al buttonGroup della loro parameterStruct*/
		qEdit.add(forward);
		qEdit.add(erase);
		qEdit.add(backward);
		
		/**Li collego all'ActionListener*/
		forward.addActionListener(listener);
		erase.addActionListener(listener);
		backward.addActionListener(listener);
		 
		/**Metto i JButton nel queueEditPanel apposito*/
		queueEditPanel.add(forward);
		queueEditPanel.add(erase);
		queueEditPanel.add(backward);
	}
	
	public String getChosenOne() {return choice.getSelection().getActionCommand();}
	public void setVisible(boolean visible) {structPanel.setVisible(visible);}
	
	private void convert() {
		try{
			/** Se su JTextField c'è un intero, lo incollo nell'array di parametri*/
			p = Integer.parseInt(txt.getText());
		} 
		catch (NumberFormatException notAnInteger) {
			/**Questo catch si attiva se il parametro registrato non è un int.
			 * Inserisco 1 come valore di default*/
			p = 1;
		}
	}	
}