package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

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
import javax.swing.text.NumberFormatter;
import javax.swing.*;
import java.awt.*;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import rulesets.RuleException;
import rulesets.RuleSet;
import rulesets.RuleSetFactory;

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

	private JTextField isbn;
	private JTextField title;
	private JList<Author> author_list;
	private String maxCheckoutLength="maxCheckoutLength";

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

		isbn = new JTextField(10);
        title = new JTextField(10);
		
		NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        JFormattedTextField countField = new JFormattedTextField(formatter);
        // countField.setText("1");

        JComboBox<String> rentType = new JComboBox<String>();
        rentType.addItem("7");
		rentType.addItem("21");

        List<Author> ids = ci.allAuthorsObj();
        DefaultListModel<Author> model = new DefaultListModel<>();
		System.out.println("Address Len:"+ids.size());
		for(Author s: ids) {
			model.addElement(s);
		}
        author_list = new JList<Author>(model);
        author_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        leftPanel.add(new JLabel("ISBN")); 
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(new JLabel("Title"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Max Checkout Length"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
        leftPanel.add(new JLabel("Copy Count"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,12)));
		leftPanel.add(new JLabel("Authors"));
        leftPanel.add(Box.createRigidArea(new Dimension(0,132)));
        
        rightPanel.add(isbn);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(rentType);
        rightPanel.add(Box.createRigidArea(new Dimension(0,8)));
        rightPanel.add(countField);
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
			int m = Integer.parseInt(rentType.getSelectedItem().toString());
			List<Author> authors = new ArrayList<Author>();
            if (author_list!=null){
                for (Author author: author_list.getSelectedValuesList()){
                    authors.add(author);
                }
            }
			String n = System.getProperty("line.separator");
			try {
				RuleSet rules = RuleSetFactory.getRuleSet(AddBookWindow.this);
				rules.applyRules(AddBookWindow.this);
				String output = i + n + t + n + m + n;
				System.out.println(output);
			
            	if (author_list!=null){
                	for (Author author: author_list.getSelectedValuesList()){
                		authors.add(author);
                	}
            	}
				System.out.println("MULTIPLE:"+authors);
				Book book = new Book(i, t, m, authors);
            	System.out.println("BOOOK:"+book);
				int len = Integer.parseInt(countField.getValue().toString());
				for (int ii=1; ii<len; ii++){
					book.addCopy();
				}
            	da.saveNewBook(book);

				List<Book> book_ids = ci.allBookObj();
				// AllBookIdsWindow.AddRowToJTable(new Object[]{
				// 	book_ids.size(),
				// 	book.getIsbn(),
				// 	book.getTitle(),
				// 	book.getAuthorsName(),
				// 	book.getMaxCheckoutLength(),
		 		// });
            	// clear
				isbn.setText(null);
				title.setText(null);
			} catch (RuleException e){
				JOptionPane.showMessageDialog(AddBookWindow.this, e.getMessage());
			}
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

	public String getIsbnValue() {
		return isbn.getText();
	}

	public String getTitleValue() {
		return title.getText();
	}

	public String getMaxLengthValue() {
		return maxCheckoutLength;
	}

	public int getAuthorsCount(){
		return author_list.getComponentCount();
	}
}
