package xymz.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xymz.dao.ProblemDao;
import xymz.pojo.Classify;
import xymz.pojo.Problem;
import xymz.pojo.WB;

@Repository("problemDao")
public class ProblemDaoImpl extends BaseDaoImpl<Problem> implements ProblemDao {

	@Override
	public List<Problem> pageQuery(Problem problem, String hql) {
		Session session = getSession();

		
		List<Problem> problemList= session.createQuery(hql).setFirstResult((problem.getPage()-1)*(problem.getRows())).setMaxResults(problem.getRows()).list();
		return problemList;
	}

	@Override
	public void merge(Problem problem) {
		Session session = getSession();
		session.merge(problem);
	}

	
	// 用chart 表现统计形式
	@Override
	public List statisticByClassify(Date startDate, Date endDate) {
		String hql = "select p.classify,count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate group by p.classify order by count(*) desc ";		
		List queryList= getSession().createQuery(hql).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		return queryList;
	}
	
	
	
	
	
	public Long statisticByClassifyByparent(Date startDate, Date endDate,Classify classify){
		
		String hql = "select count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate and p.classify=:classify ";		
		 Query q =null;
		 Long count =  (Long) getSession().createQuery(hql).setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("classify", classify).uniqueResult();
		
		return count;
	} 

	
	
	
	
	@Override
	public List statisticByUser(Date startDate, Date endDate,String type) {
		if("on_line".equals(type.trim())){
			//按照线上
			String on_linecCount = "select p.user,count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate group by p.user order by count(*) desc ";		
			List queryList= getSession().createQuery(on_linecCount).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
			return queryList;	
		}else{
			//线下
			String visitCount = "select p.appoint,count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate group by p.appoint order by count(*) desc ";	
			List queryList= getSession().createQuery(visitCount).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
			return queryList;	
		}
	}

	
	@Override
	public List statisticByWB(Date startDate, Date endDate,String type,int number) {
		if("on_line".equals(type.trim())){
			//按照线上
			String on_linecCount = "select p.wb,count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate and p.appoint is null group by p.wb order by count(*) desc ";		
			List queryList= getSession().createQuery(on_linecCount).setParameter("startDate", startDate).setParameter("endDate", endDate).setFirstResult(0).setMaxResults(number).list();
			return queryList;	
		}else{
			//线下
			String visitCount = "select p.wb,count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate and p.appoint is not null group by p.wb order by count(*) desc ";	
			List queryList= getSession().createQuery(visitCount).setParameter("startDate", startDate).setParameter("endDate", endDate).setFirstResult(0).setMaxResults(number).list();
			return queryList;	
		}
	}

	@Override
	public Long statisticByClassifyByparentAndParent(Date startDate, Date endStart, Classify parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List statisticByWBAll(Date startDate, Date endDate, int number) {
		String hql = "select p.wb,count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate group by p.wb order by count(*) desc ";		
		//查询所有
		List list = getSession().createQuery(hql).setParameter("startDate", startDate).setParameter("endDate", endDate).setFirstResult(0).setMaxResults(number).list();
		return list;
	}

	@Override
	public Long countByWB(Date startDate, Date endDate,String type, WB wb ,int number) {
		
		if("on_line".equals(type.trim())){
			String hql = "select count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate and p.wb=:wb and p.appoint is null";		
			 Long count =  (Long) getSession().createQuery(hql).setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("wb", wb).setFirstResult(0).setMaxResults(number).uniqueResult();	
			return count;	
		}else{
			String hql = "select count(*) from Problem p where p.createDate>=:startDate and p.createDate<=:endDate and p.wb=:wb and p.appoint is not null";		
			 Long count =  (Long) getSession().createQuery(hql).setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("wb", wb).setFirstResult(0).setMaxResults(number).uniqueResult();	
			return count;	
		}
		
	}
}
