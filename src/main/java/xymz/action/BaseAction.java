package xymz.action;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import xymz.service.ClassifyService;
import xymz.service.DepartmentService;
import xymz.service.HardwareInfoService;
import xymz.service.KnowledgeMenuService;
import xymz.service.MenuService;
import xymz.service.PrivilegeService;
import xymz.service.ProblemService;
import xymz.service.RoleService;
import xymz.service.SellHardwareService;
import xymz.service.SellMovieService;
import xymz.service.StatusService;
import xymz.service.TopicService;
import xymz.service.UserService;
import xymz.service.WBservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("baseAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class BaseAction<T> extends ActionSupport implements ApplicationAware, SessionAware, RequestAware, ModelDriven<T> {

	protected Map<String, Object> request;

	protected Map<String, Object> session;

	protected Map<String, Object> application;

	protected T model = null;

	@Resource
	protected UserService userService = null;
	
	@Resource
	protected MenuService menuService = null;
	
	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected RoleService roleService;	
	@Resource
	protected ProblemService problemService;
	@Resource
	protected StatusService statusService;
	
	@Resource
	protected ClassifyService classifyService;
	@Resource
	 protected WBservice wBService;

	@Resource
	protected PrivilegeService privilegeService;
	@Resource
	protected SellMovieService sellMovieService;
	@Resource
	protected SellHardwareService sellHardwareService;
	
	@Resource
	protected HardwareInfoService hardwareInfoService;
	@Resource
	protected KnowledgeMenuService knowledgeMenuService;
	@Resource
	protected TopicService topicService;
	
	
	public BaseAction() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class clazz = (Class) type.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void writeJson(Object obj) throws Exception {
		String json = JSON.toJSONString(obj);
		System.out.println("json==="+json);
		ServletActionContext.getResponse().getWriter().write(json);

	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;

	}

	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}

}
