<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<link href="<c:url value="/resources/css/tree.css" />" rel="stylesheet">
<div class="well">
    <div class="row">
        <c:choose>
            <c:when test="${empty res}">
                <h1 align="center">Нема результатів</h1>
            </c:when>
            <c:otherwise>
                <h3 align="center">Витрати за період ${res.range}</h3>

                <h2 align="center"> Загалом на <span style="color:green">${res.total}</span> ${res.curr}</h2>

                <div class="center-block">
                    <c:forEach items="${res.categories}" var="line">
                        <c:set var="line" value="${line}" scope="request"/>
                        <jsp:include page="../analysis-sub.jsp"></jsp:include>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script type="text/javascript" src="/resources/js/tree.js"></script>
