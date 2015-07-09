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

		int valPinmeteo = capteurService.getCapteurInfo().getCdec().getPinmeteo();
		
		if (valPinmeteo !=0 ) {
			afficheurLedSimple = AfficheurLedSimple.getInstance();
		}		

	}

	@Override
	public void run() {

		
		String s1Capteur = null;
		int resulToInt = 1;
		
		try {
			while (true) {
				
				Mesure m = capteurService.getMesure();

				
				// Envoi de la mesure en websocket

				boolean result =	wsc.sendMesure(m);
				
				if (result)  {
						resulToInt = 1 ;
				} 	else resulToInt = 0  ;
					
				// si la persistance est requise pour un IOT, alors pour tous les capteurs, on ajoute la mesure en base
				
				if (m.getCapteur().getCdec().getPersistance() == 1) {

					try {
						UseSQLiteDB connexion = new UseSQLiteDB("iotqvt.db");
						System.out.println("ADD VALUE");
						connexion.addValue(m.getCapteur().getIot(),m.getCapteur().getId(),m.getDate(),m.getValeur(), resulToInt );;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// si le cdec doit afficher un état, et que le capteurs influe sur l'état alors on influe sur l'état de l'IOT
	
				System.out.println("-------------------------------------------------------");
				
				int pinMeteoCDEC = m.getCapteur().getCdec().getPinmeteo();
				int pinMeteoCapteur = m.getCapteur().getPinmeteocapteur();
				
				System.out.println("METEO pour ce CDEC et CAPTEUR" + pinMeteoCDEC + "/" + pinMeteoCapteur);
				
				
				if ((pinMeteoCDEC != 0) & (pinMeteoCapteur != 0)) {
					 System.out.println("METEO pour ce CDEC");					
					// pour le capteur de température, changer état IOT
					s1Capteur = capteurService.getCapteurInfo().getTypeCapteur().getLibelle();
					if (s1Capteur.equals(TYPE_CAPTEUR_TEMPERATURE)) {
						System.out.println("METEO piloté par la température");
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
