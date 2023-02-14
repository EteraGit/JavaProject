package matrices;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import mainpackage.Panels;

public class SubtractionKeyHandler extends KeyAdapter{
	
	public int key;
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(Character.getNumericValue(event.getKeyChar()) < 10) key = Character.getNumericValue(event.getKeyChar());
		Panels.subtractionPanel.receiveKey(key);
		Panels.subtractionPanel.revalidate();
		Panels.subtractionPanel.repaint();
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