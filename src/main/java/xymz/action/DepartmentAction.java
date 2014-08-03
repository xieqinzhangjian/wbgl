package xymz.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.pojo.DataGrid;
import xymz.pojo.Department;
import xymz.pojo.DepartmentSimple;
import xymz.pojo.Json;

@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {

	public void dataGrid(){
		DataGrid dataGrid = departmentService.dataGrid(model);

		try {
			writeJson(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void departmentTree(){
		
		try {
			writeJson(departmentService.getDepartmentTree());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save(){
		Json j = departmentService.save(model);
		try {

			
			writeJson(j);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void delete(){
		Json j = departmentService.delete(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void edit(){
		Json j = departmentService.update(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void roleByDepartmentId(){
		System.out.println(model.getId());
	}
	
	public void departmentList(){
		Json j = departmentService.departmentList();
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void isParentOk(){
		
		Json j = departmentService.isParentOk(model);
		try {
			writeJson(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
