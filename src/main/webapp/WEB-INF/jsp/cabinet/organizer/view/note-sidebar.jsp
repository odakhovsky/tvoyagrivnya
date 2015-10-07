<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="margin-top-15">
    <p align="center"><a href="/cabinet/organizer/">Управління нотатками</a></p>
    <h6 align="center">Останні 5 нотаток</h6>
    <c:forEach var="note" items="${notes}">
        <div class="list-group" >
            <div class="list-group-item margin-top-5">
                <div class="row">
                    <div class="col-lg-8 truncate-note-sidebar">
                        <a href="/cabinet/organizer/note/${note.id}/info/">${note.text}</a>
                    </div>
                    <div class="col-lg-4 no-padding">
                        <span class="pull-right text-bold margin-left-5">${note.date}</span>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

