<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Faculty" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">
				<h2><c:out
					value="${language eq 'ru' ? faculty.nameRu : faculty.nameEn }" /> </h2>
					
				<p><h3><fmt:message
					key="client.entry.entry_faculty_jsp.budget_places" /> 
					
				<c:out
					value="${faculty.budgetPlaces}" /> 
					
				<fmt:message
					key="client.entry.entry_faculty_jsp.total_places" /> 
				<c:out
					value="${faculty.totalPlaces}" /> </h3>
					
					<%-- Registration form --%>
				
				<div align="center">			
				<div class="container">
					<form action="controller" method="post">
						<input type="hidden" name="command" value="entryFaculty" />
						<input type="hidden" name="facultyId" value="${faculty.id}"/>
						<input type="hidden" name="diplomaSubjects" value="${diplomaSubjects}"/>
						<input type="hidden" name="preliminarySubjects" value="${preliminarySubjects}"/>
						<div align="center">
							<h3><label><fmt:message
									key="client.entry.entry_faculty_jsp.entry_form" /></label></h3>
							<p><label><fmt:message
									key="client.entry.entry_faculty_jsp.subject_diploma" /></label>
						</div>
						
						<c:forEach var="subject" items="${diplomaSubjects}">
								<div class="row">
									<div class="col-75">
										<c:out value="${language eq 'ru' ? subject.nameRu : subject.nameEn}" />
									</div>
									<div class="col-25">
										<input type="number" min="1" max="12" name="diploma${subject.id}">
									</div>
								</div>							
						</c:forEach>
						<div align="center">
							
							<label><fmt:message
									key="client.entry.entry_faculty_jsp.subject_preliminary" /></label>
						</div>
						<c:forEach var="subject" items="${preliminarySubjects}">			
								<div class="row">
									<div class="col-75">
										<c:out value="${language eq 'ru' ? subject.nameRu : subject.nameEn}" />
									</div>
									<div class="col-25">
										<input type="number" min="1" max="12" name="preliminary${subject.id}">
									</div>
								</div>							
						</c:forEach>
						<div class="row">
								<input class="form" type="submit" name="submit"
									value=<fmt:message key="client.entry.entry_faculty_jsp.submit" />>
						</div>
					</form>
					<c:if test="${not empty entryFacultyErrorMessage}">
						<label class="validation"><fmt:message
								key="${entryFacultyErrorMessage }" /></label>
						<c:remove var="entryFacultyErrorMessage" />
					</c:if>
				</div>
				</div>
			</tr>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</table>
</body>
</html>