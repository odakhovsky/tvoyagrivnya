<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <tiles:insertAttribute name="header"/>
    <link href="<c:url value="/resources/css/grayscale.css" />" rel="stylesheet">
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

<!-- Navigation -->
<nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand page-scroll" href="#page-top">
                <i class="fa fa-money"></i> <span class="light">Твоя</span> Гривня
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
            <ul class="nav navbar-nav">
                <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <li>
                    <a class="page-scroll" href="#about">Про проект</a>
                </li>
                <li>
                    <a class="page-scroll" href="#download">Реєстрація</a>
                </li>
                <li>
                    <a class="page-scroll" href="#contact">Контакти</a>
                </li>
                <li>
                    <c:choose>
                        <c:when test="${not empty userBean}">
                            <a class="btn-cursor" href="/cabinet/">Мій кабінет</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn-cursor" id="signIn">Увійти</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Intro Header -->
<header class="intro">
    <div class="intro-body">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <h1 class="brand-heading">Твоя гривня</h1>

                    <p class="intro-text">Твій шанс здійснити мрії.
                    </p>
                    <a href="#about" class="btn btn-circle page-scroll">
                        <i class="fa fa-angle-double-down animated"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- About Section -->
<section id="about" class=" intro about container content-section ">
    <div class="col-lg-8 col-lg-offset-2">
        <h2 class="text-center" style="color: white">Ви хочете</h2>

        <p align="center">
        <ul class="no-bullets about-list text-center">
            <li> Точно знати, скільки заробляєте і витрачаєте?</li>
            <li> Запобігти непотрібні витрати?</li>
            <li> Привчити контролювати власні витрати своїх дітей</li>
            <li> Ви хочете завжди тримати під контролем власні фінанси!</li>

            <li style="margin-top: 15px">Почніть контролювати свої фінанси з <a href="#download"
                                                                                class="page-scroll"><span
                    style="color:yellow">Твоя гривня</span></a><br>
                <i class="fa fa-bank  animated"></i>Заводьте гаманці <i class="fa fa-bank  animated"></i><br>
                <i class="fa fa-exchange  animated"></i>Вносьте дані про доходи і витрати<i
                        class="fa fa-exchange  animated"></i><br>
                <i class="fa fa-money animated"></i>Система дозволяє працювати з декількома валютами<i
                        class="fa fa-money animated"></i>
            </li>
        </ul>
        </p>

    </div>
</section>

<!-- Download Section -->
<section id="download" class="intro registration content-section text-center">
    <div class="download-section">
        <div class="container">
            <div class="col-lg-8 col-lg-offset-2">
                <h2  style="color: white">Реєстрація</h2>

                <form action="/registration" method="POST" class= form-inline" id="registration-form">
                    <div class="col-lg-4">
                        <input id="reg-email" name="email" class="registration-input form-control" type="email"
                               placeholder="Ваша скринька"
                               required>
                    </div>
                    <div class="col-lg-5">
                        <input id="reg-name" name="name" class="registration-input form-control" type="text" placeholder="Ваше ім`я"
                               required>
                    </div>
                    <div class="col-lg-3">
                        <button type="submit" class="btn-reg btn btn-default btn-lg">
                            Підтвердити дані
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</section>

<!-- Contact Section -->
<section id="contact" class="feedback container content-section text-center">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 margin-bottom-105" >
            <h2 >Зворотній зв'язок</h2>

            <form  action="/feedback" method="POST" class="form-inline">
                <div class="col-xs-3">
                    <div class="form-group">
                        <input type="email" class="form-control" placeholder="Ваша пошта" required>
                        <input type="text" class="form-control margin-top-5" placeholder="Ваше ім`я" required>
                        <button type="submit" class="btn-reg btn btn-default  btn-block margin-top-5">Відправити</button>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="col-lg-3">
                        <textarea placeholder="Ваше повідомлення" class="registration-input feed-back-area"></textarea></div>
                </div>
            </form>
        </div>
    </div>
    </div>
</section>
<!-- Footer -->
<footer>
    <div class="container text-center">
        <p>&copy; Твоя Гривня 2015</p>
    </div>
</footer>
<jsp:include page="../main/registrationComfirn.jsp"/>
<jsp:include page="../main/signIn.jsp"/>
</body>
</html>
<script src="/resources/js/main/registration.js"></script>
