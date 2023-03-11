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

import rulesets.RuleException;
import rulesets.RuleSet;
import rulesets.RuleSetFactory;

public class AddMemberWindow extends JFrame implements LibWindow 
{
    public static final AddMemberWindow INSTANCE = new AddMemberWindow();
	ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;
	private JPanel lowerPanel;
	
	public AddMemberWindow() {}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Members");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(AddBookLabel);
	}
	
	public void defineOuterMiddle() {
		outerMiddle = new JPanel();
		outerMiddle.setLayout(new BorderLayout());
		
		JTextField firstName;
		JTextField lastName;
		JTextField telephone;
		JComboBox<Address> address_list = new JComboBox<Address>();
		JTextField memberId;
		
		//set up left and right panels		
		JPanel middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 65, 25);
		middlePanel.setLayout(fl);
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
		firstName = new JTextField(10);
        lastName = new JTextField(10);
        telephone = new JTextField(10);
        memberId = new JTextField(10);

        List<Address> ids = ci.allAddressObj();
        for(Address s: ids) {
            String ss = s.toString();
            address_list.addItem(s);
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
        leftPanel.add(new JLabel("Member ID"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        
        rightPanel.add(firstName);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(lastName);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(telephone);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(address_list);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(memberId);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);
		
		//add button at bottom
		JButton addBookButton = new JButton("Add Member");
        addBookButton.addActionListener(evt -> {
			DataAccess da = new DataAccessFacade();
			String fName = firstName.getText();
			String lName = lastName.getText();
			String phone = telephone.getText();
			Address addr = (Address) address_list.getSelectedItem();
			System.out.println("--------"+addr);
			String memId = memberId.getText();
            String n = System.getProperty("line.separator");

			if(fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || memId.isEmpty()){
				JOptionPane.showMessageDialog(AddMemberWindow.this, "All fields must be non-empty");
			} else if(phone.length() != 10){
				JOptionPane.showMessageDialog(AddMemberWindow.this, "Telephone must be 10 digits.");
			} else {
				String output = fName + n + lName + n + phone + n + addr + n + memId;
				System.out.println(output);
				
				LibraryMember member = da.getMemberById(memId);
				boolean bl = false;
				if (member!=null){
					bl = true;
					member.updateMember(memId, fName, lName, phone, addr);
					da.updateMember(member);
				}else{
					member = new LibraryMember(memId, fName, lName, phone, addr);
					da.saveNewMember(member);
				}
				// clear
				firstName.setText(null);
				lastName.setText(null);
				telephone.setText(null);
				// address.setText(null);
				memberId.setText(null);
				if (bl){
					JOptionPane.showMessageDialog(this, "This member UDPATED: "+ member);
				}else{
					JOptionPane.showMessageDialog(this, "This member ADDED: "+ member);
				}
			}
			// try {
			// 	RuleSet rules = RuleSetFactory.getRuleSet(AddMemberWindow.this);
			// 	rules.applyRules(AddMemberWindow.this);
			// 	String output = fName + n + lName + n + phone + n + addr + n + memId;
			// 	System.out.println(output);
				
			// 	LibraryMember member = da.getMemberById(memId);
			// 	boolean bl = false;
			// 	if (member!=null){
			// 		bl = true;
			// 		member.updateMember(memId, fName, lName, phone, addr);
			// 		da.updateMember(member);
			// 	}else{
			// 		member = new LibraryMember(memId, fName, lName, phone, addr);
			// 		da.saveNewMember(member);
			// 	}
			// 	// clear
			// 	firstName.setText(null);
			// 	lastName.setText(null);
			// 	telephone.setText(null);
			// 	// address.setText(null);
			// 	memberId.setText(null);
			// 	if (bl){
			// 		JOptionPane.showMessageDialog(this, "This member UDPATED: "+ member);
			// 	}else{
			// 		JOptionPane.showMessageDialog(this, "This member ADDED: "+ member);
			// 	}
			// } catch (RuleException e){
			// 	JOptionPane.showMessageDialog(AddMemberWindow.this, e.getMessage());
			// }
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

	public String getFirstnameValue() {
		return "firstName.getText()";
	}

	public String getLastnameString() {
		return "lastName.getText()";
	}

	public String getTelephoneValue() {
		return "1234567890";
	}

	public String getMemberIdValue() {
		return "memberId.getText()";
	}
}
