<%-- 
    Document   : createLocationFail
    Created on : Feb 4, 2018, 6:41:51 AM
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
        <title>Superhero Sighting Create New Location</title>
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
            <div>
                <div class="alert alert-danger">
                    <c:out value="${errorMsg}"/>
                    <ul class="list-group" id="errorMessages"> </ul>
                </div>
            </div>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"  class="active"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting">Superhero Sighting</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Admin
                            </a>
                        </li>                        
                    </sec:authorize>
                </ul>    
            </div>
            <div id="createLocation">
                <div  id="locationHead">
                    <h2>Create New Location</h2>
                    <hr/>
                </div>
            </div> 
            <form class="form-horizontal" 
                  role="form" 
                  method="POST" 
                  action="createLocation"
                  id="add-form">
                <div class="form-group">
                    <label for="add-name"
                           class="col-md-2 control-label">
                        Location Name: 
                    </label>
                    <div class='col-md-5'>
                        <input type="text"
                               class="form-control"
                               name="locationName"
                               id="add-name"
                               placeholder="Location Name" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-description"
                           class="col-md-2 control-label">
                        Location Description:
                    </label>
                    <div class="col-md-5">
                        <input type="text"
                               class="form-control"
                               name="locationDescription"
                               id="add-description"
                               placeholder="Location Description" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-address"
                           class="col-md-2 control-label">
                        Location Address:
                    </label>
                    <div class="col-md-5">
                        <input type="text"
                               class="form-control"
                               name="address"
                               onclick="showGetCoordinate()"
                               id="add-address"
                               placeholder="Location Address" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-city"
                           class="col-md-2 control-label">
                        Location City
                    </label>
                    <div class="col-md-5">
                        <input type="text"
                               class="form-control"
                               name="city"
                               onclick="showGetCoordinate()"
                               id="add-city"
                               placeholder="Location City" required/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="add-state"
                           class="col-md-2 control-label">
                        State
                    </label>
                    <div class="col-md-5">
                        <input type="text"
                               class="form-control"
                               name="state"
                               onclick="showGetCoordinate()"
                               id="add-state"
                               placeholder="Location State" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-zip"
                           class="col-md-2 control-label">
                        Zip
                    </label>
                    <div class="col-md-5">
                        <input type="text"
                               class="form-control"
                               name="zip"
                               pattern="[0-9]{5}"
                               onclick="showGetCoordinate()"
                               id="add-zip"
                               placeholder="Location Zip" required/>
                    </div>
                </div>

                <div id="lat-long">

                    <div class="form-group">
                        <label for="add-latitude"
                               class="col-md-2 control-label">
                            Latitude
                        </label>
                        <div class="col-md-5">
                            <input type="text"
                                   class="form-control"
                                   name="latitude"
                                   id="add-latitude"
                                   placeholder="Location Latitude" 
                                   readonly/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label
                            for="add-longitude"
                            class="col-md-2 control-label">
                            Longitude
                        </label>
                        <div class="col-md-5"> 
                            <input type="text"
                                   class="form-control"
                                   name="longitude"
                                   id="add-longitude"
                                   placeholder="Location Longitude" 
                                   readonly/>
                        </div>
                    </div>
                </div>
                <div class="col-md-offset-2" id='cord-btn'>
                    <input class='btn btn-default' 
                           type='button'
                           id='get-coordinates' 
                           onclick='getAddress()'
                           value='Get Coordinates'/> 
                </div>
                <div>
                    <p></p>    
                </div> 
                <div class="form-group">
                    <div class=" col-md-offset-1 col-md-1">
                        <a href="${pageContext.request.contextPath}/location/location" class="btn btn-default">Cancel</a>
                    </div>
                    <div id='submit-location'>
                        <div class="col-md-offset-2 col-md-1" >
                            <input id='new-location' 
                                   type="submit" 
                                   class="btn btn-primary" 
                                   value="Submit Location"/>
                        </div>
                    </div>
                </div>
            </form> 

        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>
        <script src="${pageContext.request.contextPath}/js/createLocation.js"></script> 
    </body>
</html>
