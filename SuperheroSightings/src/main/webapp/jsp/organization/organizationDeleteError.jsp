<%-- 
    Document   : organizationDeleteError
    Created on : Feb 7, 2018, 7:55:05 AM
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
        <title>Superhero Sighting Organization Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/location.css" rel='stylesheet'>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Superhero Sighting Web App</h1>
            <div class="alert alert-danger">
                <c:out value="${errorMsg}"/>
            </div>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
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
                    <h2>Organizations</h2>
                </div>
                <div class="col-md-4 form-group btn btn-group">
                    <a href="${pageContext.request.contextPath}/organization/displayCreateOrganizationForm" class="btn btn-default">Create New Organization</a>
                </div>
            </div>
        </div>
        <div class="container">
            <table class='table table-striped' id="locationTable">
                <thead style='background-color: lightgray'>
                    <tr>
                        <th width="5%"></th>
                        <th width="15%">Organization Name</th>
                        <th width="20%">Description</th>
                        <th width="20%">Address</th>
                        <th width="10%"></th>
                        <th width="10%"></th>
                    </tr>
                </thead>
                <tbody id='contentRows'>
                    <c:forEach var="currentOrg" items="${orgList}">
                        <tr>
                            <td></td>
                            <td>
                                <a href="displayOrganizationDetails?organizationId=${currentOrg.organizationId}">
                                    <c:out value="${currentOrg.organizationName}"/>
                                </a> 
                            </td>
                            <td>
                                <c:out value="${currentOrg.organizationDescription}"/>
                            </td>
                            <td>
                                <c:out value="${currentOrg.location.address}"/>, <c:out value="${currentOrg.location.city}"/>, 
                                <c:out value="${currentOrg.location.state}"/>
                            </td>

                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="displayEditOrganization?organizationId=${currentOrg.organizationId}">
                                        Edit
                                    </a>
                                </sec:authorize>
                            </td>
                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="deleteOrganization?organizationId=${currentOrg.organizationId}">
                                        Delete
                                    </a>
                                </sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>

            </table>

        </div>

    </body>

</html>