package gui;
import java.awt.*;
import javax.swing.border.AbstractBorder;

public class guiBorder extends AbstractBorder
{
    private String material;
    private int gap;

    public guiBorder(String mat, int g)
    {
        material = mat;
        gap = g;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = null;
        if (g instanceof Graphics2D)
        {
            g2d = (Graphics2D) g;
            /** Cancello gli angoli del componente originario
             *  (il bordo personalizzato avrà gli angoli arrotondati)*/
            g.clearRect(x, 0, 4, height);
            g.clearRect(width-4, 0, 4, height);
            
            /** Disegno le barre, nel materiale scelto*/
            draw.Bar(g2d, material, x+2, y+2, x+width-3, y+2);
            draw.Bar(g2d, material, x+2, y+2, x+2, y+height-3);
            draw.Bar(g2d, material, x+width-3, y+height-3, x+width-3, y+2);
            draw.Bar(g2d, material, x+width-3, y+height-3, x+2, y+height-3);
           
        }
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
        return (getBorderInsets(c, new Insets(gap, gap, gap, gap)));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets)
    {
        insets.left = insets.top = insets.right = insets.bottom = gap;
        return insets;
    }

    @Override
    public boolean isBorderOpaque()
    {
        return true;
    }
}