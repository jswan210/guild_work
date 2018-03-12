<%-- 
    Document   : createSuperheroSighting
    Created on : Jan 27, 2018, 2:43:55 PM
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

            <hr>
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
            <div class="container">
                <h2>Create New Superhero Sighting</h2>
                <form class="form-horizontal" 
                      role="form" 
                      method="POST" 
                      action="createSuperheroSighting"
                      id="add-sighting-form">

                <div class="col-md-8">


                    <div class="form-group ">
                        <label for="datetime-input" 
                               class="col-md-3 control-label">Date & Time: 
                        </label>
                        <div class="col-md-4">
                            <input class="form-control" 
                                   type="datetime-local"
                                   name="date"
                                   id="datetime-input"
                                   required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label id="add-superhero" 
                               class="col-md-3 control-label">
                            Select Superhero:
                        </label>
                        <div class="col-md-6">
                            <select class="btn btn-default" name="superhero-by-name" id="superhero-dropdown" required>
                                <option value="">--Choose A Superhero--</option> 
                                <c:forEach var="currentSuperhero" items="${superList}">
                                    <option  value="<c:out value="${currentSuperhero.superheroId}"/>"><c:out value="${currentSuperhero.superheroName}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label id="add-current-location" 
                               class="col-md-3 control-label">
                            Select Location:
                        </label>
                        <div class="col-md-6">
                            <select class="btn btn-default" name="location-by-name" id="location-dropdown" required>
                                <option value="">--Choose A Location--</option> 
                                <c:forEach var="currentLocation" items="${locList}">
                                    <option  value="<c:out value="${currentLocation.locationId}"/>"><c:out value="${currentLocation.locationName}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </div> 
                    <div class="form-group">
                        <div class=" col-md-3 control-label">
                            <a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting" class="btn btn-default">Cancel</a>
                        </div>
                        <div id='submit-organization'>
                            <div class="col-md-offset-2 col-md-1" >
                                <input id='new-superhero' 
                                       type="submit" 
                                       class="btn btn-primary" 
                                       value="Submit Sighting"/>
                            </div>
                        </div>
                    </div>
                </div>

            </form>

        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
