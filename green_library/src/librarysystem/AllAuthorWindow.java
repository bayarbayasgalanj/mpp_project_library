package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import business.Address;
import business.Author;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.*;
import java.awt.*;


public class AllAuthorWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllAuthorWindow INSTANCE = new AllAuthorWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
    //Singleton class
	private AllAuthorWindow() {}
	
	public void init() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		defineTopPanel();
		defineLowerPanel();
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        // mainPanel.add(lowerRightPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
    public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Author");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
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
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 45);
		middlePanel.setLayout(fl);
        JList<String> author_list;
		List<Author> ids = ci.allAuthorsObj();
        DefaultListModel<String> model = new DefaultListModel<>();
        System.out.println("Author Len:"+ids.size());
        for(Author s: ids) {
            String ss = s.toString();
            model.addElement(ss);
        }
        author_list = new JList<String>(model);
        author_list.setCellRenderer(new NumberedListCellRenderer());
		middlePanel.add(new JScrollPane(author_list));

		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);

        JButton deleteButton = new JButton("Delete Select Item");
		deleteButton.addActionListener(evt ->{
			if (author_list.getSelectedValue()!=null){
                DataAccess da = new DataAccessFacade();
                String author_key = da.getAuthorByKey(author_list.getSelectedValue());
                if (author_key!=null){
                    da.removeAuthor(author_key);
                }
			}
		});
		lowerPanel.add(deleteButton);
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
