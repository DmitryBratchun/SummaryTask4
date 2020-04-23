<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Blocked users" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		
		<tr>
			<td class="content">
				<table id="customers">
					<tr>

						<th><fmt:message key="admin.blocked_users_jsp.first_name" /></th>
						<th><fmt:message key="admin.blocked_users_jsp.last_name" /></th>
						<th><fmt:message key="admin.blocked_users_jsp.login" /></th>
						<th><fmt:message key="admin.blocked_users_jsp.email" /></th>
						<th><fmt:message key="admin.blocked_users_jsp.action" /></th>


					</tr>
					<c:forEach var="entrant" items="${entrants}">
						<tr>
							
							<td><c:out value="${entrant.firstName}" /></td>
							<td><c:out value="${entrant.lastName}" /></td>
							<td><c:out value="${entrant.login}" /></td>
							<td><c:out value="${entrant.email}" /></td>
							<td>
								<form action="controller" method="post">
									<input type="hidden" name="command" value="unblockEntrant" />
									<input type="hidden" name="page" value="${page}" />
									<input type="hidden" name="lines" value="${lines}" />
									<input type="hidden" name="entrantId"
										value="${entrant.id}" /> <input class="form"
										style="width:100%; min-width:70px; padding: 4px; border-radius: 0px;  float: left" type="submit"
										name="blocking"
										value=<fmt:message key="admin.blocked_users_jsp.unblock" />>
								</form>	
							</td>
						</tr>
					</c:forEach>
					<tr>
						<th colspan="5">
						
						 	<form action="controller" style="margin: 0px">
						 		<a href="controller?command=viewBlockedEntrants&page=${page}&lines=5" class="bot1g">x5</a>
						 		<a href="controller?command=viewBlockedEntrants&page=${page}&lines=10" class="bot1g">x10</a>
								<a href="controller?command=viewBlockedEntrants&page=${page}&lines=15" class="bot1g">x15</a>
								<a href="controller?command=viewBlockedEntrants&page=${page}&lines=25" class="bot1g">x25</a>
								
								<a href="controller?command=viewBlockedEntrants&page=${page-1}&lines=${lines}" class="bot1g"><c:out value="<"/></a>
								<c:out value="${page}"/>
							 	<a href="controller?command=viewBlockedEntrants&page=${page+1}&lines=${lines}" class="bot1g"><c:out value=">"/></a> 
						 		<input type="hidden" name="command" value="viewBlockedEntrants" />
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