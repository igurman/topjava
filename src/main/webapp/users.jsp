<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Users</h2>
    <a href="users?action=create">Add User</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Почта</th>
            <th>Роли</th>
            <th>Калории</th>
            <th>Активный</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>

            <tr role="row" class="odd">
                <td>${user.name}</td>
                <td>
                    <a href="mailto:${user.email}">${user.email}</a>
                </td>
                <td>
                    <c:forEach items="${user.roles}" var="role">
                        ${role.name()}
                    </c:forEach>
                </td>
                <td>${user.caloriesPerDay}</td>
                <td>
                    <input type="checkbox" ${user.enabled ? 'checked' : ''}>
                </td>

                <td><a href="users?action=update&id=${user.id}">Update</a></td>
                <td><a href="users?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>