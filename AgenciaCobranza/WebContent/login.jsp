<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">

<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    
    <title>Agencia | Ingreso</title>
    
	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
	<link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png"/>
</head>

<body>
	<div class="container">
		<h2>Agencia | Administración</h2>
		<p >Ingrese usuario y clave para acceder</p>
		<form method="post" action="login">
			<div class="form-group">
				<input type="text" class="form-control" id="userId" name="userId" placeholder="Nombre de usuario" required>
			</div>
			<div class="form-group">
				<input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
				<%=session.getAttribute("userError") == null ? "" : session.getAttribute("userError")%>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
		</form>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
