package db;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * JDBC 的工具类
 *
 */
public class JDBCUtils {

	private static DataSource dataSource = null;
	
	static{
		dataSource = new ComboPooledDataSource("bookstore");
	}
	
	//获取数据库连接
	/**获取数据库连接
	 * @return
	 * @throws DBException
	 */
	public static Connection getConnection() throws DBException{  
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}
 
	//关闭数据库连接
	/**关闭数据库连接
	 * @param connection
	 * @throws DBException
	 */
	public static void release(Connection connection) throws DBException {
		try {
			if(connection != null){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}
	
	//关闭数据库连接
	/**关闭数据库连接
	 * @param rs
	 * @param statement
	 * @throws DBException
	 */
	public static void release(ResultSet rs, Statement statement) throws DBException {
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据库连接错误!");
		}
	}
	
}

