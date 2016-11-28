package org.keega.idea.model;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class TableSet {

    private String name;
    private String desc;
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "TableSet{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
