<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="row well">

    <form:form id="edit-user-form" action="/cabinet/settings/user-edit" modelAttribute="profile">
        <form:input path="id" hidden="hidden"/>
        <fieldset class="main edit-user-form ">
            <div class="row fieldrow">
                <div class="col-md-3">
                    <label>Ім`я:</label>
                </div>
                <div class="col-md-4">
                    <form:input path="name" class="form-control input-sm" type="text" id="editusername"
                                placeholder="Ім`я" required="required"/>

                </div>
                <label for="editusername"></label>
                <form:errors path="name" cssClass="errorMessage"/>
            </div>


            <div class="row fieldrow margin-top-15">
                <div class="col-md-3">
                    <label>Рік народження:</label>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <form:input path="dateOfBirth" type='text'
                                    id="_date"
                                    class="form-control input-sm"
                                    placeholder="DD.MM.YYYY"/>

                    </div>
                </div>
                <form:errors path="dateOfBirth" cssClass="errorMessage"/>
                <label for="_date"></label>
            </div>

            <div class="row fieldrow">
                <div class="col-md-3">
                    <label>Пошта:</label>
                </div>
                <div class="col-md-4">
                    <form:input path="email" id="_email" class="form-control input-sm" type="text"
                                placeholder="@mail" block="true"/>
                </div>
                <form:errors path="email" cssClass="errorMessage"/>
                <label for="_email"></label>
                <span class="correct" id="uniqueEmail" style="font-size: 10px; color: red"></span>
            </div>
            <button type="submit" class="btn btn-sm center-block margin-top-5">
                Прийняти зміни
            </button>
        </fieldset>
    </form:form>

    <form:form id="edit-user-pass" action="/cabinet/settings/editPassword"
               modelAttribute="editPass" method="post" cssClass="edit-password-form col-lg-12">
        <div class="row fieldrow">
            <div class="col-md-3">
                <label>Пароль:</label>
            </div>
            <div class="col-md-4">
                <form:password path="password" class="form-control input-sm fieldrow" id="_password"
                               placeholder="введіть пароль" size="35" status="false"/>
            </div>
            <form:errors path="password" cssClass="error"/>
            <label for="_password"></label>
        </div>

        <div class="row fieldrow">
            <div class="col-md-3">
                <label>Повторити пароль:</label>
            </div>
            <div class="col-md-4">
                <form:password path="confirmPass" class="form-control input-sm fieldrow margin-top-15" id="_confirmPass"
                               placeholder="підтвердіть пароль" size="35" status="false"/>
            </div>
            <form:errors path="confirmPass" cssClass="error"/>
            <label for="_confirmPass"></label>
        </div>
        <div class="text-center">
            <input class="btn  btn-sm fieldrow margin-top-15" type="submit" value="Змінити пароль">
        </div>
    </form:form>
</div>
<script src="/resources/js/settings.js"></script>
