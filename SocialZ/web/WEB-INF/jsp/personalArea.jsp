<%-- 
    Document   : personalArea
    Created on : 12-mar-2018, 11.34.40
    Author     : mattia.musone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Area personale</title>
    </head>
    <body>
        <legend>Dati personali</legend>
        <p> nome : ${nome}; </p>
        <p> cognome : ${cognome};</p>
        <p> data : ${dataNascita};</p>
        <p> indirizzo : ${indirizzo};</p>
        <p> email : ${email};</p>
        <p> password : ${password};</p>
        <p> sesso : ${sesso};</p>
    </body>
</html>
