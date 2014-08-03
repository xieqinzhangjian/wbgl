package xymz.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


/**
 * 实体：岗位
 * 
 * @author tyg
 * 
 */
public class Role  implements Serializable{
	private Integer id;
	private String text;
	private String description;
	private Set<User> userSet = new HashSet<User>();

	
	private Department department;
	
	private Set<Privilege> privilegeSet = new TreeSet<Privilege>();
	

	//不参与映射
	private Integer departmentId;
	private String privilegeIds;
	
	
	
	


	public String getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}



	public Set<Privilege> getPrivilegeSet() {
		return privilegeSet;
	}

	public void setPrivilegeSet(Set<Privilege> privilegeSet) {
		this.privilegeSet = privilegeSet;
	}



	// 不参与映射
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String ids;
	private Integer parentId;


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
