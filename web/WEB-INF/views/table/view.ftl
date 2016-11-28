<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>员工信息</title>
    <link href="<@s.url '/css/table.css'/>" rel="stylesheet"/>
    <link href="<@s.url '/easyui/themes/default/easyui.css'/>" rel="stylesheet"/>
    <link href="<@s.url '/easyui/themes/icon.css'/>" rel="stylesheet"/>

</head>
<body>

${initData}
<script src="<@s.url '/js/boot.js'/>" type="text/javascript"></script>
<script src="<@s.url '/easyui/jquery.easyui.min.js'/>" type="text/javascript"></script>

<div id="contextdiv">

</div>
<#--<button type="button" onclick="testclick()">按下</button>-->
<script>

   /* function testclick() {
        var row = gridA04.getRow(0);
        alert(mini.encode(row));

        var rows = gridA37.findRows(function(row){
            if(row._id != "") return true;
        });
        var json = mini.encode(rows);
        alert(mini.encode(rows))
    }*/

/*    var oleData = mini.get("form").getData();
    mini.get("form").setData("从数据里面查训出来的");
    function save(){
        var newData = mini.get("form").getData();
        $.post("../table/employee", {
            oleData: oleData,
            newData : newData,
            a0100:a0100
        }

    }*/


    var A0100 = "${A0100}";
    var flag = "${flag}";
    var userType = "${userType}";
    //var data1;
    //var data2;

    $(document).ready(function () {//初始化界面

        var messageId = mini.loading("数据加载中...","页面初始化");
        if ("02" == flag){
            $.ajax({
                url: "../table/employee",
                type: "post",
                data: {
                    personFalg: "02",
                    personId : A0100,
                    flag:flag,
                    userType:"02"
                },
                success: function (data) {
                    $("#contextdiv").html(data);
                    //data2 = data;
                    //mini.parse();
                    /*$.ajax({
                        url: "../table/deletePhoto",
                        type: "post",
                        data: {
                            A0100 : A0100
                        },
                        success: function (data) {

                        }
                    });*/
                    mini.hideMessageBox(messageId);
                }
            });
        }else{
            $.post("../table/employee",
                    {
                        personFalg: "01",
                        personId : A0100,
                        flag:flag,
                        userType:userType
                    },
                    function (data, status) {
                        $("#contextdiv").html(data);
                        //data1 = data;
                        //mini.parse();
                        var yixiugai = $("#yitijiao").html();
                        if (yixiugai != null || userType == "02") {
                            var form = new mini.Form('#A01');
                            form.setEnabled(false);
                        }
                        /*$.ajax({
                            url: "../table/deletePhoto",
                            type: "post",
                            data: {
                                A0100 : A0100
                            },
                            success: function (data) {

                            }
                        });*/
                        mini.hideMessageBox(messageId);
                    });
        }

    });



    function bt1Click1() {//切换员工界面按钮
        //$("#contextdiv").html(data1);
        var messageId = mini.loading("数据加载中...","页面初始化");
        $.post("../table/employee",
                {
                    personFalg: "01",
                    personId : A0100,
                    flag:flag,
                    userType:userType
                },
                function (data, status) {
                    $("#contextdiv").html(data);
                    //console.log(data);
                    //data1 = data;
                    mini.hideMessageBox(messageId);
                });
    }

    function bt1Click2() {//切换人事界面按钮
        var messageId = mini.loading("数据加载中...","页面初始化");
        $.post("../table/employee",
                {
                    personFalg: "02",
                    personId : A0100,
                    flag:flag,
                    userType:"02"
                },
                function (data, status) {
                    $("#contextdiv").html(data);
                    //console.log(data);
                    //data1 = data;
                    mini.hideMessageBox(messageId);
                });

       /* if (data2 == undefined) {
            $.ajax({
                url: "../table/employee",
                type: "post",
                data: {
                    personFalg: "02",
                    personId : A0100,
                    flag:flag,
                    userType:"02"
                },
                success: function (data) {
                    $("#contextdiv").html(data);
                    //data2 = data;
                    mini.hideMessageBox(messageId);
                }
            });
        }*/
        //$("#contextdiv").html(data2);
    }

        function bt1Click4() {//返回按钮
            mini.alert("返回！");
            //window.history.back(-1);
        }

    function bt1Click3(tableName) {//报批按钮  //Approval
        if (!confirm("报批后将不能修改！！！\n请确定修改的信息无误后点击确定！")) {
            return;
        }
        var form = new mini.Form('#'+tableName);
        form.validate();//验证表单
        if (form.isValid() == false){
            mini.alert("信息填写不完整，请补充！");
            return;
        }

        $.ajax({//查看是否有照片
            url: "../table/checkPhoto",
            type: "post",
            data: {
                A0100:A0100
            },
            success: function (text) {
                if (text == "has"){
                    var data = form.getData();
                    var json = mini.encode(data);
                    $.ajax({//报批
                        url: "../table/toApproval",
                        type: "post",
                        data: {
                            submitData: json,//报批的时候把新的json报批给上级
                            A0100:A0100,
                            tableName:tableName
                        },
                        success: function (text) {
                            //mini.alert("报批成功!");
                            window.location.reload();

                        }
                    });
                }else {
                    mini.alert("不存在个人照片，请上传后再提交！");
                }
            }
        });

    }

    function empsav(tableName,show) {//保存基本信息的表单 把status更新为0
        var form = new mini.Form('#'+tableName);
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON
        //alert(oldJson);
        if (json == oldJson) {
            //alert("信息并未修改，无须保存！");
            return ;
        }
        $.ajax({//保存的时候把旧的json和和新的json一起保存
            url: "../table/readyApproval",
            type: "post",
            data: {
                submitData: json,
                A0100:A0100,
                tableName:tableName
            },
            success: function (text) {
                if ("no" != show) {
                    mini.alert("保存成功！");
                }
            }
        });
    }

    function btAuditing(tableName) {//不通过审核
        if (!confirm("确定驳回？")) {
            return;
        }
        var form = new mini.Form('#'+tableName);
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON

        $.ajax({//审核
            url: "../table/approval",
            type: "post",
            data: {
                isPass: "no",//默认yes，表示通过，no表示不通过
                A0100:A0100,
                tableName:tableName,
                submitData: json
            },
            success: function (text) {
                //mini.alert("审核完成!");
                //window.location.reload();
                CloseWindow();
            }
        });
    }

       function CloseWindow() {
           if (window.CloseOwnerWindow) return window.CloseOwnerWindow("close");
           else window.close();
       }

    function btAuditingPass(tableName) {//通过审核
        if (!confirm("确定通过审核？")) {
            return;
        }
        var form = new mini.Form('#'+tableName);
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON

        $.ajax({//审核
            url: "../table/approval",
            type: "post",
            data: {
                isPass: "yes",//默认yes，表示通过，no表示不通过
                A0100:A0100,
                tableName:tableName,
                submitData: json
            },
            success: function (text) {
                //mini.alert("审核完成!");
                //window.location.reload();
                CloseWindow();
            }
        });
    }

    function hrsav(tableName,show) {//保存基本信息的表单
        var form = new mini.Form('#'+tableName);
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON

        $.ajax({
            url: "../table/basicData",
            type: "post",
            data: {
                submitData: json,
                A0100:A0100,
                tableName:tableName
            },
            success: function (text) {
                if ("no" != show) {
                    mini.alert("保存成功！");
                }
            }
        });
    }


    function oncheckbox(name) {
        if(name == "E0122"){//部门
            var pid = mini.get("B0110").getValue();//单位
            if (pid == "") {
                alert("请先选择单位名称！");
                return;
            }
            var url = "../table/organization?fieldCode=UM&parentid="+pid;
            mini.get("E0122").setUrl(url);
        }
        if(name == "E01A1"){//岗位
            var pid = mini.get("E0122").getValue();//部门
            if (pid == "") {
                alert("请先选择部门名称！");
                return;
            }
            var url = "../table/organization?fieldCode=@K&parentid="+pid;
            mini.get("E01A1").setUrl(url);
        }
    }


    var v = "";
    var v2 = "";
    function clearValue(name,value) {//用于清楚单位或部门变化之后清空原来对应的数据。
        if (name == "B0110") {
            var b0110 = mini.get(name).getValue();
            if(v == "" && b0110 != value){
                mini.get("E0122").setValue("");
                mini.get("E01A1").setValue("");
                v = b0110;
            }else if(v !=""  && v != b0110){
                mini.get("E0122").setValue("");
                mini.get("E01A1").setValue("");
                v = b0110;
            }

        }

        if (name == "E0122") {
            var e0122 = mini.get(name).getValue();
            if(v2 == "" && e0122 != value){
                mini.get("E01A1").setValue("");
                v2 = e0122;
            }else if(v2 !=""  && v2 != e0122){
                mini.get("E01A1").setValue("");
                v2 = e0122;
            }

        }
    }

   function ajaxFileUpload() {

       var inputFile = $("#file1 > input:file")[0];
       //alert(inputFile);
       $.ajaxFileUpload({
           url: '../table/upload',                 //用于文件上传的服务器端请求地址
           fileElementId: inputFile,               //文件上传域的ID
           data: {
               A0100:A0100
           },            //附加的额外参数
           dataType: 'text',                   //返回值类型 一般设置为json
           success: function (data, status)    //服务器成功响应处理函数
           {
               if (data == "success") {
                   //mini.alert("上传成功!"+data);
                   window.location.reload();
               }else if (data == "failure"){
                   mini.alert("上传失败，照片格式为.jpg或.png或.bmp或.jpeg");
               }
           },
           error: function (data, status, e)   //服务器响应失败处理函数
           {
               alert("上传失败！" + data);
           },
           complete: function () {
               var jq = $("#file1 > input:file");
               jq.before(inputFile);
               jq.remove();
           }
       });
   }
   function F_Open_dialog(){
       alert("11111");
       //document.getElementById("file1").click();
   }


</script>

</body>
</html>

