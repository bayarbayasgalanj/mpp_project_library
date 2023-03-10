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

public class AddBookWindow extends JFrame implements LibWindow 
{
    public static final AddBookWindow INSTANCE = new AddBookWindow();
	ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

	public JPanel getMainPanel() {
		return mainPanel;
	}
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel outerMiddle;
	private JPanel lowerPanel;
	
	public AddBookWindow() {}

	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Add Book");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(AddBookLabel);
	}
	
	public void defineOuterMiddle() {
		outerMiddle = new JPanel();
		outerMiddle.setLayout(new BorderLayout());
		
		//set up left and right panels		
		JPanel middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 65, 25);
		middlePanel.setLayout(fl);
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
        // private List<Author> authors;
        // private String isbn;
        // private String title;
        // private int maxCheckoutLength;

        JTextField isbn;
        JTextField title;
        JTextField maxCheckoutLength;
        JList<String> author_list;
		isbn = new JTextField(10);
        title = new JTextField(10);
        maxCheckoutLength = new JTextField(10);
        maxCheckoutLength.setText("1");
        List<Author> ids = ci.allAuthorsObj();
        DefaultListModel<String> model = new DefaultListModel<>();
		System.out.println("Address Len:"+ids.size());
		for(Author s: ids) {
			String ss = s.toString();
			model.addElement(ss);
		}
        author_list = new JList<String>(model);
        author_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // address_list
		// orderNumber.setText("ttt");
		leftPanel.add(new JLabel("ISBN")); 
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(new JLabel("Title"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Max Checkout Length"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Authors"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        
        rightPanel.add(isbn);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(maxCheckoutLength);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(new JScrollPane(author_list));
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);
		
		//add button at bottom
		JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(evt -> {
			DataAccess da = new DataAccessFacade();
			String i = isbn.getText();
			String t = title.getText();
			int m = Integer.parseInt(maxCheckoutLength.getText());
            
			List<Author> authors = new ArrayList<Author>();
            if (author_list!=null){
                for (String s: author_list.getSelectedValuesList()){
                    System.out.println("++++++:++++++"+s);
                    Author author = da.getAuthorByKeyObj(s);
                    if (author!=null){
                        authors.add(author);
                    }
                }
            }
			System.out.println("MULTIPLE:"+authors);
            
			Book book = new Book(i, t, m, authors);
            System.out.println("BOOOK:"+book);
            da.saveNewBook(book);
            // clear
			isbn.setText(null);
			title.setText(null);
			maxCheckoutLength.setText("1");
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
