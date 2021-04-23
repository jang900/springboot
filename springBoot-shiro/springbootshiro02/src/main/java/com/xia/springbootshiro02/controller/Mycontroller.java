package com.xia.springbootshiro02.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Mycontroller {

    @RequestMapping({"/index","/"})
    public String index(Model model){
        model.addAttribute("msg","hello shiro");
        return "index";
    }

    @RequestMapping({"/user/add"})
    public String add(){
        return "user/add";
    }

    @RequestMapping({"/user/update"})
    public String update(){
        return "user/update";
    }

    @RequestMapping({"/tologin"})
    public String tologin(){
        return "login";
    }

    @RequestMapping({"/login"})
    public String login(String username,String password,Model model){
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // System.out.println("subject=========>"+subject.getPrincipal()+"||"+subject.getSession());
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // System.out.println("token========>"+token.getUsername()+"||"+token.getPassword()+"|,|"+token.getPrincipal()+"||"+token.getCredentials());
        try {
            subject.login(token);
            return "index";
        }catch (UnknownAccountException uae) { // 用户与密码不存在
            model.addAttribute("msg","用户名错误或不存在");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg","用户名密码错误");
            return "login";
        }


    }
    @RequestMapping({"/Unauthorized"})
    @ResponseBody
    public String Unauthorized(){
        return "没有授权 需要授权才能访问";
    }

}
