package xymz.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static Date createDate(){
		Date date = new Date();
		
		return null;
	}
	
	// 计算开始时间
	public static String getStartDate(int dayCount) {
		// 1.创建日期对象
		Date date = nDaysAfterOneDate(new Date(), dayCount);
		// 2.获取日期格式化对象
		DateFormat df = DateFormat.getDateInstance();
		// 自定义格式
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = df.format(date);
		System.out.println(strDate);
		return strDate;
	}

	// 获取结束时间
	public static String getEndDate() {
		// 1.创建日期对象
		Date date = new Date();
		// 2.获取日期格式化对象
		DateFormat df = DateFormat.getDateInstance();
		// 自定义格式
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = df.format(date);
		return strDate;
	}

	// 计算工具
	private static Date nDaysAfterOneDate(Date basicDate, int n) {
		long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 - n)
				* (24 * 60 * 60 * 1000);
		basicDate.setTime(nDay);
		return basicDate;
	}

	/** ++++++++++++++++++++++++++++++++++++++++++++++++++++++ **/
	public static String getStartDate2() {
		// 1.创建日期对象
		Date date = nDaysAfterOneDate(new Date(), 20);
		// 2.获取日期格式化对象
		DateFormat df = DateFormat.getDateInstance();
		// 自定义格式
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = df.format(date);
		System.out.println(strDate);
		return strDate;
	}

	//当前日期减 指定天数,返回字符串
	public static String nDaysAfterOneDate2(int n) {
		Date date = new Date();
		long nDay = (date.getTime() / (24 * 60 * 60 * 1000) + 1 - n)
				* (24 * 60 * 60 * 1000);
		date.setTime(nDay);
		return formatDate(date);
	}
	
	
	//当前日期减 指定天数,返回Date
	public static Date nDaysAfterOneDate(int n) {
		Date date = new Date();
		long nDay = (date.getTime() / (24 * 60 * 60 * 1000) + 1 - n)
				* (24 * 60 * 60 * 1000);
		date.setTime(nDay);
		return date;
	}
	
	
	public static String nDaysAfterOneDate3(int n) {
		Date date = new Date();
		long nDay = (date.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
				* (24 * 60 * 60 * 1000);
		date.setTime(nDay);
		return formatDate(date);
	}
	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	

	//把长字符串日期转成短字符串日期
	public static String toShortDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		String shortDate=null;
		try {
			date = format.parse(str);
			
			SimpleDateFormat format2 = new SimpleDateFormat("MM/dd");
			shortDate = format2.format(date);
			return shortDate;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shortDate;
	}
	


	//字符串转毫秒值
	public static Long shortStrToLongDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("MM/dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long longTime = date.getTime();
		return longTime;
	}
	
	public static String shortDateStrJJ(String startDate,int intDay) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		Date dt;
		try {
			dt = sdf.parse(startDate);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.YEAR, -0);// 日期减1年
			rightNow.add(Calendar.MONTH, 0);// 日期加3个月
			rightNow.add(Calendar.DAY_OF_YEAR, intDay);// 日期加10天
			Date dt1 = rightNow.getTime();
			String reStr = sdf.format(dt1);
			return reStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	}

	
	
	//字符串转毫秒值
	public static Long StrToLongDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long longTime = date.getTime();
		return longTime;
	}
	

	// 日期加加
	public static String dateJJ() {

		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(new Date(d.getTime() + 3 * 24 * 60 * 60 * 1000));
	}

	
	
	public static String dateStrJJ(String startDate,int intDay) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt;
		String reStr=null;
		try {
			dt = sdf.parse(startDate);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.YEAR, -0);// 日期减1年
			rightNow.add(Calendar.MONTH, 0);// 日期加3个月
			rightNow.add(Calendar.DAY_OF_YEAR, intDay);// 日期加10天
			Date dt1 = rightNow.getTime();
			reStr = sdf.format(dt1);
			return reStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reStr;
	
	}
	
	//计算每天的开始和结束时间
	public static String[] getse(String sDate) {
		String [] start_EndDate=new String[2];
		String startDate=null;
		String endDate=null;
		for (int i = 0; i < 2; i++) {
			String reStr = dateStrJJ(sDate,i);		
			if(startDate!=null){
				endDate=reStr;
			}else{
				startDate=reStr;
			}					
		}
		start_EndDate[0]=startDate;
		start_EndDate[1]=endDate;
		return start_EndDate;		
	}
	
	public static void main1(String[] args) {
		
		String [] start_EndDate =new String[2];
		start_EndDate[0]="2011-08-01 08:08";
		start_EndDate[1]="2011-08-01 08:08";
		for(int x=1;x<100;x++){
			if("2011-10-01 08:08".equals(start_EndDate[1])){
				break;
			}
			start_EndDate = getse(start_EndDate[1]);	
			System.out.println(start_EndDate[0]);
			System.out.println(start_EndDate[1]);
			
		}
	}
	
	// 日期格式转换
	public static String formatDate(Date date) {
		// 2.获取日期格式化对象
		DateFormat df = DateFormat.getDateInstance();
		// 自定义格式
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = df.format(date);
		
		//然后把字符串再转换为date返回
		
		return strDate;
	}
	
	public static Date formatDate2(Date date) {
		// 2.获取日期格式化对象
		DateFormat df = DateFormat.getDateInstance();
		// 自定义格式
		df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		//然后把字符串再转换为date返回
		Date newDate = null;
		try {
			newDate = df.parse(df.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
	public static void main(String[] args) {
		System.out.println(nDaysAfterOneDate(30));
	}
}
