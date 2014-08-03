package xymz.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.DepartmentDao;
import xymz.pojo.Department;
@SuppressWarnings("unchecked")
@Repository("departmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl<Department>implements DepartmentDao {




	public List<Department> pageQuery(Department department, String hql) {
		Session session = getSession();
		List<Department> departmentList= session.createQuery(hql).setFirstResult((department.getPage()-1)*(department.getRows())).setMaxResults(department.getRows()).list();
		return departmentList;
	}


	@Override
	public List<Department> getAllParent(String hql) {
		List<Department> dtpList = getSession().createQuery(hql).list();
		
		return  dtpList;
	}

}
