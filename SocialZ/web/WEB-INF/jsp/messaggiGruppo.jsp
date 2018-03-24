<%-- 
    Document   : messaggiGruppo
    Created on : 23-mar-2018, 11.41.16
    Author     : MATTI
--%>
<jsp:useBean   id="ut" scope="session" class="pkg.bean.Utente" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messages.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/messaggiGruppo.js"></script>
        <title>JSP Page</title>
    </head>

    <ul id="mainUL">
        <li id="mainLI"><a href="messages">Messaggi</a></li>
        <li id="mainLI"><a class="active">Gruppi</a></li>
        <li id="personalAreaLI">
            <form action="personalArea">
                <ul id="subUL">
                    <span name="mittente" id="mittente" hidden="true"><%ut.getNome();%></span>
                    <li id="dropLI"><button id="test" type="submit">Account</button></li>
                    <li id="dropLI"><a id="test" href="#">Esci</a></li>
                </ul>
            </form>
        </li>
    </ul>
    <body>
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
            <div id="storicoChat">

            </div>

            <div id="componiMessaggio">
                
                <span id="dest" value="" hidden="true"></span>
                <textarea id="inputMess" rows="4" cols="50"></textarea>
                <button type="submit" id="invioMess">></button>
                <button id="scriviMessaggio" name="nuovoMessaggio">+</button>
            </div>
        </div>
    </body>
</html>
