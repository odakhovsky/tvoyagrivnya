<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row margin-top-15" >
    <c:forEach var="line" items="${budget.spending}">
        <div class="result-list col-lg-12 text-center">
            <div class="col-lg-4">
                <span> ${line.category}</span>
            </div>
            <div class="col-lg-2" style="background-color: indianred">
                <span> ${line.factMoney}</span>
            </div>
            <div class="col-lg-2" style="background-color: indianred">
                <span> ${line.line.money}</span>
            </div>
            <div class="col-lg-2" style="background-color: indianred">
                <span> ${line.diff}</span>
            </div>
        </div>
    </c:forEach>
    <div class="result-list col-lg-12 text-center" >
        <div class="col-lg-4">
            <span> Підсумок витрат</span>
        </div>
        <div class="col-lg-2">
            <span> ${budget.spendingTotal.fact}</span>
        </div>
        <div class="col-lg-2">
            <span> ${budget.spendingTotal.budget}</span>
        </div>
        <div class="col-lg-2">
            <span> ${budget.spendingTotal.diff}</span>
        </div>
    </div>
</div>