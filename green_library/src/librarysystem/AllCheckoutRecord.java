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


public class AllCheckoutRecord extends JFrame implements LibWindow {
	private static final long serialVersionUID = 10000002L;
	public static final AllCheckoutRecord INSTANCE = new AllCheckoutRecord();
    // private static final String MEMBER_ID = "Member ID";
    ControllerInterface ci = new SystemController();
    DataAccess da = new DataAccessFacade();
	Book book;
    private boolean isInitialized = false;
	public static DefaultTableModel model = new DefaultTableModel(new String[] { "Book", "Member", "Due Date" },0);
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;

	JTextField memberId;
    
	//Singleton class
	private AllCheckoutRecord() {}
	
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
		List<CheckoutRecord> ids = ci.allCheckoutRecordObj();
		
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
		JLabel AllIDsLabel = new JLabel("Checkout Records");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);

		memberId = new JTextField(10);
		
		topPanel.add(new JLabel("Member id")); 
        topPanel.add(Box.createRigidArea(new Dimension(0,12)));

		topPanel.add(memberId);
        topPanel.add(Box.createRigidArea(new Dimension(0,8)));

		JButton searcbtn = new JButton("Search");
        searcbtn.addActionListener(evt -> {
			if (memberId.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Please fill all the fields!");
                // Validation: Member ID exists
            else if (!ci.allMemberIds().contains(memberId.getText()))
                JOptionPane.showMessageDialog(null, "Member ID does not exist!");
            else {
                LibraryMember member = ci.getMember(memberId.getText());
//                System.out.println(member);

                // Create: CheckoutRecord
                List<CheckoutRecord> uRecords = da.readUserRecords(member);
                model.setRowCount(0);
                for (CheckoutRecord cri : uRecords) {
                    model.addRow(new Object[]{ cri.getBook(), cri.getMember(), cri.getDueDate()});
                }
            }
		});
		topPanel.add(searcbtn);

		JButton duebtn = new JButton("Check due date");
		duebtn.addActionListener(evt -> {
            List<CheckoutRecord> record = da.readBookCopyRecords();
			model.setRowCount(0);
            for (CheckoutRecord cri : record) {
                model.addRow(new Object[]{ cri.getBook(), cri.getMember(), cri.getDueDate()});
            }
		});
		topPanel.add(duebtn);
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

	public void defineLowerPanel() {
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);
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
