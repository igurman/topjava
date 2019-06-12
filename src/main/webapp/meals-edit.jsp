<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Editor</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="meals">back to list meals</a></h3>
<hr>
<h3>${meal.id != null ? "Edit" : "Create"} meal</h3>

<form method="post" action="meals">
    <input type="hidden" name="action" value="${meal.id != null  ? "update" : "create"}" >
    <input type="hidden" name="id" value="${meal.id}" >
    <label>Дата <input type="date" name="date" value="${meal.dateTime.toLocalDate()}"> Время <input type="time" name="time" value="${meal.dateTime.toLocalTime()}"></label><br>
    <label>Наименование <input type="text" name="name" value="${meal.description}"></label><br>
    <label>Калории <input type="number" name="calories" value="${meal.calories}"></label><br>
    <button type="submit">Update</button>
</form>
<hr>
<br>
<style>
    label {margin: 4px 0; display: inline-block;}
</style>
</body>
</html>
