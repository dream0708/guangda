<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>员工信息审核</title>
    <#--<link href="<@s.url '/css/table.css'/>" rel="stylesheet"/>-->
</head>
<body>
<center>
<div style="width:800px;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-goto" onclick="edit()">审核</a>
                    <#--<a class="mini-button" iconCls="icon-upgrade" onclick="goback()">返回</a>-->
                </td>
                <td style="white-space:nowrap;">
                    <input id="key" class="mini-textbox" emptyText="请输入单位/部门/岗位/姓名" style="width:200px;" onenter="onKeyEnter"/>
                    <a class="mini-button" onclick="search()">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:800px;height:648px;" allowResize="false" showPager="false"
     url="../table/getInfoApproval?orgId=${orgId}"  idField="id" multiSelect="false"
>
    <div property="columns">
        <div type="indexcolumn"></div>
        <div type="checkcolumn" ></div>
        <div field="company" width="120" headerAlign="center" allowSort="true">单位名称</div>
        <div field="department" width="120" headerAlign="center" allowSort="true">部门名称</div>
        <div field="station" width="120" headerAlign="center" allowSort="true">岗位名称</div>
        <div field="name" width="120" headerAlign="center" allowSort="true">员工姓名</div>
    </div>
</div>
</center>
<script src="<@s.url '/js/boot.js'/>" type="text/javascript"></script>
<script>
    mini.parse();

    var grid = mini.get("datagrid1");
    grid.load();

    function goback() {
        mini.alert("返回按钮连接未设置！请设置再使用！");
        //window.location.href = "http://localhost:8080/table/init?flag=01&A0100=00000002&userType=01";
    }

    function edit() {
        var row = grid.getSelected();
        if (row) {
            mini.open({//init?flag=01&A0100=00000005&userType=01
                url: bootPATH + "../table/init?flag=01&A0100="+row.id+"&userType=02",
                title: "【"+row.name+"】信息审核中···", width: 1300, height: 720,
                onload: function () {
                   /* var iframe = this.getIFrameEl();
                    var data = {
                       action: "edit",
                        id: row.id
                    };
                    iframe.contentWindow.SetData(data);*/
                },
                ondestroy: function (action) {
                    grid.reload();
                }
            });
        } else {
            alert("请选中一条记录");
        }
    }

    function search() {
        var key = mini.get("key").getValue();
        grid.load({
            key: key
        });
    }
    function onKeyEnter(e) {
        search();
    }

</script>
</body>
</html>

