package com.zzptc.twds.service;

import java.util.List;

import com.zzptc.twds.pojo.Toscientific;
import com.zzptc.twds.pojo.Toworkload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzptc.twds.dao.TCoursesMapper;
import com.zzptc.twds.pojo.TCourses;
import com.zzptc.twds.pojo.TCoursesExample;


@Service
public class TCoursesServiceImpl implements TCoursesService {

	@Autowired
	private TCoursesMapper tCoursesMapper;
	
	@Override
	public boolean insertSelective(TCourses record) {
		int i=tCoursesMapper.insertSelective(record);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<TCourses> selectAll() {
		return tCoursesMapper.selectAll();
	}

	@Override
	public List<TCourses> selectAllTCourses() {
		return tCoursesMapper.selectAllTCourses();
	}

	@Override
	public List<TCourses> selectAllTCoursesByUserid(String  username) {
		return tCoursesMapper.selectAllTCoursesByUserid(username);
	}

    @Override
    public List<TCourses> selectAllTCoursesByUserName(String  username) {
        return tCoursesMapper.selectAllTCoursesByUserName(username);
    }
	@Override
	public List<TCourses> selectAllTCoursesByAdmin() {
		return tCoursesMapper.selectAllTCoursesByAdmin();
	}
	@Override
	public List <Toworkload> selectDeclareTworkView(String  username) {
		return tCoursesMapper.selectDeclareTworkView(username);
	}
    @Override
    public List <Toworkload> selectOtherWorkByUserName(String  username) {
        return tCoursesMapper.selectOtherWorkByUserName(username);
    }
	@Override
	public List <Toworkload> selectOtherWorkByAdmin() {
		return tCoursesMapper.selectOtherWorkByAdmin();
	}
	@Override
	public List <Toscientific> selectDeclareScientificView(String  username) {
		return tCoursesMapper.selectDeclareScientificView(username);
	}
	@Override
	public List <Toscientific> selectScientificByAdmin() {
		return tCoursesMapper.selectScientificByAdmin();
	}
    @Override
    public List <Toscientific> selectScientificByUserName(String  username) {
        return tCoursesMapper.selectScientificByUserName(username);
    }
	@Override
	public TCourses selectBycoId(Integer coId) {
		return tCoursesMapper.selectBycoId(coId);
	}

	@Override
	public boolean deleteByPrimaryKey(int tid) {
		int i=tCoursesMapper.deleteByPrimaryKey(tid);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public TCourses selectByPrimaryKey(int tid) {
		return tCoursesMapper.selectByPrimaryKey(tid);
	}


	@Override
	public String searchState(Integer tid) {
		return tCoursesMapper.searchState(tid);
	}
	@Override
	public Integer searchStates(Integer toId) {
		return tCoursesMapper.searchStates(toId);
	}
	@Override
	public Integer searchScientificState(Integer toId) {
		return tCoursesMapper.searchScientificState(toId);
	}

	@Override
	public boolean updateByPrimaryKeySelective(TCourses record) {
		int i=tCoursesMapper.updateByPrimaryKeySelective(record);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateTwork(Toworkload record) {
		int i=tCoursesMapper.updateTwork(record);
		if(i>0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean updateTScientific(Toscientific record) {
		int i=tCoursesMapper.updateTScientific(record);
		if(i>0) {
			return true;
		}
		return false;
	}
	@Override
	public List <Toworkload> selectOtherTwork(  ) {
		return tCoursesMapper.selectOtherTwork();
	}
	@Override
	public List <Toworkload> selectOtherTworks(int cid) {
		return tCoursesMapper.selectOtherTworks(cid);
	}


	@Override
	public List <Toscientific> selectScientificWorks(  ) {
		return tCoursesMapper.selectScientificWorks();
	}
	@Override
	public List <Toscientific> selectScientificWork(int cid) {
		return tCoursesMapper.selectScientificWork(cid);
	}



}
