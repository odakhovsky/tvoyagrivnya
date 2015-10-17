<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    $(document).ready(function () {
        $.get("/cabinet/accounts/view/", function (data) {
            $("#accounts-view").html(data);
        });
        $.get("/cabinet/organizer/note/view/", function (data) {
            $("#notes-view").html(data);
        });
    });

</script>

<div id="accs-notes">
    <div id="accounts-view"></div>
    <hr class="hr"/>
    <div id="notes-view"></div>
</div>


