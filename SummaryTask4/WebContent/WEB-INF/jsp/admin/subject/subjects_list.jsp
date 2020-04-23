<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Faculties" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		
		<tr>
			<td class="content" style="text-align: center;">
				<div align="center">
				<table id="customers" style="width: 500; text-align: center;">

					<tr>
						<th><fmt:message key="admin.subjects.subjects_list_jsp.subject_name" />
						<c:if test="${language == 'ru'}">
							<a href="controller?command=editSubjects&orederBy=name_ru&direction=ASC" class="bot1g">\/</a>
						 	<a href="controller?command=editSubjects&orederBy=name_ru&direction=DESC" class="bot1g">/\</a>
						</c:if>
						<c:if test="${language == 'en'}">
							<a href="controller?command=editSubjects&orederBy=name_en&direction=ASC" class="bot1g">\/</a> 
						 	<a href="controller?command=editSubjects&orederBy=name_en&direction=DESC" class="bot1g">/\</a> 
						</c:if> 
						 </th>
					</tr>
					<c:forEach var="subject" items="${subjects}">
						<tr><td>
							<form action="controller" method="post"> 
							<c:out value="${language eq 'ru' ? subject.nameRu : subject.nameEn}" />
							<input type="hidden" name="command" value="editSubjects" />
							<input class="sdel" type="submit" name="subject${subject.id}"
									value=<fmt:message key="admin.faculties.edit_faculty_jsp.delete" />></form>
							</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</td>
		</tr>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</table>
</body>
</html>