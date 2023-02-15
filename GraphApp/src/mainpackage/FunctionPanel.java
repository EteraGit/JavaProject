package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import ast.Expression;
import design.StylizedButton;
import design.StylizedTextArea;
import design.StylizedToolbar;
import parser.Parser;
import parser.TokenList;

@SuppressWarnings("serial")
public class FunctionPanel extends JPanel implements MouseWheelListener,MouseListener,MouseMotionListener{
	
	public double x_left;
	public double x_right;
	public double y_down;
	public double y_up;
	public int accuracy;
	private int faultTolerance = 50;
	private double zoom;
	private boolean toggleGridNumbers = true;
	private boolean toggleGridLines = true;
	private int ClickX;
	private int ClickY;
	private List<Tocka> function;
	private Parser parser = new Parser();
	public JTextField functionInput;
	public StylizedTextArea functionNames;

	public FunctionPanel() 
	{
		x_left = -8.88;
		x_right = 8.88;
		y_down = -5;
		y_up = 5;
		zoom = 0.1;
		accuracy = 10000;
		Color buttonColor = new Color(222,183,255);
		Color homeButtonColor = new Color(252, 131, 164);
		this.setLayout(null);
		setInitialFunction();
		
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.addKeyListener(new KeyPressHandler());
		
		StylizedButton homeButton = new StylizedButton("Home", 20, homeButtonColor, 2);
		homeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.functionsPanel);
					Panels.startPanel.add(Panels.mainPanel, BorderLayout.CENTER);
					Panels.mainPanel.setFocusable(true);
					Panels.mainPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		StylizedButton toggleGridNumbersButton = new StylizedButton("Toggle Grid Numbers", 9, buttonColor, 1);
		toggleGridNumbersButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  toggleGridNumbers = !toggleGridNumbers;
			  } 
			} );
		
		StylizedButton toggleGridLinesButton = new StylizedButton("Toggle Grid Lines", 9, buttonColor, 1);
		toggleGridLinesButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  toggleGridLines = !toggleGridLines;
			  } 
			} );
		
		functionInput = new JTextField("Type the function here");
		functionInput.setForeground(Color.GRAY);
		functionInput.addKeyListener(new EnterListener(parser));
		functionInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (functionInput.getText().equals("Type the function here")) {
                	functionInput.setText("");
                	functionInput.setForeground(Color.BLACK);
                }
            }
        });

		
		
		Border border = BorderFactory.createLineBorder(new Color(74,20,78), 2);
		functionInput.setBorder(border);
		
		functionNames = new StylizedTextArea(4, 15);
		
		
		
		homeButton.setBounds(20, 15, 80, 50);
		functionInput.setBounds(115,20,300,30);
		toggleGridNumbersButton.setBounds(760, 15, 100, 40);
		toggleGridLinesButton.setBounds(760, 85, 100, 40);
		functionNames.setBounds(20,345, 200, 100);
		
		
		
		this.add(homeButton);
		this.add(functionInput);
		this.add(toggleGridLinesButton);
		this.add(toggleGridNumbersButton);
		
		this.add(functionNames);
	}

	@Override
	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		
		g.setFont(new Font("default", Font.BOLD, 15));
		
		//draw grid lines
		if(toggleGridLines)
		{
			g.setColor(new Color(211,211,211));
			g.setStroke(new BasicStroke(1));
			for(int i = (int) Math.floor(y_down) + 1; i <= (int) Math.abs(y_up); i++)
			{
				JFrameTocka Ty = coordinate_system_to_jframe(0, i);
				if(i != 0) g.drawLine(0, Ty.y, Panels.WIDTH, Ty.y);
			}
			
			for(int i = (int) Math.floor(x_left) + 1; i <= (int) Math.abs(x_right); i++)
			{
				JFrameTocka Tx = coordinate_system_to_jframe(i, 0);
				if(i != 0) g.drawLine(Tx.x, 0, Tx.x, Panels.HEIGHT);
			}
		}
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		
		//draw y-axis
		if(x_right > 0 && x_left < 0)
		{
			JFrameTocka Ty_up = coordinate_system_to_jframe(0, y_up);
			JFrameTocka Ty_down = coordinate_system_to_jframe(0, y_down);
			g.drawLine(Ty_up.x, Ty_up.y, Ty_down.x, Ty_down.y);
			g.drawString("y", Ty_up.x + 10, Ty_up.y + 15);
			
			//numbers on y-axis
			if(toggleGridNumbers)
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
		
		//draw x-axis
		if(y_up > 0 && y_down < 0)
		{
			JFrameTocka Tx_right = coordinate_system_to_jframe(x_right, 0);
			JFrameTocka Tx_left = coordinate_system_to_jframe(x_left, 0);
			g.drawLine(Tx_right.x, Tx_right.y, Tx_left.x, Tx_left.y);
			g.drawString("x", Tx_right.x - 25, Tx_right.y + 10);
			
			//numbers on x-axis
			if(toggleGridNumbers)
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
		
		//draw function
		for(int i = 0; i < function.size() - 10; i++)
		{
			JFrameTocka T1 = coordinate_system_to_jframe(function.get(i).x, function.get(i).y);
			JFrameTocka T2 = coordinate_system_to_jframe(function.get(i + 1).x, function.get(i + 1).y);
			if(euclidianDistance(T1, T2) < faultTolerance)
			{
				if(function.get(i + 1).y > y_down && function.get(i + 1).y < y_up &&
						function.get(i + 1).x > x_left && function.get(i + 1).x < x_right)
					g.drawLine(T1.x, T1.y, T2.x, T2.y);
			}
		}
	}
	
	private double euclidianDistance(JFrameTocka t1, JFrameTocka t2) 
	{	
		return Math.sqrt(Math.pow(t1.x-t2.x, 2) + Math.pow(t1.y-t2.y, 2));
	}

	private void setInitialFunction() 
	{
		function = new ArrayList<Tocka>();
		for(int i = 0; i < accuracy; i++)
		{
			double broj_x = x_left + ((double) i / (double) accuracy) * Math.abs(x_right - x_left);
			double broj_y = 0;
			
			function.add(new Tocka(broj_x, broj_y));
		}
	}
	
	public void setFunction(List<Tocka> newFunction)
	{
		function = newFunction;
	}
	
	public JFrameTocka coordinate_system_to_jframe(double x, double y)
	{
		double new_x = Panels.WIDTH * (Math.abs(x - x_left) / Math.abs(x_right - x_left));
		double new_y = Panels.HEIGHT * (Math.abs(y - y_up) / Math.abs(y_up - y_down));
		return new JFrameTocka((int) new_x, (int) new_y);
	}
	
	public Tocka jframe_to_coordinate_system(int x, int y)
	{
		double new_x = x_left + Math.abs(x_right - x_left) * ((double) x / (double) Panels.WIDTH);
		double new_y = y_up - Math.abs(y_up - y_down) * ((double) y / (double) Panels.HEIGHT);
		return new Tocka(new_x, new_y);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0) //zoom in
		{
			x_right = x_right - Math.abs(x_right - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			x_left = x_left + Math.abs(x_left - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			y_up = y_up - Math.abs(y_up - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
			y_down = y_down + Math.abs(y_down - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
		}
		else //zoom out
		{
			x_right = x_right + Math.abs(x_right - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			x_left = x_left - Math.abs(x_left - jframe_to_coordinate_system(e.getX(), e.getY()).x) * zoom;
			y_up = y_up + Math.abs(y_up - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
			y_down = y_down - Math.abs(y_down - jframe_to_coordinate_system(e.getX(), e.getY()).y) * zoom;
		}
		
		parse();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ClickX = e.getX();
		ClickY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private void parse() 
	{
		TokenList Tokens = parser.Lekser(functionInput.getText());
		
		Expression expression = parser.Parse(Tokens);
		
		List<Tocka> newFunction = new ArrayList<Tocka>();
		for(int i = 0; i < accuracy; i++)
		{
			double broj_x = x_left +
							((double) i / (double) accuracy) *
							Math.abs(x_right - x_left);
			double broj_y = expression.Compute(broj_x);
			
			newFunction.add(new Tocka(broj_x, broj_y));
		}
		
		setFunction(newFunction);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Tocka T1 = jframe_to_coordinate_system(ClickX, ClickY);
		Tocka T2 = jframe_to_coordinate_system(e.getX(), e.getY());
		
		if(T1.x > T2.x && T1.y > T2.y) //gore desno
		{
			x_right = x_right + (T1.x - T2.x);
			x_left = x_left + (T1.x - T2.x);
			y_up = y_up + (T1.y - T2.y);
			y_down = y_down + (T1.y - T2.y);
		}
		else if(T1.x > T2.x && T1.y <= T2.y) //dolje desno
		{
			x_right = x_right + (T1.x - T2.x);
			x_left = x_left + (T1.x - T2.x);
			y_up = y_up - (T2.y - T1.y);
			y_down = y_down - (T2.y - T1.y);
		}
		else if(T1.x <= T2.x && T1.y > T2.y) //gore lijevo
		{
			x_right = x_right - (T2.x - T1.x);
			x_left = x_left - (T2.x - T1.x);
			y_up = y_up + (T1.y - T2.y);
			y_down = y_down + (T1.y - T2.y);
		}
		else if(T1.x <= T2.x && T1.y <= T2.y) // dolje lijevo
		{
			x_right = x_right - (T2.x - T1.x);
			x_left = x_left - (T2.x - T1.x);
			y_up = y_up - (T2.y - T1.y);
			y_down = y_down - (T2.y - T1.y);
		}
		
		ClickX = e.getX();
		ClickY = e.getY();
		
		parse();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
