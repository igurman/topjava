<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Meals manager</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>List meals</h2>
<table>
    <tr>
        <th>Time</th>
        <th>Name</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:forEach items="${listMeals}" var="meal">
        <tr bgcolor="${meal.excess ? "red" : "green"}">
            <td>${meal.dateTime.format(localDateTimeFormat)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">edit</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">X</a></td>
        </tr>
    </c:forEach>
</table>
<hr>
<p><a href="meals?action=edit">Add new Meal</a></p>
<hr>
<br>
<style>
    table {font-size: 0.85em;}
    td {padding: 5px 10px;}
    table a{color:#fff;}
    label {margin: 4px 0; display: inline-block;}
</style>
</body>
</html>
