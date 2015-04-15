<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Chess Challange by Alex Guerrieri">
<meta name="author" content="Alex Guerrieri">
<link rel="icon" href="../../favicon.ico">

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
			<form method="POST" action="CheckMate">
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
		</div>

	</div>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
