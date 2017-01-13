package org.keega.idea.service;

import com.alibaba.fastjson.JSON;
import com.dexcoder.commons.utils.JsonUtil;
import org.keega.idea.dao.ITableDao;
import org.keega.idea.upload.ImageDemo;
import org.keega.idea.model.CodeType;
import org.keega.idea.model.TableField;
import org.keega.idea.model.TableSet;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service("tableService")
public class TableService implements ITableService {

    @Inject
    private ITableDao tableDao;
    @Inject
    private TableServiceData tableServiceData;
    @Inject
    private ImageDemo imageDemo;


    @Override
    public String initData(String flag) {
        return tableServiceData.getInitData(flag);
    }

    @Override
    public String getPersionData(String personFalg,String A0100,String flag,String userType) {
        List<TableSet> tableSets = tableDao.getListTableSets();
        StringBuffer sb = new StringBuffer();
        tableServiceData.getTableData(tableSets, personFalg, sb, A0100, flag,userType);
        sb.append(tableServiceData.getGridScript(tableSets,personFalg,A0100,flag));
        return sb.toString();
    }

    @Override
    public String codeTypeString(String codesetid,String parentid) {
        List<CodeType> codeTypes;
        if (parentid == null) {
            codeTypes = tableDao.getCodeType(codesetid);
        } else {
            codeTypes = tableDao.getOrganizationCodeType(codesetid, parentid);
        }
        return JSON.toJSONString(codeTypes);
    }

    @Override
    public List<TableField> getListTableFields(String tableSetName, String persontFlag) {
        return this.tableDao.getListTableFields(tableSetName, persontFlag);
    }

    @Override
    public String getNotBaiscItem(List<TableField> tableFields, String tableName, String A0100,String flag) {
        if ("02".equals(flag)) {
            List<Map<String, Object>> mapList = this.tableDao.getNotBaiscItem(tableFields, tableName, A0100);
            return  JsonUtil.object2json(mapList);
        } else if ("01".equals(flag)) {
            Map<String, Object> status = this.tableDao.checkModifyOtherInfoStatus(tableName, A0100);
            if (status == null || status.get("modifystatus") == null) {//表示从来没有修改过，没有记录
                List<Map<String, Object>> mapList = this.tableDao.getNotBaiscItem(tableFields, tableName, A0100);
                return JsonUtil.object2json(mapList);
            } else if ("1".equals(status.get("modifystatus")) || "0".equals(status.get("modifystatus"))) {
                //表示该员工修改过数据，并且已经提交但是为审批 || 该员工仅仅是保存而已并没有提交审批
                Map<String, Object> map = this.tableDao.getModifyOtherInfoData(tableName, A0100);
                String modifycontent = (String) map.get("modifycontent");
                return modifycontent;
            } else if ("2".equals(status.get("modifystatus"))) {//表示修改过的数据已通过审核
                List<Map<String, Object>> mapList = this.tableDao.getNotBaiscItem(tableFields, tableName, A0100);
                return JsonUtil.object2json(mapList);
            } else if ("3".equals(status.get("modifystatus"))) {//审核未通过
                Map<String, Object> map = this.tableDao.getModifyOtherInfoData(tableName, A0100);
                String modifycontent = (String) map.get("modifycontent");
                return modifycontent;
                /*List<Map<String, Object>> mapList = this.tableDao.getNotBaiscItem(tableFields, tableName, A0100);
                return JsonUtil.object2json(mapList);*/
            } else {//其他情况显示原来的
                List<Map<String, Object>> mapList = this.tableDao.getNotBaiscItem(tableFields, tableName, A0100);
                return JsonUtil.object2json(mapList);
            }
        } else {
            List<Map<String, Object>> mapList = this.tableDao.getNotBaiscItem(tableFields, tableName, A0100);
            return  JsonUtil.object2json(mapList);
        }
    }

    @Override
    public void editNotBaiscData( String data, String A0100,String tableName) {
        List<Map> maps = JSON.parseArray(data, Map.class);
        String state = null;//modified    added   removed
        String I9999 = null;
        for (int i = 0; i < maps.size(); i++) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sb1 = new StringBuffer();
            Set<String> stringSet = maps.get(i).keySet();
            Iterator<String> it = stringSet.iterator();
            while (it.hasNext()) {
                String key = it.next();
                state = maps.get(i).get("_state")+"";
                if (key.equals("I9999")) {
                    I9999 = maps.get(i).get("I9999")+"";
                } else if (!key.startsWith("_") && !key.equals("I9999")
                        && maps.get(i).get(key) != null && !key.equals("name")) {// && !"".equals(maps.get(i).get(key))
                    if (state.equals("modified")) {
                        sb.append(key).append("=").append("'").append(maps.get(i).get(key)).append("',");
                    } else if (state.equals("added")) {
                        sb.append(key).append(",");
                        sb1.append("'").append(maps.get(i).get(key)).append("'").append(",");
                    }
                }
            }
            String sql = "";
            if (state.equals("modified")) {
                sql = "update Usr" + tableName + " set "
                        + sb.toString().substring(0, sb.toString().length() - 1)+" " +
                        "where A0100 = ? and I9999 = ?";
                tableDao.updateRowByOriginalSql(sql, A0100, I9999);
            } else if (state.equals("added")) {
                if ("".equals(I9999) || I9999 == null) {
                    I9999 = tableDao.queryMaxI9999("Usr" + tableName, A0100);
                    if (I9999 == null || I9999.equals("null")) {
                        I9999 = 1 + "";
                    } else {
                        I9999 = Integer.parseInt(I9999) + 1 + "";
                    }
                } else {
                    I9999 = Integer.parseInt(I9999)+1+"";
                }
                sql = "insert into Usr" + tableName + " " +
                        "(" + sb.toString() + "I9999,A0100) values " +
                        "(" + sb1.toString() +"'"+I9999+"','"+A0100+"')";
                tableDao.insertRowByOriginalSql(sql);
            } else if (state.equals("removed")) {
                sql = "delete from Usr"+tableName+" where A0100 = ? and I9999 = ?";
                tableDao.deleteRowByOriginalSql(sql,A0100,I9999);
            } else {
                System.out.println("my sql error!");
            }
        }

    }

    @Override
    public void editBaiscData(String submitData, String A0100,String tableName) {
        Map map = JSON.parseObject(submitData, Map.class);
        Set<String> stringSet = map.keySet();
        Iterator<String> it = stringSet.iterator();
        StringBuffer sb = new StringBuffer();
        String sql = "";
        while (it.hasNext()) {
            String key = it.next();//pagesize
            if (!"pagesize".equals(key)) {
                sb.append(key).append("=").append("'").append(map.get(key)).append("',");
            }
        }
        sql = "update Usr" + tableName + " set "
                + sb.toString().substring(0, sb.toString().length() - 1)+" " +
                "where A0100 = ? ";
        tableDao.updateRowByOriginalSql(sql, A0100);
    }

    @Override
    public void saveBaisReadyApprovalData(String submitData, String A0100, String tableName,String status) {
        String sql = "";
        Map<String,Object> count = tableDao.checkModifyCount(A0100);
        Integer in = (Integer) count.get("count");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (in == 0) {//insert
            sql = "insert into basicinfomodify (modifydate,modifycontent,modifyperson,modifystatus) values (?,?,?,?)";
            tableDao.insertRowByOriginalSql(sql, sdf.format(new Date()), submitData, A0100, status);
        } else {//update
            sql = "update basicinfomodify set modifydate = ? , modifycontent = ? , modifyperson = ? , modifystatus = ? where modifyperson = ? ";
            tableDao.updateRowByOriginalSql(sql,sdf.format(new Date()),submitData,A0100,status,A0100);
        }

    }

    @Override
    public void approvalBaiscInfo(String isPass, String A0100, String tableName,String submitData) {
        List<TableField> tableFieldsFlag03 = tableDao.getA01ListTableFieldByFlagEquals03();//获取A01中所有配置了flag=01,field为03的所有字段
        List<String> excludeFalg03 = new ArrayList<String>();
        if (tableFieldsFlag03 != null) {
            for (int i = 0; i < tableFieldsFlag03.size(); i++) {
                excludeFalg03.add(tableFieldsFlag03.get(i).getName());
            }
        }
        if ("yes".equals(isPass)) {//通过状态为2
            Map map = JSON.parseObject(submitData, Map.class);
            Set<String> stringSet = map.keySet();
            Iterator<String> it = stringSet.iterator();
            StringBuffer sb = new StringBuffer();
            String sql = "";
            while (it.hasNext()) {
                String key = it.next();//pagesize
                if (!"pagesize".equals(key) && !excludeFalg03.contains(key)) {
                    sb.append(key).append("=").append("'").append(map.get(key)).append("',");
                }
            }
            sql = "update Usr" + tableName + " set "
                    + sb.toString().substring(0, sb.toString().length() - 1)+" " +
                    "where A0100 = ? ";
            tableDao.updateRowByOriginalSql("update UsrA01 set sp_flag=? where A0100=?", 0, A0100);//这个是集成到oa上的状态，通过审批为0
            tableDao.updateRowByOriginalSql(sql, A0100);
            tableDao.updateRowByOriginalSql("update basicinfomodify set modifystatus = ? where modifyperson = ? ", "2", A0100);
        } else if ("no".equals(isPass)) {//不通过状态为3
            tableDao.updateRowByOriginalSql("update UsrA01 set sp_flag=? where A0100=?", 1, A0100);//这个是集成到oa上的状态，不通过审批为1
            String sql = "update basicinfomodify set modifystatus = ? where modifyperson = ? ";
            tableDao.updateRowByOriginalSql(sql, "3", A0100);
        }
        //其他子集信息的
        if ("yes".equals(isPass)) {//通过状态为2
            List<TableSet> talbeSets = tableDao.getListTableSets();
            for (TableSet tableSet : talbeSets) {
                if ("01".equals(tableSet.getFlag()) && !"A01".equals(tableSet.getName())) {
                    tableName = "Usr"+tableSet.getName();
                    Map<String, Object> map = tableDao.checkModifyOtherInfoCount(tableName, A0100);
                    Map<String, Object> statuss = this.tableDao.checkModifyOtherInfoStatus(tableName, A0100);
                    Integer in = (Integer) map.get("count");
                    if (in != 0 && statuss != null && "1".equals(statuss.get("modifystatus"))) {
                        Map<String, Object> modifyContentMap = tableDao.getModifyOtherInfoData(tableName, A0100);
                        String modifyContent = (String) modifyContentMap.get("modifycontent");
                        List<Map> maps = JSON.parseArray(modifyContent, Map.class);

                        List<String> list = null;
                        for (int i = 0; i < maps.size(); i++) {
                            list = new ArrayList<String>();
                            if (maps.get(i).get("I9999") != null) {
                                list.add((String) maps.get(i).get("I9999"));
                            }
                        }
                        List<Map<String,Object>> mapList = tableDao.getAllI9999(tableName, A0100);
                        if (mapList != null && list != null) {
                            for (int j = 0; j < mapList.size(); j++) {
                                String field = mapList.get(j).get("I9999")+"";
                                if (field != null && !list.contains(field)) {
                                    tableDao.updateRowByOriginalSql("delete from "+tableName+" where A0100 = ? and I9999 = ?",
                                            A0100,mapList.get(j).get("I9999"));//将原来的删除！
                                }
                            }
                        }

                        for (int i = 0; i < maps.size(); i++) {
                            StringBuffer sb = new StringBuffer();
                            StringBuffer sb1 = new StringBuffer();
                            Object i9999Object = maps.get(i).get("I9999");
                            String i9999 = null;
                            if (i9999Object != null) {
                                i9999 = (String) i9999Object;
                            }
                            if (i9999 == null) {//没有I9999的时候表示为插入
                                Set<String> stringSet = maps.get(i).keySet();
                                Iterator<String> it = stringSet.iterator();
                                String I99 = tableDao.queryMaxI9999(tableName, A0100);
                                if ("null".equals(I99) || "".equals(I99) || I99 == null) {
                                    I99 = "1";
                                } else {
                                    I99 = Integer.parseInt(I99) + 1 + "";
                                }
                                while (it.hasNext()) {
                                    String key = it.next();
                                    sb.append(key).append(",");
                                    sb1.append("'").append(maps.get(i).get(key)).append("',");
                                }
                                String sql = "insert into "+tableName+"(" + sb.toString() + "I9999,A0100) " +
                                        "values (" + sb1.toString() + "'" + I99 + "','"+A0100+"')";
                                tableDao.insertRowByOriginalSql(sql);
                            } else {
                                Set<String> stringSet = maps.get(i).keySet();
                                Iterator<String> it = stringSet.iterator();
                                while (it.hasNext()) {
                                    String key = it.next();
                                    sb.append(key).append("=").append("'").append(maps.get(i).get(key)).append("',");
                                }
                                String sql = "update "+tableName+" set " + sb.toString().substring(0, sb.toString().length() - 1)+" " +
                                        "where A0100 = ? and I9999 = ?";// and I9999 = ?
                                tableDao.updateRowByOriginalSql(sql,A0100,i9999);//,i9999
                            }
                            String sql = "update otherinfomodify set modifystatus = ? " +
                                    "where modifyperson = ? and modifytablename = ? ";
                            tableDao.updateRowByOriginalSql(sql,"2",A0100,tableName);
                        }

                        if (maps.size() == 0) {
                            tableDao.updateRowByOriginalSql("delete from "+tableName+" where A0100 = ? ",A0100);//将原来的删除！
                            String sql = "update otherinfomodify set modifystatus = ? " +
                                    "where modifyperson = ? and modifytablename = ? ";
                            tableDao.updateRowByOriginalSql(sql,"2",A0100,tableName);
                        }
                    }
                }
            }
        } else if ("no".equals(isPass)) {//不通过状态为3
            List<TableSet> talbeSets = tableDao.getListTableSets();

            for (TableSet tableSet : talbeSets) {
                if ("01".equals(tableSet.getFlag()) && !"A01".equals(tableSet.getName())) {
                    tableName = "Usr"+tableSet.getName();
                    Map<String, Object> statuss = this.tableDao.checkModifyOtherInfoStatus(tableName, A0100);
                    if (statuss != null && "1".equals(statuss.get("modifystatus"))) {
                        String sql = "update otherinfomodify set modifystatus = ? " +
                                "where modifyperson = ? and modifytablename = ? ";
                        tableDao.updateRowByOriginalSql(sql,"3",A0100,tableName);
                    }
                }
            }
        }
    }

    @Override
    public void saveOtherInfoData(String data, String tableName, String A0100) {
        List<Map> maps = JSON.parseArray(data, Map.class);
        for (int i = 0; i < maps.size(); i++) {
            Set<String> stringSet = maps.get(i).keySet();
            Iterator<String> it = stringSet.iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (key.startsWith("_") || "name".equals(key)) {
                    it.remove();//移除键
                    maps.get(i).remove(key);//移除值
                }
            }
        }
        if (!"[{}]".equals(JSON.toJSONString(maps))) {
            Map<String,Object> countMap = tableDao.checkModifyOtherInfoCount(tableName, A0100);
            Integer in = (Integer) countMap.get("count");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "";
            if (in == 0) {
                sql = "insert into otherinfomodify (modifydate,modifycontent,modifyperson,modifytablename,modifystatus) values (?,?,?,?,?)";
                tableDao.insertRowByOriginalSql(sql, sdf.format(new Date()), JSON.toJSONString(maps), A0100, tableName,"0");
            } else {
                sql = "update otherinfomodify set modifydate=?,modifycontent=?,modifystatus=? where modifyperson=? and modifytablename=?";
                tableDao.updateRowByOriginalSql(sql, sdf.format(new Date()), JSON.toJSONString(maps),"0", A0100, tableName);
            }
        }
    }

    @Override
    public void saveOtherInfoReadyApprovalData(String A0100, String tableName, String status) {
        List<TableSet> talbeSets = tableDao.getListTableSets();
        for (TableSet tableSet : talbeSets) {
            if ("01".equals(tableSet.getFlag()) && !"A01".equals(tableSet.getName())) {
                tableName = "Usr"+tableSet.getName();
                Map<String, Object> map = tableDao.checkModifyOtherInfoCount(tableName, A0100);
                Map<String, Object> statuss = this.tableDao.checkModifyOtherInfoStatus(tableName, A0100);
                Integer in = (Integer) map.get("count");
                if (in != 0 && statuss != null && !"1".equals(statuss.get("modifystatus"))) {
                    String sql = "update otherinfomodify set modifystatus = ? " +
                            "where modifyperson = ? and modifytablename = ? ";
                    tableDao.updateRowByOriginalSql(sql,status,A0100,tableName);
                }
            }
        }

    }

    @Override
    public String getInfo4Approval(String key,String orgId) {
        if (orgId == null || "null".equals(orgId)) orgId = "";
        String json = "";
        String sql = "";
        if ("".equals(key)) {
           sql = "select * from (" +
                   "select modifyperson as id," +
                    "(select a0101 from UsrA01 where A0100 = modifyperson) as name," +
                   "(select B0110 from UsrA01 where A0100 = modifyperson) orgid, " +
                    "(select codeitemdesc from organization where codeitemid = ((select B0110 from UsrA01 where A0100 = modifyperson))) as company," +
                    "(select codeitemdesc from organization where codeitemid = ((select E0122 from UsrA01 where A0100 = modifyperson))) as department," +
                    "(select codeitemdesc from organization where codeitemid = ((select E01A1 from UsrA01 where A0100 = modifyperson))) as station " +
                    " from basicinfomodify " +
                    " where modifystatus = '1' group by modifyperson) a where a.orgid like '"+orgId+"%'";// otherinfomodify
            //System.out.println(sql);
            List<Map<String, Object>> mapList = tableDao.queryAllInfo4Approval(sql);
            json = JSON.toJSONString(mapList);
        } else {
            sql = "select * from (" +
                    "select modifyperson as id," +
                    "(select a0101 from UsrA01 where A0100 = modifyperson ) as name," +
                    "(select B0110 from UsrA01 where A0100 = modifyperson) orgid," +
                    "(select codeitemdesc from organization where codeitemid = ((select B0110 from UsrA01 where A0100 = modifyperson))) as company," +
                    "(select codeitemdesc from organization where codeitemid = ((select E0122 from UsrA01 where A0100 = modifyperson))) as department," +
                    "(select codeitemdesc from organization where codeitemid = ((select E01A1 from UsrA01 where A0100 = modifyperson))) as station " +
                    " from basicinfomodify " +
                    " where modifystatus = '1' group by modifyperson" +
                    ") a where (a.name like '%"+key+"%' " +
                    "or a.company like '%"+key+"%' " +
                    "or a.department like '%"+key+"%' " +
                    "or a.station like '%"+key+"%') and a.orgid like '"+orgId+"%'";
            List<Map<String, Object>> mapList = tableDao.queryAllInfo4Approval(sql);
            json = JSON.toJSONString(mapList);
        }
        //System.out.println(json);
        return json;
    }

    @Override
    public void deletePhoto(HttpServletRequest request,String A0100) {
       /* File filePath = new File(request.getSession().getServletContext().getRealPath("/upload/photo/"));
        File files[] = filePath.listFiles(); // 声明目录下所有的文件 files[];
        for(int i = 0; i < files.length; i++){ // 遍历目录下所有的文件
            if (files[i].getPath().startsWith(request.getSession().getServletContext().getRealPath("/upload/photo/")+ "\\" +A0100)) {
                files[i].delete();
            }
        }*/ //已经改用定时器定时删除。
    }

    @Override
    public String uploadPhoto(MultipartFile Fdata, String A0100, HttpServletRequest request){
        try {
            String realpath = request.getSession().getServletContext().getRealPath("/upload/photo");
            if (!Fdata.isEmpty()) {
                String fileName = Fdata.getOriginalFilename();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG")
                        || fileName.endsWith(".png") || fileName.endsWith(".PNG")
                        || fileName.endsWith(".BMP")|| fileName.endsWith(".bmp")
                        || fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG")) {
                    File f = new File(realpath+"/"+A0100+".jpg");
                    Fdata.transferTo(f);
                    imageDemo.readImage2DB(A0100,realpath+"/"+A0100+".jpg");
                    f.delete();//上传完之后，删除掉。
                    //FileUtils.copyInputStreamToFile(Fdata.getInputStream(), f);
                    return "success";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    @Override
    public boolean checkHasPhoto(String A0100) {
        return imageDemo.hasPhoto(A0100);
    }

    @Override
    public void lookImage(String A0100, HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setContentType("img/jpeg");
        response.setCharacterEncoding("utf-8");
        try {
            OutputStream outputStream = response.getOutputStream();
            InputStream in = imageDemo.getImageFromDb(A0100,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
