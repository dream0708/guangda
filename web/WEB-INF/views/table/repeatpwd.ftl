<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>修改薪资密码</title>
</head>
<body>
<center>
    <div id="loginWindow" class="mini-window" title="修改薪资密码..." style="width:330px;height:200px;"
         showModal="true" showCloseButton="false" >

        <div id="loginForm" style="padding:15px;padding-top:10px;">
            <table>
                <tr>
                    <td style="width:60px;"><label for="pwd$text">旧密码：</label></td>
                    <td>
                        <input id="oldpwd" name="oldpwd" class="mini-password"
                               requiredErrorText="密码不能为空" required="true" style="width:200px;" onenter="onLoginClick"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:60px;"><label for="pwd$text">新密码：</label></td>
                    <td>
                        <input id="newpwd" name="oldpwd" class="mini-password"
                               requiredErrorText="密码不能为空" required="true" style="width:200px;" onenter="onLoginClick"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:60px;"><label for="pwd$text">确认密码：</label></td>
                    <td>
                        <input id="confpwd" name="oldpwd" class="mini-password"
                               requiredErrorText="密码不能为空" required="true" style="width:200px;" onenter="onLoginClick"/>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:15px;" colspan="2" align="center">
                        <a onclick="onLoginClick" class="mini-button" style="width:50px;">确认</a>&nbsp;
                        <#--<a onclick="onResetClick" class="mini-button" style="width:50px;">重置</a>-->
                        <a onclick="window.history.go(-1)" class="mini-button" style="width:50px;">返回</a>&nbsp;&nbsp;
                        <a onclick="resetInitPwd()" href="#">恢复初始密码？</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</center>
<script src="<@s.url '/js/boot.js'/>" type="text/javascript"></script>
<script type="text/javascript">
    mini.parse();
    var A0100 = "${A0100}";
    var loginWindow = mini.get("loginWindow");
    loginWindow.show();

    function onLoginClick(e) {
        var oldpwd = mini.get("oldpwd").value;
        var newpwd = mini.get("newpwd").value;
        var confpwd = mini.get("confpwd").value;
        var form = new mini.Form("#loginWindow");

        form.validate();
        if (form.isValid() == false) return;

        if (newpwd != confpwd) {
            mini.alert("新密码不一样！");
            return ;
        }else if(oldpwd == newpwd || oldpwd == confpwd) {
            mini.alert("新密码不能和旧密码一样！");
            return ;
        }

        $.ajax({
            url: "../salary/changePwd",
            type: "post",
            data: {
                oldPwd: oldpwd,
                newPwd: newpwd,
                confPwd: confpwd,
                A0100:A0100
            },
            success: function (data) {
                if (data != "yes"){
                    mini.alert(data);
                }else {
                    loginWindow.hide();
                    mini.loading("正在返回...", "修改成功");
                    setTimeout(function () {
                        //window.location = "../salary/changePwdSuccessPage";
                        window.history.go(-1);
                    }, 1500);
                }
            }
        });
    }

    function onResetClick(e) {
        var form = new mini.Form("#loginWindow");
        form.clear();
    }

    function resetInitPwd() {
        //var yesorno = confirm("您确认要重置密码为身份证后6位吗？");
        mini.confirm("您确认要重置密码为身份证后6位吗？？","操作确认提示！",function(action){
            if(action=="ok"){
                $.ajax({
                    url: "../salary/resetPwd",
                    type: "post",
                    data: {
                        A0100:A0100
                    },
                    success: function (data) {//success
                        loginWindow.hide();
                        mini.loading("正在返回...", "恢复初始密码成功");
                        setTimeout(function () {
                            //window.location = "../salary/changePwdSuccessPage";
                            window.history.go(-2);
                        }, 1500);
                    }
                });
            }
        });

    }

</script>

</body>
</html>

