<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h2>Meals</h2>

<table cellpadding="3" cellspacing="3" align="left" width="600px">
    <thead>
    <tr>
        <td>Дата/Время</td>
        <td>Описание</td>
        <td>Калории</td>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.excess ? 'red':'green'};">
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/>
            </td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr></tr>
    <tr>
        <td><a href="?action=insert">Add Meal</a></td>
    </tr>
    <tr>
        <td>Showing ${meals.size()} entries</td>
    </tr>
    </tfoot>
</table>
<div>
<p></p>

</div>
</body>
</html>
