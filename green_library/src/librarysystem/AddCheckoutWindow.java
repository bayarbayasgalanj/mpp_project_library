package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import business.*;
public class AddCheckoutWindow extends JFrame implements LibWindow {
    public static final AddCheckoutWindow INSTANCE = new AddCheckoutWindow();
	ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;
	private JPanel lowerPanel;
	
	public AddCheckoutWindow() {}

	public void defineOuterMiddle() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Checkout Order");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(AddBookLabel);
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		outerMiddle = new JPanel();
		outerMiddle.setLayout(new BorderLayout());

		JComboBox<Book> bookList = new JComboBox<Book>();
        List<Book> ids = ci.allBookObj();
        for(Book s: ids) { bookList.addItem(s);}

		JComboBox<LibraryMember> memberList = new JComboBox<LibraryMember>();
        List<LibraryMember> mem_ids = ci.allMembersObs();
        for(LibraryMember s: mem_ids) { memberList.addItem(s);}

		JComboBox<String> rentType = new JComboBox<String>();
        rentType.addItem("7");
		rentType.addItem("21");
		
		JPanel middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
        JTextField orderNumber;
		orderNumber = new JTextField(15);
		String random = java.util.UUID.randomUUID().toString().replace("-", "");
		orderNumber.setText(random);
		orderNumber.setEditable(false);
		leftPanel.add(new JLabel("Order Number"));
		leftPanel.add(Box.createRigidArea(new Dimension(0,22)));
		rightPanel.add(orderNumber);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		leftPanel.add(new JLabel("Select a Book"));
		leftPanel.add(Box.createRigidArea(new Dimension(0,22)));
		rightPanel.add(bookList);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		leftPanel.add(new JLabel("Due Date"));
		leftPanel.add(Box.createRigidArea(new Dimension(0,15)));
		rightPanel.add(rentType);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		leftPanel.add(new JLabel("Member"));
		leftPanel.add(Box.createRigidArea(new Dimension(0,32)));
		rightPanel.add(memberList);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		JTable bookTable = new JTable(){
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
			   return false;
			}
		};
		
		DefaultTableModel model = new DefaultTableModel(new String[] { "Book", "Due Date" },0);
		bookTable.setModel(model);
		bookTable.setRowSelectionAllowed(true);
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(30);

		JButton inputOrder = new JButton("Input Order");
		inputOrder.addActionListener(evt -> {
			Book bName = (Book) bookList.getSelectedItem();
			String rName = (String) rentType.getSelectedItem().toString();
			if(bName.getMaxCheckoutLength() < Integer.parseInt(rName)){
				JOptionPane.showMessageDialog(this,"This book: "+bName+" cannot rent this day "+rName+"!!");
			}else{
				model.addRow(new Object[]{ bName, rName});
			}
		});
		rightPanel.add(inputOrder);
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);
		
		middlePanel.add(new JScrollPane(bookTable));
		outerMiddle.add(middlePanel, BorderLayout.NORTH);
		//add button at bottom
		JButton addBookButton = new JButton("Add Order");
		addBookButton.addActionListener(evt -> {
			String oNumber = orderNumber.getText();
			LibraryMember mem = (LibraryMember)memberList.getSelectedItem();
			CheckoutRecord CR = new CheckoutRecord(oNumber, mem);
			for(int i=0; i<model.getRowCount(); i++){
				Book b = (Book)model.getValueAt(i, 0);
				int due_date = Integer.parseInt(model.getValueAt(i, 1).toString());
				BookCopy bc = b.getCopies()[0];
				CheckoutRecordItem oLine = new CheckoutRecordItem(CR, bc, due_date);
				CR.addOrderItems(oLine);
			}
			model.setRowCount(0);
			// for(int i=0; i<model.getRowCount(); i++){
			// 	model.removeRow(i);
			// }
			
	    });
		JButton deleteBookButton = new JButton("Delete Order Line");
		deleteBookButton.addActionListener(evt -> {
			if (bookTable.getSelectedRow()>-1){
				model.removeRow(bookTable.getSelectedRow());
			}
	    });
		JPanel addBookButtonPanel = new JPanel();
		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addBookButtonPanel.add(addBookButton);
		addBookButtonPanel.add(deleteBookButton);
		outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);
		
	}
    public void defineLowerPanel() {
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
	}
	
	// @Override
    public void init() {
        mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineOuterMiddle();
        defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(outerMiddle, BorderLayout.CENTER);
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
