<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../common/include.jsp"%>
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
	<c:forEach items="${listOrder}" var="i">
		<c:out value="${i}" />
	</c:forEach>
	<script>
		
	</script>
</body>

</html>
