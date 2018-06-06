<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商户平台</title>

<link href="//res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css" rel="stylesheet"/>
<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

</head>

<body>
<from action="">
<div class="page flex js_show">
    <div class="page__hd">
        <h1 class="page__title">${shop.shopName}</h1>
        <p class="page__desc">Flex布局</p>
    </div>
    <div class="page__bd page__bd_spacing">
        <div class="weui-flex">
            <div class="weui-flex__item">
	            <div class="weui-cells">
		            <div class="weui-cell">
		                <div class="weui-cell__bd">
		                    <input class="weui-input" type="text" value="dddd萨达" placeholder="请输入文本" disabled="disabled">
		                </div>
		                <div class="weui-cell__bd">
		                    <input class="weui-input" type="text" placeholder="请输入文本">
		                </div>
		            </div>
		            <div class="weui-cell">
		                <div class="weui-cell__bd">
		                    <div class="weui-uploader">
		                        <div class="weui-uploader__hd">
		                            <p class="weui-uploader__title">图片上传</p>
		                            <div class="weui-uploader__info">0/2</div>
		                        </div>
		                        <div class="weui-uploader__bd">
		                            <ul class="weui-uploader__files" id="uploaderFiles">
		                                <li class="weui-uploader__file">
	                                        <img id="image" src="" style="width: 100%;height: 100%"  onclick="viewImg(this.src)"/>
		                                </li>
		                            </ul>
		                            <div class="weui-uploader__input-box">
		                                <input onclick="chooseImage()" class="weui-uploader__input" type="file" accept="image/*" multiple="">
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	        </div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
        </div>
        <div class="weui-flex">
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
        </div>
        <div class="weui-flex">
            <div><div class="placeholder">weui</div></div>
            <div class="weui-flex__item"><div class="placeholder">weui</div></div>
            <div><div class="placeholder">weui</div></div>
        </div>
    </div>
    <div class="page__ft j_bottom">
        <a href="javascript:home()"><img src="./images/icon_footer_link.png"></a>
    </div>
</div>
</from>
	<span>${signature}</span>
	<span>js===${shop}</span>
	<span>js===${appId}</span>
	<span>js===${timestamp}</span>
	<span>js===${nonceStr}</span>
	<span onclick="chooseImage()">chooseImage===</span>
	<image id="image"></image>
	<image src="${src}/resources/pic/FQNWDPnp2B31hYiuAScFr3RadOh_PiSnpTsXjLtMCuUgmNT3cUN2AjHejz0AVpUi.jpg"></image>
	<image src="${src}/resources/pic/FQNWDPnp2B31hYiuAScFr3RadOh_PiSnpTsXjLtMCuUgmNT3cUN2AjHejz0AVpUi.jpg"></image>
	<script>
	var time = ${timestamp};
		wx.config({
		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${appId}', // 必填，公众号的唯一标识
		    timestamp: time, // 必填，生成签名的时间戳
		    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
		    signature: '${signature}',// 必填，签名
		    jsApiList: ['chooseImage','uploadImage','downloadImage'] // 必填，需要使用的JS接口列表
		});
		wx.ready(function(){
		    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		});
		wx.error(function(res){
			console.log(res)
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		});
		function pushServer(media_id){
			console.log(media_id);
			$.ajax({
				  type: 'GET',
				  url: '${src}/centercontroller/downloadImage',
				  data: {mediaId:media_id,shopId:"${shop.id}"},
				  success: function(res){
					  console.log(res)
				  }
			});
			
		}
		function viewImg(src){
			alert(src)
			$("img").attr("src")
			wx.previewImage({
				current: '', // 当前显示图片的http链接
				urls: [src] // 需要预览的图片http链接列表
			});
		}
		function chooseImage(){
			wx.chooseImage({
				count: 1, // 默认9
				sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
				sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
				success: function (res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					$("#image").attr('src',localIds);
					$("#uploaderFiles")
				}
			});
		}
	</script>
</body>

</html>
