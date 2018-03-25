<%-- 
    Document   : nuovoGruppo
    Created on : 25-mar-2018, 14.12.57
    Author     : MATTI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/creaGruppo.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <form>
            <div>
                <label for="nome">Nome Gruppo</label>
                <input type="text" id="nome">
            </div>
            <div>
                <label for="descrizione">Descrizione gruppo</label>
                <input type="text" id="descrizione">
            </div>
            <div>
                <label for="immagine">Immagine</label>
                <input type="file" accept="image/*" name="immagine">
            </div>
            <div id="partecipanti">
                <table id="tabellaPartecipanti">
                    <h1>Contatti</h1>  
                    <th>Nome</th>
                        <c:forEach var="lst" items="${listaUtenti}" >
                        <tr>
                            <td name="utente"><input type="checkbox" id="partecipa" name=${lst.getEmail()}>${lst.getNome()}</td>
                        </tr>
                    </c:forEach>  
                </table>
            </div>
            <button id="crea">CREA</button>
        </form>
    </body>
</html>
