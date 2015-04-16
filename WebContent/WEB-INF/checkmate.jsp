<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Chess Challange by Alex Guerrieri">
<meta name="author" content="Alex Guerrieri">
<link rel="icon" href="images/favicon.ico">

<title>Chess Challange</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Chess Challange</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="./">Home</a></li>
					<li><a href="https://github.com/klaidliadon/checkmate">Source</a></li>
					<li><a href="mailto:alex.d.guerrieri@gmail.com">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="starter-template">
			<h3>Checkmate</h3>
			<p>
				Find all unique configurations of a custom chess board where
				none of the pieces is in a position to take any of the others.
			</p>
			<c:choose>
	      		<c:when test="${result != null}">
	      			You searched the configurations of a ${width}x${height} board using the following pieces:
	      			<ul class="m-t">
	      				<c:if test="${!empty pawn}"><li>${pawn} Pawn${pawn!=1?'s':''}</li></c:if>
	      				<c:if test="${!empty bishop}"><li>${bishop} Bishop${bishop!=1?'s':''}</li></c:if>
	      				<c:if test="${!empty king}"><li>${king} King${king!=1?'s':''}</li></c:if>
	      				<c:if test="${!empty knight}"><li>${knight} Knight${knight!=1?'s':''}</li></c:if>
	      				<c:if test="${!empty rook}"><li>${rook} Rook${rook!=1?'s':''}</li></c:if>
	      				<c:if test="${!empty queen}"><li>${queen} Queen${queen!=1?'s':''}</li></c:if>
	      			</ul>
	      			<h3>Result</h3>
	      			<c:choose>
	      				<c:when test="${!empty result}">
	      					<table id="result" class="table m-t">
	      					
	      						<tr>
	      							<td></td>
	      							<c:forEach items="${pieces}" var="p">
										<th>${p.getClass().getSimpleName()}</th>
									</c:forEach>
	      						</tr>
								<c:forEach items="${result}" var="combination" varStatus="i">
									<tr class="result" title="Click to preview" data-toggle="tooltip" data-placement="left">
										<td><a >${i.getIndex()+1}</a></td>
										<c:forEach items="${combination}" var="p">
											<td>${p}</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</table>
							<div class="modal fade" id="preview">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">Board Preview</h4>
										</div>
										<div class="modal-body">
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-primary"
												data-dismiss="modal">Close</button>
										</div>
									</div>								
								</div>
							</div>
						</c:when>
	      				<c:otherwise>
	      				<p>No combination is possible</p>
	      				</c:otherwise>
	      			</c:choose>
	      			
	      		</c:when>
			      <c:otherwise>
					<form method="POST" action="">
						<div class="row">
							<div class="form-group col-sm-4">
								<label>Board Dimension</label>
								<div class="form-inline">
									<div class="input-group">
										<div class="input-group-addon"><span class="glyphicon glyphicon-option-horizontal" aria-hidden="true"></span></div>
										<input value="${width}" type="number" required="required" class="form-control" name="width" placeholder="Width" min="3" max="50">
									</div>
									<div class="input-group">
									<div class="input-group-addon"><span class="glyphicon glyphicon-option-vertical" aria-hidden="true"></span></div>
										<input value="${height}" type="number" required="required" class="form-control" name="height"	placeholder="Heigth" min="3" max="50">
									</div>
								</div>
							</div>
							<div class="form-group col-sm-8">
								<label>Number of Pieces</label>
								<div class="form-inline">
									<div class="input-group">
										<div class="input-group-addon"><img title="Pawn" src="images/pawn.png"/></div>
										<input type="number" class="form-control" name="pawn" placeholder="Pawn" min="1" max="10" disabled="disabled"> 
									</div>
											<div class="input-group">
										<div class="input-group-addon"><img title="Bishop" src="images/bishop.png"/></div>
										<input type="number" class="form-control" name="bishop" placeholder="Bishop" min="1" max="10"> 
									</div>
									<div class="input-group">
										<div class="input-group-addon"><img title="King" src="images/king.png"/></div>
										<input type="number" class="form-control" name="king"	placeholder="King" min="1" max="10">
									</div>
								</div>
								<div class="form-inline">
									<div class="input-group">
										<div class="input-group-addon"><img title="Knight" src="images/knight.png"/></div>
										<input type="number" class="form-control" name="knight"	placeholder="Knight" min="1" max="10">
									</div>
									<div class="input-group">
										<div class="input-group-addon"><img title="Queen" src="images/queen.png"/></div>
										<input type="number" class="form-control" name="queen"	placeholder="Queen" min="1" max="10">
									</div>
									<div class="input-group">
										<div class="input-group-addon"><img title="Rook" src="images/rook.png"/></div>
										<input type="number" class="form-control" name="rook"	placeholder="Rook" min="1" max="10">
									</div>
								</div>
							</div>
							<div class="form-group col-sm-12">
								<button type="submit" class="btn btn-primary">Search</button>
							</div>
						</div>			
					</form>
				</c:otherwise>
			</c:choose>
		</div>

	</div>
	<script	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<c:if test="${result!=null}"><script src="js/preview.js"></script></c:if>
	<script>
		var width = ${width}, height = ${height};
	</script>
</body>
</html>
