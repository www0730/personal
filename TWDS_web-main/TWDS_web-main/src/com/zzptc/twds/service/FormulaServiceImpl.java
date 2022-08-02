package com.zzptc.twds.service;

import java.util.List;

import com.zzptc.twds.pojo.Oworkload;
import com.zzptc.twds.pojo.Paras;
import com.zzptc.twds.pojo.Scientific;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzptc.twds.dao.FormulaMapper;
import com.zzptc.twds.pojo.Formula;

@Service
public class FormulaServiceImpl implements FormulaService {

	@Autowired
	private FormulaMapper formulaMapper;
	
	@Override
	public List<Formula> selectAll() {
		return formulaMapper.selectAll();
	}

	
	@Override
	public int insertSelective(Formula record) {
		
		return formulaMapper.insertSelective(record);
	}


	@Override
	public Formula selectByFname(String fname) {
		return formulaMapper.selectByFname(fname);
	}


	@Override
	public boolean deleteByPrimaryKey(Integer fid) {
		int i=formulaMapper.deleteByPrimaryKey(fid);
		if(i>0) {
			return true;
		}
		
		return false;
	}


	@Override
	public boolean updateByPrimaryKey(Formula record) {
		int i=formulaMapper.updateByPrimaryKey(record);
		if(i>0) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean updateScientific(Scientific scientific) {
		int i=formulaMapper.updateScientific(scientific);
		if(i>0) {
			return true;
		}

		return false;
	}
	@Override
	public boolean updateOworkload(Oworkload oworkload) {
		int i=formulaMapper.updateOworkload(oworkload);
		if(i>0) {
			return true;
		}

		return false;
	}
	@Override
	public boolean updatePara(Paras para) {
		int i=formulaMapper.updatePara(para);
		if(i>0) {
			return true;
		}

		return false;
	}

	@Override
	public Paras selectAllParas(Integer id) {
		return formulaMapper.selectAllParas(id);
	}

}
