<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登陆</title>

</head>
<style>
.reg{
	float: right;
	margin-right: 20px;
	border: 1px solid #30a5ff;
	padding: 5px 15px;
	border-radius: 5px;
	cursor: pointer;
}
.errMsg{
	color: red;
	font-size: 10px;
}
</style>
<body>
	
	<div class="row">
		<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">登陆</div>
				<div class="panel-body">
					<form role="form" action="${ctx}/login" method="POST">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="用户名" name="username" type="text" autofocus="">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="密码" name="password" type="password">
							</div>
							<div class="form-group">
								<span class='errMsg'>${errMsg}</span>
							</div>
							<div class="checkbox">
								<label>
									<input type="checkbox" value="">记住我
								</label>
							</div>
							<button type="submit" class="btn btn-primary">登陆</button>
							<a class='reg' href='${ctx}/register'>注册</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div><!-- /.col-->
	</div><!-- /.row -->	
	
		

	<script src="${src}/resources/js/jquery-1.11.1.min.js"></script>
	<script src="${src}/resources/js/bootstrap.min.js"></script>
	<script src="${src}/resources/js/chart.min.js"></script>
	<script src="${src}/resources/js/easypiechart.js"></script>
	<script src="${src}/resources/js/bootstrap-datepicker.js"></script>
	<script>
		!function ($) {
			$(document).on("click","ul.nav li.parent > a > span.icon", function(){		  
				$(this).find('em:first').toggleClass("glyphicon-minus");	  
			}); 
			$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
	</script>	
</body>

</html>
