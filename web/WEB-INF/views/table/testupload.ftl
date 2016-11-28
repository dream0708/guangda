<#import "spring.ftl" as s />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:s="http://www.w3.org/1999/html">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>测试上传</title>
    <#--<s:link url="/upload/photo"-->
</head>
<body>
<script src="<@s.url '/js/boot.js'/>" type="text/javascript"></script>
<script src="<@s.url '/js/ui/ajaxfileupload.js'/>" type="text/javascript"></script>

<center style="height:100%">
    <div style="background-color: #83cf65" class="" >
        <img src="${img}" alt="个人照片" style="height: 150px;width: 120px;"/><br>
        上传照片:<input class="mini-htmlfile" name="Fdata" id="file1" style="width:70px;"/><br>
        <input type="button" value="上传" onclick="ajaxFileUpload()"/>
    </div>

</center>

<script type="text/javascript">
    var A0100 = "00000120";
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
                alert("上传成功: " + data);
                window.location.reload();

            },
            error: function (data, status, e)   //服务器响应失败处理函数
            {
                alert(e);
            },
            complete: function () {
                var jq = $("#file1 > input:file");
                jq.before(inputFile);
                jq.remove();
            }
        });
    }
</script>

</body>
</html>

