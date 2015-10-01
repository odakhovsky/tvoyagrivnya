<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="row">
    <div class="col-xs-4">
        <form action="/admin/currency/" method="POST" class="form-inline col-lg-12">
            <div class="form-group">
                <input name="fullname" class="form-control" type="text" placeholder="Повна назва: Українські гривні" required>
                <input name="name" class="form-control margin-top-15" type="text" placeholder="Назва: Гривні" required>
                <input name="shortName" class="form-control margin-top-15" type="text" placeholder="Скорочення: грн | ₴" required>
                <input name="rate" class="form-control margin-top-15" type="number" placeholder="Курс до гривні: 1" required>
                <button class="btn btn-block btn-sm margin-top-15">Додати</button>
            </div>
        </form>
    </div>
    <div class="col-xs-8">

    </div>
</div>