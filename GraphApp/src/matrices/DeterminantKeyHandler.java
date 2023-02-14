package matrices;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import mainpackage.Panels;


public class DeterminantKeyHandler extends KeyAdapter{
	
	public int key;
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(Character.getNumericValue(event.getKeyChar()) < 10) key = Character.getNumericValue(event.getKeyChar());
		Panels.determinantPanel.receiveKey(key);
		Panels.determinantPanel.revalidate();
		Panels.determinantPanel.repaint();
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
