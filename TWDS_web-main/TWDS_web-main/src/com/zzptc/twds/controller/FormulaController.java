package com.zzptc.twds.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zzptc.twds.pojo.*;
import com.zzptc.twds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzptc.twds.utils.MessageBean;

@Controller
@RequestMapping("/formula")
public class FormulaController {
	private HttpSession session;

	@Autowired
	private FormulaService formulaService;

	@Autowired
	private OworkloadService oworkloadService;
	@Autowired
	private ScienfiticService scienfiticService;
	//查询全部工作量计算公式
	/*@RequestMapping("/selectAll")
	public ModelAndView selectAll(HttpSession session) {
		ModelAndView view=new ModelAndView();
		List<Formula> list=formulaService.selectAll();
		view.addObject("list", list);
		view.setViewName("formula/formula");
		return view;
	}*/
	
	
	//查询全部工作量计算公式
	/*	@RequestMapping("/selectAllFormula")
		@ResponseBody
		public List<Formula> selectAll() {
			List<Formula> list=formulaService.selectAll();
			return list;
		}*/
		
		
		//添加计算公式(视图)
		@RequestMapping("/addFormulaView")
		public ModelAndView insertView(HttpServletRequest request) {
			ModelAndView view=new ModelAndView();
			session=request.getSession();
			Paras paras=formulaService.selectAllParas(0);
			List<Scientific> scientificList=scienfiticService.selectAllScientific();
			view.addObject("scientificList", scientificList);
			view.addObject("paras", paras);
			List<Oworkload> oworkloadList=oworkloadService.selectAll();
			view.addObject("oworkloadList", oworkloadList);
			request.setAttribute("scientificLists",scientificList);
			request.setAttribute("oworkloadLists",oworkloadList);
			view.setViewName("formula/addformula");
			return view;
		}
		
		/*
		 * 添加工作量参数
		 * */
		@RequestMapping("/insert")
		@ResponseBody
		public MessageBean insert(HttpServletRequest request, String bvalue, String yvalue, String rvalue1, String rvalue2, String rvalue3,String rvalue4, String xfvalue1, String xfvalue2,String scientificMapStr,String oworkloadMapStr) {
			MessageBean messageBean=new MessageBean("");
			Boolean bool=true;
			Map<String, Object> scientificMap = JSONObject.parseObject(scientificMapStr, new TypeReference<Map<String, Object>>() {});//将scientificMapStr解析为一个JSONObject对象并返回
			Map<String, Object> oworkloadMap = JSONObject.parseObject(oworkloadMapStr, new TypeReference<Map<String, Object>>() {});
			for (String key : scientificMap.keySet()) {
				Double para = Double.parseDouble(scientificMap.get(key).toString());
				Integer scientificId=Integer.parseInt(key);
				Scientific scientific=new Scientific();
				scientific.setScientificId(scientificId);
				scientific.setPara(para);
				 bool=bool&&formulaService.updateScientific(scientific);
			}
			for (String key : oworkloadMap.keySet()) {
				Double para = Double.parseDouble(oworkloadMap.get(key).toString());
				Integer oid=Integer.parseInt(key);
				Oworkload oworkload=new Oworkload();
				oworkload.setOid(oid);
				oworkload.setPara(para);
				bool=bool&&formulaService.updateOworkload(oworkload);
			}
			Paras paras=new Paras();
			paras.setBvalue(Double.parseDouble(bvalue));
			paras.setId(0);
			paras.setRvalue1(Double.parseDouble(rvalue1));
			paras.setRvalue2(Double.parseDouble(rvalue2));
			paras.setRvalue3(Double.parseDouble(rvalue3));
			paras.setRvalue4(Double.parseDouble(rvalue4));
			paras.setYvalue(Double.parseDouble(yvalue));
			paras.setXfvalue1(Double.parseDouble(xfvalue1));
			paras.setXfvalue2(Double.parseDouble(xfvalue2));
			bool=bool&&formulaService.updatePara(paras);
				if(bool) {
					messageBean.setSuccess(true);
					messageBean.setMsg("修改成功");
				}else {
					messageBean.setSuccess(false);
					messageBean.setMsg("修改失败");
				}

			return messageBean;
		
		}
		
		
		//删除指定公式
		/*@RequestMapping("/delete")
		@ResponseBody
		public MessageBean delete(String fid) {
			
			MessageBean messageBean=new MessageBean("");
			int fid1=Integer.parseInt(fid);
			boolean bool=formulaService.deleteByPrimaryKey(fid1);
			messageBean.setSuccess(bool);
			return messageBean;
			
		}*/
		
		

/*
				//修改指定公式数据
				@RequestMapping("/update")
				@ResponseBody
				public MessageBean update(String fid,String expression,String fname,String p1,String p2,String p3,String oldfname) {
					MessageBean messageBean=new MessageBean("");

					double P1=0;
					double P2=0;
					double P3=0;
					int fid1=Integer.parseInt(fid);
					if(p1!=null&&!p1.equals("")) {
						P1=Double.parseDouble(p1);
					}
					if(p2!=null&&!p2.equals("")) {
						P2=Double.parseDouble(p2);
					}
					if(p3!=null&&!p3.equals("")) {
						P3=Double.parseDouble(p3);
					}

					List<Formula> list=formulaService.selectAll();
					boolean bl=false;
					for (Formula formula : list) {
						if(fname.equals(formula.getFname())) {
							messageBean.setSuccess(false);
							messageBean.setMsg("该公式已存在!");
							bl=true;
							break;
						}
					}
					if(fname.equals(oldfname)) {
						bl=false;
					}
					if(!bl) {
						Formula formula=new Formula();
						formula.setFid(fid1);
						formula.setExpression(expression);
						formula.setFname(fname);
						formula.setP1(P1);
						formula.setP2(P2);
						formula.setP3(P3);

						boolean bool=formulaService.updateByPrimaryKey(formula);
						if(!bool) {
							messageBean.setMsg("修改失败!");
						}else {
							messageBean.setMsg("修改成功!");
						}
						messageBean.setSuccess(bool);

					}

					return messageBean;
			}
		*/
}
