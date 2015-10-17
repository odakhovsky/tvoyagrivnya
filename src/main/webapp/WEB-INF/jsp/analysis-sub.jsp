<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul id="analysis-tree" class="tree">
    <li>
        ${line.name} <span class="analysis-money"> <b>-</b> ${line.money}</span>
        <span class="analysis-curr">${line.curr}</span> <span class="analysis-percent">${line.percent}%</span>

        <c:forEach var="line" items="${line.sublines}">
            <c:set var="line" value="${line}" scope="request"/>
            <jsp:include page="analysis-sub.jsp"/>
        </c:forEach>
    </li>
</ul>
