<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<form id="add" action="${pageContext.request.contextPath}/resume/insert" method="post">
    <div>
        <p><label for="name">姓名</label><input type="text" id="name" name="name"/></p>
        <p><label for="address">地址</label><input type="text" id="address" name="address"/></p>
        <p><label for="phone">手机</label><input type="text" id="phone" name="phone"/></p>
        <input type="submit" value="提交">
    </div>
</form>
</body>
</html>
