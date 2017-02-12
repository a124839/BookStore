package impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.TradeItemDao;
import domain.TradeItem;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDao {

	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql = "INSERT INTO tradeItem(bookId, quantity, tradeId)"
				+ "VALUES(?, ?, ?)";
		
		//这个是什么意思？
		List<TradeItem> tradeItems = new ArrayList<>(items);
		Object [][] params = new Object[items.size()][3];
		for (int i = 0; i < tradeItems.size(); i++) {
			params[i][0] = tradeItems.get(i).getBookId();
			params[i][1] = tradeItems.get(i).getQuantity();
			params[i][2] = tradeItems.get(i).getTradeId();
		}
		batch(sql, params);
	}

	@Override
	public Set<TradeItem> geTradeItemsWithTradeId(Integer tradeId) {
		String sql = "SELECT itemId tradeItemId, bookId, "+"quantity, tradeId FROM tradeitem WHERE tradeid = ?";
//		String sql = "SELECT itemId tradeItemId, bookId, " +
//				"quantity, tradeId FROM tradeitem WHERE tradeid = ?";
		return new HashSet<>(queryForList(sql, tradeId));
	}

}
