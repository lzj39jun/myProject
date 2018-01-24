<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>七七铺资源</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/bis/bisMovie/qqpList">七七铺资源列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="bisMovie" action="${ctx}/bis/bisMovie/qqpList" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>名称</th>
        <th>年份</th>
        <th>地区</th>
        <th>主演</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="bisMovie">
        <tr>
            <td><a href="${ctx}/bis/bisMovie/form?id=${bisMovie.a}">
                    ${bisMovie.name}
            </a></td>
            <td>${bisMovie.year}</td>
            <td>${bisMovie.region}</td>
            <td>${bisMovie.performer}</td>
            <td>
                <a href="${ctx}/bis/bisMovie/qqpForm?id=${bisMovie.a}">查看</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>