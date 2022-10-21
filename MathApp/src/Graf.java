import javax.swing.JFrame;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;

public class Graf extends JPanel{

    public JPanel panel;
    public double[] x;
    public double[] f;
    public double scale = 100;

    public Graf(double[] X, double[] F) 
    {
        this.x = X;
        this.f = F;
        this.panel = new JPanel();
        this.panel.setBounds(0,0,500,500);
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)  g;

        for(int i = 0; i < x.length; i++)
        {
            if(i != x.length - 1) {
                Line2D lin=new Line2D.Double(x[i] * scale, f[i] * scale, x[i+1] * scale, f[i+1] * scale);
                g2.draw(lin);
            }
        }
    }
}
