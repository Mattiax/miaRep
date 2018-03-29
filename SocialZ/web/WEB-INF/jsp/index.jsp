<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/homeNav.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
        <title>Esempio di gestione tramite Spring MVC</title>
    </head>
    <body>
        <ul class="tabs text-center">
            <li id="home" class="tab active">HOME PAGE</li>
            <li id="signin" class="tab"><a href="login">ACCESSO</a></li>
            <li id="login" class="tab"><a href="signin">REGISTRAZIONE</a></li>
        </ul>
        <h1> Benvenuto nel social network dell'ITIS C. Zuccante </h1>
    </body>
</html>
