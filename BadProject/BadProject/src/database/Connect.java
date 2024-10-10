package database;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

import model.Cartdata;
import model.Hoodie;
import model.User;

public class Connect {
	
	private final String username = "root";
	private final String password = "";
	private final String database = "ho-ohdie";
	private final String host = "localhost:3306";
	private final String connection = String.format("jdbc:mysql://%s/%s", host, database);

	private Connection con;
	private static PreparedStatement ps;
	
	public static Connect connect;
	public ResultSet rs;
	
	public static Connect getInstance() {
		if (connect == null) {
			connect = new Connect();
		}
		return connect;
		
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(connection, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet selectData(String query) {
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rs;
		
	}
	

	public void registerUser(String query, User user) {
	    try {
	        // Check if the connection is established (con is not null)
	        if (con == null) {
	            System.out.println("Connection is null");
	            return;
	        }
	        
	        // Ensure the query has the correct number of placeholders
	        System.out.println("Executing query: " + query); // Print the query for debugging
	        ps = con.prepareStatement(query);
	        
	        // Setting parameters in the prepared statement
	        ps.setString(1, user.getUserID());
	        ps.setString(2, user.getEmail());
	        ps.setString(3, user.getUsername());
	        ps.setString(4, user.getPassword());
	        ps.setString(5, user.getPhoneN());
	        ps.setString(6, user.getAddress());
	        ps.setString(7, user.getGender());
	        ps.setString(8, user.getRole());
	        
	        
	        // Execute the update
	        int rowsAffected = ps.executeUpdate();
	        System.out.println(rowsAffected + " row(s) affected");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	}
	
public void registerHoodie(String query, Hoodie hoodie) {
		
		try {
			ps =con.prepareStatement(query);
			ps.setString(1,hoodie.getHoodieId());
			ps.setString(2,hoodie.getHoodieName());
			ps.setDouble(3,hoodie.getPriceHoodie());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}

public void registercartd(String query, Cartdata cartd) {
	try {
		ps =con.prepareStatement(query);
		ps.setString(1,cartd.getUserId());
		ps.setString(2,cartd.getHoodieId());
		ps.setDouble(3,cartd.getQuantity());
		ps.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void deleteHoodie(String query) {
    try {
        ps = con.prepareStatement(query);

        ps.executeUpdate();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
public void updateHoodie(String query, double priceHoodie, String hoodieId, String hoodieName) {
    try {
        ps = con.prepareStatement(query);
        ps.setDouble(1, priceHoodie);
        ps.setString(2, hoodieId);
        ps.setString(3, hoodieName);

        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
