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
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.ControllerInterface;
import business.SystemController;


public class AllBookIdsWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	public static DefaultTableModel model = new DefaultTableModel(new String[] { "№","ISBN","Title","Authors", "Max Checkout Length" },0);
	//Singleton class
	private AllBookIdsWindow() {}
	
	public void init() {
		model = new DefaultTableModel(new String[] { "№","ISBN","Title","Authors", "Max Checkout Length" },0);
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
		int i=0;
		for(Book s: ids) {
			model.addRow(new Object[]{ ""+(i+1), s.getIsbn(), s.getTitle(), s.getAuthorsName(), ""+s.getMaxCheckoutLength()});
			i++;
		}
		JTable bookTable = new JTable(){
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
			   return false;
			}
		};
		bookTable.setModel(model);
		bookTable.setRowSelectionAllowed(true);
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(30);
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
