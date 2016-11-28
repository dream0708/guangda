package org.keega.idea.controller;

import org.bouncycastle.math.raw.Mod;
import org.keega.idea.upload.DataHamal;
import org.keega.idea.upload.RequestUitl;
import org.keega.idea.model.TableField;
import org.keega.idea.service.ITableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zun.wei on 2016/10/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/table")
public class TableController {

    @Inject
    private ITableService tableService;

    //初始化进入一个空的页面,用来请求//flag:01表示员工02表示人事;userType：01表示员工登录，02表示人事审核
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initData(Model model,
                           @RequestParam(name = "flag", defaultValue = "01") String flag,
                           @RequestParam(name = "A0100", defaultValue = "00000022") String A0100,
                           @RequestParam(name = "userType", defaultValue = "01") String userType,
                           HttpServletRequest request) {//TODO 赋予测试初识值
        String initData = this.tableService.initData(flag);
        DataHamal.setRealPath(request.getSession().getServletContext().getRealPath("/upload/photo/"));
        //RequestUitl.setUploadPath(request.getSession().getServletContext().getRealPath("/upload/photo/"));
        model.addAttribute("flag", flag);
        model.addAttribute("initData", initData);
        model.addAttribute("A0100", A0100);
        model.addAttribute("userType", userType);
        return "/table/view";
    }

    //员工信息初始化界面
    @ResponseBody
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String employeePage(String personFalg,
                               @RequestParam(name = "personId") String personId,
                               @RequestParam(name = "flag") String flag,
                               @RequestParam(name = "userType") String userType,
                               HttpServletRequest request) {
        DataHamal.setRealPath(request.getSession().getServletContext().getRealPath("/upload/photo/"));
        //RequestUitl.setUploadPath(request.getSession().getServletContext().getRealPath("/upload/photo/"));
        String json = tableService.getPersionData(personFalg, personId,flag,userType);
        json = json.replaceAll("★","<span style='color:red'>*</span>");
        //System.out.println(json);
        return json;
    }

    //long startTime = System.currentTimeMillis();//获取当前时间
    //doSomeThing();   //要运行的java程序
    //long endTime = System.currentTimeMillis();
    //System.out.println("程序运行时间："+(endTime-startTime)+"ms");


    //其他代码项的数据获取
    @ResponseBody
    @RequestMapping(value = "/fieldCode", method = RequestMethod.POST)
    public String fieldCodeData(String fieldCode) {
        //System.out.println(tableService.codeTypeString(fieldCode));
        return tableService.codeTypeString(fieldCode,null);
    }

    //单位、部门、岗位数据的获取
    @ResponseBody
    @RequestMapping(value = "/organization", method = RequestMethod.POST)
    public String fieldCodeDataOrganization(String fieldCode,String parentid) {
        //System.out.println("abc");
        return tableService.codeTypeString(fieldCode,parentid);
    }

    //初始化其他子集的的数据
    @ResponseBody
    @RequestMapping(value = "/gridData", method = RequestMethod.POST)
    public String gridData(@RequestParam(name = "tableName") String tableName,
                           @RequestParam(name = "personFlag") String personFlag,
                           @RequestParam(name = "A0100") String A0100,
                           @RequestParam(name = "flag") String flag) {
        List<TableField> tableFields = tableService.getListTableFields(tableName, personFlag);
        //List<Map<String, Object>> mapList = tableService.getNotBaiscItem(tableFields, "Usr" + tableName, A0100);
        //System.out.println(JSON.toJSONStringWithDateFormat(mapList,"yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat));
        //System.out.println(JsonUtil.object2json(mapList));
        return tableService.getNotBaiscItem(tableFields, "Usr" + tableName, A0100,flag);
    }

    //其他子集的数据维护
    @ResponseBody
    @RequestMapping(value = "/editData", method = RequestMethod.POST)
    public String editNotBasicData(@RequestParam(name = "data") String data,
                                   @RequestParam(name = "A0100") String A0100,
                                   @RequestParam(name = "tableName")String tableName) {

        tableService.editNotBaiscData(data, A0100, tableName);
        return null;
    }

    //基本指标的数据维护
    @ResponseBody
    @RequestMapping(value = "/basicData", method = RequestMethod.POST)
    public String editBasicData(@RequestParam(name = "submitData") String submitData,
                                @RequestParam(name = "A0100")String A0100,
                                @RequestParam(name = "tableName") String tableName) {
        tableService.editBaiscData(submitData, A0100,tableName);
        return null;
    }

    //员工信息修改之后保存
    @ResponseBody
    @RequestMapping(value = "/readyApproval", method = RequestMethod.POST)
    public String empSaveBaiscData(@RequestParam(name = "submitData") String submitData,
                                   @RequestParam(name = "A0100")String A0100,
                                   @RequestParam(name = "tableName") String tableName) {
        tableService.saveBaisReadyApprovalData(submitData, A0100, tableName,"0");
        return null;
    }

    //员工信息修改之后报批
    @ResponseBody
    @RequestMapping(value = "/toApproval", method = RequestMethod.POST)
    public String empApprovalData(@RequestParam(name = "submitData") String submitData,
                                  @RequestParam(name = "A0100")String A0100,
                                  @RequestParam(name = "tableName") String tableName) {
        tableService.saveBaisReadyApprovalData(submitData, A0100, tableName,"1");
        tableService.saveOtherInfoReadyApprovalData(A0100, tableName, "1");
        return null;
    }

    //审核员工修改的信息
    @ResponseBody
    @RequestMapping(value = "/approval",method = RequestMethod.POST)
    public String Approval(@RequestParam(name = "isPass") String isPass,
                           @RequestParam(name = "A0100")String A0100,
                           @RequestParam(name = "tableName") String tableName,
                           @RequestParam(name = "submitData") String submitData) {

        tableService.approvalBaiscInfo(isPass, A0100, tableName,submitData);
        return null;
    }

    //其他子集信息的保存
    @ResponseBody
    @RequestMapping(value = "/empSavOtherData", method = RequestMethod.POST)
    public String empSavOtherData(@RequestParam(name = "data") String data,
                                  @RequestParam(name = "A0100") String A0100,
                                  @RequestParam(name = "tableName")String tableName) {
        tableService.saveOtherInfoData(data, tableName, A0100);
        return null;
    }

    @RequestMapping(value = "/maintenance", method = RequestMethod.GET)
    public String go2InfoMaintenance(String orgId, Model model) {
        model.addAttribute("orgId", orgId);
        return "/table/maintenance";
    }

    @ResponseBody
    @RequestMapping(value = "/getInfoApproval", method = RequestMethod.POST)
    public String getInfoApproval(@RequestParam(name = "key", defaultValue = "") String key,String orgId) {
        return tableService.getInfo4Approval(key,orgId);
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadPhoto(@RequestParam(value = "Fdata") MultipartFile Fdata,String A0100,
                              HttpServletRequest request){//inputFile
        return tableService.uploadPhoto(Fdata,A0100,request);
    }

/*   @RequestMapping(value = "/testUpload", method = RequestMethod.GET)
    public String testUpload(Model model) {
       model.addAttribute("img", "/upload/photo/00000120.jpg");
        return "/table/testupload";
    }*/

    @ResponseBody
    @RequestMapping(value = "/deletePhoto",method = RequestMethod.POST)
    public String deletePhoto(String A0100,HttpServletRequest request) {
        tableService.deletePhoto(request,A0100);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/checkPhoto",method = RequestMethod.POST)
    public String checkHasPhoto(String A0100) {
        if (tableService.checkHasPhoto(A0100)) {
            return "has";
        } else {
            return "notHas";
        }
    }

    @RequestMapping(value = "/getPhoto",method = RequestMethod.GET)
    public void getPhoto(String A0100,HttpServletRequest request, HttpServletResponse response, Model model) {
        tableService.lookImage(A0100,request,response,model);
    }

}

