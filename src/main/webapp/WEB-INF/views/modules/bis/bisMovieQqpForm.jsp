<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>七七铺资源详情</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/layui/css/layui.css">
    <script type="text/javascript" src="${ctxStatic}/clipboard/clipboard.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/layui/layui.all.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {
            //单个复制
            var clipboard = new Clipboard('#copyUrl');
            clipboard.on('success', function (e) {
                layer.msg("复制成功");
            });
            clipboard.on('error', function (e) {
                layer.msg("复制失败，请手动复制");
            });
            //复制全部
            var clipboardAll = new Clipboard('#copyUrlAll', {
                text: function (trigger) {
                    var a = ''
                    $(".url").each(function () {
                        a = a + $(this).text() + ","
                    });
                    return a;
                }
            });
            clipboardAll.on('success', function (e) {
                layer.msg("复制成功");
            });
            clipboardAll.on('error', function (e) {
                layer.msg("复制失败，请手动复制");
            });

        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/bis/bisMovie/">七七铺资源详情</a></li>
</ul>
<input id="copyUrlAll" class="btn btn-primary" type="button" value="复制全部"/>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>名称</th>
        <th>地址</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="bisMovie">
        <tr>
            <td>
                    ${bisMovie.title}
            </td>
            <td class="url">
                    ${bisMovie.thunder}
            </td>
            <td>
                <input id="copyUrl" class="btn btn-primary" type="button" data-clipboard-text="${bisMovie.thunder}" value="复制"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>

</html>