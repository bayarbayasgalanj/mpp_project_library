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
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

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
	
	private JTextField streetField;
	private JTextField cityField;
	private JTextField stateField;
    private JTextField zipField;

	public void clearData() {
		streetField.setText("");
		cityField.setText("");
		stateField.setText("");	
        zipField.setText("");
	}
	
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
		attachAddAddressButtonListener(addBookButton);
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
	private void attachAddAddressButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String street = streetField.getText();
			String city= cityField.getText();
			String state = stateField.getText();
            String zip = zipField.getText();
            Address addr = new Address(street, city, state, zip);
			// addrs.add(new Address(street, city, state, zip));
            // DataAccessFacade.;	
            DataAccess da = new DataAccessFacade();
            da.saveNewAddress(addr);
            clearData();
            // Data.addBookTitle(title);
			// displayInfo("The book " + title + " has been added " +
			//    "to the collection!");			
		
	    });
	}

	public void updateData() {
		// nothing to do
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
        mainPanel.repaint();
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
