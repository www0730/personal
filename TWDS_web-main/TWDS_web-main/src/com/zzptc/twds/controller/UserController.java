package com.zzptc.twds.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zzptc.twds.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzptc.twds.pojo.College;
import com.zzptc.twds.pojo.Role;
import com.zzptc.twds.pojo.User;

import com.zzptc.twds.service.RoleService;
import com.zzptc.twds.service.UserService;

import com.zzptc.twds.utils.MessageBean;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private RoleService roleService;
	
	private HttpSession session;

	//登录验证
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,String username,String password) {
		session=request.getSession();//通过request获取session
		ModelAndView view=new ModelAndView();
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user=userService.selectUserAndPassword(user);

		if(user!=null) {

			Role role=roleService.selectByPrimaryKey(user.getRoleid());
			session.setAttribute("user", user);//保存在session里
			session.setAttribute("role", role);
			session.setAttribute("roleid", role.getRoleid());
            session.setAttribute("cId", user.getCid());
			view.setViewName("main");
			session.setAttribute("name", user.getName());
			session.setAttribute("username", user.getUsername());
			session.setMaxInactiveInterval(30*60);//使当前会话停止后维持半小时
		}else {
			view.addObject("errorMsg", "用户名或密码错误");//添加模型数据 将要返回的数据加入到这个对象中
			view.setViewName("login");//设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面
			System.out.println("登录失败");
		}

		return view;
	}



	//退出登录
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		session=request.getSession();
		session.removeAttribute("user");//清空session中user
		session.removeAttribute("role");
		session.removeAttribute("username");
		return new ModelAndView("login");
	}
	
	
	
	//查询全部用户  教师没有此权限
	
	@RequestMapping("/user/selectAll")
	public ModelAndView selectAll(HttpSession session) {
		ModelAndView view=new ModelAndView();
		User user=(User)session.getAttribute("user");
		List<College> collegeList=collegeService.selectAll();//二级学院信息
		List<User> list=null;
		if(user.getRoleid()==3) {//学院管理员
			 list=userService.selectAllBycId(user.getCid());//查询本学院教师
		}else{//教务管理员
			 list=userService.selectAll();//全部用户信息
		}
		Iterator<User> iterator = list.iterator();//迭代器，iterator.hasNext()方法用来判断还有没有数据可访问
		while (iterator.hasNext()){
			User next = iterator.next();//next()用来访问下一个数据
			String username = next.getUsername();
			if (username.equals(user.getUsername())){
				iterator.remove();//查询的用户中没有当前用户
			}
		}
		session.setAttribute("roleid", user.getRoleid());
		view.addObject("list", list);
		view.addObject("collegeList", collegeList);
		view.setViewName("user/allUser");
		return view;
	}
	
	

	//查询全部用户
		@RequestMapping("/user/selectAllUser")
		@ResponseBody
		public List<User> selectAllUser() {
			List<User> list=userService.selectAll();
			return list;
		}
	
	

	
/*	//查询指定用户
	@RequestMapping("/user/selectByUserid")
	@ResponseBody
	public MessageBean selectByUserid(int userid) {
		MessageBean messageBean=new MessageBean("");
		User user=userService.selectByPrimaryKey(userid);
		messageBean.setOther(user);
		messageBean.setSuccess(true);		
		return messageBean;
	}*/



	//添加用户
	@RequestMapping("/user/insert")
	@ResponseBody
	public MessageBean insert(String roleid,String cId,String username,String password,String name,String email,String phone,String age,String education,String title ) {
		MessageBean messageBean=new MessageBean("");
		List<User> list=userService.selectAll();
		boolean bl=false;//以下foreach循环，把user类型的list中的每个元素赋值给user1
		for (User user1 : list) {
			if(username.equals(user1.getUsername())) {
				messageBean.setSuccess(false);
				messageBean.setMsg("该工号已存在!");
				bl=true;
				break;
			}
		}
		if(!bl) {
			User user=new User();
			int roleid1=Integer.parseInt(roleid);
			int cId1=Integer.parseInt(cId);
			int age1=Integer.parseInt(age);
			int education1=Integer.parseInt(education);
			int title1=Integer.parseInt(title);
			user.setRoleid(roleid1);
			user.setCid(cId1);
			user.setUsername(username);
			user.setPassword(password);
			user.setName(name);
			user.setEmail(email);
			user.setPhone(phone);
			user.setAge(age1);
			user.setTitle(title1);
			user.setEducation(education1);
			user.setState(0);
			boolean bool=userService.insertSelective(user);
			if(bool) {
				messageBean.setMsg("添加成功");
				messageBean.setSuccess(true);
			}else {
				messageBean.setMsg("添加失败");
				messageBean.setSuccess(false);
			}

		}



		return messageBean;
	}

	@RequestMapping("/user/insertUser")
	public ModelAndView insertView(HttpSession session) {
		ModelAndView view=new ModelAndView();
		User user=(User)session.getAttribute("user");
		session.setAttribute("roleid", user.getRoleid());
		session.setAttribute("cid", user.getCid());
		List<College> collegeList=collegeService.selectAll();//二级学院信息
		view.addObject("collegeList", collegeList);
		view.setViewName("user/insertUser");
		return view;
	}
	
	
	//删除指定用户
	@RequestMapping("/user/delete")
	@ResponseBody
	public MessageBean delete(HttpSession session,String username) {
		MessageBean messageBean=new MessageBean("");
		boolean bool=false;
		bool=userService.deleteByUserid(username);
		messageBean.setMsg("删除成功");
		messageBean.setSuccess(bool);
		return messageBean;
		
	}
	
	
	//根据userid更改数据
	@RequestMapping("/user/update")
	@ResponseBody
	public MessageBean update(String username,String roleid,String cId,String name,String email,String phone,String age,String education,String title) {
		MessageBean messageBean=new MessageBean("");

				int roleId=Integer.parseInt(roleid);
				int cid=Integer.parseInt(cId);
				int ages=Integer.parseInt(age);
				int educations=Integer.parseInt(education);
				int titles=Integer.parseInt(title);
				User user=new User();
				user.setUsername(username);
				user.setRoleid(roleId);
				user.setCid(cid);
				user.setName(name);
				user.setEmail(email);
				user.setPhone(phone);
		         user.setAge(ages);
		         user.setTitle(titles);
		       user.setEducation(educations);
				boolean bool=userService.updateByPrimaryKeySelective(user);
				if(bool) {
					messageBean.setMsg("修改成功");
				}else {
					messageBean.setMsg("修改失败!");
				}
				System.out.println(bool);
				messageBean.setSuccess(bool);
			
		//}
		
		return messageBean;
		
	}
	
	
	
	

}
