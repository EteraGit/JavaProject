package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MPanel extends JPanel{

	public MPanel(){
		this.setBackground(Color.green);
		
		JButton h = new JButton("Home");
		h.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					System.out.println("Funkcije");
					hpanel();
			  } 
			} );
		
		this.add(h);
	}
	
		public void hpanel()
		{
			Panels.startPanel.remove(Panels.matricePanel);
			Panels.startPanel.add(Panels.mainPanel, BorderLayout.CENTER);
			Panels.startPanel.revalidate();
			Panels.startPanel.repaint();
		}
		
}
