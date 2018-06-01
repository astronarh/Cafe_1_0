<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <title><spring:message code="newAdmin.title" /></title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css" />
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.10.16/dataRender/datetime.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>

    <link href="<c:url value="/resources/static/css/admin.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/static/css/newAdmin.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/static/js/moment-with-locales.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/static/js/dataTableScriptAdmin.js" />" type="text/javascript"></script>
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

                    <div id="userEdit">
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
                                    <td><form:input path="email"/><label id="label_email" class="error"></label></td>
                                </tr>
                                    <form:hidden path="password" readonly="true"/>
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
                    </div>

                    <div id="restaurants">
                        <caption>Restaurants</caption>
                        <table id="restaurants-table" class="restaurants">
                            <thead>
                                <tr>
                                    <td width="40">id</td>
                                    <td>name</td>
                                    <td>site</td>
                                    <td>description</td>
                                    <td>foto</td>
                                    <td>enabled</td>
                                    <td>edit</td>
                                    <td>delete</td>
                                </tr>
                            </thead>
                        </table>
                    </div>

                    <div id="ratings" >
                        <caption>Ratings</caption>
                        <table id="ratings-table" class="restaurants" >
                            <thead>
                            <tr>
                                <td width="40">id</td>
                                <td>user_id</td>
                                <td>restaurant_id</td>
                                <td width="40">rating</td>
                                <td width="150">date</td>
                                <td>delete</td>
                            </tr>
                            </thead>
                        </table>
                    </div>

                    <div id="users">
                        <caption>Users</caption>
                        <table id="users-table" class="restaurants">
                            <thead>
                            <tr>
                                <td width="40">id</td>
                                <td>login</td>
                                <td style="width: 150px;">email</td>
                                <td style="width: 150px; overflow: hidden;">password</td>
                                <td>enabled</td>
                                <td>edit</td>
                                <td>delete</td>
                            </tr>
                            </thead>
                        </table>
                    </div>

                    <div id="restaurant">
                        <caption>The new restaurant</caption>
                            <%--@elvariable id="restaurant" type="ru.astronarh.dto.RestaurantDTO"--%>
                        <form:form method="POST" action="/spring-mvc-xml/addEmployee" modelAttribute="restaurant" name="userForm" commandName="restaurant">
                            <table class="restaurant">
                                <tr>
                                    <td>Id</td>
                                    <td><form:input path="id" id="restaurantId" readonly="true"/></td>
                                </tr>
                                <tr>
                                    <td>Name</td>
                                    <td><form:input path="name"/><label id="label_name" class="error"></label></td>
                                </tr>
                                <tr>
                                    <td>Site</td>
                                    <td><form:input path="site"/><label id="label_site" class="error"></label></td>
                                </tr>
                                <tr>
                                    <td>Description</td>
                                    <td><form:textarea path="description"/><label id="label_description" class="error"></label></td>
                                </tr>
                                <tr>
                                    <td>Foto</td>
                                    <td><form:input path="foto"/><label id="label_foto" class="error"></label></td>
                                </tr>
                                <tr>
                                    <td>Enabled</td>
                                    <td><form:checkbox path="enabled"/></td>
                                </tr>
                                <tr >
                                    <td><button type="submit" value="Submit" id="submit">Submit</button></td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                </security:authorize>
            </div>
        </div>
    </div>
</div>

<script>
    $(function() {
        /*  Submit form using Ajax */
        $('button[type=submit]').click(function(e) {

            //Prevent default submission of form
            e.preventDefault();

            //Remove all errors
            //$('input').next().remove();
            $('#label_name').text('');
            $('#label_description').text('');
            $('#label_foto').text('');
            $('#label_site').text('');

            $.post({
                url : '/adminAPI/newRestaurant',
                data : $('form[name=userForm]').serialize(),
                success : function(res) {

                    if(res.validated){
                        //Set response
                        //$('#resultContainer pre code').text(JSON.stringify(res.employee));
                        //$('#resultContainer').show();
                        //alert("Restaurant added!");
                        $('#restaurantId').val('');
                        $('#name').val('');
                        $('#description').val('');
                        $('#foto').val('');
                        $('#site').val('');
                        $('#show-restaurants').trigger('click');
                        restaurants.api().ajax.reload( null, false );
                    }else{
                        //Set error messages
                        $.each(res.errorMessages,function(key,value){
                            $('#label_' + key).text(value);
                        });
                    }
                }
            })
        });
    });
</script>
</body>
</html>
