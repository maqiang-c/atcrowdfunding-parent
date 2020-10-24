package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.DateUtil;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAdminServiceImpl implements TAdminService {
    @Autowired
    private TAdminMapper tAdminMapper;
    @Override
    public TAdmin getTAdmin(TAdmin tAdmin) {
        //获取查询的条件  建立一个对象
        TAdminExample example = new TAdminExample();
        //通过对象创建条件   createCreteria(创造条件)
        TAdminExample.Criteria criteria = example.createCriteria();
        //创造的具体条件  登录的账号以及密码
        criteria.andLoginacctEqualTo(tAdmin.getLoginacct());//登录的账号
        criteria.andUserpswdEqualTo(new BCryptPasswordEncoder().encode(tAdmin.getUserpswd()));//登录的密码
        //查询表格  //通过条件查询
        List<TAdmin> tAdmins = tAdminMapper.selectByExample(example);
        //判断查询成功或失败的情况
        if (tAdmins == null ||tAdmins.size()==0) {
            //失败的情况抛出个异常
            throw new RuntimeException("你登录的账号不存在");
        }

        //tAdmin.get(0)为登录的账号
        return tAdmins.get(0);
    }

    /**
     * 分页查询方法
     * @param keyWord  查询的条件
     * @return
     */
    @Override
    public List<TAdmin> listTAdminpage(String keyWord) {

        //创建查询条件对象
        TAdminExample example = new TAdminExample();
        //判断查询的条件是否为空或空的字符串
        //StringUtil.isNotEmpty(keyWord) 非空验证的方法
        if (StringUtil.isNotEmpty(keyWord)) {  //对查询条件进行拼接
            //具体的查询条件1   模糊查询条件  loginacct
            TAdminExample.Criteria criteria1 = example.createCriteria();
            criteria1.andLoginacctLike("%" + keyWord + "%");
            //具体查询条件2   username
            TAdminExample.Criteria criteria2 = example.createCriteria();
            criteria2.andUsernameLike("%" + keyWord + "%");
            //具体查询条件三   email
            TAdminExample.Criteria criteria3 = example.createCriteria();
            criteria2.andEmailLike("%" + keyWord + "%");

            //将查询条件进行连接
            example.or(criteria2);
            example.or(criteria3);


        }

        //分页查询
        List<TAdmin> tAdminList = tAdminMapper.selectByExample(example);


        return tAdminList;
    }


    @Override
    public void saveAdmin(TAdmin tAdmin) {
        //补全参数 密码   添加日期
        tAdmin.setUserpswd(new BCryptPasswordEncoder().encode(Const.DEFALUT_PASSWORD));
        tAdmin.setCreatetime(DateUtil.getFormatTime());
        int i = tAdminMapper.insertSelective(tAdmin);
    }

    //查询一个的方法
    @Override
    public TAdmin getAdminById(Integer id) {

        TAdmin admin = tAdminMapper.selectByPrimaryKey(id);

        return admin;

    }

    //删除一个的 方法
    @Override

    public void deleteAdminById(Integer id) {
        tAdminMapper.deleteByPrimaryKey(id);
    }

    //修改
    @Override
    public void updateAdmin(TAdmin tAdmin) {
        tAdminMapper.updateByPrimaryKeySelective(tAdmin);
    }

    @Override
    public void deleteAdmins(List<Integer> idInts) {

        //建立删除条件
        TAdminExample example = new TAdminExample();
        TAdminExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idInts);

        tAdminMapper.deleteByExample(example);
    }

}
