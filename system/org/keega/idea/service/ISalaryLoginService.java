package org.keega.idea.service;

/**
 * Created by zun.wei on 2016/11/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ISalaryLoginService {

    public String getLoginPassword(String A0100);

    public boolean cheakPassword(String loginPassword,String password);

    public String cheakChangePassword(String loginPwd,String oldPwd, String newPwd, String confPwd,String A0100);

    public String restInitPwd(String A0100);
}
