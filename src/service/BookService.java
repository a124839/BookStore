package service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import dao.AccountDao;
import dao.BookDao;
import dao.TradeDao;
import dao.TradeItemDao;
import dao.UserDao;
import domain.Book;
import domain.ShoppingCart;
import domain.ShoppingCartItem;
import domain.Trade;
import domain.TradeItem;
import impl.AccountDaoImpl;
import impl.BookDaoImpl;
import impl.TradeDaoImpl;
import impl.TradeItemDaoImpl;
import impl.UserDaoImpl;
import web.CriteriaBook;
import web.Page;

/**一般来说开发时需要service 包中定义接口
 * @author k570
 *
 */
public class BookService {
	
	private BookDao bookDao = new BookDaoImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook) {
		
		return bookDao.getPage(criteriaBook);
	}

	public Book getBook(int id) {
		
		return bookDao.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDao.getBook(id);
		if (book != null) {
			sc.add(book);
			return true;
		}
		
		return false;
	}

	public void removeItem(int id, ShoppingCart sc) {
		sc.removeItem(id);
		
	}

	public void clear(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(int id, int quantity, ShoppingCart sc) {
		sc.updateItemQuantity(id, quantity);
	}
	
	private AccountDao accountDao = new AccountDaoImpl();
	private TradeDao tradeDao = new TradeDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	public void bill(ShoppingCart sc, String userName, String accountIdStr) {
		//这块事务怎么写？
		//1.更新mybooks数据表相关记录的salesamout和storenum
		Collection<ShoppingCartItem> items = sc.getItems();
		bookDao.batchUpdateStoreNumAndSalesAmount(items);
		//2.更新account数据表中的balance
		accountDao.updateBalance( Integer.parseInt(accountIdStr), sc.getTotalMoney());
//		int i = 10 / 0;
		//3.向trade数据表插入一条记录
		  //获得tradeId怎么获得？--->tradeId在tradeDaoImpl中得到了，由time和id得到所以这里只要time和id就好
		  //获得tradeItem
		  //获得userId
		  //获得tradeTime
//		int userId = userDao.getUser(userName).getUserId();
//		Set<Trade> trade = tradeDao.geTradesWithUserId(userId);
//		tradeItemDao.geTradeItemsWithTradeId(tradeId);
		Trade trade = new Trade();
		trade.setUserId(userDao.getUser(userName).getUserId());
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDao.insert(trade);
		//4.想tradeItem数据表插入n条记录
		Collection<TradeItem> items2 = new ArrayList<>();
		for (ShoppingCartItem shoppingCartItem : sc.getItems()) {
			TradeItem tradeItem = new TradeItem();
			tradeItem.setBookId(shoppingCartItem.getBook().getId());
			tradeItem.setQuantity(shoppingCartItem.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items2.add(tradeItem);
		}
		tradeItemDao.batchSave(items2);
		//5.清空购物车
		sc.clear();
	}

	



	
}
