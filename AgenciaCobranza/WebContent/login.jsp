<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Agencia | Administración</title>
</head>

<body>
	<h2>Agencia | Administración</h2>
	<p >Ingrese usuario y clave para acceder</p>
	<form method="post" action="login">
		<div>
			<div>
				<input type="text" class="form-control" id="userId" name="userId"
					placeholder="Nombre de usuario" required>
			</div>
			<div>
				<input type="password" class="form-control" id="password"
					name="password" placeholder="Contraseña" required>
				<%=session.getAttribute("userError") == null ? "" : session.getAttribute("userError")%>
			</div>
			<button type="submit">Ingresar</button>
		</div>
	</form>
</body>
</html>
