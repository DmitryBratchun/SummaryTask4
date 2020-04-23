<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">
				<div align="center">
					<div class="container">
						<form id="login_form" action="controller" method="post">
								<input type="hidden" name="command" value="login" />
							<div align="center">
								<label><fmt:message key="login_jsp.form.authorization" /></label>
							</div>
							<div class="row">
								<%--<label>Login</label> --%>
								<div class="col-25">
									<label><fmt:message key="registration_jsp.form.login" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="login"
										placeholder=<fmt:message key="login_jsp.form.login" />
										required>
								</div>
							</div>
							<div class="row">
								<%--<label>Password</label> --%>
								<div class="col-25">
									<label><fmt:message key="login_jsp.form.password" /></label>
								</div>
								<div class="col-75">
									<input type="password" name="password"
										placeholder=<fmt:message key="login_jsp.form.password" />
										required>
								</div>
							</div>
							<div class="row">
								<input class="form" type="submit" name="submit"
									value=<fmt:message key="login_jsp.submit" />>
							</div>
						</form>
						<div class="row">
							<label><fmt:message key="login_jsp.dont_have_acount" />
								<a href="registration.jsp"><fmt:message key="login_jsp.click_here" /></a></label>
						</div>
						<c:if test="${not empty loginErrorMessage}">
							<label class="validation"><fmt:message
									key="${loginErrorMessage }" /></label>
							<c:remove var="loginErrorMessage" />
						</c:if>
					</div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>