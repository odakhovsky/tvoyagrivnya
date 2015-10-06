<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well" id="new-operation">
    <div class="row">
        <form action="/cabinet/operations/new/" method="post"  id="new-operation-form" class="col-lg-12 operation-form" style="padding:35px">
            <div class="row form-inline">
                <div class="col-lg-4">
                    Тип операції:
                    <select data-bind="value: $root.selectedType" name="operationtype" class="form-control">
                        <option value="plus">Дохід</option>
                        <option value="minus">Витрати</option>
                    </select>
                </div>
                <div class="col-lg-6">
                    Дата:
                    <input name="date" type='text'
                           id="date"
                           class="form-control"
                           placeholder="DD.MM.YYYY"/>
                </div>
            </div>
            <div class="row margin-top-15">
                <div class="col-lg-5">
                    <input id="description" type="text" name="description" class="form-control " placeholder="Примітка до операції"/>
                </div>
                <div class="col-lg-2">
                    <input data-bind="value:$root.money" id="money" name="money" type="number" class="form-control" min="0" step="0.01" placeholder="Сума"/>
                </div>
            </div>
            <div class="row margin-top-15">
                <div class="col-lg-4">
                    Кошелек
                    <select name="accounts" id="accounts" class="form-control" required>
                        <c:forEach var="acc" items="${accounts}">
                            <option value="${acc.id}">${acc.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-lg-3">
                    Валюта
                    <select  id="currencies" name="currency" class="form-control">
                        <c:forEach var="curr" items="${currencies}">
                            <option value="${curr.id}">${curr.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="hidden" name="category" data-bind="value:$root.category">
                <div class="col-lg-5">
                    Категорія
                    <select  id="cetegories"  class="form-control" data-bind="options: $root.viewCategories,
                       optionsText: function (item) { return item.parentName + ' | ' + item.name },
                       value: $root.selectedCategory"></select>
                </div>
            </div>
            <div class="row margin-top-15">
                <div class="col-lg-12">
                    <button type="submit" class="btn ">Зберегти</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $("#money").calculator({
    });
</script>
<script>
    $('#date').datetimepicker({
        format: 'DD.MM.YYYY',
        defaultDate:new Date()
    })
    $("#cetegories").select2();
    $("#accounts").select2();
    $("#currencies").select2();

    $(document).ready(function(){
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
<script type="text/javascript" data-main="/resources/js/operationView.js" src="/resources/js/libs/require.js"></script>
