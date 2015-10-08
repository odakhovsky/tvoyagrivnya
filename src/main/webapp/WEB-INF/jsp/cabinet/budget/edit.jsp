<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <h4 align="center">Бюджет ${budget.name}</h4>
        <h4 align="center" style="margin-top:50px">Прибуток</h4>
        <form action="/cabinet/budget/${budget.id}/addline/" method="post" class="form-inline margin-top-15">
            <div class="col-md-10 col-md-offset-1">
                <div class="col-md-5">
                    <select name="categoryId" id="plus">
                        <c:forEach items="${plus}" var="category">
                            <option id="plus" value="${category.id}">
                                <c:if test="${not empty category.parentName}">(${category.parentName})</c:if>
                                    ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <input class="form-control" type="number" min="0" step="0.001" name="money"
                           placeholder="Сума прибутку"/>
                </div>
                <button class="btn">Додати</button>
            </div>
        </form>
        <c:forEach var="a" items="${budget.incomes}">
            <c:if test="${a.active eq true}">
                <div class="list-group col-lg-4  margin-top-5">
                    <div class="list-group-item padding-5 text-center">
                        <span class="text-bold">${a.categoryBean.name}</span>
                        <span class=" label label-success">${a.money}</span>
                        <a onclick="removeLine(${a.id})"><span class="pull-right"><i
                                class="fa fa-remove"></i> </span></a>
                    </div>
                </div>
            </c:if>
        </c:forEach>


        <h4 align="center" style="margin-top:100px">Витрати</h4>
        <form action="/cabinet/budget/${budget.id}/addline/" method="POST" class="form-inline">
            <div class="col-md-10 col-md-offset-1">
                <div class="col-md-5">
                    <select name="categoryId" id="minus">
                        <c:forEach items="${minus}" var="category">
                            <option id="plus" value="${category.id}">
                                <c:if test="${not empty category.parentName}">(${category.parentName})</c:if>
                                    ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <input class="form-control" type="number" min="0" step="0.001" name="money"
                           placeholder="Сума витрат"/>
                </div>
                <button class="btn">Додати</button>
            </div>
        </form>
        <c:forEach var="a" items="${budget.spending}">
            <c:if test="${a.active eq true}">
                <div class="list-group col-lg-4  margin-top-5">
                    <div class="list-group-item padding-5 text-center">
                        <span class="text-bold">${a.categoryBean.name}</span>
                        <span class="label label-danger">${a.money}</span>
                        <a onclick="removeLine(${a.id})"><span class="pull-right"><i
                                class="fa fa-remove"></i> </span></a>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>
<script>
    function removeLine(lineId) {
        $.post("/cabinet/budget/line/" + lineId + "/remove/", function () {
            location.reload();
        });
    }

    $("#plus").select2();
    $("#minus").select2();
</script>