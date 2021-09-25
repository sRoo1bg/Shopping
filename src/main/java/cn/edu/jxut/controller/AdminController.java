package cn.edu.jxut.controller;

import cn.edu.jxut.pojo.Admin;
import cn.edu.jxut.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * @Program FivePart
 * @Description 登录控制器
 * @Author Rorschach
 * @Date 2021/9/23 9:24
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @RequestMapping("/login.action")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin=service.login(name, pwd);
        if(admin!=null){
            request.setAttribute("admin",admin);
            return "main";
        }else {
            request.setAttribute("errmsg","用户名或密码错误");
            return "login";
        }
    }
}
