<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ShkerdinVA
  Date: 19.04.2018
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <link href="<c:url value="/resources/static/css/admin.css" />" rel="stylesheet">
</head>
<body>
Edit page <a href="/admin">back admin page</a>
<form action="${pageContext.request.contextPath}/restaurant" th:object="${restaurant}" method='POST' >
    <div id="container" align="center">
        <div class="main">
            <div class="main_in_main">
                <div class="content">
                    <table>
                        <caption>Edit restaurant</caption>
                        <tr>
                            <td>
                                Name:
                            </td>
                            <td>
                                <input id="name-field" placeholder="Enter name" type='text' name='name' class="new-restaurant" value="${restaurant.name}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Site:
                            </td>
                            <td>
                                <input id="site-field" placeholder="Enter site" type='text' name='site' class="new-restaurant" value="${restaurant.site}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Description:
                            </td>
                            <td>
                <textarea id="description-field" placeholder="Enter description" name='description' style="width: 100%; height: 150px;" class="new-restaurant">
                    ${restaurant.description}
                </textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Foto:
                            </td>
                            <td>
                                <input id="foto-field-url" placeholder="Enter foto url" type='text' name='foto' class="new-restaurant" value="${restaurant.foto}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Enabled:
                            </td>
                            <td>
                                <input type="checkbox" id="checkbox-enabled" ${restaurant.enabled == 1 ? 'checked' : ''} onchange="check()"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input id="submit-button" name="submit" type="submit" value="submit" class="flat"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" id="enabled" name="enabled" value="1">
    <input type="hidden" name="id" value="${restaurant.id}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</body>
</html>
<script>
    function check() {
        if (document.getElementById('checkbox-enabled').checked) document.getElementById('enabled').value = 1;
        else document.getElementById('enabled').value = 0;
    }
</script>
