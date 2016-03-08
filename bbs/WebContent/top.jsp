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
	<a href="createmessage">新規投稿</a>
	<a href="usermanager">ユーザー管理</a>
	<a href="logout">ログアウト</a>
</div>
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
<div class="messages">
	<c:forEach items="${ messages }" var="message">
		<div class="message">
			<div class="title"><span class="title"><c:out value="★${ message.title }" /></span></div>
			<div class="text"><span class="text"><c:out value="${ message.text }" /></span></div>
			<div style="display:inline-flex">
				<div class="name"><span class="name"><c:out value="${ message.name }" /></span></div>
				<div class="date"><fmt:formatDate value="${ message.insertDate }" pattern="yyyy/MM/dd HH:mm:ss" /></div>
			</div>
		</div>
		<div class="delete">
			<c:if test="${ branch_id == 1 && department_id == 2 || branch_id == message.branch_id && department_id == 3 }">
				<form action="usermanager" method="post"><br />
					<input type="hidden" name="deletemessage" value="${ message.message_id }" />
					<input type="submit" value="削除"><br />
				</form>
			</c:if>
		</div>
		<div class="comments">
			<c:forEach items="${ comments }" var="comment">
				<c:if test="${ message.message_id == comment.message_id }">
					<div class="comment">
						<div class="text"><span class="text"><c:out value="${ comment.text }" /></span></div>
						<div style="display:inline-flex">
							<div class="name"><span class="name"><c:out value="${ comment.name }" /></span></div>
							<div class="date"><fmt:formatDate value="${ comment.insertDate }" pattern="yyyy/MM/dd HH:mm:ss" /></div>
						</div>
					</div>
					<div class="delete">
						<c:if test="${ branch_id == 1 && department_id == 2 || branch_id == comment.branch_id && department_id == 3 }">
							<form action="usermanager" method="post"><br />
								<input type="hidden" name="deletecomment" value="${ comment.id }" />
								<input type="submit" value="削除"><br />
							</form>
						</c:if>
					</div>
					<c:out value="----------" />
				</c:if>
			</c:forEach>
		</div><br />
			<div class="createcomment">
				<form action="home" method="post"><br />
					<label for="text">コメント</label><br />
					<textarea name="text" cols="40" rows="2" id="text" maxlength="500" required></textarea>
					<input type="submit" value="投稿" /><br />
					<input type="hidden" name="message_id" value="${ message.message_id }">
				</form>
			</div><br />
	</c:forEach>
</div>

</div>
</body>
</html>