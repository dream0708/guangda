<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/13
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/META-INF/resources/js/boot.js"></script>
    <style type="text/css">
        * {
            font-size: 14px;
            font-family: "微软雅黑";
        }

        input {
            width: 100%;
            height: 100%;
            border: solid 1px #ddd
        }

        body, table {
            font-size: 12px;
        }

        table {
            empty-cells: show;
            border-collapse: collapse;
            margin: 0 auto;
        }

        td {
            height: 30px;
        }

        h1, h2, h3 {
            font-size: 12px;
            margin: 0;
            padding: 0;
        }

        .title {
            background: #FFF;
            border: 1px solid #9DB3C5;
            padding: 1px;
            width: 90%;
            margin: 20px auto;
        }

        .title h1 {
            line-height: 31px;
            text-align: center;
            background: #2F589C url(th_bg2.gif);
            background-repeat: repeat-x;
            background-position: 0 0;
            color: #FFF;
        }

        .title th, .title td {
            border: 1px solid #cccccc;
            padding: 5px;
        }

        table.t1 {
            border: 1px solid #cccccc;
            color: #000;
        }

        table.t1 th {
            background-color: rgb(244, 247, 247);
            background-repeat:: repeat-x;
            background-repeat:: repeat-y;
            height: 30px;
            line-height: 30px;
        }

        table.t1 td, table.t1 th {
            border: 1px solid #ADADAD;
            padding: 0 1em 0;
        }

        table.t1 td {
            padding: 0;
        }

        table.t1 tr.a1 {
            background-color: #f5fafe;
        }

        table.t1 tr.a2 {
            background-color: #fbd8e8;
        }

        table.t2 {
            border: 1px solid #9db3c5;
            color: #666;
        }

        table.t2 th {
            background-image: url(th_bg2.gif);
            background-repeat:: repeat-x;
            height: 20px;
            color: #fff;
        }

        table.t2 td {
            border: 1px dotted #c5c5c5;
            padding: 0 2px 0;
        }

        table.t2 th {
            border: 1px solid #a7d1fd;
            padding: 0 2px 0;
        }

        table.t2 tr.a1 {
            background-color: #e8f3fd;
        }

        table.t3 {
            border: 1px solid #fc58a6;
            color: #720337;
        }

        table.t3 th {
            background-image: url(th_bg3.gif);
            background-repeat:: repeat-x;
            height: 30px;
            color: #35031b;
        }

        table.t3 td {
            border: 1px dashed #feb8d9;
            padding: 0 1.5em 0;
        }

        table.t3 th {
            border: 1px solid #b9f9dc;
            padding: 0 2px 0;
        }

        table.t3 tr.a1 {
            background-color: #fbd8e8;
        }

        A:link {
            COLOR: #1B4A98;
            TEXT-DECORATION: none;
            font-size: 12px
        }

        A:visited {
            COLOR: #1B4A98;
            TEXT-DECORATION: none;
            font-size: 12px
        }

        A:active {
            COLOR: #1B4A98;
            TEXT-DECORATION: none;
            font-size: 12px
        }

        A:hover {
            COLOR: #E39E19;
            TEXT-DECORATION: none;
            font-size: 12px
        }

        TEXTAREA {
            width: 100%;
            height: 100%;;
            border: solid 1px #ccc;
        }
    </style>
</head>
<body>
<input id="date1" class="mini-datepicker" value="2010-01-01"/>
<input class="mini-datepicker" name="A3715" value="2010-01-01"/>
<div id="contextdiv">
    <div>
        <center>
            <table class="t1" id="basicInfo" style="width: 1024px;" border="1">
                <tbody>
                <tr class="a1" style="height: 40px;">
                    <th align="left" colspan="6">基本信息</th>
                </tr>
                <tr>
                    <th align="left" style="width: 170px;">姓名</th>
                    <td align="left" style="width: 170px;"><input name="A0101"></td>
                    <th align="left" style="width: 170px;">部门</th>
                    <td align="left" style="width: 170px;"><input class="mini-treeselect" value="5,6"
                                                                  text="System,Video" multiselect="true"
                                                                  textfield="E0122" valuefield="id"
                                                                  url="../table/fieldCode?fieldCode=UM"></td>
                    <th align="left" style="width: 170px;">曾用名</th>
                    <td align="left" style="width: 170px;"><input name="A0104"></td>
                </tr>
                <tr>
                    <th align="left" style="width: 170px;">性别</th>
                    <td align="left" style="width: 170px;"><input class="mini-treeselect" value="5,6"
                                                                  text="System,Video" multiselect="true"
                                                                  textfield="A0107" valuefield="id"
                                                                  url="../table/fieldCode?fieldCode=AX"></td>
                    <th align="left" style="width: 170px;">年龄</th>
                    <td align="left" style="width: 170px;"><input name="C0101"></td>
                    <th align="left" style="width: 170px;">民族</th>
                    <td align="left" style="width: 170px;"><input class="mini-treeselect" value="5,6"
                                                                  text="System,Video" multiselect="true"
                                                                  textfield="A0121" valuefield="id"
                                                                  url="../table/fieldCode?fieldCode=AE"></td>
                </tr>
                <tr class="a1" style="height: 40px;">
                    <th align="left" colspan="6">学历信息</th>
                </tr>
                <tr>
                    <td style="padding: 2px;" colspan="6">
                        <table class="t1" id="A04" style="width: 100%;" border="1">
                            <tbody>
                            <tr>
                                <th align="center">序号</th>
                                <th align="center">毕业学校</th>
                                <th align="center">毕业时间</th>
                                <th align="center">学历性质</th>
                                <th align="center">学历</th>
                                <th align="center">学位</th>
                            </tr>
                            <tr>
                                <td align="center">1</td>
                                <td align="left"><input name="A0435"></td>
                                <td align="left"><input name="A0430" class="mini-datepicker" value="2010-01-01"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="C0401" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=KF"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="A0405" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=AM"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="A0440" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=AN"></td>
                            </tr>
                            <tr>
                                <td align="center">2</td>
                                <td align="left"><input name="A0435"></td>
                                <td align="left"><input name="A0430" class="mini-datepicker" value="2010-01-01"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="C0401" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=KF"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="A0405" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=AM"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="A0440" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=AN"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr class="a1" style="height: 40px;">
                    <th align="left" colspan="6">培训经历</th>
                </tr>
                <tr>
                    <td style="padding: 2px;" colspan="6">
                        <table class="t1" id="A37" style="width: 100%;" border="1">
                            <tbody>
                            <tr>
                                <th align="center">序号</th>
                                <th align="center">培训机构</th>
                                <th align="center">培训开始时间</th>
                                <th align="center">培训结束时间</th>
                                <th align="center">培训内容</th>
                            </tr>
                            <tr>
                                <td align="center">1</td>
                                <td align="left"><input name="H38A1"></td>
                                <td align="left"><input name="A3715" class="mini-datepicker" value="2010-01-01"></td>
                                <td align="left"><input name="A3720" class="mini-datepicker" value="2010-01-01"></td>
                                <td align="left"><input name="A3730"></td>
                            </tr>
                            <tr>
                                <td align="center">2</td>
                                <td align="left"><input name="H38A1"></td>
                                <td align="left"><input name="A3715" class="mini-datepicker" value="2010-01-01"></td>
                                <td align="left"><input name="A3720" class="mini-datepicker" value="2010-01-01"></td>
                                <td align="left"><input name="A3730"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr class="a1" style="height: 40px;">
                    <th align="left" colspan="6">家庭情况</th>
                </tr>
                <tr>
                    <td style="padding: 2px;" colspan="6">
                        <table class="t1" id="A79" style="width: 100%;" border="1">
                            <tbody>
                            <tr>
                                <th align="center">序号</th>
                                <th align="center">成员姓名</th>
                                <th align="center">与本人关系</th>
                                <th align="center">电话号码</th>
                                <th align="center">住址</th>
                            </tr>
                            <tr>
                                <td align="center">1</td>
                                <td align="left"><input name="A7905"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="A7910" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=AS"></td>
                                <td align="left"><input name="C7901"></td>
                                <td align="left"><input name="A7920"></td>
                            </tr>
                            <tr>
                                <td align="center">2</td>
                                <td align="left"><input name="A7905"></td>
                                <td align="left"><input class="mini-treeselect" value="5,6" text="System,Video"
                                                        multiselect="true" textfield="A7910" valuefield="id"
                                                        url="../table/fieldCode?fieldCode=AS"></td>
                                <td align="left"><input name="C7901"></td>
                                <td align="left"><input name="A7920"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </center>
    </div>
</div>
</body>
</html>
