package fr.iotqvt.rasp;

import java.net.URI;

import fr.iotqvt.rasp.infra.CapteurTemperature;
import fr.iotqvt.rasp.infra.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.modele.MesureMessage;

public class App implements Runnable {
	
	private int frequence;
	private WebsocketClient wsc;
	private String id;
	public App(int frequence, URI uri, String id) {
		super();
		
		this.frequence = frequence;
		this.wsc = new WebsocketClient(uri);
		
	}

	@Override
	public void run() {
		try{

			while(true){
				Mesure m =	CapteurTemperature.getInstance().getMesure();
				wsc.sendMesureMessage(new MesureMessage(m, id));
				Thread.sleep(frequence);
			}
			
		} catch (InterruptedException e) {
		}

	}
}
