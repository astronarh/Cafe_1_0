<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ShkerdinVA
  Date: 16.05.2018
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Restaurant</title>
    <link href="<c:url value="/resources/static/css/user.css" />" rel="stylesheet">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<%--@elvariable id="restaurant" type="ru.astronarh.model.Restaurant"--%>
<form:form method="POST" action="/spring-mvc-xml/addEmployee" modelAttribute="restaurant" name="userForm">
    <table>
        <tr>
            <td><form:label path="id" >Id</form:label></td>
            <td><form:input path="id" readonly="true" value="15" id="id" /></td>
        </tr>
        <tr>
            <td><form:label path="name">Name</form:label></td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td><form:label path="site">Site</form:label></td>
            <td><form:input path="site"/></td>
        </tr>
        <tr>
            <td><form:label path="description">Description</form:label></td>
            <td><form:textarea path="description"/></td>
        </tr>
        <tr>
            <td><form:label path="foto">Foto</form:label></td>
            <td><form:input path="foto"/></td>
        </tr>
        <tr>
            <td><form:label path="enabled">Enabled</form:label></td>
            <td><form:checkbox path="enabled" id="enabled"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>

<button id="change">OK</button>

<script>
    $(function() {
        $("#change").click(function(e) {
            $.post({
                url : '/restaurant/' + $("#id").val(),
                data : $('form[name=userForm]').serialize(),
                success : function(res) {
                    for(key in res)
                    {
                        if(res.hasOwnProperty(key)) {
                            $('input[name='+key+']').val(res[key]);
                            $('textarea[name='+key+']').val(res[key]);
                            $('#enabled').prop('checked', res[key]);
                        }
                    }
                    /*if(res.validated){
                        //Set response
                        $('#resultContainer pre code').text(JSON.stringify(res.userDTO));
                        $('#resultContainer').show();

                    }else{
                        //Set error messages
                        $.each(res.errorMessages,function(key,value){
                            $('input[name='+key+']').after('<span class="error">'+value+'</span><br/><br/>');
                        });
                    }*/
                }
            })
        });
    });
</script>
</body>
</html>
