<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-2">
            <h4 align="center">Додати нову нотатку</h4>
            <form id="createnot" name="createnot" class="form-inline" action="/cabinet/organizer/" method="POST">
                <textarea id="text" name="text" class="form-control create-note-text" placeholder="Текст замітки"
                          required></textarea>
                <select id="categories" name="category" class="form-control">
                    <option value="0">Без категорії</option>
                    <c:forEach var="c" items="${cats}">
                        <option value="${c.id}">
                            <c:if test="${not empty c.parent}">(${c.parent.name})</c:if>
                                ${c.name}
                        </option>
                    </c:forEach>
                </select>
                <button class="btn pull-right margin-top-15" type="submit">Додати</button>
            </form>
        </div>
    </div>
    <div class="row margin-top-25 note-list">
        <c:forEach items="${notes}" var="n">
            <div class="col-lg-12 margin-top-15 note-item-border">
                <div class="col-lg-7 " title="${n.text}">
                    <span class="truncate-note-organizer note-text-size"><a href="#"
                                                                            class="editable editable-container editable-inline"
                                                                            data-type="textarea"
                                                                            data-pk="${n.id}"
                                                                            data-name="text"
                                                                            data-url="/cabinet/organizer/editField"
                                                                            data-title="Текст нотатки">${n.text}</a></span>
                </div>
                <div class="col-lg-2 ">
                    <a href="#" class="editable editable-container editable-inline"
                       data-type="select2"
                       data-pk="${n.id}"
                       data-name="category"
                       data-url="/cabinet/organizer/editField"
                       data-title="Категорія">${n.category}</a>
                </div>
                <div class="col-lg-2">
                    <span>${n.date}</span>
                </div>
                <div class="col-lg-1">
                    <a href="/cabinet/organizer/note/${n.id}/info/"><i class="fa fa-edit btn-cursor"></i></a>
                    <i onclick="remove(${n.id})" class="margin-left-5 fa fa-remove btn-cursor"></i>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    $("#categories").select2();
    function remove(id) {
        $.confirm({
            text: "Впевнені що бажаете видалити нотатку?",
            title: "Підтвердження видалення",
            confirm: function () {
                $.ajax({
                    type: "POST",
                    url: "/cabinet/organizer/note/" + id + "/remove",
                    success: function () {
                        location.reload();
                    }
                });
            },
            cancel: function (button) {
            },
            confirmButton: "Так, видалити",
            cancelButton: "Ні, відмовляюсь",
            post: true,
            confirmButtonClass: "btn-danger",
            cancelButtonClass: "btn-hide",
            dialogClass: "modal-dialog modal-lg"
        });
    }
</script>
<script src="/resources/js/organizer.js"></script>