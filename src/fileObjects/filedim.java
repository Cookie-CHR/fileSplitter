package fileObjects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import functionsAndStructs.commonFunctions;

public class filedim extends file
{
	
	public filedim() {
		/**crea una nuova variabile di tipo File,
		 * a cui viene dato di default il nome file.txt*/
		super();
	}
	public filedim(File newFile) {
		/**crea una nuova variabile di tipo file,
		 * che punta a newFile*/
		super(newFile);
	}

	public void split(int dim){
		/**divide il file principale in tanti file,
		* ognuno di dimensione dim (tranne forse l'ultimo,
		* che potrebbe avere dimensione minore)*/
		
		/**Creo un inputStream da filePrincipale*/
		FileInputStream fin = null;
		try {
		fin = new FileInputStream(this.getFile());
		}
		catch (FileNotFoundException fnf) {

			/**Se il file non viene trovato, è impossibile dividerlo.
			 * mando un messaggio di errore, chiudo l'outputstream
			 *  e interrompo la divisione */
			System.out.println("File not found exception");
			commonFunctions.closeFin(fin);
			return;
		}
		FileOutputStream fout = null;
		int i=0;
		/**Creo una stringa che rappresenta il path dei file destinazione:
		 * dovrà essere uguale al path del file principale, ma
		 * escludiamo dalla fine del path il nome di quest'ultimo usando substring*/
		String filePath = this.getFile().getPath().substring(0,this.getFile().getPath().length()-this.getFile().getName().length());
		
		for(long fileLength=filePrincipale.length();fileLength>dim;i++, fileLength-=dim) {
			File splittedFile=new File(filePath+'d'+filePrincipale.getName()+(i+1));
			
			try {
				/** Crea un nuovo file*/
				splittedFile.createNewFile();
				
				/**crea un nuovo outputstream e scrive su splittedFile
				 * il numero di byte necessari */
				fout=new FileOutputStream(splittedFile);
				fout.write(fin.readNBytes(dim));
				System.out.println("Trasferisco "+dim+" bit nel file "+(i+1));
			}
			catch(Exception e){
				/**Se il try non ha funzionato, può essere per due motivi:
				* 1 - errore nella creazione di un nuovo file
				* 2 - problemi nel trasferimento dei dati
				* In ogni caso, la suddivisione del file è stata compromessa,
				* quindi segnalo l'errore e interrompo l'operazione */
				System.out.println("Errore nel trasferimento dei dati al file "+filePrincipale.getName()+i+1);
				System.out.println("L'operazione di trasferimento verrà interrotta");
				break;
			}
		}
		
		/**Rimane da creare un eventuale ultimo file, di dimensione minore di dim*/
		if(filePrincipale.length() != 0) {
			try {
				/**crea di nuovo l'outputstream verso l'ultimo file
				*e vi scrive sopra il numero di byte necessari*/
				fout=new FileOutputStream(filePath+'d'+filePrincipale.getName()+(i+1));
				fout.write(fin.readNBytes((int)filePrincipale.length()));
				System.out.println("Trasferisco "+(filePrincipale.length()-(i*dim))+" bit nell'ultimo file");
			}
			catch(Exception e){
				/**Se il try non ha funzionato, può essere per due motivi:
				 * 1 - errore nel FileOutputStream
				 * 2 - problemi nel trasferimento dei dati
				 * In ogni caso, la suddivisione del file è stata compromessa,
				 * quindi segnalo l'errore, chiudo l'outputstream e interrompo l'operazione 
				 */
				System.out.println("Errore nel trasferimento dei dati al file "+filePrincipale.getName()+i+1);
				System.out.println("L'operazione di trasferimento verrà interrotta");
				
				/**prima di interrompere il programma, chiudo gli stream*/
				commonFunctions.closeFin(fin);
				commonFunctions.closeFout(fout);
				return;
			}
			/**chiudo gli stream*/
			commonFunctions.closeFin(fin);
			commonFunctions.closeFout(fout);
		}
		
		/**infine, elimino il file originale, ora che è terminata la suddivisione*/
		filePrincipale.delete();
		
		//(e mando un messaggio su stdout)
		System.out.println("\nSplit avvenuto con successo.\n");

	}
	
	public void join(File file_1) {
		super.join(file_1);
	}
}