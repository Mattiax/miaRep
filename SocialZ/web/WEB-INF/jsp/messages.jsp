<%-- 
    Document   : messages
    Created on : 16-mar-2018, 18.55.28
    Author     : MATTI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messages.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
        <title>JSP Page</title>
    </head>

    <ul id="mainUL">
        <li id="mainLI"><a class="active">Messaggi</a></li>
        <li id="mainLI"><a href="#news">Gruppi</a></li>
        <li id="personalAreaLI">
            <ul id="subUL">
                <li id="dropLI"><a id="test" href="personalArea">Account</a></li>
                <li id="dropLI"><a id="test" href="#">Esci</a></li>
            </ul>
        </li>
    </ul>

    <body>

        <div class="split left">
            <input name="mittente" value="cane@cane.it">cane
            <table id="tableId">
                <h1>Contatti</h1>  
                <th>Numero</th>
                <th>IdPersona</th>
                <th>Nome</th>
                <th>Reddito</th>
                    <c:forEach var="u" items="${listaUtenti}" varStatus="status" >
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${u.getNome()}</td>
                        <td>${u.getCognome()}</td>
                        <td name="destinatario">${u.getEmail()}</td>
                    </tr>
                </c:forEach>  
            </table>
        </div>

        <div class="split right" id="rightpanel">
            <h1>Messaggi</h1>  
            <div id="storicoChat">
                
            </div>
            
            <div id="componiMessaggio">
                <textarea id="inputMess" rows="4" cols="50"></textarea>
                <button type="submit" id="invioMess">></button>
                <button id="scriviMessaggio" name="nuovoMessaggio">+</button>
            </div>
        </div>
    </body>
</html>
