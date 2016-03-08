<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>掲示板システム</title>
</head>
<body>
<div class="main-contents">

<div class="header">
	<a href="createuser">ユーザー新規登録</a>
	<a href="home">ホーム</a>
</div>

<div class="userlists">
	<c:forEach items="${ userlists }" var="userlist">
			<div class="id"><span class="id"><c:out value="ログインID：${ userlist.login_id }" /></span></div>
			<div class="name"><span class="name"><c:out value="名前：${ userlist.name }" /></span></div>
			<div class="branch_name"><span class="branch_name"><c:out value="支店：${ userlist.branch_name }" /></span></div>
			<div class="department_name"><span class="department_name"><c:out value="部署・役職：${ userlist.department_name }" /></span></div>
			<div class="status">
			<div style="display:inline-flex">
				<form action="edituser" method="get"><br />
					<input type="hidden" name="user_id" value="${ userlist.id }" />
					<input type="submit" value="編集"><br />
				</form>
				<c:if test="${ userlist.status  == 0 }">
					<form action="usermanager" method="post"><br />
						<input type="hidden" name="user_id" value="${ userlist.id }" />
						<input type="hidden" name="status" value="1" />
						<input type="submit" onClick="return confirm('ユーザーを停止しますか');" value="停止"><br />
					</form>
				</c:if>
				<c:if test="${ userlist.status == 1 }">
					<c:out value="停止中" />
					<form action="usermanager" method="post"><br />
						<input type="hidden" name="user_id" value="${ userlist.id }" />
						<input type="hidden" name="status" value="0" />
						<input type="submit" onClick="return confirm('ユーザーを復活しますか');" value="復活"><br />
					</form>
				</c:if>
			</div>
		</div><br />
	</c:forEach>
</div>

</div>
</body>
</html>