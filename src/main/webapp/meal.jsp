<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add or update meal</title>
</head>
<body>
<h2>Add or update meal</h2>
<form method="POST" action='meals' name="formAddMeal">
    <label for="description"> Description:<br/></label>
    <input id="description" type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
    <label for="calories">Calories:<br/></label>
    <input id="calories" type="text" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
    <label for="localDateTime">Date & Time:<br/></label>
    <input id="localDateTime" type="datetime-local" name="localDateTime" value="${meal.dateTime}"/> <br/>
    <br/>
    <input type="hidden" name="id" value="${meal.id}">
    <input type="submit" value="Submit"/>
</form>
<p><a href="meals">To meals list</a></p>
</body>
</html>