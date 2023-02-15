package design;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class StylizedToolbar extends JToolBar {

    public StylizedToolbar() {
    	
    	setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        setOrientation(JToolBar.HORIZONTAL);
        this.setPreferredSize(new Dimension(super.getWidth(), 26));
        setFloatable(false);
        setBackground(new Color(237,193,255));
        setBorderPainted(false);
        setRollover(true);
    }
}