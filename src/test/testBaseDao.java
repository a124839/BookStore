package test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import domain.Book;
import impl.BaseDao;
import impl.BookDaoImpl;

public class testBaseDao {

//	private BaseDao baseDao = new BaseDao<>();
	
	private BookDaoImpl bookDaoImpl = new BookDaoImpl();
	
	@Test
	public void testInsert() {
		String sql = "INSERT INTO trade (userid, tradetime) VALUES(?,?)";
		long id = bookDaoImpl.insert(sql, 1,new Date(new java.util.Date().getTime()));
		
		System.out.println(id);
		
	}
	
	@Test
	public void testUpdate(){
		String sql = "UPDATE mybooks SET salesamount = ? WHERE id = ?";
		bookDaoImpl.update(sql, 50, 4);
	}
	
	@Test
	public void testQuery(){
		String sql = "SELECT id, author,title, price, publishingDate, salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
		Book book = bookDaoImpl.query(sql, 5);
		System.out.println(book);
		
	}
	
	@Test
	public void testQueryForList(){
		String sql = "SELECT id, author,title, price, publishingDate, salesAmount, storeNumber, remark FROM mybooks WHERE id < ?";
		List<Book> book = bookDaoImpl.queryForList(sql, 6);
		System.out.println(book);
		
	}
	
	@Test
	public void testGetSingleVal(){
		String sql = "SELECT count(id) FROM mybooks";
		long count = bookDaoImpl.getSingleVal(sql);
		System.out.println(count);
		
	}
	
	@Test
	public void testBatch(){
		String sql = "UPDATE mybooks SET salesAmount = ?, storeNumber = ? WHERE id = ?";
		bookDaoImpl.batch(sql, new Object[]{20, 20, 1}, new Object[]{2, 30, 2}, new Object[]{3, 40, 3} );
		
	}

}
