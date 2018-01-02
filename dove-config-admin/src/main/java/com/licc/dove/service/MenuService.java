package com.licc.dove.service;

import com.licc.dove.dao.Order.Direction;
import com.licc.dove.dao.ParamMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.licc.dove.dao.CommonDao;
import com.licc.dove.dao.Order;
import com.licc.dove.dao.Page;
import com.licc.dove.domain.Menu;
import com.licc.dove.param.PageParam;
import com.licc.dove.service.base.impl.BaseServiceImpl;
import com.licc.dove.util.BeanMapper;
import com.licc.dove.vo.MenuVO;

/**
 *
 * <strong>菜单</strong>
 * 
 * @author lichangchao
 * @version 1.0.0
 * @date 2017/12/25 16:23
 * @see
 */
@Service
@Transactional
public class MenuService extends BaseServiceImpl<Menu> {
    @Resource
    CommonDao commonDao;

    public List<Menu> findByParentId(Boolean deleteFlag, Long parentId) {
        Menu menu = new Menu();
        menu.setDeleteFlag(deleteFlag);
        menu.setParentId(parentId);
        List<Order> orders = Lists.newArrayList(Order.create("order_num", Direction.ASC.name()));
        List<Menu> menuList = commonDao.listByExample(menu, orders);
        return menuList;
    }

    public List<MenuVO> findAndChildrenByParentId(Boolean deleteFlag, Long parentId) {
        List<Menu> menuList = this.findByParentId(deleteFlag, parentId);
        return setChildren(menuList);
    }

    public Page<MenuVO> findAll(PageParam param) {

        Page<MenuVO> menuPage = commonDao.findPageByParams(MenuVO.class, transPage(param), "MenuMapper.findList",new ParamMap());
        return menuPage;

    }

    List<MenuVO> setChildren(List<Menu> list) {
        List menuVos = Lists.newArrayList();
        for (Menu menu : list) {
            MenuVO menuVO = BeanMapper.map(menu, MenuVO.class);
            Long parentId = menuVO.getParentId();
            if (parentId == 0) {
                List<Menu> childrenList = this.findByParentId(false, menu.getId());
                menuVO.setChildren(childrenList);
            }
            menuVos.add(menuVO);
        }

        return menuVos;
    }

    List<MenuVO> setParentName(List<Menu> list) {
        List menuVos = Lists.newArrayList();
        for (Menu menu : list) {
            MenuVO menuVO = BeanMapper.map(menu, MenuVO.class);
            Long parentId = menuVO.getParentId();
            if (parentId != null) {
                Menu parent = commonDao.get(Menu.class, parentId);
                menuVO.setParentName(parent.getName());
            }
            menuVos.add(menuVO);
        }

        return menuVos;
    }

}
