import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) throws Exception 
    {
        JLabel l1 = new JLabel("y");
        l1.setFont(new Font("Serif", Font.PLAIN, 30));
        l1.setBounds(260, 0, l1.getPreferredSize().width, l1.getPreferredSize().height);

        JLabel l2 = new JLabel("x");
        l2.setFont(new Font("Serif", Font.PLAIN, 30));
        l2.setBounds(450, 400, l2.getPreferredSize().width, l2.getPreferredSize().height);
        
        
        Panel panel = new Panel();
        System.out.println("Hello world!");
        JFrame Prozor = new JFrame();
        
        Prozor.setSize(500, 500);
        Prozor.setLocationRelativeTo(null);
        Prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Prozor.setResizable(false);
        Prozor.add(panel);
        Prozor.add(l1);
        Prozor.add(l2);
    
        Prozor.setVisible(true);


    }

}
