<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Add faculty" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">
				<h2><fmt:message key="admin.faculties.add_faculty_jsp.create" /> </h2>
					
					<%-- Registration form --%>
				
				<div align="center">			
				<div class="container" style="width: 800px">
					<form action="controller" method="post">
						<input type="hidden" name="command" value="addFaculty" />
						<input type="hidden" name="facultyId" value="${faculty.id}"/>
						<div align="center">
							<h3><label><fmt:message
									key="admin.faculties.add_faculty_jsp.editor" /></label></h3>
							<p><h4><fmt:message
									key="admin.faculties.add_faculty_jsp.preliminary" /></h4>
						</div>

						<c:forEach var="subject" items="${subjects}">
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
									key="admin.faculties.add_faculty_jsp.faculty_name" /></h4>
									
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.faculties.add_faculty_jsp.name_ru" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="nameRu">
								</div>
							</div>
							
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.faculties.add_faculty_jsp.name_en" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="nameEn">
								</div>
							</div>
							
							<p><h4><fmt:message
									key="admin.faculties.add_faculty_jsp.places" /></h4>
									
							<div class="row">
								<div class="col-75">
									<label><fmt:message
											key="admin.faculties.add_faculty_jsp.total_places" />
											</label>
								</div>
								<div class="col-25">
									<input type="number" name="totalPlaces" min="1" >
								</div>
							</div>
							
							<div class="row">
								<div class="col-75">
									<label><fmt:message
											key="admin.faculties.add_faculty_jsp.budget_places" />
											</label>
								</div>
								<div class="col-25">
									<input type="number" name="budgetPlaces" min="0" >
								</div>
							</div>
							<div class="row">
								<input class="form" type="submit" name="submit"
									value=<fmt:message key="admin.faculties.add_faculty_jsp.save_changes" />>
							</div>
						</form>
					<c:if test="${not empty addFacultyErrorMessage}">
						<label class="validation"><fmt:message
								key="${addFacultyErrorMessage}" /></label>
						<c:remove var="addFacultyErrorMessage" />
					</c:if>
				</div>
				</div>
			</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body>
</html>