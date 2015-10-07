<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="invite-members-list">
            <a class="btn  btn-sm btn-primary" href="/cabinet/accounts/createAccount/">Додати рахунок</a>
            <a class="btn  btn-sm btn-primary" href="/cabinet/accounts/exchange/">Обмін\перевод</a>

            <h3 align="center">Мої рахунки</h3>
            <table id="accounts">
                <c:forEach var="acc" items="${accounts}">
                    <tr class="col-lg-10 col-lg-offset-1 list-group-item margin-top-5">
                        <td>
                            <div class="col-sm-1"><span>#${acc.id}</span></div>
                            <div class="col-sm-7">
                                <p align="center"><a
                                        href="/cabinet/accounts/accmanage/${acc.id}/info/">${acc.name}</a></p></div>
                            <div class="col-sm-4">
                        <span>
                            <c:choose>
                                <c:when test="${acc.enabled}">
                                    <a class="btn-cursor btn-default btn btn-sm" onclick="deactivate(${acc.id})">Деактивувати</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn-cursor btn-default btn btn-sm" onclick="activate(${acc.id})">Активувати</a>
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <span class="pull-right">
                            <a class="btn-cursor" onclick="remove(${acc.id})"> <i class="fa fa-remove"></i> </a>
                        </span>
                            </div>
                            <c:forEach items="${acc.balances}" var="balance">
                                <div title="${balance.currFull}"
                                     class="margin-left-5 margin-top-15 label label-primary pull-left">
                                        ${balance.balance} <b>${balance.currShort}</b>
                                </div>
                            </c:forEach>

                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="row padding-25">
            <h4 align="center">Обміни по рахунках</h4>
            <table id="exchanges" class="col-lg-12" >
                <c:forEach items="${exchanges}" var="operation">
                <tbody>
                <tr class="alert alert-info ">
                    <td>
                        <div class="col-lg-2">
                            <span>${operation.date}</span>
                        </div>
                        <div class="col-lg-4">
                            <span>${operation.money}</span>
                            <span>${operation.currency.shortName}</span>
                            <c:choose>
                                <c:when test="${operation.category eq 'Перевод'}">
                                    <i class="fa fa-arrow-circle-o-right"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa fa-arrow-left"></i>
                                    <i class="fa fa-arrow-right"></i>
                                </c:otherwise>
                            </c:choose>
                            <span>${operation.moneyTo}</span>
                            <span>${operation.currencyTo.shortName}</span>
                        </div>

                        <div class="col-lg-4 truncate-note">
                        <span><a
                                href="/cabinet/accounts/accmanage/${operation.account.id}/info/">${operation.account.name}</a></span>
                            <c:choose>
                                <c:when test="${operation.category eq 'Перевод'}">
                                    <i class="fa fa-arrow-circle-o-right"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa fa-arrow-circle-o-left"></i>
                                    <i class="fa fa-arrow-circle-o-right"></i>
                                </c:otherwise>
                            </c:choose>
                        <span><a
                                href="/cabinet/accounts/accmanage/${operation.accountTo.id}/info/">${operation.accountTo.name}</a></span>
                        </div>
                        <div class="col-lg-2">
                            <span>${operation.category}</span>
                        </div>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>
</div>
</div>
</div>

<script>
    $(document).ready(function () {

        $("#accounts").paging({

            limit: 4,
            rowDisplayStyle: 'block',
            activePage: 0,
            rows: []

        });
    });
    function activate(id) {
        $.ajax({
            type: "POST",
            url: "/cabinet/accounts/" + id + "/activate",
            success: function () {
                location.reload();
            }
        });
    }
    function deactivate(id) {
        $.confirm({
            text: "Після деактивації рахунку, буде не можливо виконувати будь - які операції з цим рахунком?",
            title: "Підтвердження деактивації",
            confirm: function () {
                $.ajax({
                    type: "POST",
                    url: "/cabinet/accounts/" + id + "/deactivate",
                    success: function () {
                        location.reload();
                    }
                });
            },
            cancel: function (button) {
            },
            confirmButton: "Так, деактивувати",
            cancelButton: "Ні, відмовляюсь",
            post: true,
            confirmButtonClass: "btn-danger",
            cancelButtonClass: "btn-hide",
            dialogClass: "modal-dialog modal-lg"
        });
    }

    function remove(id) {
        $.confirm({
            text: "Після видалення рахунку, буде не можливо відновити данні по рахунку(прибутки, витрати, інше)?",
            title: "Підтвердження видалення",
            confirm: function () {
                $.ajax({
                    type: "POST",
                    url: "/cabinet/accounts/" + id + "/remove",
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
</script>