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
 * JDBC �Ĺ�����
 *
 */
public class JDBCUtils {

	private static DataSource dataSource = null;
	
	static{
		dataSource = new ComboPooledDataSource("bookstore");
	}
	
	//��ȡ���ݿ�����
	/**��ȡ���ݿ�����
	 * @return
	 * @throws DBException
	 */
	public static Connection getConnection() throws DBException{  
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("���ݿ����Ӵ���!");
		}
	}
 
	//�ر����ݿ�����
	/**�ر����ݿ�����
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
			throw new DBException("���ݿ����Ӵ���!");
		}
	}
	
	//�ر����ݿ�����
	/**�ر����ݿ�����
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
			throw new DBException("���ݿ����Ӵ���!");
		}
		
		try {
			if(statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("���ݿ����Ӵ���!");
		}
	}
	
}

