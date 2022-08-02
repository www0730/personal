package com.zzptc.twds.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzptc.twds.pojo.Classes;
import com.zzptc.twds.pojo.Majors;
import com.zzptc.twds.service.ClassesService;
import com.zzptc.twds.service.MajorService;
import com.zzptc.twds.utils.MessageBean;
/*标记这个类是是一个SpringMVC Controller对象，
会分发处理器扫描使用该注解的类
并检测该方法是否使用了@RequestMapping注解
 */
@Controller
/*
类级别
请求处理器地址映射
表示类中所有响应请求的方法都是以该地址作为父路径
 */
@RequestMapping("/classes")
public class ClassesController {
	
	@Autowired
	private ClassesService classesService;

	@Autowired
	private MajorService majorService;

	
	//查询全部班级
	@RequestMapping("/selectAll")
	public ModelAndView selectAll(HttpSession session) {
		/*ModelAndView类用来存储处理完的结果数据，以及显示该数据的视图
		业务处理器调用模型层处理完用户请求后，把结果数据存储在该类的model属性中
		，把要返回的视图信息存储在该类的view属性中，然后让该ModelAndView返回该Spring MVC框架。
		框架通过调用配置文件中定义的视图解析器，对该对象进行解析，最后把结果数据显示在指定的页面上。

		 */
		ModelAndView view=new ModelAndView();
		int cId=(int)session.getAttribute("cId");//通过session拿到用户所属的学院编号
		List<Classes> list=classesService.selectClassesById(cId);
		view.addObject("list", list);
		view.setViewName("classes/classes");//指定视图名
		return view;
	}
	    //查询全部班级
		@RequestMapping("/selectAllClasses")
		@ResponseBody//将Java对象转化为json格式的数据
		public List<Classes> selectAllClasses(HttpSession session) {
			List<Classes> list=classesService.selectAllClasses();
			return list;
		}
		
	
	
	//添加班级
	@RequestMapping("/addClassView")
	public ModelAndView insertView(HttpSession session) {
		ModelAndView view=new ModelAndView();
		int cId=(int)session.getAttribute("cId");//通过session拿到用户所属的学院编号
		List<Majors> list=majorService.selectAllById(cId);
		view.addObject("list", list);//需要返回的值
		view.setViewName("classes/addClasses");//代表jsp路径，即访问哪一个jsp页面
		return view;
	}
	
	
	/*
	 * 添加班级
	 * */
	@RequestMapping("/insert")
	@ResponseBody
	public MessageBean insert(HttpServletRequest request,String mid,String clName,String clYear,String clNum) {
		MessageBean messageBean=new MessageBean("");
		int mId=Integer.parseInt(mid);
		int clNum1=Integer.parseInt(clNum);
		List<Classes> list=classesService.selectAll();
		boolean bl=false;
		for (Classes classes : list) {
			if(clName.equals(classes.getClName())) {
				messageBean.setSuccess(false);
				messageBean.setMsg("该班级已存在!");
				bl=true;
				break;
			}
		}
		if(!bl) {
			Classes classes=new Classes();
			classes.setMid(mId);
            classes.setState(0);
			classes.setClName(clName);
			classes.setClYear(clYear);
			classes.setClNum(clNum1);

			boolean bool=classesService.insertSelective(classes);
			if(bool) {
				messageBean.setSuccess(true);
				messageBean.setMsg("添加成功");
			}else {
				messageBean.setSuccess(false);
				messageBean.setMsg("添加失败");
			}
		}
		
		return messageBean;
	
	}
	

	
	

	
	
	//删除指定班级
	@RequestMapping("/delete")
	@ResponseBody
	public MessageBean delete(String clId) {
		MessageBean messageBean=new MessageBean("");
		int clId1=Integer.parseInt(clId);
		boolean bool=classesService.deleteByPrimaryKey(clId1);
		messageBean.setSuccess(bool);
		return messageBean;
		
	}
	//修改指定班级数据
		@RequestMapping("/update")
		@ResponseBody
		public MessageBean update(String clName,String clId,String mId,String clYear,String clNum,String olkdclName) {
			MessageBean messageBean=new MessageBean("");
			List<Classes> list=classesService.selectAll();
			boolean bl=false;
			for (Classes classes : list) {
				if(clName.equals(classes.getClName())) {
					messageBean.setSuccess(false);
					messageBean.setMsg("该班级已存在!");
					bl=true;
					break;
				}
			}
			if(clName.equals(olkdclName)) {
				bl=false;
			}
			if(!bl) {
				
					int clId1=Integer.parseInt(clId);
					int mid=Integer.parseInt(mId);
					int clNum1=Integer.parseInt(clNum);
					Classes classes=new Classes();
					classes.setMid(mid);
					classes.setClId(clId1);
					classes.setClName(clName);
					classes.setClYear(clYear);
					classes.setClNum(clNum1);
					boolean bool=classesService.updateByPrimaryKeySelective(classes);
					if(!bool) {
						messageBean.setMsg("修改失败!");
					}else {
						messageBean.setMsg("修改成功!");
					}
					messageBean.setSuccess(bool);
				
			}
			
			return messageBean;
			
		}
}
	


