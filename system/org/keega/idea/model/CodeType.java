package org.keega.idea.model;

import org.keega.idea.util.dao.annotation.SqlColumn;
import org.keega.idea.util.dao.annotation.SqlTableName;

/**
 * Created by zun.wei on 2016/10/13.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@SqlTableName("codeitem")
public class CodeType {
    //select codeitemid,codeitemdesc,parentid from codeitem where codesetid = 'AX'

    //@SqlColumn("codeitemid")
    private String codeitemid;
    //@SqlColumn()
    private String codeitemdesc;

    private String parentid;

    public String getCodeitemid() {
        return codeitemid;
    }

    public void setCodeitemid(String codeitemid) {
        this.codeitemid = codeitemid;
    }

    public String getCodeitemdesc() {
        return codeitemdesc;
    }

    public void setCodeitemdesc(String codeitemdesc) {
        this.codeitemdesc = codeitemdesc;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "CodeType{" +
                "codeitemid='" + codeitemid + '\'' +
                ", codeitemdesc='" + codeitemdesc + '\'' +
                ", parentid='" + parentid + '\'' +
                '}';
    }
}
