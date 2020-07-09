package fileObjects;
import java.io.File;

public class filecrypt extends fileExtra
{
	public filecrypt() {
		/**crea una nuova variabile di tipo File,
		 * a cui viene dato di default il nome file.txt*/
		super();
		idLetter='c';
	}
	public filecrypt(File newFile) {
		/**crea una nuova variabile di tipo file,
		 * che punta a newFile*/
		super(newFile);
		idLetter='c';
	}
	
	public void extra(File f) {
		/** crypta il file */
	}
	public void undoExtra(File f) {
		/** decrypta il file */
		
	}
}