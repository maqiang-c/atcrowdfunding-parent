package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TRoleController {

    @Autowired
    private  TRoleService tRoleService;

    /**
     * 跳转角色到首页
     * @return
     */
    @RequestMapping("/role/index")
    public String index(){

        return "role/index";
    }

    @ResponseBody  //直接响应数据  异步操作需加载的注释
    @RequestMapping("/role/loadData")
    public PageInfo loadData(
           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false, defaultValue = "2") Integer pageSize,
            @RequestParam(value = "keyWord", required = false,defaultValue = "") String keyWord
    ){

            PageHelper.startPage(pageNum,pageSize);


        List<TRole> roleList = tRoleService.listRoles(keyWord);

        PageInfo<TRole>  pageInfo = new PageInfo<>(roleList,5);



        return pageInfo ;
    }

    //添加juese信息
    @ResponseBody
    @RequestMapping("/role/roleSave")
    public String roleSava(TRole tRole) {

        Integer i = tRoleService.saveRole(tRole);
        if (i == 1) {
            return "yes";
        }
        return "no";
    }

    //删除多个
    @ResponseBody
    @RequestMapping("/role/deleteRoles")
    public String deleteRoles(String ids){
        //将ids转换为its类型
        //chai
        String[] idStrs = ids.split(",");
        //类型转换

        List<Integer> idInt = new ArrayList<>();
        for (String idStr : idStrs) {
            int id = Integer.parseInt(idStr);
            idInt.add(id);
        }
        Integer i = tRoleService.deleteRole(idInt);
        if (i>0){
            return "yes";
        }
        return "no";
    }
}
