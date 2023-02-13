package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import matrices.DeterminantPanel;

import javax.swing.JButton;



public class MainFrame implements Runnable{

	private JFrame frame;
	private final int WIDTH = 600;
	private final int HEIGHT = 400;
	private final int FPS_SET = 120;
	private Thread thread;
	private JButton funkcijeButton;
	private JButton matriceButton;

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
		Panels.functionsPanel = new FunctionPanel();	
		Panels.matrixPanel = new MatrixPanel();
		Panels.determinantPanel = new DeterminantPanel();
		
		funkcijeButton = new JButton("Funkcije");
		matriceButton = new JButton("Matrice");
		
		funkcijeButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.mainPanel);
					Panels.startPanel.add(Panels.functionsPanel, BorderLayout.CENTER);
					Panels.functionsPanel.setFocusable(true);
					Panels.functionsPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		matriceButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					Panels.startPanel.remove(Panels.mainPanel);
					Panels.startPanel.add(Panels.matrixPanel, BorderLayout.CENTER);
					Panels.matrixPanel.setFocusable(true);
					Panels.matrixPanel.requestFocusInWindow();
					Panels.startPanel.revalidate();
					Panels.startPanel.repaint();
			  } 
			} );
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panels.startPanel.add(Panels.mainPanel, BorderLayout.CENTER);
		Panels.mainPanel.add(funkcijeButton);
		Panels.mainPanel.add(matriceButton);
		
		
		frame.add(Panels.startPanel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addComponentListener(new ComponentAdapter() {
		    @SuppressWarnings("static-access")
			public void componentResized(ComponentEvent componentEvent) {
		    	if(componentEvent.getID() == componentEvent.COMPONENT_RESIZED)
		    	{
		    		Panels.WIDTH = frame.getSize().width;
		    		Panels.HEIGHT = frame.getSize().height;
		    	}
		    }
		});
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		
		long lastCheck = System.currentTimeMillis();

		while (true) {

			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {
				Panels.functionsPanel.repaint();
				lastFrame = now;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
			}
		}
		
	}

}
