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

    <h3>Users you may want to connect with:</h3>

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>
                    <a href="/user/profile/${user.id}"><c:out value="${user.name}"/></a>
                </td>
                <td>
                    <a href="/user/connect/${user.id}">Connect</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>