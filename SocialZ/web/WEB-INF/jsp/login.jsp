<%-- 
    Document   : login
    Created on : 12-mar-2018, 11.23.20
    Author     : mattia.musone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homeNav.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
        <title>Accesso</title>
    </head>
    <body>
        <ul class="tabs text-center">
            <li id="home" class="tab"><a href="index">HOME PAGE</a></li>
            <li id="signin" class="tab active">ACCESSO</li>
            <li id="login" class="tab"><a href="signin">REGISTRAZIONE</a></li>
        </ul>
        <h1>Accedi</h1>
        <form class="form-card" id="logIn" action="doLogin" method="POST">  
            <fieldset class="form-fieldset">
                <legend class="form-legend">Dati sensibili</legend>
                <div class="form-checkbox form-checkbox-inline">
                    <label class="form-checkbox-label">
                        <input name="amm" class="form-checkbox-field" type="checkbox" />
                        <i id="amministratore"></i>
                        <span class="testo">Sei un amministratore?</span>
                    </label>
                </div>
                <div class="form-element">
                    <input id="email" name="email" class="form-element-field" type="email" placeholder=" " required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Email</label>
                </div>
                <div class="form-element">
                    <input id="password" name="password" class="form-element-field" type="password" placeholder=" " required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Password</label>
                </div>
            </fieldset>
            <div class="form-actions">
                <button class="form-btn" type="submit">Accedi</button>
                <button class="form-btn-cancel -nooutline" type="reset">Reset</button>
            </div>
        </form>
    </body>
</html>
