<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well ">
    <div class="row padding-25">
        <h3 align="center">Рахунок #${account.id}(${account.name})</h3>

        <div class="form-group">
            Назва рахунку
        <span>
            <a href="#"
               class="editable editable-container editable-inline"
               data-type="text"
               data-pk="${account.id}"
               data-name="name"
               data-url="/cabinet/accounts/editField"
               data-title="Назва рахунку">${account.name}</a>
        </span>
        </div>
        <div class="form-group">
            Опис рахунку
            <span>
                <a href="#"
                   class="editable editable-container editable-inline"
                   data-type="textarea"
                   data-pk="${account.id}"
                   data-name="name"
                   data-url="/cabinet/accounts/editField"
                   data-title="Опис рахунку">${account.description}</a>
            </span>
        </div>
    </div>
    <div class="row padding-25">
        <h4 align="center">Баланс</h4>
        <c:forEach items="${account.balances}" var="balance">
            <div class="badge">
                <div class="col-lg-1 text-center">${balance.currShort}</div>
                <div class="col-lg-1 text-center">${balance.balance}</div>
            </div>
        </c:forEach>
    </div>

    <div class="row padding-25">
        <h4 align="center">Останні 30 операцій по рахунку</h4>
        <c:forEach items="${operations}" var="operation">
        <c:choose>
        <c:when test="${operation.type eq 'plus'}">
        <div class="col-lg-12 alert alert-success account-operation-line"></c:when>
            <c:otherwise>
            <c:choose>
            <c:when test="${operation.type eq 'minus'}">
            <div class="col-lg-12 alert alert-danger account-operation-line">
                </c:when>
                <c:otherwise>
                <div class="col-lg-12 alert alert-info account-operation-line">
                    </c:otherwise>
                    </c:choose>
                    </c:otherwise>
                    </c:choose>
                    <div class="col-lg-2">
                        <span>${operation.date}</span>
                    </div>
                    <c:choose>
                        <c:when test="${operation.type eq 'transfer'}">
                            <div class="col-lg-3 truncate-note">
                                <span>${operation.money}</span>
                                <span>${operation.currency.shortName}</span>
                                <c:choose>
                                    <c:when test="${operation.category eq 'Перевод'}">
                                        <i class="fa fa-arrow-circle-o-right"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fa fa-arrow-circle-o-left"></i>
                                        <i class="fa fa-arrow-circle-o-right"></i>
                                    </c:otherwise>
                                </c:choose>
                                <span>${operation.moneyTo}</span>
                                <span>${operation.currencyTo.shortName}</span>
                            </div>
                            <div class="col-lg-5 truncate-note">
                                <span>${operation.account.name}</span>
                                <c:choose>
                                    <c:when test="${operation.category eq 'Перевод'}">
                                        <i class="fa fa-arrow-circle-o-right"></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fa fa-arrow-circle-o-left"></i>
                                        <i class="fa fa-arrow-circle-o-right"></i>
                                    </c:otherwise>
                                </c:choose>
                                <span>${operation.accountTo.name}</span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-lg-2">
                                <span>${operation.money}</span>

                                <span>${operation.currency.shortName}</span>
                            </div>
                            <div class="col-lg-4">
                                <span>${operation.category}</span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                        <c:choose>
                            <c:when test="${operation.type eq 'transfer'}">
                                <div class="col-lg-2 truncate-note-transfer " title="${operation.note}">
                                    <span>${operation.note}</span>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col-lg-2 truncate-note " title="${operation.note}">
                                    <span>${operation.note}</span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                </div>
                </c:forEach>
            </div>

        </div>
    </div>
</div>
<script>
    $('.editable').editable({placement: 'down'});

</script>