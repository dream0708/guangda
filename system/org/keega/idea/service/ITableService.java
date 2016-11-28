package org.keega.idea.service;

import org.keega.idea.model.TableField;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ITableService {
    public String initData(String flag);

    public String getPersionData(String personFalg,String A0100,String flag,String userType);

    public String codeTypeString(String codesetid,String parentid );

    public List<TableField> getListTableFields(String tableSetName, String persontFlag);

    public String getNotBaiscItem(List<TableField> tableFields,String tableName,String A0100,String flag);

    public void editNotBaiscData( String data, String A0100,String tableName);

    public void editBaiscData(String submitData, String A0100,String tableName);

    public void saveBaisReadyApprovalData(String submitData, String A0100,String tableName,String status);

    public void approvalBaiscInfo(String isPass, String A0100, String tableName,String submitData);

    public void saveOtherInfoData(String data, String tableName, String A0100);

    public void saveOtherInfoReadyApprovalData(String A0100, String tableName, String status);

    public String getInfo4Approval(String key,String orgId);

    public void deletePhoto(HttpServletRequest request, String A0100);

    public String uploadPhoto(MultipartFile Fdata, String A0100, HttpServletRequest request);

    public boolean checkHasPhoto(String A0100);

    public void lookImage(String A0100, HttpServletRequest request, HttpServletResponse response, Model model);
}
