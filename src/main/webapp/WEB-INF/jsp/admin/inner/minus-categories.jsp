<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <form action="/admin/categories/" method="POST" role="form">
        <input type="hidden" name="operation" value="${minus}">
        <input type="hidden" name="id" id="admin-category-minus-id">

        <div class="col-lg-4">
            <input type="text" id="admin-category-minus-name" name="name" class="form-control" id="category-name"
                   placeholder="Назва категорії"
                   required>
        </div>

        <div class="col-lg-5">
            <select id="cat-minus-selec" name="parent" class="form-control">
                <option value="0">-----------</option>
                <c:forEach var="c" items="${categoriesMinus}">
                    <option value="${c.id}">
                        <c:if test="${not empty c.parentName}">(${c.parentName})</c:if> ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-lg-2">
            <button id="minus-submit" type="submit" class="btn btn-sm">Додати категорію</button>
        </div>
    </form>
</div>
<div class="row margin-top-15">
    <div id="minus-tree" class="categories-block"></div>
</div>

<script>
    $(document).ready(function () {

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

        $('#minus-tree').treeview({
            data: getTree("minus"),
            onNodeSelected: function (event, data) {
                $("#admin-category-minus-id").val(data.bean.id);
                $("#admin-category-minus-name").val(data.bean.name);
                $("#minus-submit").text('Оновити категорію');
                if (data.bean.parent > 0) {
                    $("#cat-minus-selec").val(data.bean.parent).trigger("change");
                }else{
                    $("#cat-minus-selec").val(0).trigger("change");
                }
            },
            showTags: true,
            highlightSelected: true
        });


        $("#cat-minus-selec").select2();

    });
</script>
