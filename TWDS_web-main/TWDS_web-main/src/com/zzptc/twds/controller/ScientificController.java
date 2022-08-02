package com.zzptc.twds.controller;

import com.zzptc.twds.pojo.Oworkload;
import com.zzptc.twds.pojo.Scientific;
import com.zzptc.twds.pojo.User;
import com.zzptc.twds.service.OworkloadService;
import com.zzptc.twds.service.ScienfiticService;
import com.zzptc.twds.service.UserService;
import com.zzptc.twds.utils.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/Scientific")
public class ScientificController {

	@Autowired
	private ScienfiticService scienfiticService;



	@RequestMapping("/insertScientific")
	public ModelAndView insertView() {
		ModelAndView view=new ModelAndView();
		view.setViewName("Oworkload/addScientific");
		return view;
	}
	//添加科研类型
	@RequestMapping("/insertScientifics")
	@ResponseBody
	public MessageBean insertScientifics(HttpServletRequest request,String scientificName,String value) {
		MessageBean messageBean=new MessageBean("");
		double value1=Double.parseDouble(value);
		List<Scientific> list=scienfiticService.selectAllScientific();

		boolean bl=false;
		for (Scientific scientific : list) {
			if(scientificName.equals(scientific.getScientificName())) {
				messageBean.setSuccess(false);
				messageBean.setMsg("该科研类型已存在!");
				bl=true;
				break;
			}
		}
		List<Scientific> lists=scienfiticService.selectAllScientifics();
		int id=0;
		for (Scientific scientifics : lists) {
			if(scientifics.getScientificId()>id){
				id=scientifics.getScientificId();
			}
		}

		if(!bl) {
			Scientific scientific=new Scientific();
			scientific.setScientificName(scientificName);
			scientific.setValue(value1);
			scientific.setState(0);
			scientific.setScientificId(id+1);
			boolean bool=scienfiticService.insert(scientific);
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
	//查询全部科研信息
	@RequestMapping("/selectAllScientific")
	public ModelAndView selectAllScientific(HttpSession session) {
		ModelAndView view=new ModelAndView();

		List<Scientific> list=scienfiticService.selectAllScientific();
		view.addObject("list", list);
		view.setViewName("Oworkload/Scientific");
		return view;
	}


	@RequestMapping("/updateScientific")
	@ResponseBody
	public MessageBean update(String scientificId,String scientificName,String value,String oldScientificName) {
		MessageBean messageBean=new MessageBean("");
		List<Scientific> list=scienfiticService.selectAllScientific();
		boolean bl=false;
		for (Scientific scientific : list) {
			if(scientificName.equals(scientific.getScientificName())) {
				messageBean.setSuccess(false);
				messageBean.setMsg("该科研类型已存在!");
				bl=true;
				break;
			}
		}
		if(scientificName.equals(oldScientificName)) {
			bl=false;
		}
		if(!bl) {

			double value1=Double.parseDouble(value);
			int scientificId1=Integer.parseInt(scientificId);
			Scientific scientific=new Scientific();
			scientific.setScientificId(scientificId1);
			scientific.setScientificName(scientificName);
			scientific.setValue(value1);
			boolean bool=scienfiticService.updateByPrimaryKeySelective(scientific);
			if(!bool) {
				messageBean.setMsg("修改失败!");
			}else {
				messageBean.setMsg("修改成功!");
			}
			System.out.println(bool);
			messageBean.setSuccess(bool);

		}

		return messageBean;

	}
	@RequestMapping("/deleteScientific")
	@ResponseBody
	public MessageBean deleteScientific(String scientificId) {

		MessageBean messageBean=new MessageBean("");
		int oid1=Integer.parseInt(scientificId);
		boolean bool=scienfiticService.deleteByPrimaryKey(oid1);
		messageBean.setSuccess(bool);
		return messageBean;

	}
	
}
