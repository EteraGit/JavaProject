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

@SuppressWarnings("serial")
public class DeterminantPanel extends JPanel implements MouseListener{
	
	JToolBar toolBar;
	JButton matrixButton;
	JTextField rows;
	JButton drawButton;
	JFrameTocka topLeft = new JFrameTocka(0,0);
	JFrameTocka highlightedSquare = new JFrameTocka(0,0);
	DeterminantKeyHandler keyHandler;
	JButton calculateDeterminant;
	JLabel result;
	int length;
	int[][] matrix;
	double offset = 3.3;
	
	public DeterminantPanel()
	{
		this.setBackground(Color.red);
		
		toolBar = new JToolBar();
		
		matrixButton = new JButton("Matrices");
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
		
		rows = new JTextField(10);	
		
		drawButton = new JButton("Draw Matrix");
		ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(!rows.getText().equals(""))
            	{
            		matrix = new int[Integer.parseInt(rows.getText())][Integer.parseInt(rows.getText())];
            		
            		repaint();
            	}
            }
        };
		drawButton.addActionListener(actionListener);
		
		calculateDeterminant = new JButton("Calculate Determinant");
		calculateDeterminant.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  result.setText(Integer.toString(calculateDetermiant(matrix)));
			  }
			} );
		
		result = new JLabel();
		
		keyHandler = new DeterminantKeyHandler();
		addKeyListener(keyHandler);
		
		toolBar.add(matrixButton);
		toolBar.add(rows);
		toolBar.add(drawButton);
		toolBar.add(calculateDeterminant);
		toolBar.add(result);
		
		this.add(toolBar);
		addMouseListener(this);
	}
	
	protected static int calculateDetermiant(int[][] matrix) {
		// TODO Auto-generated method stub
	    int det = 0;
	    if (matrix.length == 2) {
	        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
	    }
	    for (int i = 0; i < matrix.length; i++) {
	        int[][] subMatrix = createSubMatrix(matrix, i);
	        int subDet = calculateDetermiant(subMatrix);
	        det += (i % 2 == 0 ? 1 : -1) * matrix[0][i] * subDet;
	    }
	    return det;
	}
	
	protected static int[][] createSubMatrix(int[][] matrix, int excludedCol) {
	    int[][] subMatrix = new int[matrix.length - 1][matrix.length - 1];
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
			
			length = Panels.HEIGHT - (int) (offset * toolBar.getHeight()) - 
						(Panels.HEIGHT - (int) (offset * toolBar.getHeight())) % Integer.parseInt(rows.getText());
			topLeft.x = (Panels.WIDTH - length) / 2;
			topLeft.y = 2 * toolBar.getHeight();
			
			drawGrid(g, length / Integer.parseInt(rows.getText()));
			drawNumbers(g, length / Integer.parseInt(rows.getText()));
		}
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	private void drawNumbers(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
		{
			for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
			{
				g.drawString(Integer.toString(matrix[i][j]), topLeft.x + i * squareLength + squareLength / 2, 
															 topLeft.y + j * squareLength + squareLength / 2);
			}
		}
	}

	private void drawGrid(Graphics2D g, int squareLength) {
		// TODO Auto-generated method stub
		
		int height = Panels.HEIGHT - (int) (offset * toolBar.getHeight()) - 
				(Panels.HEIGHT - (int) (offset * toolBar.getHeight())) % Integer.parseInt(rows.getText());
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));
		g.drawRect(topLeft.x, 2 * toolBar.getHeight(), height, height);
		g.setColor(new Color(211,211,211));
		g.fillRect(topLeft.x + highlightedSquare.x * squareLength,
				   topLeft.y + highlightedSquare.y * squareLength, squareLength, squareLength);
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
			int squareLength = length / Integer.parseInt(rows.getText());
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
				{
					if(e.getX() >= topLeft.x + j * squareLength
					&& e.getX() < topLeft.x + (j+1) * squareLength
					&& e.getY() >= topLeft.y + i * squareLength
					&& e.getY() < topLeft.y + (i+1) * squareLength)
					{
						highlightedSquare.x = j;
						highlightedSquare.y = i;
						matrix[i][j] = 0;
						repaint();
						return;
					}
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1)
		{
			int squareLength = length / Integer.parseInt(rows.getText());
			
			for(int i = 0; i < Integer.parseInt(rows.getText()); i++)
			{
				for(int j = 0; j < Integer.parseInt(rows.getText()); j++)
				{
					if(e.getX() >= topLeft.x + j * squareLength
					&& e.getX() < topLeft.x + (j+1) * squareLength
					&& e.getY() >= topLeft.y + i * squareLength
					&& e.getY() < topLeft.y + (i+1) * squareLength)
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
		matrix[highlightedSquare.x][highlightedSquare.y] = matrix[highlightedSquare.x][highlightedSquare.y] * 10 + key;
	}
}
