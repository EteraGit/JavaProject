package mainpackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import design.StylizedButton;
import matrices.AdditionPanel;
import matrices.DeterminantPanel;
import matrices.ExponentiationPanel;
import matrices.InversePanel;
import matrices.SubtractionPanel;
import matrices.TransposingPanel;
import matrices.MultiplicationPanel;

import javax.swing.BorderFactory;



public class MainFrame implements Runnable{

	private JFrame frame;
	private final int WIDTH = 888;
	private final int HEIGHT = 500;
	private final int FPS_SET = 120;
	private Thread thread;
	private StylizedButton funkcijeButton;
	private StylizedButton matriceButton;
	private JLabel title;

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
		Panels.mainPanel.setLayout(new BorderLayout(0,0));
		Panels.mainPanel.setBackground(new Color(234,186,238));	
		Panels.functionsPanel = new FunctionPanel();	
		Panels.matrixPanel = new MatrixPanel();
		Panels.determinantPanel = new DeterminantPanel();
		Panels.transposingPanel = new TransposingPanel();
		Panels.additionPanel = new AdditionPanel();
		Panels.multiplicationPanel = new MultiplicationPanel();
		Panels.subtractionPanel = new SubtractionPanel();
		Panels.inversePanel = new InversePanel();
		Panels.exponentiationPanel = new ExponentiationPanel();
		
		Panels.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		
		Font  fnaslov  = new Font(Font.SANS_SERIF,  Font.BOLD, 90);
		Color buttonColor = new Color(254, 230, 255);
		title = new JLabel("MathApp");
		title.setFont(fnaslov);
		title.setHorizontalAlignment(JLabel.CENTER);
		
		
		funkcijeButton = new StylizedButton("Functions", 25,buttonColor, 3 );
		funkcijeButton.setPreferredSize(new Dimension(200, 200));
		
		matriceButton = new StylizedButton("Matrices", 25, buttonColor, 3);
		matriceButton.setPreferredSize(new Dimension(200, 200));
		
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
		Panels.mainPanel.add(title, BorderLayout.PAGE_START);
		Panels.mainPanel.add(funkcijeButton, BorderLayout.LINE_START);
		Panels.mainPanel.add(matriceButton, BorderLayout.LINE_END);
		
		
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
