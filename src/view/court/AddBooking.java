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
import dao.BookingDAO;
import java.util.Date;
import model.Client;
import model.BookedCourt;
import model.User;
import model.Booking;

public class AddBooking extends JFrame implements ActionListener {

    private JTable tblResult;
    private User user;
    private ArrayList<BookedCourt> court;
    private Client client;
    private AddBooking mainFrm;
    private DefaultTableModel tableModel;
    private Date in;
    private Date out;
    private ArrayList<Client> listClient;

    public AddBooking(User user, ArrayList<BookedCourt> court, Client client, Date in, Date out) {
        super("Thông tin phiếu đặt");
        this.user = user;
        this.court = court;
        this.client = client;
        this.in = in;
        this.out = out;
        mainFrm = this;
        listClient = new ArrayList<Client>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Thông tin phiếu đặt");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        String[] columnNames = {"ID", "Client ID", "Creator ID", "Court ID", "Booked Date", "Sale Off"};
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        tblResult = new JTable(tableModel);

        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));

        createBooking();

        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / tblResult.getRowHeight();

                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    (new AddBooking(user, court, listClient.get(row))).setVisible(true);
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

    private void updateBookingTable() {
        BookingDAO bd = new BookingDAO();
        ArrayList<Booking> bookings = bd.getBookingOfCourt(court, in, out);

        tableModel.setRowCount(0);

        for (Booking booking : bookings) {
            Object[] rowData = {
                booking.getId(),
                booking.getClient(),
                booking.getCreator(),
                booking.getBookedCourt(),
                booking.getBookedDate(),
                booking.getSaleoff()
            };
            tableModel.addRow(rowData);
        }
    }

    private void createBooking() {
        Booking booking = new Booking();
        booking.setClient(client);
        booking.setCreator(user);
        booking.setBookedCourt(court);
        booking.setBookedDate(new Date());
        booking.setSaleoff(0);

        BookingDAO bd = new BookingDAO();
        bd.addBooking(booking);

        updateBookingTable();
    }
}
