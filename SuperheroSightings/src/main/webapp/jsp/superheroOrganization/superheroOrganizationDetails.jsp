<%-- 
    Document   : SuperheroOrganizationDetails
    Created on : Jan 23, 2018, 5:02:58 PM
    Author     : jswan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Superhero Organization Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-10">
                    <h1>Superhero Organization Affiliation Details</h1>
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
            <div class="row">
                <div class="col-md-6" >
                <h2> <b><c:out value="${superheroOrganization.superhero.superheroName}"/></b> </h2> 

                <p>
                    <c:out value="${superheroOrganization.superhero.superheroDescription}"/>
                </p>
                <p>
                    <c:out value="${superheroOrganization.superhero.superpower}"/>
                </p>

            </div>
            <div class="col-md-6">
                <h2> <b><c:out value="${superheroOrganization.organization.organizationName}"/></b> </h2> 
                <p>
                    <c:out value="${superheroOrganization.organization.organizationDescription}"/>
                </p>
                <p>
                    <c:out value="${superheroOrganization.organization.organizationContact}"/>
                </p>
                <p>
                    <c:out value="${superheroOrganization.organization.location.locationName}"/>
                    <br>
                    <c:out value="${superheroOrganization.organization.location.address}"/>,
                    <c:out value="${superheroOrganization.organization.location.city}"/>,
                    <c:out value="${superheroOrganization.organization.location.state}"/>
                </p>

            </div>
            </div>
            <div class="row">
                <div class="col-md-offset-1 col-md-1">
                    <br>
                    <a href="${pageContext.request.contextPath}/superheroOrganization/superheroOrganization" class="btn btn-default">Back to Superhero Organization Affiliation</a>
                </div>
            </div>
        </div>



        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
