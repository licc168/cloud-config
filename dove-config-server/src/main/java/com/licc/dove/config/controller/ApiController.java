package com.licc.dove.config.controller;

import com.licc.dove.config.service.ApiService;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 服务端对完API
 * @author lichangchao
 * @version 1.0.0
 * @date 2018/1/4 13:43
 * @see
 */
@Controller
@RequestMapping(value = "api")
public class ApiController {
  @Resource
  ApiService apiService;

  @RequestMapping(value = "/getProfileByIp", method = RequestMethod.GET)
  @ResponseBody
  public String getProfileByIp(@RequestParam String ip) throws IOException {
    String profile = apiService.getProfileByIp(ip);
    return profile;
  }
}
