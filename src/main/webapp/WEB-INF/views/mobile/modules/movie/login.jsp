<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>雾途电影网-登录</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="image/movieIcon.jpg">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="referrer" content="no-referrer">
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.css">


</head>
<body>
<header class="bar bar-nav">
    <h1 class='title'>只有图标的表单</h1>
</header>
<div class="content">
    <div class="list-block">
        <ul>
            <!-- Text inputs -->
            <li>
                <div class="item-content">
                    <div class="item-media"><i class="icon icon-form-name"></i></div>
                    <div class="item-inner">
                        <div class="item-input">
                            <input type="text" placeholder="请输入手机号">
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="item-content">
                    <div class="item-media"><i class="icon icon-form-email"></i></div>
                    <div class="item-inner">
                        <div class="item-input">
                            <input type="password" placeholder="请输入密码" >
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <p><a href="#" class="button">登录</a></p>
    </div>
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

</body>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.js' charset='utf-8'></script>
<script type="text/javascript" src="${ctxStatic}/clipboard/clipboard.min.js"></script>
<script>
    var isPc = true;
    if (/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) { //移动端
        isPc = false;
    }
    //打开自动初始化页面的功能
    //建议不要打开自动初始化，而是自己调用 $.init 方法完成初始化
    $.config = {
        autoInit: true
    }
</script>
</html>