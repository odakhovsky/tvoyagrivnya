<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row invite-members-list">
        <a class="btn  btn-sm btn-primary" href="/cabinet/accounts/accmanage/">Мої рахунки</a>
        <form action="/cabinet/accounts/createAccount/"  method="POST">
            <div class="col-lg-4 col-lg-offset-4">
                <c:if test="${not empty error}">
                    <div class="form-group">
                        <span class="label label-danger">${error}</span>
                    </div>
                </c:if>
                <div class="form-group">
                    <input class="form-control" name="name" placeholder="Назва рахунку" required/>
                </div>
                <div class="form-group">
                    <textarea class="form-control add-account-description" name="description" placeholder="Примітка"
                              ></textarea>
                </div>
                <div class="form-group">
                    <button class="btn btn-block">Додати рахунок</button>
                </div>
            </div>
        </form>
    </div>
</div>
