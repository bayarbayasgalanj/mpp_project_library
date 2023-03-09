package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.*;
import java.awt.*;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class AddAuthorWindow extends JFrame implements LibWindow 
{
    public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
	ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;
	private JPanel lowerPanel;
	
	public AddAuthorWindow() {}

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
		JTextField firstName;
        JTextField lastName;
        JTextField telephone;
        JComboBox<String> address_list = new JComboBox<String>();
        JTextField bio;
        
		firstName = new JTextField(10);
        lastName = new JTextField(10);
        telephone = new JTextField(10);
        bio = new JTextField(10);

        List<Address> ids = ci.allAddressObj();
        
        List<String> choices = new ArrayList<String>();
        for(Address s: ids) {
            String ss = s.toString();
            choices.add(ss);
            address_list.addItem(ss);
        }
        // address_list
		// orderNumber.setText("ttt");
		leftPanel.add(new JLabel("First Name")); 
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(new JLabel("Last Name"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Telephone"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Address"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Bio"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        
        rightPanel.add(firstName);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(lastName);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(telephone);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(address_list);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(bio);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);
		
		//add button at bottom
		JButton addBookButton = new JButton("Add Order");
        addBookButton.addActionListener(evt -> {
			String fName = firstName.getText();
			String lName = lastName.getText();
			String phone = telephone.getText();
			// String addr = address.getText();
            String bi = bio.getText();
            Author author = new Author(fName, lName, phone, null, bi);
			DataAccess da = new DataAccessFacade();
            da.saveNewAuthor(author);
            // clear
			firstName.setText(null);
			lastName.setText(null);
			telephone.setText(null);
			// address.setText(null);
            bio.setText(null);
	    });
		JPanel addBookButtonPanel = new JPanel();
		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addBookButtonPanel.add(addBookButton);
		outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);
		
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
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));;
		lowerPanel.add(backToMainButn);
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
