package com.zzptc.twds.service;

import java.util.List;

import com.zzptc.twds.pojo.College;
import com.zzptc.twds.pojo.TCourses;

public interface CollegeService {

	public List<College> selectAll();
	public List<TCourses> selectAllCT();
	public List<TCourses> selectAllbyCid(Integer cid);
	//添加二级学院
	public boolean insertSelective(College college);
	
	//删除指定二级学院
	public boolean deleteByPrimaryKey(int cId);
	
	//修改二级学院信息
	public boolean updateByPrimaryKeySelective(College college);
}
