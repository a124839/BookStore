package test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import db.JDBCUtils;

public class testJdbc {

	@Test
	public void test() {
		Connection  conn= JDBCUtils.getConnection();
		System.out.println(conn);
		
		JDBCUtils.release(conn);
		System.out.println(conn);
	}

}
