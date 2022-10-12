import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class LaunchPage implements ActionListener
{
    JPanel ParentPanel = new JPanel();
    JFrame frame = new JFrame();
    JButton matriceButton = new JButton("Matrice");
    JButton grafoviButton = new JButton("Grafovi");
    JButton numerickaButton = new JButton("Numericka");
 
    LaunchPage()
    {
        ParentPanel.setLayout(new BorderLayout(10, 10));

        AddButtons();

        ParentPanel.add(new StartingPanel());
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);

        frame.add(ParentPanel);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);  
    }

    public void AddButtons()
    {
        matriceButton.setBounds(100,40,200,30);
        matriceButton.setFocusable(false);
        matriceButton.addActionListener(this);

        grafoviButton.setBounds(400,40,200,30);
        grafoviButton.setFocusable(false);
        grafoviButton.addActionListener(this);

        numerickaButton.setBounds(700,40,200,30);
        numerickaButton.setFocusable(false);
        numerickaButton.addActionListener(this);
    
        ParentPanel.add(matriceButton);
        ParentPanel.add(grafoviButton);
        ParentPanel.add(numerickaButton);
    }

 @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == matriceButton) 
        {
            ParentPanel.removeAll();
            AddButtons();
            ParentPanel.add(new MatricePanel());
            ParentPanel.revalidate();
            ParentPanel.repaint();
        }
        else if(e.getSource() == grafoviButton)
        {
            ParentPanel.removeAll();
            AddButtons();
            ParentPanel.add(new GrafoviPanel());
            ParentPanel.revalidate();
            ParentPanel.repaint();
        }
        else if(e.getSource() == numerickaButton)
        {
            ParentPanel.removeAll();
            AddButtons();
            ParentPanel.add(new NumerickaPanel());
            ParentPanel.revalidate();
            ParentPanel.repaint();
        }
    }
}