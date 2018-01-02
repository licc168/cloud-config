package com.licc.dove.controller;

import com.licc.dove.controller.base.BaseController;
import com.licc.dove.domain.User;
import com.licc.dove.service.UserService;
import com.licc.dove.util.ResponseVo;
import com.licc.dove.param.UserParam;
import com.licc.dove.util.ResponseVoUtil;
import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController extends BaseController {

    @Resource
    UserService userService;

    /**
     * <strong>注册用户</strong>
     * @param userParam

     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseVo register( @RequestBody UserParam userParam) {
        userService.register(userParam);
        return ResponseVoUtil.successMsg("注册成功");
    }




    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVo deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseVoUtil.successMsg("用户成功");
    }
}
