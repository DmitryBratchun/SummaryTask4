<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Profile" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
		<c:forEach var="i" begin="0" end="1" step="1">
		<tr>
			<td class="content">
				<c:out value="${not empty user.lang ? user.lang : df}"/>
			</td>
		<tr>
		</c:forEach>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>