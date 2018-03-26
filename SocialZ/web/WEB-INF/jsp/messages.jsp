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
        <li id="mainLI"><a href="messaggiGruppo" >Gruppi</a></li>
        <li id="personalAreaLI">
            <form action="personalArea">
                <ul id="subUL">
                    <li id="dropLI"><button id="test" type="submit">Account</button></li>
                    <li id="dropLI"><a id="test" href="#">Esci</a></li>
                </ul>
            </form>
        </li>
    </ul>

    <body>
        <div class="split left">
            <table id="tableId">
                <h1>Contatti</h1>  
                <th>Nome</th>
                <th>Cognome</th>
                <th>e-mail</th>
                <th>*</th>
                    <c:forEach var="u" items="${listaUtenti}" varStatus="status" >
                    <tr>
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
                <span id="dest" value="" hidden="true"></span>
                <textarea id="inputMess" rows="4" cols="50"></textarea>
                <button type="submit" id="invioMess">></button>
            </div>
        </div>
    </body>
</html>
