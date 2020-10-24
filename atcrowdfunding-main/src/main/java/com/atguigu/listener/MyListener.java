package com.atguigu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听servletcontext的创建以及销毁
 */
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //得到服务器上下文对象
        ServletContext servletContext = servletContextEvent.getServletContext();
        //获取服务器上下文中当前项目的路径
        String appPath = servletContext.getContextPath();
        //将路径放入servletcontext内
        servletContext.setAttribute("appPath",appPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
