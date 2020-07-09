package fileObjects;
import java.io.File;
import java.nio.file.*;
import java.io.IOException;

public abstract class fileExtra extends filedim
{
	protected char idLetter;

	public fileExtra() {
		/**crea una nuova variabile di tipo File,
		 * a cui viene dato di default il nome file.txt*/
		super();
	}
	public fileExtra(File newFile) {
		/**crea una nuova variabile di tipo file,
		 * che punta a newFile*/
		super(newFile);
	}
	
	@Override
	public void split(int dim){
		/**salva il nome del file originale e il suo path*/
		String filename=filePrincipale.getName();
		String filePath = this.getFile().getPath().substring(0,this.getFile().getPath().length()-this.getFile().getName().length());
		
		/**applica la funzione extra al file e lo divide in parti, data la dimensione*/
		extra(filePrincipale);
		
		super.split(dim);
		filePrincipale = new File(filePath+"d"+filePrincipale.getName()+1);
		firstLetterAdd(filePrincipale, filename, idLetter);
	}
	public void join(File file_1) {
		file_1 = firstletterChange(file_1);
		/**riunisce i file, ricreando il file principale, e annulla la funzione extra*/
		super.join(file_1);
		undoExtra(file_1);
	}
	
	protected void firstLetterAdd(File dFile, String filename, char idLetter) {
		/**sostituisco la lettera identificativa del primo file con quella giusta,
		 * così sarà possibile capire che è un file con extra */
		Path source = Paths.get(dFile.getAbsolutePath());
		try {
		Files.move(source, source.resolveSibling(idLetter+filename+".zip1"),StandardCopyOption.REPLACE_EXISTING);}
		catch(IOException e){
			System.out.println("IOException");
		}
		
	}
	protected File firstletterChange(File file_1) {
		/**rimette una d come prima lettera del file (per questioni di identificazione)*/
		Path source = Paths.get(file_1.getAbsolutePath());
		/**Creo il path (vedi spiegazioni nella sezione filedim o file)*/
		String destPath = file_1.getPath().substring(0,file_1.getPath().length()-file_1.getName().length());
		File dest = new File(destPath+'d'+file_1.getName().substring(1));
		try {
		Files.move(source, source.resolveSibling(dest.getName()),StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException e){
			System.out.println("IOException");
		}
		return dest;
	}
	
	/**Funzioni extra e undoExtra, che rappresentano rispettivamente:
	 * - la criptatura e decriptatura dei fileCrypt;
	 * - la compressione e decompressione dei fileZip.
	 * Verranno implementate nelle classi figlie.*/
	public abstract void extra(File f);
	public abstract void undoExtra(File f);
}