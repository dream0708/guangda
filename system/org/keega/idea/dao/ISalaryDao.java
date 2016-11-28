package org.keega.idea.dao;

import java.util.Map;

/**
 * Created by zun.wei on 2016/11/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ISalaryDao {

    public Map<String, Object> querySingleMapByOraginalSql(String sql, Object... realValue);

    public void updateRowByOraginalSql(Object... realValue);

    public void resetInitPwd(String A0100);


}
