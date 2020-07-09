package guiPanels;
import java.awt.*;
import javax.swing.JPanel;
import gui.draw;

public class guiLoadingBar extends JPanel {
	
	private int percentage=0;
	
	public void inc(int increase) throws InterruptedException {
		if(increase>=0) {
			if(percentage+increase<=100)
				percentage+=increase;
			else percentage=100;
			Thread.sleep(500);
		}
	}
	public void setTo(int newLoadState) throws InterruptedException {
		if(newLoadState>=percentage) {
			if(newLoadState<=100)
				percentage=newLoadState;
			else 
				percentage=100;
			Thread.sleep(100);
		}
	}
	public void showLoad() {
		revalidate();
		repaint();
		}
	
	public guiLoadingBar() {
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		
		/**Coloro lo sfondo*/
		setBackground(new Color(200,200,200));
		
		int barW = 400;
		int barH = 75;
		int percW = barW/2;
		int percH = barH/2;
		int spaceW = 50;
		int spaceH = 35;
		
		
		
		//Main bar and main panel
		draw.Bar(g,"bronze",spaceW, 2*spaceH+percH+barH/2, 3*spaceW+barW, 2*spaceH+percH+barH/2);
		draw.BorderedPanel(g,"whiteScreen", "metal",2*spaceW, 2*spaceH+percH, barW, barH);

		//Percent bar and percent panel
		draw.Bar(g,"bronze",spaceW, spaceH+percH/2, 3*spaceW+barW, spaceH+percH/2);
		draw.BorderedPanel(g,"whiteScreen", "metal",3*spaceW, spaceH, percW, percH);
		
		//Outside
		draw.Bar(g,"gold", spaceW, spaceH+percH/2, spaceW, 2*spaceH+percH+barH/2);
		draw.Bar(g,"gold", 3*spaceW+barW, spaceH+percH/2, 3*spaceW+barW, 2*spaceH+percH+barH/2);
		draw.Bar(g,"metal", spaceW/2, spaceH+percH/2+(percH/2+spaceH+barH/2)/2, spaceW, spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		draw.Bar(g,"metal", 3*spaceW+barW, spaceH+percH/2+(percH/2+spaceH+barH/2)/2, (int)(3.5*spaceW+barW), spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		draw.Bar(g,"bronze", spaceW/2, 0, spaceW/2,  spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		draw.Bar(g,"bronze", (int)(3.5*spaceW+barW), 0, (int)(3.5*spaceW+barW),  spaceH+percH/2+(percH/2+spaceH+barH/2)/2);
		
		//bar blocks
		g.setColor(new Color(0,170,0));
		for(int i=0;i<percentage;i+=5)
			g.fillRect((int)(2.5*spaceW+((spaceW)*i/14)), (int)(1.5f*spaceH+percH+barH/2f), spaceW/5, spaceH);
		
		//percent text
		g.setColor(new Color(40,40,40));
		g.drawString(("Loading... "+percentage+"%"), (int)(3.3f*spaceW), spaceH+percH/2+5);
	}
}