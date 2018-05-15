<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="common/include.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>注册</title>

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

</head>
<style>
.register {
	border: 1px solid #6699ff;
	width: 15%;
	text-align: center;
	padding: 5px 0px;
	border-radius: 5px;
	cursor: pointer;
	background-color: #6699ff;
	color: #FFF;
	display: inline-block;
}

.login {
	margin: 10px 0px;
	text-decoration: underline;
	color: #66ccff;
	cursor: pointer;
	display: inline-block;
	float : right;
}
.error{
	color:red;
	font-size: 12px;
}
</style>
<body>
	<h1 style="text-align: center;">注册</h1>
	<div
		class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
		<form id="register" role="form" action="${ctx}/registerS" method="POST">
			<div class="row">
				<div class="form-group">
					<label>用户名:</label> 
					<input class="form-control" placeholder="" name="username" value="" onblur="check(this)"> 
					<span class="error"></span>
				</div>
				<div class="form-group">
					<label>密码:</label> 
					<input class="form-control" type="password" placeholder="" name="password" value=""> 
					<span class="error"></span>
				</div>
				<div class="form-group">
					<label>确认密码:</label> 
					<input class="form-control" type="password" placeholder="" name="password2" value="">
					<span class="error"></span>
				</div>
				<div class="form-group">
					<label>手机号:</label> 
					<input class="form-control" placeholder="" name="phone" value="">
					<span class="error"></span>
				</div>
			</div>
		</form>
		<div class="row">
			<div class='register' onclick="register()">注册</div>
			<div class='login' onclick="login()">已有账户登陆</div>
		</div>
	</div>
	<script src="${ctx}/resources/js/jquery-1.11.1.min.js"></script>
	<script>
		var name = 0;
		function login() {
			window.location.href="${ctx}/login";
		}
		function check(e) {
			$.ajax({
               type: "POST",
               url: "${ctx}/checkUname",
               data: "uname="+e.value,
               success: function(data){
            	   console.log(data)
            	   	if(data==1){
                  		$(e).parent().find('span').text("用户名已存在！");
                  		name = 0;
            	   	}else{
	                 	$(e).parent().find('span').text("");
                  		name = 1;
            	   	}
               }
            });
		}
		function register() {
			if(name==0){
				$("[name='username']").focus();
				return false;
			}
			var pass = $("[type='password']");
			$(pass[1]).parent().find('span').text("");
			if(pass[0].value!=pass[1].value){
				$(pass[1]).parent().find('span').text("两次输入的密码不一致！");
				$(pass[1]).focus();
				return false;
			}
			var phone = $("[name='phone']").val();
	 		$("[name='phone']").parent().find('span').text("");
			var pattern = /^1[34578]\d{9}$/; 
		 	if(!pattern.test(phone)){
		 		$("[name='phone']").parent().find('span').text("请输入正确的手机号码！");
		 		$("[name='phone']").focus();
				return false;
		 	}
		 	$("#register").submit();
		}
	</script>
</body>

</html>
