package dao;

import domain.User;

public interface UserDao {
	/**�����û�����ȡ User ����
	 * @param userName
	 * @return
	 */
	public abstract User getUser(String userName);
}
