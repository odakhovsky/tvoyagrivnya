<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <form action="/cabinet/control/categories/" method="POST" role="form" class="categories-form-user">
        <input type="hidden" name="operation" value="${minus}">
        <input type="hidden" name="categoryId" id="admin-category-minus-id">

        <div class="form-group">
            <input type="text" id="admin-category-minus-name" name="name" class="form-control" id="category-name"
                   placeholder="Назва категорії"
                   required>
        </div>

        <div class="form-group">
            <select id="cat-minus-selec" name="parent" class="form-control">
                <option value="0">-----------</option>
                <c:forEach var="c" items="${categoriesMinus}">
                    <option value="${c.id}">
                        <c:if test="${not empty c.parentName}">(${c.parentName})</c:if> ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <button id="minus-submit" type="submit" class="btn btn-sm btn-block">Додати категорію</button>
        </div>
        <div id="delete-minus-category" class="form-group hide">
            <span id="delete-minus-category-btn" onclick="deleteCategory()" class="btn btn-sm btn-block btn-danger">Видалити категорію</span>
        </div>
    </form>
</div>
<div class="row margin-top-15">
    <div id="minus-tree" class="categories-block-user"></div>
</div>

<script>
    var item = 0;
    $(document).ready(function () {

        function getTree(type) {
            var result;
            $.ajax({
                url: '/cabinet/control/categories/user/${userBean.id}/type/' + type + '/list',
                async: false,
                dataType: 'json',
                success: function (data) {
                    result = data;
                }
            });
            return result;
        }

        $('#minus-tree').treeview({
            data: getTree("minus"),
            onNodeSelected: function (event, data) {
                if (data.state.edited || data.state.removed) {
                    $("#admin-category-minus-id").val(data.bean.id);
                    $("#admin-category-minus-name").val(data.bean.name);
                    $("#minus-submit").text('Оновити категорію');
                    $("#delete-minus-category").removeClass('hide');
                    item = data.bean.id;
                    if (data.bean.parent > 0) {
                        $("#cat-minus-selec").val(data.bean.parent).trigger("change");
                    } else {
                        $("#cat-minus-selec").val(0).trigger("change");
                    }
                } else {
                    item = 0;
                    var $deleteBtn = $("#delete-minus-category");
                    $("#admin-category-minus-id").val(0);
                    $("#admin-category-minus-name").val('');
                    $("#cat-minus-selec").val(0).trigger("change");
                    $("#minus-submit").text('Додати категорію');
                    if (!$deleteBtn.hasClass('hide')) {
                        $deleteBtn.addClass('hide');
                    }
                }
            },
            showTags: true,
            highlightSelected: true
        });


        $("#cat-minus-selec").select2();

    });
    function deleteCategory() {
        $.confirm({
            text: "Видалення категорії призвиде до видалення  всих під категорій, а також операцій по даній категорії та під категоріях?",
            title: "Підтвердження видалення",
            confirm: function () {
                console.log(item);
                $.ajax({
                    type: "POST",
                    url: "/cabinet/control/categories/" + item + "/delete",
                    success: function (data) {
                        location.reload();
                    }
                });
            },
            cancel: function (button) {
            },
            confirmButton: "Так, видаляємо",
            cancelButton: "Ні, відмовляюсь",
            post: true,
            confirmButtonClass: "btn-danger",
            cancelButtonClass: "btn-hide",
            dialogClass: "modal-dialog modal-lg"
        });
    }
</script>
