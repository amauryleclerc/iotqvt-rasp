package fr.iotqvt.rasp;

import java.net.URI;

import fr.iotqvt.rasp.infra.capteur.CapteurFactory;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.modele.MesureMessage;

public class App implements Runnable {
	
	private int frequence;
	private WebsocketClient wsc;
	private String id;
	public App(int frequence, URI uri, String id) {
		super();
		this.id = id;
		this.frequence = frequence;
		this.wsc = new WebsocketClient(uri);
	}

	@Override
	public void run() {
		try{

			while(true){
				Mesure m =	CapteurFactory.getCapteur("test").getMesure();

				MesureMessage mm = new MesureMessage(m, id);
				System.out.println(mm);
				wsc.sendMesureMessage(new MesureMessage(m, id));
				Thread.sleep(frequence);
			}
			
		} catch (InterruptedException e) {
		}

	}
}
