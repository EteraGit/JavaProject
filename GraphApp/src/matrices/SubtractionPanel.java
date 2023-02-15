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

@SuppressWarnings("serial")
public class SubtractionPanel extends JPanel implements MouseListener{
	StylizedToolbar toolBar;
	StylizedButton matrixButton;
	JTextField rows;
	JTextField columns;
	StylizedButton drawButton;
	JFrameTocka topLeftL = new JFrameTocka(0,0);
	JFrameTocka topLeftR = new JFrameTocka(0,0);
	JFrameTocka topLeftResult = new JFrameTocka(0,0);
	JFrameTocka highlightedSquareL = new JFrameTocka(0,0);
	JFrameTocka highlightedSquareR = new JFrameTocka(0,0);
	SubtractionKeyHandler keyHandler = null;
	StylizedButton calculateSubtraction;
	StylizedLabel rowsLabel, columnsLabel;
	int length;
	String[][] matrixL, matrixR;
	int[][] matrixResult;
	double offset = 6.5;
	boolean resultPressed;
	boolean started = false;
	boolean leftLastClicked = true;
	Color buttonColor = new Color(255,255,255);
	
	public SubtractionPanel()
	{
		this.setBackground(Color.white);
		this.setLayout(new BorderLayout());
		toolBar = new StylizedToolbar();

		matrixButton =new StylizedButton("Matrices",13, buttonColor, 1 );
		matrixButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.subtractionPanel);
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
				  if(!rows.getText().equals("") && !columns.getText().equals("")) 
				  {
	            	  matrixL = new String[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
	            	  matrixR = new String[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
	            	  matrixResult = new int[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
					  addRandomNumbers();
					  repaint();
				  }
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
            		matrixL = new String[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
            		matrixR = new String[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
            		matrixResult = new int[Integer.parseInt(rows.getText())][Integer.parseInt(columns.getText())];
            		setMatrixL();
  				  	setMatrixR();
            		resultPressed = false;
            		repaint();
            	}
            }
        };
		drawButton.addActionListener(actionListener);
		
		calculateSubtraction = new StylizedButton("Calculate Subtraction", 13, buttonColor, 1);
		calculateSubtraction.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  resultPressed = true;
				  matrixResult = calculateSubtraction(matrixL, matrixR);
				  
          		  Panels.subtractionPanel.setFocusable(true);
          		  Panels.subtractionPanel.requestFocusInWindow();
          		  repaint();
			  }
			} );
		
		keyHandler = new SubtractionKeyHandler();
		addKeyListener(keyHandler);
		
		rowsLabel = new StylizedLabel("Rows", 13);
		columnsLabel = new StylizedLabel("Columns", 13);
		
		
		matrixButton.setPreferredSize(new Dimension(90,20));
		drawButton.setPreferredSize(new Dimension(110, 20));
		calculateSubtraction.setPreferredSize(new Dimension(140, 20));
		randomButton.setPreferredSize(new Dimension(80, 20));
		
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(matrixButton);
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(rowsLabel);
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(rows);
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(columnsLabel);
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(columns);
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(drawButton);
		toolBar.add(Box.createHorizontalStrut(15));
		toolBar.add(randomButton);
		toolBar.add(Box.createHorizontalStrut(25));
		toolBar.add(calculateSubtraction);
		toolBar.add(Box.createHorizontalStrut(25));
		
		this.add(toolBar, BorderLayout.PAGE_START);
		addMouseListener(this);
	}
	
	protected void addRandomNumbers()
	{
		for(int i = 0; i < matrixL.length; i++)
		{
			for(int j = 0; j < matrixL[0].length; j++)
			{
				matrixL[i][j] = Integer.toString(new Random().nextInt(21) - 10);
			}
		}
		
		for(int i = 0; i < matrixR.length; i++)
		{
			for(int j = 0; j < matrixR[0].length; j++)
			{
				matrixR[i][j] = Integer.toString(new Random().nextInt(21) - 10);
			}
		}
	}
	
	protected void setMatrixL()
	{
		for(int i = 0; i < matrixL.length; i++)
		{
			for(int j = 0; j < matrixL[0].length; j++)
			{
				matrixL[i][j] = "0";
			}
		}
	}
	
	protected void setMatrixR()
	{
		for(int i = 0; i < matrixR.length; i++)
		{
			for(int j = 0; j < matrixR[0].length; j++)
			{
				matrixR[i][j] = "0";
			}
		}
	}
	
	protected static int[][] calculateSubtraction(String[][] matrixL, String[][] matrixR) {
		// TODO Auto-generated method stub
		int result[][] = new int[matrixL.length][matrixL[0].length];
		for(int i = 0; i < matrixL.length; i++)
		{
			for(int j = 0; j < matrixL[0].length; j++) {
				result[i][j] = Integer.parseInt(matrixL[i][j]) - Integer.parseInt(matrixR[i][j]);
			}
		}
		return result;
	}


	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		
		if(!rows.getText().equals(""))
		{
			Graphics2D g = (Graphics2D) g1;
			
			g.clearRect(0, 0, Panels.WIDTH, Panels.HEIGHT);
			
			int squareLength;
			
			if(Integer.parseInt(rows.getText()) <= 3 * Integer.parseInt(columns.getText()) + 8)
				squareLength = Panels.WIDTH / (3 * Integer.parseInt(columns.getText()) + 8);
			else
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / Integer.parseInt(rows.getText());
			
			if((Integer.parseInt(rows.getText()) + 2) * squareLength > Panels.HEIGHT - toolBar.getHeight())
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / (Integer.parseInt(rows.getText()) + 2);
			
			g.setFont(new Font("Arial", Font.BOLD, squareLength/2));
			
			topLeftL.x = 2 * squareLength;	
			topLeftL.y = 2 * toolBar.getHeight();
				
			g.drawString("-", topLeftL.x + (1 + Integer.parseInt(columns.getText())) * squareLength,
					   topLeftL.y + squareLength * (Integer.parseInt(rows.getText())/2));
			
			g.setFont(new Font("Arial", Font.BOLD, squareLength/3));
			drawGridL(g, squareLength);
			drawNumbersL(g, squareLength);
			
			
			g.setFont(new Font("Arial", Font.BOLD, squareLength/2));
			topLeftR.x = (4 + Integer.parseInt(columns.getText())) * squareLength;
			topLeftR.y = 2 * toolBar.getHeight();
			g.drawString("=", topLeftR.x + (1+Integer.parseInt(columns.getText())) * squareLength,
					   topLeftL.y + squareLength * (Integer.parseInt(rows.getText())/2));
			
			g.setFont(new Font("Arial", Font.BOLD, squareLength/3));
			drawGridR(g, squareLength);
			drawNumbersR(g, squareLength);
			
			if(resultPressed)
			{
				topLeftResult.x = (6 + 2 * Integer.parseInt(columns.getText())) * squareLength;
				topLeftResult.y = 2 * toolBar.getHeight();
				drawGridResult(g, squareLength);
				drawNumbersResult(g, squareLength);
			}
			
		}
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	private void drawNumbersL(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
			{
				g.drawString(matrixL[i][j], topLeftL.x + j * squareLength  + (squareLength - metrics.stringWidth(matrixL[i][j])/2) - squareLength/2, 
						topLeftL.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}
	
	private void drawNumbersR(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
			{
				g.drawString(matrixR[i][j], topLeftR.x + j * squareLength + (squareLength - metrics.stringWidth(matrixR[i][j])/2) - squareLength/2,  
						topLeftR.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}
	
	private void drawNumbersResult(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
			{
				g.drawString(Integer.toString(matrixResult[i][j]), topLeftResult.x + j * squareLength+ (squareLength - metrics.stringWidth(Integer.toString(matrixResult[i][j]))/2) - squareLength/2,  
						topLeftResult.y + i * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}

	private void drawGridL(Graphics2D g, int squareLength) {
		
		int height = squareLength * Integer.parseInt(rows.getText());
		int width = squareLength * Integer.parseInt(columns.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftL.x, 2 * toolBar.getHeight(), width, height);
		
		if(leftLastClicked)
		{
			g.setColor(new Color(211,211,211));
			g.fillRect(topLeftL.x + highlightedSquareL.x * squareLength,
					topLeftL.y + highlightedSquareL.y * squareLength, squareLength, squareLength);
			g.setColor(new Color(0,0,0));
		}
		
		for(int i = 0; i < Integer.parseInt(columns.getText()); i++)
		{
			g.drawLine(topLeftL.x + i * squareLength, topLeftL.y, topLeftL.x + i * squareLength, topLeftL.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftL.x, topLeftL.y + i * squareLength, topLeftL.x + width, topLeftL.y + i * squareLength);
		}
		
	}

	private void drawGridR(Graphics2D g, int squareLength) {
		
		int height = squareLength * Integer.parseInt(rows.getText());
		int width = squareLength * Integer.parseInt(columns.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftR.x, 2 * toolBar.getHeight(), width, height);
		
		if(!leftLastClicked)
		{
			g.setColor(new Color(211,211,211));
			g.fillRect(topLeftR.x + highlightedSquareR.x * squareLength,
					topLeftR.y + highlightedSquareR.y * squareLength, squareLength, squareLength);
			g.setColor(new Color(0,0,0));
		}
		
		for(int i = 0; i < Integer.parseInt(columns.getText()); i++)
		{
			g.drawLine(topLeftR.x + i * squareLength, topLeftR.y, topLeftR.x + i * squareLength, topLeftR.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftR.x, topLeftR.y + i * squareLength, topLeftR.x + width, topLeftR.y + i * squareLength);
		}
	}
	
	private void drawGridResult(Graphics2D g, int squareLength) {
		
		int height = squareLength * Integer.parseInt(rows.getText());
		int width = squareLength * Integer.parseInt(columns.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftResult.x, 2 * toolBar.getHeight(), width, height);
		
		for(int i = 0; i < Integer.parseInt(columns.getText()); i++)
		{
			g.drawLine(topLeftResult.x + i * squareLength, topLeftResult.y, topLeftResult.x + i * squareLength, topLeftResult.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeftResult.x, topLeftResult.y + i * squareLength, topLeftResult.x + width, topLeftResult.y + i * squareLength);
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
			int squareLength;
			
			if(Integer.parseInt(rows.getText()) <= 3 * Integer.parseInt(columns.getText()) + 8)
				squareLength = Panels.WIDTH / (3 * Integer.parseInt(columns.getText()) + 8);
			else
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / Integer.parseInt(rows.getText());
			
			if((Integer.parseInt(rows.getText()) + 2) * squareLength > Panels.HEIGHT - toolBar.getHeight())
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / (Integer.parseInt(rows.getText()) + 2);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
				{
					if(e.getX() >= topLeftL.x + j * squareLength
					&& e.getX() < topLeftL.x + (j+1) * squareLength
					&& e.getY() >= topLeftL.y + i * squareLength
					&& e.getY() < topLeftL.y + (i+1) * squareLength)
					{
						highlightedSquareL.x = j;
						highlightedSquareL.y = i;
						leftLastClicked = true;
						matrixL[i][j] = "0";
						repaint();
						return;
					}
				}
			}
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
				{
					if(e.getX() >= topLeftR.x + j * squareLength
					&& e.getX() < topLeftR.x + (j+1) * squareLength
					&& e.getY() >= topLeftR.y + i * squareLength
					&& e.getY() < topLeftR.y + (i+1) * squareLength)
					{
						highlightedSquareR.x = j;
						highlightedSquareR.y = i;
						leftLastClicked = false;
						matrixR[i][j] = "0";
						repaint();
						return;
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1) //left click
		{
			int squareLength;
			
			if(Integer.parseInt(rows.getText()) <= 3 * Integer.parseInt(columns.getText()) + 8)
				squareLength = Panels.WIDTH / (3 * Integer.parseInt(columns.getText()) + 8);
			else
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / Integer.parseInt(rows.getText());
			
			if((Integer.parseInt(rows.getText()) + 2) * squareLength > Panels.HEIGHT - toolBar.getHeight())
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / (Integer.parseInt(rows.getText()) + 2);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
				{
					if(e.getX() >= topLeftL.x + j * squareLength
					&& e.getX() < topLeftL.x + (j+1) * squareLength
					&& e.getY() >= topLeftL.y + i * squareLength 
					&& e.getY() < topLeftL.y + (i+1) * squareLength)
					{
						highlightedSquareL.x = j;
						highlightedSquareL.y = i;
						leftLastClicked = true;
						repaint();
						return;
					}
				}
			}
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columns.getText()); j++)
				{
					if(e.getX() >= topLeftR.x + j * squareLength
					&& e.getX() < topLeftR.x + (j+1) * squareLength
					&& e.getY() >= topLeftR.y + i * squareLength 
					&& e.getY() < topLeftR.y + (i+1) * squareLength)
					{
						highlightedSquareR.x = j;
						highlightedSquareR.y = i;
						leftLastClicked = false;
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
		if(leftLastClicked)
		{
			if(matrixL[highlightedSquareL.y][highlightedSquareL.x] == "-")
			{
				matrixL[highlightedSquareL.y][highlightedSquareL.x] = "-" + Integer.toString(key);
			}
			else
			{
				int absolute = Math.abs(Integer.parseInt(matrixL[highlightedSquareL.y][highlightedSquareL.x])) * 10 + key;
				if(Integer.parseInt(matrixL[highlightedSquareL.y][highlightedSquareL.x]) < 0) 
					matrixL[highlightedSquareL.y][highlightedSquareL.x] = "-" + Integer.toString(absolute);
				else 
					matrixL[highlightedSquareL.y][highlightedSquareL.x] = Integer.toString(absolute);	
			}
		}
		else
		{
			if(matrixR[highlightedSquareR.y][highlightedSquareR.x] == "-")
			{
				matrixR[highlightedSquareR.y][highlightedSquareR.x] = "-" + Integer.toString(key);
			}
			else
			{
				int absolute = Math.abs(Integer.parseInt(matrixR[highlightedSquareR.y][highlightedSquareR.x])) * 10 + key;
				if(Integer.parseInt(matrixR[highlightedSquareR.y][highlightedSquareR.x]) < 0) 
					matrixR[highlightedSquareR.y][highlightedSquareR.x] = "-" + Integer.toString(absolute);
				else 
					matrixR[highlightedSquareR.y][highlightedSquareR.x] = Integer.toString(absolute);	
			}
		}
	}

	public void receiveKeyChar(char key)
	{
		if(leftLastClicked)
		{
			if(key == '-' && Integer.parseInt(matrixL[highlightedSquareL.y][highlightedSquareL.x]) == 0)
			{
				matrixL[highlightedSquareL.y][highlightedSquareL.x] = "-";
			}
		}
		else
		{
			if(key == '-' && Integer.parseInt(matrixR[highlightedSquareR.y][highlightedSquareR.x]) == 0)
			{
				matrixR[highlightedSquareR.y][highlightedSquareR.x] = "-";
			}
		}
	}


}