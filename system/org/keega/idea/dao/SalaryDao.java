package org.keega.idea.dao;

import org.keega.idea.util.dao.impl.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by zun.wei on 2016/11/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository("salaryDao")
public class SalaryDao extends BaseDao implements ISalaryDao {

    @Override
    public Map<String, Object> querySingleMapByOraginalSql(String sql, Object... realValue) {
        return super.querySingleMapByOriginalSql(sql, realValue);
    }

    @Override
    public void updateRowByOraginalSql(Object... realValue) {
        String sql = "update UsrA01 set pwd=? where A0100=?";
        super.updateRowByOriginalSql(sql,realValue);
    }

    @Override
    public void resetInitPwd(String A0100) {
        String sql = "update UsrA01 set pwd=RIGHT(A0177,6) where A0100=?";
        super.updateRowByOriginalSql(sql, A0100);
    }

}
