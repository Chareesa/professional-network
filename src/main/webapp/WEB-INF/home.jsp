<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Questions Dashboard</title>
    <link rel="stylesheet" href="/css/style.css" />
</head>
<body>
<div class="container">
    <a href="/users">All Users</a>
    <a href="/logout">Logout</a>
    <h1>Hi <c:out value="${name}"/></h1>

    <h3>Here is your profile description:</h3>
    <p><span style="border: 2px solid black; padding: 6px"><c:out value="${description}"/></span></p>

    <h3>Your Professional Network:</h3>
    <div style="border: 2px solid black; padding: 6px">
        <c:if test="${fn:length(network) eq 0}">
            <p><i>Oh! Looks like you haven't made any connections yet!</i></p>
            <p><i>Go <a href="/users">here</a> to add some.</i></p>
        </c:if>
        <c:forEach var="person" items="${network}">
            <p><a href="/user/profile/${person.id}"><c:out value="${person.name}"/></a></p>
        </c:forEach>
    </div>

    <h3>Invitations:</h3>

    <c:if test="${fn:length(invitations) eq 0}">
        <p><i>Looks like you don't have any invitations at the moment!</i></p>
    </c:if>
    <c:if test="${fn:length(invitations) > 0}">
        <p>The following people asked you to be in their network:</p>
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="invitation" items="${invitations}">
                <tr>
                    <td>
                        <c:out value="${invitation.name}"/>
                    </td>
                    <td>
                        <a href="/user/accept/${invitation.id}">Accept Invite</a>
                        <a href="/user/ignore/${invitation.id}">Ignore</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>