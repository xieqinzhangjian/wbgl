package xymz.service;

import java.util.List;

import xymz.model.ProblemSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.Problem;

public interface ProblemService {
	
	public Json save(Problem problem);
	public Json delete(Problem problem);
	public Json updata(Problem problem);
	public ProblemSimple get(int id);
	
	public DataGrid dataGrid(Problem problem);
	public Json problemById(int id);
	//按照分类
	public List statisticByClassify(String startDate, String endStart);
	public DataGrid claasifyAndCount(String startDate, String endStart);
	public DataGrid statisticByClassifyByparentTable(String startDate, String endStart,String classifyName);
	public List statisticByClassifyByparentChart(String startDate, String endStart,String classifyName);
	//按照员工
	public List statisticByUserChart(String startDate, String endStart,String type);
	public List statisticByUserTable(String startDate, String endStart,String type);
	
	public DataGrid statisticByWBTable(String startDate, String endStart,String type,int number);
	public List statisticByWBChart(String startDate, String endStart,int number);
	
	
}
