package view.user;

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
import view.court.ManageCourtFrm;
import view.court.BookingCourtFrm;

public class SellerHomeFrm extends JFrame implements ActionListener {

    private JButton btnBooking, btnCourt, btnMiniCourt, btnStat;
    private User user;

    public SellerHomeFrm(User user) {
        super("Nhân viên");
        this.user = user;

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        JPanel lblPane = new JPanel();
        lblPane.setLayout(new BoxLayout(lblPane, BoxLayout.LINE_AXIS));
        lblPane.add(Box.createRigidArea(new Dimension(350, 0)));
        JLabel lblUser = new JLabel("Đăng nhập bởi: " + user.getName());
        lblUser.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblPane.add(lblUser);
        listPane.add(lblPane);
        listPane.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel lblHome = new JLabel("Nhân viên");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(28.0f));
        listPane.add(lblHome);
        listPane.add(Box.createRigidArea(new Dimension(0, 20)));

        btnBooking = new JButton("Đặt sân");
        btnBooking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBooking.addActionListener(this);
        listPane.add(btnBooking);
        listPane.add(Box.createRigidArea(new Dimension(0, 10)));

        btnCourt = new JButton("Quản lí chuỗi sân");
        btnCourt.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCourt.addActionListener(this);
        listPane.add(btnCourt);
        listPane.add(Box.createRigidArea(new Dimension(0, 10)));

        btnMiniCourt = new JButton("Quản lí sân");
        btnMiniCourt.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMiniCourt.addActionListener(this);
        listPane.add(btnMiniCourt);
        listPane.add(Box.createRigidArea(new Dimension(0, 10)));

        btnStat = new JButton("Thống kê");
        btnStat.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStat.addActionListener(this);
        listPane.add(btnStat);

        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.add(listPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if ((e.getSource() instanceof JButton) && (((JButton) e.getSource()).equals(btnMiniCourt))) {
            (new ManageCourtFrm(user)).setVisible(true);
            this.dispose();
        } else if ((e.getSource() instanceof JButton) && (((JButton) e.getSource()).equals(btnBooking))) {
            (new BookingCourtFrm(user)).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Chức năng đang được cập nhật!");
        }
    }

}
