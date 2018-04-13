<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="meals?action=new">Add</a></h3>
<h2>Meals</h2>
<hr>
<table border="1" width="80%" >
    <caption>Table of meals</caption>
    <thead>
    <tr>
        <th>Description</th>
        <th>Date</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr bgcolor="${meal.exceed ? 'red' : 'green'}">
            <td hidden>
                <c:out value="${meal.id}"/>
            </td>
            <td>
                <c:out value="${meal.description}"/>
            </td>
            <td>
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
            </td>
            <td>
                <c:out value="${meal.calories}"/>
            </td>
            <td>
                <a href="meals?action=update&id=${meal.id}">Update</a>
            </td>
            <td>
                <a href="meals?action=delete&id=${meal.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
