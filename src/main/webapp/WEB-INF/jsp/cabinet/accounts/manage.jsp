<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="invite-members-list">
            <a class="btn  btn-sm btn-primary" href="/cabinet/accounts/createAccount/">Додати рахунок</a>

            <h3 align="center">Мої рахунки</h3>

            <c:forEach var="acc" items="${accounts}">
                <div class="list-group-item col-lg-12 margin-top-5">
                    <div class="col-sm-1"><span>#${acc.id}</span></div>
                    <div class="col-sm-8">
                        <p align="center"><a
                                href="/cabinet/accounts/accmanage/${acc.id}/info/">${acc.name}</a></p></div>
                    <div class="col-sm-3">
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

                </div>
            </c:forEach>
        </div>
    </div>
</div>
</div>
<script>
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