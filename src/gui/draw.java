package gui;

import java.awt.Color;
import java.awt.Graphics;

public class draw{

	public static void FileIcon (Graphics g, String type, String barType, int x1, int y1, String fileName) {
		Color[] colorArray = return3Colors(barType);
		int w=100;
		int h=120;
		
		
		BorderedPanel(g,type,barType,x1,y1,w,h);
		g.setColor(colorArray[0]);
		FileDrawing(g, x1+20, y1+15, w-40, h-45);
		g.drawString(fileName,x1+10,y1+h-10);
	}
	public static void Bar(Graphics g, String type, int x1, int y1, int x2, int y2) {
		Color[] colorArray = return3Colors(type);

		g.setColor(colorArray[0]);
		for(int i=0;i<3;i+=2) {
			g.drawLine(x1+(i-2), y1+i, x2+(i-2), y2+i);
			g.drawLine(x1+i, y1+(i-2), x2+i, y2+(i-2));
			g.drawLine(x1+(i-1), y1-1, x1+(1-i), y1+1);
			g.drawLine(x2+(i-1), y2-1, x2+(1-i), y2+1);
		}
		
		g.setColor(colorArray[1]);
		for(int i=0;i<2;i++) {
			g.drawLine(x1+(i-1), y1+i, x2+(i-1), y2+i);
			g.drawLine(x1+i, y1+(i-1), x2+i, y2+(i-1));
		}
		
		g.setColor(colorArray[2]);
		g.drawLine(x1, y1, x2, y2);
	}
	
	public static void BorderedPanel(Graphics g, String type, String barType, int x1, int y1, int w, int h) {
		Color[] colorArray = return3Colors(type);

		if(!type.equals("empty")) {
			g.setColor(colorArray[0]);
			g.fillRect(x1, y1, w, h);
		
			g.setColor(colorArray[1]);
			g.fillRect(x1+4, y1+4, w-8, h-8);
		
			g.setColor(colorArray[2]);
			g.fillRect(x1+5, y1+5, w-10, h-10);
		}
		
		draw.Bar(g,barType,x1,y1,x1+w,y1);
		draw.Bar(g,barType,x1,y1,x1,y1+h);
		draw.Bar(g,barType,x1+w,y1,x1+w,y1+h);
		draw.Bar(g,barType,x1,y1+h,x1+w,y1+h);
	}
	
	public static void FileDrawing(Graphics g, int x1, int y1, int w, int h) {
		
		int[] xs = {x1, x1+(3*w/4),x1+w,x1+w,x1};
		int[] ys = {y1, y1, y1+(h/4),y1+h,y1+h};
		g.drawPolygon(xs,ys,5);
		
		xs= new int[]{x1+(3*w/4),x1+(3*w/4),x1+w};
		ys= new int[]{y1,y1+h/4,y1+h/4};
		g.drawPolygon(xs,ys,3);
		
		for(int i=1;i<6;i++) {
			if (i==1)
				g.drawLine(x1+w/5, y1+h/6, x1+3*w/4, y1+h/6);
			else
				g.drawLine(x1+w/5, y1+h*i/6, x1+4*w/5, y1+h*i/6);
		}
		
	}
	
	private static Color[] return3Colors(String type) 
	{
		Color colorArray[]= new Color[3];
		if (type.equals("bronze")) {
			colorArray[0] = new Color(80,10,0);
			colorArray[1] = new Color(210,45,30);
			colorArray[2] = new Color(255,150,100);
		}
		else if (type.contentEquals("metal")){
			colorArray[0] = new Color(30,30,30);
			colorArray[1] = new Color(100,100,100);
			colorArray[2] = new Color(180,180,180);
		}
		if (type.equals("gold")) {
			colorArray[0] = new Color(80,50,10);
			colorArray[1] = new Color(210,140,30);
			colorArray[2] = new Color(255,220,100);
		}
		if (type.equals("paper")) {

			colorArray[0] = new Color(175,140,40);
			colorArray[1] = new Color(205,175,90);
			colorArray[2] = new Color(235,210,140);
		}
		if (type.equals("whiteScreen")) {

			colorArray[0] = new Color(170,170,180);
			colorArray[1] = new Color(230,230,240);
			colorArray[2] = new Color(245,245,255);
		}
		return colorArray;
	}
}