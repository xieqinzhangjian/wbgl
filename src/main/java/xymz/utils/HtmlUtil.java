package xymz.utils;

import org.springframework.util.StringUtils;

public class HtmlUtil {
	public  static String html(String content) {
	    if(content==null) return "";       
	    String html = content;
	    html = StringUtils.replace(html, "'", "&apos;");
	    html = StringUtils.replace(html, "\"", "&quot;");
	    html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
	    html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
	    html = StringUtils.replace(html, "<", "&lt;");
	    html = StringUtils.replace(html, ">", "&gt;");
	    return html;
	}

}
