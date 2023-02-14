package matrices;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import mainpackage.Panels;

public class InverseKeyHandler extends KeyAdapter{
	
	public int key;
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(Character.getNumericValue(event.getKeyChar()) >= 0 && Character.getNumericValue(event.getKeyChar()) < 10) 
		{
			key = Character.getNumericValue(event.getKeyChar());
			Panels.inversePanel.receiveKey(key);
		}
		else if(event.getKeyChar() == '-')
		{
			Panels.inversePanel.receiveKeyChar('-');
		}

		Panels.inversePanel.revalidate();
		Panels.inversePanel.repaint();
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