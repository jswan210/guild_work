<%-- 
    Document   : deleteLocationFail
    Created on : Feb 4, 2018, 6:40:14 AM
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
        <title>Superhero Sighting Location Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/location.css" rel='stylesheet'>
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
            <div class="alert alert-danger">
                <c:out value="${errorMsg}"/>

            </div> 

            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"  class="active"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
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
            <div>
                <div class="col-md-6 " id="locationHead">
                    <h2>Locations</h2>
                </div>
                <div class="col-md-4 form-group btn btn-group">
                    <a href="${pageContext.request.contextPath}/location/displayCreateLocationForm" class="btn btn-default">Create Location</a>

                </div>
            </div>
        </div>
        <div class='container'>
            <table class='table table-striped' id="locationTable">
                <thead style='background-color: lightgray'>
                    <tr>
                        <th width="5%"></th>
                        <th width="20%">Location Name</th>
                        <th width="15%"> Address</th>
                        <th width="10%"></th>
                        <th width="10%"></th>
                    </tr>
                <tbody id='contentRows'>

                    <c:forEach var="currentLocation" items="${locationList}">
                        <tr>
                            <td></td>
                            <td>
                                <a href="displayLocationDetails?locationId=${currentLocation.locationId}">
                                    <c:out value="${currentLocation.locationName}"/>
                                </a> 
                            </td>
                            <td>
                                <c:out value="${currentLocation.address}"/>
                            </td>
                            <td>
                                <a href="displayEditLocation?locationId=${currentLocation.locationId}">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="deleteLocation?locationId=${currentLocation.locationId}">
                                        Delete
                                    </a> 
                                </sec:authorize>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>



        </div>

