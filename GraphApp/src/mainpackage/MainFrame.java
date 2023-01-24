package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;



public class MainFrame implements Runnable{

	private JFrame frame;
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	private final int FPS_SET = 120;
	private Thread thread;
	private JButton f;
	private JButton m;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();		
		Panels.startPanel = new JPanel();
		Panels.startPanel.setLayout(new BorderLayout(10, 10));	
		Panels.mainPanel = new JPanel();
		Panels.mainPanel.setBackground(new Color(255,192,203));	
		Panels.funkcijePanel = new Panel(WIDTH, HEIGHT);	
		Panels.matricePanel = new MPanel();
		
		f = new JButton("Funkcije");
		m = new JButton("Matrice");
		
		f.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.mainPanel);
					Panels.startPanel.add(Panels.funkcijePanel, BorderLayout.CENTER);
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		m.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.mainPanel);
					Panels.startPanel.add(Panels.matricePanel, BorderLayout.CENTER);
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panels.startPanel.add(Panels.mainPanel, BorderLayout.CENTER);
		Panels.mainPanel.add(f);
		Panels.mainPanel.add(m);
		
		frame.add(Panels.startPanel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		//System.out.println("FPS:");

		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		while (true) {

			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {
				Panels.funkcijePanel.repaint();
				lastFrame = now;
				frames++;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		
	}

}
