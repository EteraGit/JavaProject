package matrices;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import mainpackage.JFrameTocka;
import mainpackage.Panels;
import mainpackage.Error;

@SuppressWarnings("serial")
public class MultiplicationPanel extends JPanel implements MouseListener{
	JToolBar toolBar;
	JButton matrixButton;
	JLabel r, l;
	JTextField rowsL, rowsR;
	JTextField columnsL, columnsR;
	JButton drawButton;
	JFrameTocka topLeftL = new JFrameTocka(0,0);
	JFrameTocka topLeftR = new JFrameTocka(0,0);
	JFrameTocka topLeftResult = new JFrameTocka(0,0);
	JFrameTocka highlightedSquareL = new JFrameTocka(0,0);
	JFrameTocka highlightedSquareR = new JFrameTocka(0,0);
	MultiplicationKeyHandler keyHandler = null;
	JButton calculateMulti;
	int length;
	int[][] matrixL, matrixR, matrixResult;
	double offset = 6.5;
	boolean resultPressed;
	boolean started = false;
	boolean leftLastClicked = true;
	
	public MultiplicationPanel()
	{
		this.setBackground(Color.cyan);
		
		toolBar = new JToolBar();

		matrixButton = new JButton("Matrices");
		matrixButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.multiplicationPanel);
					Panels.startPanel.add(Panels.matrixPanel, BorderLayout.CENTER);
					Panels.mainPanel.setFocusable(true);
					Panels.mainPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		rowsL = new JTextField(5);	
		columnsL = new JTextField(5);
		rowsR = new JTextField(5);	
		columnsR = new JTextField(5);
		l=new JLabel("1.");
		r=new JLabel("2.");
		
		drawButton = new JButton("Draw Matrices");
		ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!rowsL.getText().equals("") && !columnsL.getText().equals("") && !rowsR.getText().equals("") && !columnsR.getText().equals(""))
            	{
            		matrixL = new int[Integer.parseInt(rowsL.getText())][Integer.parseInt(columnsL.getText())];
            		matrixR = new int[Integer.parseInt(rowsR.getText())][Integer.parseInt(columnsR.getText())];
            		matrixResult = new int[Integer.parseInt(rowsL.getText())][Integer.parseInt(columnsR.getText())];
            		
            		resultPressed = false;
            		repaint();
            	}
            }
        };
		drawButton.addActionListener(actionListener);
		
		calculateMulti = new JButton("Multiply");
		calculateMulti.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {   
				  matrixResult = calculateProduct(matrixL, matrixR);
				  if(matrixResult != null) 
					  resultPressed = true;
          		  Panels.additionPanel.setFocusable(true);
          		  Panels.additionPanel.requestFocusInWindow();
          		  repaint();
			  }
			} );
		
		keyHandler = new MultiplicationKeyHandler();
		addKeyListener(keyHandler);
		
		toolBar.add(matrixButton);
		toolBar.add(l);
		toolBar.add(rowsL);
		toolBar.add(columnsL);
		toolBar.add(r);
		toolBar.add(rowsR);
		toolBar.add(columnsR);
		toolBar.add(drawButton);
		toolBar.add(calculateMulti);
		
		this.add(toolBar);
		addMouseListener(this);
	}
	
	protected static int[][] calculateProduct(int[][] matrixL, int[][] matrixR) {
		// TODO Auto-generated method stub
		int result[][] = new int[matrixL.length][matrixR[0].length];
		if(matrixL[0].length != matrixR.length)
		{
			Error.showError("Matrix dimensions are incorrect!");
			return null;
		}
		for(int i = 0; i < matrixL.length; i++)
		{
			for(int j = 0; j < matrixR[0].length; j++) {
				for(int k = 0; k < matrixR.length; k++) {
					result[i][j] += matrixL[i][k] * matrixR[k][j];
				}
				
			}
		}
		return result;
	}


	public void paintComponent(Graphics g1) 
	{
		super.paintComponent(g1);
		
		if(!rowsL.getText().equals("") && !columnsL.getText().equals("") && !rowsR.getText().equals("") && !columnsR.getText().equals(""))
		{
			Graphics2D g = (Graphics2D) g1;
			
			int squareLength;
			
			if(Integer.parseInt(rowsL.getText()) > Integer.parseInt(columnsL.getText()) + 2*Integer.parseInt(columnsR.getText()) + 8)
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / Integer.parseInt(rowsL.getText());
			else
				squareLength = Panels.WIDTH / (Integer.parseInt(columnsL.getText()) + 2*Integer.parseInt(columnsR.getText()) + 8);
			
			topLeftL.x = 2 * squareLength;	
			topLeftL.y = 2 * toolBar.getHeight();
				
			drawGridL(g, squareLength);
			drawNumbersL(g, squareLength);
			
			topLeftR.x = (4 + Integer.parseInt(columnsL.getText())) * squareLength;
			topLeftR.y = 2 * toolBar.getHeight();
			
			drawGridR(g, squareLength);
			drawNumbersR(g, squareLength);
			
			if(resultPressed)
			{
				topLeftResult.x = (6 + Integer.parseInt(columnsR.getText()) + Integer.parseInt(columnsL.getText())) * squareLength;
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

		for(int i = 0; i < Integer.parseInt(rowsL.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columnsL.getText()); j++)
			{
				g.drawString(Integer.toString(matrixL[i][j]), topLeftL.x + j * squareLength + squareLength / 2, 
						topLeftL.y + i * squareLength + squareLength / 2);
			}
		}
	}
	
	private void drawNumbersR(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		for(int i = 0; i < Integer.parseInt(rowsR.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columnsR.getText()); j++)
			{
				g.drawString(Integer.toString(matrixR[i][j]), topLeftR.x + j * squareLength + squareLength / 2, 
						topLeftR.y + i * squareLength + squareLength / 2);
			}
		}
	}
	
	private void drawNumbersResult(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub

		for(int i = 0; i < Integer.parseInt(rowsL.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(columnsR.getText()); j++)
			{
				g.drawString(Integer.toString(matrixResult[i][j]), topLeftResult.x + j * squareLength + squareLength / 2, 
						topLeftResult.y + i * squareLength + squareLength / 2);
			}
		}
	}

	private void drawGridL(Graphics2D g, int squareLength) {
		
		int height = squareLength * Integer.parseInt(rowsL.getText());
		int width = squareLength * Integer.parseInt(columnsL.getText());
		
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
		
		for(int i = 0; i < Integer.parseInt(columnsL.getText()); i++)
		{
			g.drawLine(topLeftL.x + i * squareLength, topLeftL.y, topLeftL.x + i * squareLength, topLeftL.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rowsL.getText()); i++)
		{
			g.drawLine(topLeftL.x, topLeftL.y + i * squareLength, topLeftL.x + width, topLeftL.y + i * squareLength);
		}
		
	}

	private void drawGridR(Graphics2D g, int squareLength) {
		
		int height = squareLength * Integer.parseInt(rowsR.getText());
		int width = squareLength * Integer.parseInt(columnsR.getText());
		
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
		
		for(int i = 0; i < Integer.parseInt(columnsR.getText()); i++)
		{
			g.drawLine(topLeftR.x + i * squareLength, topLeftR.y, topLeftR.x + i * squareLength, topLeftR.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rowsR.getText()); i++)
		{
			g.drawLine(topLeftR.x, topLeftR.y + i * squareLength, topLeftR.x + width, topLeftR.y + i * squareLength);
		}
	}
	
	private void drawGridResult(Graphics2D g, int squareLength) {
		
		int height = squareLength * Integer.parseInt(rowsL.getText());
		int width = squareLength * Integer.parseInt(columnsR.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeftResult.x, 2 * toolBar.getHeight(), width, height);
		
		for(int i = 0; i < Integer.parseInt(columnsR.getText()); i++)
		{
			g.drawLine(topLeftResult.x + i * squareLength, topLeftResult.y, topLeftResult.x + i * squareLength, topLeftResult.y + height);
		}
		for(int i = 0; i < Integer.parseInt(rowsL.getText()); i++)
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
		
		if(rowsL.getText().equals("")) return;
		
		if (e.getButton() == MouseEvent.BUTTON3) //right click
		{
			int squareLength;
			
			if(Integer.parseInt(rowsL.getText()) > Integer.parseInt(columnsL.getText()) + 2*Integer.parseInt(columnsR.getText()) + 8)
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / Integer.parseInt(rowsL.getText());
			else
				squareLength = Panels.WIDTH / (Integer.parseInt(columnsL.getText()) + 2*Integer.parseInt(columnsR.getText()) + 8);
			
			for(int i = 0; i < Integer.parseInt(rowsL.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columnsL.getText()); j++)
				{
					if(e.getX() >= topLeftL.x + j * squareLength
					&& e.getX() < topLeftL.x + (j+1) * squareLength
					&& e.getY() >= topLeftL.y + i * squareLength
					&& e.getY() < topLeftL.y + (i+1) * squareLength)
					{
						highlightedSquareL.x = j;
						highlightedSquareL.y = i;
						leftLastClicked = true;
						matrixL[j][i] = 0;
						repaint();
						return;
					}
				}
			}
			for(int i = 0; i < Integer.parseInt(rowsR.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columnsR.getText()); j++)
				{
					if(e.getX() >= topLeftR.x + j * squareLength
					&& e.getX() < topLeftR.x + (j+1) * squareLength
					&& e.getY() >= topLeftR.y + i * squareLength
					&& e.getY() < topLeftR.y + (i+1) * squareLength)
					{
						highlightedSquareR.x = j;
						highlightedSquareR.y = i;
						leftLastClicked = false;
						matrixR[j][i] = 0;
						repaint();
						return;
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1) //left click
		{
			int squareLength;
			
			if(Integer.parseInt(rowsL.getText()) > Integer.parseInt(columnsL.getText()) + 2*Integer.parseInt(columnsR.getText()) + 8)
				squareLength = (Panels.HEIGHT - toolBar.getHeight()) / Integer.parseInt(rowsL.getText());
			else
				squareLength = Panels.WIDTH / (Integer.parseInt(columnsL.getText()) + 2*Integer.parseInt(columnsR.getText()) + 8);
			
			for(int i = 0; i < Integer.parseInt(rowsL.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columnsL.getText()); j++)
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
			for(int i = 0; i < Integer.parseInt(rowsR.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(columnsR.getText()); j++)
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
			matrixL[highlightedSquareL.y][highlightedSquareL.x] = matrixL[highlightedSquareL.y][highlightedSquareL.x] * 10 + key;
		else
			matrixR[highlightedSquareR.y][highlightedSquareR.x] = matrixR[highlightedSquareR.y][highlightedSquareR.x] * 10 + key;
	}


}