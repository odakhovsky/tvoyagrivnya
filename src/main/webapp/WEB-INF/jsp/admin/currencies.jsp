<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="row">
    <div class="col-xs-4">
        <form action="/admin/currency/" method="POST" class="form-inline col-lg-12">
            <div class="form-group">
                <input name="fullname" class="form-control" type="text" placeholder="Повна назва: Українські гривні"
                       required>
                <input name="name" class="form-control margin-top-15" type="text" placeholder="Назва: Гривні" required>
                <input name="shortName" class="form-control margin-top-15" type="text" placeholder="Скорочення: грн | ₴"
                       required>
                <input step="0.01" name="rate" class="form-control margin-top-15" type="number"
                       placeholder="Курс до гривні: 1" required>
                <button class="btn btn-block btn-sm margin-top-15">Додати</button>
            </div>
        </form>
    </div>
    <div class="col-xs-8">
        <div class="list-group col-lg-12">
            <div class="col-lg-3">Повна назва</div>
            <div class="col-lg-3">Назва</div>
            <div class="col-lg-3">Скорочення</div>
            <div class="col-lg-3">Курс гривні до валюти</div>
            <c:forEach items="${currencies}" var="c">
                <div class="list-group-item col-lg-12">
                    <div class="col-sm-3"><span> <a href="#"
                                                    class="editable editable-container editable-inline"
                                                    data-type="text"
                                                    data-pk="${c.id}"
                                                    data-name="name"
                                                    data-url="/admin/currency/editField"
                                                    data-title="Повна назва">
                            ${c.name}</a></span></div>
                    <div class="col-sm-3"><span> <a href="#"
                                                    class="editable editable-container editable-inline"
                                                    data-type="text"
                                                    data-pk="${c.id}"
                                                    data-name="currency"
                                                    data-url="/admin/currency/editField"
                                                    data-title="Назва">
                            ${c.currency}</a></span></div>
                    <div class="col-sm-3"><span> <a href="#"
                                                    class="editable editable-container editable-inline"
                                                    data-type="text"
                                                    data-pk="${c.id}"
                                                    data-name="shortName"
                                                    data-url="/admin/currency/editField"
                                                    data-title="Скорочення">
                            ${c.shortName}</a></span></div>
                    <div class="col-sm-3"><span> <a href="#"
                                                    class="editable editable-container editable-inline"
                                                    data-type="number"
                                                    data-step="0.01"
                                                    data-pk="${c.id}"
                                                    data-name="crossRate"
                                                    data-url="/admin/currency/editField"
                                                    data-title="Курс гриві до валюти">
                            ${c.crossRate}</a></span></div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script>
    $('.editable').editable({
            placement: 'left'});
</script>