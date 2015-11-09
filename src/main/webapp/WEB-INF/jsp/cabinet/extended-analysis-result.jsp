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
                <div class="col-xs-6">
                    <h5 align="center"> Загалом на <span style="color:green">${res.first.total}</span> ${res.curr}</h5>
                    <c:forEach items="${res.first.categories}" var="line">
                        <c:set var="line" value="${line}" scope="request"/>
                        <jsp:include page="../extended-analysis-sub.jsp"></jsp:include>
                    </c:forEach>
                </div>
                <div class="col-xs-6">
                    <h5 align="center"> Загалом на <span style="color:green">${res.second.total}</span> ${res.curr}</h5>
                    <c:forEach items="${res.second.categories}" var="line">
                        <c:set var="line" value="${line}" scope="request"/>
                        <c:set var="showDiff" value="true" scope="request"/>
                        <jsp:include page="../extended-analysis-sub.jsp"></jsp:include>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script type="text/javascript" src="/resources/js/tree.js"></script>
