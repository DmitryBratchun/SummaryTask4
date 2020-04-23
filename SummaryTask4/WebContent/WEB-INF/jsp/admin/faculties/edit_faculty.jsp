<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Edit faculty" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">
				<h2><c:out
					value="${language eq 'ru' ? faculty.nameRu : faculty.nameEn }" /> </h2>
					
					<%-- Registration form --%>
				
				<div align="center">			
				<div class="container" style="width: 800px">
					<form action="controller" method="post">
						<input type="hidden" name="command" value="editFaculty" />
						<input type="hidden" name="facultyId" value="${faculty.id}"/>
						<div align="center">
							<h3><label><fmt:message
									key="admin.faculties.edit_faculty_jsp.editor" /></label></h3>
							<p><h4><fmt:message
									key="admin.faculties.edit_faculty_jsp.subject_preliminary" /></h4>
						</div>
						<c:forEach var="subject" items="${preliminarySubjects}">			
								<div class="row">
									<div class="col-75">
										<c:out value="${language eq 'ru' ? subject.nameRu : subject.nameEn}" />
									</div>
									<div class="col-25">
										<input type="checkbox" name="preliminary${subject.id}" checked>
									</div>
								</div>							
						</c:forEach>
						<p>
						<c:forEach var="subject" items="${noPreliminarySubjects}">
								<div class="row">
									<div class="col-75">
										<c:out value="${language eq 'ru' ? subject.nameRu : subject.nameEn}" />
									</div>
									<div class="col-25">
										<input type="checkbox" name="preliminary${subject.id}">
									</div>
								</div>							
						</c:forEach>
						<p><h4><fmt:message
									key="admin.faculties.edit_faculty_jsp.faculty_name" /></h4>
									
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.faculties.edit_faculty_jsp.name_ru" /></label>
								</div>
								<div class="col-75">
									<c:out value="${faculty.nameRu}" />
								</div>
							</div>
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.faculties.edit_faculty_jsp.new_name_ru" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="newNameRu">
								</div>
							</div>
							
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.faculties.edit_facutly_jsp.name_en" /></label>
								</div>
								<div class="col-75">
									<c:out value="${faculty.nameEn}" />
								</div>
							</div>
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.faculties.edit_facutly_jsp.new_name_en" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="newNameEn">
								</div>
							</div>
							
							<p><h4><fmt:message
									key="admin.faculties.edit_faculty_jsp.places" /></h4>
									
							<div class="row">
								<div class="col-75">
									<label><fmt:message
											key="admin.faculties.edit_faculty_jsp.total_places" />
											<c:out value="${faculty.totalPlaces}" /> 
											</label>
								</div>
								<div class="col-25">
									<input type="number" name="newTotalPlaces" min="1" >
								</div>
							</div>
							
							<div class="row">
								<div class="col-75">
									<label><fmt:message
											key="admin.faculties.edit_faculty_jsp.budget_places" />
											<c:out value="${faculty.budgetPlaces}" /> 
											</label>
								</div>
								<div class="col-25">
									<input type="number" name="newBudgetPlaces" min="0" >
								</div>
							</div>
							<div class="row">
								<input class="form" type="submit" name="submit"
									value=<fmt:message key="admin.faculties.edit_faculty_jsp.save_changes" />>
									<input class="delete" type="submit" name="delete"
									value=<fmt:message key="admin.faculties.edit_faculty_jsp.delete" />>
							</div>
						</form>
					<c:if test="${not empty editFacultyErrorMessage}">
						<label class="validation"><fmt:message
								key="${editFacultyErrorMessage}" /></label>
						<c:remove var="editFacultyErrorMessage" />
					</c:if>
				</div>
				</div>
			</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>