import javax.swing.*;
import java.awt.*;

public class StartingPanel extends JPanel
{
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.drawRect(10,100,970,450);
    }
}
