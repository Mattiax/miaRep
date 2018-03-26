<%-- 
    Document   : newjspsignin
    Created on : 12-mar-2018, 11.23.25
    Author     : mattia.musone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signin.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.messages_it.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/formJQvalid.js"></script>
        <title>Registrazione</title>
    </head>
    <body>
        <h1>Registrati</h1>
        <form id="signIn" action="doSignin">
            <fieldset>
                <legend>Dati personali</legend>
                <div>
                    <label for="nome">Nome</label>
                    <input type="text" name="nome">
                </div>
                <div>
                    <label for="cognome">Cognome</label>
                    <input type="text" name="cognome">
                </div>
                <div>
                    <label for="dataNascita">Data di nascita</label>
                    <input type="date" name="dataNascita">
                </div>               
                <div>
                    <label for="telefono">Telefono</label>
                    <input type="text" name="telefono">
                </div>
                <div>
                    <label for="indirizzo">Indirizzo</label>
                    <input type="text" name="indirizzo">
                </div>
                <div>
                    <label for="hobbies">Hobbies</label>
                    <input type="text" name="hobbies">
                </div>
                <div>
                    <input type="radio" name="sesso" value="M">M
                    <input type="radio" name="sesso" value="F">F
                </div>
				<div>
                    <label for="immagine">Immagine</label>
                    <input type="file" accept="image/*" name="immagine">
                </div>
            </fieldset>
            <fieldset>
                <legend>Account</legend>
                <div>
                    <label for="email">E-mail</label>
                    <input type="text" name="email">
                </div>
                <div>
                    <label for="password">Password</label>
                    <input type="text" name="password">
                </div>
                <div>
                    <label for="confPassword">Conferma password</label>
                    <input type="text" name="passwordRip">
                </div>
            </fieldset>
            <div>
                <p>Acconsento l'applicazione ad utilizzare dati non sensibili</p>
                <input type="radio" name="privacy" value="true" checked="true"> Acconsento<br>
                <input type="radio" name="privacy" value="false"> Non acconsento
            </div>
            <input id="registratiBtn" type="submit" value="REGISTRATI">
        </form>
    </body>
</html>
