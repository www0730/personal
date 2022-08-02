package com.zzptc.twds.dao;

import com.zzptc.twds.pojo.TCourses;
import com.zzptc.twds.pojo.TCoursesExample;
import java.util.List;

import com.zzptc.twds.pojo.Toscientific;
import com.zzptc.twds.pojo.Toworkload;
import org.apache.ibatis.annotations.Param;

public interface TCoursesMapper {
   /* int countByExample(TCoursesExample example);

    int deleteByExample(TCoursesExample example);

   

    int insert(TCourses record);

  

    List<TCourses> selectByExample(TCoursesExample example);

 

    int updateByExampleSelective(@Param("record") TCourses record, @Param("example") TCoursesExample example);

    int updateByExample(@Param("record") TCourses record, @Param("example") TCoursesExample example);

   

    int updateByPrimaryKey(TCourses record);*/
	  
	int insertSelective(TCourses record);
	
	List<TCourses> selectAll();
	
	List<TCourses> selectAllTCourses( );
	
	List<TCourses> selectAllTCoursesByUserid( String username);
    List<TCourses> selectAllTCoursesByUserName( String username);
	List<TCourses> selectAllTCoursesByAdmin( );
	List<Toworkload> selectDeclareTworkView(String username);
	List<Toscientific> selectDeclareScientificView(String username);
    List<Toscientific> selectScientificByUserName(String username);
	List<Toscientific> selectScientificByAdmin();
    List<Toworkload> selectOtherWorkByUserName(String username);
	List<Toworkload> selectOtherWorkByAdmin();
	 TCourses selectBycoId(Integer coId);
	
	 int deleteByPrimaryKey(Integer tid);
	 
	   TCourses selectByPrimaryKey(Integer tid);
	   String searchState(Integer tid);
	Integer searchStates(Integer toId);


	Integer searchScientificState(Integer toId);
	   int updateByPrimaryKeySelective(TCourses record);
	int updateTwork(Toworkload record);
	int updateTScientific(Toscientific record);
	List<Toworkload> selectOtherTwork();
	List<Toworkload> selectOtherTworks(int cid);

	List<Toscientific> selectScientificWorks();
	List<Toscientific> selectScientificWork(int cid);
}