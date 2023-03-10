package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class AddCheckoutWindow extends JFrame implements LibWindow 
{
    public static final AddCheckoutWindow INSTANCE = new AddCheckoutWindow();
	ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	private static final AtomicInteger count = new AtomicInteger(10000); 
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;
	private JPanel lowerPanel;
	
	public AddCheckoutWindow() {}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Checkout Order");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(AddBookLabel);
	}
	
	public void defineOuterMiddle() {
		outerMiddle = new JPanel();
		outerMiddle.setLayout(new BorderLayout());
		
		//set up left and right panels		
		JPanel middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
		middlePanel.setLayout(fl);
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
        JLabel checkoutLabel = new JLabel("Order Number");
		JTextField orderNumber;
		orderNumber = new JTextField(10);
		// String random = java.util.UUID.randomUUID().toString();
		String random = "" + count.incrementAndGet(); 
		orderNumber.setText(random);
		leftPanel.add(checkoutLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		
		rightPanel.add(orderNumber);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		
		JComboBox<BookCopy> bookCopyList = new JComboBox<BookCopy>();
        List<BookCopy> ids = ci.allBookCopyObj();
        for(BookCopy s: ids) {
            bookCopyList.addItem(s);
        }
		JTable jTable1 = new JTable();
		DefaultTableModel mod=new DefaultTableModel();
		mod.addColumn("Book");
		mod.addColumn("Quantity");
		mod.addColumn("Units");
		mod.addColumn("Amount");
		mod.addRow(new Object [][] {{null, null, null, null}});
		jTable1.setModel(mod);
		jTable1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(bookCopyList));
		// jTable1.setColumnSelectionAllowed(true);
		
		middlePanel.add(new JScrollPane(jTable1));
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);
		
		//add button at bottom
		JButton addBookButton = new JButton("Add Order");
		attachAddCheckoutButtonListener(addBookButton, orderNumber);
		JPanel addBookButtonPanel = new JPanel();
		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addBookButtonPanel.add(addBookButton);
		outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);
		
	}
    public void defineLowerPanel() {
		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
	}
	private void attachAddCheckoutButtonListener(JButton butn, JTextField orderNumber) {
		butn.addActionListener(evt -> {
			String oNumber = orderNumber.getText();
			CheckoutRecord.addRecord(oNumber);
			orderNumber.setText(null);
	    });
	}

	// @Override
    public void init() {
        mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
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
