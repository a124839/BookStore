package dao;

import java.util.List;

/**
 * @author k570
 * Dao�ӿڣ�����Dao�Ļ�����������BaseDao�ṩʵ��
 * @param <T> : ʵ�ʲ����ķ�������
 */
public interface Dao<T> {
	/**ִinsert ���������ز����ļ�¼��id
	 * @param sql ��ִ�е�sql���
	 * @param args ���ռλ���Ŀɱ����
	 * @return �����м�¼��id
	 */
	long insert(String sql, Object ... args );
	/**ִ��update ����������insert ��û�з���ֵ��update�� delete
	 * @param sql
	 * @param args
	 */
	void update(String sql, Object ... args	);
	/**ִ�е�����¼��ѯ�������������¼��Ӧ�����һ������
	 * @param sql
	 * @param args
	 * @return ���¼��Ӧ���һ������
	 */
	T query(String sql, Object ... args );
	/**ִ�ж�����¼�Ĳ�ѯ�������������¼��Ӧ�����һ��list
	 * @param sql
	 * @param args
	 * @return 
	 */
	List<T> queryForList(String sql, Object ... args);
	/**ִ��һ�����Ի�ֵ�Ĳ�ѯ�����������ѯĳһ����¼��һ���ֶΣ����ѯĳ��ͳ����Ϣ������Ҫ��ѯ��ֵ
	 * @param sql
	 * @param args
	 * @return
	 */
	<V> V getSingleVal(String sql, Object ... args	);
	/**ִ���������²���
	 * @param sql
	 * @param args
	 */
	void batch(String sql, Object[] ... params );
}
