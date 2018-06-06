<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商户平台</title>

<link href="//res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css" rel="stylesheet"/>

</head>
<style>
.title{
    font-size: 25px;
    text-align: center;
	margin-bottom:5%;
}
.weui-input{
	text-align: center;
	font-size: 30px;
	text-transform: uppercase;
	color:#00BFFF;
}
.mybutton{
	margin-top:5%;
}
.des{
	margin:5%;
}
.error{
	color:red;
}
</style>
<body>
<div style="margin-top: 30%">
<div class='title'>商家码</div>
<div class="weui-cell">
	<div class="weui-cell__bd">
		<input id="code" class="weui-input" type="text" placeholder="请输绑商家码" maxlength="4">
	</div>
</div>
<hr>
<a href="javascript:binding();" class="weui-btn weui-btn_primary mybutton">绑定</a>
</div>
<div id="error" class="error"></div>
<div class="des">
申请或忘记商家码请联系管理员!
<br>电话：<span style="color:blue;">173-4655-9443</span>

</div>
	<script>
	function binding(){
		$.ajax({
			  type: 'POST',
			  url: "${src}/centercontroller/binding",
			  data: {shopCode:$("#code").val()},
			  success: function(res){
				if(res==1){
				  window.location.href="${src}/centercontroller/myShop"
				}else if(res==0){
					$("#error").html("请输入正确的商家码！")
				}else{
					$("#error").html("你是谁？请关注公账号：cocoNet2018")
				}
				  
			  }
		});
	}
	</script>	
</body>

</html>
