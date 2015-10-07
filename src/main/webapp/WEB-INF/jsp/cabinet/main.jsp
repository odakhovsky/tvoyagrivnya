<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript" data-main="/resources/js/mainCabinetView.js"
        src="/resources/js/libs/require.js"></script>

<div class="well">
    <div class="row" id="simple-statistic" data-bind="visible:visibleblock">

        <div class="col-xs-6" >
            <h4 align="center">Дохід </h4>

            <h3 align="center" data-bind="text:incomings"></h3>

            <div class="center-block main-chart" id="income-chart"></div>
        </div>
        <div class="col-xs-6">
            <h4 align="center">Витрати</h4>

            <h3 align="center" data-bind="text:spending"></h3>

            <div class="center-block main-chart" id="spending-chart"></div>
        </div>
    </div>

    <div class="row padding-25">
        <table id="operation-list" class="table">
            <thead>
            <tr>
                <th>Дата</th>
                <th>Сума</th>
                <th>Валюта</th>
                <th>Категорія</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${operations}" var="op">
                <c:choose>
                    <c:when test="${op.type eq 'plus'}">
                        <tr class="account-operation-line alert alert-success">
                    </c:when>
                    <c:otherwise>
                        <tr class="account-operation-line alert alert-danger">
                    </c:otherwise>
                </c:choose>
                <td>${op.date}</td>
                <td>${op.money}</td>
                <td>${op.currency.shortName}</td>
                <td>${op.category}</td>
                <td>
                    <a onclick="edit(${op.id})"><i class="fa fa-edit btn-cursor"></i></a>
                    <i class="margin-left-5 fa fa-remove btn-cursor"></i>
                </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <jsp:include page="modal/editoperation.jsp"/>
</div>
<script>
    var edit = function (operationId) {
        var $body = $("#edit-modal-body");
        $('#edit-operation-modal').modal('show');
        $.get("/cabinet/operations/" + operationId + "/edit/view/", function (data) {
            $body.html(data);
        });
    }
    $(document).ready(function () {

        $('#operation-list').paging({

            limit: 5,
            rowDisplayStyle: 'block',
            activePage: 0,
            rows: []

        });
    })

</script>