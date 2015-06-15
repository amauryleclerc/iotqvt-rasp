package fr.iotqvt.rasp;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.Gson;

import fr.iotqvt.rasp.infra.capteur.CapteurFactory;
import fr.iotqvt.rasp.infra.capteur.CapteurService;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Capteur;
import fr.iotqvt.rasp.modele.IOT;
import fr.iotqvt.rasp.persistence.UseSQLiteDB;


public class Launcher {

	

	public static void main(String[] args) {
		
		IOT config = null;
		
		String Meteo = args[1];
		String Persistance = args[2];
		
		// init SQLite DB
		// Création de la base si nécessaire
		
		// Comment fixer une variable qui permette par la suite dans Capteur Task d'accéder à son contenu ?
		// peut etre créer une classe args ayant des méthodes qui recherchent si la valeure figure dans au moins un argument
		
		try {
			
		   	UseSQLiteDB connexion = new UseSQLiteDB("iotqvt.db");
			connexion.createDB();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			 config = loadConfig(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WebsocketClient wsc = null;
		try {
			wsc = new WebsocketClient(new URI(config.getMaster()), config);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		for(Capteur capteur : config.getCapteurs()){
			capteur.setIot(config.getId());
			CapteurService service = CapteurFactory.getCapteur(capteur);
			CapteurTask task = new CapteurTask(service, wsc);
			new Thread(task).start();
		}

	}

	private static  IOT loadConfig(String pathstr) throws IOException{
		FileReader reader = new FileReader(pathstr);
		Gson gson = new Gson();
		return gson.fromJson(reader, IOT.class);
	}
		
}
