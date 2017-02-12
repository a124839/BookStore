package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import dao.TradeItemDao;
import domain.TradeItem;
import impl.TradeItemDaoImpl;

public class TestTradeItemDaoImpl {
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	@Test
	public void test() {
		Collection<TradeItem> items = new ArrayList<>();
		
		items.add(new TradeItem(null, 1, 2, 12));
		items.add(new TradeItem(null, 1, 3, 12));
		items.add(new TradeItem(null, 1, 1, 12));
//		items.add(new TradeItem(null, 4, 40, 30));
		
		tradeItemDao.batchSave(items);
	}
	@Test
	public void test2(){
		Set<TradeItem> items = tradeItemDao.geTradeItemsWithTradeId(13);
		System.out.println(items.toString());
		
		
	}

}
