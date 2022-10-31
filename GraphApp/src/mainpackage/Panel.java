package mainpackage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel{
	
	private Color color;
	private int x;
	private int y;

	public Panel() 
	{
		x = 10;
		y = 10;
		color = new Color(100, 100, 100);
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		g.setColor(color);
		g.fillRect(x, y, 200, 50);
		g.setColor(new Color(200,200,7));
		g.fillRect(x + 30, y + 30, 200, 50);
		
		x+=1;
		y+=1;
	}
	
}
