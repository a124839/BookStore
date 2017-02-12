package impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.BookDao;
import domain.Book;
import domain.ShoppingCartItem;
import web.CriteriaBook;
import web.Page;

public class BookDaoImpl extends BaseDao<Book> implements BookDao{

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author,title, price, publishingDate, salesAmount, "
				+ "storeNumber, remark FROM mybooks WHERE id = ?";		
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<Book>(cb.getPageNo());
		
		page.setTotalItemNum(getTotalBookNum(cb));
		
		//У��pageNo�Ϸ���
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		
		return page;
	}

	@Override
	public long getTotalBookNum(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	/* LIMIT ����Ϊ���Ƶ�ҳ������0 ��ʼ����ÿҳ�ĸ���
	 * @see dao.BookDao#getPageList(web.CriteriaBook, int)
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id, author,title, price, publishingDate, salesAmount, "
				+ "storeNumber, remark FROM mybooks WHERE price >= ? AND price <= ? "
				+ "LIMIT ?,?";
		
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (cb.getPageNo() - 1 ) * pageSize, 
				pageSize);
	
	}

	@Override
	public int getStoreNum(Integer id) {
		String sql = "SELECT storeNumber From mybooks WHERE id = ?";
		
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, storeNumber = storeNumber - ? WHERE id = ?";
		//��һ��Ƚ���Ҫ����collection������뵽arrayList�� �½�һ��List����
		List<ShoppingCartItem> sci = new ArrayList<>(items);
		Object[][] params = new Object[items.size()][3];
		for (int i = 0; i < items.size(); i++) {
//			params[0] = sci.getBook().getSalesAmount();
//			params[1] = sci.getBook().getStoreNumber();
			params[i][0] = sci.get(i).getQuantity();
			params[i][1] = sci.get(i).getQuantity();
			params[i][2] = sci.get(i).getBook().getId();
		}
		batch(sql, params);
	}
	
}
