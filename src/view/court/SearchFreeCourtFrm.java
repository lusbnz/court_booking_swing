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
import dao.CourtDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MiniCourt;
import model.User;

public class SearchFreeCourtFrm extends JFrame implements ActionListener {

    private ArrayList<MiniCourt> listCourt;
    private JTextField txtDateIn;
    private JTextField txtDateOut;
    private JButton btnSearch;
    private JTable tblResult;
    private User user;
    private SearchFreeCourtFrm mainFrm;

    public SearchFreeCourtFrm(User user) {
        super("Tìm kiếm sân");
        this.user = user;
        mainFrm = this;
        listCourt = new ArrayList<MiniCourt>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Tìm kiếm sân theo ngày");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1, BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width - 5, 20);
        pn1.add(new JLabel("Ngày bắt đầu (dd/mm/yyyy): "));
        txtDateIn = new JTextField();
        pn1.add(txtDateIn);
        pn1.add(Box.createRigidArea(new Dimension(10, 0)));
        pn1.add(new JLabel("Ngày kết thúc (dd/mm/yyyy): "));
        txtDateOut = new JTextField();
        pn1.add(txtDateOut);
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
                    (new SearchClientFrm(user, listCourt.get(row), dateInS, dateOutS)).setVisible(true);
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
            try {
                String dateIn = txtDateIn.getText().trim();
                String dateOut = txtDateOut.getText().trim();
                
                if (dateIn.isEmpty() || dateOut.isEmpty()) {
                    return;
                }
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateInS = dateFormat.parse(dateIn);
                Date dateOutS = dateFormat.parse(dateOut);
                
                CourtDAO cd = new CourtDAO();
                listCourt = cd.searchFreeCourt(dateInS, dateOutS);
                
                String[] columnNames = {"Id", "Tên sân", "Loại sân", "Giá", "Mô tả"};
                String[][] value = new String[listCourt.size()][5];
                for (int i = 0; i < listCourt.size(); i++) {
                    value[i][0] = listCourt.get(i).getId() + "";
                    value[i][1] = listCourt.get(i).getName();
                    value[i][2] = listCourt.get(i).getType();
                    value[i][3] = listCourt.get(i).getPrice() + "";
                    value[i][4] = listCourt.get(i).getDesc();
                }
                DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tblResult.setModel(tableModel);
            } catch (ParseException ex) {
                Logger.getLogger(SearchFreeCourtFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
