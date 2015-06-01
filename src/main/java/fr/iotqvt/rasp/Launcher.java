package fr.iotqvt.rasp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			 config = loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		WebsocketClient wsc = null;
		try {
			wsc = new WebsocketClient(new URI(config.getMaster()));
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

	private static  IOT loadConfig() throws IOException{


		InputStream input =   Launcher.class.getClassLoader().getResourceAsStream("config.json");
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null){
		    responseStrBuilder.append(inputStr);
		}

		Gson gson = new Gson();
		return gson.fromJson(responseStrBuilder.toString(), IOT.class);
	}
		
}
