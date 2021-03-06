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
            <div class="divList"></div>
            <!-- 加群 -->
            <div class="card">
                <div class="card-content">
                    <div class="card-content-inner">
                        没有搜到你想看的资源，加QQ群792830535反馈
                    </div>
                </div>
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
                            '         <div class="card-content" style="top: -60px;left: -40px;">' +
                            '            <div class="card-content-inner">' +
                            '                <p class="color-gray">年份：' + data.year + '</p>' +
                            '                 <p>' + data.region + '</p>' +
                            '                 <p>播放量：' + data.number + '</p>' +
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
                var list =defaultList();
                if (list.length > 0) {
                    $.each(list, function (i, data) {
                        content += ' <div class="content-block-title"><h3>' + data.name + '</h3></div> ' +
                            '  <div class="card demo-card-header-pic">' +
                            '      <div valign="bottom" class="card-header color-white no-border no-padding">' +
                            '         <img class="card-cover" src="' + data.img + '" alt="" style="width: 150px;height: 200px;margin-left: 5%;margin-top: 5%;">' +
                            '         <div class="card-content" style="top: -60px;left: -40px;">' +
                            '            <div class="card-content-inner">' +
                            '                <p class="color-gray">年份：' + data.year + '</p>' +
                            '                 <p>' + data.region + '</p>' +
//                            '                 <p>播放量：' + data.number + '</p>' +
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
            list = defaultList();
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
                    '         <div class="card-content" style="top: -60px;left: -40px;">' +
                    '            <div class="card-content-inner">' +
                    '                <p class="color-gray">年份：' + data.year + '</p>' +
                    '                 <p>' + data.region + '</p>' +
//                    '                 <p>播放量：' + data.number + '</p>' +
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

    //默认电影列表
    function defaultList() {
        var data=Array();
        data[0]={a:"/dy/dzp/39513/",name:"毒液",img:"http://pic.ikacc.com/pic/images/2018-4/201842515425137530.jpg",year:"2018",region:"地区：欧美",number:"7619"};
        data[1]={a:"/dy/xjp/39697/",name:"李茶的姑妈",img:"http://pic.ikacc.com/pic/images/2018-5/2018541031697014.jpg",year:"2018",region:"地区：欧美",number:"7619"};
        data[2]={a:"/dy/dzp/39971/",name:"无双",img:"http://pic.ikacc.com/pic/images/2018-5/20185181617966189.jpg",year:"2018",region:"地区：欧美",number:"7619"};
        data[3]={a:"/dy/xjp/39700/",name:"胖子行动队",img:"http://pic.ikacc.com/pic/images/2018-5/20185410595046310.jpg",year:"2018",region:"地区：欧美",number:"7619"};
        data[4]={a:"/dy/dzp/35509/",name:"碟中谍6",img:"http://pic.ikacc.com/pic/images/2017-8/201783021474664840.jpg",year:"2018",region:"地区：欧美",number:"7619"};
        data[5]={a:"/dy/dzp/39242/",name:"蚁人2：黄蜂女现身",img:"http://pic.ikacc.com/pic/images/2018-4/201841213242698383.jpg",year:"2018",region:"地区：欧美",number:"7619"};
        data[6]={a:"/dy/xjp/42438/",name:"快把我哥带走",img:"http://pic.ikacc.com/pic/images/2018-8/20188182091826086.jpg",year:"2018",region:"地区：大陆",number:"7619"};
        data[7]={a:"/dy/xjp/39680/",name:"一出好戏",img:"http://pic.ikacc.com/pic/images/2018-5/20185310375991128.jpg",year:"2018",region:"地区：大陆",number:"7619"};
        data[8]={a:"/dy/xjp/40476/",name:"爱情公寓电影版",img:"http://pic.ikacc.com/pic/images/2018-6/20186101730632709.jpg",year:"2018",region:"地区：大陆",number:"2545"};
        data[9]={a:"/dy/dzp/36864/",name:"狄仁杰之四大天王",img:"http://pic.ikacc.com/pic/images/2017-12/201712109455659674.jpg",year:"2018",region:"地区：大陆",number:"2136"};
        data[10]={a:"/dy/xjp/39642/",name:"西虹市首富",img:"http://pic.ikacc.com/pic/images/2018-5/2018511114966774.jpg",year:"2018",region:"地区：大陆",number:"7782"};
        data[11]={a:"/dy/jqp/39239/",name:"我不是药神",img:"http://pic.ikacc.com/pic/images/2018-4/20184121258778039.jpg",year:"2018",region:"地区：大陆",number:"5686"};
        data[12]={a:"/dy/xjp/39638/",name:"龙虾刑警",img:"http://pic.ikacc.com/pic/images/2018-5/20185110265067503.jpg",year:"2018",region:"地区：大陆",number:"8635"};
        data[13]={a:"/dy/dzp/40298/",name:"邪不压正",img:"http://pic.ikacc.com/pic/images/2018-6/20186311381784280.jpg",year:"2018",region:"地区：大陆",number:"6794"};
        data[14]={a:"/dy/dzp/36992/",name:"死侍2",img:"http://pic.ikacc.com/pic/images/2017-12/2017122115333993607.jpg",year:"2018",region:"地区：大陆",number:"7149"};
        data[15]={a:"/dy/dzp/39244/",name:"动物世界 2018",img:"http://pic.ikacc.com/pic/images/2018-4/201841213462387936.jpg",year:"2018",region:"地区：大陆",number:"1120"};
        data[16]={a:"/dy/khp/36991/",name:"侏罗纪世界2失落王国",img:"http://pic.ikacc.com/pic/images/2017-12/2017122115244365638.jpg",year:"2018",region:"地区：大陆",number:"4665"};
        data[17]={a:"/dy/dzp/36376/",name:"金蝉脱壳2",img:"http://pic.ikacc.com/pic/images/2017-11/201711816515662734.jpg",year:"2018",region:"地区：大陆",number:"7882"};
        return data;
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
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

</script>
</html>