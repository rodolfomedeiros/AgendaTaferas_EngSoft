<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page language="java" import="taskprime.model.UserEnum" %>
<%@ page language="java" import="taskprime.model.TaskEnum" %>

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
			<c:when test="${mode == UserEnum.HOME}">
				<div data-role="page">
					<!-- header -->
				    <div data-role="header" data-position="fixed" data-theme="a">
				        <h1>${NameGroup}</h1>
				        <a href="#panelMain" class="ui-btn-inline" data-ajax="false" data-icon="bars" data-iconpos="notext">Menu</a>
				        <a href="nova-tarefa" class="ui-btn-inline" data-ajax="false" data-icon="plus" data-iconpos="notext">Add</a>
				    </div>
				    <!-- /header -->
				    <!-- panelMain -->
				    <div id="panelMain" data-role="panel" data-display="reveal">
				    	<div class="ui-header">
				    		<h3>Grupos:</h3>
				    	</div>
				    	<div class="ui-body ui-body-a">
				    		<ul data-role="listview">
				    			<li><a href="user?groupTask=0">Tarefas</a></li>
				    			<c:forEach var="group" items="${groups}">
				    				<li><a href="user?groupTask=${group.id}">${group.name}</a></li>
				    			</c:forEach>
				    			<li><a href="tarefas-concluidas">Concluídas</a></li>
				    		</ul>
				    	</div>
				    	<div class="ui-header">
				    		<h3>Configurações:</h3>
				    	</div>
				    	<div class="ui-body ui-body-a">
				    		<ul data-role="listview">
				    			<li><a href="meus-dados">Meus Dados</a></li>
				    		</ul>
				    	</div>
				    	<hr>
				    	<a href="logout" class="ui-btn ui-btn-a ui-btn-corner-all ui-icon-back ui-btn-icon-right">Logout</a>
				    </div>
				    <!-- /panelMain -->
				    <div data-role="content">
				        <ul id="list" data-inset="true" data-role="listview" data-icon="false" data-split-icon="check">   
					        <c:choose>
					        	<c:when test="${concluded == UserEnum.CONCLUDED_TASK}">
					        		<c:forEach var="task" items="${tasks}">    
							            <li class="ui-field-contain">
							                <a data-ajax="false" href="alterar-tarefa?id=${task.id}">
							                    <h3 class="topic">${task.name}</h3>
							                    <p>Prazo: ${task.dateFinished}</p>
							                    <p>${task.description}</p>
							                    <p class="ui-li-aside">${task.timeFinished}</p>
							                </a>
							            </li>
						            </c:forEach>
					        	</c:when>
					        	<c:otherwise>
					        		<h4 style="color:red">Atrasada</h4>
					        		<c:forEach var="task" items="${hashMapTasks.get(TaskEnum.ATRASADA)}">    
							            <li class="ui-field-contain">
							                <a data-ajax="false" href="alterar-tarefa?id=${task.id}">
							                    <h3 class="topic">${task.name}</h3>
							                    <p>Prazo: ${task.dateFinished}</p>
							                    <p>${task.description}</p>
							                    <p class="ui-li-aside">${task.timeFinished}</p>
							                </a>
						                	<a href="concluir-tarefa?id=${task.id}" data-ajax="false" class="check">Check</a>
							            </li>
						            </c:forEach>
						            <h4 class="ui-color-azulclaro">Hoje</h4>
						            <c:forEach var="task" items="${hashMapTasks.get(TaskEnum.HOJE)}">    
							            <li class="ui-field-contain">
							                <a data-ajax="false" href="alterar-tarefa?id=${task.id}">
							                    <h3 class="topic">${task.name}</h3>
							                    <p>Prazo: ${task.dateFinished}</p>
							                    <p>${task.description}</p>
							                    <p class="ui-li-aside">${task.timeFinished}</p>
							                </a>
							                <a href="concluir-tarefa?id=${task.id}" data-ajax="false" class="check">Check</a>
							            </li>
						            </c:forEach>
						            <h4 class="ui-color-azulclaro">Falta um dia ou mais...</h4>
						            <c:forEach var="task" items="${hashMapTasks.get(TaskEnum.FALTA_MAIS)}">    
							            <li class="ui-field-contain">
							                <a data-ajax="false" href="alterar-tarefa?id=${task.id}">
							                    <h3 class="topic">${task.name}</h3>
							                    <p>Prazo: ${task.dateFinished}</p>
							                    <p>${task.description}</p>
							                    <p class="ui-li-aside">${task.timeFinished}</p>
							                </a>
							                <a href="concluir-tarefa?id=${task.id}" data-ajax="false" class="check">Check</a>
							            </li>
						            </c:forEach>
					        	</c:otherwise>
					        </c:choose>
				        </ul>
				    </div>
				    <!-- /content -->
				</div>
			</c:when>
			<c:when test="${mode == UserEnum.NEW_TASK || mode == UserEnum.UPDATE_TASK}">
				<div data-role="page" id="new-task" data-title="Tarefa">
				    <div data-role="header" data-position="fixed" data-theme="a">
				        <h1>Tarefa</h1>
				        <a href="user?groupTask=0" data-ajax="false" data-icon="home" data-iconpos="notext">Home</a>
				    	<a href="javascript:popupCriarGrupo()" data-ajax="false" data-icon="plus" data-iconpos="notext">Criar Grupo</a>
				    </div>
				    <!-- /header -->
				    <div role="main" class="ui-content">
				    	<div class="ui-body ui-body-a ui-corner-all">
					        <form data-ajax="false" method="post" action="salvar-tarefa">
					        	<input type="hidden" name="id" id="id" value="${task.id}">
					        	<label for="name">Nome:</label>
					        	<input type="text" data-clear-btn="true" name="name" id="name" value="${task.name}" required>
					        	<label for="description">Descrição</label>
					        	<input type="text" data-clear-btn="true" name="description" id="description" value="${task.description}" required>
					        	<label for="dateFinished">Prazo:</label>
					        	<input type="date" data-clear-btn="true" id="dateFinished" name="dateFinished" value="${task.dateFinished}" required>
							    <input type="time" data-clear-btn="true" id="timeFinished" name="timeFinished" value="${task.timeFinished}" required>	
							    <div class="ui-field-contain">
							        <label for="finished">Concluído?</label>
							        <c:choose>
								        <c:when test="${task.finished == true}">
											<select name="finished" id="finished" data-role="flipswitch">
									            <option value="false">Não</option>
									            <option value="true" selected>Sim</option>
									        </select>
										</c:when>
										<c:otherwise>
											<select name="finished" id="finished" data-role="flipswitch">
									            <option value="false" selected>Não</option>
									            <option value="true">Sim</option>
									        </select>
										</c:otherwise>
							        </c:choose>
							    </div>
							    <label for="groupTask">Grupo:</label>
							    <select name="groupTask" id="groupTask" required>
							    	<c:forEach var="group" items="${groups}">
							    		<c:choose>
							    			<c:when test="${task.groupTask == group.id}">
							    				<option value="${group.id}" selected>${group.name}</option>
							    			</c:when>
							    			<c:otherwise>
							    				<option value="${group.id}">${group.name}</option>
							    			</c:otherwise>
							    		</c:choose>
							    	</c:forEach>
							    </select>
							    <hr>
							    <div class="ui-grid-a">
							    	<div class="ui-block-a">
							    		<button type="submit" class="ui-btn ui-btn-a ui-icon-check ui-btn-icon-right ui-btn-corner-all">Salvar</button>
							    	</div>
							    	<div class="ui-block-b">
							    		<button formaction="deletar-tarefa?id=${task.id}" class="ui-btn ui-btn-a ui-icon-delete ui-btn-icon-right ui-btn-corner-all">Deletar</button>
							    	</div>
							    </div>
					        </form>
				        </div>
				    </div>
				    <!-- /content -->
				    <!-- popup Criar Grupo -->
				    <div id="groupCreate" class="ui-content" data-role="popup" data-theme="a">
				        <p>Você deseja criar um novo grupo?</p>
				        <form data-ajax="false" action="salvar-grupo" method="post">
				        	<label for="name">Nome:</label>
				        	<input type="text" name="name" id="name" required>
			        		<div class="ui-grid-a">
					            <div class="ui-block-a"> 
					                <button type="submit" class="ui-btn ui-corner-all ui-icon-check ui-btn-icon-right ui-mini ui-btn-a">Criar</button>
					            </div>
					            <div class="ui-block-b">
					                <button id="no" type="reset" class="ui-btn ui-corner-all ui-icon-delete ui-btn-icon-right ui-mini ui-btn-a">Cancelar</button>
					            </div>
					        </div>
				        </form>
				    </div>
				    <!-- /popup -->
				</div>
			</c:when>
			<c:when test="${mode == UserEnum.MEUS_DADOS}">
				<div data-role="page" id="new-user" data-title="Cadastrar">
				    <div data-role="header" data-position="fixed" data-theme="a">
				        <h1>Meus Dados</h1>
				        <a href="/user?groupTask=0" data-ajax="false" data-icon="home" data-iconpos="notext">Home</a>
				    </div>
				    <!-- /header -->
				    <div data-role="content">
				    	<div class="ui-body ui-body-a ui-corner-all">
							<form data-ajax="false" action="salvar-meus-dados" method="post">
								<input type="hidden" name="id" id="id" value="${user.id}">
								<label for="name">Nome:</label>
								<input type="text" name="name" id="name" value="${user.name}" required>
								<label for="email">Email:</label>
								<input type="email" name="email" id="email" value="${user.email}" required>
								<input type="hidden" name="login" id="login" value="${user.login}" required>
								<label for="password">Senha:</label>
								<input type="text" name="password" id="password" value="${user.password}" required>
								<hr>
								<div class="ui-grid-solo">
							    	<div class="ui-block-a">
							    		<button type="submit" class="ui-btn ui-btn-a ui-icon-check ui-btn-icon-right ui-btn-corner-all">Salvar</button>
							    	</div>
							    </div>
							</form>
							<hr>
							<div class="ui-grid-a">
						    	<div class="ui-block-a"></div>
						    	<div class="ui-block-b">
						    		<a href="deletar-usuario" class="ui-btn ui-btn-a ui-icon-delete ui-btn-icon-right ui-btn-corner-all">Excluir</a>
						    	</div>
						    </div>
						</div>
					</div>
				</div>
			</c:when>
		</c:choose>
    </body>
</html>