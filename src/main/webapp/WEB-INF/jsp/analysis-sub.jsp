<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul id="analysis-tree" class="tree">
    <li>
        ${line.name}
            <c:choose>
                <c:when test="${line.sublines.size() == 1 && line.curr eq line.sublines.get(0).curr}">
                </c:when>
                <c:otherwise>

                    <c:choose>
                        <c:when test="${line.sublines.size() > 1}">
                            <span class="analysis-money" style="color:grey"> <b>:</b> ${line.money}</span>
                            <span class="analysis-curr">${line.curr}</span> <span class="analysis-percent">${line.percent}%</span>
                        </c:when>
                        <c:otherwise>
                            <span class="analysis-money"> <b>:</b> ${line.money}</span>
                            <span class="analysis-curr">${line.curr}</span> <span class="analysis-percent">${line.percent}%</span>
                        </c:otherwise>
                    </c:choose>
                <%--    <span class="analysis-money"> <b>:</b> ${line.money}</span>
                    <span class="analysis-curr">${line.curr}</span> <span class="analysis-percent">${line.percent}%</span>--%>
                </c:otherwise>
            </c:choose>

        <c:forEach var="line" items="${line.sublines}">
            <c:set var="line" value="${line}" scope="request"/>
            <jsp:include page="analysis-sub.jsp"/>
        </c:forEach>
    </li>
</ul>
