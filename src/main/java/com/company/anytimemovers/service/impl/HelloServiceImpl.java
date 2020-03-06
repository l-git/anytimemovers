package com.company.anytimemovers.service.impl;


import com.company.anytimemovers.dao.HelloDao;
import com.company.anytimemovers.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2019/4/24.
 */

@Service
@Transactional
public class HelloServiceImpl implements HelloService {


    @Autowired
    private HelloDao helloDao;



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String hello(String msg) {

        List<Map<String,Object>> list=helloDao.select();

//        Map<String,Object> param=new HashMap<>();
//        param.put("username","8888");
//        param.put("password","8888");
//        param.put("createDate",new Date());
//
//        helloDao.insert(param);
//
//        if(true){
//            throw new RuntimeException("llll");
//        }

       // jdbcTemplate.execute("insert into t_system_user (username,password) values ('aaa','bbb')");

        return "index";
    }
}
