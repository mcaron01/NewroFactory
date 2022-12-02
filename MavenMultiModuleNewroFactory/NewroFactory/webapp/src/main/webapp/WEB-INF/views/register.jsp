<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Register</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<div>${message}</div>
<body>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1 id="homeTitle">Register</h1>
					<form:form action="register" method="POST" id="loginForm"
						modelAttribute="userRegisterDto">
						<fieldset>
							<div class="form-group">
								<form:label path="username">Username: <span
										style="color: red"> *</span>
								</form:label>
								<form:input type="text" class="form-control" path="username"
									placeholder="Enter username" />
							</div>
							<div class="form-group">
								<form:label path="password">Password: <span
										style="color: red"> *</span>
								</form:label>
								<form:input type="password" class="form-control" path="password"
									placeholder="Enter username" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Register" class="btn btn-primary"
								id="submitForm">
						</div>
						<div>
							<p>
								Vous avez déja un compte? <a href="login">Login</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	
	<script src="js/jquery.min.js"></script>
</body>
</html>
