package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import design.StylizedButton;
import design.StylizedLabel;

@SuppressWarnings("serial")
public class MatrixPanel extends JPanel{
	
	private int fontSize = 30;
	private Color backgroundColor = new Color(254, 230, 255);
	private Color homeButtonColor = new Color(252, 131, 164);
	StylizedLabel title = new StylizedLabel("Choose one: ", 30);

	public MatrixPanel(){
		this.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		this.setLayout(new GridLayout(5, 2, 10, 15));
		this.setBackground(new Color(191,191,255));
		
		StylizedButton homeButton = new StylizedButton("Home", fontSize,homeButtonColor, 2);
		
		homeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.mainPanel, BorderLayout.CENTER);
					Panels.mainPanel.setFocusable(true);
					Panels.mainPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		StylizedButton determinantButton = new StylizedButton("Determinant", fontSize, backgroundColor, 2);
		determinantButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.determinantPanel, BorderLayout.CENTER);
					Panels.determinantPanel.setFocusable(true);
					Panels.determinantPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
		});
		
		StylizedButton transposingButton = new StylizedButton("Transposing", fontSize, backgroundColor, 2);
		transposingButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.transposingPanel, BorderLayout.CENTER);
					Panels.transposingPanel.setFocusable(true);
					Panels.transposingPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
		});
		
		StylizedButton additionButton = new StylizedButton("Addition", fontSize, backgroundColor, 2);
		additionButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.additionPanel, BorderLayout.CENTER);
					Panels.additionPanel.setFocusable(true);
					Panels.additionPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
		});
		

		StylizedButton multiplicationButton = new StylizedButton("Multiplication", fontSize, backgroundColor, 2);
		multiplicationButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.multiplicationPanel, BorderLayout.CENTER);
					Panels.multiplicationPanel.setFocusable(true);
					Panels.multiplicationPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  }
		});
		StylizedButton subtractionButton = new StylizedButton("Subtraction", fontSize, backgroundColor, 2);
		subtractionButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.subtractionPanel, BorderLayout.CENTER);
					Panels.subtractionPanel.setFocusable(true);
					Panels.subtractionPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
		});
		
		StylizedButton inverseButton = new StylizedButton("Inverse", fontSize, backgroundColor, 2);
		inverseButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.inversePanel, BorderLayout.CENTER);
					Panels.inversePanel.setFocusable(true);
					Panels.inversePanel.requestFocusInWindow();

					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
		});
		

		StylizedButton exponentiationButton = new StylizedButton("Exponentiation", fontSize, backgroundColor, 2);
		exponentiationButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.matrixPanel);
					Panels.startPanel.add(Panels.exponentiationPanel, BorderLayout.CENTER);
					Panels.exponentiationPanel.setFocusable(true);
					Panels.exponentiationPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
		});
		
		
		
		this.add(title);
		this.add(new JLabel(""));
		this.add(homeButton);
		this.add(transposingButton);
		this.add(additionButton);
		this.add(subtractionButton);
		this.add(multiplicationButton);
		this.add(exponentiationButton);
		this.add(inverseButton);
		this.add(determinantButton);
		
	}		
}
