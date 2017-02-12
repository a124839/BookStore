package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mysql.jdbc.Statement;

import dao.Dao;
import db.JDBCUtils;
import utils.ReflectionUtils;
import web.ConnectionContext;

public class BaseDao<T> implements Dao<T> {
	
	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz ;
	//��ȡclass
	public BaseDao() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}
	
	@Override
	public long insert(String sql, Object... args) {
		
		long id = 0;
		
		Connection connection = null;
		PreparedStatement pStatement = null;
//		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionContext.getInstance().get();
			//��ִ��sql���ʱ��Ҫ��ȡ�������ڶ�������Ϊ��ȡ����
			pStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					pStatement.setObject(i + 1, args[i]);
					
				}
			}
			
			pStatement.executeUpdate();
			
			//��ȡ����
			resultSet = pStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(resultSet, pStatement);
			
		}
		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = ConnectionContext.getInstance().get();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	/* T ���� ĳһ������ ����Ҫ��ȡclass
	 * @see dao.Dao#query(java.lang.String, java.lang.Object[])
	 */
	@Override
	public T query(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = ConnectionContext.getInstance().get();
			//��һ��û����
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = ConnectionContext.getInstance().get();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;
		
		try {
			connection = ConnectionContext.getInstance().get();
			return (V) queryRunner.query(connection, sql, new ScalarHandler(), args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;
		
		try {
			connection = ConnectionContext.getInstance().get();
			queryRunner.batch(connection, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
