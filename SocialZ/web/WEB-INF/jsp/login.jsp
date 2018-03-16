<%-- 
    Document   : login
    Created on : 12-mar-2018, 11.23.20
    Author     : mattia.musone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accesso</title>
    </head>
    <body>
        <h1>Accedi</h1>
        <form action="doLogin">
            <p><input type="text" name="email"></p>
            <p><input type="text" name="password"></p>
            <p><input type="submit" value="ACCEDI"></p>
        </form>
    </body>
</html>
