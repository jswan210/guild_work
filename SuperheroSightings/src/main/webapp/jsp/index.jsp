<%-- 
    Document   : index = SuperheroSightings 
                    Main Page
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Superhero Sighting Web App Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/googleMap.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-10">
                    <h1>Superhero Sighting Web App</h1>
                </div>
                <div  class="col-md-2">
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <h4>
                            <a href="<c:url value="/login" />" > Login</a>
                        </h4>
                    </c:if>
                    <div>
                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <p>Hello : ${pageContext.request.userPrincipal.name}
                                | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                            </p>
                        </c:if>  
                    </div>
                </div>
            </div>
            <ul id="errorMessages">

            </ul>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                        <sec:authorize access="hasRole('ROLE_USER')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
                        </sec:authorize>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                        <sec:authorize access="hasRole('ROLE_USER')">
                        <li role="presentation"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting">Superhero Sighting</a></li>
                        </sec:authorize>    
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li role="presentation">
                                <a href="${pageContext.request.contextPath}/displayUserList">
                                    User Admin
                                </a>
                            </li>                        
                        </sec:authorize>
                </ul>

            </div>
            <h2>Home Page</h2>
        </div>
                        <div class="row">
                            <div class="container col-md-offset-1 col-md-6" id="map">
                                <div class="well">
                                    <p>Have you ever wanted to stalk a superhero? <br>
                                        Now is your Chance!!!!<br>
                                        This website is designed to add new Superheroes (or Villain), following what organization they <br>may be apart of,
                                        and where it is that they may have been seen!!!<br>
                                        So now is your chance see if your superhero exists and let us know where you have found him/her!<br>
                                        Its not weird!! Its like what we do to celebrities and thats seems OK, right?<br>
                                        <a href="<c:url value="/login" />" > <b>Sign In Now to Access the cite fully!</b></a>

                    </p>   
                </div>

            </div>
            <div class="container  col-md-5">
                <sec:authorize access="isAuthenticated()">
                    <table class='table table-striped' id="superheroTable">
                    <thead style='background-color: lightgray'>
                        <tr>
                            <th width ="5%"> </th>
                            <th width="20%">Date</th>
                            <th width="15%">Superhero Name</th>
                            <th width="20%">Location</th>

                        </tr>
                    <tbody id='contentRows'>

                        <c:forEach var="currentSighting" items="${sightList}">
                            <tr>
                                <td></td>
                                <td>
                                    <fmt:formatDate value="${currentSighting.date}" pattern="MMM dd, yyyy hh:mm a" var="formattedDate"/>
                                    <c:out value="${formattedDate}"/>

                                </td>
                                <td>
                                    <c:out value="${currentSighting.superhero.superheroName}"/>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.location.locationName}"/>
                                </td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </sec:authorize>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>
        <script src="${pageContext.request.contextPath}/js/landingPage.js"></script>
    </body>
</html>

