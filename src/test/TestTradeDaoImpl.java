package test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Set;

import org.junit.Test;

import dao.TradeDao;
import domain.Trade;
import domain.TradeItem;
import impl.TradeDaoImpl;

public class TestTradeDaoImpl {
	private TradeDao tradeDao = new TradeDaoImpl();
	@Test
	public void test() {
		Set<Trade> trades = tradeDao.geTradesWithUserId(1);
		System.out.println(trades.toString());
	}
	@Test
	public void test2(){
//		Trade trade = new Trade(91, new Date(new java.util.Date().getTime()), tradeItems, userId)
		Trade trade = new Trade();
//		trade.setTradeId(99);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(1);
		
		tradeDao.insert(trade);
		
	}

}
