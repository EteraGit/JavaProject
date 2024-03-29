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
public class DeterminantPanel extends JPanel implements MouseListener{
	
	StylizedToolbar toolBar;
	StylizedButton matrixButton;
	JTextField rows;
	StylizedButton drawButton;
	JFrameTocka topLeft = new JFrameTocka(0,0);
	JFrameTocka highlightedSquare = new JFrameTocka(0,0);
	DeterminantKeyHandler keyHandler;
	StylizedButton calculateDeterminant;
	StylizedLabel resultLabel;
	StylizedLabel result;
	StylizedLabel rowsLabel;
	int length;
	String[][] matrix;
	double offset = 3.3;
	Color buttonColor = new Color(255,255,255);
	
	public DeterminantPanel()
	{
		this.setBackground(Color.WHITE);
		
		this.setLayout(new BorderLayout());
		
		toolBar = new StylizedToolbar();
		
		matrixButton = new StylizedButton("Matrices",13, buttonColor, 1 );
		matrixButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.determinantPanel);
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
		rows.setPreferredSize(new Dimension(10,20));
		
		drawButton = new StylizedButton("Draw Matrix", 13, buttonColor, 1);
		ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!rows.getText().equals(""))
            	{
            		matrix = new String[Integer.parseInt(rows.getText())][Integer.parseInt(rows.getText())];
            		setMatrix();
            		repaint();
            	}
            }
        };
		drawButton.addActionListener(actionListener);
		result = new StylizedLabel("", 13);
		calculateDeterminant = new StylizedButton("Calculate Determinant", 13, buttonColor, 1);
		calculateDeterminant.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  result.setText(Integer.toString(calculateDetermiant(matrix)));
			  }
			} );
		
		resultLabel = new StylizedLabel("Result:", 13);
		rowsLabel = new StylizedLabel("Rows:", 13);
		
		matrixButton.setPreferredSize(new Dimension(70,20));
		drawButton.setPreferredSize(new Dimension(95, 20));
		calculateDeterminant.setPreferredSize(new Dimension(140, 20));
		randomButton.setPreferredSize(new Dimension(80, 20));
		
		keyHandler = new DeterminantKeyHandler();
		addKeyListener(keyHandler);
		
		toolBar.add(Box.createHorizontalStrut(32));
		toolBar.add(matrixButton);
		toolBar.add(Box.createHorizontalStrut(22));
		toolBar.add(rowsLabel);
		toolBar.add(Box.createHorizontalStrut(10));
		toolBar.add(rows);
		toolBar.add(Box.createHorizontalStrut(70));
		toolBar.add(drawButton);
		toolBar.add(Box.createHorizontalStrut(22));
		toolBar.add(randomButton);
		toolBar.add(Box.createHorizontalStrut(22));
		toolBar.add(calculateDeterminant);
		toolBar.add(Box.createHorizontalStrut(22));
		toolBar.add(resultLabel);
		toolBar.add(Box.createHorizontalStrut(22));
		toolBar.add(result);
		toolBar.add(Box.createHorizontalStrut(22));
		
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
	
	protected static int calculateDetermiant(String[][] matrix) {
	    int det = 0;
	    if (matrix.length == 2) {
	        return Integer.parseInt(matrix[0][0]) * Integer.parseInt(matrix[1][1]) - Integer.parseInt(matrix[0][1]) * Integer.parseInt(matrix[1][0]);
	    }
	    for (int i = 0; i < matrix.length; i++) {
	        String[][] subMatrix = createSubMatrix(matrix, i);
	        int subDet = calculateDetermiant(subMatrix);
	        det += (i % 2 == 0 ? 1 : -1) * Integer.parseInt(matrix[0][i]) * subDet;
	    }
	    return det;
	}
	
	protected static String[][] createSubMatrix(String[][] matrix, int excludedCol) {
	    String[][] subMatrix = new String[matrix.length - 1][matrix.length - 1];
	    int row = 0;
	    for (int i = 1; i < matrix.length; i++) {
	        int col = 0;
	        for (int j = 0; j < matrix.length; j++) {
	            if (j == excludedCol) {
	                continue;
	            }
	            subMatrix[row][col] = matrix[i][j];
	            col++;
	        }
	        row++;
	    }
	    return subMatrix;
	}

	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		
		if(!rows.getText().equals(""))
		{
			Graphics2D g = (Graphics2D) g1;
			
			g.clearRect(0, 0, Panels.WIDTH, Panels.HEIGHT);
			
			int squareLength = (Panels.HEIGHT - toolBar.getHeight()) / (Integer.parseInt(rows.getText()) + 2);
			
			
			topLeft.x = (Panels.WIDTH - (Integer.parseInt(rows.getText()) * squareLength)) / 2;
			topLeft.y = 2 * toolBar.getHeight();
			
			g.setFont(new Font("Arial", Font.BOLD, squareLength / 3));
			g.setStroke(new BasicStroke(4));
			g.drawLine(topLeft.x - squareLength/3, topLeft.y - squareLength/3, topLeft.x - squareLength/3 , topLeft.y + Integer.parseInt(rows.getText())*squareLength + squareLength/3);
			g.drawLine(topLeft.x + Integer.parseInt(rows.getText())*squareLength + squareLength/3, topLeft.y - squareLength/3, topLeft.x + Integer.parseInt(rows.getText())*squareLength + squareLength/3, topLeft.y + Integer.parseInt(rows.getText())*squareLength + squareLength/3);
			drawGrid(g, squareLength);
			drawNumbers(g, squareLength);
		}
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	private void drawNumbers(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		FontMetrics metrics = g.getFontMetrics();
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
			{
				g.drawString(matrix[i][j], topLeft.x + i * squareLength + (squareLength - metrics.stringWidth(matrix[i][j])/2) - squareLength/2, 
										   topLeft.y + j * squareLength + squareLength - metrics.getHeight()/2 + metrics.getAscent() - squareLength/2);
			}
		}
	}
	

	private void drawGrid(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		
		int height = squareLength * Integer.parseInt(rows.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeft.x, 2 * toolBar.getHeight(), height, height);
		g.setColor(new Color(211,211,211));
		g.fillRect(topLeft.x + highlightedSquare.y * squareLength,
				   topLeft.y + highlightedSquare.x * squareLength, squareLength, squareLength);
		g.setColor(new Color(0,0,0));
		
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			g.drawLine(topLeft.x + i * squareLength, topLeft.y, topLeft.x + i * squareLength, topLeft.y + height);
			g.drawLine(topLeft.x, topLeft.y + i * squareLength, topLeft.x + height, topLeft.y + i * squareLength);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if(rows.getText().equals("")) return;
		
		if (e.getButton() == MouseEvent.BUTTON3) 
		{
			int squareLength = (Panels.HEIGHT  - toolBar.getHeight())/ (Integer.parseInt(rows.getText()) + 2);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
				{
					if(e.getX() >= topLeft.x + j * squareLength
					&& e.getX() < topLeft.x + (j+1) * squareLength
					&& e.getY() >= topLeft.y + i * squareLength
					&& e.getY() < topLeft.y + (i+1) * squareLength)
					{
						highlightedSquare.x = i;
						highlightedSquare.y = j;
						matrix[j][i] = "0";
						repaint();
						return;
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1)
		{
			int squareLength = (Panels.HEIGHT  - toolBar.getHeight())/ (Integer.parseInt(rows.getText()) + 2);
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
				{
					if(e.getX() >= topLeft.x + j * squareLength
					&& e.getX() < topLeft.x + (j+1) * squareLength
					&& e.getY() >= topLeft.y + i * squareLength
					&& e.getY() < topLeft.y + (i+1) * squareLength)
					{
						highlightedSquare.x = i;
						highlightedSquare.y = j;
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
