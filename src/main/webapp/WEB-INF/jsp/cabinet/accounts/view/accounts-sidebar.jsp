<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="func" uri="http://com.tvoyagryvnia.util/functions" %>
<p align="center"><a href="/cabinet/accounts/accmanage/">Управління рахунками</a></p>

<div class="list-group-item  total-acc-balance " style="margin-bottom: 5px; border-radius: 15px">
    <span>Загалом</span>
    <span class="pull-right text-bold ">${func:totalMoneyByAccounts(accounts)} ${currency}</span>
</div>

<table id="sidebar-accounts-list" class="">
    <c:forEach var="a" items="${accounts}">
        <tr class="list-group">
            <td width="260px">
                <div class="list-group">
                    <div class="list-group-item">
                        <a href="/cabinet/accounts/accmanage/${a.id}/info/"> ${a.name}</a>
                        <span class="pull-right text-bold margin-left-5">${a.currency}</span>
                        <span class="pull-right">${a.totalBalance}</span>
                    </div>
                </div>
            </td>
        </tr>
        <c:set var="currency" value="${a.currency}"/>
    </c:forEach>
</table>
<script>
    $(document).ready(function () {
        $("#sidebar-accounts-list").paging({
            limit: 3,
            rowDisplayStyle: 'block',
            activePage: 0,
            rows: []

        });
    });
</script>
