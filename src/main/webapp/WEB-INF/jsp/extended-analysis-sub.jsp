<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul id="analysis-tree" class="tree">
    <li>
        <c:if test="${showDiff}">
            <c:set var="dif" value="${line.diff}"/>
            <c:set var="showDiff" value="true" scope="request"/>
            <c:if test="${not empty dif}">
                <c:choose>
                    <c:when test="${dif > 0}">
                        <span  style="color:green">${dif}%</span>
                    </c:when>
                    <c:otherwise>
                        <span  style="color:red">${dif}%</span>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:if>
        ${line.name} <span class="analysis-money"> <b>-</b> ${line.money}</span>
        <span class="analysis-curr">${line.curr}</span> <span class="analysis-percent">${line.percent}%</span>


        <c:forEach var="line" items="${line.sublines}">
            <c:set var="line" value="${line}" scope="request"/>
            <jsp:include page="extended-analysis-sub.jsp"/>
        </c:forEach>
    </li>
</ul>
