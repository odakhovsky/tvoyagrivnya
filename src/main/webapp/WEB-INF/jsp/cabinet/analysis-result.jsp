<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <c:choose>
            <c:when test="${empty res}">
                <h1 align="center">Нема результатів</h1>
            </c:when>
            <c:otherwise>
                <h3 align="center">Витрати за період ${res.range}</h3>
                <c:forEach items="${res.categories}" var="c">
                    <h5 align="center">${c.name} (${c.money} ${c.curr}) Пріорітет ${c.value}</h5>
                    <div class="progress" style="color:black">
                        <div class="progress-bar" role="progressbar"
                             aria-valuenow="${c.percent}" aria-valuemin="0" aria-valuemax="100" style="width: ${c.percent}%; color: black; font-weight: bold">
                                ${c.percent}%
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

</div>