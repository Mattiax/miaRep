<%-- 
    Document   : messaggiGruppo
    Created on : 23-mar-2018, 11.41.16
    Author     : MATTI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messages.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/newcss.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/messaggiGruppo.js"></script>
        <title>JSP Page</title>
    </head>

        <ul id="nav"></ul>
        <input type="checkbox" id="menu-toggle"/>
        <label id="trigger" for="menu-toggle"></label>
        <label id="burger" for="menu-toggle"></label>
        <ul id="menu">
            <li><a href="messages">Messaggi</a></li>
            <li><a href="messaggiGruppo">Gruppi</a></li>
        </ul>
        <div class="split left">
            <table id="tabellaGruppi">
                <h1>Contatti</h1>  
                <th>Nome</th>
                    <c:forEach var="lst" items="${listaGruppi}" >
                    <tr>
                        <td name="destinatario">${lst}</td>
                    </tr>
                </c:forEach>  
            </table>
        </div>
        <div class="split right" id="rightpanel">
            <h1>Messaggi</h1>  
            <div class="split up" id="storicoChat">

            </div>

            <div class="split down" id="componiMessaggio">
                <span id="dest" value="" hidden="true"></span>
                <textarea id="inputMess" rows="4" cols="50"></textarea>
                <button type="submit" id="invioMess">></button>
                <a id="nuovoGruppo" name="nuovoMessaggio" href="nuovoGruppo">+</a>
            </div>
        </div>
    </body>
</html>
