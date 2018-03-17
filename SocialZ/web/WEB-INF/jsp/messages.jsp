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
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
        <title>JSP Page</title>
    </head>
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

        <div class="split right">
            <h1>Messaggi</h1>  

            <button name="nuovoMessaggio">NUOVO</button>
        </div>
        
    </body>
</html>
