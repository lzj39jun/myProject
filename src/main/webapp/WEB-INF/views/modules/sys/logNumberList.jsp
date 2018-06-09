<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	    $(function () {
            var num=0;
            $(".num").each(function(){
                num+=parseInt($(this).text());
            });
            var sumNum=0;
            $(".sumNum").each(function(){
                sumNum+=parseInt($(this).text())
            });
            $(".numT").text(num);
            $(".sumNumT").text(sumNum);
        })

	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/log/">日志列表</a></li> --%>
<!-- 	</ul> -->
	<form:form id="searchForm" action="${ctx}/sys/log/findByNumberList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div>
			<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
				value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>日期</th><th>访问人数</th><th>访问次数</th></thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<tr>
			<td>总计</td>
			<td class="numT"></td>
			<td class="sumNumT"></td>
		</tr>
		<c:forEach items="${page.list}" var="log">
			<tr>
				<td>${log.days}</td>
				<td class="num">${log.num}</td>
				<td class="sumNum">${log.sumNum}</td>
			</tr>
			<c:if test="${not empty log.exception}"><tr>
				<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
<%-- 					用户代理: ${log.userAgent}<br/> --%>
<%-- 					提交参数: ${fns:escapeHtml(log.params)} <br/> --%>
					异常信息: <br/>
					${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}</td>
			</tr></c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>