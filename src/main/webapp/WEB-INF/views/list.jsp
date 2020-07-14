<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 목록</title>
</head>
<body>
<h1>방명록</h1>
<br>방명록 전체 수 : ${cnt }, 방문자수 : ${cookieCount}
<br>
<br>

<c:forEach items="${list }" var="book">
${book.id }<br>
${book.name }<br>
${book.content }<br>
${book.regdate }<br>
<c:if test="${sessionScope.isAdmin == 'true' }">
<a href="delete?id=${book.id }">삭제</a><br><br>
</c:if>
</c:forEach>
<br>
<c:forEach items="${pageStartList }" var="page" varStatus="status">
	<a href="list?start=${page }">${status.index + 1 }</a>&nbsp;&nbsp;
</c:forEach>

<br>
<br>
<form action="write" method="post">
name:<input type="text" name="name"><br>
<textarea name="content" rows="6" cols="60"></textarea>
<br><input type="submit" value="등록">
</form>
</body>
</html>