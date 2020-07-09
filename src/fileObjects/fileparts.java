package fileObjects;
import java.io.*;
import functionsAndStructs.commonFunctions;

public class fileparts extends file
{
	
	public fileparts() {
		/**crea una nuova variabile di tipo File,
		 * a cui viene dato di default il nome file.txt*/
		super();
	}
	public fileparts(File newFile) {
		/**crea una nuova variabile di tipo file,
		 * che punta a newFile*/
		super(newFile);
	}
	
	public void split(int numParts) {
		/**divide il filePrincipale in numParts parti di uguale dimensione (o quasi)*/

		FileInputStream fin = null;
		try
		{
			/**Avvio l'input stream dal file indicato*/
			fin = new FileInputStream(filePrincipale);
		}
		catch (FileNotFoundException e)
		{
			/**Se il file non viene trovato, è inutile dividerlo,
			 * quindi interrompo lo splitting e mando un messaggio di errore*/
			System.out.println("File not found exception");
			return;
		}
		FileOutputStream fout = null;
		int filePrincipaleDim=(int)filePrincipale.length();
		String filePath = this.getFile().getPath().substring(0,this.getFile().getPath().length()-this.getFile().getName().length());
		
		for(int i=0;i<numParts;i++) {	
			/**creo un nuovo file dove mettere i byte*/
			File splittedFile=new File(filePath+'p'+filePrincipale.getName()+(i+1));
			try {
				splittedFile.createNewFile();
			}
			catch(IOException e) {
				/**c'è stato un'errore nella creazione del file,
				 *la scomposizione non può procedere ulteriormente*/
				System.out.println("IOException durante la creazione del file"+'p'+filePrincipale.getName()+(i+1));
				commonFunctions.closeFin(fin);
				commonFunctions.closeFout(fout);
				
				return;
			}
			
			/**crea un nuovo outputstream*/
			try
			{
				fout=new FileOutputStream(splittedFile);
			}
			catch (FileNotFoundException e)
			{
				/**In teoria l'esistenza del file è già stata controllata,
				 * ma in ogni caso se l'outputsteam non viene trovato
				 * mando un messaggio di errore, chiudo l'outputstream
				 *  e interrompo lo split */
				
				System.out.println("File not found exception");
				commonFunctions.closeFout(fout);
				commonFunctions.closeFin(fin);

				return;
			}
			/**Trasferisco il numero di byte necessari da fin a fout */

			try {
				/**scrive su fout il numero di byte necessari...*/
				fout.write(fin.readNBytes(filePrincipaleDim/numParts));
			}
			catch(IOException e){
				/**Se il try non ha funzionato, può essere per tre motivi:
				 * 1 - errore nella creazione di un nuovo file
				 * 2 - errore nel FileOutputStream
				 * 3 - problemi nel trasferimento dei dati
				 * In ogni caso, la suddivisione del file è stata compromessa,
				 * quindi segnalo l'errore, chiudo l'outputstream e interrompo l'operazione 
				 */
				System.out.println("Errore nel trasferimento dei dati");
				System.out.println("L'operazione di trasferimento verrà interrotta");
				
				commonFunctions.closeFin(fin);
				commonFunctions.closeFout(fout);
				return;
			}
			System.out.println("Trasferisco "+filePrincipaleDim/numParts+" bit nel file "+(i+1));
		}
		
		/**siccome filePrincipaleDim/numParts è una divisione intera,
		 * potrebbero essere rimasti alcuni bit di resto in filePrincipale
		 * Metto suddetti bit nell'ultimo file*/
		if(filePrincipale.length() > 0) {
			System.out.println("rimasti "+filePrincipale.length()%(filePrincipale.length()/numParts)+" bit.");
			try {
				/**scrive su fout il numero di byte necessari...*/
				fout.write(fin.readNBytes((int)(filePrincipale.length()%(filePrincipale.length()/numParts))));
			}
			catch(IOException e){
				/**Se il try non ha funzionato, può essere per due motivi:
				 * 1 - errore nel FileOutputStream
				 * 2 - problemi nel trasferimento dei dati
				 * In ogni caso, la suddivisione del file è stata compromessa,
				 * quindi segnalo l'errore, chiudo l'outputstream e interrompo l'operazione 
				 */
				System.out.println("Errore nel trasferimento dei dati");
				System.out.println("L'operazione di trasferimento verrà interrotta");
				
				commonFunctions.closeFin(fin);
				commonFunctions.closeFout(fout);
				
				return;
			}
			System.out.println("Trasferisco "+filePrincipale.length()%(filePrincipale.length()/numParts)+" bit nell'ultimo file ");
		}
		
		/**chiudo gli stream*/
		commonFunctions.closeFin(fin);
		commonFunctions.closeFout(fout);
		
		/**infine, elimino il file originale, ora che è terminata la suddivisione*/
		filePrincipale.delete();
		
		//(e mando un messaggio su stdout)
		System.out.println("\nSplit avvenuto con successo.\n");
	}
	
	public void join(File file_1) {
		super.join(file_1);
	}
}