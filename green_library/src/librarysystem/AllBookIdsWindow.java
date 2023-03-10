package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import business.Book;
import business.ControllerInterface;
import business.SystemController;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	//Singleton class
	private AllBookIdsWindow() {}
	
	public void init() {
		JPanel mainPanel;
		JPanel topPanel;
		JPanel middlePanel;
		JPanel lowerPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// defineTopPanel();
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Books");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);

		// defineMiddlePanel();
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);

		List<Book> ids = ci.allBookObj();
		String data[][] = new String[ids.size()][5];
		String column[]={"№","ISBN","Title","Authors", "Max Checkout Length"};
		for(int i=0; i<ids.size(); i++) {
			Book s = ids.get(i);
			data[i][0] = ""+(i+1);
			data[i][1] = s.getIsbn();
			data[i][2] = s.getTitle();
			data[i][3] = s.getAuthorsName();
			data[i][4] = ""+s.getMaxCheckoutLength();
		}
		JTable bookTable = new JTable(data,column);
		middlePanel.add(new JScrollPane(bookTable));
		// defineLowerPanel();
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
    		
		}
	}
	
	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
}
