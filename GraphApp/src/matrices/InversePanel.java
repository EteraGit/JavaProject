package matrices;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import design.StylizedButton;
import design.StylizedLabel;
import design.StylizedToolbar;
import mainpackage.JFrameTocka;
import mainpackage.Panels;
import mainpackage.Error;

@SuppressWarnings("serial")
public class InversePanel extends JPanel implements MouseListener{
	StylizedToolbar toolBar;
	StylizedButton matrixButton;
	JTextField rows;
	StylizedButton drawButton;
	JFrameTocka topLeftL = new JFrameTocka(0,0);
	JFrameTocka topLeftR = new JFrameTocka(0,0);
	JFrameTocka highlightedSquare = new JFrameTocka(0,0);
	InverseKeyHandler keyHandler = null;
	StylizedButton calculateInverse;
	int length;
	String[][] matrix; double[][] inverse;
	double offset = 6.5;
	boolean inversePressed = false;
	boolean started = false;
	Color buttonColor = new Color(255,255,255);
	StylizedLabel rowsLabel;
	
	public InversePanel()
	{
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		toolBar = new StylizedToolbar();

		matrixButton =  new StylizedButton("Matrices",13, buttonColor, 1 );
		matrixButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.inversePanel);
					Panels.startPanel.add(Panels.matrixPanel, BorderLayout.CENTER);
					Panels.mainPanel.setFocusable(true);
					Panels.mainPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		StylizedButton randomButton = new StylizedButton("Random",13, buttonColor, 1);
		randomButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  if(!rows.getText().equals("")) 
				  {
	            	  matrix = new String[Integer.parseInt(rows.getText())][Integer.parseInt(rows.getText())];
					  addRandomNumbers();
					  repaint();
				  }
			  } 
			} );
		
		rows = new JTextField(10);
		
		drawButton =  new StylizedButton("Draw matrices",13, buttonColor, 1 );
		ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!rows.getText().equals(""))
            	{
            		matrix = new String[Integer.parseInt(rows.getText())][Integer.parseInt(rows.getText())];
            		inverse = new double[Integer.parseInt(rows.getText())][Integer.parseInt(rows.getText())];
            		
            		setMatrix();
            		
            		repaint();
            	}
            }
        };
		drawButton.addActionListener(actionListener);
		
		calculateInverse = new StylizedButton("Calculate inverse",13, buttonColor, 1 );
		calculateInverse.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  inverse = calculateInverse(matrix);
				  if(inverse != null) inversePressed = true;
				  
          		  Panels.transposingPanel.setFocusable(true);
          		  Panels.transposingPanel.requestFocusInWindow();
          		  repaint();
			  }
			} );
		
		keyHandler = new InverseKeyHandler();
		addKeyListener(keyHandler);
		
		rowsLabel = new StylizedLabel("Rows", 13);
		matrixButton.setPreferredSize(new Dimension(70,20));
		drawButton.setPreferredSize(new Dimension(95, 20));
		calculateInverse.setPreferredSize(new Dimension(140, 20));
		randomButton.setPreferredSize(new Dimension(80, 20));
		
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(matrixButton);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(rowsLabel);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(rows);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(drawButton);
		toolBar.add(Box.createHorizontalStrut(22));
		toolBar.add(randomButton);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(calculateInverse);
		toolBar.add(Box.createHorizontalStrut(32));
		
		this.add(toolBar, BorderLayout.PAGE_START);
		addMouseListener(this);
	}
	protected void addRandomNumbers()
	{
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				matrix[i][j] = Integer.toString(new Random().nextInt(21) - 10);
			}
		}
	}
	
	protected void setMatrix()
	{
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[0].length; j++)
			{
				matrix[i][j] = "0";
			}
		}
	}
	
	protected double[][] calculateInverse(String[][] matrix) {
		
        int n = matrix.length;
        double[][] a = new double[n][n];
        double[][] b = new double[n][n];

        // Create an identity matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = Integer.parseInt(matrix[i][j]);
                b[i][j] = (i == j) ? 1.0 : 0.0;
            }
        }

        // Perform row operations on the augmented matrix
        for (int i = 0; i < n; i++) {
            int pivot = i;
            double pivotValue = Math.abs(a[i][i]);
            for (int j = i + 1; j < n; j++) {
                double temp = Math.abs(a[j][i]);
                if (temp > pivotValue) {
                    pivot = j;
                    pivotValue = temp;
                }
            }
            if (pivotValue == 0) {
            	Error.showError("Matrix is not invertible");
                return null;
            }
            if (pivot != i) {
                double[] temp = a[i];
                a[i] = a[pivot];
                a[pivot] = temp;
                temp = b[i];
                b[i] = b[pivot];
                b[pivot] = temp;
            }
            double aii = a[i][i];
            for (int j = 0; j < n; j++) {
                a[i][j] /= aii;
                b[i][j] /= aii;
            }
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double aij = a[j][i];
                    for (int k = 0; k < n; k++) {
                        a[j][k] -= a[i][k] * aij;
                        b[j][k] -= b[i][k] * aij;
                    }
                }
            }
        }

        return b;
	}


	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		
		if(!rows.getText().equals(""))
		{
			Graphics2D g = (Graphics2D) g1;
			
			g.clearRect(0, 0, Panels.WIDTH, Panels.HEIGHT);
			
			int squareLength = Panels.WIDTH / (2 * Integer.parseInt(rows.getText()) + 6);
			
			topLeftL.x = 2 * squareLength;	
			topLeftL.y = 2 * toolBar.getHeight();
			
			g.setFont(new Font("Arial", Font.BOLD, squareLength / 3));
			drawGrid(g, squareLength);
			drawNumbers(g, squareLength);
			
			
			g.drawString("-1", topLeftL.x + Integer.parseInt(rows.getText()) * squareLength + squareLength / 10,
							   topLeftL.y - squareLength / 10);
			
			if(inversePressed)
			{
				topLeftR.x = Panels.WIDTH - (2 + Integer.parseInt(rows.getText())) * squareLength;
				topLeftR.y = 2 * toolBar.getHeight();
				drawGridT(g, squareLength);
				drawNumbersT(g, squareLength);
				g.drawString("=", topLeftL.x + Integer.parseInt(rows.getText()) * squareLength + squareLength,
						   topLeftL.y + squareLength * (Integer.parseInt(rows.getText()))/2);
				
			}
			
		}
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	private void drawNumbersT(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
			{
				g.drawString(Double.toString(inverse[i][j]), topLeftR.x + j * squareLength + (squareLength - metrics.stringWidth(Double.toString(inverse[i][j]))/2) - squareLength/2, 
						topLeftR.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}

	private void drawGridT(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		int height = squareLength * Integer.parseInt(rows.getText());
		int width = squareLength * Integer.parseInt(rows.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftR.x, topLeftR.y, width, height);
		g.setColor(new Color(0,0,0));
		
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftR.x + i * squareLength, topLeftR.y, topLeftR.x + i * squareLength, topLeftR.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftR.x, topLeftR.y + i * squareLength, topLeftR.x + width, topLeftR.y + i * squareLength);
		}
		
	}

	private void drawNumbers(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
			{
				g.drawString(matrix[i][j], topLeftL.x + j * squareLength  + (squareLength - metrics.stringWidth(matrix[i][j])/2) - squareLength/2, 
						topLeftL.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}

	private void drawGrid(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		
		int height = squareLength * Integer.parseInt(rows.getText());
		int width = squareLength * Integer.parseInt(rows.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftL.x, 2 * toolBar.getHeight(), width, height);
		g.setColor(new Color(211,211,211));
		g.fillRect(topLeftL.x + highlightedSquare.x * squareLength,
				topLeftL.y + highlightedSquare.y * squareLength, squareLength, squareLength);
		g.setColor(new Color(0,0,0));
		
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftL.x + i * squareLength, topLeftL.y, topLeftL.x + i * squareLength, topLeftL.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftL.x, topLeftL.y + i * squareLength, topLeftL.x + width, topLeftL.y + i * squareLength);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(rows.getText().equals("")) return;
		
		if (e.getButton() == MouseEvent.BUTTON3) //right click
		{
			int squareLength = Panels.WIDTH / (2 * Integer.parseInt(rows.getText()) + 6);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
				{
					if(e.getX() >= topLeftL.x + j * squareLength
					&& e.getX() < topLeftL.x + (j+1) * squareLength
					&& e.getY() >= topLeftL.y + i * squareLength
					&& e.getY() < topLeftL.y + (i+1) * squareLength)
					{
						highlightedSquare.x = j;
						highlightedSquare.y = i;
						matrix[i][j] = "0";
						repaint();
						return;
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1) //left click
		{
			int squareLength = Panels.WIDTH / (2 * Integer.parseInt(rows.getText()) + 6);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
				{
					if(e.getX() >= topLeftL.x + j * squareLength
					&& e.getX() < topLeftL.x + (j+1) * squareLength
					&& e.getY() >= topLeftL.y + i * squareLength 
					&& e.getY() < topLeftL.y + (i+1) * squareLength)
					{
						highlightedSquare.x = j;
						highlightedSquare.y = i;
						repaint();
						return;
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void receiveKey(int key)
	{
		if(matrix[highlightedSquare.y][highlightedSquare.x] == "-")
		{
			matrix[highlightedSquare.y][highlightedSquare.x] = "-" + Integer.toString(key);
		}
		else
		{
			int absolute = Math.abs(Integer.parseInt(matrix[highlightedSquare.y][highlightedSquare.x])) * 10 + key;
			if(Integer.parseInt(matrix[highlightedSquare.y][highlightedSquare.x]) < 0) 
				matrix[highlightedSquare.y][highlightedSquare.x] = "-" + Integer.toString(absolute);
			else 
				matrix[highlightedSquare.y][highlightedSquare.x] = Integer.toString(absolute);	
		}
	}

	public void receiveKeyChar(char key)
	{
		if(key == '-' && Integer.parseInt(matrix[highlightedSquare.y][highlightedSquare.x]) == 0)
		{
			matrix[highlightedSquare.y][highlightedSquare.x] = "-";
		}
	}
}