<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="func" uri="http://com.tvoyagryvnia.util/functions" %>
<p align="center"><a href="/cabinet/accounts/accmanage/">Управління рахунками</a></p>

<c:forEach var="a" items="${accounts}">
    <div class="list-group">
        <div class="list-group-item">
               <a href="/cabinet/accounts/accmanage/${a.id}/info/"> ${a.name}</a>
            <span class="pull-right text-bold margin-left-5">${a.currency}</span>
            <span class="pull-right">${a.totalBalance}</span>
        </div>
    </div>
    <c:set var="currency" value="${a.currency}"/>
</c:forEach>
<div class="list-group-item  total-acc-balance">
    <span>Загалом</span>
    <span class="pull-right text-bold margin-left-5">${currency}</span>
    <span class="pull-right ">${func:totalMoneyByAccounts(accounts)}</span>
</div>
