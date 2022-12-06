package mainpackage;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel implements MouseWheelListener{
	
	public int WIDTH;
	public int HEIGHT;
	public double x_left;
	public double x_right;
	public double y_down;
	public double y_up;
	public int Preciznost;
	public int x;
	public int y;
	public double zoom;
	public Vector<Tocka> funkcija;

	public Panel(int width, int height) 
	{
		x = 10;
		y = 10;
		WIDTH = width;
		HEIGHT = height;
		x_left = -8.88;
		x_right = 8.88;
		y_down = -5;
		y_up = 5;
		zoom = 0.1;
		Preciznost = 1000;
		
		funkcija = new Vector<Tocka>();
		
		postaviFunkciju();
		
		addMouseWheelListener(this);
	}
	
	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		
		Graphics2D g = (Graphics2D) g1;
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.setFont(new Font("default", Font.BOLD, 15));
		
		//crtaj y-os
		if(x_right > 0 && x_left < 0)
		{
			JFrameTocka Ty_up = coordinate_system_to_jframe(0, y_up);
			JFrameTocka Ty_down = coordinate_system_to_jframe(0, y_down);
			g.drawLine(Ty_up.x, Ty_up.y, Ty_down.x, Ty_down.y);
			g.drawString("y", Ty_up.x + 10, Ty_up.y + 15);
			
			//brojevi na y-osi
			for(int i = (int) Math.floor(y_down) + 1; i <= (int) Math.abs(y_up); i++)
			{
				JFrameTocka Ty = coordinate_system_to_jframe(0, i);
				if(i != 0) 
					g.drawString(Integer.toString(i),
							(int) Ty.x - 20, 
							(int) Ty.y + 5);
				g.drawLine(Ty.x - 5, Ty.y, Ty.x + 5, Ty.y);
			}
		}
		
		//crtaj x-os
		if(y_up > 0 && y_down < 0)
		{
			JFrameTocka Tx_right = coordinate_system_to_jframe(x_right, 0);
			JFrameTocka Tx_left = coordinate_system_to_jframe(x_left, 0);
			g.drawLine(Tx_right.x, Tx_right.y, Tx_left.x, Tx_left.y);
			g.drawString("x", Tx_right.x - 25, Tx_right.y + 10);
			
			//brojevi na x-osi
			for(int i = (int) Math.floor(x_left) + 1; i <= (int) Math.abs(x_right); i++)
			{
				JFrameTocka Tx = coordinate_system_to_jframe(i, 0);
				g.drawString(Integer.toString(i),
							(int) Tx.x - 5,
							(int) Tx.y + 20);
				g.drawLine(Tx.x, Tx.y - 5, Tx.x, Tx.y + 5);
			}
		}
		
		g.setColor(new Color(95,0,160));
		g.setStroke(new BasicStroke(3));
		
		//crtaj funkciju
		for(int i = 0; i < funkcija.size() - 10; i++)
		{
			JFrameTocka T1 = coordinate_system_to_jframe(funkcija.get(i).x, funkcija.get(i).y);
			JFrameTocka T2 = coordinate_system_to_jframe(funkcija.get(i + 1).x, funkcija.get(i + 1).y);
			if(funkcija.get(i + 1).y > y_down && funkcija.get(i + 1).y < y_up &&
					funkcija.get(i + 1).x > x_left && funkcija.get(i + 1).x < x_right)
				g.drawLine(T1.x, T1.y, T2.x, T2.y);
		}
		
		//pocetni pravokutnici
		for(int i = 0; i < 10; i++)
		{
			g.setColor(new Color(i * 10,i * 15, i * 5));
			g.fillRect(x + i * 10, y + i * 10, 200, 50);	
		}
				
		x += 1;
		y += 1;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println(e.getWheelRotation());
		System.out.println(e.getX() + "  " + e.getY());
		System.out.println(jframe_to_coordinate_system(e.getX(), e.getY()).x + "  " + jframe_to_coordinate_system(e.getX(), e.getY()).y);
		
		if(e.getWheelRotation() < 0) //zoom in
		{
			x_right = x_right - Math.abs(x_right - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			x_left = x_left + Math.abs(x_left - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			y_up = y_up - Math.abs(y_up - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
			y_down = y_down + Math.abs(y_down - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
		}
		else //zoom out
		{
			x_right = x_right + Math.abs(x_left - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			x_left = x_left - Math.abs(x_right - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			y_up = y_up + Math.abs(y_down - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
			y_down = y_down - Math.abs(y_up - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
		}
		postaviFunkciju();
		repaint();
	}
	
	public void postaviFunkciju()
	{
		funkcija.clear();
		for(int i = 0; i < Preciznost; i++)
		{
			double broj_x = x_left + ((double) i / (double) Preciznost) * Math.abs(x_right - x_left);
			double broj_y = Math.sin(broj_x);
			
			funkcija.add(new Tocka(broj_x, broj_y));
		}
	}
	
	public JFrameTocka coordinate_system_to_jframe(double x, double y)
	{
		double new_x = WIDTH * (Math.abs(x - x_left) / Math.abs(x_right - x_left));
		double new_y = HEIGHT * (Math.abs(y - y_up) / Math.abs(y_up - y_down));
		return new JFrameTocka((int) new_x, (int) new_y);
	}
	
	public Tocka jframe_to_coordinate_system(int x, int y)
	{
		double new_x = x_left + Math.abs(x_right - x_left) * ((double) x / (double) WIDTH);
		double new_y = y_up - Math.abs(y_up - y_down) * ((double) y / (double) HEIGHT);
		return new Tocka(new_x, new_y);
	}
}
