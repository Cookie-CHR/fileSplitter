package functionsAndStructs;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.*;

import fileObjects.file;

public class commonFunctions
{
	/**Una lista di funzioni comuni a più file, raggruppate qui per maggiore leggibilità*/
	
	public static void closeFout(FileOutputStream stream) {
		/**Chiude l'output stream, occupandosi della IOException*/
		try {
			stream.close();
		}
		catch (IOException exc)
		{
			/**L'IOException avviene se non riesco a salvare
			 * segnalo l'errore
			 */
			System.out.println("Errore nella chiusura dell'output stream");
		}
	}
	public static void closeFin(FileInputStream stream) {
		/**Chiude l'inputstream, occupandosi della IOException*/
		try {
			stream.close();
		}
		catch (IOException exc)
		{
			/**L'IOException avviene se non riesco a salvare
			 * segnalo l'errore
			 */
			System.out.println("Errore nella chiusura dell'input stream");
		}
	}
	
	public static ArrayList<file> getChosenFiles(){
		/** crea il Jframe da cui selezioneremo i file*/
		JFrame sPanel = new JFrame("Seleziona i file");
		
		/** Crea un JFileChooser e lo associa alla finestra sPanel*/
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.showOpenDialog(sPanel);
		
		/**Ritorna i file scelti sotto forma di una variabile di tipo Vector<file>*/
		/**fileChooser.getSelectedFiles() ritorna un array di File,
		 * Arrays.asList la trasforma in una lista di File,
		 * convert lo trasformerà invece in una lista di tipo file(minuscolo).*/
		return new ArrayList<file>(commonFunctions.convert(new ArrayList<File>(Arrays.asList(fileChooser.getSelectedFiles()))));
	}
	private static ArrayList<file> convert(ArrayList<File> v) 
	{
		/**Trasforma una lista di File (maiuscolo) in una di file (minuscolo).*/
		ArrayList<file> vfile = new ArrayList<file>();
		for(File elem:v) {
			vfile.add(new file(elem));
		}
		return vfile;
	}
}