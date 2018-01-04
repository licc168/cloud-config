package com.licc.dove.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.licc.dove.dao.CommonDao;
import com.licc.dove.domain.User;
import com.licc.dove.param.UserParam;
import com.licc.dove.service.base.impl.BaseServiceImpl;
import com.licc.dove.util.BeanMapper;


/**
 * @author lichangchao
 * @Time 2017 -03-29 09:21:05
 */
@Service
@Transactional
public class UserService extends BaseServiceImpl<User> {
    @Resource
    CommonDao commonDao;



    public User register(UserParam userParam) {
        User user = BeanMapper.map(userParam,User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreateTime(new Date());
        commonDao.save(user);
        return user;
    }





    public User getByUserName(String userName) {
        User user =  new User();
        user.setUserName(userName);
        user.setDeleteFlag(false);
       List<User> list =  commonDao.listByExample(user);
       if(CollectionUtils.isEmpty(list)){
           return  null;
       }
        return  list.get(0);
    }


}
