package fr.iotqvt.rasp;

import java.io.IOException;

import fr.iotqvt.rasp.infra.afficheur.AfficheurLedSimple;
import fr.iotqvt.rasp.infra.capteur.CapteurService;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.persistence.UseSQLiteDB;

public class CapteurTask implements Runnable {
	
	private final static String TYPE_CAPTEUR_TEMPERATURE = "temperature";
	
	private CapteurService capteurService;
	private WebsocketClient wsc;
	private static final String newLine = System.getProperty("line.separator");
	
	private AfficheurLedSimple afficheurLedSimple ;

	public CapteurTask(CapteurService capteurService, WebsocketClient wsc) {
		super();
		this.wsc = wsc;
		this.capteurService = capteurService;
		afficheurLedSimple = AfficheurLedSimple.getInstance();
	}

	@Override
	public void run() {
		
		String s1Capteur = null;
		
		try {
			while (true) {
				
				Mesure m = capteurService.getMesure();
				System.out.println("mesure :" + m);
				System.out.println(newLine);
				// Envoi de la mesure en websocket
				wsc.sendMesure(m);
				
				
				// pour tous les capteurs ajout de la mesure en base
				try {
					UseSQLiteDB connexion = new UseSQLiteDB("iotqvt.db");
					connexion.addValue(m.getCapteur().getIot(),m.getCapteur().getId(),m.getDate(),m.getValeur());;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// si il s'agit d'un capteur ayant une indication d'état
				if ( m.getCapteur().getPinmeteo() != 0) {
					 System.out.println("METEO");					
					// pour le capteur de température, changer état IOT
					s1Capteur = capteurService.getCapteurInfo().getTypeCapteur().getLibelle();
					if (s1Capteur.equals(TYPE_CAPTEUR_TEMPERATURE)) {
						System.out.println("METEO TEMP");
						this.setEtatIOT(m.getValeur(),capteurService.getCapteurInfo().getRefMin(),capteurService.getCapteurInfo().getRefMax());
					}
				}
				
				Thread.sleep(capteurService.getCapteurInfo().getFrequenceMesures());
			}
		} catch (InterruptedException e) {
			
			// pourquoi c'est vide ici ?
		}
	}

	// pour l'instant utilisable pour tout les capteurs
	public void setEtatIOT(float valMesure, float valRefMin, float valRefMax) {
		if (valMesure < valRefMin) {
			System.out.println("INF");			
			this.afficheurLedSimple.clignote(1, 2);
				} else if ((valMesure >= valRefMin) && ( valMesure <= valRefMax )) {
					System.out.println("OK");	
					this.afficheurLedSimple.off();
						} else if (valMesure > valRefMax) {
							System.out.println("SUP");								
							this.afficheurLedSimple.on();
		    }
	}

}
