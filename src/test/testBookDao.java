package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import dao.BookDao;
import domain.Book;
import domain.ShoppingCartItem;
import impl.BookDaoImpl;
import web.CriteriaBook;
import web.Page;

public class testBookDao {
	
	private BookDao bookDao = new BookDaoImpl();
	
	@Test
	public void testGetBook() {
		Book book = bookDao.getBook(6);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(0, 600, 60);
		Page<Book> page = bookDao.getPage(cb);
		System.out.println("pageNo :" + page.getPageNo());
		System.out.println("list :" + page.getList());
		System.out.println("totalItemNum :" + page.getTotalPageNum());
		System.out.println("prevPage :" + page.getPrevPage());
		System.out.println("nextPage :" + page.getNextPage());
		System.out.println(page.getPageSize());
	}

	@Test
	public void testGetTotalBookNum() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPageList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStoreNum() {
		int storeNum = bookDao.getStoreNum(6);
		System.out.println(storeNum);
	}

	@Test
	public void testBatchUpdateStoreNumAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<>();
		Book book = null;
		ShoppingCartItem sci = null;
		
			book = bookDao.getBook(1);
			sci = new ShoppingCartItem(book);
			sci.setQuantity(1);
			items.add(sci);
			
			book = bookDao.getBook(2);
			sci = new ShoppingCartItem(book);
			sci.setQuantity(2);
			items.add(sci);
			
			book = bookDao.getBook(3);
			sci = new ShoppingCartItem(book);
			sci.setQuantity(3);
			items.add(sci);
			
		
		bookDao.batchUpdateStoreNumAndSalesAmount(items);
		
	}

}
