<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.UserManager"%>
<%@page import="data.Location"%>
<%@page import="data.Login"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.Constants, data.User"%>
<%
	User user = (User) session.getAttribute(Constants.SESSION_IDENTFIER_USER_INFO);
	if (user == null) {
		user = new User(0, "", "", false);
	}
	String title;
	String button;
	List<Location> currentPermissions;
	List<Location> missingPermissions;
	if (user.getId() == 0) {
		title = "Nuevo usuario";
		button = "Crear";
		currentPermissions = new ArrayList<Location>();
		missingPermissions = new ArrayList<Location>();
	} else {
		title = "Modificar usuario";
		button = "Modificar";
		currentPermissions = UserManager.getInstance().getPermissions(user.getId());
		missingPermissions = UserManager.getInstance().getMissingPermission(user.getId());
	}
	String message = (String) session.getAttribute("mensaje");
	if (message == null) {
		message = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuarios</title>
<script>
	function addPermission() {
		document.getElementById("action").value = "add_one";
	}
	function addAllPermissions() {
		document.getElementById("action").value = "add_all";
	}
	function removePermission() {
		document.getElementById("action").value = "rem_one";
	}
	function removeAllPermissions() {
		document.getElementById("action").value = "rem_all";
	}
</script>
</head>
<body>
	<h1><%=title%></h1>
	<%=((Login) session.getAttribute(Constants.SESSION_IDENTFIER_LOGIN_INFO)).getUser().getName()%>
	|
	<a href="logout">Cerrar Sesión</a> |
	<a href="menu.jsp">Menú principal</a>
	<form method="post" action="modify_user">
		<input type="hidden" id="userid" name="userid" value=<%=user.getId()%>>
		<table>
			<tr>
				<td><label for="shortName">Usuario</label></td>
				<td><input type="text" id="shortName" name="shortName"
					value="<%=user.getShortName()%>" placeholder="Nickname" required />
				</td>
			</tr>
			<tr>
				<td><label for="name">Nombre completo</label></td>
				<td><input type="text" id="name" name="name"
					value="<%=user.getName()%>" placeholder="Nombre completo" required /></td>
			</tr>
			<tr>
				<td><label for="password">Contraseña</label></td>
				<td><input type="password" id="password" name="password"
					placeholder="Contraseña" required /></td>
			</tr>
			<tr>
				<td>Administrador</td>
				<td><input id="admin" name="admin" type="checkbox"
					<%=(user.getIsAdmin() ? "checked" : "")%> /></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<button id="btnAdd" type="submit"><%=button%></button>
				</td>
			</tr>
		</table>
		<%=message%>
	</form>
	<h2>Permisos</h2>
	<form method="post" action="permit_user">
		<input id="action" name="action" type="hidden" value="none">
		<table>
			<col width="200">
			<col width="100">
			<col width="200">
			<tr>
				<td>Permisos inactivos</td>
				<td></td>
				<td>Permisos activos</td>
			</tr>
			<tr>
				<td><select id="to_add_location" name="to_add_location"
					size="10" style="width: 200px">
						<%
							for (Location l : missingPermissions) {
								out.print("<option value ='" + l.getId() + "'>" + l.getName() + "</option>");
							}
						%>
				</select></td>
				<td>
					<table>
						<tr>
							<td align="center">
								<button onclick="addPermission()">Agregar</button>
							</td>
						</tr>
						<tr>
							<td align="center">
								<button onclick="addAllPermissions()">Agregar todos</button>
							</td>
						</tr>
						<tr>
							<td align="center">
								<button onclick="removePermission()">Quitar</button>
							</td>
						</tr>
						<tr>
							<td align="center">
								<button onclick="removeAllPermissions()">Quitar todos</button>
							</td>
						</tr>
					</table>
				</td>
				<td><select id="to_remove_location" name="to_remove_location"
					size="10" style="width: 200px">
						<%
							for (Location l : currentPermissions) {
								out.print("<option value ='" + l.getId() + "'>" + l.getName() + "</option>");
							}
						%>
				</select></td>
			</tr>
		</table>
	</form>
	<form method="post" action="delete_user">
		<input id="userid" name="userid" type="hidden" value=<%=user.getId()%>>
		<button <%=(user.getId() == 0 ? "disabled" : "")%>>Eliminar este usuario</button>
	</form>
	<button onclick="window.location.href='userlist.jsp'">Volver</button>
</body>
</html>
