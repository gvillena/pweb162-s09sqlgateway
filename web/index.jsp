<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Plataforma Web - SQLite Gateway</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    
    <c:if test="${sqlStatement == null}">
        <c:set var="sqlStatement" value="" />
    </c:if>
    
    <h1>SQLite Gateway</h1>
    <p>Ingrese una sentencia SQL y haga clic en el boton 'Ejecutar'.</p>

    <p><b>Sentencia SQL:</b></p>
    <form action="sqlGateway" method="post">
        <textarea name="sqlStatement" cols="60" rows="8">${sqlStatement}</textarea>
        <input type="submit" value="Ejecutar">
    </form>

    <p><b>Resultado SQL:</b></p>
    ${sqlResult}
    
</body>
</html>