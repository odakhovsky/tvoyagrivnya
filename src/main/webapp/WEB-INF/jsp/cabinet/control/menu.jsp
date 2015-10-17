<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav nav-pills">
  <li role="presentation"><a href="/cabinet/control/categories/">Категорії</a></li>
  <sec:authorize access="hasRole('ROLE_OWNER')">
    <li role="presentation"><a href="/cabinet/control/members/">Члени сім'ї</a></li>
  </sec:authorize>
  <li role="presentation"><a href="/cabinet/control/currencies/">Валюти</a></li>
</ul>
