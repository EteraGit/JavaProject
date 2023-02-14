package matrices;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import mainpackage.Panels;

public class AdditionKeyHandler extends KeyAdapter{
	
	public int key;
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(Character.getNumericValue(event.getKeyChar()) < 10) key = Character.getNumericValue(event.getKeyChar());
		Panels.additionPanel.receiveKey(key);
		Panels.additionPanel.revalidate();
		Panels.additionPanel.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		
	}
	
	@Override
	public void keyTyped(KeyEvent event)
	{

	}

}