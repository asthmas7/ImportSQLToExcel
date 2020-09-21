<%--
  Created by IntelliJ IDEA.
  User: lpx
  Date: 2020/9/15
  Time: 4:59 下午
  Description: 前端入参页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="script/jquery-3.3.1.js"></script>

<html>
<head>
    <title>Export</title>

</head>
<%--使用Ajax异步提交，而不使用form表单提交--%>

<body>
<%--<form id="export" name="export" method="post">--%>
    ip<input id="ip" name="ip" class="value"/><br>         <%--  主机ip--%>
    port<input id="port" name="port" /><br>                 <%-- 端口--%>
    database<input id="database" name="database" /><br>     <%--  数据库名--%>
    username<input id="username" name="username" /><br>        <%--  用户名--%>
    password<input id="password" name="password" /><br>         <%--  密码--%>
    sheetName<input id="sheetName" name="sheetName" /><br>      <%-- 要导出的表名--%>
    <input id ="button" type="button" onclick="submit()"  value="导出Excel"/>
<%--</form>--%>

<%--Ajax实现--%>
<script type="text/javascript">
function submit(){
    var data= {
        ip:$('#ip').val(),
        port:$('#port').val(),
        database:$('#database').val(),
        username:$('#username').val(),
        password:$('#password').val(),
        filePath:$('#filePath').val(),
        sheetName:$('#sheetName').val(),
    };
    console.log(data)
    $.ajax({
        type: 'POST',
        url: 'export/process',
        data: JSON.stringify(data),
        dataType:"text",    //返回字符串为text类型
        contentType: 'application/json;charset=utf-8',
        success: function (file) {
            alert("响应数据成功")
            console.log("success");
            console.log(file)
            var a = document.createElement("a");
            a.download = data.sheetName;
            a.href = file;
            a.click();

        },
        error: function (file) {

            alert("响应失败")

        }
    })
          }



</script>
</body>
</html>
