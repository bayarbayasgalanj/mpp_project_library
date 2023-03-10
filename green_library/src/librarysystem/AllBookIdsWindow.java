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
	private JPanel middlePanel;
	private static final long serialVersionUID = 1L;
	public static final AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	public static DefaultTableModel tableModel;
	public static JTable table;
	
	//Singleton class
	private AllBookIdsWindow() {}

     
	
	public void init() {
		JPanel mainPanel;
		JPanel topPanel;
		JPanel lowerPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Books");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
		
		middlePanel();
		// 


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

	public void middlePanel() {
		middlePanel = new JPanel();
		middlePanel.revalidate();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		List<Book> ids = ci.allBookObj();
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		String column[]={"№","ISBN","Title","Authors", "Max Checkout Length"};
		tableModel.setColumnIdentifiers(column);
		System.out.println("Address Len:"+ids.size());
		tableModel.setRowCount(0);
		String data[][] = new String[ids.size()][5];
		for(int i=0; i<ids.size(); i++) {
			Book s = ids.get(i);
			data[i][0] = ""+(i+1);
			data[i][1] = s.getIsbn();
			data[i][2] = s.getTitle();
			data[i][3] = s.getAuthorsName();
			data[i][4] = ""+s.getMaxCheckoutLength();
		}
		for(int i=0; i<ids.size(); i++) {
			AddRowToJTable(data[i]);
		}
		table.setRowSelectionAllowed(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(3);
		middlePanel.add(new JScrollPane(table));
		
	}

	public static void AddRowToJTable(Object[] dataRow){
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.addRow(dataRow);
		// System.out.println("okee"+dataRow);
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
