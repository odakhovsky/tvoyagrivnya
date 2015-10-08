<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row margin-top-15">
    <c:forEach var="line" items="${budget.incomes}">
        <div class="result-list col-lg-12 text-center">
            <div class="col-lg-4">
                <span> ${line.category}</span>
            </div>
            <div class="col-lg-2" style="background-color: mediumseagreen">
                <span> ${line.factMoney}</span>
            </div>
            <div class="col-lg-2" style="background-color: mediumseagreen">
                <span> ${line.line.money}</span>
            </div>
            <div class="col-lg-2" style="background-color: mediumseagreen">
                <span> ${line.diff}</span>
            </div>
        </div>
    </c:forEach>
    <div class="result-list col-lg-12 text-center" >
        <div class="col-lg-4">
            <span> Підсумок доходів</span>
        </div>
        <div class="col-lg-2">
            <span>${budget.incomesTotal.fact}</span>
        </div>
        <div class="col-lg-2">
            <span> ${budget.incomesTotal.budget}</span>
        </div>
        <div class="col-lg-2">
            <span> ${budget.incomesTotal.diff}</span>
        </div>
    </div>
</div>