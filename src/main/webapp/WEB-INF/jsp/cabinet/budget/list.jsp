<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="padding-25">
            <form action="/cabinet/budget/create/" method="post" class="form-inline col-lg-offset-2">
                Створити бюджет на період
                <div class="form-group">
                    <input id="date-from" name="date-range" class="form-control" type="text">
                </div>
                <div class="form-group">
                    <button class="btn">Створити</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row padding-25">

        <c:forEach var="bu" items="${budgets}">
            <div class="col-lg-5 col-lg-offset-3 alert alert-info">
                <div class="col-lg-9">
                    <a class="btn-cursor" onclick="showBudget(${bu.id})">${bu.name}</a>
                </div>
                <div class="col-lg-3">
                    <a href="/cabinet/budget/${bu.id}/edit/"><i class="fa fa-edit"></i></a>
                    <i class="btn-cursor fa fa-remove" onclick="remove(${bu.id})"></i>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${not empty budget}">
        <div class="list-group-item text-center">
            <span>Виконання бюджета <b>${budget.name} <small class="budget-small">(Суми вказані в ${budget.currency})</small></b></span>
        </div>
        <div class="list-group-item">
            <div class="result-list col-lg-12 text-center text-bold margin-bottom-10">
                <div class="col-lg-4">
                    <span> Категорія</span>
                </div>
                <div class="col-lg-2">
                    <span> По факту</span>
                </div>
                <div class="col-lg-2">
                    <span> Заплановано</span>
                </div>
                <div class="col-lg-2">
                    <span> Відхилення</span>
                </div>
            </div>
            <jsp:include page="line/incomes.jsp"/>
            <jsp:include page="line/spendings.jsp"/>

            <div class="row margin-top-15">
                <div class="result-list col-lg-12 text-center">
                    <div class="col-lg-4">
                        <span> <i class="text-bold">Підсумок залишок</i></span>
                    </div>
                    <div class="col-lg-2">
                        <span> ${budget.grandTotal.fact}</span>
                    </div>
                    <div class="col-lg-2">
                        <c:choose>
                            <c:when test="${(budget.grandTotal.budget eq 0) or (budget.grandTotal.budget gt 0)}">
                                <span style="color:green">${budget.grandTotal.budget}</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red">${budget.grandTotal.budget}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-lg-2">
                        <c:choose>
                            <c:when test="${(budget.grandTotal.diff eq 0) or (budget.grandTotal.diff gt 0)}">
                                <span style="color:green">${budget.grandTotal.diff}</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red">${budget.grandTotal.diff}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <button class="btn" onclick="exportXLS(${budget.id})">Експортувати у форматі XLS</button>
    </c:if>
</div>

<script>
    function exportXLS(id) {

        $.fileDownload("/cabinet/budget/getBudgetReportAsXls/" + id + "/", {
            httpMethod : 'POST',
        });
    }
    function showBudget(id) {
        window.location.replace('/cabinet/budget/?budgetId=' + id);
    }
    function remove(id) {
        $.confirm({
            text: "Впевнені що бажаете видалити бюджет?",
            title: "Підтвердження видалення",
            confirm: function () {
                $.ajax({
                    type: "POST",
                    url: "/cabinet/budget/" + id + "/remove/",
                    success: function () {
                        location.reload();
                    }
                });
            },
            cancel: function (button) {
            },
            confirmButton: "Так, видалити",
            cancelButton: "Ні, відмовляюсь",
            post: true,
            confirmButtonClass: "btn-danger",
            cancelButtonClass: "btn-hide",
            dialogClass: "modal-dialog modal-lg"
        });
    }
    initDateRange("#date-from");
</script>