package impl;

import java.util.HashSet;
import java.util.Set;

import dao.TradeDao;
import domain.Trade;

public class TradeDaoImpl extends BaseDao<Trade> implements TradeDao {

	
	@Override
	public void insert(Trade trade) {
		String sql = "INSERT INTO trade ( userid , tradetime) VALUES(?, ?)";
//		int tradeId = trade.getTradeId();
//		int userId = trade.getUserId();
//		Date tradeTime = trade.getTradeTime();
//		insert(sql, tradeId, userId, tradeTime);
		long tradeId = insert(sql, trade.getUserId(),trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> geTradesWithUserId(Integer userId) {
		String sql = "SELECT tradeId, tradeTime FROM trade WHERE userId = ?";
		
		return new HashSet<>(queryForList(sql, userId));
	}

}
