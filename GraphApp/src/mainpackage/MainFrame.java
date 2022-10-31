package mainpackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame implements Runnable{

	private JFrame frame;
	private JPanel mainPanel;
	private final int FPS_SET = 120;
	private Thread thread;

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
		mainPanel = new Panel();
		mainPanel.requestFocus();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mainPanel);
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		System.out.println("FPS:");

		int frames = 0;
		long lastCheck = System.currentTimeMillis();

		while (true) {

			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {
				mainPanel.repaint();
				lastFrame = now;
				frames++;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		
	}

}
