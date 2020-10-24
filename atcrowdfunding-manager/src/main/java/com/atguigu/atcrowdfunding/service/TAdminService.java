package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TAdmin;

import java.util.List;

public interface TAdminService {

    /**
     * 登录的方法
     * loginacct  登录的账号
     * userpswd  登录的密码
     * @return
     */
    public TAdmin getTAdmin(TAdmin tAdmin);

    List<TAdmin> listTAdminpage(String keyWord);

    void saveAdmin(TAdmin tAdmin);

     TAdmin getAdminById(Integer id);

    void deleteAdminById(Integer id);


    void updateAdmin(TAdmin tAdmin);

    void deleteAdmins(List<Integer> idInts);
}
