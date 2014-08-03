package xymz.dao;

import java.util.List;

import xymz.pojo.User;

public interface UserDao extends BaseDao<User>{
	
	public List<User> pageQuery(User u,String hql) ;
	public User userByname(String name);
}
