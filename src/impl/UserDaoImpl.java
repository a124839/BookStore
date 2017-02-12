package impl;

import dao.UserDao;
import domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public User getUser(String userName) {
		String sql = "SELECT userId, userName, accountID FROM userinfo WHERE userName = ? ";
		return query(sql, userName);
	}

}
