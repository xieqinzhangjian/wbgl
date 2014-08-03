package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.model.UserSimple;
import xymz.pojo.DataGrid;
import xymz.pojo.Problem;
import xymz.pojo.User;

@Controller("problemAction")
@Scope("prototype")
public class ProblemAction extends BaseAction<Problem> {

	public ProblemAction() {

	}

	public void dataGrid() {
		DataGrid dataGrid = problemService.dataGrid(model);
		try {
			writeJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void save() {

		User user = (User) session.get("user");
		User u = userService.get(user.getId());

		// 保存受理人
		if (u != null) {
			model.setUser(u);
			model.setUserName(user.getName());
		}
		try {
			writeJson(problemService.save(model));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() {

		try {
			writeJson(problemService.delete(model));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void byProblemId() {
		try {
			writeJson(problemService.get(model.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void problemById() {

		try {
			writeJson(problemService.problemById(model.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit() {

		try {
			writeJson(problemService.updata(model));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void statisticsByClassify(){
		

		try {
			writeJson(problemService.statisticByClassify(model.getStartDate(), model.getEndDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void claasifyAndCount(){
		try {
			writeJson(problemService.claasifyAndCount(model.getStartDate(), model.getEndDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void statisticByUserTable(){		
		try {
			writeJson(problemService.statisticByUserTable(model.getStartDate(), model.getEndDate(), model.getType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void statisticByUserChart(){		
		try {
			writeJson(problemService.statisticByUserChart(model.getStartDate(), model.getEndDate(), model.getType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void statisticByClassifyByparentTable(){		
		try {
			writeJson(problemService.statisticByClassifyByparentTable(model.getStartDate(), model.getEndDate(), model.getClassifyName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void statisticByClassifyByparentChart(){		
		try {
			writeJson(problemService.statisticByClassifyByparentChart(model.getStartDate(), model.getEndDate(), model.getClassifyName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void statisticByWBTable(){
		
		try {
			writeJson(problemService.statisticByWBTable(model.getStartDate(), model.getEndDate(), model.getType(), model.getNumber()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void statisticByWBChart(){
		try {
			writeJson(	problemService.statisticByWBChart(model.getStartDate(), model.getEndDate(),model.getNumber()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	;
	}
}
