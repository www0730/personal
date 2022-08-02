package com.zzptc.twds.controller;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzptc.twds.pojo.Classes;
import com.zzptc.twds.pojo.College;
import com.zzptc.twds.pojo.Courses;
import com.zzptc.twds.pojo.User;
import com.zzptc.twds.service.ClassesService;
import com.zzptc.twds.service.CollegeService;
import com.zzptc.twds.service.CoursesService;
import com.zzptc.twds.service.UserService;
import com.zzptc.twds.utils.MessageBean;

@Controller
@RequestMapping("/courses")
public class CoursesController {

	
	@Autowired
	private CollegeService collegeService;
	
	@Autowired
	private CoursesService coursesService;
	
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private UserService userService;
/*
	@Autowired
	private WorkteamService workteamService;*/
	
	//查询全部课程
	@RequestMapping("/selectAllCoures")
	@ResponseBody
	public List<Courses> selectAll() {
		Courses courses1=new Courses();	
		List<Courses> list=coursesService.selectAllCourses(courses1);
		return list;//所有课程列表
	}
	//查询全部课程
	@RequestMapping("/selectAll")//多处调用
	public ModelAndView selectAll(HttpSession session) {
		ModelAndView view=new ModelAndView();
		User user=(User)session.getAttribute("user");//从Session中取出key为user的对象。然后强制转换为User对象。然后赋值给user
		if(user.getRoleid()==3) {
			Courses courses1=new Courses();
			List<Courses> list=coursesService.selectAllCoursesBycId(courses1);//按照学院管理员学院课程查询
			view.addObject("list", list);
		}else {
			List<College> listCollege=collegeService.selectAll();
			Courses courses1=new Courses();
			List<Courses> list=coursesService.selectAllCourses(courses1);//教务查看所有课程
			view.addObject("listCollege", listCollege);
			view.addObject("list", list);
		}
		view.addObject("cId", -1);
		view.addObject("roleId", user.getRoleid());
		view.setViewName("courses/courses");
		return view;
	}
	
	
	//通过学院查询课程
	@RequestMapping("/selectAllBycId")
	public ModelAndView selectAllBycId(HttpSession session,String cId) {
		ModelAndView view=new ModelAndView();
		Courses courses1=new Courses();
			if(cId.equals("-1")) {
				List<Courses> list=coursesService.selectAllCourses(courses1);
				List<College> listCollege=collegeService.selectAll();
				view.addObject("cId", cId);
				view.addObject("listCollege", listCollege);
				view.addObject("list", list);//全部学院的全部课程
			}else {
				courses1.setcId(Integer.parseInt(cId));//获取课程所属学院编号
				List<Courses> list=coursesService.selectAllCoursesBycId(courses1);
				List<College> listCollege=collegeService.selectAll();
				view.addObject("listCollege", listCollege);
				view.addObject("list", list);
				view.addObject("cId", cId);
			}
		view.setViewName("courses/courses");
		return view;
	}
	

	
	//添加课程页面
	@RequestMapping("/addCoursesView")
	public ModelAndView insertView() {
		ModelAndView view=new ModelAndView();
		List<College> list=collegeService.selectAll();
		view.addObject("list", list);
		view.setViewName("courses/addCourses");
		return view;
	}//添加课程页面需选择所属学院
	/*
	 * 添加课程
	 * */
	@RequestMapping("/insert")
	@ResponseBody
	public MessageBean insert(HttpServletRequest request,String cId,String coName,String coType,String coWeek,String coWenum,String coTotal,String coTerm ) {
		MessageBean messageBean=new MessageBean("");
		int cId1=Integer.parseInt(cId);
		Double coWeek1=Double.parseDouble(coWeek);
		Double coTotal1=Double.parseDouble(coTotal);
		int coWenum1=Integer.parseInt(coWenum);
		Courses courses1=new Courses();
		courses1.setState("0");
		courses1.setSta(0);
		List<Courses> list=coursesService.selectAllCourses(courses1);
		boolean bl=false;
		if(coType.equals("研究生课程")) {
			for (Courses courses : list) {
				if(coName.equals(courses.getCoName())&&(cId1==(courses.getcId()))&&coTerm.equals(courses.getCoTerm())) {//课程名、学院 、开课学期一致判定为统一课程
					messageBean.setSuccess(false);
					messageBean.setMsg("该课程已存在!");
					bl=true;
					break;
				}
			}
		}else if(coType.equals("本科生课程")){
			for (Courses courses : list) {
				if(coName.equals(courses.getCoName())&&(cId1==(courses.getcId()))&&coTerm.equals(courses.getCoTerm())) {
					messageBean.setSuccess(false);
					messageBean.setMsg("该课程已存在!");
					bl=true;
					break;
				}
			}
		}

		if(!bl) {
			Courses courses=new Courses();
			courses.setcId(cId1);
			courses.setCoName(coName);
			courses.setCoType(coType);
			courses.setCoWeek(coWeek1);
			courses.setCoWenum(coWenum1);
			courses.setCoTotal(coTotal1);
			courses.setCoTerm(coTerm);
			courses.setState("0");
			courses.setSta(0);
			boolean bool=coursesService.insertSelective(courses);
			if(bool) {
				messageBean.setSuccess(true);
				messageBean.setMsg("添加成功");
			}else {
				messageBean.setSuccess(false);
				messageBean.setMsg("添加失败");
			}
		}
		
		return messageBean;//返回添加结果弹窗
	
	}
	
	    //删除指定课程
		@RequestMapping("/delete")
		@ResponseBody
		public MessageBean delete(String coId) {
			
			MessageBean messageBean=new MessageBean("");
			int coId1=Integer.parseInt(coId);
			boolean bool=coursesService.deleteByPrimaryKey(coId1);
			messageBean.setSuccess(bool);
			return messageBean;
			
		}
		        //修改指定课程数据
				@RequestMapping("/update")
				@ResponseBody
				public MessageBean update(String coId,String cId,String coName,String coType,String coWeek,String coWenum,String coTotal,String coTerm,String oldcoName) {
					MessageBean messageBean=new MessageBean("");
					int coId1=Integer.parseInt(coId);
					int cId1=Integer.parseInt(cId);
					Double coWeek1=Double.parseDouble(coWeek);
					Double coTotal1=Double.parseDouble(coTotal);
					int coWenum1=Integer.parseInt(coWenum);
					Courses courses1=new Courses();
					List<Courses> list=coursesService.selectAllCourses(courses1);
					boolean bl=false;
					if(coType.equals("本科课程")) {
						for (Courses courses : list) {
							if(coName.equals(courses.getCoName())&&(cId1==(courses.getcId()))&&coTerm.equals(courses.getCoTerm())) {
								messageBean.setSuccess(false);
								messageBean.setMsg("该课程已存在!");
								bl=true;
								break;
							}
						}
					}else if(coType.equals("研究生课程")){
						for (Courses courses : list) {
							if(coName.equals(courses.getCoName())&&(cId1==(courses.getcId()))&&coTerm.equals(courses.getCoTerm())) {
								messageBean.setSuccess(false);
								messageBean.setMsg("该课程已存在!");
								bl=true;
								break;
							}
						}
					}
				/*	if(coName.equals(oldcoName)) {
						bl=false;
					}*/
					if(!bl) {
						
						Courses courses=new Courses();
						courses.setCoId(coId1);
						courses.setcId(cId1);
						courses.setCoName(coName);
						courses.setCoType(coType);
						courses.setCoWeek(coWeek1);
						courses.setCoWenum(coWenum1);
						courses.setCoTotal(coTotal1);
						courses.setCoTerm(coTerm);
							boolean bool=coursesService.updateByPrimaryKeySelective(courses);
							if(!bool) {
								messageBean.setMsg("修改失败!");
							}else {
								messageBean.setMsg("修改成功!");
							}
							messageBean.setSuccess(bool);
						
					}
					
					return messageBean;
					
				}
				//填充分配课程页面(视图)
				@RequestMapping("/addDisCoursesView")
				public ModelAndView addDeclareTCoursesView(HttpSession session,String coId,String username) {
					ModelAndView view=new ModelAndView();
					Courses courses1=new Courses();
					courses1.setState("0");
					List<Classes> listClasses=classesService.selectAllClasses();//班级
					List<Courses> listcourses=coursesService.selectAllCourses(courses1);//未注销课程
					List<User> listUser=userService.selectAll();//教师

					int coId1=Integer.parseInt(coId);
					Courses courses=coursesService.selectByPrimaryKey(coId1);//根据课程名查询课程
					try {
						username=new String(username.getBytes("ISO-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(username.equals("请选择任课老师")) {
						view.addObject("username","");
					}else {
						view.addObject("username",username);
					}
					view.addObject("listUser",listUser);
					view.addObject("listcourses", listcourses);
					view.addObject("courses", courses);
					view.addObject("listClasses", listClasses);
					view.setViewName("courses/DisCourses");
					return view;
				}

				//分配课程(视图)
					@RequestMapping("/DisCoursesView")
					public ModelAndView insertView(HttpSession session) {
						ModelAndView view=new ModelAndView();
						User user=(User)session.getAttribute("user");
						Courses courses=new Courses();
						courses.setState("0");
						List<Courses> listcourses=coursesService.selectAllCourses(courses);
						List<User> listUser=userService.selectAll();
						List<Classes> listClasses=classesService.selectAllClasses();
						Iterator<User> iterator = listUser.iterator();
						while (iterator.hasNext()){//判断是否存在下一个元素
							User next = iterator.next();
							String name = next.getUsername();
							if (name.equals(user.getUsername())){
								iterator.remove();
							}
						}
						view.addObject("listcourses", listcourses);
						view.addObject("listUser",listUser);
						view.addObject("listClasses",listClasses);
						view.setViewName("courses/DisCourses");
						return view;
					}
					
					
	
}
