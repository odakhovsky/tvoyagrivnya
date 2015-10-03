<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
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
    <div class="row">
        <h4 align="center">Баланс</h4>
        <c:forEach items="${account.balances}" var="balance">
            <div class="badge">
                <div class="col-lg-1 text-center">${balance.currShort}</div>
                <div class="col-lg-1 text-center">${balance.balance}</div>
            </div>
        </c:forEach>
    </div>

    <div class="row">
        <h4 align="center">Останні 30 операцій по рахунку</h4>
    </div>

</div>
<script>
    $('.editable').editable({placement: 'down'});

</script>