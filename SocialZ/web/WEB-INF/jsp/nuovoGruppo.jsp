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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hamburger.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/creaGruppo.js"></script>
        <title>Creazione nuovo gruppo</title>
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
        <form class="form-card" id="creaGruppo" >  
            <fieldset class="form-fieldset">
                <legend class="form-legend">Dati gruppo</legend>
                <div class="form-element">
                    <input id="nome" name="nome" class="form-element-field" type="input" placeholder=" " required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Nome gruppo</label>
                </div>
                <div class="form-element">
                    <input id="descrizione" name="descrizione" class="form-element-field" type="input" placeholder="Es. Gruppo calcio"/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Descrizione gruppo</label>
                </div>
                <div class="form-element">
                    <input id="immagine" name="immagine" class="form-element-field" type="file" accept="image/*"/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label">Immagine</label>
                </div>
            </fieldset>
            <fieldset class="form-fieldset">
                <legend class="form-legend">Partecipanti</legend>
                <div class="form-checkbox form-checkbox-inline">
                    <c:forEach var="lst" items="${listaUtenti}" >
                        <label class="form-checkbox-label">
                            <input id="partecipante" class="form-checkbox-field" type="checkbox" value="${lst.getEmail()}"/>
                            <i class="form-checkbox-button"></i>
                            <span>${lst.getNome()}</span>
                        </label>
                    </c:forEach>  

                </div>
            </fieldset>
            <div class="form-actions">
                <button class="form-btn"id="crea">Crea</button>
                <button class="form-btn-cancel -nooutline" type="reset">Reset</button>
            </div>
        </form>
    </body>
</html>
