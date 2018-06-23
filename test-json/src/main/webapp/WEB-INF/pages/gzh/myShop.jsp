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
<style type="text/css">
body{padding-top: 0px;}
</style>
</head>

<body>
<div class="js_dialog" id="upImgDialog" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog weui-skin_android">
        <div class="weui-dialog__bd">
           	确定要保存修改吗？
        </div>
        <div class="weui-dialog__ft">
            <a href="javascript:$('#upImgDialog').toggle();" class="weui-dialog__btn weui-dialog__btn_default">取消</a>
            <a href="javascript:$('#modifyFrom').submit();" class="weui-dialog__btn weui-dialog__btn_primary">确定</a>
        </div>
    </div>
</div>
<form id="modifyFrom" action="${src}/centercontroller/modifyShop" method="post">
<input type="hidden" name="id" value="${shop.id}"/>
<input type="hidden" name="mainImage" id="mainImage"/>
<input type="hidden" name="slideImage" id="slideImage"/>
<input type="hidden" name="menuImage" id="menuImage"/>
</form>
<div class="page flex js_show">
    <div class="page__hd" id="infoBase">
        <h1 class="page__title">${shop.shopName}</h1>
        <p class="page__desc">地址：${shop.adress}</p>
        <p class="page__desc">电话：${shop.phone}</p>
        <p class="page__desc">店内优惠：${shop.sale}</p>
    </div>
    <!-- lll -->
<form id="modifyFromBase" action="${src}/centercontroller/modifyShop" method="post">
<input type="hidden" name="id" value="${shop.id}"/>
    <div class="page__bd page__bd_spacing" id="modifyInfoBase" style="display: none;">
        <div class="weui-flex">
            <div class="weui-flex__item">
            	<div class="weui-cells__title">店铺名称</div>
            	<div class="weui-cells">
		            <div class="weui-cell">
		                <div class="weui-cell__bd">
		                    <input name="shopName" class="weui-input" type="text" placeholder="请输入文本" value="${shop.shopName}">
		                </div>
		            </div>
		        </div>
            	<div class="weui-cells__title">地址</div>
            	<div class="weui-cells">
		            <div class="weui-cell">
		                <div class="weui-cell__bd">
		                    <input name="adress" class="weui-input" type="text" placeholder="请输入文本" value="${shop.adress}">
		                </div>
		            </div>
		        </div>
	            <div class="weui-cells">
		            <div class="weui-cell">
		                <div class="weui-cell__hd"><label class="weui-label">电话</label></div>
		                <div class="weui-cell__bd">
		                    <input name="phone" class="weui-input" type="number" pattern="^[1][3,4,5,7,8][0-9]{9}$" placeholder="请输入数字" value="${shop.phone}">
		                </div>
		            </div>
	            </div>
	            <div class="weui-cells">
		            <div class="weui-cells__title">店内优惠</div>
					<div class="weui-cells weui-cells_form">
					    <div class="weui-cell">
					        <div class="weui-cell__bd">
					            <textarea name="sale" class="weui-textarea" placeholder="请输入文本" rows="3">${shop.sale}</textarea>
					        </div>
					    </div>
					</div>
				</div>
	            
	        </div>
        </div>
    </div>
    <a id="mib" href="javascript:$('#infoBase').hide();$('#mib').hide();$('#modifyInfoBase').show();$('#mib').next().show();" class="weui-btn weui-btn_primary" style="margin-top: 10px;">修改基本信息</a>
    <a href="javascript:$('#modifyFromBase').submit();" style="display: none;" class="weui-btn weui-btn_warn">确定修改</a>
</form>
<!-- 基本信息end -->
    <hr>
<div class="page__bd">
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <div class="weui-uploader">
                        <div class="weui-uploader__hd">
                            <p class="weui-uploader__title">店铺主图</p>
                            <div onclick="$('#reupMainImg').toggle();$('#mainImg').toggle();$(this).hide();$(this).next().show();" style="color:#2900ff;">重新上传</div>
                            <div onclick="$('#upImgDialog').toggle();" style="display: none;color:red">确定上传</div>
                        </div>
                        <div class="weui-uploader__bd"  id="mainImg">
                            <ul class="weui-uploader__files">
                                <li class="weui-uploader__file">
                                	<img src="${shop.mainImage}" onclick="viewImg('${shop.mainImage}',0)" width="100%" height="100%"/>
                                </li>
                            </ul>
                        </div>
                        <div class="weui-uploader__bd" id="reupMainImg" style="display: none;">
                            <ul class="weui-uploader__files" id="uploaderFiles0">
                            </ul>
                            <div class="weui-uploader__input-box">
                                <input onclick="selectSlideImage(0)" class="weui-uploader__input" type="file" accept="image/*" multiple="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>        
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <div class="weui-uploader">
                        <div class="weui-uploader__hd">
                            <p class="weui-uploader__title">轮播图</p>
                            <div onclick="$('#reupSlideImg').toggle();$('#slideImg').toggle();$(this).hide();$(this).next().show();" style="color:#2900ff;">重新上传</div>
                            <div onclick="$('#upImgDialog').toggle();" style="display: none;color:red">确定上传</div>
                        </div>
                        <div class="weui-uploader__bd" id="slideImg">
                            <ul class="weui-uploader__files">
                            </ul>
                        </div>
     	                <div class="weui-uploader__bd" id="reupSlideImg" style="display: none;">
                            <ul class="weui-uploader__files" id="uploaderFiles1">
                            </ul>
                            <div class="weui-uploader__input-box">
                                <input onclick="selectSlideImage(1)" class="weui-uploader__input" type="file" accept="image/*" multiple="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <div class="weui-uploader">
                        <div class="weui-uploader__hd">
                            <p class="weui-uploader__title">菜单图</p>
                            <div onclick="$('#reupMenuImg').toggle();$('#menuImg').toggle();$(this).hide();$(this).next().show();" style="color:#2900ff;">重新上传</div>
                            <div onclick="$('#upImgDialog').toggle();" style="display: none;color:red">确定上传</div>
                        </div>
                        <div class="weui-uploader__bd" id="menuImg">
                            <ul class="weui-uploader__files" id="menuImg">
                            </ul>
                        </div>
         			    <div class="weui-uploader__bd" id="reupMenuImg" style="display: none;">
                            <ul class="weui-uploader__files" id="uploaderFiles2">
                            </ul>
                            <div class="weui-uploader__input-box">
                                <input onclick="selectSlideImage(2)" class="weui-uploader__input" type="file" accept="image/*" multiple="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="weui-cells weui-cells_form">
        	<div class="weui-cell weui-cell_switch">
                <div class="weui-cell__bd">绑定码:&nbsp;&nbsp;&nbsp;<span style="color: red;">5G6F</span></div>
                <div class="weui-cell__ft">
                    <label for="switchCP" class="weui-switch-cp">
                        <input id="switchCP" class="weui-switch-cp__input" type="checkbox" checked="checked">
                        <div class="weui-switch-cp__box"></div>
                    </label>
                </div>
            </div>
        </div>
        	<div class="weui-cell"><a href="http://lbs.qq.com/tool/getpoint/">ssss</a></div>
        	<div class="weui-cell"></div>
    </div>
</div>

	<script>
	var time = ${timestamp};
	$(function(){
		var sI = "${shop.slideImage}";
		jQuery.each("${shop.slideImage}".split(","), function(i,val) {
			if(val){
				$("#slideImg").find("ul").append('<li class="weui-uploader__file"><img src="'+val+'" onclick="viewImg(\'${shop.slideImage}\','+i+')" width="100%" height="100%"/></li>');
			}
		});
		jQuery.each("${shop.menuImage}".split(","), function(i,val) {
			if(val){
				$("#menuImg").find("ul").append('<li class="weui-uploader__file"><img src="'+val+'" onclick="viewImg(\'${shop.menuImage}\','+i+')" width="100%" height="100%"/></li>')
			}
		});
	})
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
			alert(res)
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		});
		function subImgForm(){
		}
		function viewImg(arr,i){
			wx.previewImage({
				current: arr.split(",")[i], // 当前显示图片的http链接
				urls: arr.split(",") // 需要预览的图片http链接列表
			});
		}
		function selectSlideImage(i){
			wx.chooseImage({
				count: 1, // 默认9
				sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
				sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
				success: function (res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					img[img.length]=localIds+"";
					$("#uploaderFiles"+i).append('<li class="weui-uploader__file"><img src="'+localIds
							+'" style="width: 100%;height: 100%"  onclick="viewImg('+img+","+img.length+')"/></li>');
					$("#picSize").html(img.length);
					wx.uploadImage({
						localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
						isShowProgressTips: 1, // 默认为1，显示进度提示
						success: function (res) {
							var serverId = res.serverId; // 返回图片的服务器端ID
							if(i==0){
								$("#mainImage").val(serverId);
								$("#uploaderFiles0").next().hide();
							}if(i==1){
								$("#slideImage").val($("#slideImage").val()+","+serverId);
							}if(i==2){
								$("#menuImage").val($("#menuImage").val()+","+serverId);
							}
						}
					});
				}
			});			
		}
	</script>
</body>

</html>
