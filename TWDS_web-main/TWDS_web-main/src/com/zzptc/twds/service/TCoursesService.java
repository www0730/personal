package com.zzptc.twds.service;

import java.util.List;

import com.zzptc.twds.pojo.TCourses;
import com.zzptc.twds.pojo.Toscientific;
import com.zzptc.twds.pojo.Toworkload;

import javax.persistence.criteria.CriteriaBuilder;

public interface TCoursesService {
	
	//添加老师申报课程数据
	boolean insertSelective(TCourses record);
	
	//查询所有数据
	List<TCourses> selectAll();
	
	//多表联合查询当前用户所有数据
	List<TCourses> selectAllTCourses();
	
	//多表联合查询当前用户所有数据
	List<TCourses> selectAllTCoursesByUserid(String username);
    List<TCourses> selectAllTCoursesByUserName(String username);
	List<Toworkload> selectDeclareTworkView(String username);
	List<Toscientific> selectDeclareScientificView(String username);
    List<Toscientific> selectScientificByUserName(String username);
	List<Toworkload> selectOtherWorkByAdmin();
    List<Toworkload> selectOtherWorkByUserName(String username);
	List<TCourses> selectAllTCoursesByAdmin();
	List<Toscientific> selectScientificByAdmin();
	//根据课程id查询记录
	TCourses selectBycoId(Integer coId);
	
	//删除指定id数据
	boolean deleteByPrimaryKey(int tid);
	
	//通过id查询
	TCourses selectByPrimaryKey(int tid);
	
	//根据id修改数据
	boolean updateByPrimaryKeySelective(TCourses record);

	//根据id修改数据
	boolean updateTwork(Toworkload record);
	boolean updateTScientific(Toscientific record);

	String searchState(Integer tid);

	Integer searchStates(Integer toId);
	Integer searchScientificState(Integer toId);

	List<Toworkload> selectOtherTwork();
	List<Toworkload> selectOtherTworks(int cid);

	List<Toscientific> selectScientificWorks();
	List<Toscientific> selectScientificWork(int cid);


}
