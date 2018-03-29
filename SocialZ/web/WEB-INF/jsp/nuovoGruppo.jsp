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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/creaGruppo.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <form class="form-card" id="signIn" action="creaGruppo" method="POST">  
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
                            <input name=partecipa class="form-checkbox-field" type="checkbox" name="${lst.getEmail()}"/>
                            <i class="form-checkbox-button"></i>
                            <span>${lst.getNome()}</span>
                        </label>
                    </c:forEach>  

                </div>
            </fieldset>
            <div class="form-actions">
                <button class="form-btn" type="submit">Crea</button>
                <button class="form-btn-cancel -nooutline" type="reset">Reset</button>
            </div>
        </form>
    </body>
</html>
