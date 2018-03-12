<%-- 
    Document   : editOrganization
    Created on : Jan 18, 2018, 11:03:53 AM
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
        <title>Edit Organization</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
    </head>
    <body>
        <div class="container" id="createOrganization">

            <div class="row">
                <div class="col-md-10">
                    <h1>Edit Organization: <c:out value="${organization.organizationName}"/> </h1>
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
            <div class="container">

                <hr/>
                <div>
                    <sf:form class="form-horizontal" 
                             role="form" 
                             modelAttribute="organization" 
                             action="editOrganization" 
                             method="POST" 
                             id="add-form">

                        <div id="org-edit" class="col-md-6">
                            <div class="form-group">
                                <label for="add-org-name" 
                                       class="col-md-5 control-label">
                                    Organization Name: 
                                </label>
                                <div class='col-md-6'>
                                    <sf:input type="text"
                                              class="form-control"
                                              id="add-org-name"
                                              path="organizationName"
                                              placeholder="Enter Organization Name"
                                              required="true"/>
                                    <sf:errors path="organizationName" cssclass="error"> </sf:errors>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="add-org-description" 
                                           class="col-md-5 control-label">
                                        Organization Description: 
                                    </label>
                                    <div class='col-md-6'>
                                    <sf:input type="text"
                                              class="form-control"
                                              id="add-org-description"
                                              path="organizationDescription"
                                              placeholder="Enter Organization Description"
                                              required="true"/>
                                    <sf:errors path="organizationDescription" cssclass="error"> </sf:errors>

                                    </div>
                                </div>

                                        <div class="form-group">
                                            <label for="add-org-contact" 
                                                   class="col-md-5 control-label">
                                                Organization Contact: 
                                            </label>
                                            <div class='col-md-6'>
                                            <sf:input type="text"
                                                      class="form-control"
                                                      id="add-org-contact"
                                                      path="organizationContact"
                                                      placeholder="Enter Organization Contact"
                                                      required="true"/>
                                            <sf:errors path="organizationContact" cssclass="error"> </sf:errors>
                                            <sf:hidden path="organizationId"/>
                                        </div>
                                    </div>
                                    <div class="form-group"> 
                                        <div id="add-location-name-block">
                                            <label for="add-name" 
                                                   class="col-md-5 control-label" >
                                                Location Name: 
                                            </label>
                                            <div class='col-md-6'>
                                                <sf:input type="text"
                                                          class="form-control"
                                                          id="add-name"
                                                          path="location.locationName"
                                                          placeholder="Enter Location Name"
                                                          readonly="true"
                                                          required="true"/>
                                                <sf:errors path="location.locationName" cssclass="error"> </sf:errors>
                                                <sf:hidden path="location.locationId"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div>

                                <div class="form-group ">

                                    <input class='btn btn-default col-md-offset-4' 
                                           type='button'
                                           id='change-location-btn' 
                                           onclick='displayToChangeLocation()'
                                           value='Click to Change Location'/> 
                                </div>
                                <div class="form-group" id="left-cancel-submit-div">
                                    <div class=" col-md-offset-3 col-md-1">
                                        <a href="${pageContext.request.contextPath}/organization/organization" class="btn btn-default">Cancel</a>
                                    </div>
                                    <div class="form-group">
                                        <div class=" col-md-offset-1 col-md-1">
                                            <input class="btn btn-primary" 
                                                   id="update-organization" 
                                                   type="submit"  
                                                   value="Update Organization"/>
                                        </div>
                                    </div>
                                </div>
                            </div >
                        </div>
                        <div id="loc-edit" class="col-md-6">

                            <h3 class="text-center " id="update-location-heading"> Update Location </h3>
                            <div id="choose-or-create-new">
                                <div class="form-group" id="loc-dropdown-div">

                                    <select class="btn btn-default col-md-offset-4" name="location-by-name" id="location-dropdown">
                                        <option value="">--Choose A Location--</option> 
                                        <c:forEach var="currentLocation" items="${locList}">
                                            <option  value="<c:out value="${currentLocation.locationId}"/>"><c:out value="${currentLocation.locationName}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </div>


                            <div class="form-group " id="right-cancel-submit-div">
                                <div class="col-md-offset-3 col-md-4 ">
                                    <a href="${pageContext.request.contextPath}/organization/organization" class="btn btn-default">Cancel</a>
                                </div>
                                <div class="form-group">
                                    <div class=" col-md-2 ">
                                        <input class="btn btn-primary" 
                                               id="update-organization" 
                                               type="submit"  
                                               value="Update Organization"/>
                                    </div>
                                </div>
                                    <p><sub>**If you do not see your location <a href="${pageContext.request.contextPath}/location/displayCreateLocationForm">create a New Location </a>**</sub></p>
                            </div>
                        </div>
                    </sf:form>
                </div>
            </div>

        </div>
                    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
                    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
                    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>
                    <script src="${pageContext.request.contextPath}/js/createLocation.js"></script> 
                    <script src="${pageContext.request.contextPath}/js/editOrganization.js"></script>

    </body>
</html>
