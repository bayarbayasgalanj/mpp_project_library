package librarysystem;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import javax.swing.*;
import java.awt.*;

public class AllMemberIdsWindow extends JFrame implements LibWindow {
	public static final AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
	// public static TextArea textArea;
	private DefaultListModel<String> model = new DefaultListModel<>();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	// public JPanel getMainPanel() {
	// 	return mainPanel;
	// }
	private AllMemberIdsWindow() {}
	
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

	public void init() {
		// textArea = null;
		JPanel mainPanel;
		JPanel topPanel;
		JPanel middlePanel;
		JPanel lowerPanel;

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(65, 65));
		// defineTopPanel();
		topPanel = new JPanel();
		JLabel AllIDsLabel = new JLabel("All Members");
		Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(AllIDsLabel);
		// defineMiddlePanel();
		
		middlePanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 45, 55);
		middlePanel.setLayout(fl);
		// textArea = new TextArea(10,50);
		model.clear();
		List<LibraryMember> ids = ci.allMembersObs();
		String sb = "";
		for(LibraryMember s: ids) {
			// sb += s.toString() + "\n";
			model.addElement(s.toString());
		}
		// model.clear();
		JList<String> author_list;
        author_list = new JList<String>(model);
        author_list.setCellRenderer(new NumberedListCellRenderer());
		middlePanel.add(new JScrollPane(author_list));


		System.out.println(ids.size());
		// textArea.setText(sb.toString());
		// middlePanel.add(textArea);

		// defineLowerPanel();
		lowerPanel = new JPanel();
		FlowLayout fl2 = new FlowLayout(FlowLayout.LEFT);
		lowerPanel.setLayout(fl2);
		JButton backButton = new JButton("<== Back to Main");
		addBackButtonListener(backButton);
		lowerPanel.add(backButton);

		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);	
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized = true;
	}
	// public void defineTopPanel() {
		
	// }
	
	// public void defineMiddlePanel() {
		
	// }
	
	// public void defineLowerPanel() {
		
	// }
	
	// public void setData(String data) {
	// 	textArea.setText(data);
	// }
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
		   LibrarySystem.hideAllWindows();
		   LibrarySystem.INSTANCE.setVisible(true);
	    });
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
}


