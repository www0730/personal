package com.zzptc.twds.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzptc.twds.pojo.Courses;
import com.zzptc.twds.pojo.TCourses;
import com.zzptc.twds.pojo.Toworkload;
import com.zzptc.twds.service.ToworkloadService;
import com.zzptc.twds.utils.MessageBean;

@Controller
@RequestMapping("/Toworkload")
public class ToworkloadController {

	
	@Autowired
	private ToworkloadService toworkloadService;
	
	
	//分配其它工作量
	@RequestMapping("/insert")
	@ResponseBody
	public MessageBean insert(HttpServletRequest request,HttpSession session,String oid,String toname,String username,String totime){
		MessageBean messageBean=new MessageBean("");
		List<Toworkload> list=toworkloadService.selectAll();
		int oid1=Integer.parseInt(oid);
		boolean bl=false;
		for (Toworkload toworkload : list) {
			if(toname.equals(toworkload.getToname())&&(toworkload.getUsername()==username)) {
				messageBean.setSuccess(false);
				messageBean.setMsg("该工作量已分配给此老师!");
				bl=true;
				break;
			}
		}
		if(!bl) {
			
			Toworkload toworkload=new Toworkload();
			toworkload.setOid(oid1);
			toworkload.setToname(toname);
			toworkload.setUsername(username);
			toworkload.setTotime(totime);
			toworkload.setState(0);//0；已分配 1：已申报 2：审核通过 3：审核不通过
			boolean bool=toworkloadService.insert(toworkload);
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
	
	
	//删除指定信息
			@RequestMapping("/delete")
			@ResponseBody
			public MessageBean delete(String toid) {
				MessageBean messageBean=new MessageBean("");
				int toid1=Integer.parseInt(toid);
				boolean bool=toworkloadService.deleteByPrimaryKey(toid1);
				messageBean.setSuccess(bool);
				return messageBean;
			}
			
			
		/*//更改数据
		@RequestMapping("/update")
		@ResponseBody
		public MessageBean update(String toid,String username,String totime,String toname) {
			MessageBean messageBean=new MessageBean("");
			List<Toworkload> list=toworkloadService.selectAll();
			boolean bl=false;
			boolean bool=false;
			for (Toworkload toworkload : list) {
				if(toname.equals(toworkload.getToname())&&(toworkload.getUsername()==username)) {
					messageBean.setSuccess(false);
					messageBean.setMsg("该工作量已分配给当前老师!");
					bl=true;
					break;
				}
			}
			if(!bl) {
				Toworkload toworkload=new Toworkload();
				toworkload.setToid(Integer.parseInt(toid));
				toworkload.setUsername(username);
				toworkload.setTotime(totime);
				toworkload.setToname(toname);
				bool=toworkloadService.updateByPrimaryKeySelective(toworkload);
				if(bool) {
					messageBean.setMsg("修改成功");
				}else {
					messageBean.setMsg("修改失败!");
				}
			}
						
			messageBean.setSuccess(bool);
						
			return messageBean;
						
		}*/
			
}
