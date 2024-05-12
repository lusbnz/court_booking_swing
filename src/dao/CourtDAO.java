package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.MiniCourt;

public class CourtDAO extends DAO{
	
	public CourtDAO() {
		super();
	}

	public ArrayList<MiniCourt> searchCourt(String key){
		ArrayList<MiniCourt> result = new ArrayList<MiniCourt>();
		String sql = "SELECT * FROM tblMiniCourt WHERE name LIKE ?";
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "%" + key + "%");
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				MiniCourt mc = new MiniCourt();
				mc.setId(rs.getInt("id"));
				mc.setName(rs.getString("name"));
				mc.setType(rs.getString("type"));
				mc.setPrice(rs.getFloat("price"));
				mc.setDesc(rs.getString("desc"));
				result.add(mc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return result;
	}
	
	public boolean updateCourt(MiniCourt mc){
		String sql = "UPDATE tblMiniCourt SET name=?, type=?, price=?, `desc`=? WHERE id=?";
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, mc.getName());
			ps.setString(2, mc.getType());
			ps.setFloat(3, mc.getPrice());
			ps.setString(4, mc.getDesc());
			ps.setInt(5, mc.getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	public ArrayList<MiniCourt> searchFreeCourt(Date checkin, Date checkout){
		ArrayList<MiniCourt> result = new ArrayList<MiniCourt>();
		String sql = "SELECT * FROM tblMiniCourt WHERE id NOT IN (SELECT idcourt FROM tblBookedCourt WHERE checkout > ? AND checkin < ?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, sdf.format(checkin));
			ps.setString(2, sdf.format(checkout));
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				MiniCourt mc = new MiniCourt();
				mc.setId(rs.getInt("id"));
				mc.setName(rs.getString("name"));
				mc.setType(rs.getString("type"));
				mc.setPrice(rs.getFloat("price"));
				mc.setDesc(rs.getString("desc"));
				result.add(mc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
		return result;
	}
}
