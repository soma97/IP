package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import db.ConnectionPool;
import db.DAOUtil;
import db.models.EmergencyCall;
import db.models.EmergencyCategory;

public class EmergencyDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL_CALLS = "select * from emergency_call;";
	private static final String SQL_SELECT_CALL_BY_ID = "select * from emergency_call where id=?;";
	private static final String SQL_INSERT_CALL = "insert into emergency_call (title,description,date, location,image_src, is_deleted, emergency_category_id) values(?,?,?,?,?,?,?);";
	private static final String SQL_DELETE_CALL = "update emergency_call set is_deleted = true where id = ?;";
	
	private static final String SQL_SELECT_EMER_CATEGORIES = "select * from emergency_category;";
	private static final String SQL_SELECT_CATEGORY_BY_ID = "select * from emergency_category where id = ?;";
	private static final String SQL_INSERT_CATEGORY = "insert into emergency_category (name,is_deleted) values(?,?);";
	
	
	public static ArrayList<EmergencyCall> selectAllCalls(){
		ArrayList<EmergencyCall> retVal = new ArrayList<EmergencyCall>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_ALL_CALLS, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal.add(new EmergencyCall(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
					new Date(rs.getTimestamp("date").getTime()), rs.getString("location"),rs.getString("image_src"), rs.getBoolean("is_deleted"),
						rs.getInt("emergency_category_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static EmergencyCall selectCallById(int id){
		EmergencyCall retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_CALL_BY_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal = new EmergencyCall(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
					new Date(rs.getTimestamp("date").getTime()), rs.getString("location"),rs.getString("image_src"), rs.getBoolean("is_deleted"),
						rs.getInt("emergency_category_id"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	

	public static boolean insertEmergencyCall(EmergencyCall call) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { call.getTitle(),call.getDescription(),new Timestamp(call.getDate().getTime()), call.getLocation(), call.getImageSource(),
				call.isDeleted(), call.getEmergencyCategoryId() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT_CALL, true,
					values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
			{
				call.setId(generatedKeys.getInt(1));
			}
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean deleteCall(int id) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_CALL, false,
					values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<EmergencyCategory> selectEmergencyCategories(){
		ArrayList<EmergencyCategory> retVal = new ArrayList<EmergencyCategory>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_EMER_CATEGORIES, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal.add(new EmergencyCategory(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_deleted")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static EmergencyCategory selectCategoryById(int id){
		EmergencyCategory retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_CATEGORY_BY_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal = new EmergencyCategory(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_deleted"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static boolean insertEmergencyCategory(EmergencyCategory category) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { category.getName(), category.isDeleted() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT_CATEGORY, true,
					values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
			{
				category.setId(generatedKeys.getInt(1));
			}
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
}
