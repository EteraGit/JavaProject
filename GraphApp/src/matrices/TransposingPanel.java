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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import design.StylizedButton;
import design.StylizedLabel;
import design.StylizedToolbar;
import mainpackage.JFrameTocka;
import mainpackage.Panels;

@SuppressWarnings("serial")
public class TransposingPanel extends JPanel implements MouseListener{
	StylizedToolbar toolBar;
	StylizedButton matrixButton;
	JTextField rows;
	JTextField columns;
	StylizedButton drawButton;
	JFrameTocka topLeftL = new JFrameTocka(0,0);
	JFrameTocka topLeftR = new JFrameTocka(0,0);
	JFrameTocka highlightedSquare = new JFrameTocka(0,0);
	TransposingKeyHandler keyHandler = null;
	StylizedButton calculateTransposed;
	StylizedLabel rowsLabel, columnsLabel;
	int length;
	String[][] matrix;
	int[][] transposed;
	double offset = 6.5;
	boolean transPressed;
	boolean started = false;
	Color buttonColor = new Color(255,255,255);
	
	public TransposingPanel()
	{
		this.setBackground(Color.WHITE);
		
		this.setLayout(new BorderLayout());
		
		toolBar = new StylizedToolbar();

		matrixButton = new StylizedButton("Matrices",13, buttonColor, 1 );
		matrixButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.transposingPanel);
					Panels.startPanel.add(Panels.matrixPanel, BorderLayout.CENTER);
					Panels.mainPanel.setFocusable(true);
					Panels.mainPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		rows = new JTextField(10);	
		columns = new JTextField(10);
		
		drawButton = new StylizedButton("Draw matrices",13, buttonColor, 1 );
		ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!rows.getText().equals("") && !columns.getText().equals(""))
            	{
            		matrix = new String[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
            		transposed = new int[Integer.parseInt(columns.getText())][Integer.parseInt(rows.getText())];
            		setMatrix();
            		repaint();
            	}
            }
        };
		drawButton.addActionListener(actionListener);
		
		calculateTransposed = new StylizedButton("Calculate transposed matrix",13, buttonColor, 1 );
		calculateTransposed.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  transPressed = true;
				  transposed = calculateTrans(matrix);
				  
          		  Panels.transposingPanel.setFocusable(true);
          		  Panels.transposingPanel.requestFocusInWindow();
          		  repaint();
			  }
			} );
		
		keyHandler = new TransposingKeyHandler();
		addKeyListener(keyHandler);
		
		rowsLabel = new StylizedLabel("Rows", 13);
		columnsLabel = new StylizedLabel("Columns", 13);
		matrixButton.setPreferredSize(new Dimension(70,20));
		drawButton.setPreferredSize(new Dimension(95, 20));
		calculateTransposed.setPreferredSize(new Dimension(200, 20));
		
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(matrixButton);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(rowsLabel);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(rows);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(columnsLabel);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(columns);
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(drawButton);
		toolBar.add(Box.createHorizontalStrut(32));	
		toolBar.add(calculateTransposed);
		toolBar.add(Box.createHorizontalStrut(32));
		
		
		this.add(toolBar, BorderLayout.PAGE_START);
		addMouseListener(this);
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
	
	protected static int[][] calculateTrans(String[][] matrix) {
		// TODO Auto-generated method stub
		int transposed[][] = new int[matrix[0].length][matrix.length];
		for(int i = 0; i < matrix[0].length; i++)
		{
			for(int j = 0; j < matrix.length; j++) {
				transposed[i][j] = Integer.parseInt(matrix[j][i]);
			}
		}
		return transposed;
	}


	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		
		if(!rows.getText().equals(""))
		{
			Graphics2D g = (Graphics2D) g1;
			
			int squareLength = Panels.WIDTH / (Integer.parseInt(rows.getText()) + Integer.parseInt(columns.getText()) + 6);
			
			topLeftL.x = 2 * squareLength;	
			topLeftL.y = 2 * toolBar.getHeight();
			g.setFont(new Font("Arial", Font.BOLD, squareLength / 3));
			
			
			drawGrid(g, squareLength);
			drawNumbers(g, squareLength);
			g.drawString("T", topLeftL.x + Integer.parseInt(columns.getText()) * squareLength + squareLength / 10,
					   topLeftL.y - squareLength / 10);
			
			if(transPressed)
			{
				topLeftR.x = Panels.WIDTH - (2 + Integer.parseInt(rows.getText())) * squareLength;
				topLeftR.y = 2 * toolBar.getHeight();
				drawGridT(g, squareLength);
				drawNumbersT(g, squareLength);
				g.drawString("=", topLeftL.x + Integer.parseInt(columns.getText()) * squareLength + squareLength,
						   topLeftL.y + squareLength * (Integer.parseInt(rows.getText()))/2);
			}
			
		}
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	private void drawNumbersT(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(columns.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
			{
				g.drawString(Integer.toString(transposed[i][j]), topLeftR.x + j * squareLength + (squareLength - metrics.stringWidth(Integer.toString(transposed[i][j]))/2) - squareLength/2, 
						topLeftR.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}

	private void drawGridT(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		int height = squareLength * Integer.parseInt(columns.getText());
		int width = squareLength * Integer.parseInt(rows.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftR.x, topLeftR.y, width, height);
		g.setColor(new Color(0,0,0));
		
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftR.x + i * squareLength, topLeftR.y, topLeftR.x + i * squareLength, topLeftR.y + height);
		}
		for(int i = 0; i < Integer.parseInt(columns.getText()); i++)
		{
			g.drawLine(topLeftR.x, topLeftR.y + i * squareLength, topLeftR.x + width, topLeftR.y + i * squareLength);
		}
		
	}

	private void drawNumbers(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
			{
				g.drawString(matrix[i][j], topLeftL.x + j * squareLength + (squareLength - metrics.stringWidth(matrix[i][j])/2) - squareLength/2, 
						topLeftL.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}

	private void drawGrid(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		
		int height = squareLength * Integer.parseInt(rows.getText());
		int width = squareLength * Integer.parseInt(columns.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftL.x, 2 * toolBar.getHeight(), width, height);
		g.setColor(new Color(211,211,211));
		g.fillRect(topLeftL.x + highlightedSquare.x * squareLength,
				topLeftL.y + highlightedSquare.y * squareLength, squareLength, squareLength);
		g.setColor(new Color(0,0,0));
		
		for(int i = 0; i < Integer.parseInt(columns.getText()); i++)
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
			int squareLength = Panels.WIDTH / (Integer.parseInt(rows.getText()) + Integer.parseInt(columns.getText()) + 6);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
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
			int squareLength = Panels.WIDTH / (Integer.parseInt(rows.getText()) + Integer.parseInt(columns.getText()) + 6);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
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
