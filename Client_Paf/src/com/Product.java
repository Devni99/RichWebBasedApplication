package com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Product {

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_db", "root", "Devni_@1999?");

			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}
	
	    //View Product Details
	
	    public String ViewProducts() {
		
		String output = "";
		
		Product product = new Product();
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Product Code</th>"+"<th>Product Name</th><th>Product Description</th>"
					+ "<th>Inventor Name</th><th>Price</th>"
					+ "<th>Quantity</th> " + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from products";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String pID = Integer.toString(rs.getInt("pID"));
				String pCode = rs.getString("pCode");
				String pName = rs.getString("pName");
				String description = rs.getString("description");
				String inventor = rs.getString("inventor");
				String price = Double.toString(rs.getDouble("price"));
				String quantity = Integer.toString(rs.getInt("quantity"));
				
				// Add into the html table
				output += "<tr><td>" + pCode + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + inventor + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + quantity + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-pid='" + pID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-pid='" + pID + "'></td></tr>";
	
			}
			con.close();
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while viewing products details.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	    //Insert Product Details
	
		public String insertProducts(String pCode, String pName, String description, String inventor ,String price , String quantity) {
			
			String output = "";

			try {

				Connection con = connect();

				if (con == null) {

					return "Error while connecting to the database";
				}

				// insert data

				String query = " insert into products (`pID`,`pCode`,`pName`,`description`,`inventor`,`price`,`quantity`)"
						+ " values (?, ?, ?, ?, ?, ?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, pCode);
				preparedStmt.setString(3, pName);
				preparedStmt.setString(4, description);
				preparedStmt.setString(5, inventor);
				preparedStmt.setString(6, price);
				preparedStmt.setString(7, quantity);

				// execute the statement
				preparedStmt.execute();
				con.close();
				String newItems = ViewProducts();
				
				output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
				
			} catch (Exception e) {

				output = "{\"status\":\"error\", \"data\":\"Error while inserting the Product.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
		
		
		//Update Product Details

		public String updateProducts(String pID,String pCode, String pName, String description, String inventor ,String price , String quantity) {

			String output = "";

			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				// create a prepared statement
				String query = "UPDATE products SET pCode=?,pName=?,description=?,inventor=?,price=?,quantity=? WHERE pID =?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values

				preparedStmt.setString(1, pCode);
				preparedStmt.setString(2, pName);
				preparedStmt.setString(3, description);
				preparedStmt.setString(4, inventor);
				preparedStmt.setDouble(5, Double.parseDouble(price));
				preparedStmt.setString(6,quantity);
				preparedStmt.setInt(7, Integer.parseInt(pID));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = ViewProducts();
				output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
				
			} catch (Exception e) {
				output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		//Delete QUERY //Not coded
		
		public String deleteProduct(String pID) {
			String output = "";
			try {

				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}

				// create a prepared statement
				String query = "DELETE FROM products WHERE pID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				  preparedStmt.setInt(1, Integer.parseInt(pID));
				  
				// execute the statement
				preparedStmt.execute();
				con.close();
				 
			     String newItems = ViewProducts();
			     output = "{\"status\":\"success\", \"data\": \"" +
			     newItems + "\"}";

			} catch (Exception e) {

				output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
				System.err.println(e.getMessage());
			}

			return output;
		}
	
		
}
