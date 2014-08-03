package xymz.pojo;

import java.util.Set;
import java.util.TreeSet;

public class DataGridOfSet {
	
	private Long total=0l;
	private Set rows = new TreeSet();	
	private String msg="";
	private boolean success=true;
	
	
	

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Set getRows() {
		return rows;
	}
	public void setRows(Set rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}

	
}
