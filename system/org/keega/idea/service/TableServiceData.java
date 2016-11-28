package org.keega.idea.service;

import com.alibaba.fastjson.JSON;
import org.keega.idea.dao.ITableDao;
import org.keega.idea.upload.ImageDemo;
import org.keega.idea.upload.RequestUitl;
import org.keega.idea.model.CodeType;
import org.keega.idea.model.TableField;
import org.keega.idea.model.TableSet;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2016/10/13.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component("tableServiceData")
public class TableServiceData {

    @Inject
    private ITableDao tableDao;

    @Inject
    private ImageDemo imageDemo;


    //初始化数据
    String getInitData(String flag) {
       /* StringBuffer sb = new StringBuffer("<center>");
        sb.append("<div id='tbl' class=\"mini-toolbar\" style='width:1018px;text-align:left'>");
        if (!"01".equals(flag)) {
            sb.append(" <a id='button1' class=\"mini-button\" iconCls=\"icon-user\">员工页面</a>");
            sb.append(" <a id='button2' class=\"mini-button\" iconCls=\"icon-expand\">人事页面</a>");
        } else {
            sb.append(" <a id='button3' class=\"mini-button\" iconCls=\"icon-goto\">报批</a>");
        }
        sb.append("<span class=\"separator\"></span>");
        sb.append(" <a id='button4' class=\"mini-button\" iconCls=\"icon-upgrade\">返回</a>");
        sb.append("</div></center>");
        return sb.toString();*/
        return  null;
    }

    //获取表格数据信息
    void getTableData(List<TableSet> tableSets, String personFalg, StringBuffer sb, String A0100, String flag,String userType) {
        for (TableSet tableSet : tableSets) {
            if (tableSet.getFlag().equals(personFalg)) {
                this.setEmployeeData(sb, tableSet, personFalg,A0100,flag,userType);//基本信息
                this.setAllData(sb, tableSet, personFalg,A0100,userType,flag);
            }
        }
        //sb.append("</table>").append("</center>");
        sb.append("</table>").append("</td>").append("</tr>").append("</table>").append("</center>");
    }

    //设置数据
    private void setAllData(StringBuffer sb, TableSet tableSet, String personFlag,String A0100,String userType,String flag) {
        if (!tableSet.getName().equals("A01")) {
            this.rowData(sb, tableSet, tableSet.getName(), personFlag,A0100,userType,flag);
        }
    }


    //处理基本信息以外的表。
    private void rowData(StringBuffer sb, TableSet tableSet, String tableSetName, String personFlag,String A0100,String userType,String flag) {
        List<TableField> tableFields = tableDao.getListTableFields(tableSet.getName(), personFlag);
        if (tableSet.getFlag().equals(personFlag)) {// && mapList.size() >0 如果某个指标集没有值则不显示。
            sb.append("<tr class='a1' style='height:40px'>");
            sb.append("<td colspan='6' style='padding:2px'>");

            sb.append("<div style=\"width:1024px;\">");// style='width:1018px;text-align:left'
            sb.append("<div class=\"mini-toolbar\" style=\"border-bottom:0;padding:0px;\">");
            sb.append("<table style=\"width:100%;\">");
            sb.append("<tr>");
            sb.append("<td style=\"width:100%;text-align:left\">").append("<b>").append(tableSet.getDesc()).append("</b>");
            Map<String, Object> status = tableDao.checkModifyStatus(A0100);
            sb.append("<div style=\"float: right;\">");
            if ("01".equals(userType) && status == null) {
                sb.append("<span class=\"separator\"></span>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-add\" onclick=\"addRow"+tableSet.getName()+"()\" plain=\"true\" tooltip=\"增加...\">增加</a>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-remove\" onclick=\"removeRow"+tableSet.getName()+"()\" plain=\"true\">删除</a>");
                sb.append("<span class=\"separator\"></span>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-save\" onclick=\"saveData"+tableSet.getName()+"()\" plain=\"true\">保存</a>");
            } else if ("01".equals(userType) && status != null && !"1".equals(status.get("modifystatus"))) {
                sb.append("<span class=\"separator\"></span>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-add\" onclick=\"addRow"+tableSet.getName()+"()\" plain=\"true\" tooltip=\"增加....\">增加</a>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-remove\" onclick=\"removeRow"+tableSet.getName()+"()\" plain=\"true\">删除</a>");
                sb.append("<span class=\"separator\"></span>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-save\" onclick=\"saveData"+tableSet.getName()+"()\" plain=\"true\">保存</a>");
            } else if ("02".equals(flag)) {
                sb.append("<span class=\"separator\"></span>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-add\" onclick=\"addRow"+tableSet.getName()+"()\" plain=\"true\" tooltip=\"增加.....\">增加</a>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-remove\" onclick=\"removeRow"+tableSet.getName()+"()\" plain=\"true\">删除</a>");
                sb.append("<span class=\"separator\"></span>");
                sb.append("<a class=\"mini-button\" iconCls=\"icon-save\" onclick=\"saveData"+tableSet.getName()+"()\" plain=\"true\">保存</a>");
            }
            sb.append("</div>");
            sb.append("</td>");
            sb.append("</tr>");
            sb.append("</table>");
            sb.append("</div>");
            sb.append("</div>");
            //TODO ...其他子集url位置
            sb.append("<div id=\"" + tableSetName + "\" class=\"mini-datagrid\" style=\"width:100%;height:200px;\" allowSortColumn=\"false\" " +
                    " idField=\"I9999\" allowResize=\"false\" ");
            if (status != null && "1".equals(status.get("modifystatus")) && flag.equals("01")) {
                sb.append(" allowCellEdit=\"false\" ");
            } else {
                sb.append(" allowCellEdit=\"true\" ");
            }
            sb.append(" allowCellSelect=\"true\" multiSelect=\"true\" editNextOnEnterKey=\"true\" editNextRowCell=\"true\" showPager=\"false\" ");
            sb.append(" url=\"../table/gridData?tableName="+tableSetName+"&personFlag="+personFlag+"&A0100="+A0100+"&flag="+flag+"\">");
            sb.append("<div property=\"columns\">");
            sb.append("<div type=\"indexcolumn\"></div>");
            sb.append("<div type=\"checkcolumn\"></div>");
            for (int k = 1; k < tableFields.size(); k++) {
                sb.append(getInputType1(tableFields.get(k), A0100));
            }
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</td>");
            sb.append("</tr>");
        }
    }

    //显示的基本信息
    private void setEmployeeData(StringBuffer sb, TableSet tableSet, String personFlag,String A0100,String flag,String userType) {
        List<TableField> tableFields = tableDao.getListTableFields(tableSet.getName(), personFlag,flag+userType);
        Map<String, Object> status = tableDao.checkModifyStatus(A0100);
        if (tableSet.getFlag().equals(personFlag) && tableFields != null && tableSet.getName().equals("A01")) {
            sb.append("<center>").append("<table>").append("<tr>");
            sb.append("<td style=\"width: 1260px;\">");
            //sb.append("<div style=\"position:absolute;top:0;left:150px\">");
            sb.append("<div style=\"float: left;\">");
            //sb.append("<div>");
          /*  Map<String, String> map = new HashMap<String, String>();
            String uploadPath = null;
            try {
                String x = A0100+"-"+(Math.random()*100);
                map.put("A0100", x);
                uploadPath = RequestUitl.getUploadPath()+"/"+x+".jpg";
                imageDemo.readDB2Image(A0100,uploadPath);
            } finally {
                RequestUitl.removeUploadPath();//alt="还没有个人照片.."
            }*/
            //sb.append("<div>").append(tableSet.getDesc()).append("</div>");
            //sb.append("<img src=\"/upload/photo/"+map.get("A0100")+".jpg\" style=\"height: 150px;width: 120px;\" alt=\"点击浏览添加个人照片..\" /><br>\n");
            sb.append("<img src=\"../table/getPhoto?A0100="+A0100+"\" style=\"height: 150px;width: 120px;\" alt=\"点击浏览添加个人照片..\" /><br>\n");
            if ("01".equals(flag) && "01".equals(userType) && status != null && !"1".equals(status.get("modifystatus"))) {
                sb.append("<span style='color:red;'>").append("请上传白底正装证").append("<br>").append("件照");
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                sb.append("<input class=\"mini-htmlfile\" name=\"Fdata\" id=\"file1\" style=\"width:57px;\" " +
                        " onfileselect=\"ajaxFileUpload()\"/><br>\n");
                sb.append("</span>");
            } else if ("01".equals(flag) && "01".equals(userType) && status == null) {
                sb.append("<span style='color:red;'>").append("请上传白底正装证").append("<br>").append("件照");
                sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                sb.append("<input class=\"mini-htmlfile\" name=\"Fdata\" id=\"file1\" style=\"width:57px;\" " +
                        " onfileselect=\"ajaxFileUpload()\"/><br>\n");
                sb.append("</span>");
            }
            sb.append("</div>");

            //sb.append("</td>");
            //sb.append("<td>");

            //sb.append("<center>").append("<table id='A01' class='t1'  border='1' style='width:1024px'>");
            sb.append("<table id='A01' class='t1'  border='1' style='width:1024px'>");
            sb.append("<tr class='a1' style='height:40px'>");
            sb.append("<th align='left' colspan='6' >");//class="img1"

            sb.append("<div style=\"float: right;\">");
            if (status != null && "01".equals(flag) && "1".equals(status.get("modifystatus")) && "01".equals(userType)) {
                sb.append("<span style=\"color:red;\" id=\"yitijiao\">人事信息审核中...</span>");
                sb.append("<span class=\"separator\"></span>");
            } else if (status != null && "01".equals(flag) && "3".equals(status.get("modifystatus")) && "01".equals(userType)) {
                sb.append("<span style=\"color:red;\">审核未通过，请检查修改后重新提交审核！</span>");
                sb.append("<span class=\"separator\"></span>");
            }

            if ("01".equals(flag)) {
                if ("01".equals(userType) && status == null && !"02".equals(userType)) {
                    sb.append("<a id='buttonSavA01' class=\"mini-button\" iconCls=\"icon-save\" onclick='empsav(\"A01\",\"yes\")' >保存</a>");
                } else if (status != null && !"1".equals(status.get("modifystatus")) && !"02".equals(userType)) {
                    sb.append("<a id='buttonSavA01' class=\"mini-button\" iconCls=\"icon-save\" onclick='empsav(\"A01\",\"yes\")' >保存</a>");
                } else if ("02".equals(userType)) {

                }
            } else if ("02".equals(flag)){
                sb.append("<a id='buttonSavA01' class=\"mini-button\" iconCls=\"icon-save\" onclick='hrsav(\"A01\",\"yes\")' >保存</a>");
            }

            if (!"01".equals(flag)) {
                sb.append("<span class=\"separator\"></span>");
                sb.append(" <a id='button1' class=\"mini-button\" onclick=\"bt1Click1()\" iconCls=\"icon-user\">员工页面</a>");
                sb.append(" <a id='button2' class=\"mini-button\" onclick=\"bt1Click2()\" iconCls=\"icon-expand\">人事页面</a>");
            } else {
                if ("01".equals(userType)) {
                    sb.append("<span class=\"separator\"></span>");
                    if (status == null) {
                        sb.append(" <a id='button3' class=\"mini-button\" onclick='bt1Click3(\"A01\")' iconCls=\"icon-goto\">提交</a>");
                    } else if (!"1".equals(status.get("modifystatus"))) {
                        sb.append(" <a id='button3' class=\"mini-button\" onclick='bt1Click3(\"A01\")' iconCls=\"icon-goto\">提交</a>");
                    }
                } else if ("02".equals(userType)) {
                    if (status != null && "1".equals(status.get("modifystatus"))) {
                        sb.append(" <a id='buttonAuditing' class=\"mini-button\" onclick='btAuditing(\"A01\")' iconCls=\"icon-goto\">驳回</a>");
                        sb.append("<span class=\"separator\"></span>");
                        sb.append(" <a id='buttonAuditingPass' class=\"mini-button\" onclick='btAuditingPass(\"A01\")' iconCls=\"icon-goto\">同意</a>");
                    }
                } else {
                    //这个仅仅是在开发的时候方便而设置的一个而外的条件。
                    //sb.append(" <a id='buttonAuditing' class=\"mini-button\" onclick='btAuditing(\"A01\")' iconCls=\"icon-goto\">审核</a>");
                }
            }

            sb.append("</div>");
            sb.append("</th>");
            sb.append("</tr>");
            setDateEmployeeOrHuman(sb, tableFields, personFlag, tableSet,A0100,"A01",flag,userType);
        }
    }

    //基本信息的处理
    private void setDateEmployeeOrHuman(StringBuffer sb, List<TableField> tableFields, String personFlag,
                                        TableSet tableSet,String A0100,String tableName,String flag,String userType) {
        //如果flag=02表示人事登录。查询modifybaiscinfo中的status空为没修改过，0表示保存时默认状态，1表示提交审核中,2表示审核后
        //Map<String,Object> map = null;
        Map map = null;
        if ("02".equals(flag)) {
            map = tableDao.getBasicItem(tableFields, "Usr" + tableName, A0100);
        } else if ("01".equals(flag)){
            Map<String, Object> status = tableDao.checkModifyStatus(A0100);
            if (status == null || status.get("modifystatus") == null) {//如果为空，表示该员工从来没有修改过数据。
                //System.out.println("111111");
                map = tableDao.getBasicItem(tableFields, "Usr" + tableName, A0100);
            } else if ("1".equals(status.get("modifystatus"))) { //表示该员工修改过数据，并且已经提交但是为审批
                //System.out.println("22222");
                List<TableField> tableFieldsFlag03 = tableDao.getA01ListTableFieldByFlagEquals03();//获取A01中所有配置了flag=01,field为03的所有字段
                Map mapFlag03 = tableDao.getBasicItem(tableFieldsFlag03, "Usr" + tableName, A0100);
                String stringFlag03 = ","+JSON.toJSONString(mapFlag03).substring(1);
                Map<String,Object> modifyData = tableDao.getModifyBaiscInfoData(A0100);
                String modifycontent = (String) modifyData.get("modifycontent");
                String modifycontent1 = modifycontent.substring(0, modifycontent.length() - 1);
                String newMapString = modifycontent1 + stringFlag03;
                map = JSON.parseObject(newMapString, Map.class);
                //map = JSON.parseObject(modifycontent, Map.class);
            } else if ("0".equals(status.get("modifystatus"))){// 该员工仅仅是保存而已并没有提交审批
                //System.out.println("3333333");
                Map<String,Object> modifyData = tableDao.getModifyBaiscInfoData(A0100);
                String modifycontent = (String) modifyData.get("modifycontent");
                map = JSON.parseObject(modifycontent, Map.class);
            }else if ("2".equals(status.get("modifystatus"))) {//表示修改过的数据已通过审核
                //System.out.println("44444444444");
                map = tableDao.getBasicItem(tableFields, "Usr" + tableName, A0100);
            } else if ("3".equals(status.get("modifystatus"))) {//审核未通过
                //System.out.println("5555555555555");
                List<TableField> tableFieldsFlag03 = tableDao.getA01ListTableFieldByFlagEquals03();//获取A01中所有配置了flag=01,field为03的所有字段
                Map mapFlag03 = tableDao.getBasicItem(tableFieldsFlag03, "Usr" + tableName, A0100);
                String stringFlag03 = ","+JSON.toJSONString(mapFlag03).substring(1);
                Map<String,Object> modifyData = tableDao.getModifyBaiscInfoData(A0100);
                String modifycontent = (String) modifyData.get("modifycontent");
                //String modifycontent2 = modifycontent.substring(0, modifycontent.length() - 1);
                String newMapString = modifycontent.substring(0, modifycontent.length() - 1) + stringFlag03;
                map = JSON.parseObject(newMapString, Map.class);
                //map = tableDao.getBasicItem(tableFields, "Usr" + tableName, A0100);
            }//其他状态显示从UsrA01中取
        }

        if (tableSet.getFlag().equals(personFlag)) {
            int k = 0;
            for (int j = 0; j < tableFields.size(); j++) {
                if (k == 3) k = 0;
                if (k == 0) sb.append("<tr>");
                if (tableSet.getFlag().equals(personFlag)) {//当set里面的Flag等于登录人的身份（flag）的时候
                    //k = excuteEmployeeData(j, sb, tableFields, personFlag,A0100,map,userType);
                    Object value = null;
                    if (tableFields.get(j).getFalg().equals("01")) {
                        //field里面的flag等于01时候表示显示的字段。03表示领导审核才能显示的字段
                        if (map != null && map.size() > 0) {
                            value = map.get(tableFields.get(j).getName());//tableFields.get(j).isRequired();
                        }
                        sb.append("<th align='left' style='width:120px'>");//170
                        sb.append(tableFields.get(j).getDesc());
                        if ("true".equals(tableFields.get(j).isRequired())) {
                            sb.append("<span style=\"color:red\">*</span>");
                        }
                        sb.append("</th>")
                                .append("<td align='left' style='width:256px'>")
                                .append(getInputType(tableFields.get(j), A0100, value))
                                .append("</td>");
                        k++;
                    } else if (tableFields.get(j).getFalg().equals("03") && "02".equals(userType)) {
                        if (map != null && map.size() > 0) {
                            value = map.get(tableFields.get(j).getName());//tableFields.get(j).isRequired();
                        }
                        sb.append("<th align='left' style='width:120px'>");//170
                        sb.append(tableFields.get(j).getDesc());
                        if ("true".equals(tableFields.get(j).isRequired())) {
                            sb.append("<span style=\"color:red\">*</span>");
                        }
                        sb.append("</th>")
                                .append("<td align='left' style='width:255px'>")
                                .append(getInputType(tableFields.get(j), A0100, value))
                                .append("</td>");
                        k++;
                    }
                }
                if (k == 3) sb.append("</tr>");
            }
        }
    }

    //获取input类型
    private String getInputType(TableField tableField,String A0100,Object value) {
        String name = tableField.getName();//指标名称
        String dataType = tableField.getDataType();//数据类型
        String fieldCode = tableField.getFieldCode();//代码类代号
        String isEditor = tableField.getIsEdit();//是否可编辑
        String required = tableField.isRequired();//是否必填
        //System.out.println(required+"<-----必填项");
        //String desc = tableField.getDesc();
        return inputType(dataType, name, fieldCode,value,required,isEditor);
    }

    //获取input类型
    private String getInputType1(TableField tableField, String A0100) {
        String name = tableField.getName();//指标名称
        String dataType = tableField.getDataType();//数据类型
        String fieldCode = tableField.getFieldCode();//代码类代号
        //String isEditor = tableField.getIsEdit();//是否可编辑
        String required = tableField.isRequired();//是否必填
        String desc = tableField.getDesc();
        return inputType1(dataType, name, fieldCode,desc);
    }

    //判断其他子集指标
    private String inputType1(String dataType, String name, String fieldCode,String desc) {
        StringBuffer sb = new StringBuffer();
        if ("数值型".equals(dataType)) {
            sb.append("<div field=\""+name+"\"  allowSort=\"true\" >"+desc+"");
            sb.append("<input property=\"editor\" class=\"mini-spinner\"  minValue=\"0\" maxValue=\"250\" value=\"25\" style=\"width:100%;\"/>");
            sb.append("</div>");
            return sb.toString();
        } else if ("日期型".equals(dataType)) {
            //System.out.println(name + "<-----name");
            sb.append("<div name=\""+name+"\" field=\""+name+"\"  allowSort=\"true\" dateFormat=\"yyyy-MM-dd\" headerAlign=\"center\">"+desc+"");
            sb.append("<input property=\"editor\" class=\"mini-datepicker\" style=\"width:100%;\"/>");
            sb.append("</div>");
            return sb.toString();
        } else if ("字符型".equals(dataType)) {
            sb.append("<div name=\""+name+"\"  field=\""+name+"\" " +
                    "headerAlign=\"center\" allowSort=\"true\"  >"+desc+"");
            sb.append("<input property=\"editor\" class=\"mini-textbox\" style=\"width:100%;\" minWidth=\"200\" />");
            sb.append("</div>");
            return sb.toString();
        } else if ("代码型".equals(dataType)) {//valueField='id'  codeitemdesc  System,Video
            sb.append("<div type=\"treeselectcolumn\" field=\""+name+"\" headerAlign=\"center\" >"+desc+"");
            sb.append("<input  property=\"editor\" class='mini-treeselect' " +
                    "url='../table/fieldCode?fieldCode=" + fieldCode + "' " +
                    "valueField='codeitemid' textField='codeitemdesc'" +
                    " text='请选择' />");// multiSelect='true'
            sb.append("</div>");//parentField='parentid'
            return sb.toString();
        } else {
            sb.append("<div name=\""+name+"\" field=\""+name+"\" " +
                    "headerAlign=\"center\" allowSort=\"true\"  >"+desc+"");
            sb.append("<input property=\"editor\" class=\"mini-textbox\" style=\"width:100%;\" minWidth=\"200\" />");
            sb.append("</div>");
            return sb.toString();
        }
    }

    //判断基本指标input的类型      //disabled="disabled"
    private String inputType(String dataType, String name, String fieldCode,Object value,String required,String isEditor) {
        if (value == null) value = "";

        if ("true".equals(isEditor)) {//这里是可编辑的
            if ("数值型".equals(dataType)) {
                if ("true".equals(required)) {
                    return "<input class=\"mini-spinner\" width=\"100%\" required=\"true\"  minValue=\"0\" maxValue=\"250\" name='" + name + "' value='" + value + "'/>";
                } else {
                    return "<input class=\"mini-spinner\" width=\"100%\" required=\"false\"  minValue=\"0\" maxValue=\"250\" name='" + name + "' value='" + value + "'/>";
                }
            } else if ("日期型".equals(dataType)) {
                if ("true".equals(required)) {
                    return "<input  class='mini-datepicker' width=\"100%\"  required=\"true\" name='" + name + "' value=" + value + "/>";
                } else {
                    return "<input  class='mini-datepicker' width=\"100%\"  required=\"false\" name='" + name + "' value=" + value + "/>";
                }
            } else if ("字符型".equals(dataType)) {
                if ("true".equals(required)) {
                    /*if ("C0104".equals(name)) {//移动电话
                        return "<input class=\"mini-textbox\" width=\"100%\" id=\"C0104\" required=\"true\" name='" + name + "' value='" + value + "'/>";
                    } else if ("H01T2".equals(name)) {//办公室电话
                        return "<input class=\"mini-textbox\" width=\"100%\" id=\"H01T2\" required=\"true\" name='" + name + "' value='" + value + "'/>";
                    } else {
                        return "<input class=\"mini-textbox\" width=\"100%\"  required=\"true\" name='" + name + "' value='" + value + "'/>";
                    }*/
                    return "<input class=\"mini-textbox\" width=\"100%\"  required=\"true\" name='" + name + "' value='" + value + "'/>";
                } else {
                    return "<input class=\"mini-textbox\" width=\"100%\"  required=\"false\" name='" + name + "' value='" + value + "'/>";
                }
            } else if ("代码型".equals(dataType)) {//valueField='id'  codeitemdesc  System,Video
                if ("true".equals(required)) {
                    if ("".equals(value)) {
                        return "<input  class='mini-treeselect' required=\"true\" onbeforeshowpopup= \"oncheckbox('"+name+"')\" style=\"width:100%;background:#fff\" " +
                                "url='../table/fieldCode?fieldCode=" + fieldCode + "' onnodeclick=\"clearValue('"+name+"','"+value+"')\" " +
                                "valueField='codeitemid' textField='codeitemdesc' multiSelect='false' parentField='parentid' " +
                                "value='" + value + "' text='请选择' name='" + name + "' id='" + name + "'/>";//codeitemid
                    } else {
                        List<CodeType> codeTypes = tableDao.getCodeType(fieldCode,value+"");
                        //System.out.println(codeTypes.size());
                        if (codeTypes.size() < 1) {
                            return "<input  class='mini-treeselect' required=\"true\" onbeforeshowpopup= \"oncheckbox('" + name + "')\" style=\"width:100%;background:#fff\" " +
                                    "url='../table/fieldCode?fieldCode=" + fieldCode + "' onnodeclick=\"clearValue('" + name + "','" + value + "')\" " +
                                    "valueField='codeitemid' textField='codeitemdesc' multiSelect='false' parentField='parentid' " +
                                    "value='" + value + "' text='请选择' name='" + name + "' id='" + name + "'/>";
                        } else {
                            return "<input  class='mini-treeselect' required=\"true\" parentField='parentid' style=\"width:100%;background:#fff\" " +
                                    "url='../table/fieldCode?fieldCode="+fieldCode+"' onbeforeshowpopup= \"oncheckbox('"+name+"')\"  " +
                                    "valueField='codeitemid' textField='codeitemdesc' onnodeclick=\"clearValue('"+name+"','"+value+"')\"  " +
                                    "value='"+value+"' text='"+codeTypes.get(0).getCodeitemdesc()+"' name='"+name+"'  id='" + name + "'/>";//codeitemid
                        }
                    }
                } else {
                    if ("".equals(value)) {
                        return "<input  class='mini-treeselect' required=\"false\" parentField='parentid'  onnodeclick=\"clearValue('"+name+"','"+value+"')\" " +
                                "url='../table/fieldCode?fieldCode=" + fieldCode + "' onbeforeshowpopup= \"oncheckbox('"+name+"')\"  " +
                                "valueField='codeitemid' textField='codeitemdesc' multiSelect='false' style=\"width:100%;background:#fff\" " +
                                "value='" + value + "' text='请选择' name='" + name + "'  id='" + name + "'/>";//codeitemid
                    } else {
                        List<CodeType> codeTypes = tableDao.getCodeType(fieldCode,value+"");
                        if (codeTypes.size() < 1) {
                            return "<input  class='mini-treeselect' required=\"false\" parentField='parentid'  onnodeclick=\"clearValue('" + name + "','" + value + "')\" " +
                                    "url='../table/fieldCode?fieldCode=" + fieldCode + "' onbeforeshowpopup= \"oncheckbox('" + name + "')\"  " +
                                    "valueField='codeitemid' textField='codeitemdesc' multiSelect='false' style=\"width:100%;background:#fff\" " +
                                    "value='" + value + "' text='请选择' name='" + name + "'  id='" + name + "'/>";
                        } else {
                            return "<input  class='mini-treeselect' required=\"false\" parentField='parentid'  onnodeclick=\"clearValue('"+name+"','"+value+"')\" " +
                                    "url='../table/fieldCode?fieldCode="+fieldCode+"' onbeforeshowpopup= \"oncheckbox('"+name+"')\"  " +
                                    "valueField='codeitemid' textField='codeitemdesc' style=\"width:100%;background:#fff\" " +
                                    "value='"+value+"' text='"+codeTypes.get(0).getCodeitemdesc()+"' name='"+name+"' id='" + name + "'/>";//codeitemid
                        }
                    }
                }
            } else {
                if ("true".equals(required)) {
                    return "<input class=\"mini-textbox\" required=\"true\" width=\"100%\"  name='" + name + "' value='"+value+"'/>";
                } else {
                    return "<input class=\"mini-textbox\" required=\"false\" width=\"100%\"  name='" + name + "' value='"+value+"'/>";
                }
            }
        } else {//这里不是不可以编辑的false
            if ("数值型".equals(dataType)) {
                if ("true".equals(required)) {
                    return "<input class=\"mini-spinner\"  style=\"width:100%;\" required=\"true\"  minValue=\"0\" maxValue=\"250\" name='" + name + "' value='" + value + "' enabled=\"false\" />";
                } else {
                    return "<input class=\"mini-spinner\"  style=\"width:100%;\" required=\"false\"  minValue=\"0\" maxValue=\"250\" name='" + name + "' value='" + value + "' enabled=\"false\" />";
                }
            } else if ("日期型".equals(dataType)) {
                if ("true".equals(required)) {
                    return "<input  class='mini-datepicker'  style=\"width:100%;\" required=\"true\" name='" + name + "' value=" + value + " enabled=\"false\"/>";
                } else {
                    return "<input  class='mini-datepicker' style=\"width:100%;\" required=\"false\" name='" + name + "' value=" + value + " enabled=\"false\"/>";
                }
            } else if ("字符型".equals(dataType)) {
                if ("true".equals(required)) {
                    return "<input class=\"mini-textbox\"  style=\"width:100%;\" required=\"true\" name='" + name + "' value='" + value + "' enabled=\"false\"/>";
                } else {
                    return "<input class=\"mini-textbox\"  style=\"width:100%;\" required=\"false\" name='" + name + "' value='" + value + "' enabled=\"false\"/>";
                }
            } else if ("代码型".equals(dataType)) {//valueField='id'  codeitemdesc  System,Video
                if ("true".equals(required)) {
                    if ("".equals(value)) {
                        return "<input  class='mini-treeselect' required=\"true\" onbeforeshowpopup= \"oncheckbox('"+name+"')\"  style=\"width:100%;\" " +
                                "url='../table/fieldCode?fieldCode=" + fieldCode + "' onnodeclick=\"clearValue('"+name+"','"+value+"')\" " +
                                "valueField='codeitemid' textField='codeitemdesc' multiSelect='true' parentField='parentid' " +
                                "value='" + value + "' text='请选择' name='" + name + "' id='" + name + "' enabled=\"false\"/>";//codeitemid
                    } else {
                        List<CodeType> codeTypes = tableDao.getCodeType(fieldCode,value+"");
                        return "<input  class='mini-treeselect' required=\"true\" parentField='parentid'  style=\"width:100%;\" " +
                                "url='../table/fieldCode?fieldCode="+fieldCode+"' onbeforeshowpopup= \"oncheckbox('"+name+"')\"  " +
                                "valueField='codeitemid' textField='codeitemdesc' onnodeclick=\"clearValue('"+name+"','"+value+"')\"  " +
                                "value='"+value+"' text='"+codeTypes.get(0).getCodeitemdesc()+"' name='"+name+"'  id='" + name + "' enabled=\"false\"/>";//codeitemid
                    }
                } else {
                    if ("".equals(value)) {
                        return "<input  class='mini-treeselect' required=\"false\" parentField='parentid'  onnodeclick=\"clearValue('"+name+"','"+value+"')\" " +
                                "url='../table/fieldCode?fieldCode=" + fieldCode + "' onbeforeshowpopup= \"oncheckbox('"+name+"')\"  " +
                                "valueField='codeitemid' textField='codeitemdesc' multiSelect='false'  style=\"width:100%;\" " +
                                "value='" + value + "' text='请选择' name='" + name + "'  id='" + name + "' enabled=\"false\"/>";//codeitemid
                    } else {
                        List<CodeType> codeTypes = tableDao.getCodeType(fieldCode,value+"");
                        return "<input  class='mini-treeselect' required=\"false\" parentField='parentid'  onnodeclick=\"clearValue('"+name+"','"+value+"')\" " +
                                "url='../table/fieldCode?fieldCode="+fieldCode+"' onbeforeshowpopup= \"oncheckbox('"+name+"')\"  " +
                                "valueField='codeitemid' textField='codeitemdesc'  style=\"width:100%;\" " +
                                "value='"+value+"' text='"+codeTypes.get(0).getCodeitemdesc()+"' name='"+name+"' id='" + name + "' enabled=\"false\"/>";//codeitemid
                    }
                }
            } else {
                if ("true".equals(required)) {
                    return "<input class=\"mini-textbox\"  style=\"width:100%;\" required=\"true\" name='" + name + "' value='"+value+"' enabled=\"false\"/>";
                } else {
                    return "<input class=\"mini-textbox\"  style=\"width:100%;\" required=\"false\" name='" + name + "' value='"+value+"' enabled=\"false\"/>";
                }
            }
        }

    }

    //脚本文件
    String getGridScript(List<TableSet> tableSets, String personFalg, String A0100,String flag) {
        StringBuffer sb = new StringBuffer("");
        sb.append("<script>");
        sb.append("mini.parse();");

        for (int i = 0; i < tableSets.size(); i++) {
            if (tableSets.get(i).getFlag().equals(personFalg) && !tableSets.get(i).getName().equals("A01")) {
                String grid = " var grid"+tableSets.get(i).getName();

                String grid1 = " grid"+tableSets.get(i).getName();
                String tableName = tableSets.get(i).getName();
                sb.append(grid).append(" = mini.get(\"").append(tableName).append("\");").
                        append(grid1).append(".load();");
                sb.append("function addRow"+tableSets.get(i).getName()+"() { " +
                        "var newRow = { name: \"New Row\" }; " +
                        ""+grid1+".addRow(newRow, 0); " +
                        ""+grid1+".beginEditCell(newRow, \"LoginName\");}");

                sb.append("function removeRow"+tableSets.get(i).getName()+"() {" +
                        "var rows = "+grid1+".getSelecteds();" +
                        "if (rows.length > 0) {" +
                        ""+grid1+".removeRows(rows, true);}}");

                sb.append("function saveData"+tableSets.get(i).getName()+"() {");
                if ("02".equals(flag)) {
                    sb.append("var data = "+grid1+".getChanges();" +
                            "var json = mini.encode(data);" +
                            ""+grid1+".loading(\"保存中，请稍后......\");");//TODO ..其他子集保存方法url位置
                    sb.append("$.ajax({ " +
                            "url: \"../table/editData?A0100="+A0100+"&tableName="+tableSets.get(i).getName()+"\"," +
                            "data: { data: json }," +
                            "type: \"post\",");
                    sb.append("success: function (text) {" +
                            " " +
                            ""+grid1+".reload();},");
                    sb.append("error: function (jqXHR, textStatus, errorThrown) {alert(jqXHR.responseText);}});");
                } else if ("01".equals(flag)) {
                    sb.append("var rows = "+grid1+".findRows(function(row){\n" +
                            "            if(row._id != \"\") return true;\n" +
                            "        });\n" +
                            "        var json = mini.encode(rows);");
                    sb.append("$.ajax({ " +
                            "url: \"../table/empSavOtherData?A0100="+A0100+"&tableName=Usr"+tableSets.get(i).getName()+"\"," +
                            "data: { data: json }," +
                            "type: \"post\",");
                    sb.append("success: function (text) {" +
                            " " +
                            ""+grid1+".reload();},");
                    sb.append("error: function (jqXHR, textStatus, errorThrown) {alert(jqXHR.responseText);}});");
                }
                sb.append("}");
                sb.append(""+grid1+".on(\"celleditenter\", function (e) { var index = "+grid1+".indexOf(e.record);" +
                        "if (index == "+grid1+".getData().length - 1) {var row = {};"+grid1+".addRow(row) }});");//"+tableSets.get(i).getName()+"
                sb.append(""+grid1+".on(\"beforeload\", function (e) {if ("+grid1+".getChanges().length > 0) {" +
                        "if (confirm(\"有增删改的数据未保存，是否取消本次操作？\")) {e.cancel = true;}}});");

                sb.append("var oldRows"+tableName+" = "+grid1+".findRows(function(row){\n" +
                        "            if(row._id != '') return true;\n" +
                        "        });\n" +
                        "        var oldJson"+tableName+" = mini.encode(oldRows"+tableName+");");
                //sb.append("alert(oldJson"+tableName+");");
            }
        }
        sb.append("var oldData = new mini.Form('#A01').getData();");
        sb.append("var oldJson = mini.encode(oldData);");
       /* sb.append("function isTelOrMobile(telephone){  \n" +
                "    var teleReg = /^((0\\d{2,3})-)(\\d{7,8})$/;  \n" +
                "    var mobileReg =/^1[358]\\d{9}$/;   \n" +
                "    if (!teleReg.test(telephone) && !mobileReg.test(telephone)){  \n" +
                "        return false;  \n" +
                "    }else{  \n" +
                "        return true;  \n" +
                "    }  \n" +
                "}  ");*/
        sb.append("</script>");
        return sb.toString();
    }

}
