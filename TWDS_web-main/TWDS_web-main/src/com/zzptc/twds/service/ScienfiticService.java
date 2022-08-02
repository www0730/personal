package com.zzptc.twds.service;

import com.zzptc.twds.pojo.Oworkload;
import com.zzptc.twds.pojo.Scientific;

import java.util.List;

public interface ScienfiticService {

	boolean insert(Scientific record);
	 
	//查询全部其它工作量
	 List<Oworkload> selectAll();

	List<Scientific> selectAllScientific();

	List<Scientific> selectAllScientifics();
	 boolean updateByPrimaryKeySelective(Scientific record);
	 

	 boolean deleteByPrimaryKey(int scientificId);
	 
	 //根据di查询其它工作量
	 Oworkload selectByPrimaryKey(Integer oid);
}
