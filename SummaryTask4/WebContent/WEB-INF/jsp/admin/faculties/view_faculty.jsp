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
				<form action="controller">
				<input type="hidden" name="command" value="viewStatement" />
				<input type="hidden" name="facultyId" value="${faculty.id}" />
				<input class="form" style="width: 50%; font-size: 24px; padding: 4px; border-radius: 0px" type="submit" name="submit"
									value=<fmt:message key="admin.faculties.view_jsp.statement" />>
				</form>
				<form action="controller">
				<input type="hidden" name="command" value="editFaculty" />
				<input type="hidden" name="facultyId" value="${faculty.id}" />
				<input class="form" style="width: 50%; font-size: 24px; padding: 4px; border-radius: 0px" type="submit" name="submit"
									value=<fmt:message key="admin.faculties.view_jsp.edit" />>
				</form>
				<table id="customers">

					<tr>

						<th><fmt:message key="admin.faculties.view_jsp.first_name" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.last_name" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.login" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.email" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.diploma" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.preliminary" /></th>
						<th><fmt:message key="admin.faculties.view_jsp.action" /></th>

					</tr>
					<c:forEach var="application" items="${applications}">
					<c:if test="${application.isBlocked == true }">
						<tr style="color: red;">
					</c:if>
					<c:if test="${application.isBlocked == false }">
						<tr>
					</c:if>
						
							<td><c:out value="${application.firstName}" /></td>
							<td><c:out value="${application.lastName}" /></td>
							<td><c:out value="${application.login}" /></td>
							<td><c:out value="${application.email}" /></td>
							<td><c:out value="${application.diplomaScore}" /></td>
							<td><c:out value="${application.preliminaryScore}" /></td>
							<td>
								<c:if test="${application.isBlocked == false}">
								<form action="controller" method="post">
									<input type="hidden" name="command" value="blockEntrant" />
									<input type="hidden" name="facultyId" value="${faculty.id}" />
									<input type="hidden" name="page" value="${page}" />
									<input type="hidden" name="lines" value="${lines}" />
									<input type="hidden" name="entrantId"
										value="${application.entrantId}" /> <input class="form"
										style="width:50%; min-width:70px; padding: 4px; border-radius: 0px; background-color: red; float: left" type="submit"
										name="blocking"
										value=<fmt:message key="admin.faculties.view_jsp.block" />>
								</form>	
								</c:if>

								<c:if test="${application.entryTypeId == 0 }">
								<form action="controller" method="post">
									<input type="hidden" name="command" value="addToStatement" />
									<input type="hidden" name="facultyId" value="${faculty.id}" />
									<input type="hidden" name="page" value="${page}" />
									<input type="hidden" name="lines" value="${lines}" />
									<input type="hidden" name="entrantId"
										value="${application.entrantId}" />
									<input class="form"
										style="width:50%; min-width:70px; padding: 4px; border-radius: 0px; float: right" type="submit"
										name="submit"
										value=<fmt:message key="admin.faculties.view_jsp.add_to_statement" />>
								</form>
								</c:if>	
								<c:if test="${application.entryTypeId != 0 }">
									<fmt:message key="admin.faculties.view_jsp.added" />
								</c:if>	
							</td>
					</c:forEach>
					<tr>
						<th colspan="7">
						
						 	<form action="controller" style="margin: 0px">
						 		<a href="controller?command=viewFaculty&page=${page}&lines=5&facultyId=${faculty.id}" class="bot1g">x5</a>
						 		<a href="controller?command=viewFaculty&page=${page}&lines=10&facultyId=${faculty.id}" class="bot1g">x10</a>
								<a href="controller?command=viewFaculty&page=${page}&lines=15&facultyId=${faculty.id}" class="bot1g">x15</a>
								<a href="controller?command=viewFaculty&page=${page}&lines=25&facultyId=${faculty.id}" class="bot1g">x25</a>
								
								<a href="controller?command=viewFaculty&page=${page-1}&lines=${lines}&facultyId=${faculty.id}" class="bot1g"><c:out value="<"/></a>
								<c:out value="${page}"/>
							 	<a href="controller?command=viewFaculty&page=${page+1}&lines=${lines}&facultyId=${faculty.id}" class="bot1g"><c:out value=">"/></a> 
						 		<input type="hidden" name="command" value="viewFaculty" />
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