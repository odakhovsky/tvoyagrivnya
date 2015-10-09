<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="padding-25">
            <form action="/cabinet/budget/create/" method="post" class="form-inline">
                <input type="hidden" id="user-id" value="${userBean.id}">
                Тип
                <div class="form-group">
                    <select data-bind="value: $root.selectedType" class="form-control">
                        <option value="1">Всі</option>
                        <option value="plus">Прибутки</option>
                        <option value="minus">Витрати</option>
                    </select>
                </div>
                Період
                <div class="form-group">
                    <input data-bind="value:$root.selectedRange" id="date-from" name="date-range" class="form-control"
                           type="text">
                </div>
                Категорії
                <div class="form-group">
                    <div class="col-lg-5">
                        <select style="width: 200px" id="cetegories" class="form-control" data-bind="options: $root.viewCategories,
                       optionsText: function (item) { return item.parentName + ' | ' + item.name },
                       value: $root.selectedCategory"></select>
                    </div>
                </div>
                <div class="form-group" style="width: 50px">
                    <button data-bind="event{click:$root.report}" class="btn">Сформувати</button>
                </div>
            </form>
        </div>
    </div>
    <div class="row" data-bind="visible:visibleblock">
        <div class="row" id="simple-statistic">

            <div class="col-xs-6">
                <h4 align="center">Дохід </h4>
                <h3 align="center" data-bind="text:$root.incomings"></h3>
                <div class="center-block main-chart" id="income-report"></div>
            </div>
            <div class="col-xs-6">
                <h4 align="center">Витрати</h4>
                <h3 align="center" data-bind="text:$root.spending"></h3>
                <div class="center-block main-chart" id="spending-report"></div>

            </div>
        </div>
        <div class="operations">
            <div >
                <table id="operation-list" class="table">
                    <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Сума</th>
                        <th>Валюта</th>
                        <th class="text-center">Категорія</th>
                    </tr>
                    </thead>
                    <tbody data-bind="foreach: $root.operations">

                    <tr data-bind="css: { 'account-operation-line':true, 'alert' :  true, 'alert-success' :  type == 'plus','label-danger': type=='minus'}">

                        <td><span data-bind="text:date"></span></td>
                        <td><span data-bind="text:money"></span></td>
                        <td><span data-bind="text:currency.shortName"></span></td>
                        <td class="text-center"><span data-bind="text:category"></span></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    $("#cetegories").select2();
    initDateRange("#date-from", true);

</script>
<script type="text/javascript" data-main="/resources/js/reportsView.js" src="/resources/js/libs/require.js"></script>
