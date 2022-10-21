import javax.swing.JFrame;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;

class Panel extends JPanel{

    public Panel()
    {
        //JPanel panel = new JPanel();
        
        //setSize(500, 500);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2=(Graphics2D)  g;
        //g2.setFont(new Font("Helvetica", Font.PLAIN, 20));
        //g2.drawString("x", 465, 250);
        Line2D lin=new Line2D.Float(250, 0, 250, 500 );
        g2.draw(lin);

        
        Line2D lin2=new Line2D.Float(0, 250, 500, 250);
        g2.draw(lin2);  
    }

    
}