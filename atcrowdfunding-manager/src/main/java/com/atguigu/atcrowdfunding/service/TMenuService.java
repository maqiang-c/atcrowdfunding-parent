package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TMenu;

import java.util.List;

public interface TMenuService {
    /**
     * 查询所有的菜单
     * @return
     */
    List<TMenu> listMenu();
}
