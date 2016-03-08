<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>新規投稿</title>
</head>
<body>
<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${ errorMessages }" var="message">
				<li><c:out value="${ message }" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session" />
</c:if>
<form action="createmessage" method="post"><br />
	<label for="title">件名</label>
	<input name="title" value="${ inputData.title }" id="title" size="40" /><br />

	<label for="text">本文</label><br />
	<textarea name="text" cols="50" rows="10" id="text">${ inputData.text }</textarea><br />

	<label for="category">カテゴリー</label>
	<input name="category" value="${ inputData.category }" id="category" /><br />

	<input type="submit" value="登録" /><br />
	<a href="home">戻る</a>
</form>

</div>
</body>
</html>