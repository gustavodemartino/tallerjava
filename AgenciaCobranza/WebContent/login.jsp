<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agencia | Administraci�n</title>


    <!--STYLESHEET-->
    <!--=================================================-->

    <!--Open Sans Font-->
	<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin" rel="stylesheet">

    <!--Bootstrap Stylesheet-->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!--Stylesheet-->
    <link href="css/styles.css" rel="stylesheet">

    <!--Font Awesome-->
    <link href="plugins/font-awesome/css/font-awesome.css" rel="stylesheet">


    <!--SCRIPT-->
    <!--=================================================-->

    <!--Page Load Progress Bar-->
    <link href="plugins/pace/pace.min.css" rel="stylesheet">
    <script src="plugins/pace/pace.min.js"></script>
		

</head>

<body>

    <!-- LOGIN FORM -->
    <!--===================================================-->
	<div id="container">
		
		<div class="cls-content">
			<div class="pad-btm mar-btm text-center">
				<h2 class="text-thin mar-no">Agencia | Administraci�n</h2>
			</div>
			<div class="cls-content-sm panel">
				<div class="panel-body">
					<p class="pad-btm">Ingrese usuario y clave para acceder</p>
					<form method="post" action="Login">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"><i class="fa fa-user"></i></div>
								<input type="text" class="form-control" id="userId" placeholder="Nombre de usuario" required>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon"><i class="fa fa-asterisk"></i></div>
								<input type="password" class="form-control" id="password" placeholder="Contrase�a" required>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-8 text-left text-danger">
								<%= session.getAttribute("userError")==null?"Hea":session.getAttribute("userError") %>
							</div>
							<div class="col-xs-4">
								<div class="form-group text-right">
								<button class="btn btn-primary text-uppercase" type="submit">Ingresar</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
	</div>
	<!--===================================================-->
		
    <!--SCRIPT-->
    <!--=================================================-->

    <!--jQuery [ REQUIRED ]-->
    <script src="js/jquery-2.1.1.min.js"></script>

    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="js/bootstrap.js"></script>
    
    <!--Nifty Admin [ RECOMMENDED ]-->
    <script src="js/agencia.js"></script>

</body>
</html>