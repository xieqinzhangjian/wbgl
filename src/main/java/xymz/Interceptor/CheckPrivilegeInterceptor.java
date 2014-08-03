package xymz.Interceptor;

import xymz.pojo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		User user   = (User) ActionContext.getContext().getSession().get( "user" );
		String actionName = invocation.getProxy().getActionName();
		
		if(user==null){
			if(actionName.startsWith("user_login.action")){
				 return invocation.invoke();
			}else{
				return "返回登录页面";
			}
	
		}else{
			
			
		}
		
		
		
		return null;
	}

}
