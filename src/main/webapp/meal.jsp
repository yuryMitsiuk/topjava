<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="meals">List of meal</a></h3>
<h2>${param.action == 'new' ? 'Create meal' : 'Edit meal'}</h2>
<hr>
    <jsp:useBean id="meal" scope="request" class="ru.javawebinar.topjava.model.Meal"/>
    <form method="post">
        <table border="1" cellpadding="5">
            <c:if test="${meal.id != 0}">
                <input type="hidden" name="id" value="<c:out value="${meal.id}"/>"/>
            </c:if>
            <tr>
                <th>Description: </th>
                <td>
                    <input name="description" value="<c:out value='${meal.description}' />"/>
                </td>
            </tr>
            <tr>
                <th>Date: </th>
                <td>
                    <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
                </td>
            </tr>
            <tr>
                <th>Description: </th>
                <td>
                    <input type="number" min="0" step="1" name="calories" value="<c:out value='${meal.calories}' />"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="submit">Save</button>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
