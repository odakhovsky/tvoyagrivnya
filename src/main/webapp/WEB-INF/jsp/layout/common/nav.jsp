<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="/">
                <i class="fa fa-money"></i> <span class="light">Твоя</span> Гривня
            </a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/cabinet/main/">Головна</a>
                </li>
                <li>
                    <a href="/cabinet/new-transaction/">Нова операція</a>
                </li>

                <li>
                    <a href="/cabinet/control/">Управління</a>
                </li>
                <li>
                    <a href="/cabinet/settings/">Налаштування</a>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li>
                        <a href="/admin/">Адмін панель</a>
                    </li>
                </sec:authorize>
                <li class="pull-right">
                    <a class="btn-cursor" onclick="logout()">Вихід</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>