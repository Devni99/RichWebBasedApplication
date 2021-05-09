/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidpIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "ProductsAPI",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onItemSaveComplete(response.responseText, status);
			}
		});
});

function onItemSaveComplete(response, status) 
{
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidpIDSave").val("");
	$("#formItem")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "ProductsAPI",
			type: "DELETE",
			data: "pID =" + $(this).data("pid"),
			dataType: "text",
			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});

function onItemDeleteComplete(response, status) 
{
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidpIDSave").val($(this).data("pid"));
	$("#pCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#pName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#description").val($(this).closest("tr").find('td:eq(2)').text());
	$("#inventor").val($(this).closest("tr").find('td:eq(3)').text());
	$("#price").val($(this).closest("tr").find('td:eq(4)').text());
	$("#quantity").val($(this).closest("tr").find('td:eq(5)').text());
});

// CLIENT-MODEL================================================================
function validateItemForm() {
	// CODE
	if ($("#pCode").val().trim() == "") {
		return "Insert Product Code.";
	}
	// NAME
	if ($("#pName").val().trim() == "") {
		return "Insert Product Name.";
	}
	// DESC
	if ($("#description ").val().trim() == "") {
		return "Insert Product Description.";
	}
	// Invnetor
	if ($("#inventor").val().trim() == "") {
		return "Insert Product Inventor name.";
	}
	// PRICE-------------------------------
	if ($("#price").val().trim() == "") {
		return "Insert ProductPrice.";
	}
	// is numerical value
	var tmpPrice = $("#price").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Product Price.";
	}
	// convert to decimal price
	$("#price").val(parseFloat(tmpPrice).toFixed(2));
	
	// 	QUANTITY-------------------------------
	if ($("#quantity").val().trim() == "") {
		return "Insert Product Quantitiy.";
	}
	
	return true;
}

