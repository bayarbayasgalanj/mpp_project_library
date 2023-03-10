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
import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;


public class AllBookCopyWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookCopyWindow INSTANCE = new AllBookCopyWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	//Singleton class
	private AllBookCopyWindow() {}
	
	public void init() {
		JPanel mainPanel;
		JPanel topPanel;
		JPanel middlePanel;
		JPanel lowerPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		// defineTopPanel();
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Book copies");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);

		// defineMiddlePanel();
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);

		List<BookCopy> ids = ci.allBookCopyObj();
		String data[][] = new String[ids.size()][4];
		String column[]={"№","Book","Copy Number","Is Available"};
		for(int i=0; i<ids.size(); i++) {
			BookCopy s = ids.get(i);
			data[i][0] = ""+(i+1);
			data[i][1] = s.getBook().toString();
			data[i][2] = ""+s.getCopyNum();
			data[i][3] = ""+s.isAvailable();
		}
		JTable bookTable = new JTable(data,column){
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
			   return false;
			}
		};
		bookTable.setRowSelectionAllowed(true);
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(3);
		bookTable.getColumnModel().getColumn(1).setPreferredWidth(10);
		bookTable.getColumnModel().getColumn(2).setPreferredWidth(3);
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
