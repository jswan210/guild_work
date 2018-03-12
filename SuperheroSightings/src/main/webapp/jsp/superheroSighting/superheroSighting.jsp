<%-- 
    Document   : superheroSighting
    Created on : Jan 27, 2018, 2:43:08 PM
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
        <title>Superhero Sighting Superheros</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
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
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting">Superhero Sighting</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Admin
                            </a>
                        </li>                        
                    </sec:authorize>  
                </ul>
            </div>
            <div>
                <div class="col-md-6 " id="sighting-head">
                    <h2>Superhero Sighting</h2>
                </div>

                <div class="col-md-4 form-group btn btn-group">
                    <a href="${pageContext.request.contextPath}/superheroSighting/displayCreateSuperheroSightingForm" class="btn btn-default">Create New Sighting</a>
                </div> 

            </div>
            <div class="container">
                <table class='table table-striped' id="superhero-organization-table">
                    <thead style='background-color: lightgray'>
                        <tr>

                            <th width="15%">Superhero Name</th>
                            <th width="15%">Location Name</th>
                            <th width="15%">Date of Sighting</th>
                            <th width="10%"></th>
                            <th width="10%"></th>
                        </tr>
                    </thead>
                    <tbody  id='contentRows'>
                        <c:forEach var="currentSighting" items="${sightList}">
                            <tr>

                                <td>
                                    <a name="superSighting" 
                                       href="displaySuperheroSightingDetails?sightId=${currentSighting.sightId}">
                                        <c:out value="${currentSighting.superhero.superheroName}"/>
                                    </a> 
                                </td>

                                <td>
                                    <c:out value="${currentSighting.location.locationName}"/>
                                </td>

                                <td>
                                    <fmt:formatDate value="${currentSighting.date}" pattern="MMM dd, yyyy hh:mm a" var="formattedDate"/>
                                    <c:out value="${formattedDate}"/>
                                </td>

                                <td>
                                    <a href="displayEditSuperheroSighting?sightId=${currentSighting.sightId}">
                                        Edit
                                    </a>
                                </td>

                                <td>
                                    <a href="deleteSuperheroSigthing?sightId=${currentSighting.sightId}">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>

                </table>

            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
