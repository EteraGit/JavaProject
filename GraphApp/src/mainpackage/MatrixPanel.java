package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import matrices.DeterminantKeyHandler;

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
		
		this.add(determinantButton);
		this.add(homeButton);
	}		
}
