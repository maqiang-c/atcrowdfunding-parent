package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.service.TRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TRoleServiceImpl implements TRoleService {

    @Autowired
    TRoleMapper tRoleMapper;
    @Override
    public List<TRole> listRoles(String keyWord) {
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%"+keyWord+"%");
        List<TRole> tRoles = tRoleMapper.selectByExample(example);
        return tRoles;
    }

    @Override
    public Integer saveRole(TRole tRole) {
        int i = tRoleMapper.insertSelective(tRole);
        return i;
    }

    @Override
    public Integer deleteRole(List<Integer> idInt) {
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idInt);
        int i = tRoleMapper.deleteByExample(example);
        return i;
    }

}
