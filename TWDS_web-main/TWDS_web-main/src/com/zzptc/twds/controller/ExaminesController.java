package com.zzptc.twds.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

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
@RequestMapping("/examines")
public class ExaminesController {

	@Autowired
	private ExaminesService examinesService;

	@Autowired
	private FormulaService formulaService;

	@Autowired
	private CollegeService collegeService;
	
	@Autowired
	private TCoursesService tCoursesService;
	
	private String cId;
	private HttpSession session;
	
	//管理员审核老师申报课程页面
		@RequestMapping("/examinesDeclareView")
		public ModelAndView insertView() {
			ModelAndView view=new ModelAndView();
			view.setViewName("examines/examines");
			return view;
		}
		/* *教学工作量申报
		 * */
	/*	@RequestMapping("/insert")
		@ResponseBody
		public MessageBean insert(HttpSession session,String weeks,String values,String coId) {
			MessageBean messageBean=new MessageBean("");
			int coId1=Integer.parseInt(coId);
			TCourses tCourses=tCoursesService.selectBycoId(coId1);//根据课程名查询课程
			String[] weeks1=weeks.split(",");
			String[] values1=values.split(",");
			int userid=((User) session.getAttribute("user")).getUserid();
			List<Examines> list=examinesService.selectAll(userid);
			boolean bl=false;
			for (Examines examines : list) {
					String	week=String.valueOf(examines.geteWeek());
					if(examines.getTid().equals(tCourses.getTid())&&weeks.contains(week)) {
						messageBean.setSuccess(false);
						messageBean.setMsg("该课程工作量已申请!");
						bl=true;
						break;
					}
				
			}
			
			
			if(!bl) {
				boolean bool=false;
				for(int i=0;i<weeks1.length;i++) {
					Examines examines=new Examines();
					examines.setTid(tCourses.getTid());
					examines.seteWeek(Integer.parseInt(weeks1[i]));
					examines.setValue(Double.parseDouble(values1[i]));
					SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date=simpleDateFormat.format(new Date());
					examines.setEtime(date);
					bool=examinesService.insertSelective(examines);
					
				}
				
				if(bool) {
					messageBean.setSuccess(true);
					messageBean.setMsg("添加成功");
				}else {
					messageBean.setSuccess(false);
					messageBean.setMsg("添加失败");
				}
			
			}
			
		
			return messageBean;
		
		}*/


	@RequestMapping("/examScientific")
	@ResponseBody
	public MessageBean examScientific(HttpSession session,String username,String scientificId,String toName,String year) {
		MessageBean messageBean=new MessageBean("");
		int oId=Integer.parseInt(scientificId);
			boolean bool=false;
			Toscientific toscientific=new Toscientific();
			toscientific.setState(0);
			toscientific.setOid(oId);
		toscientific.setUsername(username.trim());//trim（）删除字符串中多余的空格，但会在英文字符串中保留一个作为词与词之间分隔的空格。
		toscientific.setToname(toName);
		toscientific.setTime(year);

				bool=examinesService.insertToscientific(toscientific);
			if(bool) {
				messageBean.setSuccess(true);
				messageBean.setMsg("添加成功");
			}else {
				messageBean.setSuccess(false);
				messageBean.setMsg("添加失败");
			}

		return messageBean;

	}
			@RequestMapping("/MoWorkLoad")
			public ModelAndView MoWorkLoad(HttpSession session) {
				ModelAndView view=new ModelAndView();
				int userid=((User) session.getAttribute("user")).getUserid();
				List<Examines> list=examinesService.selectAll(userid);
				view.addObject("list", list);
				view.setViewName("examines/MoWorkLoad");
				return view;
			}
    //工作量汇总
	@RequestMapping("/AllWorkLoad")
	public ModelAndView AllWorkLoad(HttpSession session,String year) {
		ModelAndView view=new ModelAndView();
		String username=session.getAttribute("username").toString();
		String name=session.getAttribute("name").toString();//获取工号及姓名
		List<TCourses> tCoursesList =new ArrayList<>();
		tCoursesList=tCoursesService.selectAllTCoursesByUserName(username);
        List<Toscientific> toScientificList  =new ArrayList<>();
		toScientificList=tCoursesService.selectScientificByUserName(username);
        List<Toworkload> otherWorkList  =new ArrayList<>();
		otherWorkList=tCoursesService.selectOtherWorkByUserName(username);//根据工号查询三类工作量
		if(session.getAttribute("roleid").toString().equals("2")){//教务管理员查询所有
			tCoursesList=tCoursesService.selectAllTCoursesByAdmin();
			toScientificList=tCoursesService.selectScientificByAdmin();
			otherWorkList=tCoursesService.selectOtherWorkByAdmin();
		}
		List<TCourses> list1=new ArrayList<>();
		List<Toscientific> list2=new ArrayList<>();
		List<Toworkload> list3=new ArrayList<>();
		TreeMap<Integer,Double> resultMap=new TreeMap<>();//有序
		TreeMap<Integer,Double> yearMap=new TreeMap<>();//有序
		double totalTechWorkload=0;
		double totalScientificWorkload=0;
		double totalOtherWorkload=0;
		for (TCourses tCourses : tCoursesList) {
			double courseLoad=0;
			Courses course=tCourses.getCourses();
			Classes calsee=tCourses.getClasses();
			Paras paras=formulaService.selectAllParas(0);
			if(course.getCoType().equals("本科课程")){
                courseLoad+=course.getCoTotal()*paras.getBvalue();
			}
			if(course.getCoType().equals("研究生课程")){
                courseLoad+=course.getCoTotal()*paras.getYvalue();
			}
			if(calsee.getClNum()<=40){
                courseLoad+=course.getCoTotal()*paras.getRvalue1();
			}
			if(calsee.getClNum()>40&&calsee.getClNum()<=60){
                courseLoad+=course.getCoTotal()*paras.getRvalue2();
			}
			if(calsee.getClNum()>60&&calsee.getClNum()<=100){
                courseLoad+=course.getCoTotal()*paras.getRvalue3();
			}
			if(calsee.getClNum()>100){
                courseLoad+=course.getCoTotal()*paras.getRvalue4();
			}
			if(course.getCoWenum()<=4){
                courseLoad+=course.getCoTotal()*paras.getXfvalue1();

			}else{
                courseLoad+=course.getCoTotal()*paras.getXfvalue2();
			}
			totalTechWorkload+=courseLoad;
			tCourses.setTotalTworkload(courseLoad);
			Integer key=Integer.parseInt(tCourses.getCourses().getCoTerm().substring(0,4));//substring(0,4)返回字符串的子字符串，coterm前四个元素为年份，获取年份值
			yearMap.put(key,0.0);
			if(year==null||year.equals("-1")){
				list1.add(tCourses);
				resultMap.put(key,resultMap.getOrDefault(key,0.0)+courseLoad);
			}else{
				if(key.toString().equals(year)){//tostring（）先转化为字符串对象与所需年份比对
					list1.add(tCourses);
					resultMap.put(key,resultMap.getOrDefault(key,0.0)+courseLoad);//将年份与工作量值匹配映射
				}
			}

        }
        for (Toscientific scientific : toScientificList) {
			double scientificLoad=0;
            scientificLoad+=scientific.getScientific().getValue()*scientific.getScientific().getPara();
            scientific.setTotalWorkload(scientificLoad);

			totalScientificWorkload+=scientificLoad;
			Integer key=Integer.parseInt(scientific.getTime());

			yearMap.put(key,0.0);
			if(year==null||year.equals("-1")){
				list2.add(scientific);
				resultMap.put(key,resultMap.getOrDefault(key,0.0)+scientificLoad);
			}else{
				if(key.toString().equals(year)){
					list2.add(scientific);
					resultMap.put(key,resultMap.getOrDefault(key,0.0)+scientificLoad);
				}
			}
        }
        for (Toworkload toWorkload : otherWorkList) {
			double otherWordLoad=0;
            otherWordLoad+=toWorkload.getOworkload().getOvalue()*toWorkload.getOworkload().getPara();

            toWorkload.setTotalWorkload(otherWordLoad);

			totalOtherWorkload+=otherWordLoad;
			Integer key=Integer.parseInt(toWorkload.getTotime().toString().substring(0,4));
			yearMap.put(key,0.0);
			if(year==null||year.equals("-1")){
				list3.add(toWorkload);
				resultMap.put(key,resultMap.getOrDefault(key,0.0)+otherWordLoad);
			}else{
				if(key.toString().equals(year)){
					list3.add(toWorkload);
					resultMap.put(key,resultMap.getOrDefault(key,0.0)+otherWordLoad);
				}
			}
        }
		view.addObject("year", year);
		view.addObject("yearMap", yearMap);
		view.addObject("resultMap", resultMap);
		view.addObject("list1", list1);//
		view.addObject("list2", list2);
		view.addObject("list3", list3);
		view.addObject("totalTechWorkload", totalTechWorkload);
		view.addObject("totalScientificWorkload", totalScientificWorkload);
		view.addObject("totalOtherWorkload", totalOtherWorkload);
		view.addObject("list", tCoursesList);
		view.addObject("size", tCoursesList.size());
		view.setViewName("tCourses/TWorkLoad");
		return view;
	}

//教师工作量汇总（教务端）
	@RequestMapping("/AllTechWorkLoad")
	public ModelAndView AllTechWorkLoads(String year) {
		ModelAndView view=new ModelAndView();
		List<TCourses> resultList =new ArrayList<>();
		List<TCourses> tCoursesList =new ArrayList<>();
		List<Toscientific> toScientificList  =new ArrayList<>();
		List<Toworkload> otherWorkList  =new ArrayList<>();
		tCoursesList=tCoursesService.selectAllTCoursesByAdmin();//状态为2，即通过审核的信息
		toScientificList=tCoursesService.selectScientificByAdmin();
		otherWorkList=tCoursesService.selectOtherWorkByAdmin();
		List<TCourses> list1=new ArrayList<>();
		List<Toscientific> list2=new ArrayList<>();
		List<Toworkload> list3=new ArrayList<>();
		TreeMap<Integer,Double> yearMap=new TreeMap<>();
		Set<String> userName=new HashSet<>();//set集合
		for (TCourses tCourses : tCoursesList) {
			double courseLoad=0;
			Courses course=tCourses.getCourses();
			Classes calsee=tCourses.getClasses();
			Paras paras=formulaService.selectAllParas(0);
			if(course.getCoType().equals("本科课程")){
				courseLoad+=course.getCoTotal()*paras.getBvalue();
			}
			if(course.getCoType().equals("研究生课程")){
				courseLoad+=course.getCoTotal()*paras.getYvalue();
			}
			if(calsee.getClNum()<=40){
				courseLoad+=course.getCoTotal()*paras.getRvalue1();
			}
			if(calsee.getClNum()>40&&calsee.getClNum()<=60){
				courseLoad+=course.getCoTotal()*paras.getRvalue2();
			}
			if(calsee.getClNum()>60&&calsee.getClNum()<=100){
				courseLoad+=course.getCoTotal()*paras.getRvalue3();
			}
			if(calsee.getClNum()>100){
				courseLoad+=course.getCoTotal()*paras.getRvalue4();
			}
			if(course.getCoWenum()<=4){
				courseLoad+=course.getCoTotal()*paras.getXfvalue1();

			}else{
				courseLoad+=course.getCoTotal()*paras.getXfvalue2();
			}
			tCourses.setTotalTworkload(courseLoad);//教学工作量添加进去
			Integer key=Integer.parseInt(tCourses.getCourses().getCoTerm().substring(0,4));//得到年份数据
			yearMap.put(key,0.0);
			if(year==null||year.equals("-1")){
				list1.add(tCourses);
				userName.add(tCourses.getUsername());
			}else{
				if(key.toString().equals(year)){
					list1.add(tCourses);
					userName.add(tCourses.getUsername());
				}
			}

		}
		for (Toscientific scientific : toScientificList) {
			double scientificLoad=0;
			scientificLoad+=scientific.getScientific().getValue()*scientific.getScientific().getPara();
			scientific.setTotalWorkload(scientificLoad);
			Integer key=Integer.parseInt(scientific.getTime());

			yearMap.put(key,0.0);
			if(year==null||year.equals("-1")){
				list2.add(scientific);
				userName.add(scientific.getUsername());
			}else{
				if(key.toString().equals(year)){
					list2.add(scientific);
					userName.add(scientific.getUsername());
				}
			}
		}
		for (Toworkload toWorkload : otherWorkList) {
			double otherWordLoad=0;
			otherWordLoad+=toWorkload.getOworkload().getOvalue()*toWorkload.getOworkload().getPara();
			toWorkload.setTotalWorkload(otherWordLoad);
			Integer key=Integer.parseInt(toWorkload.getTotime().toString().substring(0,4));
			yearMap.put(key,0.0);
			if(year==null||year.equals("-1")){
				list3.add(toWorkload);
				userName.add(toWorkload.getUsername());
			}else{
				if(key.toString().equals(year)){
					list3.add(toWorkload);
					userName.add(toWorkload.getUsername());
				}
			}
		}

		for (String str : userName) {
			TCourses result=new TCourses();
			Double techWork=0.0;
		   for(int i=0;i<list1.size();i++){
			   TCourses tCourses=list1.get(i);
                if(str.equals(tCourses.getUsername())){
					techWork+=tCourses.getTotalTworkload();
				}
		   }
			result.setUsername(str);
			result.setTotalTworkload(techWork);//将教学工作量加入进去
			Double sciWork=0.0;
			for(int i=0;i<list2.size();i++){
				Toscientific toscientific=list2.get(i);
				if(str.equals(toscientific.getUsername())){
					sciWork+=toscientific.getTotalWorkload();
				}
			}
			result.setTotalworkload(sciWork);//科研工作量加入进去
			Double otherWork=0.0;
			for(int i=0;i<list3.size();i++){
				Toworkload toworkload=list3.get(i);
				if(str.equals(toworkload.getUsername())){
					otherWork+=toworkload.getTotalWorkload();
				}
			}
			result.setTotalOworkload(otherWork);//将其他工作总量加入进去
			result.setTotal(techWork+sciWork+otherWork);//总的工作量加起来
			resultList.add(result);

		}

		view.addObject("year", year);
		view.addObject("yearMap", yearMap);
		view.addObject("list", resultList);
		view.setViewName("tCourses/AllWorkLoad");
		return view;
	}
	//教学工作量汇总，按照学院及状态查询汇总工作量
	@RequestMapping("/MTechWorkLoad")
	public ModelAndView MTechWorkLoad(HttpSession session,String state,String year) {
		ModelAndView view=new ModelAndView();
		String username=session.getAttribute("username").toString();
		List<TCourses> list =tCoursesService.selectAllTCoursesByUserid(username);//按用户工号获取课程
		List<TCourses> tCoursesList=new ArrayList<>();
		List<yearCollect> yearCollectList=new ArrayList<>();
		TreeMap<Integer,Double> resultMap=new TreeMap<>();//映射关系排序
		Map <Integer,Double> resulut =new HashMap<>();//映射关系无序
		for (TCourses tCourses : list) {//循环每个课程信息
			double totalWorkload=0;
			Courses course=tCourses.getCourses();
			Classes calsee=tCourses.getClasses();
			Paras paras=formulaService.selectAllParas(0);//获取计算所需参数、班级、课程
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
			String states=tCoursesService.searchState(tid);//根据课程信息编号得到审核状态
			tCourses.setState(states);
			tCourses.setTotalTworkload(totalWorkload);//审核状态及工作量都放入tcourses
			Integer  key=Integer.parseInt(tCourses.getCourses().getCoTerm().substring(0,4));//取得课程所开设年份
			yearCollect yearCollect=new yearCollect();
			yearCollect.setState(states);
			yearCollect.setYear(key);
			yearCollect.setTatal(totalWorkload);
			yearCollectList.add(yearCollect);//状态、年份、工作量
			resultMap.put(key,resultMap.getOrDefault(key,0.0)+totalWorkload);//将年份与其对应工作量映射
			if(state==null&&year==null){
				tCoursesList.add(tCourses);
				resulut.put(key,resultMap.get(key));//所有年份与相应工作量
			}else{
				if(state.equals("-1")) {
					if(year.equals("-1")){
						tCoursesList.add(tCourses);
						resulut.put(key,resultMap.get(key));

					}else{
						if(tCourses.getCourses().getCoTerm().contains(year)){//当且仅当此字符串包含指定的char值序列,返回TRUE
							tCoursesList.add(tCourses);
							resulut.put(Integer.parseInt(year),resultMap.get(Integer.parseInt(year)));//根据具体年份获取工作量
						}
					}

				}else{
					if(year.equals("-1")){
						if(states.equals(state)){
							tCoursesList.add(tCourses);
							double d=0;
							for(yearCollect   data    :    yearCollectList)
							{
								if(data.getState().equals(state)){//循环得到查询目标审核状态下的工作量
                                       d+=data.getTatal();
								}

							}
							resulut.put(Integer.parseInt(year),d);

						}
					}else{
						if(tCourses.getCourses().getCoTerm().contains(year)&&states.equals(state)){//年份与状态双重查询
							tCoursesList.add(tCourses);
							double d=0;
							for(yearCollect   data    :    yearCollectList)
							{
								if(data.getState().equals(state)&&data.getYear()==Integer.parseInt(year)){
									d+=data.getTatal();
								}

							}
							resulut.put(Integer.parseInt(year),d);
						}
					}

				}
				view.addObject("state",state);
				view.addObject("year", year);
			}
		}
		view.addObject("resulut", resulut);//年份、每个年份总工作量
		view.addObject("resultMap", resultMap);//年份、每个工作量
		view.addObject("list", tCoursesList);//课程信息
		view.addObject("size", tCoursesList.size());
		view.setViewName("examines/MoWorkLoad");
		return view;
	}

	//科研工作量汇总（学院管理员、教师）
	@RequestMapping("/MScientificWorkLoad")
	public ModelAndView MScientificWorkLoad(HttpServletRequest request,String state,String year) {
		ModelAndView view=new ModelAndView();
		session=request.getSession();
		String username=session.getAttribute("username").toString();
		String name=session.getAttribute("name").toString();
		request.setAttribute("username",username);
		request.setAttribute("name",name);
		List<Toscientific> list =tCoursesService.selectDeclareScientificView(username);
		List<Toscientific> scientificList=new ArrayList<>();
		List<yearCollect> yearCollectList=new ArrayList<>();
		TreeMap<Integer,Double> resultMap=new TreeMap<>();//有序
		Map <Integer,Double> resulut =new HashMap<>();//无序
		for (Toscientific scientific : list) {
			double totalWorkload=0;
			Integer toId=scientific.getToid();
			totalWorkload=scientific.getScientific().getValue()*scientific.getScientific().getPara();
			Integer states=tCoursesService.searchScientificState(toId);
			scientific.setState(states);
			scientific.setTotalWorkload(totalWorkload);

			Integer  key=Integer.parseInt(scientific.getTime());
			yearCollect yearCollect=new yearCollect();
			yearCollect.setState(states.toString());
			yearCollect.setYear(key);
			yearCollect.setTatal(totalWorkload);
			yearCollectList.add(yearCollect);
			resultMap.put(key,resultMap.getOrDefault(key,0.0)+totalWorkload);
			if(state==null&&year==null){
				resulut.put(key,resultMap.get(key));
				scientificList.add(scientific);
			}else{
				if(state.equals("-1")) {
					if(year.equals("-1")){
						scientificList.add(scientific);
						resulut.put(key,resultMap.get(key));

					}else{
						if(scientific.getTime().equals(year)){
							scientificList.add(scientific);
							resulut.put(Integer.parseInt(year),resultMap.get(Integer.parseInt(year)));
						}
					}

				}else{
					if(year.equals("-1")){
						if(states.toString().equals(state)){
							scientificList.add(scientific);
							double d=0;
							for(yearCollect   data    :    yearCollectList)
							{
								if(data.getState().equals(state)){
									d+=data.getTatal();
								}

							}
							resulut.put(Integer.parseInt(year),d);
						}

					}else{
						if(scientific.getTime().equals(year)&&states.toString().equals(state)){
							scientificList.add(scientific);
							double d=0;
							for(yearCollect   data    :    yearCollectList)
							{
								if(data.getState().equals(state)&&data.getYear()==Integer.parseInt(year)){
									d+=data.getTatal();
								}

							}
							resulut.put(Integer.parseInt(year),d);
						}
					}

				}
				view.addObject("state",state);
				view.addObject("year", year);
		}
		}
		view.addObject("resulut", resulut);
		view.addObject("resultMap", resultMap);
		view.addObject("list", scientificList);
		view.addObject("size", scientificList.size());
		view.setViewName("tCourses/MoScinetificWorkLoad");
		return view;
	}
	//其他工作量汇总（学院管理员、教师）
	@RequestMapping("/MotherWorkLoad")
	public ModelAndView MotherWorkLoad(HttpSession session,String state,String year) {
		ModelAndView view=new ModelAndView();
		String username=session.getAttribute("username").toString();
		List<Toworkload> list =tCoursesService.selectDeclareTworkView(username);
		List<Toworkload> tWorkList=new ArrayList<>();
		List<yearCollect> yearCollectList=new ArrayList<>();
		TreeMap<Integer,Double> resultMap=new TreeMap<>();
		Map <Integer,Double> resulut =new HashMap<>();
		int  i=0;
		for (Toworkload toWorkload : list) {
			double totalWorkload=0;
			Integer toId=toWorkload.getToid();
			totalWorkload=toWorkload.getOworkload().getOvalue()*toWorkload.getOworkload().getPara();
			Integer states=tCoursesService.searchStates(toId);
			toWorkload.setState(states);
			toWorkload.setTotalWorkload(totalWorkload);

			Integer  key=Integer.parseInt(toWorkload.getTotime().toString().substring(0,4));
			yearCollect yearCollect=new yearCollect();
			yearCollect.setState(states.toString());
			yearCollect.setYear(key);
			yearCollect.setTatal(totalWorkload);
			yearCollectList.add(yearCollect);
			resultMap.put(key,resultMap.getOrDefault(key,0.0)+totalWorkload);
			if(state==null&&year==null){
				resulut.put(key,resultMap.get(key));
				tWorkList.add(toWorkload);
			}else {
				if (state.equals("-1")) {
					if (year.equals("-1")) {
						tWorkList.add(toWorkload);
						resulut.put(key, resultMap.get(key));

					} else {
						if (key.toString().equals(year)) {
							tWorkList.add(toWorkload);
							resulut.put(Integer.parseInt(year), resultMap.get(Integer.parseInt(year)));
						}
					}

				} else {
					if (year.equals("-1")) {
						if (states.toString().equals(state)) {
							tWorkList.add(toWorkload);
							double d = 0;
							for (yearCollect data : yearCollectList) {
								if (data.getState().equals(state)) {
									d += data.getTatal();
								}

							}
							resulut.put(Integer.parseInt(year), d);
						}

					} else {
						if (key.toString().equals(year) && states.toString().equals(state)) {
							tWorkList.add(toWorkload);
							double d = 0;
							for (yearCollect data : yearCollectList) {
								if (data.getState().equals(state) && data.getYear() == Integer.parseInt(year)) {
									d += data.getTatal();
								}

							}
							resulut.put(Integer.parseInt(year), d);
						}
					}

				}
				view.addObject("state", state);
				view.addObject("year", year);
			}
		}
		view.addObject("resulut", resulut);
		view.addObject("resultMap", resultMap);
		view.addObject("list", tWorkList);
		view.addObject("size", tWorkList.size());
		view.setViewName("tCourses/MoOtherWorkLoad");
		return view;
	}

	         //通过审核状态查询已申报月度教学工作量申报汇总
		/*	@RequestMapping("/MoWorkLoadByResult")
			public ModelAndView MoWorkLoadByResult(HttpSession session,String result) {
				ModelAndView view=new ModelAndView();
				int userid=((User) session.getAttribute("user")).getUserid();
				*//*try {
					result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*//*
				if(result.equals("全部")) {
					List<Examines> list=examinesService.selectAll(userid);
					view.addObject("list", list);
					
				}else {
					Examines examines=new Examines();
					examines.setResult(result);
					examines.setUserid(userid);
					List<Examines> list=examinesService.selectByresultandUserId(examines);
					view.addObject("list", list);
					view.addObject("result", result);
				}
				
				view.setViewName("examines/MoWorkLoad");
				return view;
			}*/
            //教学工作量审核
			@RequestMapping("/selectCT")
			public ModelAndView selectCT(HttpSession session,Examines examines) {
					User user=(User)session.getAttribute("user");
				String username=session.getAttribute("username").toString();
				int cid=Integer.parseInt(session.getAttribute("cId").toString());
					ModelAndView view=new ModelAndView();
				List<TCourses> list=new ArrayList<>();
						if(user.getRoleid()==2) {
							list=collegeService.selectAllCT();

						}else if(user.getRoleid()==3) {
							list=collegeService.selectAllbyCid(cid);
						}
				List<TCourses> tCoursesList=new ArrayList<>();
				int  i=0;
				for (TCourses tCourses : list) {
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
				view.setViewName("examines/StateExamine");
				return view;
			}


	//教师其他工作量审核
	@RequestMapping("/selectOtherWork")
	public ModelAndView selectOtherWork(HttpSession session) {
		User user=(User)session.getAttribute("user");
		String username=session.getAttribute("username").toString();
		int cid=Integer.parseInt(session.getAttribute("cId").toString());
		ModelAndView view=new ModelAndView();
		List<Toworkload> list =new ArrayList<>();
		if(user.getRoleid()==2) {
			list=tCoursesService.selectOtherTwork();

		}else if(user.getRoleid()==3) {
			list=tCoursesService.selectOtherTworks(cid);
		}
		List<Toworkload> tWorkList=new ArrayList<>();
		int  i=0;
		for (Toworkload toWorkload : list) {
			double totalWorkload=0;
			Integer toId=toWorkload.getToid();
			totalWorkload=toWorkload.getOworkload().getOvalue()*toWorkload.getOworkload().getPara();//其他工作量
			Integer state=tCoursesService.searchStates(toId);//每个其他工作量信息的状态
			toWorkload.setState(state);
			toWorkload.setTotalWorkload(totalWorkload);
				tWorkList.add(toWorkload);


		}
		view.addObject("list", tWorkList);//其他信息编号、状态、其他工作量
		view.addObject("size", tWorkList.size());
		view.setViewName("examines/OtherExamine");
		return view;
	}



	//教师科研工作量申报数据(待审核)
	@RequestMapping("/selectScientificWork")
	public ModelAndView selectScientificWork(HttpSession session,Examines examines) {
		User user=(User)session.getAttribute("user");
		int cid=Integer.parseInt(session.getAttribute("cId").toString());
		ModelAndView view=new ModelAndView();
		List<Toscientific> list =new ArrayList<>();
		if(user.getRoleid()==2) {
			list=tCoursesService.selectScientificWorks();

		}else if(user.getRoleid()==3) {
			list=tCoursesService.selectScientificWork(cid);
		}
		List<Toscientific> tWorkList=new ArrayList<>();
		int  i=0;
		for (Toscientific toscientific : list) {
			double totalWorkload=0;
			Integer toId=toscientific.getToid();
			totalWorkload=toscientific.getScientific().getValue()*toscientific.getScientific().getPara();
			Integer state=tCoursesService.searchScientificState(toId);
			toscientific.setState(state);
			toscientific.setTotalWorkload(totalWorkload);

			tWorkList.add(toscientific);


		}
		//totalWorkload=0;
		//view.addObject("totalWorkload", totalWorkload);
		view.addObject("list", tWorkList);
		view.addObject("size", tWorkList.size());
		view.setViewName("examines/ScientificExamine");
		return view;
	}



	//教师工作量申报数据
			@RequestMapping("/selectAllTCourses1")
			@ResponseBody
			public Map selectAllTCourses1(HttpSession session,Examines examines) {
				User user=(User)session.getAttribute("user");
				
				Map<String,Object>  map=null;
				if(cId!=null) {
					try {
						cId=new String(cId.getBytes("ISO-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(cId.equals("全部")) {
						
					}else {
						examines.setcId(Integer.parseInt(cId));
						
					}
					
				}
				map=examinesService.selectAllByresult(examines,user);
				
				List<College> listCollege=collegeService.selectAll();
				map.put("listCollege",listCollege);
				cId=null;
				return map;
			}
			@RequestMapping("/selectAllTCoursesByCid")
			public ModelAndView selectAllTCoursesByCid(HttpSession session,String cid) {
				ModelAndView view=new ModelAndView();
				view.setViewName("examines/StateExamine");
				cId=cid;
				return view;
				
				
			}


			/*//教师工工作量申报数据(已审核)
			@RequestMapping("/selectAllexamines")
			public ModelAndView selectAllexamines(HttpSession session) {
					ModelAndView view=new ModelAndView();
					User user=(User)session.getAttribute("user");
					if(user.getRoleid()==3) {
						Workteam workteam=workteamService.selectBywId(user.getwId());
						List<Examines> list=examinesService.selectAllByCld1(workteam.getcId());
						view.addObject("list", list);
					}else {
						List<Examines> list=examinesService.selectAllByresult1();
						List<College> listCollege=collegeService.selectAll();
						view.addObject("listCollege", listCollege);
						view.addObject("list", list);
					}
					view.addObject("roleId", user.getRoleid());
					view.setViewName("examines/Audited");
					return view;
			}
			
			
			
			//通过过学院查询教师工工作量申报数据(已审核)
			@RequestMapping("/selectAllexaminesByCid")
			public ModelAndView selectAllexaminesByCid(HttpSession session,String cId) {
					ModelAndView view=new ModelAndView();
					User user=(User)session.getAttribute("user");
					
					if(user.getRoleid()==3) {
						Workteam workteam=workteamService.selectBywId(user.getwId());
						List<Examines> list=examinesService.selectAllByCld1(workteam.getcId());
						view.addObject("list", list);
					}else {
						try {
							cId=new String(cId.getBytes("ISO-8859-1"),"UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(cId.equals("全部")) {
							List<Examines> list=examinesService.selectAllByresult1();
							view.addObject("list", list);
						}else {
							List<Examines> list=examinesService.selectAllByCld1(Integer.parseInt(cId));
							view.addObject("list", list);
							view.addObject("cId", cId);
						}
						List<College> listCollege=collegeService.selectAll();
						view.addObject("listCollege", listCollege);
					}
					
					
					view.setViewName("examines/Audited");
					return view;
			}
			*/

			//审核是否通过
			@RequestMapping("/update")
			@ResponseBody
			public MessageBean update(String tid,String content,String state) {
					MessageBean messageBean=new MessageBean("");
					boolean bool=false;
					TCourses tCourses=new TCourses();
				tCourses.setTid(Integer.parseInt(tid));
				tCourses.setState(state);/*从jsp页面传输state为2或3进来*/
				tCourses.setContent(content);
					bool=examinesService.updateByPrimaryKeySelective(tCourses);
					messageBean.setSuccess(bool);
					messageBean.setMsg("审核成功");
					return messageBean;
			}

	//审核是否通过
	@RequestMapping("/updateOther")
	@ResponseBody
	public MessageBean updateOther(HttpSession session,String toid,String content,String state) {
		MessageBean messageBean=new MessageBean("");
		boolean bool=false;
		Toworkload toworkload=new Toworkload();
		toworkload.setToid(Integer.parseInt(toid));
		toworkload.setState(Integer.parseInt(state));
		toworkload.setContent(content);
		bool=examinesService.updateOther(toworkload);

		messageBean.setSuccess(bool);
		messageBean.setMsg("审核成功");
		return messageBean;
	}
	//审核是否通过
	@RequestMapping("/updateScientific")
	@ResponseBody
	public MessageBean updateScientific(HttpSession session,String toid,String content,String state) {
		MessageBean messageBean=new MessageBean("");
		boolean bool=false;
		Toscientific toscientific=new Toscientific();
		toscientific.setToid(Integer.parseInt(toid));
		toscientific.setState(Integer.parseInt(state));
		toscientific.setContent(content);
		bool=examinesService.updateScientific(toscientific);

		messageBean.setSuccess(bool);
		messageBean.setMsg("审核成功");
		return messageBean;
	}


	@RequestMapping("/insertScientific")
	@ResponseBody
	public MessageBean insertScientific(HttpSession session,String username,String name,String scientificId,String toName,String value) {
		MessageBean messageBean=new MessageBean("");



		return messageBean;

	}




}
