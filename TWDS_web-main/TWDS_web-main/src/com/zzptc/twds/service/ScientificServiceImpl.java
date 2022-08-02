package com.zzptc.twds.service;

import com.zzptc.twds.dao.OworkloadMapper;
import com.zzptc.twds.dao.ScientificMapper;
import com.zzptc.twds.pojo.Oworkload;
import com.zzptc.twds.pojo.Scientific;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificServiceImpl implements ScienfiticService {

	@Autowired
	private ScientificMapper scientificMapper;
	
/*
	@Override
	public boolean insert(Oworkload record) {
		int i=oworkloadMapper.insert(record);
		if(i>0) {
			return true;
		}
		return false;
	}
*/


	@Override
	public boolean insert(Scientific record) {
		int i=scientificMapper.insert(record);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Oworkload> selectAll() {
		return null;
	}

	@Override
	public List<Scientific> selectAllScientific() {
		return scientificMapper.selectAllScientific();
	}
	@Override
	public List<Scientific> selectAllScientifics() {
		return scientificMapper.selectAllScientifics();
	}
	@Override
	public boolean updateByPrimaryKeySelective(Scientific record) {
		int i=scientificMapper.updateByPrimaryKeySelective(record);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByPrimaryKey(int scientificId) {
		int i=scientificMapper.deleteByPrimaryKey(scientificId);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public Oworkload selectByPrimaryKey(Integer oid) {
		return null;
	}


	/*@Override
	public boolean updateByPrimaryKeySelective(Oworkload record) {
		int i=oworkloadMapper.updateByPrimaryKeySelective(record);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByPrimaryKey(int oid) {
		int i=oworkloadMapper.deleteByPrimaryKey(oid);
		if(i>0) {
			return true;
		}
		return false;
	}

	@Override
	public Oworkload selectByPrimaryKey(Integer oid) {
		return oworkloadMapper.selectByPrimaryKey(oid);
	}
*/
}
