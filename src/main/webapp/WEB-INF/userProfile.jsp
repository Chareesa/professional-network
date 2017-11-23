<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Questions Dashboard</title>
    <link rel="stylesheet" href="/css/style.css" />
</head>
<body>
<div class="container">
    <a href="/home">My Profile</a>
    <a href="/logout">Logout</a>
    <h1><c:out value="${name}"/></h1>
    <h3>Description: <c:out value="${description}"/></h3>
</div>
</body>
</html>