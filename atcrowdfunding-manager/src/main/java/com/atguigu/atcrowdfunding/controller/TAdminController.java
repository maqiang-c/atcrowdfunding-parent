package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.service.TMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TAdminController {
    @Autowired
    private TAdminService tAdminService;
    @Autowired
    private TMenuService tMenuService;

    /**
     * 登录成功后跳转到main.jsp页面
     * @return
     */
    @RequestMapping("/main")
    public String main(HttpSession session){

        //获取查询到的全部的菜单项
        List<TMenu> tMenus = tMenuService.listMenu();

        //创建一个数组对象存放父节点
        ArrayList<TMenu> parentMenu = new ArrayList<>();

        for (TMenu tMenu : tMenus) {
            if (tMenu.getPid() == 0) {
                //将父节点保存到数组内
                parentMenu.add(tMenu);
            }
        }
        //获取父节点中的字节点
        for (TMenu child : tMenus) {
            //如果菜单选项的pid不为说明此菜单是子节点
            if (child.getPid() != 0) {
                //遍历所有的父节点
                for (TMenu parent : parentMenu) {
                    //如果父节点的id和子节点的pid相等说明此字节点在此父节点内
                    if (parent.getId() == child.getPid()){
                        //将子节点存入父节点内
                        parent.getChildMenus().add(child);
                    }

                }
            }

        }
        //将父节点存入域中
        session.setAttribute("parentMenu" , parentMenu);
        return "main";
    }

//    @RequestMapping("/login")
//    public String login(TAdmin tAdmin , HttpSession session ){
//
//        try {
//            //后面需要登录者的信息所以需要返回值 返回值如果有错与try一下
//            TAdmin tAdmin1 = tAdminService.getTAdmin(tAdmin);
//            //如果成功返回登陆者
//            session.setAttribute("loginAdmin",tAdmin1);
//        } catch (Exception e){
//            //如果有错误返回错误
//            session.setAttribute("err",e.getMessage());
//            return "redirect:welcome.jsp";
//        }
//        //重定向防止表单重复提交
//        return "redirect:/main";
//    }

    /**
     * 系统退出
     * @param
     * @return
     */
//    @RequestMapping("/logout")
//    public String logout(HttpSession session){
//        if (session == null) {
//            //手动关闭session
//            session.invalidate();
//        }
//        return "redirect:welcome.jsp";
//    }




    //访问t-admin的方法  (查询用户信息)
    /**
     *admin/index 必须和t-menu的url地址一致
     *1.支持条件查询
     * string keyword : 是们进行查询的条件  进行模糊查询
     * keyword :可能是  loginacct(账号)  username  email
     * 2.支持分页查询
     * pagenum:  查询当前页的页码
     *pagesize:  当前页码中显示的数据长度
     * @RequestParam: 用于设置上传的参数
     *                 value:上传过来参数的名字
     *                 required(需要):是否必须使用这个参数 true必须使用 false可以不使用
     *                 defaultValue (默认的参数): 如果没有收到这个参数就使用默认的参数
     * @return
     */
    @RequestMapping("/admin/index")
    public String index(
          @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize,
          @RequestParam(value = "pageNum",required = false,defaultValue = "1")  Integer pageNum,
          @RequestParam(value = "keyWord",required = false,defaultValue = "")  String keyWord,
          Model model
    ){
        //(页)pagehelper  一个进行分页的工具
        //此为sql select * from t_admin limit(限定) ?,?
        PageHelper.startPage(pageNum,pageSize);//相当于准备sql语句的分页条件 limit ?,?,会自动加到我们查询条件后面

        //分页查询的方法  返回一个集合
        List<TAdmin> tAdminList = tAdminService.listTAdminpage(keyWord);//相当于select *from t_admin where loginacct like%?%,or username like %?%

        //pageinfo 类是将数据分页栏制造的类 param(参数) 1 要分页的数据
         //                               param2    底部要显示的页数
        PageInfo<TAdmin> pageInfo = new PageInfo<>(tAdminList,5);//5  逻辑分页(页码)

        //将查询的结果放入model域中
        model.addAttribute("pageInfo", pageInfo);

        return "admin/index";
    }


    //调到添加页面
    @RequestMapping("/admin/toAdd")
    public String toAdd(){

        return"admin/add";
    }

    //添加的方法
    @RequestMapping("/admin/add")
    public String add(TAdmin tAdmin){

        tAdminService.saveAdmin(tAdmin);

        return "redirect:/admin/index?pageNum=" + Integer.MAX_VALUE;
    }

    //跳转到去修改的页面   edit(编译)
    @RequestMapping("/admin/toEdit")
    public String toEdit(Integer id ,Map<String,Object> map ,Integer pageNum){
        TAdmin admin = tAdminService.getAdminById(id);
        map.put("admin", admin);
        return "admin/edit";
    }

    //修改的方法

    @RequestMapping("/admin/edit")
    public String edit(TAdmin tAdmin ,Integer pageNum){

        tAdminService.updateAdmin(tAdmin);
        return "redirect:/admin/index?pageNum="+pageNum;
    }

    //删除的方法单个的方法

    @RequestMapping("/admin/del")
    public String del(Integer id) {
        tAdminService.deleteAdminById(id);
       return "redirect:/admin/index" ;
    }

    //删除多个的方法
    @RequestMapping("/admin/deleteBath")
    public String deleteBath(String ids){
        //进行字符串的分割  split(劈开)
        String[] idStrs= ids.split(",");

        //建立一个list集合存储id
        List<Integer> idInts = new ArrayList<>();
        for (String idStr : idStrs) {
            Integer id = Integer.parseInt(idStr);
            idInts.add(id);
        }
        tAdminService.deleteAdmins(idInts);
        return "redirect:/admin/index";
    }

}
