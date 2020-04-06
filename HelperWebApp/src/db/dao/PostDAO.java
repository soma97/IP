package db.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import db.ConnectionPool;
import db.DAOUtil;
import db.models.Comment;
import db.models.EmergencyCategory;
import db.models.Post;

public class PostDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "select * from post;";
	private static final String SQL_SELECT_ALL_DANGER = "select * from post where is_emergency_warning=true;";
	private static final String SQL_SELECT_BY_ID = "select * from post where id=?;";
	private static final String SQL_INSERT = "insert into post (title,text,link,video_src, location, is_emergency_warning,is_potential_danger, is_deleted,user_account_id) values(?,?,?,?,?,?,?,?,?);";
	private static final String SQL_DELETE_POST = "update post set is_deleted = true where id = ?;";
	
	private static final String SQL_SELECT_ALL_COMMENTS_BY_POST_ID = "select * from comment where post_id = ?;";
	private static final String SQL_INSERT_COMMENT = "insert into comment (text, image_src, date_time, is_deleted, user_account_id, post_id) values (?,?,?,?,?,?);";
	private static final String SQL_DELETE_COMMENT = "update comment set is_deleted=true where id=?;";
	
	private static final String SQL_SELECT_EMERGENCY_CATEGORIES_BY_POST_ID = "select * from emergency_category ec inner join post_emergency_category pec on ec.id=pec.category_id where pec.post_id = ?;";
	private static final String SQL_INSERT_EMERGENCY_CATEGORY_FOR_POST = "insert into post_emergency_category (category_id, post_id) values(?,?);";
	
	public static ArrayList<Post> selectAllPosts(){
		ArrayList<Post> retVal = new ArrayList<Post>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal.add(new Post(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getString("link"), rs.getString("video_src"),
						rs.getString("location"),rs.getBoolean("is_emergency_warning"),rs.getBoolean("is_potential_danger"),rs.getBoolean("is_deleted"),rs.getInt("user_account_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<Post> selectAllPotentialDangerPosts(){
		ArrayList<Post> retVal = new ArrayList<Post>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_ALL_DANGER, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal.add(new Post(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getString("link"), rs.getString("video_src"),
						rs.getString("location"),rs.getBoolean("is_emergency_warning"),rs.getBoolean("is_potential_danger"),rs.getBoolean("is_deleted"),rs.getInt("user_account_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static Post selectPostById(int id){
		Post retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {id};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_BY_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal = new Post(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getString("link"), rs.getString("video_src"),
						rs.getString("location"),rs.getBoolean("is_emergency_warning"),rs.getBoolean("is_potential_danger"),rs.getBoolean("is_deleted"),rs.getInt("user_account_id"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static boolean insertPost(Post post) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { post.getTitle(),post.getText(),post.getLink(),post.getVideoSource(),
			post.getLocation(),post.isEmergencyWarning(),post.isPotentialDanger(), post.isDeleted(), post.getUserAccountId() };
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
				post.setId(generatedKeys.getInt(1));
			}
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean deletePost(int id) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_POST, false,
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
	
	public static ArrayList<Comment> selectCommentsForPost(int postId){
		ArrayList<Comment> retVal = new ArrayList<Comment>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { postId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_ALL_COMMENTS_BY_POST_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal.add(new Comment(rs.getInt("id"), rs.getString("text"), rs.getString("image_src"), rs.getTimestamp("date_time"), 
						rs.getBoolean("is_deleted"), rs.getInt("user_account_id"), rs.getInt("post_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	
	public static boolean insertComment(Comment comment) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { comment.getText(), comment.getImageSource(), new Date(comment.getDateTime().getTime()), comment.isDeleted(), comment.getUserAccountId(), comment.getPostId() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT_COMMENT, true,
					values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
			{
				comment.setId(generatedKeys.getInt(1));
			}
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	
	public static boolean deleteComment(int id) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_DELETE_COMMENT, false,
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
	
	public static ArrayList<EmergencyCategory> selectEmergencyCategoriesForPost(int postId){
		ArrayList<EmergencyCategory> retVal = new ArrayList<EmergencyCategory>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { postId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_EMERGENCY_CATEGORIES_BY_POST_ID, false, values);
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
	
	public static boolean insertEmergencyCategoryForPost(int postId, int emergencyCategoryId) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { emergencyCategoryId, postId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT_EMERGENCY_CATEGORY_FOR_POST, true,
					values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0)
				retVal = false;
			else
				retVal = true;
			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next())
			{
				generatedKeys.getInt(1);
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
