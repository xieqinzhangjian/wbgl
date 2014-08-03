package xymz.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import xymz.dao.BaseDao;
import xymz.pojo.Department;

@SuppressWarnings("unchecked")
@Repository("baseDao")
@Lazy(true) // 因为这个类是交给spring 管理的,默认是一启动就立马实例化,否则会体构造方法异常,因为构造方法里的this也就是子类还没实例化导致
//Caused by: org.springframework.beans.BeanInstantiationException: Could not instantiate bean class [xymz.dao.impl.BaseDaoImpl]:
//Constructor(构造方法异常) threw exception; nested exception is java.lang.ClassCastException: 
//java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
public class BaseDaoImpl<T> implements BaseDao<T> {

	private Class clazz;

	public BaseDaoImpl() {

		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();

		// 返回表示此类型实际类型参数的 Type 对象的数组。
		clazz = (Class) type.getActualTypeArguments()[0];
	}

	@Resource
	protected SessionFactory sessionFactory = null;
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	//这个方法删除,不会级联删除,报异常
/*	public void delete(int id) {
		System.out.println("id========"+id);
		String hql = "delete from " + clazz.getSimpleName() + " where id=:id";
		sessionFactory.getCurrentSession().createQuery(hql).setInteger("id", id).executeUpdate();
		
	}*/
	
	public void delete(int id) {
		Object obj = sessionFactory.getCurrentSession().get(clazz, id);
		if(obj!=null){
			
			sessionFactory.getCurrentSession().delete(obj);
		}
	}

	@Override
	public T get(Integer id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@Override
	public List<T> query() {
		return sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName()).list();
	}
	
	//总记录数

	@Override
	public void createSQLQuery(String sql) {
		getSession().createSQLQuery(sql);
	}
	
	
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	public Long count (String hql, Map<String, Object> params) {
		Query q = this.getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public List<T> conditionFind(String hql, String key, Object value) {
		return getSession().createQuery(hql).setParameter(key, value).list();
	}


	
	

}
