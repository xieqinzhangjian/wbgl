package xymz.pojo;

import java.util.Date;

public class HardwareInfo {

	private Integer id;
	private String name;//硬件名称
	private Date createDate;//销售日期
	private String expirationDate;//保质期
	private String sn; //序列号
	
	private String price;//单价
	private String costing; //成本
	private SellHardware sellHardware;
	private String payment;//以付金额
	private String status;//状态
	private String percentage;//提成比例
	private String remarks;//备注
	private User salesman;//业务员
	private String salesmanName;//业务员
	
	private Integer number;
	private String hardware_detail;
	
	
	
	


	private int page;
	private int rows;
	private String sort;
	private String order;
	
	
	
	
	

	
	
	public SellHardware getSellHardware() {
		return sellHardware;
	}


	public void setSellHardware(SellHardware sellHardware) {
		this.sellHardware = sellHardware;
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



	public String getSalesmanName() {
		return salesmanName;
	}


	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}


	public String getHardware_detail() {
		return hardware_detail;
	}


	public void setHardware_detail(String hardware_detail) {
		this.hardware_detail = hardware_detail;
	}


	public User getSalesman() {
		return salesman;
	}


	public void setSalesman(User salesman) {
		this.salesman = salesman;
	}


	public String getPayment() {
		return payment;
	}


	public void setPayment(String payment) {
		this.payment = payment;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getPercentage() {
		return percentage;
	}


	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	
	public String getCosting() {
		return costing;
	}
	public void setCosting(String costing) {
		this.costing = costing;
	}

	
	
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}


	@Override
	public int hashCode() {
		
		return 0;
	}


	
}
