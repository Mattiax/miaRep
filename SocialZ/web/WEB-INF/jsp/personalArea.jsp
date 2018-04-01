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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hamburger.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/areaPersonale.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/areaPersonale.js"></script>
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
            <li><a href="richieste">Richieste</a></li>
            <li><a href="esci">Esci</a></li>
        </ul>
        <div class="container">
            <div class="header">
                <img id="profile-picture" src="img/no_photo.png"/>
                <h class="header-text">${nome} ${cognome}</h>
            </div>
            <button class="fab" id="modify">
                <img id="fabImg" src="img/modify_pencil.png" width="30">
            </button>
            <div class="content">
                <div class="info">
                    <table>
                        <tr>
                            <td>Nome</td>
                            <td> 
                                <div class="form-element disabled">
                                    <input id="nome" name="nome" class="form-element-field" type="text" placeholder=" " required value="${nome}"/>
                                    <div class="form-element-bar"></div>
                                </div> 
                            </td>
                        </tr>
                        <tr>
                            <td>Cognome</td>
                            <td> 
                                <div class="form-element disabled">
                                    <input id="cognome" name="cognome" class="form-element-field" type="text" placeholder=" " required value="${cognome}"/>
                                    <div class="form-element-bar"></div>
                                </div> 
                            </td>
                        </tr>
                        <tr>
                            <td>Data di nascita</td>
                            <td>${dataNascita}</td>
                        </tr>
                        <tr>
                            <td>Indirizzo</td>
                            <td>
                                <div class="form-element disabled">
                                    <input id="indirizzo" name="indirizzo" class="form-element-field" type="text" placeholder=" " required value="${indirizzo}"/>
                                    <div class="form-element-bar"></div>
                                </div> 
                            </td>
                        </tr>
                        <tr>
                            <td>Telefono</td>
                            <td>
                                <div class="form-element disabled">
                                    <input id="telefono" name="telefono" class="form-element-field" type="number" placeholder=" " required value="${telefono}"/>
                                    <div class="form-element-bar"></div>
                                </div> 
                            </td>
                        </tr>
                        <tr>
                            <td>Acconsento trattamento dati non sensibili</td>
                            <td id="permTd">${datiPers}</td>
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
                            <td> 
                                <div class="form-element disabled">
                                    <input id="password" name="password" class="form-element-field" type="password" placeholder=" " required value="${password}"/>

                                    <div class="form-element-bar"></div>
                                </div> 
                            </td>
                            <td><img class="passVisibility" id="open" src="img/open_eye.ico" width="20"></td>
                        </tr>
                        <tr>
                            <td><p>Lista Hobbies:</p></td>
                        </tr>
                        <c:forEach var="h" items="${listaHobbies}" varStatus="status" >
                            <tr class="hobbies disabled">
                                <td class="firstCol">${h}</td>
                                <td class="eliminaHobby"><img  src="img/bin.png" width="20"><td>
                            </tr>
                        </c:forEach> 
                        <tr>
                            <td class="firstCol inputHobby"></td>
                            <td>
                                <input type="button" class="nuovoHobby disabled" value="+">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>