<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2018/5/1
  Time: 8:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h1>欢迎登录</h1>
<form action="/loginUser" method="post">
    <input type="text" name="username"/> <br>
    <input type="password" name="password"> <br>
    <input type="submit" value="登录">
</form>
</body>
</html>
