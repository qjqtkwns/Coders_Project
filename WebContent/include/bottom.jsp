<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<style type="text/css">


	  html, body {
        margin: 0;
      }
      #footer {
        width: 100%;
        background: blue;
      }

	footer {
		margin-top: 100px;
		background-color: #2b59c6;
	}
	
	.footer_Wrab{
	display: flex;
	min-width: 800px;
	}
	
	.footer-main {
		align-items: center;
		height: 8rem;
		width: 85.7142857143rem;
	    margin: 0 auto;
	    padding-left: 3.5714285714rem;
	    padding-right: 3.5714285714rem;
	    line-height: 13.3571428571rem;
	    display: flex;
	    justify-content: space-between;
	  
	}

 	.container-left {
  		min-width: 195px;
 	} 
	
/* 	.container-left h4 { */
/* 		font-size: 1.5rem; */
/* 		line-height: 5rem; */
/* 		color: #90CAF9; */
/* 	} */
	
	img {
		width: 3rem;
		height: 3rem;
	}
	
/* 	.container-center{ */
/* 		margin-left: 650px; */
/* 		justify-content: center; */
/* 		align-items: center; */
/* 	} */
	
 	.container-center h6 { 
 		color: cornflowerblue; 
 	} 
	
	.container-left a {
		color: white;
		text-decoration: none;
	}
	
	
</style>
</head>
<body>
    <div id="container"> </div>
    <div id="footer">
		<footer>

			<div class="footer_Wrab">
				<div class="footer-main">
					<div class="container-left">
						<h4>
							<a href="<%=request.getContextPath()%>/main.jsp"> <img
								src="<%=request.getContextPath()%>/upload/logo2.png">&nbsp;CODE
								BOARD
							</a>
						</h4>
					</div>

					<div class="container-center">
						<h6>Copyright &copy; 2022 CODE BOARD All rights reserved.</h6>
					</div>
				</div>
			</div>
		</footer>
	</div>
    <script>
      const footerDom = document.getElementById('container');
      for(i=0; i<10; i++){
        let p = document.createElement("p")
        p.append('');
        footerDom.append(p)
      }
    </script>
</body>
</html>