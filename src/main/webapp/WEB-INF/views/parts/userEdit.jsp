<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: ShkerdinVA
  Date: 29.05.2018
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>userEdit</title>
</head>
<body>
<%--@elvariable id="userDTO" type="ru.astronarh.dto.UserDTO"--%>
<form:form method="POST" action="" modelAttribute="userDTO" name="userDTO" commandName="userDTO">
    <table class="restaurant">
        <tr>
            <td>Id</td>
            <td><form:input path="id" readonly="true"/></td>
        </tr>
        <tr>
            <td>Login</td>
            <td><form:input path="login"/><label id="label_login" class="error"></label></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><form:textarea path="email"/><label id="label_email" class="error"></label></td>
        </tr>
        <tr>
            <td>Admin</td>
            <td><form:checkbox path="admin"/></td>
        </tr>
        <tr>
            <td>Enabled</td>
            <td><form:checkbox path="enabled"/></td>
        </tr>
        <tr >
            <td><button type="submit" value="Submit" id="submitUser">Submit</button></td>
        </tr>
    </table>
</form:form>
</body>
</html>
