<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="row">
    <jsp:include page="menu.jsp"/>
</div>
<div class="row">
    <div class="col-lg-10 col-lg-offset-2 margin-top-15">
        <form action="/cabinet/control/currencies/default" method="post">
            <div class="col-lg-3">
                <label class="text-center">Основна валюта</label>
            </div>
            <div class="col-lg-4">
                <select name="curr" class="form-control">
                    <c:forEach items="${currs}" var="c">
                        <option value="${c.id}" <c:if test="${c.def}">selected="selected"</c:if>>${c.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-lg-4">
                <button type="submit" class="btn btn-sm">Прийняти</button>
            </div>
        </form>
    </div>
    <div class="currencies-list">
        <div class="list-group col-lg-12 margin-top-15 text-center">
            <div class="col-lg-3">Повна назва</div>
            <div class="col-lg-2">Назва</div>
            <div class="col-lg-2">Скорочення</div>
            <div class="col-lg-2">Курс до баз.</div>
            <div class="col-lg-3">Дата оновлення</div>
            <c:forEach items="${currs}" var="c">
                <div class="list-group-item col-lg-12 <c:if test="${c.def}">alert alert-info text-bold</c:if>">
                    <div class="col-sm-3">
                        <span>
                                ${c.name}
                        </span>
                    </div>
                    <div class="col-sm-3">
                        <span>
                                ${c.currency}
                        </span>
                    </div>
                    <div class="col-sm-1">
                        <span>
                                ${c.shortName}
                        </span>
                    </div>
                    <div class="col-sm-2"><span>
                        <c:choose>
                            <c:when test="${not c.def}">
                                <a href="#"
                                   class="editable editable-container editable-inline"
                                   data-type="number"
                                   data-step="0.0001"
                                   data-pk="${c.id}"
                                   data-name="crossRate"
                                   data-url="/cabinet/control/currencies/editrate"
                                   data-title="Курс гриві до валюти">

                                        ${c.crossRate}</a>
                            </c:when>
                            <c:otherwise>
                                ${c.crossRate}
                            </c:otherwise>
                        </c:choose>

                    </span></div>
                    <div class="col-sm-3">
                        <span>
                            ${c.stringDate}
                        </span>
                    </div>
                </div>

            </c:forEach>
        </div>
    </div>
</div>
<script>
    $('.editable').editable({
        placement: 'left'
    });
</script>