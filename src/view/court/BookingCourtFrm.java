package view.court;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.User;

public class BookingCourtFrm extends JFrame implements ActionListener{
	private JButton btnSearch;
	private User user;

	public BookingCourtFrm(User user) {
		super("Đặt sân");	
		this.user = user;

		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

		JPanel lblPane = new JPanel();
		lblPane.setLayout(new BoxLayout(lblPane, BoxLayout.LINE_AXIS));
		lblPane.add(Box.createRigidArea(new Dimension(450, 0)));
		JLabel lblUser = new JLabel("Đăng nhập bởi: " + user.getName());
		lblUser.setAlignmentX(Component.RIGHT_ALIGNMENT);	
		lblPane.add(lblUser);
		listPane.add(lblPane);
		listPane.add(Box.createRigidArea(new Dimension(0,20)));

		JLabel lblHome = new JLabel("Tìm kiếm sân");
		lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);	
		lblHome.setFont (lblHome.getFont ().deriveFont (28.0f));
		listPane.add(lblHome);
		listPane.add(Box.createRigidArea(new Dimension(0,20)));

		btnSearch = new JButton("Tìm kiếm sân");
		btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSearch.addActionListener(this);
		listPane.add(btnSearch);
		listPane.add(Box.createRigidArea(new Dimension(0,10)));

		this.setSize(600,300);				
		this.setLocation(200,10);
		this.add(listPane, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if((e.getSource() instanceof JButton)&&(((JButton)e.getSource()).equals(btnSearch))) {
			(new SearchFreeCourtFrm(user)).setVisible(true);
			this.dispose();
		}else {
			JOptionPane.showMessageDialog(this, "Chức năng đang được cập nhật!");
		}
	}

}
