package com.licc.dove.controller;

import com.licc.dove.controller.base.BaseController;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/25 13:54
 * @see
 */
@Controller
public class IndexController extends BaseController {


  @RequestMapping("/index")
  public String welcome(Map<String, Object> model) {
     return "index1";
  }


}
