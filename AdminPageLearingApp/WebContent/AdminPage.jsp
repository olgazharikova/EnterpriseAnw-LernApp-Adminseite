<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL" %>
<%@page import="java.net.URLConnection" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="MainDiv" style="background-color: lightgray; margin-left:10%;margin-right:10%;box-shadow: 5px 5px 5px grey">
		<form action="/AdminPageLearingApp/AdminPage" method="post">
			<table style="background-color: #24387f;height:75px;width:100%">
				<tr style="height:100%;width:100%">
					<td style="text-align:center;width:30%;">
						<button class="AddNewQuestion" onclick="window.location.href='/AdminPageLearingApp/QuestionController'">Question</button>
					</td>
					<td style="text-align:center;width:30%">
						<button class="AddNewCategory" onclick="window.location.href='/AdminPageLearingApp/CategoryController'">Category</button>
					</td>
				</tr>
			</table>
			<table style="width:100%;height:45%">
				<tr>
					<td style="width:50%">
						<div class="CategoryListDiv" style="background-color: white; min-height:400px;max-height:400px;">
							<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
								<tr>
									<td>
										<h3>Category</h3>
									</td>
									<td style="text-align:right">
										<button class="AddNewCategory" onclick="window.location.href='/AdminPageLearingApp/CategoryController'" style="text-align:center;border-radius:12px">Add</button>
									</td>
								</tr>
							</table>						
							<div class="CategoryListDiv" style="height:335px;overflow:auto">
							    <c:forEach var="category" items="${categories}" varStatus="i">
							    <div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr>
									    	<td style="font-weight:bold">${category.title}</td>
									    </tr>			
									    <tr>
									    	<td>${category.description}</td>
									    </tr>					        
								  </table>
							    </div>
								</c:forEach>
							</div>
						</div>				
					</td>
					<td style="width:50%">
						<div class="JSONDiv" style="background-color: white;max-height:400px;min-height:400px">
								<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
									<tr>
										<td>
											<h3>Input JSON</h3>
										</td>
										<td style="text-align:right">
											<button class="AddJSONContent" name="sendJson" style="text-align:center;border-radius:12px">Send</button>
										</td>
									</tr>
								</table>	
							<div class="JSONTextfieldDiv" style="text-align:center;padding-bottom:1px;">
								<textarea type="text" name="JSONTextfield" style="width:90%;min-height:328px;max-height:328px;min-width:600px;max-width:600px"></textarea>
							</div>
						</div>				
					</td>
				</tr>
			</table>
			<table style="width:100%;height:65%">
				<tr>
					<td>
						<div class="QuestionDiv" style="background-color: white;">
							<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
								<tr>
									<td>
										<h3>Question</h3>
									</td>
									<td style="text-align:right">
										<button class="AddNewQuestion" onclick="window.location.href='/AdminPageLearingApp/QuestionController'" style="text-align:center;border-radius:12px">Add</button>
									</td>
								</tr>
							</table>
						</div>
						<div style="background-color: white;min-height:400px;overflow:auto">
							<c:forEach var="question" items="${questions}" varStatus="i">
								<div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr>
									    	<td style="width:70%">${question.text}</td>
									    	<td style="text-align:right;width:30%">${question.category.title}</td>
									    	<td><button name="${question.id}">Delete</button></td>
									    </tr>							        
								  </table>
								  <table style="width:100%">
								    	<tr>
								    		<td>
										    	<c:forEach var="answer" items="${question.answers}">
										    		<td style="text-align:center;width:25%">${answer.text}</td>
										    	</c:forEach>
										    </td>
									    </tr>	
								    </table>
							    </div>
							</c:forEach>
						</div>	
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>