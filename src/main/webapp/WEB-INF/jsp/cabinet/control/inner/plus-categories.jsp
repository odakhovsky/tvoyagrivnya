<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <form action="/cabinet/control/categories/" method="POST" role="form" class="categories-form-user">
        <input type="hidden" name="operation" value="${plus}">
        <input type="hidden" name="categoryId" id="admin-category-plus-id">

        <div class="form-group">
            <input type="text" id="admin-category-plus-name" name="name" class="form-control" id="category-name"
                   placeholder="Назва категорії"
                   required>
        </div>
        <div class="form-group">
            <select id="cat-plus-sel" name="parent" class="form-control">
                <option value="0">-----------</option>
                <c:forEach var="c" items="${categoriesPlus}">
                    <option value="${c.id}">
                        <c:if test="${not empty c.parentName}">(${c.parentName})</c:if> ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <button id="plus-submit" type="submit" class="btn btn-sm btn-block">Додати категорію</button>
        </div>
        <div id="delete-btn-div" class="form-group hide">
            <span id="delete-category-btn" onclick="deletePlusCategory()" class="btn btn-sm btn-block btn-danger">Видалити категорію</span>
        </div>
    </form>
</div>
<div class="row margin-top-15">
    <div id="plus-tree" class="categories-block-user"></div>
</div>
<script>
    var item = 0;
    $(document).ready(function () {
        $("#cat-plus-sel").select2();

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

        $('#plus-tree').treeview({
            data: getTree('plus'),
            onNodeSelected: function (event, data) {
                if (data.state.edited || data.state.removed) {
                    $("#admin-category-plus-id").val(data.bean.id);
                    $("#admin-category-plus-name").val(data.bean.name);
                    $("#plus-submit").text('Оновити категорію');
                    $("#delete-btn-div").removeClass('hide');
                    item = data.bean.id;
                    if (data.bean.parent > 0) {
                        $("#cat-plus-sel").val(data.bean.parent).trigger("change");
                    } else {
                        $("#cat-plus-sel").val(0).trigger("change");
                    }
                } else {
                    item = 0;
                    var $deleteBtn = $("#delete-btn-div");
                    $("#admin-category-plus-id").val(0);
                    $("#admin-category-plus-name").val('');
                    $("#cat-plus-sel").val(0).trigger("change");
                    $("#plus-submit").text('Додати категорію');
                    if (!$deleteBtn.hasClass('hide')) {
                        $deleteBtn.addClass('hide');
                    }
                }
            },
            showTags: true,
            highlightSelected: true
        });
    });
    function deletePlusCategory() {
        $.confirm({
            text: "Видалення категорії призвиде до видалення  всих під категорій, а також операцій по даній категорії та під категоріях?",
            title: "Підтвердження видалення",
            confirm: function () {

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