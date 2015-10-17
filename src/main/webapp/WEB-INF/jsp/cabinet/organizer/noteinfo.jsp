<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row padding-25">
        <a class="btn btn-primary" href="/cabinet/organizer/">Нотатки</a>

        <h3 align="center">Нотатка №${note.id}</h3>
        <div class="box">

            <form id="createnot" name="createnot" class="form-inline " action="/cabinet/organizer/edit" method="post">
                <input hidden="hidden" name="noteId" value="${note.id}"/>
                <textarea id="text" name="text" class="form-control create-note-text-edit" placeholder="Текст замітки"
                          required>${note.text}</textarea>

                <select id="categories" name="category" class="form-control">
                    <option value="0">Без категорії</option>
                    <c:forEach var="c" items="${cats}">
                        <option value="${c.id}" <c:if
                                test="${c.id eq note.categoryId}"> selected="selected"</c:if> >
                            <c:if test="${not empty c.parent}">(${c.parent.name})</c:if>
                                ${c.name}
                        </option>
                    </c:forEach>
                </select>
                <button class="btn pull-right margin-top-15">Оновити</button>
            </form>
        </div>
    </div>
</div>
<script>
    $("#categories").select2()
</script>