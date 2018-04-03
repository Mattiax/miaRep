<%-- 
    Document   : gruppi
    Created on : 1-apr-2018, 17.55.58
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
        <title>Gruppi</title>
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
        <h1>Gruppi</h1>
        <table>
            <th>Nome</th>
            <th>Descrizione</th>
                <c:forEach var="g" items="${listaGruppi}" varStatus="status" >
                <tr>
                    <td class="nome">${g[0]}</td>
                    <td>${g[1]}</td>
                    <td><button class="eliminaGruppo">Elimina</button></td>
                </tr>
            </c:forEach>  
        </table>
    </body>
</html>
