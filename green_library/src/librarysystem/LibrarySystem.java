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

import business.ControllerInterface;
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
	JMenuItem author_menu, add_author_menu, address_menu, add_address_menu, login, allBookIds, allMemberIds, add_member_menu, checkout_menu, checkout_record, book_copy; 
    String pathToImage;
    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = { 
    	LibrarySystem.INSTANCE,
		LoginWindow.INSTANCE,
		AllMemberIdsWindow.INSTANCE,	
		AllBookIdsWindow.INSTANCE
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
		allMemberIds.addActionListener(new AllMemberIdsListener());
		address_menu.addActionListener(new AllAddressListener());
		add_member_menu = new JMenuItem("Add member");
		options.add(login);
		book_menus.add(allBookIds);
		book_menus.add(book_copy);
		member_menus.add(allMemberIds);
		member_menus.add(add_member_menu);
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
			AllAddressWindow.INSTANCE.init();
				List<String> ids = ci.allBookIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for(String s: ids) {
					sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllAddressWindow.INSTANCE.setData(sb.toString());
			AllAddressWindow.INSTANCE.pack();
			//AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllAddressWindow.INSTANCE);
			AllAddressWindow.INSTANCE.setVisible(true);
			
		}
    	
    }
    
    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);
			
			
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
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
			
			
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();
			
			System.out.println("ci.allAddress()--------"+ci.allAddress());
			List<String> ids = ci.allAddress();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s.toString() + "\n");
			}
			System.out.println(sb.toString());
			AllAddressWindow.INSTANCE.setData(sb.toString());
			AllAddressWindow.INSTANCE.pack();
			//AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllAddressWindow.INSTANCE);
			AllAddressWindow.INSTANCE.setVisible(true);
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
