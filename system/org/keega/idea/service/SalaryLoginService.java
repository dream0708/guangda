package org.keega.idea.service;

import org.keega.idea.dao.ISalaryDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by zun.wei on 2016/11/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service("salaryLoginService")
public class SalaryLoginService implements ISalaryLoginService {

    @Inject
    private ISalaryDao salaryDao;

    @Override
    public String getLoginPassword(String A0100) {
        String sql = "select pwd as password from UsrA01 where A0100=?";
        Map<String,Object> mapPassword = salaryDao.querySingleMapByOraginalSql(sql, A0100);
        return (String) mapPassword.get("password");
    }

    @Override
    public boolean cheakPassword(String loginPassword,String password) {
        if (password == null && loginPassword != null) {
            return false;
        } else if (password != null) {
            return password.equals(loginPassword);
        }
        return false;
    }

    @Override
    public String cheakChangePassword(String loginPwd,String oldPwd, String newPwd, String confPwd,String A0100) {
        if (!newPwd.equals(confPwd)) {
            return "新修改的密码不一致，请重新输入！";
        } else if (newPwd.equals(confPwd) && !oldPwd.equals(newPwd) && !oldPwd.equals(confPwd) && oldPwd.equals(loginPwd)) {
            //TODO在这里执行更新修改原来的薪酬密码。
            salaryDao.updateRowByOraginalSql(confPwd,A0100);
            return "yes";
        } else if (!oldPwd.equals(loginPwd)) {
            return "你输入的旧密码有误，请重新输入！";
        } else {
            return "修改失败，请确认新旧密码!";
        }
    }

    @Override
    public String restInitPwd(String A0100) {
        salaryDao.resetInitPwd(A0100);
        return "success";
    }
}
