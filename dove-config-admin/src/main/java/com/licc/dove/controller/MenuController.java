package com.licc.dove.controller;

import java.io.IOException;
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
import com.licc.dove.param.MenuParam;
import com.licc.dove.param.PageParam;
import com.licc.dove.service.MenuService;
import com.licc.dove.util.ResponseVo;
import com.licc.dove.util.ResponseVoUtil;
import com.licc.dove.vo.MenuVO;

/**
 * <strong>菜单</strong>
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/26 15:27
 * @see
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {
    @Resource
    MenuService menuService;

    @RequestMapping(value = "/toMenuListPage", method = RequestMethod.GET)
    public String toMenuListPage() {
        return "/menu/menuList";
    }

    @RequestMapping(value = "/ajaxData", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo ajaxData(PageParam param, HttpServletRequest request) throws IOException {

        Page<MenuVO> pages = menuService.findAll(param);
        return this.result(pages, request.getParameter("draw"));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo save(@Validated MenuParam param) throws IOException {

        menuService.save(param);
        return ResponseVoUtil.successMsg("操作成功");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo delete(@PathVariable("id") Long id) throws IOException {
        menuService.deleteById(id);
        return ResponseVoUtil.successMsg("操作成功");
    }
}
