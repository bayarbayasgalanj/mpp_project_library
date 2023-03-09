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
import business.ControllerInterface;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.*;
import java.awt.*;


public class AllAddressWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;
	public static final AllAddressWindow INSTANCE = new AllAddressWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
	
	// private JPanel mainPanel;
	// private JPanel topPanel;
	// private JPanel middlePanel;
	// private JPanel lowerPanel;
    //Singleton class
	private AllAddressWindow() {}
	
	public void init() {
		JPanel mainPanel;
		JPanel topPanel;
		JPanel middlePanel;
		JPanel lowerPanel;
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// defineTopPanel();
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Address");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);

		
		// defineLowerPanel();
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 45);
		middlePanel.setLayout(fl);
        JList<String> addrrs_list;
        List<Address> ids = ci.allAddressObj();
		DefaultListModel<String> model = new DefaultListModel<>();
		System.out.println("Address Len:"+ids.size());
		for(Address s: ids) {
			String ss = s.toString();
			model.addElement(ss);
		}
        addrrs_list = new JList<String>(model);
        addrrs_list.setCellRenderer(new NumberedListCellRenderer());
		addrrs_list.setVisible(true);
		middlePanel.add(new JScrollPane(addrrs_list));

		JButton backToMainButn = new JButton("<= Back to Main");
		backToMainButn.addActionListener(new BackToMainListener());
		lowerPanel = new JPanel();
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(backToMainButn);

        JButton deleteButton = new JButton("Delete Select Item");
		deleteButton.addActionListener(evt ->{
			if (addrrs_list.getSelectedValue()!=null){
                DataAccess da = new DataAccessFacade();
                String addr_key = da.getAddressByKey(addrrs_list.getSelectedValue());
                if (addr_key!=null){
                    da.removeAddress(addr_key);
					// addrrs_list.setVisible(false);
					// addrrs_list.setVisible(true);
					LibrarySystem.hideAllWindows();
					addrrs_list.removeAll();
					AllAddressWindow.INSTANCE.init();
					AllAddressWindow.INSTANCE.pack();
					AllAddressWindow.INSTANCE.setVisible(true);
                }
			}
		});
		lowerPanel.add(deleteButton);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        // mainPanel.add(lowerRightPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	
    // public void defineTopPanel() {
		
	// }
	
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

	// public void defineLowerPanel() {
		
	// }
    class BackToMainListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
			INSTANCE.isInitialized(false);
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
