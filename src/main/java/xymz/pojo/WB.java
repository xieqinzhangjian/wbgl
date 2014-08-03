package xymz.pojo;

import java.util.HashSet;
import java.util.Set;

public class WB {
	private int id;
	private String name; //网吧名称
	private String contact;//联系人
	private String wbTel;//网吧电话
	private String addr;//网吧地址
	private String gameSoftName; //游戏更新软件
	private String movieSoftName;//电影
	
	private String hardwareInfo;//硬件销售
	private SellMovie sellMovie; //电影,与网吧一对一
	
	
	
	public SellMovie getSellMovie() {
		return sellMovie;
	}
	public void setSellMovie(SellMovie sellMovie) {
		this.sellMovie = sellMovie;
	}
	
	
	
	
	
	public String getGameSoftName() {
		return gameSoftName;
	}
	public void setGameSoftName(String gameSoftName) {
		this.gameSoftName = gameSoftName;
	}
	public String getMovieSoftName() {
		return movieSoftName;
	}
	public void setMovieSoftName(String movieSoftName) {
		this.movieSoftName = movieSoftName;
	}
	public String getHardwareInfo() {
		return hardwareInfo;
	}
	public void setHardwareInfo(String hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getWbTel() {
		return wbTel;
	}
	public void setWbTel(String wbTel) {
		this.wbTel = wbTel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
}
