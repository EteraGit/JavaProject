package design;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

@SuppressWarnings("serial")
public class StylizedTextArea extends JTextArea {

    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255); // set background color
    private static final Color TEXT_COLOR = new Color(80, 80, 80); // set text color
    private static final Font FONT = new Font("Arial", Font.PLAIN, 14); // set font
    private static final Border BORDER = BorderFactory.createLineBorder(new Color(74,20,78), 1); // set border

    public StylizedTextArea(int rows, int columns) {
        super(rows, columns);
        setLineWrap(true); 
        setWrapStyleWord(true); 
        setBackground(BACKGROUND_COLOR);
        setForeground(TEXT_COLOR);
        setFont(FONT); 
        setBorder(BORDER);

        
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // set the scroll pane as the content pane of this text area
        setLayout(new BorderLayout());
        //add(scrollPane, BorderLayout.CENTER);
    }
}
