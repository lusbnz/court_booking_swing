package view.court;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import dao.ClientDAO;
import java.util.Date;
import model.Client;
import model.MiniCourt;
import model.User;

public class SearchClientFrm extends JFrame implements ActionListener {

    private ArrayList<Client> listClient;
    private JTextField txtKey;
    private JButton btnSearch;
    private JTable tblResult;
    private User user;
    private SearchClientFrm mainFrm;
    private Date in;
    private Date out;

    public SearchClientFrm(User user, MiniCourt court, Date in, Date out) {
        super("Tìm kiếm khách hàng");
        this.user = user;
        mainFrm = this;
        listClient = new ArrayList<Client>();
        this.in = in;
        this.out = out;

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Tìm kiếm khách hàng");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1, BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width - 5, 20);
        pn1.add(new JLabel("Tên khách hàng: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
        tblResult = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));

        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tblResult.getRowHeight();

                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    (new AddBooking(user, court, listClient.get(row), in, out)).setVisible(true);
                    mainFrm.dispose();
                }
            }
        });

        pn2.add(scrollPane);
        pnMain.add(pn2);
        this.add(pnMain);
        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnSearch)) {
            if ((txtKey.getText() == null) || (txtKey.getText().length() == 0)) {
                return;
            }
            ClientDAO cd = new ClientDAO();
            listClient = cd.searchClient(txtKey.getText().trim());

            String[] columnNames = {"Id", "Tên sân", "CCCD", "Địa chỉ", "Số điện thoại", "Email", "Mô tả"};
            String[][] value = new String[listClient.size()][7];
            for (int i = 0; i < listClient.size(); i++) {
                value[i][0] = listClient.get(i).getId() + "";
                value[i][1] = listClient.get(i).getName();
                value[i][2] = listClient.get(i).getIdCard();
                value[i][3] = listClient.get(i).getAddress() + "";
                value[i][4] = listClient.get(i).getTel() + "";
                value[i][5] = listClient.get(i).getEmail() + "";
                value[i][6] = listClient.get(i).getNote() + "";
            }
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblResult.setModel(tableModel);
        }
    }
}
