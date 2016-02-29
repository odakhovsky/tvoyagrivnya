<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="func" uri="http://com.tvoyagryvnia.util/functions" %>

<ul id="analysis-tree" class="tree">
    <li>
        ${line.name}

        <c:if test="${showDiff}">
            <c:set var="dif" value="${line.diff}"/>
            <c:set var="showDiff" value="true" scope="request"/>
            <c:if test="${not empty dif}">
                <c:choose>
                    <c:when test="${dif > 0}">
                        <span  style="color:green">${func:cut(func:absolute(dif),2)}%</span>
                    </c:when>
                    <c:otherwise>
                        <span  style="color:red">${func:cut(func:absolute(dif),2)}%</span>
                    </c:otherwise>
                </c:choose>
            </c:if>

        </c:if>

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

            </c:otherwise>
        </c:choose>



        <c:forEach var="line" items="${line.sublines}">
            <c:set var="line" value="${line}" scope="request"/>
            <jsp:include page="extended-analysis-sub.jsp"/>
        </c:forEach>
    </li>
</ul>
