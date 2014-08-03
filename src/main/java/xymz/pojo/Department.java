package xymz.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 * 
 * @author tyg
 * 
 */
public class Department implements Serializable{
	private Integer id;
	private Set<User> users = new HashSet<User>();
	private Department parent;
	private Set<Department> children = new HashSet<Department>();
	
	
	private String tel;//部门电话
	private String fax;//传真

	private Set<Role> roles = new HashSet<Role>();
	private String text;
	private String description;
	

	
	
	// 不参与映射
	private int page;
	private int rows;
	private String sort;
	private String order;
	private String ids;
	private Integer parentId;
	

	
	
	
	
	


	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}





	public Set<User> getUsers() {
		return users;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}


}
