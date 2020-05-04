<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Settings" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<tr>
		<td class="content">

<div align="center">
	<div class="container" >
		<form  action="controller" method="post">
			<input type="hidden" name="command" value="userSettings"/>
			<div align="center" >
				<label ><fmt:message key="client.settings_jsp.form.settings" /></label>
			</div>
			<div class="row">
				<div class="col-25">
					<label><fmt:message key="client.settings_jsp.form.new_password" /></label>
				</div>
				<div class="col-75">
					<input type="password" name="new_password" autocomplete="off">
				</div>
			</div>
		
			<div class="row">
				<div class="col-25">
					<label><fmt:message key="client.settings_jsp.form.new_email" /></label>
				</div>
				<div class="col-75">
					<input type="email" name="new_email" autocomplete="off">
				</div>
			</div>
		
			<div class="row">
				<c:if test="${language == 'ru'}">
					<fmt:message key="client.settings_jsp.form.russian" /><input type="radio" name="lang" id="ru" checked="checked" value="ru">
					<fmt:message key="client.settings_jsp.form.english" /><input type="radio" name="lang" id="en" value="en">
				</c:if>
				<c:if test="${language == 'en'}">
					<fmt:message key="client.settings_jsp.form.russian" /><input type="radio" name="lang" id="ru" value="ru">
					<fmt:message key="client.settings_jsp.form.english" /><input type="radio" name="lang" id="en" checked="checked" value="en">
				</c:if>
			</div>
			<div class="row" >
				<input class="form" type="submit" name="submit" value=<fmt:message key="client.settings_jsp.form.submit" />>
			</div>
		</form>
		<c:if test="${not empty userSettingsErrorMessage}">
			<label class="validation"><fmt:message key="${userSettingsErrorMessage }" /></label>
			<c:remove var="userSettingsErrorMessage"/>
		</c:if>
	</div>
</div>
</td>
</tr>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</table>
</body>
</html>