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
    <link href="/resources/css/shop-homepage.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="container">
    <div id="nav">
        <tiles:insertAttribute name="nav"/>
    </div>
    <div id="body">
        <!-- Page Content -->
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <tiles:insertAttribute name="body"/>
                </div>
            </div>
        </div>
    </div>
    <div id="footer">
        <div class="container">
            <tiles:insertAttribute name="footer"/>
        </div>
    </div>

</div>
</body>
</html>
