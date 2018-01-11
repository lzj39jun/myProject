<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>短信模板管理</title>
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
            var mobile=$("#mobile").val();
            if(mobile==null||mobile==""){
                top.$.jBox.tip('请输入手机号','warning');
                return;
            }
            $.ajax({
                url: "${ctx}/bis/bisSmsTemplate/sendSms",
                type:"POST",
                data:{mobile:mobile,id:id},
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
    <li class="active"><a href="${ctx}/bis/bisSmsTemplate/">短信模板列表</a></li>
    <shiro:hasPermission name="bis:bisSmsTemplate:edit"><li><a href="${ctx}/bis/bisSmsTemplate/form">短信模板添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="bisSmsTemplate" action="${ctx}/bis/bisSmsTemplate/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>名称：</label>
            <form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>类别：</label>
            <form:select path="type" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('bis_sms_template_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li><label>手机号：</label>
            <input id="mobile" class="input-medium">
        </li>
        <li><label>区段1867188：</label>
            <input id="phone" class="input-medium">
        </li>
        <li><label>数量：</label>
            <input id="count" class="input-medium">
        </li>
        <li class="btns"><input class="btn btn-primary" type="button" onclick="send()" value="发送"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>名称</th>
        <th>url</th>
        <th>创建时间</th>
        <shiro:hasPermission name="bis:bisSmsTemplate:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="bisSmsTemplate">
        <tr>
            <td><a href="${ctx}/bis/bisSmsTemplate/form?id=${bisSmsTemplate.id}">
                    ${bisSmsTemplate.name}
            </a></td>
            <td>
                ${bisSmsTemplate.url}
            </td>
            <td>
                <fmt:formatDate value="${bisSmsTemplate.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="bis:bisSmsTemplate:edit"><td>
                <a href="${ctx}/bis/bisSmsTemplate/form?id=${bisSmsTemplate.id}">修改</a>
                <a href="${ctx}/bis/bisSmsTemplate/delete?id=${bisSmsTemplate.id}" onclick="return confirmx('确认要删除该短信模板吗？', this.href)">删除</a>
                <a href="javascrpit:viod(0)" onclick="send('${bisSmsTemplate.id}')">发送</a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>