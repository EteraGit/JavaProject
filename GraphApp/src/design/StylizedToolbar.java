package design;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class StylizedToolbar extends JToolBar {

    public StylizedToolbar() {
    	
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        setOrientation(JToolBar.HORIZONTAL);
        this.setPreferredSize(new Dimension(super.getWidth(), 26));
        setFloatable(false);
        setBackground(new Color(237,193,255));
        setBorderPainted(false);
        setRollover(true);
    }
}