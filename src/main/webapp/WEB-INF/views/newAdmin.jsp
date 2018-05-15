<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ShkerdinVA
  Date: 14.05.2018
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New Admin Page</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="<c:url value="/resources/static/css/admin.css" />" rel="stylesheet">
</head>
<body>
<div id="container" align="center">
    <div class="main">
        <div class="main_in_main">
            <div class="content" id="content">
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <h1 align="center">Administration page</h1>
                    <input type='button' value='Main page' class="flat" onclick="location.href='/';">
                    <input type='button' id='add-new-restaurant' value='Add new restaurant' class="flat">
                    <input type='button' id='show-ratings' value='Show ratings' class="flat">
                    <input type='button' id='show-restaurants' value='Show restaurants' class="flat">
                    <input type='button' id='show-users' value='Show users' class="flat">
                </security:authorize>
            </div>
        </div>
    </div>
</div>
</body>
</html>
