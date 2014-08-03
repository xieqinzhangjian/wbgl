package xymz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import xymz.dao.ClassifyDao;
import xymz.dao.ProblemDao;
import xymz.dao.StatusDao;
import xymz.dao.UserDao;
import xymz.dao.WBDao;
import xymz.model.ClassifyCount;
import xymz.model.ProblemSimple;
import xymz.model.UserCount;
import xymz.model.WBCount;
import xymz.pojo.Classify;
import xymz.pojo.DataGrid;
import xymz.pojo.Json;
import xymz.pojo.Problem;
import xymz.pojo.Status;
import xymz.pojo.User;
import xymz.pojo.WB;
import xymz.service.ProblemService;
import xymz.service.WBservice;
import xymz.utils.DateUtil;
import xymz.utils.HqlHelper;
import xymz.utils.HtmlUtil;

@Service("problemService")
public class ProblemServiceImpl implements ProblemService {

	@Resource
	private ProblemDao problemDao;
	@Resource
	private WBDao wBDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StatusDao statusDao;
	@Resource
	private ClassifyDao classifyDao;

	@Override
	public Json save(Problem problem) {
		Json j = new Json();
		// 处理网吧的问题
		String wbName = problem.getWbName();
		WB wb = wBDao.queryByName(wbName);

		if (wb != null) {
			problem.setWb(wb);
			problem.setWbName(wbName);
		} else {
			WB newWB = new WB();
			newWB.setName(problem.getWbName());
			newWB.setWbTel(problem.getWbTel());
			newWB.setContact(problem.getContact());
			newWB.setAddr(problem.getAddr());
			wBDao.save(newWB);

			wb = wBDao.queryByName(wbName);
			problem.setWb(wb);
			problem.setWbName(wbName);
		}

		// 保存事务
		// 1.保存分类
		Classify classify = classifyDao.get(Integer.parseInt(problem.getClassifyName()));
		problem.setClassify(classify);
		problem.setClassifyName(classify.getText());

		// 2.保存状态
		Status status = statusDao.get(Integer.parseInt(problem.getStatusName()));
		problem.setStatus(status);
		problem.setStatusName(status.getText());

		// 3.保存时间
		if (problem.getCreateDate() == null) {
			problem.setCreateDate(new Date());
		}

		// 4.保存受理人

		problemDao.save(problem);

		j.setSuccess(true);
		j.setMsg("保存成功");
		return j;
	}

	@Override
	public Json delete(Problem problem) {
		Json j = new Json();
		try {
			problemDao.delete(problem.getId());
			j.setMsg("删除成功");
			j.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			j.setMsg("删除失败");
			j.setSuccess(false);
			j.setMsg("删除成功");

		}
		return j;

	}

	@Override
	public Json updata(Problem problem) {
		Json j = new Json();
		Problem oldProblem = problemDao.get(problem.getId());
		// 处理网吧
		if (!problem.getWbName().equals(oldProblem.getWbName())) {
			WB wb = wBDao.queryByName(problem.getWbName());
			if (wb != null) {
				if (!wb.getAddr().equals(problem.getAddr())) {
					wb.setAddr(problem.getAddr());

				}
				if (!wb.getContact().equals(problem.getContact())) {
					wb.setContact(problem.getContact());
				}
				if (!wb.getWbTel().equals(problem.getWbTel())) {

					wb.setWbTel(problem.getWbTel());
				}
				// wBDao.update(wb);
				oldProblem.setWb(wb);
				oldProblem.setWbName(problem.getWbName());
			} else {
				WB newwb = new WB();
				newwb.setAddr(problem.getAddr());
				newwb.setContact(problem.getContact());
				newwb.setWbTel(problem.getWbTel());
				newwb.setName(problem.getWbName());
				wBDao.save(newwb);
				oldProblem.setWb(wBDao.queryByName(problem.getWbName()));
				oldProblem.setWbName(problem.getWbName());
			}
		} else {
			if (!problem.getAddr().equals(oldProblem.getWb().getAddr())) {
				oldProblem.getWb().setAddr(problem.getAddr());
			}
			if (!problem.getWbTel().equals(oldProblem.getWb().getWbTel())) {
				oldProblem.getWb().setWbTel(problem.getWbTel());
			}
			if (!problem.getContact().equals(oldProblem.getWb().getContact())) {
				oldProblem.getWb().setContact(problem.getContact());
			}

		}

		// 处理标题
		oldProblem.setTitle(problem.getTitle());
		oldProblem.setContent(problem.getContent());
		if (problem.getCreateDate() != null) {
			oldProblem.setCreateDate(problem.getCreateDate());
		}

		// 处理分类
		Classify classify = classifyDao.queryByText(problem.getClassifyName());
		if (classify == null) {
			Classify classify2 = classifyDao.get(Integer.parseInt(problem.getClassifyName()));
			if (classify2 != null) {
				oldProblem.setClassify(classify2);
				oldProblem.setClassifyName(classify2.getText());

			}

		}

		// 处理状态
		Status status = statusDao.queryByText(problem.getStatusName());
		if (status == null) {
			Status status2 = statusDao.get(Integer.parseInt(problem.getStatusName()));
			if (status2 != null) {
				oldProblem.setStatus(status2);
				oldProblem.setStatusName(status2.getText());
			}

		}

		// 来访日期
		if (problem.getCreateDate() != null) {
			oldProblem.setCreateDate(problem.getCreateDate());
		}

		try {
			if (oldProblem != null) {
				problemDao.merge(oldProblem);

			}
			j.setSuccess(true);
			j.setMsg("更新成功");

		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(true);
			j.setMsg("更新失败,请与管理员联系");
			return j;
		}
		return j;

	}

	@Override
	public DataGrid dataGrid(Problem problem) {

		String fromClause = "from Problem u ";
		String whereClause = "";
		String orderByClause = "";

		DataGrid dg = new DataGrid();
		// 1.准备参数
		Map<String, Object> parameters = new HashMap<String, Object>();

		HqlHelper hqlHelper = new HqlHelper(problem);

		// 条件
		// 标题
		if (problem.getTitle() != null && !"".equals(problem.getTitle().trim())) {
			whereClause += "where u.title like :title";
			parameters.put("title", "%%" + problem.getTitle() + "%%");
		}
		// 开始时间
		if (problem.getStartCreateDate() != null && !"".equals(problem.getStartCreateDate().trim())) {
			Date startDate = DateUtil.strToDate(problem.getStartCreateDate());

			if (whereClause.length() == 0) {
				whereClause = " where u.createDate>=:startCreateDate ";
			} else {
				whereClause += " and u.createDate>=:startCreateDate ";
			}
			parameters.put("startCreateDate", startDate);
		}
		// 截至时间
		if (problem.getEndCreateDate() != null && !"".equals(problem.getEndCreateDate().trim())) {
			Date endDate = DateUtil.strToDate(problem.getEndCreateDate());
			if (whereClause.length() == 0) {
				whereClause = " where u.createDate>=:endCreateDate ";
			} else {
				whereClause += " and u.createDate<=:endCreateDate ";
			}
			parameters.put("endCreateDate", endDate);
		}
		// 来访网吧
		if (problem.getWbName() != null && !"".equals(problem.getWbName().trim())) {
			String hql = "from WB w where w.name=:name ";
			WB wb = wBDao.queryByName(problem.getWbName());
			if (wb != null) {
				if (whereClause.length() == 0) {
					whereClause = " where u.wb=:wb ";
				} else {
					whereClause += " and u.wb=:wb ";
				}
				parameters.put("wb", wb);

			}

		}
		// 受理人
		if (problem.getUserName() != null && !"".equals(problem.getUserName().trim())) {
			User user = userDao.get(Integer.parseInt(problem.getUserName()));
			if (user != null) {
				if (whereClause.length() == 0) {
					whereClause = " where u.user=:user ";
				} else {
					whereClause += " and u.user=:user ";
				}
				parameters.put("user", user);
			}
		}

		// 线下受理人
		if (problem.getAppointName() != null && !"".equals(problem.getAppointName().trim())) {
			User user = userDao.get(Integer.parseInt(problem.getAppointName()));
			if (user != null) {
				if (whereClause.length() == 0) {
					whereClause = " where u.appoint=:appoint ";
				} else {
					whereClause += " and u.appoint=:appoint ";
				}
				parameters.put("appoint", user);
			}
		}

		// 状态
		if (problem.getStatusName() != null && !"".equals(problem.getStatusName().trim())) {
			Status status = statusDao.get(Integer.parseInt(problem.getStatusName()));
			if (whereClause.length() == 0) {
				whereClause = " where u.status=:status ";
			} else {
				whereClause += " and u.status=:status ";
			}
			parameters.put("status", status);
		}

		// 分类
		if (problem.getClassifyName() != null && !"".equals(problem.getClassifyName().trim())) {
			Classify classify = classifyDao.get(Integer.parseInt(problem.getClassifyName()));
			if (whereClause.length() == 0) {
				whereClause = " where u.classify=:classify ";
			} else {
				whereClause += " and u.classify=:classify ";
			}
			parameters.put("classify", classify);

		}
		if (parameters.size() > 0) {
			hqlHelper.addCondition(parameters);
		}

		// 排序
		if (problem.getSort() != null && !"".equals(problem.getSort())) {
			// hqlHelper.addOrder(problem.getSort(), problem.getOrder());
			orderByClause += " order by " + problem.getSort() + " " + problem.getOrder();

		}

		String hql = fromClause + whereClause + orderByClause;

		List<Problem> problemList = problemDao.find(hql, parameters, problem.getPage(), problem.getRows());

		if (problemList.size() != 0) {
			List<ProblemSimple> problemSimpleList = new ArrayList<ProblemSimple>();
			for (Problem oldproblem : problemList) {
				ProblemSimple problemSimple = new ProblemSimple();
				problemSimple.setId(oldproblem.getId());
				problemSimple.setTitle(oldproblem.getTitle());

				// problemSimple.setContent(oldproblem.getContent());

				BeanUtils.copyProperties(oldproblem, problemSimple);

				problemSimple.setContent(HtmlUtil.html(problemSimple.getContent()));
				problemSimple.setCreateDateString(oldproblem.getCreateDate().toLocaleString());
				if (oldproblem.getWbName() == null) {
					if (oldproblem.getWb() != null) {
						problemSimple.setWbName(oldproblem.getWb().getName());
					}

				}
				if (oldproblem.getUserName() == null || "".equals(oldproblem.getUserName().trim())) {

					if (oldproblem.getUser() != null) {
						problemSimple.setUserName(oldproblem.getUser().getName());

					}

				}

				if (oldproblem.getStatusName() == null || "".equals(oldproblem.getStatusName().trim())) {
					if (oldproblem.getStatus() != null) {

						problemSimple.setStatusName(oldproblem.getStatus().getText());
					}

				}

				System.out.println("oldproblem.getAppointName()==" + oldproblem.getAppointName());
				if (oldproblem.getAppointName() == null || "".equals(oldproblem.getAppointName().trim())) {
					if (oldproblem.getAppoint() != null) {
						problemSimple.setAppointName(oldproblem.getAppoint().getName());
					}
				}

				if (oldproblem.getLastUpdateTime() != null) {
					problemSimple.setLastUpdateDateString(oldproblem.getLastUpdateTime().toLocaleString());

				}

				problemSimpleList.add(problemSimple);
			}
			dg.setRows(problemSimpleList);
			// dg.setRows(problemList);
			dg.setTotal(problemDao.count("select count(*) " + hql, parameters));
			return dg;

		}
		return dg;

	}

	@Override
	public ProblemSimple get(int id) {
		ProblemSimple ps = new ProblemSimple();

		Problem p = problemDao.get(id);

		ps.setTitle(p.getTitle());
		ps.setId(p.getId());
		ps.setUserName(p.getUser().getName());
		if (p.getAppoint() != null) {
			ps.setAppointName(p.getAppoint().getName());

		}
		ps.setStatusName(p.getStatus().getText());
		if (p.getClassify() != null) {

			ps.setClassifyName(p.getClassify().getText());

		}

		ps.setContent(html(p.getContent()));

		// 网吧
		ps.setWbName(p.getWb().getName());
		ps.setWbTel(p.getWb().getWbTel());
		ps.setWbAddr(p.getWb().getAddr());
		if (p.getWb().getContact() != null) {
			ps.setContact(p.getWb().getContact());
		}

		return ps;
	}

	public String html(String content) {
		if (content == null)
			return "";
		String html = content;
		html = StringUtils.replace(html, "'", "&apos;");
		html = StringUtils.replace(html, "\"", "&quot;");
		html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
		html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
		html = StringUtils.replace(html, "<", "&lt;");
		html = StringUtils.replace(html, ">", "&gt;");
		return html;
	}

	public static void main(String[] args) {
		String str = "<p>随机，不固定机器，黑屏2秒</p><p>换显卡驱动测试了，原来的驱动是</p><p></p><p><br /></p><p>换为剑灵官方推荐的331.93</p><p><br /></p>";

	}

	@Override
	public Json problemById(int id) {
		Json j = new Json();
		Problem p = problemDao.get(id);
		j.setSuccess(true);
		j.setObj(p);
		return j;
	}

	// 用chart 表现统计形式
	@Override
	public List statisticByClassify(String startDate, String endStart) {
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);
		} else {
			sDate = DateUtil.nDaysAfterOneDate(720);

		}

		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}
		// 获取所有父类菜单

		List<Classify> parentClassifyList = classifyDao.queryAllparent();
		Long TotalCount = 0l;

		List queryList = problemDao.statisticByClassify(sDate, eDate);
		List ccList = new ArrayList();

		for (Classify parentClassify : parentClassifyList) {
			Object[] cc = new Object[2];

			Long tempTotalCount = 0l;
			String tempClaasifyText = parentClassify.getText();

			for (int i = 0; i < queryList.size(); i++) {
				Classify classify = null;
				Long count = 0l;
				Object o[] = (Object[]) queryList.get(i);
				for (int j = 0; j < o.length; j++) {

					if (o[j] instanceof Classify) {
						classify = (Classify) o[j];
					} else {
						count = (Long) o[j];
					}
				}

				if (classify.getParent() != null) {
					if (classify.getParent().equals(parentClassify)) {
						tempTotalCount += count;
					}
				}
				if (classify.equals(parentClassify)) {
					tempTotalCount += count;
				}
			}

			if (tempTotalCount != 0l) {
				cc[0] = tempClaasifyText;
				cc[1] = tempTotalCount;
				TotalCount += tempTotalCount;
				ccList.add(cc);

			}

		}
		System.out.println("chart总=" + TotalCount);
		return ccList;
	}

	// 用表格表现统计
	public DataGrid claasifyAndCount(String startDate, String endStart) {

		//
		DataGrid dg = new DataGrid();
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(720);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}

		// 获取所有父类菜单

		List<Classify> parentClassifyList = classifyDao.queryAllparent();
		Long TotalCount = 0l;
		List queryList = problemDao.statisticByClassify(sDate, eDate);

		List ccList = new ArrayList();

		for (Classify parentClassify : parentClassifyList) {
			Long tempTotalCount = 0l;
			String tempClaasifyText = parentClassify.getText();
			ClassifyCount cc = new ClassifyCount();
			for (int i = 0; i < queryList.size(); i++) {
				Classify classify = null;
				Long count = 0l;
				Object o[] = (Object[]) queryList.get(i);
				for (int j = 0; j < o.length; j++) {

					if (o[j] instanceof Classify) {
						classify = (Classify) o[j];
					} else {
						count = (Long) o[j];
					}
				}

				if (classify.getParent() != null) {
					if (classify.getParent().equals(parentClassify)) {
						tempTotalCount += count;
					}
				}
				if (classify.equals(parentClassify)) {
					tempTotalCount += count;
				}
			}

			if (tempTotalCount != 0l) {
				cc.setClassifyText(tempClaasifyText);
				cc.setCount(tempTotalCount);
				TotalCount += tempTotalCount;
				ccList.add(cc);

			}

		}
		dg.setRows(ccList);
		dg.setTotal(TotalCount);
		return dg;
	}

	@Override
	public List statisticByUserChart(String startDate, String endStart, String type) {
		DataGrid dg = new DataGrid();
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(7200);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}
		List userCountList = new ArrayList();

		List ucList = problemDao.statisticByUser(sDate, eDate, type);
		for (int i = 0; i < ucList.size(); i++) {
			Object[] userCount = new Object[2];
			User user = null;
			Long conut = 0l;
			Object o[] = (Object[]) ucList.get(i);
			for (int j = 0; j < o.length; j++) {

				if (o[j] instanceof User) {
					user = (User) o[j];
					userCount[j] = user.getName();
				} else {
					userCount[j] = o[j];
				}
			}
			userCountList.add(userCount);
		}

		return userCountList;
	}

	@Override
	public List statisticByUserTable(String startDate, String endStart, String type) {
		DataGrid dg = new DataGrid();
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(7200);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}

		List<UserCount> userCountList = new ArrayList<UserCount>();
		List ucList = problemDao.statisticByUser(sDate, eDate, type);
		for (int i = 0; i < ucList.size(); i++) {
			UserCount uc = new UserCount();
			User user = null;
			Long conut = 0l;
			Object[] o = (Object[]) ucList.get(i);
			for (int j = 0; j < o.length; j++) {

				if (o[j] instanceof User) {
					user = (User) o[j];
					uc.setUserName(user.getName());
				} else {
					conut = (Long) o[j];
					if (user != null) {
						uc.setCount(conut);
					}
				}
			}
			userCountList.add(uc);
		}

		return userCountList;
	}

	@Override
	public DataGrid statisticByWBTable(String startDate, String endStart, String type, int number) {
		//
		DataGrid dg = new DataGrid();
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(720);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}
		List queryList = problemDao.statisticByWB(sDate, eDate, type, number);
		List wBCountList = dg.getRows();

		for (int i = 0; i < queryList.size(); i++) {
			WBCount wbc = new WBCount();
			WB wb = null;
			Long wbCount = 0l;
			Object[] o = (Object[]) queryList.get(i);
			for (int j = 0; j < o.length; j++) {
				if (o[j] instanceof WB) {
					wb = (WB) o[j];
				} else {
					wbCount = (Long) o[j];
				}
			}
			wbc.setWbName(wb.getName());
			wbc.setCount(wbCount);
			wBCountList.add(wbc);

		}
		dg.setRows(wBCountList);
		return dg;
	}

	// 根据父分类查询具体
	@Override
	public DataGrid statisticByClassifyByparentTable(String startDate, String endStart, String classifyName) {
		DataGrid dg = new DataGrid();
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(7200);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}

		Classify classifyParten = classifyDao.queryByText(classifyName);

		// 获取所有父类菜单
		Set<Classify> childenClassifyList = classifyParten.getChildren();
		List<ClassifyCount> ccList = new ArrayList<ClassifyCount>();
		Long totalCount = 0l;

		for (Classify childenClassify : childenClassifyList) {
			ClassifyCount cc = new ClassifyCount();
			Long count = problemDao.statisticByClassifyByparent(sDate, eDate, childenClassify);
			if (count != 0l) {
				cc.setClassifyText(childenClassify.getText());
				cc.setCount(count);
				totalCount += count;
				ccList.add(cc);
			}

		}

		// 还有自己本身
		Long count = problemDao.statisticByClassifyByparent(sDate, eDate, classifyParten);
		ClassifyCount cc = new ClassifyCount();

		if (totalCount != 0l) {
			cc.setClassifyText(classifyParten.getText());
			cc.setCount(count);
			totalCount += count;
			ccList.add(cc);
		}

		dg.setRows(ccList);
		dg.setTotal(totalCount);

		return dg;
	}

	@Override
	public List statisticByClassifyByparentChart(String startDate, String endStart, String classifyName) {
		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(7200);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}

		Classify classifyParten = classifyDao.queryByText(classifyName);
		List ccList = new ArrayList();
		// 获取所有父类菜单
		Set<Classify> childenClassifyList = classifyParten.getChildren();

		for (Classify childenClassify : childenClassifyList) {
			Object[] cc = new Object[2];
			Long count = problemDao.statisticByClassifyByparent(sDate, eDate, childenClassify);

			if (count != 0l) {
				cc[0] = childenClassify.getText();
				cc[1] = count;
				ccList.add(cc);

			}

		}

		Long count = problemDao.statisticByClassifyByparent(sDate, eDate, classifyParten);
		Object[] cc = new Object[2];

		if (count != 0l) {

			cc[0] = classifyParten.getText();
			cc[1] = count;
			ccList.add(cc);
		}

		return ccList;
	}

	@Override
	public List statisticByWBChart(String startDate, String endStart, int number) {

		Date sDate = null;
		Date eDate = null;
		if (startDate != null && !"".equals(startDate.trim())) {
			sDate = DateUtil.strToDate(startDate);

		} else {
			sDate = DateUtil.nDaysAfterOneDate(7200);

		}
		if (endStart != null && !"".equals(endStart.trim())) {
			eDate = DateUtil.strToDate(endStart);

		} else {
			eDate = new Date();

		}
		// 查询所有
		List ccAllList = problemDao.statisticByWBAll(sDate, eDate, number);
		List<WBCount> tempList = new ArrayList<WBCount>();

		List wbcCountList = new ArrayList();

		for (int i = 0; i < ccAllList.size(); i++) {

			WBCount wbc = new WBCount();
			Object[] o = (Object[]) ccAllList.get(i);
			WB wb = null;
			Long totalCount = 0l;
			Long xsCount = 0l;
			Long xxCount = 0l;
			// 1.获取总数
			for (int j = 0; j < o.length; j++) {
				if (o[j] instanceof WB) {
					wb = (WB) o[j];
				} else {
					totalCount = (Long) o[j];
				}
			}
			// 2.根据当前获取的总数分别获取线上与线下
			xsCount = problemDao.countByWB(sDate, eDate, "on_line", wb, number);
			xxCount = problemDao.countByWB(sDate, eDate, "visit", wb, number);
			wbc.setCount(totalCount);
			wbc.setWbName(wb.getName());
			wbc.setXsCount(xsCount);
			wbc.setXxCount(xxCount);
			tempList.add(wbc);
		}

		// 定义三个数组
		// 1.存放网吧名称　２.存放线下数量　３.存放线上数量
		String[] wbName = new String[number];
		Long[] xsCount = new Long[number];
		Long[] xxCount = new Long[number];
		Long[] count = new Long[number];

		for (int i = 0; i < tempList.size(); i++) {
			WBCount wbc = tempList.get(i);
			wbName[i] = wbc.getWbName();
			xsCount[i] = wbc.getXsCount();
			xxCount[i] = wbc.getXxCount();
			count[i] = wbc.getCount();

		}

		wbcCountList.add(wbName);
		wbcCountList.add(count);
		wbcCountList.add(xsCount);
		wbcCountList.add(xxCount);

		return wbcCountList;
	}

}
