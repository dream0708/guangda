package org.keega.idea.model;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class TableField {
    private String name;
    private String dataType;
    private String fieldCode;
    private String isEdit;
    private String desc;
    private String falg;
    private String required;

    public String getFalg() {
        return falg;
    }

    public void setFalg(String falg) {
        this.falg = falg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String isRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return "TableField{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", fieldCode='" + fieldCode + '\'' +
                ", isEdit='" + isEdit + '\'' +
                ", desc='" + desc + '\'' +
                ", falg='" + falg + '\'' +
                ", required=" + required +
                '}';
    }
}
