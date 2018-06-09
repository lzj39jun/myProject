<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>雾途电影网</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="image/movieIcon.jpg">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="referrer" content="no-referrer">
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
            <h1 class="title">影视资源</h1>
        </header>

        <!-- 工具栏 -->
        <nav class="bar bar-tab">
            <a class="tab-item external active" href="#" data-movieType="0">
                <span class="icon icon-home"></span>
                <span class="tab-label">电影</span>
            </a>
            <a class="tab-item external" href="#" data-movieType="1">
                <span class="icon icon-computer"></span>
                <span class="tab-label">电视剧</span>
            </a>
            <a class="tab-item external" href="#" data-movieType="2">
                <span class="icon icon-clock"></span>
                <span class="tab-label">综艺</span>
            </a>
        </nav>
        <div class="bar bar-header-secondary">
            <div class="searchbar">
                <a class="searchbar-cancel">搜索</a>
                <div class="search-input">
                    <label class="icon icon-search" for="search"></label>
                    <input type="search" id='search' placeholder='搜索电影'/>
                </div>
            </div>
        </div>
        <!-- 这里是页面内容区 -->
        <div class="content">
            <!-- 电影列表 -->
            <div class="divList">
            </div>
            <!-- 免责声明 -->
            <div class="card">
                <div class="card-content">
                    <div class="card-content-inner">免责声明:本站所有视频均来自互联网收集而来，版权归原创者所有，如果侵犯了你的权益，请通知我们，我们会及时删除侵权内容，谢谢合作！联系邮箱
                        lzj39jun@163.com
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- 电影详情 Popup -->
    <div class="popup popup-about">
        <div class="content-block">
            <p><a href="#" class="close-popup">返回</a></p>
            <p><a href="#" class="button button-big" id="copyUrlAll">复制全部</a></p>
            <div class="card-div">
                <!-- 电影详情 -->
            </div>
        </div>
    </div>
    <!-- 视频播放 Popup -->
    <div class="popup popup-services">
        <div class="content-block">
            <p><a href="#" class="close-services">关闭</a></p>
            <div id="video" style="width: 100%; height: 40%;"></div>
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

</body>

<script type='text/javascript' src='//g.alicdn.com/sj/lib/zepto/zepto.js' charset='utf-8'></script>
<script type='text/javascript' src='//g.alicdn.com/msui/sm/0.6.2/js/sm.js' charset='utf-8'></script>
<script type="text/javascript" src="${ctxStatic}/clipboard/clipboard.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
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
    var newData;//最新资源数据
    $(function () {
//        list("复仇者联盟3");
        newDataList();

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

    //电影详情
    $(document).on('click', '.movieInfo', function () {
        console.log($(this).attr("movieid"))
        $.showPreloader();
        $.post("${api}/movie/qqpForm", {id: $(this).attr("movieid")}, function (result) {
            console.log(result);
            if (result.success && result.data.count > 0) {
                var content = "";
                $.each(result.data.list, function (i, data) {
                    content += '<div class="card">'
                        + '<div class="card-header"  style="word-wrap:break-all">' + data.title + '</div>'
                        + '<div class="card-content">'
                        + '  <div class="card-content-inner"  style="word-wrap:break-word">' + data.thunder + '</div>'
                        + '</div>'
                        + '<div class="card-footer">' +
                        '<a href="#" class="link" id="copyThunder" data-clipboard-text="' + data.thunder + '">复制迅雷地址</a>&nbsp;' +
                        '<a href="#" class="link m3u8"  data-m3u8Url="' + data.m3u8Url + '">在线播放</a>' +
                        '</div>'
                        + '</div>'
                })
                $(".card-div").html(content);
                $.popup('.popup-about');
            } else {
                $.toast("暂无资源");
            }
            $.hidePreloader();

        });

    });

    //电影列表
    function list(name) {
        if (name == '') {
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
                            '         <img class="card-cover" src="' + data.img + '" alt="" style="width: 150px;height: 200px;margin-left: 5%;margin-top: 5%;">' +
                            '         <div class="card-content" style="top: -90px;left: -60px;">' +
                            '            <div class="card-content-inner">' +
                            '                <p class="color-gray">年份：' + data.year + '</p>' +
                            '                 <p>' + data.region + '</p>' +
                            //                            '               <p>' + data.performer + '</p>' +
                            '           </div>' +
                            '       </div>' +
                            '      </div>' +
                            '         <div class="card-content">' +
                            '            <div class="card-content-inner">' +
                            '               <p>' + data.performer + '</p>' +
                            '           </div>' +
                            '       </div>' +
                            '       <div class="card-footer">' +
                            '           <a href="#" class="link movieInfo" movieid="' + data.a + '">在线播放</a>' +
                            '       </div>' +
                            '   </div>'
                    })
                }
                $(".divList").html(content);
            }
            $.hidePreloader();
        });
    }

    //最新资源列表数据
    function newDataList() {
        $.showPreloader();
        $.post("${api}/movie/qqpNewList", {name: 1}, function (result) {
            console.log(result);
            if (result.success) {
                var content = "";
                newData = result.data;
                var list = newData.movie;
                if (list.length > 0) {
                    $.each(list, function (i, data) {
                        content += ' <div class="content-block-title"><h3>' + data.name + '</h3></div> ' +
                            '  <div class="card demo-card-header-pic">' +
                            '      <div valign="bottom" class="card-header color-white no-border no-padding">' +
                            '         <img class="card-cover" src="' + data.img + '" alt="" style="width: 150px;height: 200px;margin-left: 5%;margin-top: 5%;">' +
                            '         <div class="card-content" style="top: -90px;left: -60px;">' +
                            '            <div class="card-content-inner">' +
                            '                <p class="color-gray">年份：' + data.year + '</p>' +
                            '                 <p>' + data.region + '</p>' +
                            '           </div>' +
                            '       </div>' +
                            '      </div>' +
                            '       <div class="card-footer">' +
                            '           <a href="#" class="link movieInfo" movieid="' + data.a + '">在线播放</a>' +
                            '       </div>' +
                            '   </div>'
                    })
                }
                $(".divList").html(content);
            }
            $.hidePreloader();
        }, "json");
    }

    //最新资源列表加载
    function newlist(type) {
        $.showPreloader();
        var content = "";
        var list
        if (type == 0) {
            list = newData.movie;
        } else if (type == 1) {
            list = newData.tv;
        } else if (type == 2) {
            list = newData.variety;
        }
        if (list.length > 0) {
            $.each(list, function (i, data) {
                content += ' <div class="content-block-title"><h3>' + data.name + '</h3></div> ' +
                    '  <div class="card demo-card-header-pic">' +
                    '      <div valign="bottom" class="card-header color-white no-border no-padding">' +
                    '         <img class="card-cover" src="' + data.img + '" alt="" style="width: 150px;height: 200px;margin-left: 5%;margin-top: 5%;">' +
                    '         <div class="card-content" style="top: -90px;left: -60px;">' +
                    '            <div class="card-content-inner">' +
                    '                <p class="color-gray">年份：' + data.year + '</p>' +
                    '                 <p>' + data.region + '</p>' +
                    '           </div>' +
                    '       </div>' +
                    '      </div>' +
                    '       <div class="card-footer">' +
                    '           <a href="#" class="link movieInfo" movieid="' + data.a + '">在线播放</a>' +
                    '       </div>' +
                    '   </div>'
            })
        }
        $(".divList").html(content);
        $.hidePreloader();
    }

    var player;

    //播放视频
    function changeVideo(videoUrl) {
        var newVideoObject = {
            container: '#video', //容器的ID
            variable: 'player',
            autoplay: true, //是否自动播放
            html5m3u8: isPc,
//            loaded: 'loadedHandler', //当播放器加载后执行的函数
            video: videoUrl
        }
        player = new ckplayer(newVideoObject);

        //判断是需要重新加载播放器还是直接换新地址
        if (player.playerType == 'html5video') {
            if (player.getFileExt(videoUrl) == '.flv' || player.getFileExt(videoUrl) == '.m3u8' || player.getFileExt(videoUrl) == '.f4v' || videoUrl.substr(0, 4) == 'rtmp') {
                player.removeChild();

                player = null;
                player = new ckplayer();
                player.embed(newVideoObject);
            } else {
                player.newVideo(newVideoObject);
            }
        } else {
            if (player.getFileExt(videoUrl) == '.mp4' || player.getFileExt(videoUrl) == '.webm' || player.getFileExt(videoUrl) == '.ogg') {
                player = null;
                player = new ckplayer();
                player.embed(newVideoObject);
            } else {
                player.newVideo(newVideoObject);
            }
        }
    }

    function loadedHandler() {//播放器加载后会调用该函数
        player.addListener('time', timeHandler); //监听播放时间,addListener是监听函数，需要传递二个参数，'time'是监听属性，这里是监听时间，timeHandler是监听接受的函数
    }

    function timeHandler(t) {
        console.log('当前播放的时间：' + t);
    }

    //在线播放
    $(document).on('click', '.m3u8', function () {
        console.log($(this).attr("data-text"))
        $.showPreloader();
        $.post("${api}/movie/findM3u8Url", {id: $(this).attr("data-m3u8Url")}, function (result) {
            console.log(result);
            if (result.success) {
                if (result.data == '') {
                    $.toast("抱歉，暂无在线视频");
                } else {
                    changeVideo(result.data);
                    $.popup('.popup-services');
                }

            }
            $.hidePreloader();

        });
    });

    //关闭播放页面
    $(document).on('click', '.close-services', function () {
        $.closeModal('.popup-services');
        $.popup('.popup-about');
        player.videoPause();
        player.videoClear()
    });

    //菜单切换
    $(document).on('click', '.external', function () {
        newlist($(this).attr("data-movieType"));
    });

</script>
</html>