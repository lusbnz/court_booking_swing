package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.BookedCourt;
import model.Booking;
import model.Client;

public class BookingDAO extends DAO{

	public BookingDAO() {
		super();
	}
        
        public boolean addBooking(Booking b) {
		String sqlAddBooking = "INSERT INTO tblBooking(idcreator, idclient, bookingdate, saleoff, note) VALUES(?,?,?,?,?)";
		String sqlAddBookedCourt = "INSERT INTO tblBookedCourt(idbooking, idcourt, checkin, checkout, price, saleoff, ischeckin) "
				+ "VALUES(?,?,?,?,?,?,?)";
		String sqlCheckbookedCourt = "SELECT * FROM tblBookedCourt WHERE idcourt = ? AND checkout > ? AND checkin < ?";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean result = true;
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(sqlAddBooking, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, b.getCreator().getId());
			ps.setInt(2, b.getClient().getId());
			ps.setString(3, sdf.format(b.getBookedDate()));
			ps.setFloat(4, b.getSaleoff());
			ps.setString(5, b.getNote());
			
			ps.executeUpdate();			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				b.setId(generatedKeys.getInt(1));
				
				for(BookedCourt bc: b.getBookedCourt()) {
					ps = con.prepareStatement(sqlCheckbookedCourt);
					ps.setInt(1, bc.getCourt().getId());
					ps.setString(2, sdf.format(bc.getCheckin()));
					ps.setString(3, sdf.format(bc.getCheckout()));
					
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						result = false;
						try {
							con.rollback();
							con.setAutoCommit(true);
						}catch(Exception ex) {
							result = false;
							ex.printStackTrace();
						}
						return result;
					}
					
					ps = con.prepareStatement(sqlAddBookedCourt, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, b.getId());
					ps.setInt(2, bc.getCourt().getId());
					ps.setString(3, sdf.format(bc.getCheckin()));
					ps.setString(4, sdf.format(bc.getCheckout()));
					ps.setFloat(5, bc.getPrice());
					ps.setFloat(6, bc.getSaleoff());
					ps.setBoolean(7, bc.isChecked());
					
					ps.executeUpdate();			
					generatedKeys = ps.getGeneratedKeys();
					if (generatedKeys.next()) {
						bc.setId(generatedKeys.getInt(1));
					}
				}
			}
			
		}catch(Exception e) {
			result = false;			
			try {				
				con.rollback();
			}catch(Exception ex) {
				result = false;
				ex.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {				
			}catch(Exception ex) {
				result = false;
				ex.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<Booking> getBookingOfCourt(int idcourt, Date startDate, Date endDate){
		ArrayList<Booking> result = new ArrayList<Booking>();
		String sql = "SELECT a.id as idbookedcourt, GREATEST(a.checkin,?) as checkin, LEAST(a.checkout,?) as checkout, a.price, a.saleoff as courtsaleoff,"
				+ "b.id as idbooking, b.saleoff as bookingsaleoff,"
				+ " c.id as idclient, c.name, c.address, c.idcard, c.tel"
				+ " FROM tblBookedCourt a, tblBooking b, tblClient c WHERE a.idcourt = ? AND a.ischeckin = 1 "
				+ "AND a.checkout > ? AND a.checkin < ? AND b.id = a.idbooking AND c.id = b.idclient";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, sdf.format(startDate));
			ps.setString(2, sdf.format(endDate));
			ps.setInt(3, idcourt);
			ps.setString(4, sdf.format(startDate));
			ps.setString(5, sdf.format(endDate));
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				Booking b = new Booking();
				b.setId(rs.getInt("idbooking"));
				b.setSaleoff(rs.getFloat("bookingsaleoff"));
				Client c = new Client();
				c.setId(rs.getInt("idclient"));
				c.setName(rs.getString("name"));
				c.setAddress(rs.getString("address"));
				c.setIdCard(rs.getString("idcard"));
				b.setClient(c);
				BookedCourt bc = new BookedCourt();
				bc.setId(rs.getInt("idbookedcourt"));				
				bc.setSaleoff(rs.getFloat("courtsaleoff"));
				bc.setPrice(rs.getFloat("price"));
				bc.setCheckin(rs.getDate("checkin"));
				bc.setCheckout(rs.getDate("checkout"));		
				b.getBookedCourt().add(bc);
				result.add(b);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return result;
	}
}
