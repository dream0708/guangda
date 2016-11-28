package org.keega.idea.dao;

import org.keega.idea.model.CodeType;
import org.keega.idea.model.TableField;
import org.keega.idea.model.TableSet;

import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ITableDao {
    public List<TableSet> getListTableSets();

    public List<CodeType> getCodeType(String codesetId,String... value);

    public List<CodeType> getOrganizationCodeType(String codesetId,String parentid);

    public List<TableField> getListTableFields(String tableSetName,String persontFlag,String... userType);

    public List<Map<String, Object>> getNotBaiscItem(List<TableField> tableFields,String tableName,String A0100);

    public String queryMaxI9999(String tableName, String A0100);

    public Map<String, Object> getBasicItem(List<TableField> tableFields,String tableName,String A0100);

    public void insertRowByOriginalSql(String sql,Object... realValue);

    public void deleteRowByOriginalSql(String sql, Object... realValue);

    public void updateRowByOriginalSql(String sql, Object... realValue);

    public Map<String, Object> getModifyBaiscInfoData(String A0100);

    public Map<String, Object> checkModifyStatus(String modifyPerson);

    public Map<String, Object> checkModifyCount(String modifyPerson);

    public void insertModifyData(String sql,Object... realValue);

    public Map<String, Object> getModifyOtherInfoData(String tableName, String A0100);

    public Map<String, Object> checkModifyOtherInfoStatus(String tableName, String A0100);

    public Map<String, Object> checkModifyOtherInfoCount(String tableName, String A0100);

    public List<Map<String, Object>> getAllI9999(String tableName,String A0100);

    public List<Map<String, Object>> queryAllInfo4Approval(String sql);

    public List<TableField> getA01ListTableFieldByFlagEquals03();
}
