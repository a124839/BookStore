package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**购物车类
 * @author k570
 *
 */
public class ShoppingCart {
	
	private  Map<Integer, ShoppingCartItem> books = new HashMap<>();
	
	/**购物车商品添加方法
	 * @param book 
	 */
	public void add(Book book) {
		//首先要判断是否有购物车封装的商品对象，没有就创建一个并把book对象放到购物车商品对象中
		//如果有商品购物车对象，则把对应的book数量+1
		
		ShoppingCartItem sci = books.get(book.getId());
		
		if (sci == null) {
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		} else {
			sci.increatment();
		}
		
	}
	
	/**判断是否有此书籍方法,直接调用map中的方法：根据id判断是否有此对象
	 * @param id
	 * @return 
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}
	
	/**返回 Map<Integer, ShoppingCartItem>类型的 books对象
	 * @return books
	 */
	public Map<Integer, ShoppingCartItem> getBooks() {		
		
		return books;
	}
	
	/**返回Collection<ShoppingCartItem> 的对应collection对象
	 * @return 
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}
	
	/**返回书籍总量。其中foreach循环终止条件为Map类型的对象对应的Collection集合
	 * @return 返回总数量
	 */
	public int getBookNum() {
		int total = 0;
		for (ShoppingCartItem sci : books.values()) {
			total += sci.getQuantity();
			
		}
		
		return total;
	}
	
	/**
	 * @return 返回总钱数
	 */
	public float getTotalMoney() {
		int total = 0 ;
		
		for (ShoppingCartItem sci : getItems()) {
			total += sci.getItemMoney();
		}
		
		return total;
	}
	
	/**map对象中有判断是否为空的方法，直接调用
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}
	
	/**
	 * 清空购物车
	 */
	public void clear() {
		books.clear();
	}
	
	/**删除商品
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}
	
	/**修改购物车中商品的数量
	 * @param id
	 * @param quantity
	 */
	public void updateItemQuantity(Integer id,int quantity) {
		ShoppingCartItem sci = books.get(id);
		if (sci != null) {
			sci.setQuantity(quantity);
		}
	}
}
