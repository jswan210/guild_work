<%-- 
    Document   : createSuperhero
    Created on : Jan 19, 2018, 8:36:42 AM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Superhero Sighting Create New Superhero</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">       
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
        <link href="${pageContext.request.contextPath}/css/superheroSighting.css" rel="stylesheet">
    </head>
    <body>
        <div class="container" >

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
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"  ><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/organization/organization">Organization</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
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
            <div id="createOrganization">
                <div  id="organizationHead">
                    <h2>Create New Superhero</h2>
                    <hr/>
                </div>
            </div> 
        </div>
        <div class="container">
            <form class="form-horizontal" 
                  role="form" 
                  method="POST" 
                  action="createSuperhero"
                  id="add-superhero-form">
                <div class="col-md-6" id="div-for-super-info">
                    <div class="form-group">
                        <label for="add-super-name"
                               class="col-md-5 control-label">
                            Superhero Name: 
                        </label>
                        <div class='col-md-6'>
                            <input type="text"
                                   class="form-control"
                                   name="superheroName"
                                   id="add-super-name"
                                   placeholder="Suerhero Name" required/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="add-super-description"
                               class="col-md-5 control-label">
                            Superhero Description: 
                        </label>
                        <div class='col-md-6'>
                            <input type="text"
                                   class="form-control"
                                   name="superheroDescription"
                                   id="add-super-description"
                                   placeholder="Suerhero Description" required/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-superpower"
                               class="col-md-5 control-label">
                            Superpower: 
                        </label>
                        <div class='col-md-6'>
                            <input type="text"
                                   class="form-control"
                                   name="superpower"
                                   id="add-superpower"
                                   placeholder="Superpower" required/>
                        </div>
                    </div>
                    <div id="first-submit-section">
                        <div class="form-group" id="add-org-btns">
                            <div class="form-group">
                                <label id="add-current-org-location" 
                                       class="col-md-5 control-label">
                                </label>
                                <div class="col-md-5">
                                    <select class="btn btn-default" name="organization-by-name" id="organization-dropdown">
                                        <option value="">--Choose An Organization--</option> 
                                        <c:forEach var="currentOrganizaiton" items="${orgList}">
                                            <option  value="<c:out value="${currentOrganizaiton.organizationId}"/>"><c:out value="${currentOrganizaiton.organizationName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <p class="col-md-offset-6 "> - OR - <br></p>

                            <div class="form-group ">
                                <label id="add-new-org-location" 
                                       class="col-md-5 control-label">
                                </label>
                                <div class ="col-md-5">
                                    <input class='btn btn-default' 
                                           type='button'
                                           id='create-new-org-btn' 
                                           onclick='showCreateNewOrganizationForm()'
                                           value='Create New Organization'/> 
                                </div> 

                            </div>
                            <div class="form-group">
                                    <div class="col-md-offset-3  col-md-5">
                                        <a href="${pageContext.request.contextPath}/superhero/superhero" class="btn btn-default">Cancel</a>
                                    </div>

                                    <div id='submit-superhero-new-org'>
                                        <div class="col-md-1" >
                                            <input id='new-superhero-submit' 
                                                   type="submit" 
                                                   class="btn btn-primary" 
                                                   value="Submit Superhero"/>
                                        </div>
                                    </div>
                                </div>

                        </div>
                    </div>
                    <!-- CREATE NEW ORGANIZATION DIV -->
                    <div id="add-new-org">
                        <div class="text-center">
                            <p> Insert Organization Info: </p>
                        </div>
                        <div class="form-group">
                            <label for="add-org-name"
                                   class="col-md-5 control-label">
                                Organization Name: 
                            </label>
                            <div class='col-md-6'>
                                <input type="text"
                                       class="form-control"
                                       name="organizationName"
                                       id="add-org-name"
                                       placeholder="Organization Name"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-org-description"
                                   class="col-md-5 control-label">
                                Organization Description:
                            </label>
                            <div class=" col-md-6">
                                <input type="text"
                                       class="form-control"
                                       name="organizationDescription"
                                       id="add-org-description"
                                       placeholder="Organization Description"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="add-org-contact"
                                   class="col-md-5 control-label">
                                Organization Contact:
                            </label>
                            <div class="col-md-6">
                                <input type="text"
                                       class="form-control"
                                       name="organizationContact"
                                       id="add-org-contact"
                                       placeholder="Organization Contact:"/>
                            </div>
                        </div>
                        <div id="div-for-new-location-btn">
                            <div class="form-group" id="loc-dropdown" >
                                <label id="add-current-org-location" 
                                       class="col-md-5 control-label">
                                </label>
                                <div class="col-md-5" >
                                    <select class="btn btn-default" name="location-by-name" id="location-dropdown">
                                        <option value="">--Choose A Location--</option> 
                                        <c:forEach var="currentLocation" items="${locList}">
                                            <option  value="<c:out value="${currentLocation.locationId}"/>"><c:out value="${currentLocation.locationName}"/></option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>  
                            <p class="col-md-offset-6 "> - OR - <br></p>


                            <div class='form-group'>
                                <label id="add-org-location" 
                                       class="col-md-5 control-label">
                                </label>
                                <div class="col-md-5 " id="create-new-location-btn">

                                    <input class='btn btn-default' 
                                           type='button'
                                           id='get-new-location' 
                                           onclick='getAddress()'
                                           value='Create New Location'/> 
                                </div>
                            </div>
                            <div class="form-group">
                                <div class=" col-md-offset-3 col-md-1">
                                    <a href="${pageContext.request.contextPath}/superhero/superhero" class="btn btn-default">Cancel</a>
                                </div>
                                <div id='submit-organization'>
                                    <div class="col-md-offset-3 col-md-1" >
                                        <input id='new-superhero' 
                                               type="submit" 
                                               class="btn btn-primary" 
                                               value="Submit Superhero"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>


                </div>
                <!-- CREATE NEW LOCATION DIV-->

                <div class="col-md-6" id="div-for-add-location">
                    <div class="form-group">
                        <label for="add-name"
                               class="col-md-4 control-label">
                            Location Name: 
                        </label>
                        <div class='col-md-7'>
                            <input type="text"
                                   class="form-control"
                                   name="locationName"
                                   id="add-name"
                                   placeholder="Location Name" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-description"
                               class="col-md-4 control-label">
                            Location Description:
                        </label>
                        <div class="col-md-7">
                            <input type="text"
                                   class="form-control"
                                   name="locationDescription"
                                   id="add-description"
                                   placeholder="Location Description" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-address"
                               class="col-md-4 control-label">
                            Location Address:
                        </label>
                        <div class="col-md-7">
                            <input type="text"
                                   class="form-control"
                                   name="address"
                                   onclick="showGetCoordinate()"
                                   id="add-address"
                                   placeholder="Location Address" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-city"
                               class="col-md-4 control-label">
                            City:
                        </label>
                        <div class="col-md-7">
                            <input type="text"
                                   class="form-control"
                                   name="city"
                                   onclick="showGetCoordinate()"
                                   id="add-city"
                                   placeholder="Location City" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="add-state"
                               class="col-md-4 control-label">
                            State:
                        </label>
                        <div class="col-md-7">
                            <input type="text"
                                   class="form-control"
                                   name="state"
                                   onclick="showGetCoordinate()"
                                   id="add-state"
                                   placeholder="Location State" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-zip"
                               class="col-md-4 control-label">
                            Zip:
                        </label>
                        <div class="col-md-7">
                            <input type="text"
                                   class="form-control"
                                   name="zip"
                                   pattern="[0-9]{5}"
                                   onclick="showGetCoordinate()"
                                   id="add-zip"
                                   placeholder="Location Zip" />
                        </div>
                    </div>

                    <div id="lat-long">

                        <div class="form-group">
                            <label for="add-latitude"
                                   class="col-md-4 control-label">
                                Latitude
                            </label>
                            <div class="col-md-7">
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
                                class="col-md-4 control-label">
                                Longitude
                            </label>
                            <div class="col-md-7"> 
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
                        <label
                            class="col-md-5 control-label">


                        </label>
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
                        <div class="col-md-offset-3  col-md-5">
                            <a href="${pageContext.request.contextPath}/superhero/superhero" class="btn btn-default">Cancel</a>
                        </div>
                        <div id='submit-w-new-location'>
                            <div class="col-md-1" >
                                <input id='new-location' 
                                       type="submit" 
                                       class="btn btn-primary" 
                                       value="Submit Superhero"/>
                            </div>
                        </div>
                    </div>

                </div>
            </form>


        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>
        <script src="${pageContext.request.contextPath}/js/createLocation.js"></script> 
        <script src="${pageContext.request.contextPath}/js/superhero.js"></script>

    </body>
</html>
