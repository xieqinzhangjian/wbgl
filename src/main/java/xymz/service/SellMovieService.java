package xymz.service;

import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.SellMovie;
import xymz.pojo.User;

public interface SellMovieService {
	public Json save(SellMovie sellMovie,String name);
	public DataGrid dataGrid(SellMovie sellMovie);
	public Json updateXuFei(User user,SellMovie sellMovie);
	public DataGrid xuFeiLogBySellMovie(SellMovie sellMovie);
	public Json delete(int id) throws Exception;
	public void updateDue(int id); //把使用时间到的设置已过期
	
	public Json update(SellMovie sellMovie);
	
}	
