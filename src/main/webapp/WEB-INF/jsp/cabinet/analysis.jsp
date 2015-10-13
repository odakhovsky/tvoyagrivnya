<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="padding-25">
            <h5 align="center">Період</h5>

            <div class="row">
                <div class="col-lg-4 col-lg-offset-4">
                    <input data-bind="value:$root.selectedRange" id="date-from" name="date-range" class="form-control" type="text">
                </div>
            </div>
            <div class=" margin-top-15">
                <div class="row col-lg-offset-3 " style="margin-top:3px">
                    <div class="col-lg-4">
                        <h5 align="center">Категорія</h5>
                    </div>
                    <div class="col-lg-5">
                        <h5 >Значимость(1-100)</h5>
                    </div>
                </div>
                <div class="col-lg-offset-3 " data-bind="foreach:categories">
                    <div class="row " style="margin-top:3px">
                        <div class="col-lg-5">
                            <input data-bind="value:name" readonly="readonly" class="form-control">
                        </div>
                        <div class="col-lg-2">
                            <input data-bind="value:value, valueUpdate:'afterkeydown'" class="form-control" type="number" value="100" min="1" max="100"
                                   placeholder="Вага категорії">
                        </div>
                    </div>
                </div>

                <button data-bind="event{click:sendRequest}" class="btn btn-block margin-top-15">Провести аналіз</button>
            </div>
        </div>
    </div>
</div>
<script>
    initMonthRange("#date-from");
</script>
<script type="text/javascript" data-main="/resources/js/analysis.js"
        src="/resources/js/libs/require.js"></script>