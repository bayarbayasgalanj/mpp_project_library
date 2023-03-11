package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import business.Book;
import business.CheckoutRecord;
// import business.CheckoutRecordItem;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.DefaultListCellRenderer;
import javax.swing.*;
import java.awt.*;

public class checkDueDateWindow extends JFrame implements LibWindow{
    // private static final long serialVersionUID = 10000002L;
	public static final checkDueDateWindow INSTANCE = new checkDueDateWindow();
    private boolean isInitialized = false;

    public static DefaultTableModel model = new DefaultTableModel(new String[] { "Book", "Member", "Due Date" },0);
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
    JTextField memberId;
    @Override
    public void init() {
        model.setRowCount(0);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();

		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 45);
		middlePanel.setLayout(fl);
		// List<CheckoutRecordItem> crItems = CheckoutRecord.INSTANCE.getItemIds();
		// List<CheckoutRecordItem> crItems = CheckoutRecord.INSTANCE.getItemIds();
		List<CheckoutRecord> ids = da.readBookCopyRecords();
		System.out.println("CHECK LEN"+ids.size());
		JTable bookTable = new JTable(){
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
			   return false;
			}
		};
		if(ids!=null){
			for(CheckoutRecord cri: ids){
				model.addRow(new Object[]{ cri.getBook(), cri.getMember(), cri.getDueDate()});
			}
		}
		bookTable.setModel(model);
		bookTable.setRowSelectionAllowed(true);
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		middlePanel.add(new JScrollPane(bookTable));

		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        // mainPanel.add(lowerRightPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
    }

        public void defineTopPanel() {
            topPanel = new JPanel();
            JLabel AllIDsLabel = new JLabel("Due Checkout Records");
            Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
            topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            topPanel.add(AllIDsLabel);
    
            memberId = new JTextField(10);
    }
    @Override
    public boolean isInitialized() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInitialized'");
    }
    @Override
    public void isInitialized(boolean val) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInitialized'");
    }
    public void defineMiddlePanel() {
		
    }
    public class NumberedListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (c instanceof JLabel) {
                ((JLabel) c).setText((index + 1) + ". " + value);
            }
            return c;
        }
    }
    class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		}
	}

    public void defineLowerPanel() {
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);
	}
    
}
