package service;

import java.util.Iterator;
import java.util.Set;

import dao.BookDao;
import dao.TradeDao;
import dao.TradeItemDao;
import dao.UserDao;
import domain.Trade;
import domain.TradeItem;
import domain.User;
import impl.BookDaoImpl;
import impl.TradeDaoImpl;
import impl.TradeItemDaoImpl;
import impl.UserDaoImpl;

public class UserService {
	private UserDao userDao = new UserDaoImpl();

	public User getUserByUserName(String userName) {
		User user = userDao.getUser(userName);
		return user;
	}
	
	private TradeDao tradeDao = new TradeDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	public User getUserByTrades(String userName) {
		User user = getUserByUserName(userName);
		if (user == null) {
			return null;
		}
		int userId = user.getUserId();
		
		Set<Trade> trades = tradeDao.geTradesWithUserId(userId);
		if (trades != null) {
			Iterator<Trade> tradeIt = trades.iterator();
				while (tradeIt.hasNext()) {
					Trade trade = (Trade) tradeIt.next();
					
					int tradeId = trade.getTradeId();
					Set<TradeItem> tradeItems = tradeItemDao.geTradeItemsWithTradeId(tradeId);
					
					if (tradeItems != null) {
						for (TradeItem tradeItem : tradeItems) {
							tradeItem.setBook(bookDao.getBook(tradeItem.getBookId()));
							
						}
						if (tradeItems != null && tradeItems.size() != 0) {
							trade.setTradeItems(tradeItems);
						}
					}
					if (tradeItems == null || tradeItems.size() == 0) {
						tradeIt.remove();					
					}
					
				}
				if (trades != null && trades.size() != 0) {
					user.setTrades(trades);
				}
		}
		return user;
		
	}
	
//	private TradeDao tradeDao = new TradeDaoImpl();
//	public User getUserAndTrade(Integer userId) {
//		tradeDao.geTradesWithUserId(userId);
//		
//		return null;
//	}
	
}
