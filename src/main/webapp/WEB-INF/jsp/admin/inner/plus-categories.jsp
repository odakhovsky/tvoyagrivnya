<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <form action="/admin/categories/" method="POST" role="form">
        <input type="hidden" name="operation" value="${plus}">
        <input type="hidden" name="id" id="admin-category-plus-id">

        <div class="col-lg-4">
            <input type="text" id="admin-category-plus-name" name="name" class="form-control" id="category-name"
                   placeholder="Назва категорії"
                   required>
        </div>
        <div class="col-lg-5">
            <select id="cat-plus-sel" name="parent" class="form-control">
                <option value="0">-----------</option>
                <c:forEach var="c" items="${categoriesPlus}">
                    <option value="${c.id}">
                        <c:if test="${not empty c.parentName}">(${c.parentName})</c:if> ${c.name}
                    </option>
                    </c:forEach>
            </select>
        </div>
        <div class="col-lg-2">
            <button id="plus-submit" type="submit" class="btn btn-sm">Додати категорію</button>
        </div>
    </form>
</div>
<div class="row margin-top-15">
    <div id="plus-tree" class="categories-block"></div>
</div>
<script>
    $(document).ready(function () {
        $("#cat-plus-sel").select2();

        var selectedItem = null;

        function getTree(type) {
            var result;
            $.ajax({
                url: '/admin/categories/' + type + '/list',
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
                $("#admin-category-plus-id").val(data.bean.id);
                $("#admin-category-plus-name").val(data.bean.name);
                $("#plus-submit").text('Оновити категорію');
                if (data.bean.parent > 0) {
                    $("#cat-plus-sel").val(data.bean.parent).trigger("change");
                }else{
                    $("#cat-plus-sel").val(0).trigger("change");
                }
            },
            showTags: true,
            highlightSelected: true
        });
    });

</script>