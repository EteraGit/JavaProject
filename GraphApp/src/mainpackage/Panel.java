package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

public class Panel extends JPanel{
	
	private Color color;
	private int x;
	private int y;
	public final int Preciznost = 100;
	Vector<Tocka> arr;

	public Panel() 
	{
		x = 10;
		y = 10;
		arr = new Vector<Tocka>();
		
		color = new Color(100, 100, 100);
		for(int i = 0; i < 2 * 628; i++)
		{
			double broj_x = ((double) i ) / 100 - 6.28;
			double broj_y = Math.log(broj_x) * broj_x;
			
			arr.add(new Tocka(broj_x, broj_y));
		}
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		g.setColor(new Color(100,100,100));
		g.drawLine(250, 0, 250, 500);
		g.drawLine(0, 250, 500, 250);
		g.drawString("y", 240, 15);
		g.drawString("x", 475, 240);
		
		//crtaj funkciju
		for(int i = 0; i < arr.size()-1; i++)
		{
			g.drawLine((int) (arr.get(i).x * 39.8 + 250), 
						(int) (arr.get(i).y * (-39.8) + 250), 
						(int) (arr.get(i+1).x * 39.8 + 250), 
						(int) (arr.get(i+1).y * (-39.8) + 250));
			System.out.println(arr.get(i).x);
		}
		
		//pocetni pravokutnici
		for(int i = 0; i < 10; i++)
		{
			g.setColor(new Color(i * 10,i * 15, i * 5));
			g.fillRect(x + i * 10, y + i * 10, 200, 50);	
		}
		
		//crtaj brojeve na osima
		for(int i = 0; i < 13; i++)
		{
			g.drawString(Integer.toString(i - 6), (int) ((i-6) * 39.8 + 250), 260);
			if(i-6 != 0) g.drawString(Integer.toString(i - 6), 255, (int) ((i-6) * (-39.8) + 250));
		}
		
		x+=1;
		y+=1;
	}
	
	public void drawFunction(Graphics g)
	{
		
	}
	
}
