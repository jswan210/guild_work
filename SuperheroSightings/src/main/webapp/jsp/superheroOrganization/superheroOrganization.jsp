<%-- 
    Document   : superheroOrganization
    Created on : Jan 21, 2018, 11:13:10 PM
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
        <title>Superhero Sighting Superhero-Organization Affiliation Page</title>
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
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
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
            <div>
                <div class="col-md-6 " id="locationHead">
                    <h2>Superhero Organization Affiliation</h2>
                </div>

                <div class="col-md-4 form-group btn btn-group">
                    <a href="${pageContext.request.contextPath}/superheroOrganization/displayCreateSuperheroOrganizationForm" class="btn btn-default">Create New Superhero Affiliation</a>
                </div> 

            </div>
        </div>
                    <div class="container">
                        <table class='table table-striped' id="superhero-organization-Table">
                            <thead style='background-color: lightgray'>
                                <tr>
                                    <th width="5%"></th>
                                    <th width="15%">Organization Name</th>
                                    <th width="20%">Superhero Name</th>
                                    <th width="10%"></th>
                                    <th width="10%"></th>
                                </tr>
                            </thead>
                            <tbody id='contentRows'>
                                <c:forEach var="currentSuperOrg" items="${superOrgList}">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <a name="superOrg" 
                                               href="displaySuperheroOrganizationDetails?organizationId=${currentSuperOrg.organization.organizationId}&amp;superheroId=${currentSuperOrg.superhero.superheroId}">
                                                <c:out value="${currentSuperOrg.organization.organizationName}"/>
                                            </a> 
                                        </td>



                            <td>
                                <c:out value="${currentSuperOrg.superhero.superheroName}"/>
                            </td>


                            <td>
                                <!-- Dont think im gonna need an edit...basically delete it and find another....right? -->
                      <!--          <a href="displayEditOrganization?organizationId=${currentSuperOrg.organization.organizationId}">
                                    Edit
                            </a>
                                -->
                            </td>
                            <td>
                                <a href="deleteSuperheroOrganization?organizationId=${currentSuperOrg.organization.organizationId}&amp;superheroId=${currentSuperOrg.superhero.superheroId}">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>

            </table> 


        </div>


    </body>
</html>
