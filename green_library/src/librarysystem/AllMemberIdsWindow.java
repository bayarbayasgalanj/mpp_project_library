package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;


public class AllMemberIdsWindow extends JFrame implements LibWindow {
	public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
		
	private AllMemberIdsWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(65, 65));
		defineTopPanel();
		// defineMiddlePanel();
		TextArea textArea;
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 65, 35);
		middlePanel.setLayout(fl);
		textArea = new TextArea(10,30);
		
		List<LibraryMember> ids = ci.allMembersObs();
		String sb = "";
		for(LibraryMember s: ids) {
			sb += s.toString() + "\n";
		}
		System.out.println(sb.toString());
		textArea.setText(sb.toString());
		middlePanel.add(textArea);

		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);	
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Member IDs");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
	}
	
	// public void defineMiddlePanel() {
		
	// }
	
	public void defineLowerPanel() {
		lowerPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);
	}
	
	// public void setData(String data) {
	// 	textArea.setText(data);
	// }
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   LibrarySystem.INSTANCE.setVisible(true);
	    });
	}

	@Override
	public boolean isInitialized() {
		
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
		
	}
	
}


