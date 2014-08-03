package xymz.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	public void save(T t);
	public void update(T t);
	public void delete(int id);
	public T get(Integer id);
	public List<T> query();
	public Long count (String hql, Map<String, Object> params);
	
	public void createSQLQuery(String sql);
	
	
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) ;

	public List<T>  conditionFind(String hql,String key,Object value);
	
	
}
