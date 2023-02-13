package mainpackage;

import javax.swing.JOptionPane;

public class Error {
	public static void showError(String errorMessage)
	{
		JOptionPane.showMessageDialog(null, errorMessage, "", JOptionPane.INFORMATION_MESSAGE);
	}
}
