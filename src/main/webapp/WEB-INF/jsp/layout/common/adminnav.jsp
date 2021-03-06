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
                    <a href="/admin/">Головна</a>
                </li>
                <li>
                    <a href="/admin/categories/">Категорії</a>
                </li>
                <li>
                    <a href="/admin/currency/">Валюти</a>
                </li>
                <li>
                    <a href="/admin/feedback/">Зворотній зв`язок</a>
                </li>
                <li class="pull-right">
                    <a onclick="logout()">Вихід</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>