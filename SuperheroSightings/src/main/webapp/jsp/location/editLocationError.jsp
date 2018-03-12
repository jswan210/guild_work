<%-- 
    Document   : editLocationError
    Created on : Feb 7, 2018, 7:22:31 AM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Location</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    </head>
    <body>   
        <div id="createLocation" class="container">
            <div class="row">
                <div class="col-md-10">
                    <h1>Edit Location: <c:out value ="${location.locationName}"/> </h1>
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
            <div class="alert alert-danger">
                <c:out value="${errorMsg}"/>
                <ul class="list-group" id="errorMessages"> </ul>
            </div>
            <hr/>
            <sf:form class="form-horizontal" 
                     role="form" 
                     modelAttribute="location" 
                     action="editLocation" 
                     method="POST" 
                     id="add-form">
                <div class="form-group"> 
                    <label for="add-name" 
                           class="col-md-2 control-label">
                        Location Name: 
                    </label>
                    <div class='col-md-3'>
                        <sf:input type="text"
                                  class="form-control"
                                  id="add-name"
                                  path="locationName"
                                  placeholder="Enter Location Name"
                                  required="true"/>
                        <sf:errors path="locationName" cssclass="error"> </sf:errors>
                        </div>
                    </div >
                    <div class="form-group">
                        <label for="add-description" 
                               class="col-md-2 control-label">
                            Location Description: 
                        </label>
                        <div class='col-md-3'>
                            <div>
                            <sf:input type="text"
                                      class="form-control"
                                      id="add-description"
                                      path="locationDescription"
                                      placeholder="Enter Location Description"
                                      required="true"/>
                            <sf:errors path="locationDescription" cssclass="error"> </sf:errors>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-address" 
                               class="col-md-2 control-label">
                            Location Address: 
                        </label>
                        <div class='col-md-3'>
                            <div>
                            <sf:input type="text"
                                      class="form-control"
                                      id="add-address"
                                      path="address"
                                      onclick="showGetCoordinate()"
                                      placeholder="Enter Location Address"
                                      required="true"/>
                            <sf:errors path="address" cssclass="error"> </sf:errors>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-city" 
                               class="col-md-2 control-label">
                            Location City: 
                        </label>
                        <div class='col-md-3'>
                            <div>
                            <sf:input type="text"
                                      class="form-control"
                                      id="add-city"
                                      path="city"
                                      onclick="showGetCoordinate()"
                                      placeholder="Enter Location City"
                                      required="true"/>
                            <sf:errors path="city" cssclass="error"> </sf:errors>
                            </div>
                        </div>
                    </div>   
                    <div class="form-group">
                        <label for="add-state" 
                               class="col-md-2 control-label">
                            Location State: 
                        </label>
                        <div class='col-md-3'>
                            <div>
                            <sf:input type="text"
                                      class="form-control"
                                      id="add-state"
                                      path="state"
                                      onclick="showGetCoordinate()"
                                      placeholder="Enter Location State"
                                      required="true"/>
                            <sf:errors path="state" cssclass="error"> </sf:errors>
                            </div>
                        </div>
                    </div> 
                    <div class="form-group">
                        <label for="add-zip" 
                               class="col-md-2 control-label">
                            Location Zip: 
                        </label>
                        <div class='col-md-3'>
                            <div>
                            <sf:input type="text"
                                      class="form-control"
                                      id="add-zip"
                                      path="zip"
                                      onclick="showGetCoordinate()"
                                      placeholder="Enter Location zip"
                                      required="true"/>
                            <sf:errors path="zip" cssclass="error"> </sf:errors>
                            </div>
                        </div>
                    </div>
                    <div id="lat-long">
                        <div class="form-group">
                            <label for="add-latitude" 
                                   class="col-md-2 control-label">
                                Location Latitude: 
                            </label>
                            <div class='col-md-3'>
                                <div>
                                <sf:input type="text"
                                          class="form-control"
                                          id="add-latitude"
                                          path="latitude"
                                          placeholder="Latitude"
                                          readonly="true"
                                          required="true"/>
                                <sf:errors path="latitude" cssclass="error"> </sf:errors>
                                </div>
                            </div>
                        </div>   
                        <div class="form-group">
                            <label for="add-longitude" 
                                   class="col-md-2 control-label">
                                Location Longitude: 
                            </label>
                            <div class='col-md-3'>
                                <div>
                                <sf:input type="text"
                                          class="form-control"
                                          id="add-longitude"
                                          path="longitude"
                                          placeholder="longitude"
                                          readonly="true"/>
                                <sf:errors path="longitude" cssclass="error"> </sf:errors>
                                <sf:hidden path="locationId"/>
                            </div>
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
                    <div class="form-group">
                        <div class=" col-md-offset-1 col-md-1" id="submit-update">
                            <input class="btn  btn-primary" id="update-location" type="submit"  value="Update Location"/>
                        </div>
                    </div>
                </div>
            </sf:form>
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>
        <script src="${pageContext.request.contextPath}/js/createLocation.js"></script> 
    </body>
</html>
