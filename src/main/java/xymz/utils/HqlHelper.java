package xymz.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import xymz.pojo.User;

public class HqlHelper {
	private  String fromClause; // From子句，必须
	private  String whereClause = ""; // Where子句，可选
	private  String orderByClause = ""; // OrderBy子句，可选
	
	//保存参数
	Map<String,Object> parameters = new HashMap<String, Object>();
	
	
	//1.fromClause,别名默认为o
	public HqlHelper(Object obj){
		fromClause="from "+obj.getClass().getSimpleName()+" o";
		System.out.println("fromClause=="+fromClause);
	}
	
	//2.拼接查询条件
	public void addCondition(Map<String,Object> parameters,Object... params ){
		//

		
		if(parameters.size()>0 & parameters!=null){
			Set<String> keySet = parameters.keySet();
			for (String key : keySet) {
				if (whereClause.length() == 0) {
					
					whereClause=" where ";
				}else{
					whereClause +=" and ";
				}
				whereClause+=key+" "+parameters.get(key);
			}
		}
	}
	
	//是否排序
	public void addOrder(String propertyName, String order) {
		if (orderByClause.length() == 0) {
			orderByClause = " order by " + propertyName
					+ " "+order;
		} else {
			orderByClause += ", " + propertyName + " "+order;
		}
	}
	
	
	
	//查询总数
	public String getTotal(){
		
		return  "select count(*) "+fromClause+" "+whereClause;
	}
	
	
	//获取完整hql
	public String getHql(){
		
		return  fromClause+" "+whereClause+" "+orderByClause;
	}
	
}
