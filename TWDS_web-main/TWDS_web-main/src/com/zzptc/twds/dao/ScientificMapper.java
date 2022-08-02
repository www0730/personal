package com.zzptc.twds.dao;

import com.zzptc.twds.pojo.Oworkload;
import com.zzptc.twds.pojo.Scientific;

import java.util.List;

public interface ScientificMapper {
   /* int countByExample(OworkloadExample example);

    int deleteByExample(OworkloadExample example);

   

   

    int insertSelective(Oworkload record);

    List<Oworkload> selectByExample(OworkloadExample example);

    
    int updateByExampleSelective(@Param("record") Oworkload record, @Param("example") OworkloadExample example);

    int updateByExample(@Param("record") Oworkload record, @Param("example") OworkloadExample example);

    int updateByPrimaryKeySelective(Oworkload record);

    int updateByPrimaryKey(Oworkload record);*/
	
	 int insert(Scientific record);
	 
	 List<Oworkload> selectAll();
	List<Scientific> selectAllScientific();
	List<Scientific> selectAllScientifics();
	int updateByPrimaryKeySelective(Scientific record);
	
	 int deleteByPrimaryKey(Integer scientificId);
	 
	 Oworkload selectByPrimaryKey(Integer oid);

}