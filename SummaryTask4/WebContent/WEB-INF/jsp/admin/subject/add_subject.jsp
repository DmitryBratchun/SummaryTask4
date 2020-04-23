<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Add subject" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content">

				<div align="center">
					<div class="container">
						<form action="controller" method="post">
							<input type="hidden" name="command" value="addSubject" />
							<div align="center">
								<label><fmt:message
										key="admin.subjects.add_subject_jsp.add_subject" /></label>
							</div>
							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.subjects.add_subject_jsp.name_ru" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="nameRu">
								</div>
							</div>

							<div class="row">
								<div class="col-25">
									<label><fmt:message
											key="admin.subjects.add_subject_jsp.name_en" /></label>
								</div>
								<div class="col-75">
									<input type="text" name="nameEn">
								</div>
							</div>
							<div class="row">
								<input class="form" type="submit" name="submit"
									value=<fmt:message key="admin.subjects.add_subject_jsp.submit" />>
							</div>
						</form>
						<c:if test="${not empty addSubjectErrorMessage}">
							<label class="validation"><fmt:message
									key="${addSubjectErrorMessage }" /></label>
							<c:remove var="addSubjectErrorMessage" />
						</c:if>
					</div>
				</div>
			</td>
		</tr>
	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
	</table>
</body>
</html>