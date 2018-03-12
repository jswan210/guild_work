<%-- 
    Document   : displayUserList
    Created on : Feb 23, 2018, 10:48:49 AM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin page</title>
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
            <c:if test="${errorMsg != null}">
                <div  class="alert alert-danger">
                    <c:out value="${errorMsg}"/>
                </div>
            </c:if>
            <hr/>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/index">Current Sightings</a></li>
                    <li role="presentation" ><a href="${pageContext.request.contextPath}/location/location">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/organization/organization">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superhero/superhero">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization">Superhero Organization Affiliation</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/superheroSighting/superheroSighting">Superhero Sighting</a></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation" class="active">
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Admin
                            </a>
                        </li>                        
                    </sec:authorize>  

                </ul>    
            </div>
            <h1>Users</h1>
            <a href="displayUserForm">Add a User</a><br/>
            <hr/>
            <table class='table table-striped' id="locationTable">
                <thead style='background-color: lightgray'>
                    <tr>
                        <th width="5%"></th>
                        <th width="20%">User Name</th>
                        <th width="15%"> Access</th>
                        <th width="10%"></th>
                        <th width="10%"></th>
                    </tr>
                </thead>
                <tbody id='contentRows'>

                    <c:forEach var="currentUser" items="${users}">
                        <tr>
                            <td></td>
                            <td><c:out value="${currentUser.username}"/>
                            </td>
                            <td>
                                <c:if test="${(fn:length(currentUser.authorities))==1}">
                                    <p> Disabled Access<p>
                                    </c:if>
                                    <c:if test="${(fn:length(currentUser.authorities))==2}">
                                    <p> User Access<p>
                                    </c:if>  
                                    <c:if test="${(fn:length(currentUser.authorities))==3}">
                                    <p> Admin Access<p>
                                    </c:if> 
                            </td>
                            <!--               <td>
               
                                <c:forEach var="authority" items="${currentUser.authorities}">
                                    <c:if test="${authority.equals('IS_AUTHENTICATED_ANONYMOUSLY')}"> 
                                        <p>Limited Access</p>
                                    </c:if>
                                    <c:if test="${authority.equals('ROLE_USER')}"> 
                                        <p>Sidekick Access</p>
                                    </c:if>
                                    <c:if test="${authority.equals('ROLE_ADMIN')}"> 
                                        <p>Admin Access</p>
                                    </c:if>  
                                </c:forEach>

                                           </td>
             -->
             <td>
                                <a href="displayEditUser?userId=${currentUser.id}"> 
                                    Edit 
                                </a>
                            </td>
                            <td>
                                <a href="deleteUser?userId=${currentUser.id}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
