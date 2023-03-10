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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import rulesets.RuleException;
import rulesets.RuleSet;
import rulesets.RuleSetFactory;

public class AddAddressWindow extends JFrame implements LibWindow {
    public static final AddAddressWindow INSTANCE = new AddAddressWindow();
    // ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;
	private JPanel lowerPanel;

	JTextField streetField;
	JTextField cityField;
	JTextField stateField;
	JTextField zipField;
	
	public AddAddressWindow() {}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Add Address");
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
		
        // String street, String city, String state, String zip

		JLabel streetLabel = new JLabel("Street");
		JLabel cityLabel = new JLabel("City");
		JLabel stateLabel = new JLabel("State");
        JLabel zipLabel = new JLabel("Zip");
		
		streetField = new JTextField(10);
		cityField = new JTextField(10);
		stateField = new JTextField(10);
        zipField = new JTextField(10);
		
		leftPanel.add(streetLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(cityLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(stateLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(zipLabel);
		
		
		rightPanel.add(streetField);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		rightPanel.add(cityField);
		rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		rightPanel.add(stateField);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
		rightPanel.add(zipField);
		
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);
		
		//add button at bottom
		JButton addBookButton = new JButton("Add Address");
		addBookButton.addActionListener(evt -> {
			String street = streetField.getText();
			String city= cityField.getText();
			String state = stateField.getText();
            String zip = zipField.getText();

			if(street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty()){
				JOptionPane.showMessageDialog(AddAddressWindow.this, "All fields must be non empty");
			} else if(zip.length() != 5 || Integer.parseInt(zip) <= 0){
				JOptionPane.showMessageDialog(AddAddressWindow.this, "Zip must be numeric or digits must be 5");
			} else {
				System.out.println("------Add address------\n" + "Street: " + street + " City: " + city + " State: " + state + " zip: " + zip);
				Address addr = new Address(street, city, state, zip);
				DataAccess da = new DataAccessFacade();
				da.saveNewAddress(addr);
				// clear
				streetField.setText(null);
				cityField.setText(null);
				stateField.setText(null);
				zipField.setText(null);
			}
			// try {
			// 	if(street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty()){
			// 		throw new RuleException("All fields must be non empty");
			// 	}
			// 	RuleSet rules = RuleSetFactory.getRuleSet(AddAddressWindow.this);
			// 	rules.applyRules(AddAddressWindow.this);
			// 	String output = street + ", " + city + ", " + state + ", " + zip;
			// 	System.out.println("------Add address------\n" + "Street: " + street + " City: " + city + " State: " + state + " zip: " + zip);
				
			// 	Address addr = new Address(street, city, state, zip);
			// 	DataAccess da = new DataAccessFacade();
			// 	da.saveNewAddress(addr);
			// 	// clear
			// 	streetField.setText(null);
			// 	cityField.setText(null);
			// 	stateField.setText(null);
			// 	zipField.setText(null);
			// } catch (RuleException e){
			// 	JOptionPane.showMessageDialog(AddAddressWindow.this, e.getMessage());
			// }
	    });

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
	
	@Override
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

	public String getStreetValue(){
		return streetField.getText();
	}

	public String getCityValue(){
		return cityField.getText();
	}

	public String getStateValue(){
		return stateField.getText();
	}

	public String getZipValue(){
		return zipField.getText();
	}
}
