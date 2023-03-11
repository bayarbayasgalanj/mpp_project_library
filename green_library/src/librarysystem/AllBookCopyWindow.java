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
import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;


public class AllBookCopyWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllBookCopyWindow INSTANCE = new AllBookCopyWindow();
    ControllerInterface ci = new SystemController();
	public static DefaultTableModel model = new DefaultTableModel(new String[] { "№","Book","Copy Number","Is Available" },0);
    private boolean isInitialized = false;
	
	//Singleton class
	private AllBookCopyWindow() {}
	
	public void init() {
		model.setRowCount(0);
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
		int i=0;
		for(BookCopy s: ids) {
			model.addRow(new Object[]{ ""+(i+1), s.getBook().toString(),""+ s.getCopyNum(), ""+s.isAvailable()});
			i++;
		}
		JTable bookTable = new JTable(){
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
			   return false;
			}
		};
		bookTable.setAutoCreateRowSorter(true); 
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
