<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>雾途电影网</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.css">


</head>
<body>
<!-- page集合的容器，里面放多个平行的.page，其他.page作为内联页面由路由控制展示 -->
<div class="page-group">
    <!-- 单个page ,第一个.page默认被展示-->
    <div class="page">
        <!-- 标题栏 -->
        <header class="bar bar-nav">
            <a class="icon icon-me pull-left open-panel"></a>
            <h1 class="title">电影</h1>
        </header>

        <!-- 工具栏 -->
        <nav class="bar bar-tab">
            <a class="tab-item external active" href="#">
                <span class="icon icon-home"></span>
                <span class="tab-label">首页</span>
            </a>
            <a class="tab-item external" href="#">
                <span class="icon icon-star"></span>
                <span class="tab-label">收藏</span>
            </a>
            <a class="tab-item external" href="#">
                <span class="icon icon-settings"></span>
                <span class="tab-label">设置</span>
            </a>
        </nav>
        <div class="bar bar-header-secondary">
            <div class="searchbar">
                <a class="searchbar-cancel">取消</a>
                <div class="search-input">
                    <label class="icon icon-search" for="search"></label>
                    <input type="search" id='search' placeholder='输入关键字...'/>
                </div>
            </div>
        </div>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <%--<div class="content-block-title">电影名称</div>--%>
            <%--<div class="card demo-card-header-pic">--%>
            <%--<div valign="bottom" class="card-header color-white no-border no-padding">--%>
            <%--<img class='card-cover' src="image/movie.jpg" alt="">--%>
            <%--</div>--%>
            <%--<div class="card-content">--%>
            <%--<div class="card-content-inner">--%>
            <%--<p class="color-gray">年份：2018</p>--%>
            <%--<p>地区：大陆</p>--%>
            <%--<p>主演：张雪迎 秦俊杰 黄圣池 陈欣予 黄德毅 麦迪娜 袁子芸 李逸男 刘佳</p>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="card-footer">--%>
            <%--<a href="#" class="link">获取地址</a>--%>
            <%--</div>--%>
            <%--</div>--%>


        </div>
    </div>

    <!-- About Popup -->
    <div class="popup popup-about">
        <div class="content-block">
            <p><a href="#" class="close-popup">返回</a></p>
            <p> <a href="#" class="button button-big" id="copyUrlAll">复制全部</a></p>
            <div class="card-div">
                <%--<div class="card">--%>
                    <%--<div class="card-header">电影名称</div>--%>
                    <%--<div class="card-content">--%>
                        <%--<div class="card-content-inner"  style="word-wrap:break-word">thunder://QUFodHRwOi8vZGwxMTIuODBzLmNvbS5jbzo5OTkvMTgwNS9b5LiKSOWls1pU6Ym0XeesrDAx6ZuGL1vkuIpI5aWzWlTpibRd56ysMDHpm4ZfYmQubXA0Wlo=</div>--%>
                     <%--</div>--%>
                    <%--<div class="card-footer"><a href="#" class="link copyThunder">复制</a></div>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
    <!-- 其他的单个page内联页（如果有） -->
    <div class="page">...</div>
</div>

<!-- popup, panel 等放在这里 -->
<div class="panel-overlay"></div>
<!-- Left Panel with Reveal effect -->
<div class="panel panel-left panel-reveal">
    <div class="content-block">
        <p>欢迎您！</p>
        <p></p>
        <!-- Click on link with "close-panel" class will close panel -->
        <p><a href="#" class="close-panel">关闭</a></p>
    </div>
</div>


<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.js' charset='utf-8'></script>
<script type="text/javascript" src="${ctxStatic}/clipboard/clipboard.min.js"></script>
<script>
    //打开自动初始化页面的功能
    //建议不要打开自动初始化，而是自己调用 $.init 方法完成初始化
    $.config = {
        autoInit: true
    }
    $(function () {
        list(1);

        //单个复制
        var clipboard = new Clipboard('#copyThunder');
        clipboard.on('success', function (e) {
            $.toast("复制成功");
        });
        clipboard.on('error', function (e) {
            $.toast("复制失败，请手动复制");
        });
        //复制全部
        var clipboardAll = new Clipboard('#copyUrlAll', {
            text: function (trigger) {
                var a = ''
                $(".card-content-inner").each(function () {
                    a = a + $(this).text() + ","
                });
                return a;
            }
        });
        clipboardAll.on('success', function (e) {
            $.toast("复制成功");
        });
        clipboardAll.on('error', function (e) {
            $.toast("复制失败，请手动复制");
        });
    });
    $("#search").blur(function () {
        list(this.value);
    })

    $(document).on('click', '.movieInfo', function () {
        console.log($(this).attr("movieid"))
        $.showPreloader();
        $.post("${api}/movie/qqpForm", {id: $(this).attr("movieid")}, function (result) {
            console.log(result);
            if (result.success) {
                var content = "";
                    $.each(result.data.list, function (i, data) {
                        content += '<div class="card">'
                                      +'<div class="card-header"  style="word-wrap:break-all">'+data.title+'</div>'
                                      +'<div class="card-content">'
                                      +'  <div class="card-content-inner"  style="word-wrap:break-word">'+data.thunder+'</div>'
                                      +'</div>'
                                      +'<div class="card-footer"><a href="#" class="link" id="copyThunder" data-clipboard-text="'+data.thunder+'">复制</a></div>'
                                  +'</div>'
                    })
                $(".card-div").html(content);
                $.hidePreloader();
            }
        });
        $.popup('.popup-about');
    });

    //电影列表
    function list(name) {
        if(name==''){
            return;
        }
        $.showPreloader();
        $.post("${api}/movie/qqpList", {name: name}, function (result) {
            console.log(result);
            if (result.success) {
                var content = "";
                if (result.data.count > 0) {
                    $.each(result.data.list, function (i, data) {
                        content += ' <div class="content-block-title"><h3>' + data.name + '</h3></div> ' +
                            '  <div class="card demo-card-header-pic">' +
                            '      <div valign="bottom" class="card-header color-white no-border no-padding">' +
                            '         <img class="card-cover" src="image/movie.jpg" alt="">' +
                            '      </div>' +
                            '         <div class="card-content">' +
                            '            <div class="card-content-inner">' +
                            '                <p class="color-gray">年份：' + data.year + '</p>' +
                            '                 <p>' + data.region + '</p>' +
                            '               <p>' + data.performer + '</p>' +
                            '           </div>' +
                            '       </div>' +
                            '       <div class="card-footer">' +
                            '           <a href="#" class="link movieInfo" movieid="' + data.a + '">获取地址</a>' +
                            '       </div>' +
                            '   </div>'
                    })
                }
                $(".content").html(content);
                $.hidePreloader();
            }
        });
    }
</script>

</body>
</html>