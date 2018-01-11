<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>手机号区段管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function send(id) {
            $.ajax({
                url: "${ctx}/bis/bisSmsMobile/sendSms",
                type:"POST",
//                data:{mobile:mobile,id:id},
                success: function(data){
                    if(data.code==200){
                        top.$.jBox.tip('发送成功:'+data.result+'条','warning');
                    }else {
                        top.$.jBox.tip('发送失败','warning');
                    }
                    console.log(data);
                }});
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/bis/bisSmsMobile/">手机号区段列表</a></li>
    <shiro:hasPermission name="bis:bisSmsMobile:edit"><li><a href="${ctx}/bis/bisSmsMobile/form">手机号区段添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="bisSmsMobile" action="${ctx}/bis/bisSmsMobile/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>手机号：</label>
            <form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>省：</label>
            <form:input path="province" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>市：</label>
            <form:input path="city" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>运营商：</label>
            <form:input path="type" htmlEscape="false" maxlength="10" class="input-medium"/>
        </li>
        <li><label>电话：</label>
            <form:input path="phone" htmlEscape="false" maxlength="10" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" onclick="send()" value="发送"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>手机号</th>
        <th>省</th>
        <th>市</th>
        <th>运营商</th>
        <th>电话</th>
        <th>数量</th>
        <shiro:hasPermission name="bis:bisSmsMobile:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="bisSmsMobile">
        <tr>
            <td><a href="${ctx}/bis/bisSmsMobile/form?id=${bisSmsMobile.id}">
                    ${bisSmsMobile.mobile}
            </a></td>
            <td>
                    ${bisSmsMobile.province}
            </td>
            <td>
                    ${bisSmsMobile.city}
            </td>
            <td>
                    ${bisSmsMobile.type}
            </td>
            <td>
                    ${bisSmsMobile.phone}
            </td>
            <td>
                    ${bisSmsMobile.num}
            </td>
            <shiro:hasPermission name="bis:bisSmsMobile:edit"><td>
                <a href="${ctx}/bis/bisSmsMobile/form?id=${bisSmsMobile.id}">修改</a>
                <a href="${ctx}/bis/bisSmsMobile/delete?id=${bisSmsMobile.id}" onclick="return confirmx('确认要删除该手机号区段吗？', this.href)">删除</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>