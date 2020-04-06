package db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import db.ConnectionPool;
import db.DAOUtil;
import db.models.ImagePath;

public class ImageDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL_IMAGES_BY_POST_ID = "select * from image where post_id = ?;";
	private static final String SQL_INSERT = "insert into image (image_src, post_id) values (?,?);";
	
	public static ArrayList<ImagePath> selectAllForPostId(int postId){
		ArrayList<ImagePath> retVal = new ArrayList<ImagePath>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { postId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
					SQL_SELECT_ALL_IMAGES_BY_POST_ID, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()){
				retVal.add(new ImagePath(rs.getInt("id"), rs.getString("image_src"), rs.getInt("post_id")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean insertImage(ImagePath imagePath) {
		boolean retVal = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { imagePath.getImageSource(), imagePath.getPostId() };
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
				imagePath.setId(generatedKeys.getInt(1));
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
