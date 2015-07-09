package fr.iotqvt.rasp;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Delayed;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import fr.iotqvt.rasp.infra.capteur.CapteurFactory;
import fr.iotqvt.rasp.infra.capteur.CapteurService;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Capteur;
import fr.iotqvt.rasp.modele.IOT;
import fr.iotqvt.rasp.persistence.UseSQLiteDB;


public class Launcher {

	private static Logger LOG = LogManager.getLogger(Launcher.class);
	
	public static void main(String[] args) {
	
		LOG.info("Démarrage du CEDEC...");
		IOT config = null;

		
		try {
			 config = loadConfig(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// init SQLite DB en fonction de l'iot persistance du fichier config.json
		// Création de la base si nécessaire
		
		if (config.getPersistance() == 1) {
			try {
			
				LOG.info("Persistance interne activée.");
				UseSQLiteDB connexion = new UseSQLiteDB("iotqvt.db");
				connexion.createDB();
			    try{
			        Thread.sleep(1000);
			        }catch(InterruptedException e){}
				} catch (IOException e) {
				e.printStackTrace();
				}
		} else {
			LOG.info("Aucune persistance interne.");			
		}
		
		if (config.getPinmeteo()>0) {
			LOG.info("Led d'état déclarée sur la pin "+config.getPinmeteo());
		} else {
			LOG.info("Aucune led d'état déclarée.");			
		}		
		
		WebsocketClient wsc = null;
		try {
			wsc = new WebsocketClient(new URI(config.getMaster()), config);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		for(Capteur capteur : config.getCapteurs()){
			capteur.setIot(config.getId());
			capteur.setCdec(config);
			CapteurService service = CapteurFactory.getCapteur(capteur);
			CapteurTask task = new CapteurTask(service, wsc);
			LOG.info("Lancement du capteur "+capteur.getId()+" | "+capteur.getTypeCapteur().getLibelle()+"("+capteur.getTypeCapteur().getUnite()+")");
			new Thread(task).start();
		}

		// Lancement de la tache de synchro des values si déco

		if (config.getPersistance() == 1) {
			LOG.info("Lancement de la tache de synchro.");
			IotSynchroDbTask taskSyncrhoDB = new IotSynchroDbTask(wsc);
			new Thread(taskSyncrhoDB).start();
		}
		
	}

	private static  IOT loadConfig(String pathstr) throws IOException{
		FileReader reader = new FileReader(pathstr);
		Gson gson = new Gson();
		return gson.fromJson(reader, IOT.class);
	}
		
}
