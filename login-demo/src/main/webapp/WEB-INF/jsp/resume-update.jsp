<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改简历</title>
</head>
<body>
<form id="update" action="${pageContext.request.contextPath}/resume/update" method="post">
    <jsp:useBean id="resume" scope="request" type="com.github.codingdebugallday.logindemo.pojo.Resume"/>
    <p><label for="id">id: </label><input type="text" id="id" name="id" value="${resume.id}" readonly="readonly"/></p>
    <p><label for="name">姓名: </label><input type="text" id="name" name="name" value="${resume.name}"/></p>
    <p><label for="address">地址: </label><input type="text" id="address" name="address" value="${resume.address}"/></p>
    <p><label for="phone">手机: </label><input type="text" id="phone" name="phone" value="${resume.phone}"/></p>
    <input type="submit" value="提交">
</form>
</body>
</html>
