package fr.iotqvt.rasp;

import java.io.IOException;

import fr.iotqvt.rasp.infra.capteur.CapteurService;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.persistence.UseSQLiteDB;

public class CapteurTask implements Runnable {

	private CapteurService capteurService;
	private WebsocketClient wsc;
	private static final String newLine = System.getProperty("line.separator");

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
				System.out.println(newLine);
				
				// ajout de la mesure en base
				
				try {
					UseSQLiteDB connexion = new UseSQLiteDB("iotqvt.db");
					connexion.addValue(m.getCapteur().getIot(),m.getCapteur().getId(),m.getDate(),m.getValeur());;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				wsc.sendMesure(m);
				Thread.sleep(capteurService.getCapteurInfo().getFrequenceMesures());
			}

		} catch (InterruptedException e) {
		}

	}

}
