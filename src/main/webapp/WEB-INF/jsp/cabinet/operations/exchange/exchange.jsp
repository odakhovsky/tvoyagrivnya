<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well" id="new-operation">
    <div class="row">
        <h4 align="center">Переказ \ обмін коштів</h4>
        <form action="/cabinet/accounts/exchange/new/" method="POST" id="new-operation-form"
              class="col-lg-12 operation-form" style="padding:35px">
            <div class="row form-inline mar">

                <div class="col-lg-3">
                    Дата:
                    <input name="date" type='text'
                           id="date"
                           class="form-control"
                           placeholder="DD.MM.YYYY"/>
                </div>
                <div class="col-lg-9">
                    <input id="description" type="text" name="description" class="form-control "
                           placeholder="Примітка до операції"/>
                </div>
            </div>
            <div class="row margin-top-15">
                <div class="col-xs-6">
                    <div class="form-group">
                        <h4 align="center">З рахунку</h4>
                        <select  data-bind="value:$root.selectedAccFrom,event:{change:changeAccountFrom}" name="accFrom" class="form-control" required>
                            <c:forEach var="acc" items="${accounts}">
                                <option value="${acc.id}">${acc.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <h4 align="center">Валюта</h4>
                        <input type="hidden" name="currencyFrom" data-bind="value:$root.selCurrFrom">
                        <select   id="currencyFrom"  class="form-control" data-bind="options: $root.currencies,
                       optionsText: 'name',
                       value: $root.selectedCurrFrom,event:{change:changeCurrFrom}"></select>
                    </div>

                    <div class="form-group">
                        <input data-bind="value:$root.money, valueUpdate: 'afterkeydown'"  id="money" name="moneyFrom" type="number" class="form-control"
                               min="0" step="0.01" placeholder="Сума"/>
                    </div>
                </div>

                <div class="col-xs-6">
                    <div class="form-group">
                        <h4 align="center">На рахунок</h4>
                        <select data-bind="value:$root.selectedAccTo,event:{change:changeAccountTo}" name="accTo" class="form-control" required>
                            <c:forEach var="acc" items="${accounts}">
                                <option value="${acc.id}">${acc.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <h4 align="center">Валюта</h4>
                        <input type="hidden" name="currencyTo" data-bind="value:$root.selCurrTo">
                        <select  id="currencyTo"  class="form-control" data-bind="options: $root.currencies,
                       optionsText: 'name',
                       value: $root.selectedCurrTo,event:{change:changeCurrTo}"></select>
                    </div>

                    <div class="form-group">
                        <input hidden="hidden" name="moneyTo" data-bind="value:$root.moneyTo">
                        <input data-bind="value:$root.moneyTo" disabled="disabled"  id="moneyTo"  type="number" class="form-control"
                               min="0" step="0.001" placeholder="Сума"/>
                    </div>
                </div>

            </div>


            <div class="row margin-top-15">
                <div class="col-lg-12">
                    <button data-bind="enable:$root.validvalues" type="submit" class="btn " >Зберегти</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    $('#date').datetimepicker({
        format: 'DD.MM.YYYY',
        defaultDate: new Date()
    })


    $(document).ready(function () {


        $("#new-operation-form").validate({
            rules: {
                date: {
                    required: true
                },
                money: {
                    required: true,
                    min: 0.01
                },
                accounts: {
                    required: true
                }
            },

            messages: {
                date: {
                    required: "Обов'язкове поле"
                },
                money: {
                    required: "Обов'язкове поле",
                    min: "Мін к-сть {0}"
                },
                accounts: {
                    required: "Обов'язкове поле",
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
<script type="text/javascript" data-main="/resources/js/exchangeView.js" src="/resources/js/libs/require.js"></script>
