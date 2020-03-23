package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class PostgreSQLJDBC {
	
	public static void main(String args[]) {
		Connection c = null;
		
		try {
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/COVID",
	            "postgres", "Xboxlive72");
	         Statement statement = c.createStatement();
	         ResultSet resultSet = statement.executeQuery("SELECT * FROM \"covidData\".\"CaseData\"");
	         while (resultSet.next()) {
	                System.out.printf("%-30.30s, %d  \n", resultSet.getString("CountryNm"), resultSet.getInt("ConfirmedNm"));
	            }
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   }
}


