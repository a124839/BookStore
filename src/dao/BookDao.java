package dao;

import java.util.Collection;
import java.util.List;

import domain.Book;
import domain.ShoppingCartItem;
import web.CriteriaBook;
import web.Page;

public interface BookDao {
	/**根据id获取指定 Book 对象
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	/**根据传入的CriteriaBook 对象返回对应的Page对象
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	/**根据传入的CriteriaBook 对象返回记录条数
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNum(CriteriaBook cb);
	/**根据传入的CriteriaBook 和PageSize 返回当前页对应的List
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook cb, int pageSize);
	/**返回指定 id 的book 的storeNum 字段的值
	 * @param id
	 * @return
	 */
	public abstract int getStoreNum(Integer id);
	/**根据传入的 ShoppingCartItem 的集合批量跟新 books 数据表的 storenumber和salesnumber字段的值
	 * @param items
	 */
	public abstract void batchUpdateStoreNumAndSalesAmount(Collection<ShoppingCartItem> items);
}
