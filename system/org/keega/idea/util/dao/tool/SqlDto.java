package org.keega.idea.util.dao.tool;

import java.util.Arrays;

/**
 * Created by zun.wei on 2016/10/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class SqlDto {
    private String sqlPart;
    private Object[] sqlParams;

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    public Object[] getSqlParams() {
        return sqlParams;
    }

    public void setSqlParams(Object[] sqlParams) {
        this.sqlParams = sqlParams;
    }

    @Override
    public String toString() {
        return "SqlDto{" +
                "sqlPart='" + sqlPart + '\'' +
                ", sqlParams=" + Arrays.toString(sqlParams) +
                '}';
    }
}
