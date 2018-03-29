<%-- 
    Document   : personalArea
    Created on : 12-mar-2018, 11.34.40
    Author     : mattia.musone
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/newcss.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/areaPersonale.css">
        <title>Area personale</title>
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
        </ul>
        <div class="container">
            <div class="header">
                <img id="profile-picture" src="img/no_photo.png"/>
                <h class="header-text">${nome} ${cognome}</h>
            </div>
            <button class="fab" type="file" tab-index="0">
                <img src="img/modify_pencil.png" width="30">
            </button>
            <div class="content">
                <div class="info">
                    <table>
                        <tr>
                            <td>Nome</td>
                            <td>${nome}</td>
                        </tr>
                        <tr>
                            <td>Cognome</td>
                            <td>${cognome}</td>
                        </tr>
                        <tr>
                            <td>Data di nascita</td>
                            <td>${dataNascita}</td>
                        </tr>
                        <tr>
                            <td>Sesso</td>
                            <td>${sesso}</td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td>${email}</td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td>${password}</td>
                        </tr>
                        <tr>
                            <td><p>Lista Hobbies:</p></td>
                        </tr>
                        <c:forEach var="h" items="${listaHobbies}" varStatus="status" >
                            <tr>
                                <td></td>
                                <td>${h}</td>
                            </tr>
                        </c:forEach> 
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
