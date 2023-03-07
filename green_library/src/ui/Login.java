package ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * This version of MyFrame illustrates some initialization, including
 * centering on the desktop and configuring window closing. It does
 * not add any components to the content pane.
 *
 */
public class Login extends JFrame {

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login");
		setSize(320, 240); //pack is not a good choice here because there are no components yet
		//pack();
		centerFrameOnDesktop(this);
		setResizable(false);
	}

	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		int xpos = (width - frameWidth) / 2;
		int ypos = (height - frameHeight) / 3;
		f.setLocation(xpos, ypos);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable()
		{
			public void run() {
				Login mf = new Login();				
				mf.setVisible(true);
			}
		});
	}

	private static final long serialVersionUID = 3618976789175941431L;
}
