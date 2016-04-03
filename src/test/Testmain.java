package test;
import java.sql.*;


public class Testmain {

	
	
	public static void asd() {
		
		String database="jdbc:postgresql://localhost/dbs_proj";
		String user = "Fiare";
	    String password = "fiare";
	    Connection conn;
		Statement statem;
		
		try {
			conn= DriverManager.getConnection(database, user, password);
			System.out.println("connected");
			
			statem=conn.createStatement();
		
		
		statem.executeUpdate("CREATE TABLE asd (id SERIAL PRIMARY KEY,name varchar(50),number int);");
		
		
		statem.executeUpdate("INSERT INTO asd (name,number) VALUES ('asd',1),('lol',2);");
		
		
		ResultSet res=statem.executeQuery("SELECT * FROM asd;");
		
		
		
		
		res.close();
		statem.close();
		conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}

}
