<%@page import="com.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Management</title>
<link rel="stylesheet" href="Views/bootstrap.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/product.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Products Management Service</h1>
				<form id="formItem" name="formItem">
					Product Code: <input id="pCode" name="pCode" type="text"
						class="form-control form-control-sm"> <br>
					Product Name:<input id="pName" name="pName" type="text"
						class="form-control form-control-sm"> <br> 
				    Product Description: <input id="description" name="description" type="text"
						class="form-control form-control-sm"> <br> 
					Inventor: <input id="inventor" name="inventor" type="text"
						class="form-control form-control-sm"> <br>
				    Product Price: <input id="price" name="price" type="text"
						class="form-control form-control-sm"> <br>
					Quantity: <input id="quantity" name="quantity" type="text"
						class="form-control form-control-sm"> <br>
							
						 <input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidpIDSave" name="hidpIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					<%
					               Product product = new Product();
								   out.print(product.ViewProducts());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>