<%-- 
    Document   : superheroDetails
    Created on : Jan 19, 2018, 8:36:14 AM
    Author     : jswan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <title>Superhero Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <div class="container">
            <div class="col-md-offset-1">
                <div class="row">
                    <div class="col-md-10">
                        <h1>Superhero Details</h1>
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
            </div>
            <hr> 

            <div class="col-md-offset-1 container">

                <h2>
                    <b><c:out value="${superhero.superheroName}"/></b>
                </h2>
                <p>
                    <c:out value="${superhero.superheroDescription}"/>
                </p>
                <p>
                    <c:out value="${superhero.superpower}"/>
                </p>
                <br>
                <h5><b>Superhero is Affiliated with the following Organization(s): </b></h5>
                <ul> 
                    <c:forEach var="currentOrg" items="${orgListForOurSuper}">

                        <li> <c:out value="${currentOrg.organization.organizationName}"/></li>
                        </c:forEach>
                </ul>
            </div>
                    <div class=" col-md-offset-1 col-md-1">
                        <br>
                        <a href="${pageContext.request.contextPath}/superhero/superhero" class="btn btn-default">Back to Superheroes</a>
                    </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
