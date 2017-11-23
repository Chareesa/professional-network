<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login and registration</title>
</head>
<body>
<fieldset>
    <legend>Registration</legend>
    <c:if test="${isIncorrect}">
        <p class="incorrect" style="color: red; font-weight: 700">${message}</p>
    </c:if>
    <%--<form:errors path="user.*"/>--%>
    <%--${errors}--%>
    <form:form method="POST" action="/register/user" modelAttribute="user">
        <p><form:label path="name">Name:*</form:label>
            <form:input path="name" required="required"/></p>

        <p><form:label path="email">Email:*</form:label>
            <form:input type= "email" path="email" required="required"/></p>
        <p style="font-size:x-small">Password should be at least 8 characters</p>
        <p><form:label path="password">Password:*</form:label>
            <form:input type="password" path="password" required="required" pattern=".{8,}" title="8 chars minimum"/></p>
        <p>
            <label for="c_password">Confirm Password:*</label>
            <input type="password" name="c_password" id="c_password">
        </p>
        <p><form:label path="description">Description:*</form:label>
            <form:input path="description" required="required"/></p>
        <input type="submit" value="Register">
    </form:form>
</fieldset>

<fieldset>
    <legend>Login</legend>
    <c:if test="${loginFailed}">
        <p class="incorrect" style="color: red; font-weight: 700">${loginMessage}</p>
    </c:if>
    <form action="/login/user" method="POST">
        <p>Email:<input type="text" name = "email">
        <p>Password:<input type="password" name = "password">
            <input type = "submit" value="Login">
    </form>
</fieldset>
</body>
</html>