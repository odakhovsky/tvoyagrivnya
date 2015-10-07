<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form action="/cabinet/operations/update/" method="post"  id="new-operation-form" class="col-lg-12 operation-form" style="padding:35px">
    <div class="row form-inline">
        <div class="col-lg-4">
            <input name="date" type='text'
                   id="date"
                   value="${operation.date}"
                   class="form-control"
                   placeholder="YYYY.MM.DD"/>
        </div>
        <div class="col-lg-4">
            <input type="hidden" name="operationId" value="${operation.id}">
        </div>
        <div class="col-lg-8">
            <input value="${operation.note}" id="description" type="text" name="description" class="form-control " placeholder="Примітка до операції"/>
        </div>
    </div>
    <div class="row margin-top-15">

        <div class="col-lg-3">
            <input value="${operation.money}" id="money" name="money" type="number" class="form-control" min="0" step="0.01" placeholder="Сума"/>
        </div>
        <div class="col-lg-9">
            <select  id="currencies" name="currency" class="form-control">
                <c:forEach var="curr" items="${currencies}">
                    <option  value="${curr.id}"
                            <c:if test="${operation.currency.id eq curr.id}"> selected="selected"</c:if>
                            >${curr.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="row margin-top-15">
        <div class="col-lg-4">
            <select name="accounts" id="accounts" class="form-control" required>
                <c:forEach var="acc" items="${accounts}">
                    <option value="${acc.id}"
                            <c:if test="${operation.account.id eq acc.id}"> selected="selected"</c:if>
                            >${acc.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="col-lg-8">
            <select name="category"   id="cetegories"  class="category-list-style">
                <c:forEach items="${categories}" var="cat">
                    <option value="${cat.id}"
                    <c:if test="${operation.categoryId eq cat.id}"> selected="selected"</c:if>
                    ><c:if test="${not empty cat.parentName}"> |${cat.parentName}|</c:if>${cat.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="row margin-top-15">
        <div class="col-lg-12">
            <button type="submit" class="btn btn-block">Зберегти</button>
        </div>
    </div>
</form>
<script>
    $('#date').datetimepicker({
        format: 'YYYY.MM.DD',
    })

    $(document).ready(function(){
        $("#cetegories").select2();

        $("#new-operation-form").validate({
            rules: {
                date: {
                    required: true
                },
                money:{
                    required: true,
                    min:0.01
                },
                accounts:{
                    required:true
                }
            },

            messages: {
                date: {
                    required:"Обов'язкове поле"
                },
                money: {
                    required:"Обов'язкове поле",
                    min:"Мін к-сть {0}"
                },
                accounts:{
                    required:"Обов'язкове поле",
                }
            },

            ignore: ':hidden:not(.chzn-done)',

            errorElement: "label",
            errorClass: "errorMessage",
            validClass: "success",
            highlight: function (element, errorClass, validClass) {
                $(element).addClass(errorClass).removeClass(validClass);
                $(element.form).find("label[for=" + element.id + "]")
                        .addClass(errorClass);
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass(errorClass).addClass(validClass);
                $(element.form).find("label[for=" + element.id + "]")
                        .removeClass(errorClass);
            }
        });
    })
</script>
