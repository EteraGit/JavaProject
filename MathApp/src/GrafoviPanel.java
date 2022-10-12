import javax.swing.*;
import java.awt.*;


public class GrafoviPanel extends JPanel 
{
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.green);
        g.fillRect(10,100,970,450);
    }
}
