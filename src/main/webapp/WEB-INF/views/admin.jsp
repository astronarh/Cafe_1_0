<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: ShkerdinVA
  Date: 29.03.2018
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Admin</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>

<div id="new_restaurant">
    <security:authorize access="hasRole('ROLE_ADMIN')">
        Administration page <a href="${pageContext.request.contextPath}/">back</a>
        <form action="${pageContext.request.contextPath}/restaurant" th:object="${restaurant}" method='POST' >
            <h1>New restaurant</h1>
            <table>
                <tr>
                    <td>
                        Name:
                    </td>
                    <td>
                        <input id="name-field" placeholder="Enter name" type='text' name='name'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Site:
                    </td>
                    <td>
                        <input id="site-field" placeholder="Enter site" type='text' name='site'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Description:
                    </td>
                    <td>
                        <textarea id="description-field" placeholder="Enter description" name='description' style="width: 100%; height: 150px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        Foto:
                    </td>
                    <td>
                        <input id="foto-field-url" placeholder="Enter foto url" type='text' name='foto'/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input id="submit-button" name="submit" type="submit" value="submit" />
                    </td>
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <table>
            <thead>
            <tr>
                <td>
                    id
                </td>
                <td>
                    name
                </td>
                <td>
                    site
                </td>
                <td>
                    description
                </td>
                <td>
                    foto
                </td>
                <td>
                    enabled
                </td>
                <td>
                    edit
                </td>
                <td>
                    delete
                </td>
            </tr>
            </thead>
            <c:forEach var="restaurants" items="${restaurants}">
            <tr>
                <td>
                    <c:out value="${restaurants.id}" />
                </td>
                <td>
                    <c:out value="${restaurants.name}" />
                </td>
                <td>
                    <a href="<c:out value="${restaurants.site}" />" title="<c:out value="${restaurants.site}" />"><c:out value="${restaurants.name}" /></a>
                </td>
                <td width="300px">
                    <c:out value="${restaurants.description}" />
                </td>
                <td>
                    <div class="bgc-img" style="height: 25px">
                        <img src="<c:out value="${restaurants.foto}" />" style="position: absolute;"
                             onmouseover="this.height=150"
                             onmouseout="this.height=25"
                        height="25px">
                    </div>
                </td>
                <td>
                    <c:out value="${restaurants.enabled}" />
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/edit/${restaurants.id}" >edit</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/delete/${restaurants.id}" >delete</a>
                </td>
            </tr>
            </c:forEach>
        </table>

    </security:authorize>
    <security:authorize access="isAnonymous()">
        Access is denied <a href="${pageContext.request.contextPath}/">back</a>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_USER') && !hasRole('ROLE_ADMIN')">
        Access is denied <a href="${pageContext.request.contextPath}/">back</a>
    </security:authorize>
</div>
</body>
</html>

<script type="text/javascript">

</script>