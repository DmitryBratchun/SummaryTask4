<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="View faculty" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		
		<tr>
			<td class="content">
				<form action="controller" method="post">
				<input type="hidden" name="command" value="fileStatement" />
				<input type="hidden" name="facultyId" value="${faculty.id}" />
				<input class="form" style="width: 100%; font-size: 24px; padding: 4px; border-radius: 0px" type="submit" name="submit"
									value=<fmt:message key="admin.faculties.view_statement_jsp.file_statement" />>
				</form>
				<table id="customers">
					<tr>

						<th><fmt:message key="admin.faculties.view_jsp.first_name" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.last_name" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.login" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.email" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.diploma" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.preliminary" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.status" /></th>

					</tr>
					<c:forEach var="application" items="${applications}">
						<tr>
							
							<td><c:out value="${application.firstName}" /></td>
							<td><c:out value="${application.lastName}" /></td>
							<td><c:out value="${application.login}" /></td>
							<td><c:out value="${application.email}" /></td>
							<td><c:out value="${application.diplomaScore}" /></td>
							<td><c:out value="${application.preliminaryScore}" /></td>
							<td><fmt:message key="admin.faculties.view_jsp.status_id_${application.entryTypeId}" /></td>
						</tr>
					</c:forEach>
					<tr>
						<th colspan="7">
						
						 	<form action="controller" style="margin: 0px">
						 		<a href="controller?command=viewStatement&page=${page}&lines=5&facultyId=${faculty.id}" class="bot1g">x5</a>
						 		<a href="controller?command=viewStatement&page=${page}&lines=10&facultyId=${faculty.id}" class="bot1g">x10</a>
								<a href="controller?command=viewStatement&page=${page}&lines=15&facultyId=${faculty.id}" class="bot1g">x15</a>
								<a href="controller?command=viewStatement&page=${page}&lines=25&facultyId=${faculty.id}" class="bot1g">x25</a>
								
								<a href="controller?command=viewStatement&page=${page-1}&lines=${lines}&facultyId=${faculty.id}" class="bot1g"><c:out value="<"/></a>
								<c:out value="${page}"/>
							 	<a href="controller?command=viewStatement&page=${page+1}&lines=${lines}&facultyId=${faculty.id}" class="bot1g"><c:out value=">"/></a> 
						 		<input type="hidden" name="command" value="viewStatement" />
								<input type="hidden" name="facultyId" value="${faculty.id}" />
								<input type="hidden" name="lines" value="${lines}" />
								<input style="padding: 2px; width: 100px; float: right; border-radius: 0px;" type="number" name="page" placeholder="${page}" width=10%>
								<input class="form" style="float: right min-width:70px; padding: 4px; border-radius: 0px;" type="submit" name="dfs" value="<fmt:message key="admin.faculties.view_jsp.go_to" />"/>
						 	</form> 
						</th>
					</tr>
				</table>
			</td>
		</tr>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</table>
</body>
</html>