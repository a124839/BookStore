package dao;

import domain.User;

public interface UserDao {
	/**根据用户名获取 User 对象
	 * @param userName
	 * @return
	 */
	public abstract User getUser(String userName);
}
