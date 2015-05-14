package fr.iotqvt.rasp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class Launcher {

	public static void main(String[] args) {
		Properties properties = null;
		Integer frequence = 10000;
		URI uri = null;
		String id = null;
		 try {
			 properties = loadProperties();
			 frequence = Integer.valueOf(properties.getProperty("frequence"));
			 uri = new URI(properties.getProperty("urlMaster"));
			 id = properties.getProperty("identifiant");
			 } catch (IOException | URISyntaxException ex) {
			System.err.println("IOException exception: " + ex.getMessage());
		}
		App app = new App(frequence, uri ,id);
		Thread t = new Thread(app);
		t.start();

	}
	private static Properties loadProperties() throws IOException{

		Properties applicationProps = new Properties();
		FileInputStream	in = new FileInputStream("config");
		applicationProps.load(in);
		in.close();
		return null;
		
	}
}
