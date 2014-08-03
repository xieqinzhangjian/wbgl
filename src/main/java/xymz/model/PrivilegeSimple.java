package xymz.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrivilegeSimple implements java.io.Serializable {

	private static final long serialVersionUID = -4041846212302855579L;

	private Integer id;
	private String name;
	private String state;

	List<PrivilegeSimple> children = new ArrayList<PrivilegeSimple>();

	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PrivilegeSimple> getChildren() {
		return children;
	}

	public void setChildren(List<PrivilegeSimple> children) {
		this.children = children;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}