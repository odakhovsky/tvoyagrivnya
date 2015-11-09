<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="padding-25">
            <a href="/cabinet/analysis/extended">Розширений аналіз</a>
            <h5 align="center">Період</h5>

            <div class="row">
                <div class="col-lg-4 col-lg-offset-4">
                    <input data-bind="value:$root.selectedRange" id="date-from" name="date-range" class="form-control" type="text">
                </div>
            </div>
                <button data-bind="event{click:sendRequest}" class="btn btn-block margin-top-15">Провести аналіз</button>
        </div>
        <div id="analysis-result"></div>
    </div>
</div>
<script>
    initMonthRange("#date-from");
    $(document).on({
        ajaxStart: function() {
            HoldOn.open({
                theme:"sk-cube",
                message:'Зачекайте, йде аналіз даних!',
                textColor:"white",
            });
        },
        ajaxStop: function() { HoldOn.close(); }
    });
</script>
<script type="text/javascript" data-main="/resources/js/analysis.js"
        src="/resources/js/libs/require.js"></script>
