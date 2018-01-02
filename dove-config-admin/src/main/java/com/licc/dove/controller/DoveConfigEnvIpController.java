package com.licc.dove.controller;

import com.licc.dove.param.DoveConfigEnvIpParam;
import com.licc.dove.service.DoveConfigEnvIpService;
import com.licc.dove.vo.DoveConfigEnvIpVO;
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
import com.licc.dove.domain.DoveConfigEnv;
import com.licc.dove.param.DoveConfigEnvParam;
import com.licc.dove.service.DoveConfigEnvService;
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
@RequestMapping("/config/envIp")
public class DoveConfigEnvIpController extends BaseController {
  @Resource
  DoveConfigEnvIpService doveConfigEnvIpService;
  @Resource
  DoveConfigEnvService doveConfigEnvService;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public String toPage(Map<String, Object> model) {
      List<DoveConfigEnv>  envs =  doveConfigEnvService.findAll();
      model.put("envs",envs);
      return "/config/configEnvIpList";
    }


  @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo ajaxData(DoveConfigEnvIpParam param, HttpServletRequest request) throws IOException {
    Page<DoveConfigEnvIpVO> pages = doveConfigEnvIpService.page(param);
    return this.result(pages, request.getParameter("draw"));
  }


  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public ResponseVo save(@Validated DoveConfigEnvIpParam param) throws IOException {

    doveConfigEnvIpService.save(param);
    return ResponseVoUtil.successMsg("操作成功");
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public ResponseVo delete(@PathVariable("id") Long id) throws IOException {
    doveConfigEnvIpService.updateDeleteFlagById(id);
    return ResponseVoUtil.successMsg("操作成功");
  }
}
