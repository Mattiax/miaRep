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
        <title>Accesso</title>
    </head>
    <body>
        <h1>Accedi</h1>
		<form class="form-card" id="logIn" action="doLogin" method="POST">  
            <fieldset class="form-fieldset">
                <legend class="form-legend">Dati sensibili</legend>
				<div class="form-checkbox form-checkbox-inline">
					<label class="form-checkbox-label">
						<input name="amm" class="form-checkbox-field" type="checkbox" />
						<i class="form-checkbox-button"></i>
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
