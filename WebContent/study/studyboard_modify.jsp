<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="dto" value="${Modify }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.6.1.js"></script>
	
	
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

<style type="text/css">

@import
	url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400&display=swap')
	;

body {
	font-family: 'Noto Sans KR' !important;
}

.btn.btn-primary{
--bs-btn-bg: #2b59c6;
--bs-btn-border-color: #2b59c6;

--bs-btn-hover-color: #2b59c6;
--bs-btn-hover-bg: #ffffff00;
--bs-btn-hover-border-color: #2b59c6;

}
.btn.btn-outline-primary{
--bs-btn-border-color: #2b59c6;
--bs-btn-color: #2b59c6;

--bs-btn-hover-color: white;
--bs-btn-hover-bg: #2b59c6;
--bs-btn-hover-border-color: #2b59c6;
}

.container col-md-6 {
	margin: auto;
}
.card-subtitle{
	text-align: right;
    font-size: 0.9rem;
    color: gray;
}

#study_title_1 {
	border: 2px solid #2b59c6;
	background-color: #2b59c6;
	border-radius: 15px;
	width: 400px;
	color: white;
	padding-bottom: 5px;
	box-shadow: 3px 3px 2px -1px rgba(0, 0, 0, 0.48);
	-webkit-box-shadow: 3px 3px 2px -1px rgba(0, 0, 0, 0.48);
	-moz-box-shadow: 3px 3px 2px -1px rgba(0, 0, 0, 0.48);
	font-weight: bold;
}

#study_title_1 a {
	text-decoration: none;
	color: white;
}
</style>

</head>
<body>

	<c:if test="${empty userId }">
		<jsp:include page="../include/none_top.jsp" />
	</c:if>

	<c:if test="${!empty userId }">
		<jsp:include page="../include/user_top.jsp" />
	</c:if>

	<form method="post" enctype="multipart/form-data"
		action="<%=request.getContextPath()%>/studyboard_modify_ok.do">

		<input type="hidden" name="study_no" value="${dto.getStudy_num() }">
		<input type="hidden" name="study_writer"
			value="${dto.getStudy_writer() }"> <br>
			
		<div align="center">
			<h3 align="center" id = "study_title_1">⦁  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a href="<%=request.getContextPath() %>/studyBoard_list.do">Study</a> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ⦁</h3>
		</div>
			
		<br>
		<div class="align-middle">
			<div class="container col-md-6" style="width: 800px;">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title mb-3">
							<input name="study_title" class="form-control"
								value="${dto.getStudy_title() }" placeholder="제목을 입력하세요.">
						</h4>
						<h6 class="card-subtitle">
							<i class="fa-regular fa-user"></i>&nbsp;${dto.getStudy_writer() }
							&nbsp; <i class="fa-regular fa-clock"></i> ${dto.getStudy_date()}
							&nbsp; <i class="fa-regular fa-eye"></i> ${dto.getStudy_hit() }</h6>
						<br> <br>
						<p>
							<img class="card-img"
								src="<%=request.getContextPath()%>/study_upload/${dto.getStudy_file()}"
								alt="" /> <input class="form-control" class="card-img"
								type="file" name="study_file">
						</p>
						<p class="card-text">
							<textarea onkeyup="adjustHeight();" class="form-control"
								id="study_cont" name="study_cont" placeholder="본문을 입력하세요.">${dto.getStudy_cont() }</textarea>
						</p>

						<div class="mb-3 row">
							<label for="exampleFormControlInput1"
								class="col-sm-2 col-form-label">모집인원</label>
							<div class="col-sm-10">
								<input type="number" name="study_people" class="form-control"
									min="0" max="10" value="${dto.study_people }">
							</div>
						</div>

						<div class="mb-3 row">
							<label for="exampleFormControlInput1"
								class="col-sm-2 col-form-label">모집기간</label>
							<div class="col-sm-10">

								<input type="text" class="form-control" name="study_daterange" />
								<input type="hidden" name="study_start" id="study_start"
									title="시작일" maxlength="10"
									value="${dto.study_start.substring(0, 10) }"> <input
									type="hidden" name="study_end" id="study_end" title="종료일"
									maxlength="10" value="${dto.study_end.substring(0, 10) }">

							</div>
						</div>








						<div align="center">
							<input class="btn btn-primary" type="submit" value="수정">
							&nbsp;&nbsp; <input class="btn btn-outline-primary" type="reset"
								value="리셋"> &nbsp;&nbsp; <input type="button" value="취소"
								class="btn btn-outline-danger" onclick="history.back()">
						</div>

					</div>

				</div>
			</div>
		</div>
	</form>




	<script type="text/javascript">
		$(function() {
			function adjustHeight() {
				var textEle = $('textarea');
				textEle[0].style.height = 'auto';
				var textEleHeight = textEle.prop('scrollHeight');
				textEle.css('height', textEleHeight + 8);
			};

			adjustHeight();

			$('input[name="study_daterange"]')
					.daterangepicker(
							{

								locale : {
									"separator" : " ~ ",
									"format" : 'YYYY-MM-DD',
									"applyLabel" : "확인",
									"cancelLabel" : "취소",
									"daysOfWeek" : [ "일", "월", "화", "수", "목","금", "토" ],
									"monthNames" : [ "01월", "02월", "03월","04월", "05월", "06월", "07월", "08월","09월", "10월", "11월", "12월" ]
								},
								opens : 'left'
							},
							function(start, end, label) {
								$("#study_start").val(
										start.format('YYYY-MM-DD'));
								$("#study_end").val(end.format('YYYY-MM-DD'));
							});

			$('input[name="study_daterange"]').data('daterangepicker').setStartDate('${dto.study_start.substring(0, 10) }');
			$('input[name="study_daterange"]').data('daterangepicker').setEndDate('${dto.study_end.substring(0, 10) }');

		});
	</script>


	<script src="https://kit.fontawesome.com/7703fd875c.js" crossorigin="anonymous"></script>

	<jsp:include page="../include/bottom.jsp" />
	
</body>
</html>