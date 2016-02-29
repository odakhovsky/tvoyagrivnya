<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="padding-25">
            <a href="/cabinet/analysis/">Простий аналіз</a>

                <h5 align="center" >Оберіть місяці для порівняння</h5>
            <div  class="form-inline col-lg-offset-1" >
                <div class="form-group text-center">
                    <label for="first">Перший місяць</label>
                    <input id="first" lang="uk-UA" data-bind="value:$root.fromDate" class="form-control" type="month">
                </div>
                <div class="form-group text-center">
                    <label for="second">Другий місяць</label>
                    <input id="second" lang="uk-UA" data-bind="value:$root.toDate" class="form-control" type="month">
                </div>
                </div>
            <button data-bind="event{click:$root.sendRequestExtended}" class="btn btn-block margin-top-15">Провести
                аналіз
            </button>
        </div>
        <div id="analysis-result"></div>
    </div>
</div>
<script>
    $(document).on({
        ajaxStart: function () {
            HoldOn.open({
                theme: "sk-cube",
                message: 'Зачекайте, триває обробка даних!',
                textColor: "white",
            });
        },
        ajaxStop: function () {
            HoldOn.close();
        }
    });
</script>
<script type="text/javascript" data-main="/resources/js/analysis.js"
        src="/resources/js/libs/require.js"></script>
