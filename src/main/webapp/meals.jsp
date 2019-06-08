<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Время</th>
        <th>Наименование</th>
        <th>Калории</th>
    </tr>

    <c:forEach items="${listMeals}" var="meal">
        <tr bgcolor="${meal.excess ? "red" : "green"}">
            <td>${meal.dateTime.format(localDateTimeFormat)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
