<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game form</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<style>
body {
	font-family: Palatino, Helvetica, sans-serif;
	background-color: #222831;
	color: #eeeeee;
}

h2 {
	color: #eeeeee;
	font-size: 30px;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #CDCDCD
}

tr:nth-child(odd) {
	background-color: whitesmoke
}

th {
	background-color: #B6D6F8;
	color: whitesmoke;
}

ul.b {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #00adb5;
}

li {
	float: left;
}

li.navLi a.active {
	color: #222831;
	background-color: #eeeeee;
}

li a, .dropbtn {
	display: block;
	color: #eeeeee;
	font-size: medium;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	margin: 1px 0px 0px 0px;
	font-size: medium;
}

li a:hover, .dropdown:hover .dropbtn {
	background-color: #eeeeee;
}

li.dropdown {
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #00adb5;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: #222831;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #eeeeee;
}

.dropdown:hover .dropdown-content {
	display: block;
}

#logoutButton {
	background-color: #00adb5;
	border: 0;
	color: #eeeeee;
	font-size: medium;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 0;
	padding: 13px 16px 0px 16px;
	cursor: pointer;
	font-size: medium;
}

.mySlides {
	display: none;
}

/* unvisited link */
a:link {
	color: #393e46;
}

/* visited link */
a:visited {
	color: #393e46;
}

/* mouse over link */
a:hover {
	color: #00adb5;
}

/* selected link */
a:active {
	color: #393e46;
}
</style>
</head>
<body>

<div style="margin: 10px 10px;">
		<ul class="b">
			<li class="navLi"><a href="${pageContext.request.contextPath}">Home</a></li>
			<li class="navLi"><a
				href="${pageContext.request.contextPath}/booking/myBookings?userId=${user.id}">My
					Bookings</a></li>
			<security:authorize access="hasRole('ADMIN')">
				<li class="navLi"><a
					href="${pageContext.request.contextPath}/booking/showAllBookings">All
						Bookings</a></li>
			</security:authorize>


			<li class="navLi"><a
				href="${pageContext.request.contextPath}/game/showAllGames">All
					Games</a></li>
			<security:authorize access="hasRole('ADMIN')">
				<li class="navLi"><a class="active" href="${pageContext.request.contextPath}/game/create">Create
				Game</a></li>
				<li class="navLi"><a href="${pageContext.request.contextPath}/category/create">Create
				Category</a></li>
				<li class="navLi"><a href="${pageContext.request.contextPath}/platform/create">Create
				Platform</a></li>
			</security:authorize>
			<li class="navLi"><a
					href="${pageContext.request.contextPath}/aboutme">About
						Me</a>
			</li>
			<li class="navLi">
				<!-- Add a logout button --> <form:form
					action="${pageContext.request.contextPath}/logout" method="POST">

					<input type="submit" value="Logout" id="logoutButton" />

				</form:form>
			</li>
		</ul>
	</div>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<div id="loginbox" style="margin-top: 50px;"
		class="mainbox col-md-7 col-md-offset-2 col-sm-8 col-sm-offset-4">

		<div class="panel panel-info">
			<div class="panel-heading" style="background-color: #00adb5; color: #eeeeee;">
				<c:if test="${createOrUpdate == 'create'}">
					<div class="panel-title">Add New Game</div>
				</c:if>
				<c:if test="${createOrUpdate == 'update'}">
					<div class="panel-title">Update Game</div>
				</c:if>

			</div>

			<div style="padding-top: 30px; color: #393e46; font-size: medium; background-color: #eeeeee;" class="panel-body" style="background-color:#eeeeee;">
				<div class="container">
					<c:choose>
						<c:when test="${createOrUpdate == 'create'}">
							<form:form class="form-horizontal"
								action="${contextPath}/game/create" method="post"
								modelAttribute="game">

								<div class="form-group">
									<label class="control-label col-sm-2">Title:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-blackboard"></i></span>
											<form:input type="text" path="title" required="required"
												class="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Price:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-jpy"></i></span>
											<form:input type="number" path="price" required="required"
												class="form-control" step="0.01" min="0.5" max="50"/>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Category:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-tasks"></i></span>
											<form:select path="category" items="${categories}"
												multiple="true" required="required" class="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Developers:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-book"></i></span>
											<form:input type="string" path="developers"
												required="required" class="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">IGN Rating:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-registration-mark"></i></span>
											<form:input type="number" path="ignrating"
												required="required" class="form-control" step="0.01" min="0.01" max="10"/>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Platform:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-tasks"></i></span>
											<form:select path="platform" items="${platforms}"
												multiple="true" required="required" class="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Premium:</label>
									<div class="col-sm-1">
										<form:radiobutton path="premium" value="Yes"
											class="form-control" />
										Yes
									</div>
									<div class="col-sm-1" style="margin-bottom: 25px">
										<form:radiobutton path="premium" value="No"
											class="form-control" />
										No
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Quantity:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-piggy-bank"></i></span>
											<form:input type="number" path="quanity" required="required"
												class="form-control" min="0"/>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Image:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-picture"></i></span>
											<form:input type="string" id="photo" path="imageUrl"
												required="required" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-5">
										<input type="submit" name="Submit" value="Post Game"
											class="btn btn-primary" role="button" aria-pressed="true" style="background-color:#00adb5; color:#eeeeee;">
									</div>
								</div>

							</form:form>
						</c:when>
						
						
						<c:when test="${createOrUpdate == 'update'}">
							<form:form class="form-horizontal"
								action="${contextPath}/game/showFormForUpdate" method="post"
								modelAttribute="game">

								<form:hidden path="gameId" />

								<div class="form-group">
									<label class="control-label col-sm-2">Title:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-blackboard"></i></span>
											<form:input type="text" path="title" required="required"
												class="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Price:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-jpy"></i></span>
											<form:input type="number" path="price" required="required"
												class="form-control" step="0.01" min="0.5" max="50"/>
										</div>
									</div>
								</div>

								<%-- <div class="form-group">
									<label class="control-label col-sm-2">Category:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-tasks"></i></span>
											<form:select path="category" items="${categories}"
												multiple="true" required="required" class="form-control" />
										</div>
									</div>
								</div> --%>

								<div class="form-group">
									<label class="control-label col-sm-2">Developers:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-book"></i></span>
											<form:input type="string" path="developers"
												required="required" class="form-control" />
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">IGN Rating:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-registration-mark"></i></span>
											<form:input type="number" path="ignrating"
												required="required" class="form-control" step="0.01" min="0.01" max="10"/>
										</div>
									</div>
								</div>

								<%-- <div class="form-group">
									<label class="control-label col-sm-2">Platform:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-tasks"></i></span>
											<form:select path="platform" items="${platforms}"
												multiple="true" required="required" class="form-control" />
										</div>
									</div>
								</div> --%>

								<div class="form-group">
									<label class="control-label col-sm-2">Premium:</label>
									<div class="col-sm-1">
										<form:radiobutton path="premium" value="Yes"
											class="form-control" />
										Yes
									</div>
									<div class="col-sm-1" style="margin-bottom: 25px">
										<form:radiobutton path="premium" value="No"
											class="form-control" />
										No
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Quantity:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-piggy-bank"></i></span>
											<form:input type="number" path="quanity" required="required"
												class="form-control" min="0"/>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2">Image:</label>
									<div class="col-sm-5">
										<div style="margin-bottom: 25px" class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-picture"></i></span>
											<form:input type="string" id="photo" path="imageUrl"
												 />
										</div>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-5">
										<input type="submit" name="Submit" value="Post Game"
											class="btn btn-primary" role="button" aria-pressed="true" style="background-color:#00adb5; color:#eeeeee;">
									</div>
								</div>

							</form:form>
						</c:when>
						</c:choose>
						
				</div>
			</div>
		</div>
	</div>

</body>
</html>