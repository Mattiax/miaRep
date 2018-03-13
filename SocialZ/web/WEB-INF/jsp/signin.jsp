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
        <title>Registrazione</title>
    </head>
    <body>
        <h1>Registrati</h1>
		<form id="signIn" action="action">
			<div>
				<label for="nome">Nome</label>
				<input type="text">
			</div>
			<div>
				<label for="cognome">Cognome</label>
				<input type="text">
			</div>
			<div>
				<label for="dataNascita">Data di nascita</label>
				<input type="text">
			</div>
			<div>
				<label for="telefono">Telefono</label>
				<input type="text">
			</div>
			<div>
				<label for="hobbies">Hobbies</label>
				<input type="text">
			</div>
			<div>
				<label for="email">E-mail</label>
				<input type="text">
			</div>
			<div>
				<label for="password">Password</label>
				<input type="text">
			</div>
			<div>
				<label for="confPassword">Conferma password</label>
				<input type="text">
			</div>
			<p><input type="button" value="REGISTRATI"></p>
		</form>
    </body>
</html>
