package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import db.*;
import db.models.Login;
import db.models.UserAccount;

public class UserAccountDAO {
		private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
		private static final String SQL_SELECT_ALL = "select * from user_account;";
		private static final String SQL_SELECT_BY_ID = "select * from user_account where id=?;";
		private static final String SQL_SELECT_BY_USERNAME = "select * from user_account where username=?;";
		private static final String SQL_INSERT = "insert into user_account (name,surname,username,password,email,country,region,city,image_src,receive_emer_notif,number_of_logins,is_admin,is_approved,is_blocked, is_logged_in)\r\n" + 
				"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		private static final String SQL_UPDATE_USER = "update user_account set name = ?,surname=?,username=?,password=?,email=?,country=?,"
				+ "region=?,city=?,image_src=?,receive_emer_notif=?,number_of_logins=?,is_admin=?, is_approved = ?, is_blocked = ?, is_logged_in = ? where id = ?;";
		
		private static final String SQL_SELECT_ALL_LOGIN = "select * from login;";
		private static final String SQL_SELECT_ALL_LOGIN_BY_ACCOUNT_ID = "select * from login where user_account_id = ?;";
		private static final String SQL_INSERT_LOGIN = "insert into login (date_login, date_logout, user_account_id) values (?,?,?);";
		private static final String SQL_UPDATE_LOGIN = "update login set date_logout=? where id=?;";
		
		public static ArrayList<UserAccount> selectAllUsers(){
			ArrayList<UserAccount> retVal = new ArrayList<UserAccount>();
			Connection connection = null;
			ResultSet rs = null;
			Object values[] = {};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
						SQL_SELECT_ALL, false, values);
				rs = pstmt.executeQuery();
				while (rs.next()){
					retVal.add(new UserAccount(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("username"), 
							rs.getString("password"), rs.getString("email"), rs.getString("country"), rs.getString("region"), 
							rs.getString("city"), rs.getString("image_src"), rs.getString("receive_emer_notif"), rs.getInt("number_of_logins"),
							rs.getBoolean("is_admin"), rs.getBoolean("is_approved"), rs.getBoolean("is_blocked"), rs.getBoolean("is_logged_in")));
				}
				pstmt.close();
			} catch (SQLException exp) {
				exp.printStackTrace();
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}
		
		public static UserAccount selectUserById(int id){
			UserAccount retVal = null;
			Connection connection = null;
			ResultSet rs = null;
			Object values[] = {id};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
						SQL_SELECT_BY_ID, false, values);
				rs = pstmt.executeQuery();
				while (rs.next()){
					retVal = new UserAccount(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("username"), 
							rs.getString("password"), rs.getString("email"), rs.getString("country"), rs.getString("region"), 
							rs.getString("city"), rs.getString("image_src"), rs.getString("receive_emer_notif"), rs.getInt("number_of_logins"),
							rs.getBoolean("is_admin"), rs.getBoolean("is_approved"), rs.getBoolean("is_blocked"), rs.getBoolean("is_logged_in"));
				}
				pstmt.close();
			} catch (SQLException exp) {
				exp.printStackTrace();
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}
		
		public static UserAccount selectUserByUsername(String username){
			UserAccount retVal = null;
			Connection connection = null;
			ResultSet rs = null;
			Object values[] = {username};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
						SQL_SELECT_BY_USERNAME, false, values);
				rs = pstmt.executeQuery();
				while (rs.next()){
					retVal = new UserAccount(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("username"), 
							rs.getString("password"), rs.getString("email"), rs.getString("country"), rs.getString("region"), 
							rs.getString("city"), rs.getString("image_src"), rs.getString("receive_emer_notif"), rs.getInt("number_of_logins"),
							rs.getBoolean("is_admin"), rs.getBoolean("is_approved"), rs.getBoolean("is_blocked"), rs.getBoolean("is_logged_in"));
				}
				pstmt.close();
			} catch (SQLException exp) {
				exp.printStackTrace();
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}
		

		public static boolean insertUser(UserAccount account) {
			boolean retVal = false;
			Connection connection = null;
			ResultSet generatedKeys = null;
			Object values[] = { account.getName(),account.getSurname(),account.getUsername(),account.getPassword(),
					account.getEmail(),account.getCountry(),account.getRegion(),account.getCity(),account.getImageSource(),
					account.getReceiveEmergencyNotifications(),account.getNumberOfLogins(),account.isAdmin(),account.isApproved(),
					account.isBlocked(), account.isLoggedIn()};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true,
						values);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0)
					retVal = false;
				else
					retVal = true;
				generatedKeys = pstmt.getGeneratedKeys();
				if (generatedKeys.next())
				{
					account.setId(generatedKeys.getInt(1));
				}
				pstmt.close();
			} catch (SQLException e) {
				retVal = false;
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}

		public static boolean updateUser(UserAccount account) {
			boolean retVal = false;
			Connection connection = null;
			Object values[] = { account.getName(), account.getSurname(), account.getUsername(),account.getPassword(),
					account.getEmail(), account.getCountry(), account.getRegion(),account.getCity(),account.getImageSource(), 
					account.getReceiveEmergencyNotifications(), account.getNumberOfLogins(), account.isAdmin(), account.isApproved(),
					account.isBlocked(), account.isLoggedIn(), account.getId()};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER, false,
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
		
		public static ArrayList<Login> selectAllLogins(){
			ArrayList<Login> retVal = new ArrayList<Login>();
			Connection connection = null;
			ResultSet rs = null;
			Object values[] = { };
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
						SQL_SELECT_ALL_LOGIN, false, values);
				rs = pstmt.executeQuery();
				while (rs.next()){
					Timestamp logoutDate = rs.getTimestamp("date_logout");
					java.util.Date logoutDateJava = logoutDate == null ? null : new java.util.Date(logoutDate.getTime());
					retVal.add(new Login(rs.getInt("id"), new Date(rs.getTimestamp("date_login").getTime()), logoutDateJava, rs.getInt("user_account_id")));
				}
				pstmt.close();
			} catch (SQLException exp) {
				exp.printStackTrace();
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}
		
		public static ArrayList<Login> selectLoginsForUser(int userId){
			ArrayList<Login> retVal = new ArrayList<Login>();
			Connection connection = null;
			ResultSet rs = null;
			Object values[] = { userId };
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
						SQL_SELECT_ALL_LOGIN_BY_ACCOUNT_ID, false, values);
				rs = pstmt.executeQuery();
				while (rs.next()){
					retVal.add(new Login(rs.getInt("id"), rs.getDate("date_login"), rs.getDate("date_logout"), rs.getInt("user_account_id")));
				}
				pstmt.close();
			} catch (SQLException exp) {
				exp.printStackTrace();
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}
		
		public static boolean insertLogin(Login login) {
			boolean retVal = false;
			Connection connection = null;
			ResultSet generatedKeys = null;
			Object values[] = { new Date(login.getDateLogin().getTime()), null, login.getUserAccountId()};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT_LOGIN, true,
						values);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0)
					retVal = false;
				else
					retVal = true;
				generatedKeys = pstmt.getGeneratedKeys();
				if (generatedKeys.next())
				{
					login.setId(generatedKeys.getInt(1));
				}
				pstmt.close();
			} catch (SQLException e) {
				retVal = false;
			} finally {
				connectionPool.checkIn(connection);
			}
			return retVal;
		}
		
		public static boolean updateLogoutTime(Login login) {
			boolean retVal = false;
			Connection connection = null;
			Object values[] = { new Date(login.getDateLogout().getTime()), login.getId()};
			try {
				connection = connectionPool.checkOut();
				PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE_LOGIN, false,
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

}
