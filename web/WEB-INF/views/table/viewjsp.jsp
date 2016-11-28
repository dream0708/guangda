<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/11
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <%--<link rel="stylesheet" type="text/css" href="table.css">--%>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <title>员工信息</title>
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
${initData}

<script>

    /*function personTypeClick(e) {
     $.post("../table/employee", {
     personFalg: e.value
     },
     function (data, status) {
     $("#div1").html(data);
     });
     }*/

    $(document).ready(function () {
        var data1;
        var data2;
        $("#button1").click(function () {
            if (data1 == undefined) {
                $.post("../table/employee", {
                            personFalg: "01"
                        },
                        function (data, status) {
                            $("#div1").html(data);
                            data1 = data;
                        });
            }
            $("#div1").html(data1);
        });
        $("#button2").click(function () {
            if (data2 == undefined) {
                $.post("../table/employee", {
                            personFalg: "02"
                        },
                        function (data, status) {
                            $("#div1").html(data);
                            data2 = data;
                        });
            }
            $("#div1").html(data2);
        });


    })

</script>
</body>
</html>
