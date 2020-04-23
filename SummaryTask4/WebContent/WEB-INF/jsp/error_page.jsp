<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Error" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<c:if test="${not empty wrongfulActErrorMessage}">
			<tr style="height: 100px; vertical-align: text-top;">
				<th><label class="validation"><fmt:message
							key="${wrongfulActErrorMessage}" /></label> <c:remove
						var="wrongfulActErrorMessage" /></th>
			</tr>
		</c:if>
		<tr style="vertical-align: text-top;">
			<th><h3><fmt:message key="error_page_jsp.standart_error_message" /></h3></th>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>