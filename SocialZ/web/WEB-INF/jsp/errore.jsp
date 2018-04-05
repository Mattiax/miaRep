 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/errore.css"/>
        <title>Errore</title>
    </head>
    <body>
        <h1>Sembra che qualcosa sia andato storto :(</h1>
        <div> 
        <p>  ${errore}</p>
        <p><a href ="index"> home </a></p>
        </div>
        
    </body>
</html>
