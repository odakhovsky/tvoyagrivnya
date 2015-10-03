<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p align="center" ><a href="/cabinet/accounts/accmanage/">Управління рахунками</a></p>

<div class="list-group" data-bind="foreach: accounts">
    <div class="list-group-item">
        <a data-bind="text:name, attr:{href:$root.url(id)}"></a>
        <span class="pull-right text-bold margin-left-5" data-bind="text:currency"></span>
        <span class="pull-right " data-bind="text:total"></span>
    </div>
</div>
<div class="list-group-item  total-acc-balance">
    <span>Загалом</span>
    <span class="pull-right text-bold margin-left-5" data-bind="text:currency"></span>
    <span class="pull-right " data-bind="text:totalCount"></span>
</div>
<script type="text/javascript" data-main="/resources/js/accView.js" src="/resources/js/libs/require.js"></script>

