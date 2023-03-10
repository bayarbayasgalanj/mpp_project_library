package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;


public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE =new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
    JMenu options;
	JMenu book_menus;
	JMenu author_menus;
	JMenu checkout_menus;
	JMenu member_menus;
	JMenu address_menus;
	JMenuItem book_copy_view,add_book_menu, author_menu, add_author_menu, address_menu, add_address_menu, login, allBookIds, allMemberIds, add_member_menu, checkout_menu, checkout_record, book_copy; 
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE,
		AllAddressWindow.INSTANCE,
		AddAddressWindow.INSTANCE,
		AllCheckoutRecord.INSTANCE,
		AddCheckoutWindow.INSTANCE,
		AllAuthorWindow.INSTANCE,
		AddAuthorWindow.INSTANCE,
		AddMemberWindow.INSTANCE,
		AddBookWindow.INSTANCE,
		BookCopyWindow.INSTANCE,
		AllBookCopyWindow.INSTANCE,
	};
    	
	public static void hideAllWindows() {		
		for(LibWindow frame: allWindows) {
			frame.setVisible(false);			
		}
	}
     
    private LibrarySystem() {}
    
    public void init() {
    	formatContentPane();
    	setPathToImage();
    	insertSplashImage();
		
		createMenus();
		//pack();
		setSize(660,500);
		isInitialized = true;
    }
    
    private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	//for Windows file system
//    	pathToImage = currDirectory+"\\src\\librarysystem\\library.jpg";
    	//for unix file system
    	pathToImage = currDirectory+"/src/librarysystem/library.jpg";
    	
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }
    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
		book_menus = new JMenu("Book");
		address_menus  = new JMenu("Address");
		address_menu = new JMenuItem("Address list");
		add_address_menu = new JMenuItem("Add Address");
		author_menus = new JMenu("Author");
		author_menu = new JMenuItem("Author list");
		add_author_menu = new JMenuItem("Add Author");
		checkout_menus = new JMenu("Checkout");
		options = new JMenu("Login");
		member_menus = new JMenu("Member");
		checkout_menu = new JMenuItem("Checkout");
		checkout_record = new JMenuItem("Checkout record");
		book_copy = new JMenuItem("Book copy");
		book_copy_view = new JMenuItem("All book copy");
		add_book_menu = new JMenuItem("Add Book");
		author_menus.add(author_menu);
		author_menus.add(add_author_menu);
		address_menus.add(address_menu);
		address_menus.add(add_address_menu);
		checkout_menus.add(checkout_menu);
		checkout_menus.add(checkout_record);
		menuBar.add(options);
		menuBar.add(author_menus);
		menuBar.add(book_menus);
		menuBar.add(member_menus);
		menuBar.add(checkout_menus);
		menuBar.add(address_menus);
		login = new JMenuItem("Login");
		login.addActionListener(new LoginListener());
		allBookIds = new JMenuItem("All Books");
		allBookIds.addActionListener(new AllBookIdsListener());
		
		allMemberIds = new JMenuItem("All Members");
		add_member_menu = new JMenuItem("Add member");

		allMemberIds.addActionListener(new AllMemberIdsListener());
		address_menu.addActionListener(new AllAddressListener());
		checkout_record.addActionListener(new AllCheckoutListener());
		checkout_menu.addActionListener(new AddCheckoutListener());
		add_address_menu.addActionListener(new AddAddressListener());
		author_menu.addActionListener(new AllAuthorListener());
		add_author_menu.addActionListener(new AddAuthorListener());
		add_member_menu.addActionListener(new AddMemberListener());
		add_book_menu.addActionListener(new AddBookListener());
		book_copy.addActionListener(new BookCopyListener());
		book_copy_view.addActionListener(new AllBookCopyListner());
		options.add(login);
		book_menus.add(allBookIds);
		book_menus.add(add_book_menu);
		book_menus.add(book_copy);
		book_menus.add(book_copy_view);
		member_menus.add(allMemberIds);
		member_menus.add(add_member_menu);
		// hideMenuLibrarian(false);
		// hideMenuAdmin(false);
		hideMenuLibrarian(true);
		hideMenuAdmin(true);
    }
	// LIBRARIAN, ADMIN, BOTH;
	public void hideMenuLibrarian(boolean show){
		JMenu[] hideMenus = {checkout_menus, book_menus, member_menus};
		for (JMenu m: hideMenus){
			m.setVisible(show);
		}
	}
	public void hideMenuAdmin(boolean show){
		JMenu[] hideMenus = {address_menus, author_menus};
		for (JMenu m: hideMenus){
			m.setVisible(show);
		}
	}
	class AllBookCopyListner implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookCopyWindow.INSTANCE.init();
			AllBookCopyWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(AllBookCopyWindow.INSTANCE);
			AllBookCopyWindow.INSTANCE.setVisible(true);
		}
    }
	class BookCopyListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			BookCopyWindow.INSTANCE.init();
			BookCopyWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(BookCopyWindow.INSTANCE);
			BookCopyWindow.INSTANCE.setVisible(true);
		}
    }
	class AddBookListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddBookWindow.INSTANCE.init();
			AddBookWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
			AddBookWindow.INSTANCE.setVisible(true);
		}
    }
	class AddMemberListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddMemberWindow.INSTANCE.init();
			AddMemberWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(AddMemberWindow.INSTANCE);
			AddMemberWindow.INSTANCE.setVisible(true);
		}
    }
    class AllAuthorListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllAuthorWindow.INSTANCE.init();
			AllAuthorWindow.INSTANCE.pack();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllAuthorWindow.INSTANCE);
			AllAuthorWindow.INSTANCE.setVisible(true);
		}
    }
	class AddAuthorListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddAuthorWindow.INSTANCE.init();
			AddAuthorWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(AddAuthorWindow.INSTANCE);
			AddAuthorWindow.INSTANCE.setVisible(true);
		}
    }
    class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);
		}
    }
    class AllBookIdsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			AllBookIdsWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
		}
    }
    
    class AllMemberIdsListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);
		}
    }

	class AllAddressListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllAddressWindow.INSTANCE.init();
			AllAddressWindow.INSTANCE.pack();
			AllAddressWindow.INSTANCE.setVisible(true);
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllAddressWindow.INSTANCE);
			AllAddressWindow.INSTANCE.setVisible(true);
		}
    }
	class AddCheckoutListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddCheckoutWindow.INSTANCE.init();
			AddCheckoutWindow.INSTANCE.pack();
			Util.centerFrameOnDesktop(AddCheckoutWindow.INSTANCE);
			AddCheckoutWindow.INSTANCE.setVisible(true);
		}
    }
	class AllCheckoutListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllCheckoutRecord.INSTANCE.init();
			AllCheckoutRecord.INSTANCE.setVisible(true);
			AllCheckoutRecord.INSTANCE.pack();
			Util.centerFrameOnDesktop(AllCheckoutRecord.INSTANCE);
			AllCheckoutRecord.INSTANCE.setVisible(true);
		}
    }
	class AddAddressListener implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddAddressWindow.INSTANCE.init();
			AddAddressWindow.INSTANCE.pack();
			AddAddressWindow.INSTANCE.setVisible(true);
			// AddAddressWindow.INSTANCE.pack();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AddAddressWindow.INSTANCE);
			AddAddressWindow.INSTANCE.setVisible(true);
		}
    }
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
