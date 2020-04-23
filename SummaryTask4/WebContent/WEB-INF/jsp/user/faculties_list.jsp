<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Faculties" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		
		<tr>
			<td class="content">
				<table id="customers">

					<tr>
						<th><fmt:message key="user.faculties_list_jsp.faculty_name" />
						<c:if test="${language == 'ru'}">
							<a href="controller?command=viewAllFaculties&orederBy=name_ru&direction=ASC&page=${page-1}&lines=${lines}" class="bot1g">\/</a> 
						 	<a href="controller?command=viewAllFaculties&orederBy=name_ru&direction=DESC&page=${page-1}&lines=${lines}" class="bot1g">/\</a>  
						</c:if>
						<c:if test="${language == 'en'}">
							<a href="controller?command=viewAllFaculties&orederBy=name_en&direction=ASC&page=${page-1}&lines=${lines}" class="bot1g">\/</a> 
						 	<a href="controller?command=viewAllFaculties&orederBy=name_en&direction=DESC&page=${page-1}&lines=${lines}" class="bot1g">/\</a> 
						</c:if> 
						 </th>
						<th><fmt:message key="user.faculties_list_jsp.budget_places" />
							<a href="controller?command=viewAllFaculties&orederBy=budget_places&direction=ASC&page=${page-1}&lines=${lines}" class="bot1g">\/</a> 
						 	<a href="controller?command=viewAllFaculties&orederBy=budget_places&direction=DESC&page=${page-1}&lines=${lines}" class="bot1g">/\</a>  
						</th>
						<th><fmt:message key="user.faculties_list_jsp.total_places" />
							<a href="controller?command=viewAllFaculties&orederBy=total_places&direction=ASC&page=${page-1}&lines=${lines}" class="bot1g">\/</a> 
						 	<a href="controller?command=viewAllFaculties&orederBy=total_places&direction=DESC&page=${page-1}&lines=${lines}" class="bot1g">/\</a>  
						</th>
					</tr>
					<c:forEach var="faculty" items="${faculties}">
						<tr><td><a href="<c:url value="controller?command=viewFaculty"> 
						<c:param name="facultyId" value="${faculty.id}"/></c:url>">
							<c:out value="${language eq 'ru' ? faculty.nameRu : faculty.nameEn}" /></a></td>
							<td><c:out value="${faculty.budgetPlaces}" /></td>
							<td><c:out value="${faculty.totalPlaces}" /></td>
						</tr>
					</c:forEach>
					<tr>
						<th colspan="7">
						 	<form action="controller" style="margin: 0px">
						 		<a href="controller?command=viewAllFaculties&page=${page}&lines=5" class="bot1g">x5</a>
						 		<a href="controller?command=viewAllFaculties&page=${page}&lines=10" class="bot1g">x10</a>
								<a href="controller?command=viewAllFaculties&page=${page}&lines=15" class="bot1g">x15</a>
								<a href="controller?command=viewAllFaculties&page=${page}&lines=25" class="bot1g">x25</a>
								
								<a href="controller?command=viewAllFaculties&page=${page-1}&lines=${lines}" class="bot1g"><c:out value="<"/></a>
								<c:out value="${page}"/>
							 	<a href="controller?command=viewAllFaculties&page=${page+1}&lines=${lines}" class="bot1g"><c:out value=">"/></a> 
						 		<input type="hidden" name="command" value="viewAllFaculties" />
								<input type="hidden" name="lines" value="${lines}" />
								<input style="padding: 2px; width: 100px; float: right; border-radius: 0px;" type="number" name="page" placeholder="${page}" width=10%>
								<input class="form" style="float: right min-width:70px; padding: 4px; border-radius: 0px;" type="submit" name="dfs" value="<fmt:message key="admin.faculties.view_jsp.go_to" />"/>
						 	</form> 
						</th>
					</tr>
				</table>
			</td>
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>