<%-- 
    Document   : richieste
    Created on : 31-mar-2018, 9.51.00
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/richieste.js"></script>
        <title>Richieste</title>
    </head>
    <body>
        <ul id="nav"></ul>
        <input type="checkbox" id="menu-toggle"/>
        <label id="trigger" for="menu-toggle"></label>
        <label id="burger" for="menu-toggle"></label>
        <ul id="menu">
            <li><img  width="50" src="img/no_photo.png"><a id="immagineProfilo" href="personalArea" >Profilo</a></li>
            <li><a href="messages">Messaggi</a></li>
            <li><a href="messaggiGruppo">Gruppi</a></li>
            <li><a href="richieste">Richieste</a></li>
            <li><a href="esci">Esci</a></li>
        </ul>
        <h1>Richieste</h1>
        <table>
            <th>Numero richiesta</th>
            <th>Richiedente</th>
            <th>Gruppo</th>
            <th>Descrizione</th>
                <c:forEach var="r" items="${listaRichieste}" varStatus="status" >
                <tr id="richiesta">
                    <td id="nRichiesta">${r.getId()}</td>
                    <td id="richiedente">${r.getRichiedente()}</td>
                    <td id="gruppo">${r.getGruppo()}</td>
                    <td>${r.getRichiesta()}</td>
                    <td><button id="approvaRichiesta">Approva</button></td>
                </tr>
            </c:forEach> 
        </table>
    </body>
</html>
