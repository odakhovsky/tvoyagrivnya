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
                <div class="row margin-top-15 ">
                    <div class="form-group">
                        <input value="0" type="number" name="sum" id="sum" placeholder="Сума" min="0"
                               class="form-group-sm form-control"
                               style="width: 100px; margin-left: 15px;">

                        <select id="currencies" name="currency" class="form-control">
                            <c:forEach var="c" items="${currs}">
                                <option value="${c.id}">
                                        ${c.shortName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <button class="btn pull-right " type="submit">Додати</button>

                </div>
                <div id="purpose-text"></div>
            </form>
        </div>
    </div>
    <div class="row margin-top-25 note-list">
        <c:forEach items="${notes}" var="n">
            <div class="col-lg-12 margin-top-15 note-item-border">
                <c:choose>
                    <c:when test="${n.sum > 0}">
                        <div class="col-lg-4 truncate-note-organizer-sum" title="${n.text}">
                            <span class="  ">${n.text}</span>
                        </div>
                        <div class="col-lg-2">
                                ${n.sum} ${n.currency}
                        </div>
                        <div class="col-lg-2 ">
                                ${n.category}
                        </div>
                        <div class="col-lg-2">
                            <span>${n.date}</span>
                        </div>

                        <div class="col-lg-1">
                            <a href="/cabinet/organizer/note/${n.id}/info/"><i class="fa fa-edit btn-cursor"></i></a>

                            <i onclick="removeNote(${n.id})" class="margin-left-5 fa fa-remove btn-cursor"></i>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-lg-7 truncate-note-organizer" title="${n.text}">
                            <span class="  ">${n.text}</span>
                        </div>
                        <div class="col-lg-2 ">
                                ${n.category}
                        </div>
                        <div class="col-lg-2">
                            <span>${n.date}</span>
                        </div>
                        <div class="col-lg-1">
                            <a href="/cabinet/organizer/note/${n.id}/info/"><i class="fa fa-edit btn-cursor"></i></a>
                            <i onclick="removeNote(${n.id})" class="margin-left-5 fa fa-remove btn-cursor"></i>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</div>

<script>

    function removeNote(id) {
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

    $("#currencies").on("change", function () {
        purpose.call(this);
    })

    function purpose() {
        if (this.value == '') {
            $("#purpose-text").html("").fadeOut("slow");
        } else if ($(this).val()) {
            getPurpose();
        }
    }
    $("#sum").bind('keyup input', function () {
        purpose.call(this);
    });

    function getPurpose() {

        var value = $("#sum").val();
        var currency = $("#currencies").val();

        if (value && value > 0 && currency) {
            $.ajax({
                url: "/getPurpose?sum=" + value + "&currency=" + currency,
                type: 'GET',
                dataType: "json",
                contentType: 'application/json',
                mimeType: 'application/json',
                async: true,
                success: function (result) {
                }, error: function (result) {
                    $("#purpose-text").html(result.responseText).fadeIn("slow");
                }
            });
        }
    }

    $("#categories").select2();

</script>
<script src="/resources/js/organizer.js"></script>
