<%-- 
    Document   : newjsp
    Created on : 26-mar-2018, 16.44.20
    Author     : MATTI
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homeNav.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signin.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.messages_it.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/formJQvalid.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/signin.js"></script>
        <title>Registrati</title>
    </head>
    <body>
        <ul class="tabs text-center">
            <li id="home" class="tab"><a href="index">HOME PAGE</a></li>
            <li id="signin" class="tab"><a href="login">ACCESSO</a></li>
            <li id="login" class="tab active">REGISTRAZIONE</li>
        </ul>
        <h1>Iscrizione a SocialZ</h1>
        <form class="form-card" id="signIn" action="doSignin" method="POST" enctype="multipart/form-data">  
            <fieldset class="form-fieldset">
                <legend class="form-legend">Dati personali</legend>
                <div class="form-element">
                    <input id="nome" name="nome" class="form-element-field" type="input" placeholder=" " required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Nome</label>
                </div>
                <div class="form-element">
                    <input id="cognome" name="cognome" class="form-element-field" type="input" placeholder=" " required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Cognome</label>
                </div>
                <div class="form-element">
                    <input id="dataNascita" name="dataNascita" class="form-element-field" type="date" required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Data di nascita</label>
                </div>
                <div class="form-element">
                    <input id="indirizzo" name="indirizzo" class="form-element-field" type="input" placeholder=" " />
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Indirizzo</label>
                </div>
                <div class="form-element">
                    <input id="telefono" name="telefono" class="form-element-field" placeholder="1234567890" type="number"/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label" >Telefono</label>
                </div>
                <div class="form-radio form-radio-block">
                    <div class="form-radio-legend"></div>
                    <label class="form-radio-label">
                        <input name="sesso" class="form-radio-field" type="radio" value="M" required/>
                        <i class="form-radio-button"></i>
                        <span>Maschio</span>
                    </label>
                    <label class="form-radio-label">
                        <input name="sesso" class="form-radio-field" type="radio" value="F" required/>
                        <i class="form-radio-button"></i>
                        <span>Femmina</span>
                    </label>
                </div>
                <div class="form-element">
                    <p>Seleziona i tuoi hobby</p>
                    <select name="hobbies" multiple>
                        <c:forEach var="h" items="${listaHobbies}" varStatus="status" >
                            <option>${h}</option>
                        </c:forEach> 
                    </select>
                    <div id="aggiungiHobby">Hobby non presente? Aggiungine uno</div>
                </div>
            </fieldset>
            <fieldset class="form-fieldset">
                <legend class="form-legend">Creazione account</legend>
                <div class="form-element">
                    <input id="email" name="email" class="form-element-field" placeholder="esempio@esempio.it" type="email" required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label">Email</label>
                </div>
                <div class="form-element">
                    <input id="password" name="password" class="form-element-field" placeholder=" " type="password" required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label">Password</label>
                    <small id="lungPass1"></small>
                </div>
                <div class="form-element">
                    <input id="password2" name="password2" class="form-element-field" placeholder=" " type="password" required/>
                    <div class="form-element-bar"></div>
                    <label class="form-element-label">Conferma password</label>
                    <small id="eqPass2"></small>
                </div>
                <div class="form-element">
                    <input id="immagine" name="immagine" class="form-element-field" type="file" />
                    <div class="form-element-bar"></div>
                    <label class="form-element-label">Immagine</label>
                </div>
            </fieldset>
            <fieldset class="form-fieldset">
                <legend class="form-legend">Visibilità dati non sensibili</legend>
                <div class="form-radio form-radio-block">
                    <div class="form-radio-legend">Acconsento il trattamento di alcuni dati non sensibili per fornire funzionalità alla piattaforma</div>
                    <label class="form-radio-label">
                        <input name="privacy" class="form-radio-field" type="radio" value="T" required/>
                        <i class="form-radio-button"></i>
                        <span>Accetto</span>
                    </label>
                    <label class="form-radio-label">
                        <input name="privacy" class="form-radio-field" type="radio" value="F" required/>
                        <i class="form-radio-button"></i>
                        <span>Non accetto</span>
                    </label>
                </div>
                <input type="button" id="ciao" value="asds">
            </fieldset>
            <div class="form-actions">
                <button class="form-btn" type="submit">Iscriviti</button>
                <button class="form-btn-cancel -nooutline" type="reset">Reset</button>
            </div>
        </form>
    <body>
</html>
