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

<body>
	<a href="javascript:;" class="weui-btn weui-btn_primary">绿色按钮</a>
	<c:forEach items="${listOrder}" var="i">
		<c:out value="${i.phone}" />
		<c:out value="${i.arriveTime}" />
		<c:out value="${i.people}" />
		<c:out value="${i.remark}" />
		<c:out value="${i.status}" />
		
		---
		${i.formId}+${i.id}+${i.openId}
		
		<a href="${src}/centercontroller/operateOrder?id=${i.id}">test</a>
	</c:forEach>
	<div class="weui-tabbar">
        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on">
            <span style="display: inline-block;position: relative;">
                <img src="./images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                <span class="weui-badge" style="position: absolute;top: -2px;right: -13px;">8</span>
            </span>
            <p class="weui-tabbar__label">微信</p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item">
            <img src="./images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">通讯录</p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item">
            <span style="display: inline-block;position: relative;">
                <img src="./images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
                <span class="weui-badge weui-badge_dot" style="position: absolute;top: 0;right: -6px;"></span>
            </span>
            <p class="weui-tabbar__label">发现</p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item">
            <img src="./images/icon_tabbar.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label">我</p>
        </a>
    </div>
	<script>
		
	</script>
</body>

</html>
