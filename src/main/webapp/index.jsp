<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>hola</h1>
<form action="${pageContext.request.contextPath}/mascotas" method="post">
    <input name="nombre">
    <input name="idRaza">
    <input name="idPropietario">
    <input name="fechaNacimiento" type="date">
    <input type="submit" value="enviar">
</form>
</body>
</html>