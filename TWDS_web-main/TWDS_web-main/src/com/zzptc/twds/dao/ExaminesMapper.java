package com.zzptc.twds.dao;

import com.zzptc.twds.pojo.*;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ExaminesMapper {
   /* int countByExample(ExaminesExample example);

    int deleteByExample(ExaminesExample example);

    int deleteByPrimaryKey(Integer eid);

    

    int insert(Examines record);

    List<Examines> selectByExample(ExaminesExample example);

    Examines selectByPrimaryKey(Integer eid);

    int updateByExampleSelective(@Param("record") Examines record, @Param("example") ExaminesExample example);

    int updateByExample(@Param("record") Examines record, @Param("example") ExaminesExample example);

    int updateByPrimaryKeySelective(Examines record);

    int updateByPrimaryKey(Examines record);*/
	
	int insertSelective(Examines record);
	int insertToscientific(Toscientific record);
	  List<Examines> selectAll(int userid);
	List<TCourses> selectAllTechWorkLoad(String username);
	  List<Examines> selectAllByresult();
	  
	  int updateByPrimaryKeySelective(TCourses record);

	int updateOther(Toworkload record);
	int updateScientific(Toscientific record);
	  List<Examines> selectAllByresult1();
	  
	  List<Examines> selectAllByCld1(int cId);
	  
	  List<Examines> selectAllByCld(int cId);
	  
	  List<Examines>  selectByresultandUserId(Examines examines);
	  
	  List<Examines> selectAllByresultPage(Examines examines);
	  
	
}