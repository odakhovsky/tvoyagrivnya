<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <tiles:insertAttribute name="header"/>
</head>
<body>
<div class="container">
    <div id="nav">
        <tiles:insertAttribute name="nav"/>
    </div>
    <input type="hidden" value="${userBean.id}" id="userId"/>
    <input type="hidden" value="${userBean.groupBean.id}" id="groupId"/>
    <input type="hidden" value="${userBean.name} ${userBean.lastName}" id="userFio"/>
    <div id="body">
        <div class="col-xs-4">
            <tiles:insertAttribute name="sidebar"/>
        </div>
        <div class="col-xs-8">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>

    <div id="footer">
        <tiles:insertAttribute name="footer"/>
    </div>

</div>
</body>
<script type="text/javascript" data-main="/resources/js/groupView.js" src="/resources/js/libs/require.js" ></script>

</html>
