package matrices;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import mainpackage.Panels;

public class ExponentiationKeyHandler extends KeyAdapter{
	
	public int key;
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(Character.getNumericValue(event.getKeyChar()) >= 0 && Character.getNumericValue(event.getKeyChar()) < 10) 
		{
			key = Character.getNumericValue(event.getKeyChar());
			Panels.exponentiationPanel.receiveKey(key);
		}
		else if(event.getKeyChar() == '-')
		{
			Panels.exponentiationPanel.receiveKeyChar('-');
		}

		Panels.exponentiationPanel.revalidate();
		Panels.exponentiationPanel.repaint();
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