package dao;

import java.util.Collection;
import java.util.List;

import domain.Book;
import domain.ShoppingCartItem;
import web.CriteriaBook;
import web.Page;

public interface BookDao {
	/**����id��ȡָ�� Book ����
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	/**���ݴ����CriteriaBook ���󷵻ض�Ӧ��Page����
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	/**���ݴ����CriteriaBook ���󷵻ؼ�¼����
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNum(CriteriaBook cb);
	/**���ݴ����CriteriaBook ��PageSize ���ص�ǰҳ��Ӧ��List
	 * @param cb
	 * @param pageSize
	 * @return
	 */
	public abstract List<Book> getPageList(CriteriaBook cb, int pageSize);
	/**����ָ�� id ��book ��storeNum �ֶε�ֵ
	 * @param id
	 * @return
	 */
	public abstract int getStoreNum(Integer id);
	/**���ݴ���� ShoppingCartItem �ļ����������� books ���ݱ�� storenumber��salesnumber�ֶε�ֵ
	 * @param items
	 */
	public abstract void batchUpdateStoreNumAndSalesAmount(Collection<ShoppingCartItem> items);
}
