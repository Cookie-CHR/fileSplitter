package fileObjects;
import java.io.*;
import java.util.zip.*;

import functionsAndStructs.commonFunctions;

public class filezip extends fileExtra
{
	
	public filezip() {
		/**crea una nuova variabile di tipo File,
		 * a cui viene dato di default il nome file.txt*/
		super();
		idLetter='z';
	}
	public filezip(File newFile) {
		/**crea una nuova variabile di tipo file,
		 * che punta a newFile*/
		super(newFile);
		idLetter='z';
	}
	
	public void extra(File f) {
		/**comprime il file*/
		/**Utilizzeremo uno zipOutputStream per comprimere il file prima di dividerlo*/
		filePrincipale = f;
		File dest = new File(f+".zip");
		FileOutputStream out = null;
		FileInputStream in = null;
		try {
			/**Inizializziamo gli stream di base*/
			in = new FileInputStream(this.getFile());
			out = new FileOutputStream(dest);
		}catch(FileNotFoundException fnf) {
			/**Se il file non viene trovato, è inutile comprimerlo.
			 * In teoria l'esistenza del file è già stata controllata,
			 * ma in ogni caso se uno degli stream non viene trovato
			 * mando un messaggio di errore, chiudo l'outputstream
			 *  e interrompo la compressione */
			System.out.println("File not found exception");
			commonFunctions.closeFin(in);
			commonFunctions.closeFout(out);
			return;
		}
		/**Creo lo zipOutputStream*/
		ZipOutputStream zipOut = new ZipOutputStream(out);
		
		try {
			/**Creo una zipEntry e un buffer*/
			zipOut.putNextEntry(new ZipEntry(filePrincipale.getName()));
			int len;
			byte[] buffer = new byte[1024];
			/**Finché nel file non compresso c'è qualcosa da leggere...*/
			while ((len = in.read(buffer)) > 0) 
				/**scrive su dest il contenuto del buffer di inputsteam*/{
				System.out.println("Trasferisco "+buffer.length+" bit dal file "+f.getName()+" al file "+dest.getName());
				zipOut.write(buffer, 0, len);
			}
			zipOut.closeEntry();
		}
		catch(IOException ioe){
			/**Se il try non ha funzionato, può essere a causa di problemi nel trasferimento dei dati
			* In ogni caso, la suddivisione del file è stata compromessa,
			* quindi segnalo l'errore e interrompo l'operazione */
			System.out.println("Errore nel trasferimento dei dati al file ");
			System.out.println("L'operazione di compressione verrà interrotta");
			commonFunctions.closeFin(in);
			commonFunctions.closeFout(out);
			return;
		}
		filePrincipale = dest;
		/**La compressione è avvenuta con successo*/
		/**Elimino il file originale*/
		f.delete();
		/**Chiudo gli stream*/
		commonFunctions.closeFin(in);
		commonFunctions.closeFout(out);
		
		//E mando un messaggio in output
		System.out.println("Compressione riuscita");
		
	}
	public void undoExtra(File f) {
		/**decomprime il file*/
		/**Creo il path*/
		String filePath = f.getPath().substring(0,f.getPath().length()-f.getName().length());
		
		/**Creo il file destinazione*/
		f = new File(filePath+f.getName().substring(1,f.getName().length()-1));
		File dest = new File(filePath+f.getName().substring(0,f.getName().length()-4));
		
        /**Creo un buffer, che conterrà la zipentry*/
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(f));
            ZipEntry ze = zipIn.getNextEntry();
           
            	System.out.println("Trasferisco "+buffer.length+" bytes nel file "+dest);
                FileOutputStream fout = new FileOutputStream(dest);
                int len;
                while ((len = zipIn.read(buffer)) > 0) {
                	/**trasferisco i byte nel buffer e poi nel file non compresso*/
                	fout.write(buffer, 0, len);
                }
            /**Chiudiamo l'outputstream*/
            fout.close();
            
            /**Chiudiamo la zipEntry*/
            zipIn.closeEntry();
            zipIn.close();
            
        } catch (IOException e) {
            /**C'è stato un problema, lo segnalo su stdout*/
        	System.out.println("IOException durante la decompressione. L'operazione verrà interrotta.");
        	return;
        }
        /**La compressione è avvenuta con successo*/
        /**Elimino il file compresso*/
        f.delete();
	}
}