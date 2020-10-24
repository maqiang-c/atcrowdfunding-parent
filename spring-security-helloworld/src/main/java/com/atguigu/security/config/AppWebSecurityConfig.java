package com.atguigu.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 是spring security(安全)框架的配置文件(配置类)
 *
 * @Configuration   (组合注解)  扫描管理项目中的所有组件(标注@component注解的类)
 * @EnableWebSecurity (组合注解)  支持springsecurity框架中的注解
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)//：开启全局的细粒度方法级别权限控制功能
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        //在内存中临时加载用户信息
//        auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("学徒")
//                .and()
//                .withUser("lisi").password("123").authorities("罗汉拳","武当长拳","太极拳");

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // super.configure(http);  //调用父类中的授权的方法(所有的资源全部拦截)
        //authorize(授权) permit(许可)
        //自定义授权的方法(首页和静态资源放行)
        http.authorizeRequests().antMatchers("/index.jsp","/layui/**").permitAll()
                //实验六：基于角色的访问控制
                .antMatchers("/level1/**").hasAnyRole("学徒","大师","宗师")
                .antMatchers("/level1/2").hasAuthority("太极拳")
                .antMatchers("/level2/**").hasRole("大师")
                .antMatchers("/level3/**").hasRole("宗师")


                .anyRequest().authenticated();//除index.jsp和layui中所有的静态元素外其他的请求必须经过身份认证
        //HTTP Status 403   Access Denied  访问受限
        //3.2实验二：默认及自定义登录页
        http.formLogin().loginPage("/index.jsp");//访问受限后进入的页面
        //http.formLogin().loginProcessingUrl();  //指定登录后台控制器方法的地址
        //http.formLogin().defaultSuccessUrl(""); //登录成功默认的进入的页面

        //  实验三  改造登录页面  指定对应的空控制器方法
        //使用springsecurity中默认的login控制其方法
        http.formLogin().loginProcessingUrl("/login")
                .usernameParameter("username")  //获取账号
                 .passwordParameter("password")  //获取密码parameter 参数
                .defaultSuccessUrl("/main.html");//登录成功之后挑战到具体的控制器
        http.csrf().disable();  //disable(禁用)  暂时禁用csrf


        //实验五：用户注销完成
        http.logout();

        //实验七：自定义访问拒绝处理页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");


        http.rememberMe();
    }
}
