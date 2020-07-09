package gui;
import java.awt.*;
import javax.swing.*;

public class guiLabel extends JLabel {

	/**Creo una label con grafica personalizzata (bordo)*/
    private String labelName;
    private String material;

    public guiLabel(String label, String mater, int alignment) {
    	/**Imposto l'etichetta sul bottone*/
        super(label, alignment);
        labelName = label;
        material = mater;
        
        /**Aggiungo il bordo e un background bianco*/
        this.setBorder(new guiBorder(material, 10));
		this.setOpaque(true);
		this.setBackground(Color.white);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += size.height;
        return size;
    }
}