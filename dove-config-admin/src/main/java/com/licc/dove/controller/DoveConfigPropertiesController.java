package com.licc.dove.controller;

import com.licc.dove.param.DoveConfigPropertieParam;
import com.licc.dove.service.DoveConfigPropertiesService;
import com.licc.dove.vo.DoveConfigPropertiesVO;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.licc.dove.controller.base.BaseController;
import com.licc.dove.dao.Page;
import com.licc.dove.domain.DoveConfigApp;
import com.licc.dove.param.DoveConfigAppParam;
import com.licc.dove.service.DoveConfigAppService;
import com.licc.dove.util.ResponseVo;
import com.licc.dove.util.ResponseVoUtil;

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
@RequestMapping("/config/properties")
public class DoveConfigPropertiesController extends BaseController {
  @Resource
  DoveConfigPropertiesService doveConfigPropertiesService;
  @Resource
  DoveConfigAppService doveConfigAppService;


    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public String toPage(Map<String, Object> model) {
     List<DoveConfigApp> doveConfigAppList =  doveConfigAppService.findAll();
      model.put("apps",doveConfigAppList);
      return "/config/appPropertiesList";
    }


  @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo ajaxData(DoveConfigPropertieParam param, HttpServletRequest request) throws IOException {
    Page<DoveConfigPropertiesVO> pages = doveConfigPropertiesService.findAll(param);
    return this.result(pages, request.getParameter("draw"));
  }


  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo save(@Validated DoveConfigPropertieParam param) throws IOException {

    doveConfigPropertiesService.save(param);
    return ResponseVoUtil.successMsg("操作成功");
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseVo delete(@PathVariable("id") Long id) throws IOException {
    doveConfigPropertiesService.deleteById(id);
    return ResponseVoUtil.successMsg("操作成功");
  }
}
