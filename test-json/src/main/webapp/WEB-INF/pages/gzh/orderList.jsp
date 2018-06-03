<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
.remark{
  font-size: 10px;
}
</style>
<body>
<div class="weui-navbar">
	<div id="ordering" class="weui-navbar__item">预约中 </div>
	<div id="orderpass" class="weui-navbar__item">已过期</div>
</div>
<div style="text-align: center;">${listOrder[0].shopName}</div>
<div class="weui-cells" style="margin-top: -2px">
<c:forEach items="${listOrder}" var="i">
	<%-- ${i.formId}+${i.id}+${i.openId}+${i.status}+${i.remark}+${i.arriveTime}+${i.phone} --%>
<div class="weui-cell">
	<div class="weui-cell__bd">
		<p>${i.arriveTime}分(<span style="color:red">${i.people}人</span>)，电话：<span style="color:blue">${i.phone}</span></p>
		<p class='remark'>留言：${i.remark}</p>
	</div>
	<div class="weui-cell__ft">
		<c:if test="${state==1}">
			<c:if test="${i.status==0}">
				<a href="${src}/centercontroller/operateOrder?id=${i.id}&state=1" class="weui-btn weui-btn_mini weui-btn_primary">接单</a>
			</c:if>
			<c:if test="${i.status==1}">
				<span href="#" class="weui-btn weui-btn_mini weui-btn_default">已确认</span>
			</c:if>
			<c:if test="${i.status==2}">
				<a href="#" class="weui-btn weui-btn_mini weui-btn_default">已取消</a>
			</c:if>
		</c:if>
	</div>
</div>
</c:forEach>
</div>
<script>
$(function(){
	var state = "${state}";
	if(state=="2"){
		$("#orderpass").toggleClass("weui-bar__item_on");
	}else {
		$("#ordering").toggleClass("weui-bar__item_on");
	}
})
	$("#ordering").click( function () { 
		$(this).toggleClass("weui-bar__item_on");
		$("#orderpass").toggleClass("weui-bar__item_on");
		window.location.href="${src}/centercontroller/orderList?state=1"
	});
	$("#orderpass").click( function () {
		$("#ordering").toggleClass("weui-bar__item_on");
		$(this).toggleClass("weui-bar__item_on");
		window.location.href="${src}/centercontroller/orderList?state=2"
	});
</script>
</body>

</html>
