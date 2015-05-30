package fr.iotqvt.rasp;

import fr.iotqvt.rasp.infra.capteur.CapteurService;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;

public class CapteurTask implements Runnable {

	private CapteurService capteurService;
	private WebsocketClient wsc;

	public CapteurTask(CapteurService capteurService, WebsocketClient wsc) {
		super();
		this.wsc = wsc;
		this.capteurService = capteurService;
	}

	@Override
	public void run() {
		try {

			while (true) {
				Mesure m = capteurService.getMesure();
				System.out.println("mesure :" + m);
				wsc.sendMesure(m);
				Thread.sleep(capteurService.getFrequenceMesures());
			}

		} catch (InterruptedException e) {
		}

	}

}
