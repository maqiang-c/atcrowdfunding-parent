package com.atguigu.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    //认证

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //登录
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());


    }


    //授权

    /**
     * authorize 授权
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //自定义资源放行(首页和静态资源)
        http.authorizeRequests().antMatchers("/welcome.jsp", "/static/**").permitAll()//放行


                .anyRequest().authenticated();//除index.jsp 和所有的在layui的所有静态资源  其他的请求必须要经过身份认证才能访问
        //3.2	实验二：默认及自定义登录页
        http.formLogin().loginPage("/welcome.jsp");

        //试验三 改造登录页面  指定对应的控制器方法
        //使用springsecurity中默认的login控制器
        http.formLogin().loginProcessingUrl("/login")
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")//getParamemter("")
                .defaultSuccessUrl("/main");//登录成功之后跳转到具体的控制器

        http.csrf().disable();//服务器端禁用csrf的验证
        //实验五  注销
        http.logout();

        //第二种处理异常的方式实现异常处理器
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){

                    response.getWriter().write("403");

                }else {


                    request.setAttribute("err", accessDeniedException.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/unauth.jsp").forward(request, response);
                }
            }
        });

        //实验八  记住我  cookie版
        http.rememberMe();
    }
}
