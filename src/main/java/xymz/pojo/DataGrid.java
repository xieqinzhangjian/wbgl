package xymz.pojo;

import java.util.ArrayList;
import java.util.List;

public class DataGrid {
	
	private Long total=0l;
	private List rows = new ArrayList();	
	private String msg="";
	private boolean success=true;
	private Object obj;
	
	
	
	
	

	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
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
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}

	
}
