<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>薪资登录</title>
</head>
<body>
<center style="height:100%">
    <div id="loginWindow" class="mini-window" title="请输入薪资查看密码..." style="width:330px;height:150px;"
         showModal="true" showCloseButton="false" >

        <div id="loginForm" style="padding:15px;padding-top:10px;">
            <table>
                <tr>
                    <td style="width:60px;color:red;" colspan=2>初始密码默认为身份证后6位</td>

                </tr>
                <tr>
                    <td style="width:60px;"><label for="pwd$text">密码：</label></td>
                    <td>
                        <input id="pwd" name="pwd" class="mini-password"
                               requiredErrorText="密码不能为空" required="true" style="width:150px;" onenter="onLoginClick"/>
                        &nbsp;&nbsp;<a href="../salary/changePwdInit?A0100=${A0100}">修改密码</a>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td style="padding-top:5px;">
                        <a onclick="onLoginClick" class="mini-button" style="width:60px;">确认</a>
                       <!-- <a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a> -->
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
        var password = mini.get("pwd").value;
        var form = new mini.Form("#loginWindow");

        form.validate();
        if (form.isValid() == false) return;
        $.ajax({
            url: "../salary/password",
            type: "post",
            data: {
                password: password,
                A0100:A0100
            },
            success: function (data) {
                if (data != "yes"){
                    mini.alert(data);
                }else {
                    loginWindow.hide();
                    mini.loading("登录成功，马上转到系统...", "登录成功");
                    setTimeout(function () {
                        window.location = "/fwv1/gd/gda58.do?A0100="+A0100;
                    }, 1500);
                }
            }
        });
    }

    function onResetClick(e) {
        var form = new mini.Form("#loginWindow");
        form.clear();
    }




</script>

</body>
</html>

