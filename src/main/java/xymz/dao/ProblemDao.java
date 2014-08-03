package xymz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import xymz.pojo.Classify;
import xymz.pojo.Problem;
import xymz.pojo.WB;

public interface ProblemDao extends BaseDao<Problem>{
	
	public List<Problem> pageQuery(Problem problem, String hql);
	public void merge(Problem problem);
	
	public List statisticByClassify(Date startDate,Date endDate);
	
	public Long statisticByClassifyByparent(Date startDate, Date endDate,Classify parent);
	public Long statisticByClassifyByparentAndParent(Date startDate, Date endDate,Classify parent);
	public List statisticByUser(Date startDate, Date endDate,String type);
	public List statisticByWB(Date startDate, Date endDate,String type,int number);
	
	public List statisticByWBAll(Date startDate, Date endDate,int number);
	public Long countByWB(Date startDate, Date endDate, String type,WB wb,int number);

}
