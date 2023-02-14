package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MatrixPanel extends JPanel{

	public MatrixPanel(){
		this.setBackground(Color.green);
		
		JButton homeButton = new JButton("Home");
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
		
		JButton determinantButton = new JButton("Determinant");
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
		
		JButton transposingButton = new JButton("Transposing");
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
		
		JButton additionButton = new JButton("Addition");
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
		
		JButton multiplicationButton = new JButton("Multiplication");
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
		
		this.add(multiplicationButton);
		this.add(additionButton);
		this.add(transposingButton);
		this.add(determinantButton);
		this.add(homeButton);
	}		
}
