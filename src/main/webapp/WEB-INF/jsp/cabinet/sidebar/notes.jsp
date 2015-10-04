<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="margin-top-15">
    <p align="center"><a href="/cabinet/organizer/">Управління нотатками</a></p>
    <h6 align="center">Останні 5 нотаток</h6>
    <div class="list-group" data-bind="foreach: notes.slice(0, 5)">
        <div class="list-group-item margin-top-5" >
            <div class="row">
                <div class="col-lg-8 truncate-note-sidebar">
                    <a data-bind="text:text, attr:{href:$root.noteurl(id)}"></a>
                </div>
                <div class="col-lg-4 no-padding">
                    <span class="pull-right text-bold margin-left-5" data-bind="text:date"></span>
                </div>
            </div>
        </div>
    </div>
</div>

