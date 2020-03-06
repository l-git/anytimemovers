package com.company.anytimemovers.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/4/24.
 */

@Mapper
public interface HelloDao {

    List<Map<String,Object>> select();

    int insert(Map<String, Object> param);


}
