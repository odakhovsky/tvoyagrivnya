<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="row">
    <ul class="nav nav-pills">
        <li role="presentation"><a href="#">Члени сім'ї</a></li>
        <li role="presentation"><a href="#">Категорії</a></li>
        <li role="presentation"><a href="#">Валюти</a></li>
    </ul>
</div>
<div class="row">
    <div class="invite-form">
        <c:set var="tooltip" value="${TOOL_TIP_ROLE}"/>
        <form:form action="/cabinet/control/invite-new-user" method="post" class="form-inline" commandName="newUser">
            <div class="form-group">
                <form:input type="email" class="form-control" path="email" required="required" placeholder="Пошта"/>
            </div>
            <div class="form-group margin-left-5">
                <form:input type="text" class="form-control" path="name" required="required"
                            placeholder="Ім'я"></form:input>
            </div>
            <div class="checkbox margin-left-5">
                <label><form:checkbox path="superMember"></form:checkbox>Додаткові повноваження </label>
               <span class="glyphicon glyphicon-question-sign InfoTooltip pull-right" data-toggle="tooltip"
                     title="${tooltip}"></span>
            </div>
            <button type="submit" class="btn btn-default margin-left-5">Додати нового члена</button>
            <div class="form-group">
                <c:if test="${not empty error}"><span class="label label-danger">${error}</span> </c:if>
            </div>
        </form:form>
    </div>
    <div class="invite-members-list">
        <h3 align="center">Члени сім`ї</h3>

        <div class="list-group col-lg-12">
            <div class="col-lg-4">Ім`я</div>
            <div class="col-lg-4">Пошта</div>
            <div class="col-lg-4">Додаткові повноваження</div>
            <c:forEach var="member" items="${userMembers}">
                <div class="list-group-item col-lg-12">
                    <div class="col-sm-4"><span> <a href="#"
                                                    class="editable editable-container editable-inline"
                                                    data-type="text"
                                                    data-pk="${member.id}"
                                                    data-name="name"
                                                    data-url="/cabinet/control/user/editField"
                                                    data-title="Enter name">
                            ${member.name}</a></span></div>
                    <div class="col-sm-4"><span>${member.email}</span></div>
                    <div class="col-sm-4">
                        <c:choose>
                            <c:when test="${member.superMember}">
                                <form method="post"  action="/cabinet/control/user/${member.id}/superMember">
                                    <input type="hidden" name="value" value="false">
                                    <button  type="submit" onclick="return confirm('Ви впевнені?')" class="btn btn-sm">Отключити</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form method="post" action="/cabinet/control/user/${member.id}/superMember">
                                    <input type="hidden" name="value" value="true">
                                    <button  type="submit" onclick="return confirm('Ви впевнені?')" class="btn btn-sm">Включити</button>
                                </form>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script>
    $(".InfoTooltip").tooltip({placement: "right", html: true});
    $('.editable').editable();

</script>