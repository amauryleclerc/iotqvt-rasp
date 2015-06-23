package fr.iotqvt.rasp.persistence;


import java.io.IOException;
import java.sql.*;

import fr.iotqvt.rasp.modele.Mesure;

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

	public void createDB() throws IOException {
		    Connection c = null;
		    Statement stmt = null;
		    String sql =null;
		    
		    try {
		     Class.forName("org.sqlite.JDBC");
		     c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
			 System.out.println("Opened database successfully");
			 stmt = c.createStatement();
		      
		     sql = "CREATE TABLE IF NOT EXISTS IOTCAPTEURS " +
		                   "(ID_IOT TEXT     		NOT NULL," +
		                   " ID_CAP TEXT     		NOT NULL," +
		                   " M_TIMESTAMP DATETIME DEFAULT CURRENT_TIMESTAMP    NOT NULL," +
		                   " M_VAL  DECIMAL         NOT NULL );" ;
		      
		      stmt.executeUpdate(sql);
		      stmt.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		  }

	public void addValue(String id_iot, String id_cap, long m_timestamp, Float m_val) throws IOException  {

		Connection c = null;
	    Statement stmt = null;
	    Date d = null;
	    
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
	      c.setAutoCommit(false);


	      // =>"YYYY-MM-DD HH:MM:SS"
	      d = new Date(m_timestamp);
	      
	      // inserting data
	      PreparedStatement prep = c.prepareStatement("insert into IOTCAPTEURS values(?,?,?,?);");
	      prep.setString(1, id_iot); 
	      prep.setString(2, id_cap);
	      prep.setDate(3, d);
	      prep.setFloat(4, m_val);
	      
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

	public void getAllValues () throws IOException {
	
		  Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
		      c.setAutoCommit(false);

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM IOTCAPTEURS;" );
		      
		      while ( rs.next() ) {
		         String id_iot = rs.getString("ID_IOT");
		         String  id_cap = rs.getString("ID_CAP");
		         Date date  = rs.getDate("M_TIMESTAMP");
		         float val = rs.getFloat("M_VAL");
		         System.out.println( "ID_IOT = " + id_iot );
		         System.out.println( "ID_CAP = " + id_cap );
		         System.out.println( "M_VAL = " + val );
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
	

	public void flushValues() throws IOException {

	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      String sql = "DELETE * FROM IOTCAPTEURS;";
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }

	public boolean findValueToSynchro(Mesure mesureFromBD)  throws IOException {
		// TODO Auto-generated method stub
		
		
		return true;
		
	}

	public void updateValue(Mesure mesureFromDB) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}


