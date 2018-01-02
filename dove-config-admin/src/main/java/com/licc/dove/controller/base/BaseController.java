package com.licc.dove.controller.base;

import com.licc.dove.dao.Page;
import com.licc.dove.service.MenuService;
import com.licc.dove.util.ResponseVo;
import com.licc.dove.vo.MenuVO;
import com.licc.dove.util.ResponseVoUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/25 17:36
 * @see
 */

public class BaseController {
  @Resource
  MenuService menuService;

  @ModelAttribute
  public void init(Map<String, Object> model){
    List<MenuVO> menuList = menuService.findAndChildrenByParentId(false,0L);
    model.put("menus",menuList);
  }






  public  <T> ResponseVo result(Page<T> page,String draw)  {
    if(StringUtils.isEmpty(draw)) draw = "0";
    return  ResponseVoUtil.successData(page).setDraw(Integer.parseInt(draw));
  }
}
