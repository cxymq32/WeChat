<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商户平台</title>

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>可可科技</span>小程序后台</a>
				<ul class="user-menu">
					<li class="dropdown pull-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> 用户 <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<!-- <li><a href="#"><span class="glyphicon glyphicon-user"></span> 个人信息</a></li>
							<li><a href="#"><span class="glyphicon glyphicon-cog"></span> 设置</a></li> -->
							<li><a href="${ctx}/loginOut"><span class="glyphicon glyphicon-log-out"></span> 退出</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div><!-- /.container-fluid -->
	</nav>
		
	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>
		<ul class="nav menu">
			<li class="active"><a href="index.html"><span class="glyphicon glyphicon-dashboard"></span> 我的店铺</a></li>
			<li class=""><a href="test.html"><span class="glyphicon glyphicon-dashboard"></span> 测试</a></li>
		</ul>
	</div><!--/.sidebar-->
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
	<form role="form" action="${ctx}/modifyShop" enctype="mutltipart/form-data" method="post">
		<div style="display: none;">
			<input name="id" value="${shop.id}"/>
			<input name="userId" value="${shop.userId}"/>
		</div>
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
				<li class="active">店铺详情</li>
			</ol>
		</div><!--/.row-->
		
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">店铺详情</h1>
			</div>
		</div><!--/.row-->
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<label>店铺名称:</label>
					<input class="form-control" placeholder="" name="shopName" value="${shop.shopName}">
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label>联系电话:</label>
					<input class="form-control" placeholder="" name="phone" value="${shop.phone}">
				</div>
			</div>	
		</div><!--/.row-->	
		<div class="row">
				<div class="form-group col-md-3">
					<label>店铺地址:</label>
					<input class="form-control" placeholder="" name="adress" value="${shop.adress}">
				</div>
				<div class="form-group col-md-3">
					<label>坐标:</label>
					<input class="form-control" placeholder="xx.xxxxxx,xx.xxxxxx" name="location" value="${shop.location}">
					坐标查询：<a href="http://lbs.qq.com/tool/getpoint/" target="_blank">http://lbs.qq.com/tool/getpoint/</a>
				</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label>店铺门面图:</label>
					<input class="" name="files" type="file">
				</div>
				<div class="form-group">
					<label>轮播图1:</label>
					<input class="" name="files" type="file">
				</div>
				<div class="form-group">
					<label>轮播图2:</label>
					<input class="form-control" name="image2" placeholder="图片链接地址" value="${shop.image2}">
				</div>
				<div class="form-group">
					<label>轮播图3:</label>
					<input class="form-control" name="image3" placeholder="图片链接地址" value="${shop.image3}">
				</div>
			</div>	
		</div><!--/.row-->
		<div class="row">
			<div class="col-md-6">
				<label>优惠信息:</label>
				<textarea name="sale" style="width: 100%;height:100px" >${shop.sale}</textarea>
			</div>
		</div><!--/.row-->
		<div class="row">
			<div class="col-md-3">
				<button type="submit" class="center-block btn btn-primary">保存</button>
			</div>
			<div class="col-md-3">
				<button type="reset" class="center-block btn btn-default">取消修改</button>
			</div>
		</div>	
	</form>
	</div>	
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
