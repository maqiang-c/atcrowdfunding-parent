package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TRole;

import java.util.List;

public interface TRoleService {

    List<TRole> listRoles(String keyWord);

    Integer saveRole(TRole tRole);

    Integer deleteRole(List<Integer> idInt);
}
