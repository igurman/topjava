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
        <th>Id</th>
        <th>Time</th>
        <th>Name</th>
        <th>Calories</th>
        <th>Remove</th>
    </tr>
    <c:forEach items="${listMeals}" var="meal">
        <tr bgcolor="${meal.excess ? "red" : "green"}">
            <td>${meal.id}</td>
            <td>${meal.dateTime.format(localDateTimeFormat)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">X</a></td>
        </tr>
    </c:forEach>
</table>
<hr>
<h3>Add new Meal</h3>
<form method="post" action="meals">
    <input type="hidden" name="action" value="create" >
    <label>Дата <input type="date" name="date"> Время <input type="time" name="time"></label><br>
    <label>Наименование <input type="text" name="name"></label><br>
    <label>Калории <input type="number" name="calories"></label><br>
    <button type="submit">Create</button>
</form>

<hr>
<h3>Update Meal</h3>
<form method="post" action="meals">
    <input type="hidden" name="action" value="update" >
    <label>Id <input type="text" name="id" value="1"></label><br>
    <label>Дата <input type="date" name="date" value="2015-05-30"> Время <input type="time" name="time" value="10:00"></label><br>
    <label>Наименование <input type="text" name="name" value="Завтрак"></label><br>
    <label>Калории <input type="number" name="calories" value="500"></label><br>
    <button type="submit">Update</button>
</form>
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
