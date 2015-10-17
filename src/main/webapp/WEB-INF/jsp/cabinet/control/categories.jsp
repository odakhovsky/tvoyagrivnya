<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
<div class="row">
    <jsp:include page="menu.jsp"/>
</div>
<div class="row">
    <div class="col-xs-6 ">
        <H4 align="center">Категорії доходів</H4>
        <jsp:include page="inner/plus-categories.jsp"/>
    </div>
    <div class="col-xs-6">
        <H4 align="center">Категорії витрат</H4>
        <jsp:include page="inner/minus-categories.jsp"/>
    </div>
</div>
<ul id="contextMenu" class="dropdown-menu" role="menu" style="display:none">
    <li><a tabindex="-1" href="#">Перейменувати</a></li>
    <li><a tabindex="-1" href="#">Змінити категорію</a></li>
</ul>
</div>