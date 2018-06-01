<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: ShkerdinVA
  Date: 23.04.2018
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
    <link href="<c:url value="/resources/static/css/user.css" />" rel="stylesheet">
</head>
<body>
<form action="API/saveUser" style="border:1px solid #ccc" th:object="${user}" method='POST' name="userForm">
    <div class="container">
        <h1>User information</h1>

        <label for="id"><b>Id</b></label>
        <input type="text" placeholder="Enter Email" name="id" id="id" required value="${user.id}" readonly>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter Login" name="login" id="login" required value="${user.login}">

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" id="email" required value="${user.email}" >

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Password" name="password" id="password" required value="${user.password}" readonly ondblclick="editPassword()">

        <label for="matchingPassword" id="labelForRepeatPassword" style="display: none;"><b>Repeat Password</b></label>
        <input type="password" placeholder="Repeat Password" id="matchingPassword" name="matchingPassword" required value="${user.password}" style="display: none;">

        <label>
            <c:if test="${user.enabled == 1}"><input type="checkbox" checked="checked" name="enabled" style="margin-bottom:15px"> Enabled</c:if>
            <c:if test="${user.enabled == 0}"><input type="checkbox" name="enabled" style="margin-bottom:15px"> Enabled</c:if>
            <c:if test="${admin}"><input type="checkbox" name="admin" style="margin-bottom:15px" checked> Set Admin Role</c:if>
            <c:if test="${!admin}"><input type="checkbox" name="admin" style="margin-bottom:15px"> Set Admin Role</c:if>
        </label>

        <div class="clearfix">
            <button type="button" class="cancelbtn" onclick="closeUserInfo()">Cancel</button>
            <button type="submit" class="signupbtn">Change</button>
            <!--<button type="button" class="cancelbtn" id="change">ChangeAJAX</button>-->
        </div>
    </div>
    <script>
        function editPassword() {
            $('#password').prop('readonly', false);
            $('#matchingPassword').toggle('hide');
            $('#labelForRepeatPassword').toggle('hide');
        }

        $(function() {
            /*  Submit form using Ajax */
            $("#change").click(function(e) {

                //Prevent default submission of form
                e.preventDefault();

                //Remove all errors
                $('input').next().remove();

                $.post({
                    url : 'API/saveUser/POST',
                    data : $('form[name=userForm]').serialize(),
                    success : function(res) {

                        if(res.validated){
                            //Set response
                            $('#resultContainer pre code').text(JSON.stringify(res.userDTO));
                            $('#resultContainer').show();

                        }else{
                            //Set error messages
                            $.each(res.errorMessages,function(key,value){
                                $('input[name='+key+']').after('<span class="error">'+value+'</span><br/><br/>');
                            });
                        }
                    }
                })
            });
        });

    </script>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<!-- Result Container  -->
<div id="resultContainer" style="display: none;">
    <hr/>
    <h4 style="color: green;">JSON Response From Server</h4>
    <pre style="color: green;">
    <code></code>
   </pre>
</div>

</body>
</html>
