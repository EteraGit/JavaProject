import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) throws Exception 
    {
        JLabel l1 = new JLabel("y");
        l1.setOpaque(true);
        l1.setFont(new Font("Serif", Font.PLAIN, 30));
        l1.setBounds(260, 0, l1.getPreferredSize().width, l1.getPreferredSize().height);

        JLabel l2 = new JLabel("x");
        l2.setOpaque(true);
        l2.setFont(new Font("Serif", Font.PLAIN, 30));
        l2.setBounds(450, 250, 30, 30);
        
        
        JPanel panel = new JPanel() {
            public void paint(Graphics g) {
                super.paintComponent(g);
                g.drawLine(0, 0, 20, 35);
            }
        };

        JLayeredPane lay = new JLayeredPane();
        lay.setBounds(0,0,500,500);
        lay.add(l1, Integer.valueOf(1));
        lay.add(l2, Integer.valueOf(1));
        lay.add(panel, Integer.valueOf(0));

        System.out.println("Hello world!");
        JFrame Prozor = new JFrame();

        double[] x = {0,1,2,3,4,5};

        double[] f = {0,0.5,3.2,3.6,4.1,4.9};

        Graf G = new Graf(x, f);

        Prozor.add(G);
        
        Prozor.setSize(500, 500);
        Prozor.setLocationRelativeTo(null);
        Prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Prozor.setResizable(false);
        

    
        Prozor.setVisible(true);
    }

}
