<%-- 
    Document   : utenti
    Created on : 1-apr-2018, 17.56.04
    Author     : MATTI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hamburger.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/richieste.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/richiesteAmm.js"></script>
        <title>Utenti</title>
    </head>
    <body>
        <ul id="nav"></ul>
        <input type="checkbox" id="menu-toggle"/>
        <label id="trigger" for="menu-toggle"></label>
        <label id="burger" for="menu-toggle"></label>
        <ul id="menu">
            <li><a href="richiesteAmm">Richieste</a></li>
            <li><a href="gruppi">Gruppi</a></li>
            <li><a href="utenti">Utenti</a></li>
            <li><a href="esci">Esci</a></li>
        </ul>
        <h1>Utenti</h1>
        <table>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Email</th>
                <c:forEach var="l" items="${listaUtenti}" varStatus="status" >
                <tr>
                    <td>${l.getNome()}</td>
                    <td>${l.getCognome()}</td>
                    <td class="email">${l.getEmail()}</td>
                    <td><button class="eliminaUtente">Elimina</button></td>
                </tr>
            </c:forEach>  
        </table>
    </body>
</html>
