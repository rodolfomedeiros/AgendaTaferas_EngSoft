<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page language="java" import="taskprime.model.MainEnum" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		
		<meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
		<meta http-equiv="pragma" content="no-cache" />
		
		<title>Task Prime</title>
		
		<!-- JQUERY -->
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		<link href="../../css/jquery.mobile-1.4.5.css" rel="stylesheet">
		<link href="../../css/style.css" rel="stylesheet">
		
		<!--  JQUERY JS -->
		<script src="../../js/jquery-1.11.1.js"></script>
		<script src="../../js/jquery.mobile-1.4.5.js"></script>
		<script src="../../js/user.js"></script>
	</head>
<body>
	<c:choose>
		<c:when test="${mode == MainEnum.LOGIN}">
			<div data-role="page">
				<div data-role="header">
					<h1>TaskPrime</h1>
				</div>
				
				<div data-role="content">
					<h1 class="ui-color-azulclaro">Sing in</h1>
					<hr>
					<div class="ui-body ui-body-a ui-corner-all">
						<c:if test="${alert == MainEnum.ALERT_LOGIN}">
							<span style="color:red">Login ou Senha inválidos!!!</span>
						</c:if>
						<form data-ajax="false" action="entrar" method="post">
							<label for="login">Login:</label>
							<input type="text" name="login" id="login" required>
							<label for="password">Senha:</label>
							<input type="password" name="password" id="password" required>
							
							<div class="ui-grid-a">
								<div class="ui-block-a"></div>
								<div class="ui-block-b">
									<button type="submit" class="ui-btn ui-btn-icon-right ui-icon-arrow-r ui-corner-all">Entrar</button>
								</div>
							</div>
						</form>
					</div>
					<hr>
					<div class="ui-grid-solo">
						<div class="ui-block-a">
							<a class="ui-btn ui-btn-corner-all ui-btn-icon-left ui-icon-edit" href="cadastrar">Novo Cadastro</a>
						</div>
					</div>
					<hr>
				</div>
			</div>
		</c:when>
		<c:when test="${mode == MainEnum.CREATE}">
			<div data-role="page" id="new-user" data-title="Cadastrar">
			    <div data-role="header" data-position="fixed" data-theme="a">
			        <h1>Cadastrar</h1>
			        <a href="/" data-ajax="false" data-icon="home" data-iconpos="notext">Home</a>
			    </div>
			    <!-- /header -->
			    <div data-role="content">
			    	<div class="ui-body ui-body-a ui-corner-all">
				    	<c:if test="${alert == MainEnum.ALERT_CREATE}">
				    		<p style="color:red">Login já existe, escolha outro!</p>
				    	</c:if>
						<form data-ajax="false" action="salvar-usuario" method="post">
							<label for="name">Nome:</label>
							<input type="text" name="name" id="name" required>
							<label for="email">Email:</label>
							<input type="email" name="email" id="email" required>
							<label for="login">Login:</label>
							<input type="text" name="login" id="login" required>
							<label for="password">Senha:</label>
							<input type="password" name="password" id="password" required>
							<hr>
							<button type="submit" class="ui-btn ui-btn-a ui-icon-check ui-btn-icon-right ui-corner-all">Cadastrar</button>
						</form>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</body>
</html>