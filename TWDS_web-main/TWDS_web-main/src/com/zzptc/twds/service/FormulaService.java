package com.zzptc.twds.service;

import java.util.List;

import com.zzptc.twds.pojo.Formula;
import com.zzptc.twds.pojo.Oworkload;
import com.zzptc.twds.pojo.Paras;
import com.zzptc.twds.pojo.Scientific;

public interface FormulaService {

	//查询全部工作量计算公式
	List<Formula> selectAll();
	
	//插入工作量计算公式
	 int insertSelective(Formula record);
	 
	 //通过公式名称查找数据
	 Formula selectByFname(String fname);
	 
	 //删除指定工作量计算公式
	 boolean deleteByPrimaryKey(Integer fid);
	 
	 //修改指定公式数据
	 boolean updateByPrimaryKey(Formula record);


	boolean updateScientific(Scientific scientific);
	boolean updateOworkload(Oworkload oworkload);

	boolean updatePara(Paras para);

	Paras  selectAllParas(Integer id);
}
