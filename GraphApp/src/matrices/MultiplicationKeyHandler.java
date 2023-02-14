package matrices;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import mainpackage.Panels;

public class MultiplicationKeyHandler extends KeyAdapter{
	
	public int key;
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(Character.getNumericValue(event.getKeyChar()) >= 0 && Character.getNumericValue(event.getKeyChar()) < 10) 
		{
			key = Character.getNumericValue(event.getKeyChar());
			Panels.multiplicationPanel.receiveKey(key);
		}
		else if(event.getKeyChar() == '-')
		{
			Panels.multiplicationPanel.receiveKeyChar('-');
		}

		Panels.multiplicationPanel.revalidate();
		Panels.multiplicationPanel.repaint();
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
