package fr.iotqvt.rasp;

import java.net.URI;
import java.util.Date;

import fr.iotqvt.rasp.infra.CapteurTemperature;
import fr.iotqvt.rasp.infra.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.modele.MesureMessage;

public class App implements Runnable {
	
	private int frequence;
	private WebsocketClient wsc;
	private String id;
	private boolean connected= false;
	public App(int frequence, URI uri, String id) {
		super();
		this.id = id;
		this.frequence = frequence;
		this.wsc = new WebsocketClient(uri);
		connected = true;
	}

	@Override
	public void run() {
		try{

			while(true){
//				Mesure m =	CapteurTemperature.getInstance().getMesure();
				
				Mesure m = new Mesure();
				m.setTemp(20555);
				m.setDate(new Date().getTime());
				MesureMessage mm = new MesureMessage(m, id);
				System.out.println(mm);
				wsc.sendMesureMessage(new MesureMessage(m, id));
				Thread.sleep(frequence);
			}
			
		} catch (InterruptedException e) {
		}

	}
}
