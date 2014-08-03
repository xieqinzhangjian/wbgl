package xymz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xymz.dao.SellMovieDao;
import xymz.dao.WBDao;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.MoviePayLog;
import xymz.pojo.Problem;
import xymz.pojo.SellMovie;
import xymz.pojo.User;
import xymz.pojo.WB;
import xymz.service.SellMovieService;

@Service("sellMovieService")
public class SellMovieServiceImpl implements SellMovieService {

	@Resource
	private WBDao wBDao;

	@Resource
	private SellMovieDao sellMovieDao;

	@Override
	public Json save(SellMovie sellMovie,String name) {
		Json j = new Json();
		MoviePayLog mpl = new MoviePayLog();

		mpl.setCost(sellMovie.getCost());// 初始金额
		mpl.setRemarks("初始数据");// 初始数据
		mpl.setPayDate(sellMovie.getPaymentDate()); // 续费日期
		mpl.setEndUseDate(sellMovie.getEndUseDate());// 续费的时候设置的到期时间
		mpl.setUserName(name);//操作人
		sellMovie.getMoviePayLog().add(mpl);
		sellMovieDao.save(sellMovie);

		j.setSuccess(true);
		j.setMsg("添加成功");
		return j;
	}

	@Override
	public DataGrid dataGrid(SellMovie sellMovie) {
		DataGrid dg = new DataGrid();

		String fromClause = "from SellMovie s";
		String whereClause = "";
		String orderByClause = "";

		Map<String, Object> parameters = new HashMap<String, Object>();

		// 条件
		if (sellMovie.getWbName() != null && !"".equals(sellMovie.getWbName().trim())) {
			whereClause += " where s.wbName like :wbName ";
			parameters.put("wbName", "%%" + sellMovie.getWbName() + "%%");
			
			System.out.println("网吧名称=="+sellMovie.getWbName());
		}

		// 区域
		if (sellMovie.getScope() != null && !"".equals(sellMovie.getScope())) {
			if (!"".equals(whereClause)) {
				whereClause += " and s.scope like:scope";
				parameters.put("scope", "%%" +sellMovie.getScope()+"%%");
			} else {
				whereClause += " where s.scope like:scope";
				parameters.put("scope", "%%" +sellMovie.getScope()+"%%");
			}

		}

		if (sellMovie.getRegNumber() != null && !"".equals(sellMovie.getRegNumber())) {
			if (!"".equals(whereClause)) {
				whereClause += " and s.regNumber like:regNumber";
				parameters.put("regNumber", "%%" +sellMovie.getRegNumber()+"%%" );
			} else {
				whereClause += " where s.regNumber like:regNumber";
				parameters.put("regNumber", "%%" + sellMovie.getRegNumber()+"%%" );
			}

		}

		if (sellMovie.getCost() != null && !"".equals(sellMovie.getCost())) {
			if (!"".equals(whereClause)) {
				whereClause += " and s.cost like:cost";
				parameters.put("cost", "%%" +sellMovie.getCost()+"%%");
			} else {
				whereClause += " where s.cost like:cost";
				parameters.put("cost", "%%" +sellMovie.getCost()+"%%");
			}

		}

		// 排序
		if (sellMovie.getSort() != null && !"".equals(sellMovie.getSort())) {
			orderByClause += " order by " + "s." + sellMovie.getSort() + " " + sellMovie.getOrder();
		}

		if (sellMovie.getState() == null || "".equals(sellMovie.getState().trim())) {
			if (!"".equals(whereClause)){
				whereClause += " and s.state=:state";
			}else{
				whereClause += " where  s.state=:state";
				
			}
			parameters.put("state", "正常使用");
		}

		if (!"查看全部".equals(sellMovie.getState())) {
			// whereClause.replace("s.state=:state", "");
			// parameters.remove("state");
			if (sellMovie.getState() != null && !"".equals(sellMovie.getState())) {
				if (!"".equals(whereClause)) {
					whereClause += " and s.state=:state";
					parameters.put("state", sellMovie.getState());
				} else {
					whereClause += " where s.state=:state";
					parameters.put("state", sellMovie.getState());
				}

			}

		}

		String hql = fromClause + whereClause + orderByClause;
		
		System.out.println("hql==="+hql);
		
		List<SellMovie> sellMovieList = sellMovieDao.find(hql, parameters, sellMovie.getPage(), sellMovie.getRows());

		dg.setTotal(sellMovieDao.count("select count(*) " + hql, parameters));

		dg.setRows(sellMovieList);

		dg.setSuccess(true);

		return dg;
	}

	public Json updateXuFei(User user, SellMovie sellMovie) {
		Json j = new Json();
		// 准备续费日志
		MoviePayLog mpl = new MoviePayLog();
		mpl.setCost(sellMovie.getCost());// 续费金额
		mpl.setPayDate(sellMovie.getPayDate());// 设置续费日期
		mpl.setEndUseDate(sellMovie.getEndUseDate());

		if (sellMovie.getRemarks() != null && !"".equals(sellMovie.getRemarks().trim())) {
			mpl.setRemarks(sellMovie.getRemarks()); // 续费的备注

		}
		mpl.setUserName(user.getName());// 续费的操作人

		// 更新当前续费的SellMovie
		SellMovie newSellMovie = sellMovieDao.get(sellMovie.getId());
		newSellMovie.setEndUseDate(sellMovie.getEndUseDate());
		newSellMovie.setState(sellMovie.getState());
		newSellMovie.getMoviePayLog().add(mpl);
		newSellMovie.setCost(sellMovie.getCost());
		newSellMovie.setStartUseDate(sellMovie.getPayDate());
		newSellMovie.setIsCharge("是");

		sellMovieDao.save(newSellMovie);

		j.setMsg("续费成功");
		j.setSuccess(true);
		return j;
	}

	@Override
	public DataGrid xuFeiLogBySellMovie(SellMovie sellMovie) {
		DataGrid dg = new DataGrid();
		SellMovie newSellMovie = sellMovieDao.get(sellMovie.getId());
		Set<MoviePayLog> sellMovieSet = newSellMovie.getMoviePayLog();
		List<MoviePayLog> sellMovieList = new ArrayList<MoviePayLog>();
		sellMovieList.addAll(sellMovieSet);
		dg.setTotal((long) sellMovieList.size());
		dg.setRows(sellMovieList);
		return dg;
	}

	@Override
	public Json delete(int id) throws Exception {
		Json j = new Json();
		try {
			sellMovieDao.delete(id);
		} catch (Exception e) {
			j.setMsg("删除失败,请联系管理员");
			j.setSuccess(false);
			e.printStackTrace();
		}
		j.setMsg("删除成功");
		j.setSuccess(true);
		return j;
	}

	@Override
	public void updateDue(int id) {
		SellMovie sellMovie = sellMovieDao.get(id);
		sellMovie.setState("已过期");
		sellMovieDao.update(sellMovie);
	}

	@Override
	public Json update(SellMovie sellMovie) {
		SellMovie oldSellMovie = sellMovieDao.get(sellMovie.getId());
		Json j = new Json();
		if (sellMovie.getAddr() != null && !"".equals(sellMovie.getAddr())) {
			oldSellMovie.setAddr(sellMovie.getAddr());

		}
		if (sellMovie.getWbName() != null && !"".equals(sellMovie.getWbName())) {
			oldSellMovie.setWbName(sellMovie.getWbName());

		}
		if (sellMovie.getAgent() != null && !"".equals(sellMovie.getAgent())) {
			oldSellMovie.setAgent(sellMovie.getAgent());

		}
		if (sellMovie.getContact() != null && !"".equals(sellMovie.getContact())) {
			oldSellMovie.setContact(sellMovie.getContact());

		}

		if (sellMovie.getCost() != null && !"".equals(sellMovie.getCost())) {
			oldSellMovie.setCost(sellMovie.getCost());

		}
		if (sellMovie.getIsCharge() != null && !"".equals(sellMovie.getIsCharge())) {
			oldSellMovie.setIsCharge(sellMovie.getIsCharge());

		}
		if (sellMovie.getIsMyGameSoft() != null && !"".equals(sellMovie.getIsMyGameSoft())) {
			oldSellMovie.setIsMyGameSoft(sellMovie.getIsMyGameSoft());

		}
		if (sellMovie.getRegNumber() != null && !"".equals(sellMovie.getRegNumber())) {
			oldSellMovie.setRegNumber(sellMovie.getRegNumber());

		}
		if (sellMovie.getScope() != null && !"".equals(sellMovie.getScope())) {
			oldSellMovie.setScope(sellMovie.getScope());

		}

		if (sellMovie.getStartUseDate() != null) {
			oldSellMovie.setStartUseDate(sellMovie.getStartUseDate());

		}
		if (sellMovie.getEndUseDate() != null) {
			oldSellMovie.setEndUseDate(sellMovie.getEndUseDate());

		}
		if (sellMovie.getState() != null && !"".equals(sellMovie.getState())) {
			oldSellMovie.setState(sellMovie.getState());

		}
		if (sellMovie.getWbTel() != null && !"".equals(sellMovie.getWbTel())) {
			oldSellMovie.setWbTel(sellMovie.getWbTel());

		}
		if (sellMovie.getPaymentDate() != null) {
			oldSellMovie.setPaymentDate(sellMovie.getPaymentDate());

		}

		try {
			sellMovieDao.update(oldSellMovie);
			j.setMsg("更新成功");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("更新失败,请与管理员联系");
			j.setSuccess(false);
			e.printStackTrace();
			return j;
		}

		return j;
	}

}
