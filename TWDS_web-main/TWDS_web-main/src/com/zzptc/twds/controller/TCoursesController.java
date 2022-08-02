package com.zzptc.twds.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zzptc.twds.pojo.*;
import com.zzptc.twds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzptc.twds.utils.MessageBean;

@Controller
@RequestMapping("/tCourses")
public class TCoursesController {

	@Autowired
	private TCoursesService tCoursesService;
	@Autowired
	private FormulaService formulaService;
	@Autowired
	private ScienfiticService scienfiticService;
	@Autowired
	private CoursesService coursesService;

	@Autowired
	private ToworkloadService toworkloadService;

	private HttpSession session;
	//填充老师申报工作量页面(视图)
	@RequestMapping("/addDeclareTCoursesView")
	public ModelAndView addDeclareTCoursesView(HttpSession session,String coId) {
		ModelAndView view=new ModelAndView();
		int coId1=Integer.parseInt(coId);
		Courses courses=coursesService.selectByPrimaryKey(coId1);//根据课程编号查询课程
		String name=((User)session.getAttribute("user")).getName();
		String username=((User) session.getAttribute("getUsername")).getUsername();
		List<TCourses> list=tCoursesService.selectAllTCoursesByUserid(username);//通过工号查询每位教师课程相关信息
		for (TCourses tCourses : list) {
			if(tCourses.getCoId()==coId1) {
				view.addObject("Classes",tCourses.getClasses());
				break;
			}
		}
		view.addObject("list", list);
		view.addObject("name",name);
		view.addObject("courses", courses);
		view.setViewName("tCourses/addTCourses");
		return view;
	}
	
	
	//老师申报课程(视图)
		@RequestMapping("/DeclareTCoursesView")
		public ModelAndView insertView(HttpSession session) {
			ModelAndView view=new ModelAndView();
			String name=((User)session.getAttribute("user")).getName();
			view.addObject("name",name);
			String username= session.getAttribute("username").toString();
			List<TCourses> list=tCoursesService.selectAllTCoursesByUserid(username);
			view.addObject("list", list);
			view.setViewName("tCourses/addTCourses");
			return view;
		}


	//教学工作量申报界面，查询当前用户教学课程
	@RequestMapping("/selectAllCourses")
	public ModelAndView selectAllCourses(HttpSession session) {
		ModelAndView view=new ModelAndView();
		String username=session.getAttribute("username").toString();//返回该对象的字符串表示
		List<TCourses> list =tCoursesService.selectAllTCoursesByUserid(username);
		List<TCourses> tCoursesList=new ArrayList<>();
		for (TCourses tCourses : list) {//将TCourses类型的list赋值给tCourses
			double totalWorkload=0;
			Courses course=tCourses.getCourses();
			Classes calsee=tCourses.getClasses();
			Paras paras=formulaService.selectAllParas(0);
			if(course.getCoType().equals("本科课程")){
				totalWorkload+=course.getCoTotal()*paras.getBvalue();
			}
			if(course.getCoType().equals("研究生课程")){
				totalWorkload+=course.getCoTotal()*paras.getYvalue();
			}
			if(calsee.getClNum()<=40){
				totalWorkload+=course.getCoTotal()*paras.getRvalue1();
			}
			if(calsee.getClNum()>40&&calsee.getClNum()<=60){
				totalWorkload+=course.getCoTotal()*paras.getRvalue2();
			}
			if(calsee.getClNum()>60&&calsee.getClNum()<=100){
				totalWorkload+=course.getCoTotal()*paras.getRvalue3();
			}
			if(calsee.getClNum()>100){
				totalWorkload+=course.getCoTotal()*paras.getRvalue4();
			}
			if(course.getCoWenum()<=4){
				totalWorkload+=course.getCoTotal()*paras.getXfvalue1();

			}else{
				totalWorkload+=course.getCoTotal()*paras.getXfvalue2();
			}
			Integer tid=tCourses.getTid();
			String state=tCoursesService.searchState(tid);
			tCourses.setState(state);
			tCourses.setTotalTworkload(totalWorkload);
			tCoursesList.add(tCourses);

		}

		view.addObject("list", tCoursesList);
		view.addObject("size", tCoursesList.size());
		view.setViewName("tCourses/TeachWorkLoad");
		return view;
	}

	//查询当前用户其他工作量申报
	@RequestMapping("/DeclareTworkView")
	public ModelAndView DeclareTworkView(HttpSession session) {
		ModelAndView view=new ModelAndView();
		String username=session.getAttribute("username").toString();
		List<Toworkload> list =tCoursesService.selectDeclareTworkView(username);
		List<Toworkload> tWorkList=new ArrayList<>();
		for (Toworkload toWorkload : list) {
			double totalWorkload=0;
			Integer toId=toWorkload.getToid();//toid:其他工作量信息编号
			totalWorkload=toWorkload.getOworkload().getOvalue()*toWorkload.getOworkload().getPara();
			Integer state=tCoursesService.searchStates(toId);
			toWorkload.setState(state);
			toWorkload.setTotalWorkload(totalWorkload);
			tWorkList.add(toWorkload);

		}
		view.addObject("list", tWorkList);
		view.addObject("size", tWorkList.size());
		view.setViewName("tCourses/OtherWorkLoad");
		return view;
	}
	//查询当前用户科研工作量申报
	@RequestMapping("/DeclareScientificView")
	public ModelAndView DeclareScientificView(HttpServletRequest request) {
		ModelAndView view=new ModelAndView();
		session=request.getSession();
		String username=session.getAttribute("username").toString();
		String name=session.getAttribute("name").toString();
		request.setAttribute("username",username);
		request.setAttribute("name",name);
		List<Scientific> sclist=scienfiticService.selectAllScientific();
		request.setAttribute("sclist",sclist);
		List<Toscientific> list =tCoursesService.selectDeclareScientificView(username);
		//List<Toworkload> listToworkload=toworkloadService.selectByUserId(0);

		List<Toscientific> scientificList=new ArrayList<>();
		int  i=0;
		for (Toscientific scientific : list) {
			double totalWorkload=0;
			Integer toId=scientific.getToid();
			totalWorkload=scientific.getScientific().getValue()*scientific.getScientific().getPara();
			Integer state=tCoursesService.searchScientificState(toId);
			scientific.setState(state);
			scientific.setTotalWorkload(totalWorkload);
			scientificList.add(scientific);

		}
		//totalWorkload=0;
		//view.addObject("totalWorkload", totalWorkload);
		view.addObject("list", scientificList);
		view.addObject("size", scientificList.size());
		view.setViewName("tCourses/ScinetificWorkLoad");
		return view;
	}

			//查询全部用户其它工作量值
				@RequestMapping("/selectAllOworkload")
				public ModelAndView selectAllOworkload(HttpSession session) {
					ModelAndView view=new ModelAndView();
					List<Toworkload> listOworkload=toworkloadService.selectAllToworkload();
					
					view.addObject("listOworkload", listOworkload);
					view.setViewName("Oworkload/allOworkload");
					return view;
				}
			
			
			
			
		//给老师分配课程
		
		@RequestMapping("/insert")
		@ResponseBody
		public MessageBean insert(HttpServletRequest request,HttpSession session,String coId,String clId,String username){
			MessageBean messageBean=new MessageBean("");
			List<TCourses> list=tCoursesService.selectAll();
			int coId1=Integer.parseInt(coId);
			Courses courses=coursesService.selectByPrimaryKey(coId1);
			boolean bl=false;
			for (TCourses tCourses : list) {
				if(coId1==tCourses.getCoId()&&Integer.parseInt(clId)==tCourses.getClId()) {
					messageBean.setSuccess(false);
					messageBean.setMsg("该课程已经分配给这个班,不可重复分配!");
					bl=true;
					break;
				}
			}
			if(!bl) {
				int clId1=Integer.parseInt(clId);
				TCourses tCourses=new TCourses();
				tCourses.setClId(clId1);
				tCourses.setCoId(courses.getCoId());
				tCourses.setUsername(username);
				tCourses.setState("0");//已分配课程状态为0
				boolean bool=tCoursesService.insertSelective(tCourses);
				if(bool) {
					Courses courses1=new Courses();
					courses1.setCoId(clId1);
					courses1.setSta(1);
					bool=coursesService.updateByPrimaryKeySelective(courses1);
					messageBean.setSuccess(true);
					messageBean.setMsg("添加成功");
				}else {
					messageBean.setSuccess(false);
					messageBean.setMsg("添加失败");
				}
			}
		
			return messageBean;
		
		}
		

		//删除指定数据
		@RequestMapping("/delete")
		@ResponseBody
		public MessageBean delete(String tid) {
			MessageBean messageBean=new MessageBean("");
			int tid1=Integer.parseInt(tid);
			TCourses tCourses=tCoursesService.selectByPrimaryKey(tid1);
			Courses courses=new Courses();
			courses.setCoId(tCourses.getCoId());
			courses.setState("0");
			boolean bool=tCoursesService.deleteByPrimaryKey(tid1);
			if(bool) {
				bool=coursesService.updateByPrimaryKeySelective(courses);
			}
			messageBean.setSuccess(bool);
			return messageBean;
			
		}


		//更改数据
				@RequestMapping("/updateTCourse")
				@ResponseBody
				public MessageBean updateTCourse(String tid) {
					MessageBean messageBean=new MessageBean("");
					TCourses tCourses=new TCourses();
					tCourses.setTid(Integer.parseInt(tid));
					tCourses.setState("1");//已提交状态
					boolean bool=tCoursesService.updateByPrimaryKeySelective(tCourses);
					if(bool) {
						messageBean.setMsg("申报成功");
					}else {
						messageBean.setMsg("申报失败!");
					}
					messageBean.setSuccess(bool);
					return messageBean;

				}


	//更改数据
	@RequestMapping("/updateTwork")
	@ResponseBody
	public MessageBean updateTwork(String toid) {
		MessageBean messageBean=new MessageBean("");
       Toworkload toworkload=new Toworkload();

		toworkload.setToid(Integer.parseInt(toid));
		toworkload.setState(1);
		boolean bool=tCoursesService.updateTwork(toworkload);
		if(bool) {
			messageBean.setMsg("申报成功");
		}else {
			messageBean.setMsg("申报失败!");
		}
		//	System.out.println(bool);
		messageBean.setSuccess(bool);


		return messageBean;

	}


	//更改数据
	@RequestMapping("/updateTScientific")
	@ResponseBody
	public MessageBean updateTScientific(String toid) {
		MessageBean messageBean=new MessageBean("");
     Toscientific toscientific=new Toscientific();
		toscientific.setToid(Integer.parseInt(toid));
		toscientific.setState(1);
		boolean bool=tCoursesService.updateTScientific(toscientific);
		if(bool) {
			messageBean.setMsg("申报成功");
		}else {
			messageBean.setMsg("申报失败!");
		}
		//	System.out.println(bool);
		messageBean.setSuccess(bool);


		return messageBean;

	}
}
