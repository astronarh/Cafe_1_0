<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Restaurant Website and Gallery Template with jQuery and Google Maps</title>
    <meta charset=utf-8>
    <meta name="description" content="Restaurant Website and Gallery Template with jQuery and Google Maps" />
    <meta name="keywords" content="jquery, gallery, /resources/static/images, css3, html5, photography, website, template, google maps, sliding, background"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/static/favicon.png" type="image/png"/>
    <link href="<c:url value="/resources/static/css/style.css" />" rel="stylesheet">
    <link href="<c:url value="http://fonts.googleapis.com/css?family=PT+Sans+Narrow" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="http://fonts.googleapis.com/css?family=Terminal+Dosis+Light" />" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js" />"></script>
    <script src="<c:url value="http://maps.google.com/maps/api/js?sensor=true" />" type="text/javascript"></script>
</head>
<body>


<div id="map"></div>
<div id="bf_container" class="bf_container">
    <div id="bf_background" class="bf_background">
        <img src="${pageContext.request.contextPath}/resources/static/images/background/default.jpg" alt="image1" style="display:none;"/>
        <div class="bf_overlay"></div>
    </div>
    <div id="bf_page_menu" class="bf_menu" >
        <h1 class="title">Restaurateur<span><span>in each of us</span></span></h1>
        <ul>
            <li><a href="#" data-content="home"><span class="bf_hover"></span><span>Welcome</span></a></li>
            <security:authorize access="isAnonymous()"><li><a href="#" data-content="login"><span class="bf_hover"></span><span>Login</span></a></li></security:authorize>
            <security:authorize access="isAuthenticated()"><li><a href="#" data-content="login"><span class="bf_hover"></span><span><security:authentication property="principal.username" /></span></a></li></security:authorize>
            <li><a href="#" data-content="about"><span class="bf_hover"></span><span>About us</span></a></li>
            <li><a href="#" data-content="menu"><span class="bf_hover"></span><span>Restaurants</span></a></li>
            <li><a href="#" data-content="visit"><span class="bf_hover"></span><span>Visit us</span></a></li>
        </ul>
    </div>
    <div class="bf_page" id="home" style="display:block;">
        <div class="bf_content_text">
            <h2>Welcome</h2>
            <p>When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.</p>
        </div>
    </div>
    <div class="bf_page" id="login">
        <div class="bf_content_text">
            <security:authorize access="isAuthenticated()">
                <h2><security:authentication property="principal.username" /></h2>
                <form action="/logout" method="post">
                    <p>
                        Login as <security:authentication property="principal.username" />
                        <input id="logout-button" type="submit" value="Logout">
                        <security:authorize access="hasRole('ROLE_ADMIN')">
                            <a href="/admin" style="color: white">Admin page</a>
                        </security:authorize>
                    </p>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </security:authorize>
            <security:authorize access="isAnonymous()">
                <div id="login_div">
                    <h2>Login <a href="#" onclick="changeRegLog()">Registration</a></h2>
                    <form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST' role="form">
                        <table class="login_form">
                            <tr>
                                <td><span><p>User:</p></span></td>
                            </tr>
                            <tr>
                                <td><span><p><input type='text' name='username' placeholder="Username"></p></span></td>
                            </tr>
                            <tr>
                                <td><span><p>Password:</p></span></td>
                            </tr>
                            <tr>
                                <td><span><p><input type='password' name='password' placeholder="Password"/></p></span></td>
                            </tr>
                            <td>
                                &nbsp&nbsp&nbsp&nbsp&nbsp
                                <label style="font-weight:bold; cursor: pointer;">Remember me<input type="checkbox" name="remember-me"/></label>
                            </td>
                            </tr>
                            <tr>
                                <td>
                                    <span><p><input id="submit-login" name="submit" type="submit" value="login" style="cursor: pointer;"/></p></span>
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
                <div id="registration_div">
                    <h2><a href="#" onclick="changeRegLog()">Login</a> Registration</h2>
                    <form action="/registration" th:object="${user}" method='POST'>
                        <table>
                            <tr>
                                <td>
                                    <div>
                                        <security:authorize access="isAnonymous()">
                                            <table cellpadding="3" cellspacing="3">
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p>Username:</p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p><input id="login-field" placeholder="Enter Username" type='text' name='login' value="${user.getLogin()}"></p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p>Email:</p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p><input id="email-field" placeholder="Enter Email" type='text' name='email' value="${user.getEmail()}"></p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p>Password:</p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p><input id="password-field" placeholder="Enter Password" type='password' name='password' /></p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p>Confirm password:</p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p><input id="confirm-password-field" placeholder="Confirm Password" type='password' name='matchingPassword' /></p></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <span><p><input id="submit-registration" name="Submit" type="submit" value="submit" /></p></span>
                                                    </td>
                                                </tr>
                                            </table>
                                        </security:authorize>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
            </security:authorize>
        </div>
    </div>
    <div class="bf_page" id="about">
        <div class="bf_content_text">
            <h2>About us</h2>
            <p>It is a paradisematic country, in which roasted parts of sentences fly into your mouth.</p>
            <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.</p>
        </div>
    </div>
    <div class="bf_page" id="menu">
        <div class="bf_content_text">
            <h2>Restaurants</h2>
            <p>Choose catering establishments.</p>
            <ul id="bf_dishes">
                <c:forEach var="restaurants" items="${restaurants}" varStatus="status">
                    <li><a href="#"><p alt="thumb${status.index}">${restaurants.name}</p></a></li>
                </c:forEach>
            </ul>
        </div>
        <div id="bf_gallery" class="bf_gallery">
            <a id="bf_close" href="#" class="bf_close"></a>
            <div class="bf_nav">
                <a id="bf_prev" href="#" class="bf_prev"></a>
                <a id="bf_next" href="#" class="bf_next"></a>
            </div>
            <div class="bf_gallery_wrapper">
                <c:forEach var="restaurants" items="${restaurants}" varStatus="status">
                    <div class="bf_gallery_item">
                        <div class="bf_heading"><h2><c:out value="${restaurants.name}" /></h2></div>
                        <div class="bf_desc">
                            <p><c:out value="${restaurants.description.length() > 27 ? restaurants.description.substring(0, 27) : restaurants.description}" />...</p>
                        </div>
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                        <br/>

                        <form id="new-rating${status.index}">
                            <div id="reviewStars-input">
                                <input id="star-4${status.index}" type="radio" name="rating" value="5" onclick="sendRating(${restaurants.id}, 5)"/>
                                <label title="gorgeous" for="star-4${status.index}"></label>

                                <input id="star-3${status.index}" type="radio" name="rating" value="4" onclick="sendRating(${restaurants.id}, 4)"/>
                                <label title="good" for="star-3${status.index}"></label>

                                <input id="star-2${status.index}" type="radio" name="rating" value="3" onclick="sendRating(${restaurants.id}, 3)"/>
                                <label title="regular" for="star-2${status.index}"></label>

                                <input id="star-1${status.index}" type="radio" name="rating" value="2" onclick="sendRating(${restaurants.id}, 2)"/>
                                <label title="poor" for="star-1${status.index}"></label>

                                <input id="star-0${status.index}" type="radio" name="rating" value="1" onclick="sendRating(${restaurants.id}, 1)"/>
                                <label title="bad" for="star-0${status.index}"></label>
                            </div>
                            <img src="${restaurants.foto}" alt="image1${status.index}" data-bgimg="${restaurants.foto}" />
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="bf_page" id="visit">

    </div>
</div>
<div class="msg-box" id="msg-box">
    <!-- <a class="bf_left" href="http://tympanus.net/Development/CircularDiscographyTemplate/"><span>&laquo; Previous Demo:</span> Circular Discography Template with jQuery</a>
    <a href="http://tympanus.net/codrops/2011/04/21/restaurant-template/"><strong>back to the Codrops tutorial</strong></a>
    <a href="http://www.flickr.com/photos/krossbow/" target="_blank">and krossbow</a>
    <a href="http://www.flickr.com/people/avlxyz/" target="_blank">Photography by avlxyz</a> -->
    <p>${message}</p>
    <c:if test="${param.error != null}">
        <p>Invalid username or password</p>
    </c:if>
</div>
<!-- The JavaScript -->
<!-- the mousewheel plugin - optional to provide mousewheel support -->
<script type="text/javascript" src="<c:url value="/resources/static/js/jquery.mousewheel.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/static/js/jquery.template.min.js" />"></script>
<script>
    function changeRegLog() {
        if(document.getElementById('login_div').style.display == '') {
            document.getElementById('login_div').style.display = 'block';
        }
        if(document.getElementById('login_div').style.display == 'block') {
            document.getElementById('login_div').style.display = 'none';
            document.getElementById('registration_div').style.display = 'block';
        } else {
            document.getElementById('login_div').style.display = 'block';
            document.getElementById('registration_div').style.display = 'none';
        }
    }

    $(function () {
        $(".msg-box").delay(3000).animate({opacity:'0'}, 3000,
            function()
            {
                $(this).css({display:'none'});
            });
    });

    function setStar() {
        document.getElementById('star-0').disabled=true;
        document.getElementById('star-1').disabled=true;
        document.getElementById('star-2').disabled=true;
        document.getElementById('star-3').disabled=true;
        document.getElementById('star-4').disabled=true;
    }

    $(document).ready(function() {
        $('#star-0').click(function (event) {
            //setStar();
            $.get('rating', {
                userId : $('#userId').val(),
                restaurantId : $('#restaurantId').val(),
                rating : 0
            }, function(responseText) {
                $('#msg-box').text('Rating set ' + 0);
            });
        })
    });

    function sendRating(restaurantId, rating) {
        $.get('rating', {
            restaurantId : restaurantId,
            rating : rating
        }, function (responseText) {
            $('#msg-box').text('Rating set ' + rating);
        });
    }
</script>
</body>
</html>