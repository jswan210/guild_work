<%-- 
    Document   : createSuperheroOrganziation
    Created on : Jan 21, 2018, 11:14:19 PM
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
        <title>Superhero Sighting Create New Superhero Organization Affiliation</title>
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
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/organization/organization">Organization</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
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
                <div class="container">
                    <h2>Create New Superhero Organization Affiliation</h2>
                    <form class="form-horizontal" 
                          role="form" 
                          method="POST" 
                          action="createSuperheroOrganization"
                          id="add-superhero-form">
                    <div class="col-md-8">

                        <div class="form-group">
                            <label id="add-superhero" 
                                   class="col-md-3 control-label">
                                Select Superhero:
                            </label>
                            <div class="col-md-6">
                                <select class="btn btn-default" 
                                        name="superhero-by-name" 
                                        id="superhero-dropdown" required>
                                    <option value="">--Choose A Superhero--</option> 
                                    <c:forEach var="currentSuperhero" items="${superList}">
                                        <option  value="<c:out value="${currentSuperhero.superheroId}"/>"><c:out value="${currentSuperhero.superheroName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label id="add-current-org-location" 
                                   class="col-md-3 control-label">
                                Select Organization:
                            </label>
                            <div class="col-md-6">
                                <select class="btn btn-default" 
                                        name="organization-by-name" 
                                        id="organization-dropdown" 
                                        required>
                                    <option value="">--Choose An Organization--</option> 
                                    <c:forEach var="currentOrganizaiton" items="${orgList}">
                                        <option  value="<c:out value="${currentOrganizaiton.organizationId}"/>"><c:out value="${currentOrganizaiton.organizationName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group"> 
                            <div class="col-md-3  control-label">
                                <a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization" class="btn btn-default">Cancel</a>
                            </div>


                            <div class="" id='submit-new-superhero-org-affiliation'>
                                <div class="col-md-offset-3 col-md-1" >
                                    <input id='new-superOrg' 
                                           type="submit" 
                                           class="btn btn-primary" 
                                           value="Submit Superhero Affiliation"/>
                                </div>
                            </div>

                        </div>
                    </div>

                </form>



            </div>

                                <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
                                <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
                                <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-Bl7bzgKMYkEqbPzcnqT3Dpv_b_4Pj3g" type="text/javascript" ></script>
                                <script src="${pageContext.request.contextPath}/js/createOrganization.js"></script>
                                <script src="${pageContext.request.contextPath}/js/createLocation.js"></script> 
        </body>
</html>
