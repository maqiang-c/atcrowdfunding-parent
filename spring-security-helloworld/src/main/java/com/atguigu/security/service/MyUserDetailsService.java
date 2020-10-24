package com.atguigu.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String queryUser = "SELECT * FROM `t_admin` WHERE loginacct=?";

//1、查询指定用户的信息
        Map<String, Object> map = jdbcTemplate.queryForMap(queryUser, username);
        //存放所有登录用户的角色和权限
        Set<GrantedAuthority> authorities = new HashSet<>();
        //查询用户的角色

        String roleSql=" select t_role.`name` from t_role INNER JOIN t_admin_role on(t_role.id=t_admin_role.roleid) INNER JOIN t_admin on(t_admin.id=t_admin_role.adminid) where t_admin.id=?";
        //查询用户的角色
        List<Map<String, Object>> roleList = jdbcTemplate.query(roleSql, new ColumnMapRowMapper(), map.get("id"));
//        String roleSql= "FROM t_role INNER JOIN t_admin_role on (t_role.id=t_admin_role.roleid) INNER JOIN t_admin ON (t_admin_role.adminid=t_admin.id) WHERE t_admin.id=?";
//        //new ColumnMapRowMapper(),将结果包装为一个map集合
//        List<Map<String, Object>> roleList = jdbcTemplate.query(roleSql, new ColumnMapRowMapper(), map.get("id"));
        //查询权限信息
        String PermissionSql="select t_permission.`name` from t_permission INNER JOIN t_role_permission on(t_permission.id=t_role_permission.permissionid) INNER JOIN t_role on(t_role.id=t_role_permission.roleid) INNER JOIN t_admin_role on(t_role.id=t_admin_role.roleid) where t_admin_role.adminid=?";
        List<Map<String, Object>> PermissionList = jdbcTemplate.query(PermissionSql, new ColumnMapRowMapper(), map.get("id"));

        for (Map<String, Object> roleMap : roleList) {
            //GrantedAuthority  是接口类型无法使用 须使用它的实现类new SimpleGrantedAuthority
            authorities.add(new SimpleGrantedAuthority("ROLE_"+roleMap.get("name").toString()));
        }

        for (Map<String, Object> stringObjectMap : PermissionList) {
            authorities.add(new SimpleGrantedAuthority(stringObjectMap.get("name").toString()));
        }
//2、将查询到的用户封装到框架使用的UserDetails里面
        return new User(map.get("loginacct").toString(), map.get("userpswd").toString(),
                authorities);//暂时写死，过后数据库中查
    }
}
