<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<title>Home Page</title>
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
	<div> 
		<ul class="b">
			<li class="navLi"><a class="active" href="${pageContext.request.contextPath}">Home</a></li>
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
				<li class="navLi"><a href="${pageContext.request.contextPath}/game/create">Create
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
	<h2>Featuring Now</h2>
	<hr>
	<div style="background-color: #eeeeee;">
		<div class="w3-content w3-display-container">
			<c:forEach var="game" items="${games}">
				<c:if test="${game.premium == 'Yes'}">
					<div class="w3-display-container mySlides">
						<div
							style="color: #393e46; font-size: 20px; margin: 5px 150px 5px 150px; padding: 10px;"
							class="w3-display-bottomright w3-large w3-container w3-padding-16 w3-black">
							<c:out value="${game.title}" />
							- Available On -
							<c:out value="${game.platform}" />
							For $
							<c:out value="${game.price}" />
							Per Day - <a class="featured-links"
								href="${pageContext.request.contextPath}/game/showGame?gameId=${game.gameId}">
								More Details</a> - <a class="featured-links"
								href="${pageContext.request.contextPath}/booking/showBookingForm?gameId=${game.gameId}&userId=${user.id}">Rent
								Now!</a>
						</div>

						<a
							href="${pageContext.request.contextPath}/game/showGame?gameId=${game.gameId}">
							<img src="${game.imageUrl}"
							style="width: 80%; margin: 0px 0px 0px 150px; height: 500px;">
						</a> 

					</div>

				</c:if>
			</c:forEach>
			<button class="w3-button w3-display-left w3-black"
				onclick="plusDivs(-1)"
				style="background-color: #00adb5; color: #eeeeee; font-size: medium; margin: 0px 0px 0px 150px;">&#10094;</button>
			<button class="w3-button w3-display-right w3-black"
				onclick="plusDivs(1)"
				style="background-color: #00adb5; color: #eeeeee; font-size: medium;">&#10095;</button>
		</div>
	</div>



	<hr>

	<!-- display user name and role -->

	<p>
		Welcome : ${user.firstName} ${user.lastName}
	</p>

	<script>
		var slideIndex = 1;
		showDivs(slideIndex);

		function plusDivs(n) {
			showDivs(slideIndex += n);
		}

		function showDivs(n) {
			var i;
			var x = document.getElementsByClassName("mySlides");
			if (n > x.length) {
				slideIndex = 1
			}
			if (n < 1) {
				slideIndex = x.length
			}
			for (i = 0; i < x.length; i++) {
				x[i].style.display = "none";
			}
			x[slideIndex - 1].style.display = "block";
		}
	</script>
</body>

</html>