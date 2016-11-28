package org.keega.idea.dao;

import org.dom4j.Document;
import org.dom4j.Element;
import org.keega.idea.model.CodeType;
import org.keega.idea.model.TableField;
import org.keega.idea.model.TableSet;
import org.keega.idea.util.dao.impl.BaseDao;
import org.keega.idea.xml.XMLUtil;
import org.springframework.stereotype.Repository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Repository("tableDao")
public class TableDao extends BaseDao<CodeType> implements ITableDao {

    private Document document;

    public TableDao() {
        this.document = XMLUtil.getTableDocument();
    }

    private TableSet element2TableSet(Element element) {
        TableSet tableSet = new TableSet();
        tableSet.setDesc(element.attributeValue("desc"));
        tableSet.setName(element.attributeValue("name"));
        tableSet.setFlag(element.attributeValue("flag"));
        return tableSet;
    }

    private TableField element2TableField(TableField tableField,Element element) {
        tableField.setName(element.attributeValue("name"));
        tableField.setDataType(element.attributeValue("dataType"));
        tableField.setFieldCode(element.attributeValue("fieldCode"));
        tableField.setIsEdit(element.attributeValue("isEdit"));
        tableField.setFalg(element.attributeValue("flag"));
        tableField.setRequired(element.attributeValue("required"));
        tableField.setDesc(element.getText());
        return tableField;
    }


    @Override
    public List<TableSet> getListTableSets() {
        List<Element> es = document.getRootElement().elements();//this.document.selectNodes(document.getRootElement());
        List<TableSet> tableSets = new ArrayList<TableSet>();
        for (Element element : es) {
            tableSets.add(element2TableSet(element));
        }
        return tableSets;
    }

    @Override
    public List<TableField> getListTableFields(String tableSetName,String personFlag,String... userType) {
        List<Element> es = document.getRootElement().elements();//this.document.selectNodes(path);
        List<TableField> tableFields = new ArrayList<TableField>();
        for (int i = 0; i < es.size(); i++) {
            if (es.get(i).attributeValue("name").equals(tableSetName) && es.get(i).attributeValue("flag").equals(personFlag)) {
                //获取set中的flag是否为01
                List<Element> elements = es.get(i).elements();
                for (Element element1 : elements) {
                    TableField tableField = new TableField();
                    //tableField.setDesc(element.elementText("field"));
                    if ("02".equals(personFlag)) {
                        if (element1.attributeValue("flag").equals("01") || element1.attributeValue("flag").equals("03")) {
                            //获取field中是否为01或者03
                            tableFields.add(element2TableField(tableField, element1));
                        }
                    } else if ("01".equals(personFlag) && userType.length>0 && "0102".equals(userType[0])){
                        if (element1.attributeValue("flag").equals("01") || element1.attributeValue("flag").equals("03")) {
                            //获取field中是否为01或者03
                            tableFields.add(element2TableField(tableField, element1));
                        }
                    }else {
                        if (element1.attributeValue("flag").equals("01")) {
                            //获取field中是否为01
                            tableFields.add(element2TableField(tableField, element1));
                        }
                    }
                }
            }
        }
        return tableFields;
    }

    @Override
    public List<CodeType> getCodeType(String codesetId,String... value) {//codeitem
        String sql = null;
            if (!"UM".equals(codesetId) && !"UN".equals(codesetId) && !"@K".equals(codesetId)) {//UM:部门，UN:单位名称，@K:岗位名称
                if ("AT".equals(codesetId)){
                    if (value.length < 1 || "".equals(value)) {//政治面貌
                        sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end)  as parentid" +
                                "  from codeitem where\n" +
                                " (codeitemid='01' or codeitemid='02' or codeitemid='13' or codeitemid='03'\n" +
                                " or codeitemid='99') and codesetid='AT'";
                        return super.queryBeansListByOriginalSql(CodeType.class,sql);
                    } else {
                        sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end)  as parentid" +
                                "  from codeitem where " +
                                " (codeitemid='01' or codeitemid='02' or codeitemid='13' or codeitemid='03'" +
                                " or codeitemid='99') and codesetid='AT' and codeitemid='"+value[0]+"'";
                        return super.queryBeansListByOriginalSql(CodeType.class,sql);//,codesetId,value[0]
                    }
                }else if ("AJ".equals(codesetId)){
                if (value.length < 1 || "".equals(value)) {//专业技术资格名称
                    sql = "select * from codeitem where (codeitemid='011' or codeitemid='012' or\n" +
                            " codeitemid='013' or codeitemid='014' or codeitemid='05' or codeitemid='081' or\n" +
                            " codeitemid='082' or codeitemid='083' or codeitemid='084' or codeitemid='085' or\n" +
                            " codeitemid='122' or codeitemid='123' or codeitemid='124' or codeitemid='133' or\n" +
                            " codeitemid='134' or codeitemid='135' or codeitemid='193' or codeitemid='39' or\n" +
                            " codeitemid='682' or codeitemid='683' or codeitemid='684' or codeitemid='685' ) and codesetid='AJ'";
                    return super.queryBeansListByOriginalSql(CodeType.class,sql);
                } else {
                    sql = "select * from codeitem where (codeitemid='011' or codeitemid='012' or\n" +
                            " codeitemid='013' or codeitemid='014' or codeitemid='05' or codeitemid='081' or\n" +
                            " codeitemid='082' or codeitemid='083' or codeitemid='084' or codeitemid='085' or\n" +
                            " codeitemid='122' or codeitemid='123' or codeitemid='124' or codeitemid='133' or\n" +
                            " codeitemid='134' or codeitemid='135' or codeitemid='193' or codeitemid='39' or\n" +
                            " codeitemid='682' or codeitemid='683' or codeitemid='684' or codeitemid='685' ) and codesetid=? and codeitemid=?";
                    return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
                }
            }else if ("YT".equals(codesetId)){
                    if (value.length < 1 || "".equals(value)) {//学历：小学以后都不要。
                        sql = "select codeitemid,codeitemdesc,parentid from codeitem \n" +
                                "where codesetid = 'YT' and codeitemid!='11' and codeitemid!='12'";
                        return super.queryBeansListByOriginalSql(CodeType.class,sql);
                    } else {
                        sql = "select codeitemid,codeitemdesc,parentid from codeitem \n" +
                                "where codeitemid!='11' and codeitemid!='12' and codesetid =? and codeitemid=?";
                        return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
                    }
                }else if ("BF".equals(codesetId)){//A01的健康状况
                    if (value.length < 1 || "".equals(value)) {
                        sql = "select codeitemid,codeitemdesc," +
                                " (case when parentid = codeitemid then '-1' else parentid end) parentid" +
                                " from codeitem \n" +
                                " where codesetid = 'BF' and (codeitemid='1' or codeitemid='2')";
                        return super.queryBeansListByOriginalSql(CodeType.class,sql);
                    } else {
                        sql = "select codeitemid,codeitemdesc," +
                                " parentid "+
                                " from codeitem \n" +
                                " where codesetid=? and codeitemid=? and (codeitemid='1' or codeitemid='2')";
                        return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
                    }
                }else if ("AN".equals(codesetId)) {//学位证
                        if (value.length < 1 || "".equals(value)) {
                            sql = "select codeitemid,codeitemdesc,parentid from codeitem where codesetid = 'AN' and \n" +
                                    " (codeitemid='0' or codeitemid='2' or\n" +
                                    " codeitemid='3' or codeitemid='4' or codeitemid='9')";
                            return super.queryBeansListByOriginalSql(CodeType.class,sql);
                        } else {
                            sql = "select codeitemid,codeitemdesc,parentid from codeitem where codesetid = ? and codeitemid=? and\n" +
                                    " (codeitemid='0' or codeitemid='2' or\n" +
                                    " codeitemid='3' or codeitemid='4' or codeitemid='9')";
                            return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
                        }
                }else if ("AS".equals(codesetId)) {//如果是与本人关系下拉项
                    if (value.length < 1 || "".equals(value)) {

                        /*sql = "select codeitemid,codeitemdesc,parentid" +
                            " from dbo.codeitem c where " +
                            " (c.codeitemid='11' or c.codeitemid='12' or c.codeitemid='22' or c.codeitemid='23' " +
                            " or c.codeitemid='24' or c.codeitemid='25' or c.codeitemid='32' or c.codeitemid='33' " +
                            " or c.codeitemid='34' or c.codeitemid='35' or c.codeitemid='41' or c.codeitemid='42' " +
                            " or c.codeitemid='43' or c.codeitemid='44' or c.codeitemid='51' or c.codeitemid='52' " +
                            " or c.codeitemid='53' or c.codeitemid='54' or c.codeitemid='55' or c.codeitemid='56' " +
                            " or c.codeitemid='71' or c.codeitemid='72' or c.codeitemid='73' or c.codeitemid='21' " +
                            " or c.codeitemid='31' " +
                            " or c.codeitemid='75' or c.codeitemid='76' or c.codeitemid='77' or c.codeitemid='78') " +
                            " and codesetid='AS' ";*/
                        sql = "select codeitemid,codeitemdesc,parentid" +
                            " from dbo.codeitem c where " +
                            " (c.codeitemid='11' or c.codeitemid='12' " +
                            " or c.codeitemid='2' or c.codeitemid='3' or c.codeitemid='51' or c.codeitemid='52')" +
                            " and codesetid='AS' ";

                    return super.queryBeansListByOriginalSql(CodeType.class,sql);//,codesetId
                } else {

                    /*sql = "select codeitemid,codeitemdesc,\n" +
                            " (case when parentid = codeitemid then '-1' else parentid end) parentid \n" +
                            " from dbo.codeitem c where \n" +
                            " (c.codeitemid='11' or c.codeitemid='12' or c.codeitemid='22' or c.codeitemid='23'\n" +
                            " or c.codeitemid='24' or c.codeitemid='25' or c.codeitemid='32' or c.codeitemid='33'\n" +
                            " or c.codeitemid='34' or c.codeitemid='35' or c.codeitemid='41' or c.codeitemid='42'\n" +
                            " or c.codeitemid='43' or c.codeitemid='44' or c.codeitemid='51' or c.codeitemid='52'\n" +
                            " or c.codeitemid='53' or c.codeitemid='54' or c.codeitemid='55' or c.codeitemid='56'\n" +
                            " or c.codeitemid='71' or c.codeitemid='72' or c.codeitemid='73' or c.codeitemid='74'\n" +
                            " or c.codeitemid='75' or c.codeitemid='76' or c.codeitemid='77' or c.codeitemid='78') \n" +
                            " and c.codesetid=? and codeitemid =?";*/
                    sql = "select codeitemid,codeitemdesc,\n" +
                            " (case when parentid = codeitemid then '-1' else parentid end) parentid \n" +
                            " from dbo.codeitem c where \n" +
                            " (c.codeitemid='11' or c.codeitemid='12' \n" +
                            " or c.codeitemid='2' or c.codeitemid='3' or c.codeitemid='51' or c.codeitemid='52')\n" +
                            " and c.codesetid=? and codeitemid =?";

                    return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
                }
            } else {
                if (value.length < 1 || "".equals(value)) {
                    sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end)  as parentid " +
                            "from dbo.codeitem where codesetid=?";
                    return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId);
                } else {
                    sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end)  as parentid " +
                            "from dbo.codeitem where codesetid=? and codeitemid =?";
                    return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
                }
            }
        } else {
            if (value.length < 1 || "".equals(value)) {//(case when parentid = codeitemid then '-1' else parentid end) as parentid
                sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end) as parentid " +
                        "from organization where codesetid=? and end_date ='9999-12-31 00:00:00.000'";
                return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId);
            } else {
                sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end) as parentid " +
                        "from organization where codesetid=? and end_date ='9999-12-31 00:00:00.000' and codeitemid =?";
                return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,value[0]);
            }
        }
    }

    @Override
    public List<CodeType> getOrganizationCodeType(String codesetId,String parentid) {
        String sql = null;
        sql = "select codeitemid,codeitemdesc,(case when parentid = codeitemid then '-1' else parentid end) parentid " +
                "from organization where codesetid=? and parentid=?" +
                " and end_date ='9999-12-31 00:00:00.000'";
        return super.queryBeansListByOriginalSql(CodeType.class,sql,codesetId,parentid);
    }

    @Override
    public List<Map<String, Object>> getNotBaiscItem(List<TableField> tableFields,String tableName, String A0100) {
        StringBuffer stringBuffer = new StringBuffer("I9999 ");
        for (int i = 0; i < tableFields.size(); i++) {
            stringBuffer.append(tableFields.get(i).getName()).append(" ,");
        }
        String fields = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
        String sql = "select "+fields+" from "+tableName+" where A0100 = ?";
        //System.out.println(sql);
        return super.queryListMapByOriginalSql(sql,A0100);
    }

    @Override
    public String queryMaxI9999(String tableName, String A0100) {
        String sql = "select MAX(I9999) as I9999 from "+tableName +" where A0100 = ?";
        Map<String,Object> map = super.querySingleMapByOriginalSql(sql, A0100);
        return map.get("I9999")+"";
    }

    @Override
    public Map<String, Object> getBasicItem(List<TableField> tableFields,String tableName,String A0100) {
        StringBuffer stringBuffer = new StringBuffer(" ");
        for (int i = 0; i < tableFields.size(); i++) {
            stringBuffer.append(tableFields.get(i).getName()).append(" ,");
        }
        String fields = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
        String sql = "select "+fields+" from "+tableName+" where A0100 = ?";
        //System.out.println(sql);
        return super.querySingleMapByOriginalSql(sql, A0100);
    }

    @Override
    public Map<String, Object> getModifyBaiscInfoData(String A0100) {
        String sql = "select * from basicinfomodify where modifyperson = ?";
        return super.querySingleMapByOriginalSql(sql, A0100);
    }

    @Override//获取的status有四个状态，0表示仅保存，1表示已提交审核，2表示审核通过，3表示审核未通过
    public Map<String, Object> checkModifyStatus(String modifyPerson) {
        String sql = "select modifystatus from basicinfomodify where modifyperson = ?";
        return super.querySingleMapByOriginalSql(sql, modifyPerson);
    }

    @Override
    public Map<String, Object> checkModifyCount(String modifyPerson) {
        String sql = "select COUNT(*) as count from basicinfomodify where modifyperson = ?";
        return super.querySingleMapByOriginalSql(sql, modifyPerson);
    }

    @Override
    public void insertModifyData(String sql, Object... realValue) {
        super.insertRowByOriginalSql(sql, realValue);
    }

    @Override
    public Map<String, Object> getModifyOtherInfoData(String tableName, String A0100) {
        String sql = "select * from otherinfomodify where modifyperson = ? and modifytablename = ?";
        return super.querySingleMapByOriginalSql(sql, A0100,tableName);
    }

    @Override
    public Map<String, Object> checkModifyOtherInfoStatus(String tableName, String A0100) {
        String sql = "select modifystatus from otherinfomodify where modifyperson = ? and modifytablename = ?";
        return super.querySingleMapByOriginalSql(sql, A0100,tableName);
    }

    @Override
    public Map<String, Object> checkModifyOtherInfoCount(String tableName, String A0100) {
        String sql = "select COUNT(*) as count from otherinfomodify where modifyperson = ? and modifytablename = ?";
        return super.querySingleMapByOriginalSql(sql, A0100,tableName);
    }

    @Override
    public List<Map<String, Object>> getAllI9999(String tableName,String A0100) {
        String sql = "select I9999 from " + tableName + " where A0100 = ?";
        return super.queryListMapByOriginalSql(sql, A0100);
    }

    @Override
    public List<Map<String, Object>> queryAllInfo4Approval(String sql) {
        return super.queryListMapByOriginalSql(sql);
    }

    @Override
    public List<TableField> getA01ListTableFieldByFlagEquals03() {
        List<Element> es = document.getRootElement().elements();
        List<TableField> tableFields = new ArrayList<TableField>();
        for (int i = 0; i < es.size(); i++) {
            if (es.get(i).attributeValue("name").equals("A01") && es.get(i).attributeValue("flag").equals("01")) {
                //获取set中的flag是否为01
                List<Element> elements = es.get(i).elements();
                for (Element element1 : elements) {
                    TableField tableField = new TableField();
                    //tableField.setDesc(element.elementText("field"));
                    if (element1.attributeValue("flag").equals("03")) {
                        //获取field中是否为03
                        tableFields.add(element2TableField(tableField,element1));
                    }
                }
            }
        }
        return tableFields;
    }



}
