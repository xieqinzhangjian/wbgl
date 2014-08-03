package xymz.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SellHardware {
	private Integer id;
	private String wbTel;// 联系电话
	private String contact; //
	private String wbName;

	private String wbAddr;//
	
	
	
	private Date lastupdateDate;
	
	private String hardware_detail;
	private String hardware_change; //硬件更换记录
	


	// 不参与映射
	private int page;
	private int rows;
	private String sort;
	private String order;
	
	
	public String getHardware_change() {
		return hardware_change;
	}
	public void setHardware_change(String hardware_change) {
		this.hardware_change = hardware_change;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWbTel() {
		return wbTel;
	}
	public void setWbTel(String wbTel) {
		this.wbTel = wbTel;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getWbName() {
		return wbName;
	}
	public void setWbName(String wbName) {
		this.wbName = wbName;
	}
	public String getWbAddr() {
		return wbAddr;
	}
	public void setWbAddr(String wbAddr) {
		this.wbAddr = wbAddr;
	}
	public Date getLastupdateDate() {
		return lastupdateDate;
	}
	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}
	public String getHardware_detail() {
		return hardware_detail;
	}
	public void setHardware_detail(String hardware_detail) {
		this.hardware_detail = hardware_detail;
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




	
	


}
