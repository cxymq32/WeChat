<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="src" value="${pageContext.request.contextPath}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}/admin"/>

<!-- Jquery -->
<script src="${src}/resources/jquery-3.3.1/jquery-3.3.1.min.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" type="text/javascript"></script>	


<!-- CSS -->
<link href="${src}/resources/css/bootstrap.css" rel="stylesheet"/>
<link href="${src}/resources/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${src}/resources/css/bootstrap.css.map" rel="stylesheet"/>
<link href="${src}/resources/css/styles.css" rel="stylesheet"/>
<link href="${src}/resources/css/bootstrap-table.css" rel="stylesheet"/>

<!-- JS 
<script src="${src}" type="text/javascript"></script>
<script src="${src}" type="text/javascript"></script>
-->
<style>

</style>
<script>
var type = 1;
    function toIndex() {
        window.location.href = '${ctx}/';
    }
    //退出登录
    function loginOut(){
    	 window.location.href = '${ctx}/loginOut';
    }
    //登录确认
    function login(){
        $('#login_fm').form('submit',{
            url: '${ctx}/login',
            onSubmit: function(){
                return jQuery('#login_fm').form('enableValidation').form('validate');
            },
            success: function(result){
            	console.log(result);
            	if(result=='true'){
	            	toIndex();
            	}else{
            		$.messager.alert('消息','密码错误！','info');
            	}
            }
        });
    }

</script>
<style>
</style>

