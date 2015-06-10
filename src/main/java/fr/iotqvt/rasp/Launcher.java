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

public class Launcher {

	public static void main(String[] args) {
		
		IOT config = null;
		try {
			 System.out.println("1");			
			 config = loadConfig(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WebsocketClient wsc = null;
		try {
			System.out.println("2");
			wsc = new WebsocketClient(new URI(config.getMaster()), config);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		for(Capteur capteur : config.getCapteurs()){
			System.out.println(capteur.getRefMax());
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
