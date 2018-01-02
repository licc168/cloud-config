package com.licc.dove.controller;

import com.licc.dove.dao.Page;
import com.licc.dove.domain.DoveConfigApp;
import com.licc.dove.param.DoveConfigAppParam;
import com.licc.dove.param.MenuParam;
import com.licc.dove.param.PageParam;
import com.licc.dove.service.DoveConfigAppService;
import com.licc.dove.service.MenuService;
import com.licc.dove.util.ResponseVo;
import com.licc.dove.util.ResponseVoUtil;
import com.licc.dove.vo.MenuVO;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.licc.dove.controller.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 配置中心-应用管理
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/29 13:14
 * @see
 */
@Controller
@RequestMapping("/config/app")
public class DoveConfigAppController extends BaseController {
  @Resource
  DoveConfigAppService doveConfigAppService;


    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public String toPage() {
        return "/config/appList";
    }


  @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo ajaxData(DoveConfigAppParam param, HttpServletRequest request) throws IOException {
    Page<DoveConfigApp> pages = doveConfigAppService.page(param);
    return this.result(pages, request.getParameter("draw"));
  }


  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo save(@Validated DoveConfigAppParam param) throws IOException {

    doveConfigAppService.save(param);
    return ResponseVoUtil.successMsg("操作成功");
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseVo delete(@PathVariable("id") Long id) throws IOException {
    doveConfigAppService.deleteById(id);
    return ResponseVoUtil.successMsg("操作成功");
  }
}
