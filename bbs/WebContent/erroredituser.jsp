<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
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
<c:out value="${ presentLogin_id }" /><br />
<c:out value="${ presentName }" /><br />
<form action="edituser" name="createuser" method="post"><br />
	<input type="hidden" name="id" value="${ inputData.id }" />
	<input type="hidden" name="presentLogin_id" value="${ presentLogin_id }" />
	<input type="hidden" name="presentName" value="${ presentName }" />
	<label for="login_id">ログインID</label>
	<input name="login_id" id="login_id" value="${ inputData.login_id }"  /><br />

	<label for="password">パスワード</label>
	<input type="password" name="password"  id="password" /><br />
	<label for="passwordConfirm">パスワード(確認)</label>
	<input type="password" name="passwordConfirm" id="passwordConfirm" /><br />
	<input type="hidden" name="presentPassword" value="${ inputData.password }" />

	<label for="name">名称</label>
	<input name="name" id="name" value="${ inputData.name }" /><br />

	<label for="branch_id" >支店</label>
		<select name="branch_id">
			<option value="0">選択してください</option>
			<option value="1">本社</option>
			<option value="2">支店A</option>
			<option value="3">支店B</option>
			<option value="4">支店C</option>
		</select>
	<label for="department_id">部署・役職</label>
		<select name="department_id">
			<option value="0">選択してください</option>
			<option value="1">人事総務部</option>
			<option value="2">情報セキュリティ部</option>
			<option value="3">店長</option>
			<option value="4">社員</option>
		</select>
	<input type="submit" value="登録" /><br />
</form><br />

<form action="deleteuser" name="deleteuser" method="post"><br />
	<input type="hidden" name="id" value="${ editUser.id }">
	<input type="submit" value="ユーザー削除"><br />
</form>

<a href="usermanager">戻る</a>
</div>
</body>
</html>