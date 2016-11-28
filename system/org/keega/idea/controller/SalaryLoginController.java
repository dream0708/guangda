package org.keega.idea.controller;

import org.keega.idea.service.ISalaryLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;

/**
 * Created by zun.wei on 2016/11/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping("/salary")
public class SalaryLoginController {

    @Inject
    private ISalaryLoginService salaryLoginService;

    //薪酬验证密码
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String salaryLogin(Model model,@RequestParam(name = "A0100") String A0l00) {
        model.addAttribute("A0100", A0l00);
        return "/table/salarylogin";
    }

    //ajax验证薪酬密码
    @ResponseBody
    @RequestMapping(value = "/password",method = RequestMethod.POST)
    public String getPassWord(@RequestParam(name = "password") String password,
                              @RequestParam(name = "A0100") String A0100) {
        String pwd = salaryLoginService.getLoginPassword(A0100);
        boolean login = salaryLoginService.cheakPassword(password, pwd);
        if (!login) return "密码有误请重新输入！";
        else return "yes";
    }

    //前往薪酬页面
    @RequestMapping(value = "/salarypage",method = RequestMethod.GET)
    public String go2SalaryPage() {
        return "/table/test";
    }

    //前往修改密码成功页面
    @RequestMapping(value = "/changePwdSuccessPage",method = RequestMethod.GET)
    public String go2ChangePwdSuccessPage() {
        return "/table/test";
    }

    //进入修改薪酬密码页面
    @RequestMapping(value = "changePwdInit",method = RequestMethod.GET)
    public String changePasswordInit(Model model,@RequestParam(name = "A0100") String A0l00){
        model.addAttribute("A0100", A0l00);
        return "/table/repeatpwd";
    }

    //ajax修改薪酬密码
    @ResponseBody
    @RequestMapping(value = "changePwd",method = RequestMethod.POST)
    public String changePassword(@RequestParam(name = "oldPwd") String oldPwd,
                                 @RequestParam(name = "newPwd") String newPwd,
                                 @RequestParam(name = "confPwd") String confPwd,
                                 @RequestParam(name = "A0100") String A0100){
        String pwd = salaryLoginService.getLoginPassword(A0100);
        return salaryLoginService.cheakChangePassword(oldPwd,pwd,newPwd,confPwd,A0100);
    }

    //重置初始密码
    @ResponseBody
    @RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
    public String resetInitPwd(@RequestParam(name = "A0100") String A0100) {
        return salaryLoginService.restInitPwd(A0100);
    }


}
