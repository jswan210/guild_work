<%-- 
    Document   : editUser
    Created on : Feb 23, 2018, 12:22:01 PM
    Author     : jswan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit User</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"> 
    </head>
    <body>
        <div id="editUser" class="container">


            <div class="row">
                <div class="col-md-10">
                    <h1>Edit User: <c:out value ="${user.username}"/> </h1>
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
            <hr>

            <sf:form class="form-horizontal"
                     role="form"
                     modelAttribute="user"
                     action="editUser"
                     method="POST"
                     id="edit-user">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="edit-username" 
                                   class="col-md-2 control-label">
                                Username:
                            </label>
                            <div class="col-md-5">
                                <sf:input type="text"
                                          class="form-control"
                                          id="edit-username"
                                          path="username"
                                          placeholder="Enter Username"
                                          required="true"/>
                                <sf:errors path="username" cssclass="error"> </sf:errors>

                            </div>
                        </div>

                                    <div class="form-group">
                                        <label for="edit-password" 
                                               class="col-md-2 control-label">
                                            Password:
                                        </label>
                                        <div class="col-md-5">
                                        <sf:input type="password"
                                                  class="form-control"
                                                  id="edit-password"
                                                  path="password"
                                                  placeholder="Enter Password"
                                                  required="false"
                                                  readonly="true"/>
                                        <sf:errors path="password" cssclass="error"> </sf:errors>

                                </div>
                                </div>
                                <div class="form-group">
                                    <label for="edit-adminaccess" 
                                           class="col-md-2 control-label">
                                        Access(es):
                                    </label>
                                    <div class="col-md-5">
                                        <ul >
                                        <c:forEach var="authority" items="${user.authorities}">
                                            <li>
                                                <c:if test="${authority.equals('IS_AUTHENTICATED_ANONYMOUSLY')}">
                                                    Limited Access
                                                </c:if>
                                                <c:if test="${authority.equals('ROLE_USER')}"> 
                                                    Sidekick Access
                                                </c:if>
                                                <c:if test="${authority.equals('ROLE_ADMIN')}"> 
                                                    Admin Access
                                                </c:if> 
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <sf:errors path="authorities" cssclass="error"></sf:errors>
                                <sf:hidden path="authorities" />
                                <sf:hidden path="id"/>
                            </div>
                    </div>
                        <!-- changes to the User -->
                        <div class="col-md-6">

                        <div class="form-group">
                            <label for="change-password"
                                   class="col-md-4 control-label">
                                Change Password:
                            </label>
                            <div class='col-md-5'>
                                <input type="text"
                                       class="form-control"
                                       name="editPassword"
                                       id="change-password"
                                       maxlength="18"
                                       placeholder="Password Max 18 characters" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="radio sr-radio col-md-offset-1">
                                <label class="radio-inline"><input type="radio" name="editIsUser" >Sidekick (User Authority)</label>
                                <label class="radio-inline"><input type="radio" name="editIsAdmin" >Admin (Full Authority)</label>
                            </div>
                        </div>
                        <div class="col-md-3"></div> 
                        <p><b>- OR -</b> </p>

                        <div class="form-group" >
                            <div class="checkbox col-md-offset-1">
                                <label class="checkbox-inline"><input type="checkbox" name="disable" >Disable all Authority</label>
                            </div>
                        </div>
                    </div> 
                </div>

                            <div class="form-group">
                                <div class=" col-md-offset-1 col-md-1">
                                    <a href="${pageContext.request.contextPath}/displayUserList" class="btn btn-default">Cancel</a>
                                </div>
                    <div class="form-group">
                        <div class=" col-md-offset-1 col-md-1" id="submit-update">
                            <input class="btn  btn-primary" id="update-user" type="submit"  value="Update User"/>
                        </div>
                    </div>
                </div>


            </sf:form>


        </div>

    </body>
</html>
