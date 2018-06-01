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
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link href="<c:url value="/resources/static/css/admin.css" />" rel="stylesheet">
</head>
<body>
<div id="user" align="center">
    <div class="main">
        <div class="main_in_main">
            <div class="content">
                <div style="border-width: thin; width: 100%; position: absolute; top: 0px; left: 0px; text-align: right;">
                    <img src="/resources/static/images/close_red.png" width=30 height=30>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="container" align="center">
    <div class="main">
        <div class="main_in_main">
            <div class="content">
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <h1 align="center">Administration page</h1>
                    <input type='button' value='Main page' class="flat" onclick="location.href='/';">
                    <input type='button' id='add-new-restaurant' value='Add new restaurant' class="flat">
                    <input type='button' id='show-ratings' value='Show ratings' class="flat">
                    <input type='button' id='show-restaurants' value='Show restaurants' class="flat">
                    <input type='button' id='show-users' value='Show users' class="flat">

                    <div id="new_restaurant" style="display: none; background-color: #eeeeee">

                        <a href="/restaurant">NEW</a>

                        <form action="${pageContext.request.contextPath}/restaurant" th:object="${restaurant}" method='POST' >
                            <table>
                                <caption>New restaurant</caption>
                                <tr>
                                    <td>
                                        Name:
                                    </td>
                                    <td>
                                        <input id="name-field" placeholder="Enter name" type='text' name='name' class="new-restaurant"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Site:
                                    </td>
                                    <td>
                                        <input id="site-field" placeholder="Enter site" type='text' name='site' class="new-restaurant"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Description:
                                    </td>
                                    <td>
                                        <textarea id="description-field" placeholder="Enter description" name='description' style="width: 100%; height: 150px;" class="new-restaurant"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Foto:
                                    </td>
                                    <td>
                                        <input id="foto-field-url" placeholder="Enter foto url" type='text' name='foto' class="new-restaurant"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input id="submit-button" name="submit" type="submit" value="submit" class="flat"/>
                                    </td>
                                </tr>
                            </table>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </form>
                    </div>
                    <div id="ratings" style="display: none;">
                        <table class="restaurants">
                            <caption>Ratings</caption>
                            <tr>
                                <td>
                                    id
                                </td>
                                <td>
                                    user_id
                                </td>
                                <td>
                                    restaurant_id
                                </td>
                                <td>
                                    rating
                                </td>
                                <td>
                                    date
                                </td>
                                <td>
                                    delete
                                </td>
                            </tr>
                            <c:forEach var="ratings" items="${ratings}" varStatus="status">
                                <tr>
                                    <td><c:out value="${ratings.id}" /></td>
                                    <td><a href="#" onclick="userInfo(${ratings.userID})"><c:out value="${ratings.userID}" /></a></td>
                                    <td><c:out value="${ratings.restaurantId}"/></td>
                                    <td><c:out value="${ratings.rating}" /></td>
                                    <td><c:out value="${ratings.date}" /></td>
                                    <td><a href="${pageContext.request.contextPath}rating/delete/${ratings.id}" >delete</a></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div id="restaurants">
                        <table class="restaurants">
                            <caption>Restaurants</caption>
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
                                    <td style="width: 300px" title="<c:out value="${restaurants.description}" />">
                                        <span id="description"><c:out value="${restaurants.description}" /></span>
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
                    </div>
                    <div id="users" style="display: none; background-color: #eeeeee">
                        <table class="restaurants">
                            <caption>Users</caption>
                            <tr>
                                <td width="50px">
                                    id
                                </td>
                                <td>
                                   login
                                </td>
                                <td width="200px">
                                    email
                                </td>
                                <td>
                                    enabled
                                </td>
                                <td>
                                    role_admin
                                </td>
                                <td>
                                    edit
                                </td>
                            </tr>
                            <c:forEach var="user" items="${users}" varStatus="status">
                                <tr>
                                    <td>
                                        <c:out value="${user.id}" />
                                    </td>
                                    <td>
                                        <c:out value="${user.login}" />
                                    </td>
                                    <td>
                                        <c:out value="${user.email}" />
                                    </td>
                                    <td>
                                        <label style="color: green;"><c:if test="${user.enabled == 1}">enabled</c:if></label>
                                        <label style="color: red;"><c:if test="${user.enabled == 0}">disabled</c:if></label>
                                    </td>
                                    <td>
                                        role_admin
                                    </td>
                                    <td>
                                        <a href="#" onclick="userInfo(${user.id})">edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>

                </security:authorize>
                <security:authorize access="isAnonymous()">
                    Access is denied <a href="${pageContext.request.contextPath}/">back</a>
                </security:authorize>
                <security:authorize access="hasRole('ROLE_USER') && !hasRole('ROLE_ADMIN')">
                    Access is denied <a href="${pageContext.request.contextPath}/">back</a>
                </security:authorize>
            </div>
        </div>
    </div>
</div>

</body>
</html>

<script>
    $(document).ready(function(){
        $('#hideshow').click(function(event) {
            $('#new_restaurant').toggle('hide');
        });

        $('#hideshow-ratings').click(function (event) {
            $('#ratings').toggle('hide');
            $('#restaurants').toggle('hide');
            //$('#hideshow-ratings').val('Show restaurants');
            var value = $('#hideshow-ratings').attr('value');
            if (value === 'Show ratings') $('#hideshow-ratings').val('Show restaurants')
            else $('#hideshow-ratings').val('Show ratings');
        })

        $('#show-users').click(function () {
            $('#users').show();
            $('#ratings').hide();
            $('#restaurants').hide();
            $('#new_restaurant').hide();
        })
        $('#add-new-restaurant').click(function () {
            $('#users').hide();
            $('#ratings').hide();
            $('#restaurants').hide();
            $('#new_restaurant').show();
        })
        $('#show-ratings').click(function () {
            $('#users').hide();
            $('#ratings').show();
            $('#restaurants').hide();
            $('#new_restaurant').hide();
        })
        $('#show-restaurants').click(function () {
            $('#users').hide();
            $('#ratings').hide();
            $('#restaurants').show();
            $('#new_restaurant').hide();
        })

    });

    function userInfo(id) {
        $.get('user/' + id, {
        }, function (responseText) {
            $('#user').html(responseText);
            $('#user').toggle('hide');
        });
    }

    function closeUserInfo() {
        $('#user').toggle('hide');
    }

</script>