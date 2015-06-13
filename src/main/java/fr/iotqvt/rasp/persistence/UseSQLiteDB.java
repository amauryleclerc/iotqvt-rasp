package fr.iotqvt.rasp.persistence;

// http://tips.androidhive.info/2013/10/android-insert-datetime-value-in-sqlite-database/

import java.io.IOException;
import java.sql.*;

public class UseSQLiteDB {
	
	private String DBPath = "./iotqvt.db";
	private Connection connection = null;
	private Statement statement = null;

	public UseSQLiteDB(String dBPath) throws IOException {
		DBPath = dBPath;
	}
	
	public void connect() throws IOException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
			statement = connection.createStatement();
			System.out.println("Connexion a " + DBPath + " avec succès");
		} catch (ClassNotFoundException notFoundException) {
			notFoundException.printStackTrace();
			System.out.println("Erreur de connexion");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("Erreur de connexion");
		}
	}

	public void close() throws IOException {
		try {
			connection.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Create DB if rebuild is false
	
	public void createDB(boolean rebuild) throws IOException {
		
		    Connection c = null;
		    Statement stmt = null;
		    String sql =null;
		    
		    try {
		    	
			 Class.forName("org.sqlite.JDBC");
			 c = DriverManager.getConnection("jdbc:sqlite:test.db");
			 System.out.println("Opened database successfully");
		     
			 stmt = c.createStatement();
		      
		     sql = "CREATE TABLE IF NOT EXISTS IOTCAPTEURS " +
		                   "(ID_IOT INT     		NOT NULL," +
		                   " ID_CAP INT     		NOT NULL," +
		                   " M_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP    NOT NULL," +
		                   " M_VAL  DECIMAL         NOT NULL," ;
		      
		      stmt.executeUpdate(sql);
		      stmt.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Table created successfully");
		  }

	public void addValue(String id_iot, String id_cap, long m_timestamp, Float m_val) throws IOException  {

		Connection c = null;
	    Statement stmt = null;
	    Date d = null;
	    
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
	      c.setAutoCommit(false);
	      System.out.println("addVAlue");

	      d = new Date(m_timestamp);
	      
	      // inserting data
	      PreparedStatement prep = c.prepareStatement("insert into IOTCAPTEURS values(?,?,?,?);");
	      prep.setInt(1, Integer.parseInt(id_iot)); 
	      prep.setInt(2, Integer.parseInt(id_cap));
	      prep.setDate(3, d);
	      prep.setFloat(4, m_val);
	      
	      System.out.println("requete ajout :"  );
	      
	      prep.execute();
	      prep.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	
	}
	

	public void afficheValues () throws IOException {
	
		  Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
		      while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         int age  = rs.getInt("age");
		         String  address = rs.getString("address");
		         float salary = rs.getFloat("salary");
		         System.out.println( "ID = " + id );
		         System.out.println( "NAME = " + name );
		         System.out.println( "AGE = " + age );
		         System.out.println( "ADDRESS = " + address );
		         System.out.println( "SALARY = " + salary );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
		  }
	
	
	public void setValues() throws IOException {
		 Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
		      stmt.executeUpdate(sql);
		      c.commit();

		      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
		      while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         int age  = rs.getInt("age");
		         String  address = rs.getString("address");
		         float salary = rs.getFloat("salary");
		         System.out.println( "ID = " + id );
		         System.out.println( "NAME = " + name );
		         System.out.println( "AGE = " + age );
		         System.out.println( "ADDRESS = " + address );
		         System.out.println( "SALARY = " + salary );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Operation done successfully");
	}

	public void delValues() throws IOException {

	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "DELETE from COMPANY where ID=2;";
	      stmt.executeUpdate(sql);
	      c.commit();

	      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }


	
}


