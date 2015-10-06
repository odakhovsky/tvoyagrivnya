<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript" data-main="/resources/js/mainCabinetView.js" src="/resources/js/libs/require.js"></script>

<div class="well">
    <div class="row" id="simple-statistic">

        <div class="col-xs-6">
            <h4 align="center">Дохід </h4>
            <h3 align="center"  data-bind="text:incomings"></h3>
            <div class="center-block main-chart" id="income-chart"></div>
        </div>
        <div class="col-xs-6">
            <h4 align="center">Витрати</h4>
            <h3 align="center" data-bind="text:spending"></h3>
            <div class="center-block main-chart" id="spending-chart"></div>
        </div>
    </div>
</div>
