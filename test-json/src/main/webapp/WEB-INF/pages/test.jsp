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
	<span onclick="test()">tesst</span>
	<script>
	function test(){
		console.log(1)
		$.ajax({
			  type: 'POST',
			  url: "${src}/centercontroller/startService",
			  data: "<xml>  <ToUserName>< ![CDATA[toUser] ]></ToUserName>  <FromUserName>< ![CDATA[fromUser] ]></FromUserName>  <CreateTime>1348831860</CreateTime>  <MsgType>< ![CDATA[text] ]></MsgType>  <Content>< ![CDATA[this is a test] ]></Content>  <MsgId>1234567890123456</MsgId>  </xml>",
			  success: function(res){
				  console.log(res)
			  }
		});
	}
	</script>	
</body>

</html>
