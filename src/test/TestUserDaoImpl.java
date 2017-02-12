package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.UserDao;
import domain.User;
import impl.UserDaoImpl;

public class TestUserDaoImpl extends UserDaoImpl {

	private UserDao userDao = new UserDaoImpl();
	
	@Test
	public void test() {
		User user = userDao.getUser("EE");
		
		System.out.println(user);
	}

}
