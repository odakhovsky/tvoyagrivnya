<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="well">
    <div class="row">
        <div class="padding-25">
            <h5 align="center">Зворотній зв`язок</h5>

            <div class="row ">
                <div class="col-lg-offset-3 margin-top-15">
                    <div class="col-lg-4">
                        <input data-bind="value:$root.selectedRange" id="date-from" name="date-range"
                               class="form-control" type="text">
                    </div>
                    <div class="col-lg-3">
                        <input id="email-filter" placeholder="Email(not required)" data-bind="value:$root.email,
                            valueUpdate: 'afterkeydown'," id="date-from"
                               name="date-range" class="form-control" type="email">
                    </div>
                    <div class="col-lg-2">
                        <button data-bind="event{click:sendRequest}" class="btn  pull-right">Пошук</button>
                    </div>
                </div>
            </div>
        </div>
        <table class="col-lg-10 col-lg-offset-1 padding-5" id="feedbacks">
            <tbody data-bind="foreach:$root.feeds">
            <tr class="">
                <td class="col-lg-12" >
                    <h5 align="center" class="title">
                        Email:
                        <span style="font-weight: bold" data-bind="text:email"></span>
                    </h5>

                    <textarea data-bind="text:text" class="form-control feedback-area" style="width: 100%;" readonly="readonly">
                    </textarea>
                    <%--<div  class="form-group padding-5 ">
                         <textarea  class="form-control feedback-area" ></textarea>
                        <button data-bind="event{click:$root.sendAnswer}" class="btn pull-right margin-top-5">Відповісти</button>
                    </div>--%>
                    <hr />
                </td>
            </tr>
            </tbody>
        </table>

    </div>
</div>
<script>
    initDateRange("#date-from", true);
</script>
<script type="text/javascript" data-main="/resources/js/feedback.js"
        src="/resources/js/libs/require.js"></script>
