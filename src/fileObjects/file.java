package fileObjects;
import java.io.*;

import functionsAndStructs.commonFunctions;

public class file
{
	protected File filePrincipale;
	
	public file() {
		/**crea una nuova variabile di tipo File,
		 * a cui viene dato di default il nome file.txt*/
		filePrincipale = new File("file.txt");
	}
	public file(File newFile) {
		/**crea una nuova variabile di tipo file,
		 * che punta a newFile*/
		filePrincipale = newFile;
	}
	
	public void setFile(File newFile) {
		/**cambia il file (elimina il precedente e lo
		 * sostituisce con newFile) */
	
		filePrincipale.delete();
		filePrincipale = newFile;
	}
	public File getFile() {
		/**restituisce come parametro il file*/
		return filePrincipale;
	}
	
	public void split(int a) {}
	
	public void join(File file_1) {
		/**tolgo l'1 dalla fine del nome del file*/
		String filename = file_1.getName();
		filename = filename.substring(0, filename.length()-1);
		
		/**Trovo il path del file riunificato:
		 * sarà uguale a quello del file passato in input,
		 * ma togliamo il nome di quest'ultimo con substring*/
		String filePath = file_1.getPath().substring(0,file_1.getPath().length()-file_1.getName().length());
		/**
	 	 * tolgo la prima lettera dal nome del file destinazione,
		 * in modo che il nome del file torni come prima della divisione, e
		 * modifico il path in modo che il file finisca di nuovo in filesFolder*/
		File destFile = new File (filePath+filename.substring(1)); //destFile sarà il nostro file destinazione

		FileOutputStream dest = null;
		try
		{
			/**Creo dest File e Avvio l'output stream da esso*/
			destFile.createNewFile();
			dest = new FileOutputStream(destFile);
		}
		catch (FileNotFoundException e)
		{
			/**In teoria, avendo noi creato il file poco prima, questa eccezione non verrà
			 * mai sollevata, ma nel caso mando un messaggio di errore e interrompo il processo*/
			System.out.println("File not found exception");
			return;
		}
		catch(IOException e) {
			/**c'è stato un'errore nella creazione del file,
			 *il join non può procedere ulteriormente*/
			System.out.println("IOException durante la creazione del file");
			commonFunctions.closeFout(dest);
			
			return;
		}
		
		FileInputStream source = null;
		for(int i=1; (new File(filePath+filename+i)).exists();i++) {	
			
			File sourceFile = new File(filePath+filename+i);
			
			/**crea un nuovo inputstream*/
			try
			{
				source=new FileInputStream(sourceFile);
			}
			catch (FileNotFoundException e)
			{
				/**In teoria l'esistenza del file è garantita dalla condizione sul for,
				 * ma in ogni caso se l'inputsteam non viene trovato
				 * mando un messaggio di errore, chiudo gli stream
				 *  e interrompo il join */
				
				System.out.println("File not found exception");
				commonFunctions.closeFout(dest);
				commonFunctions.closeFin(source);

				return;
			}
			/**Trasferisco i byte da source a dest */

			try {
				/**scrive su dest il contenuto di source...*/
				System.out.println("Trasferisco "+sourceFile.length()+" bit dal file "+sourceFile.getName()+" al file "+destFile.getName());
				dest.write(source.readNBytes((int)sourceFile.length()));
			}
			catch(IOException e){
				/**Se il try non ha funzionato, può essere per due motivi:
				 * 1 - errore nel FileOutputStream
				 * 2 - problemi nel trasferimento dei dati
				 * In ogni caso, il join del file è stato compromesso,
				 * quindi segnalo l'errore, chiudo l'outputstream e interrompo l'operazione 
				 */
				System.out.println("Errore nel trasferimento dei dati");
				System.out.println("L'operazione di trasferimento verrà interrotta");
				
				commonFunctions.closeFin(source);
				commonFunctions.closeFout(dest);
				return;
			}
			/**elimino il file sourceFile, adesso che è stato riunito col principale*/
			sourceFile.delete();
		}
		/**chiudo gli stream*/
		commonFunctions.closeFin(source);
		commonFunctions.closeFout(dest);
		//e mando un messaggino
		System.out.println("Join avvenuto con successo.");
	}
}