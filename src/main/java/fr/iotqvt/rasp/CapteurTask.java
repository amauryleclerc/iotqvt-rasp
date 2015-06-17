package fr.iotqvt.rasp;

import java.io.IOException;

import fr.iotqvt.rasp.infra.afficheur.AfficheurLedSimple;
import fr.iotqvt.rasp.infra.capteur.CapteurService;
import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.persistence.UseSQLiteDB;

public class CapteurTask implements Runnable {

	private CapteurService capteurService;
	private WebsocketClient wsc;
	private static final String newLine = System.getProperty("line.separator");
	
//	private AfficheurLedSimple afficheurLedSimple ;

	public CapteurTask(CapteurService capteurService, WebsocketClient wsc) {
		super();
		this.wsc = wsc;
		this.capteurService = capteurService;
//		afficheurLedSimple = AfficheurLedSimple.getInstance();
	}

	@Override
	public void run() {
	
		
		try {
			while (true) {
				
				Mesure m = capteurService.getMesure();
				System.out.println("mesure :" + m);
				System.out.println(newLine);
				// Envoi de la mesure en websocket
				wsc.sendMesure(m);
				// pour tous les capteurs ajout de la mesure en base
//				try {
//					UseSQLiteDB connexion = new UseSQLiteDB("iotqvt.db");
//					connexion.addValue(m.getCapteur().getIot(),m.getCapteur().getId(),m.getDate(),m.getValeur());;
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
//				if (capteurService.getCapteurInfo().getMeteo().equals(s3Meteo)) {
//					// pour le capteur de température, changer état IOT
//					s1Capteur = capteurService.getCapteurInfo().getTypeCapteur().getLibelle();
//					if (s1Capteur.equals(s2Mesure)) {
//						this.setEtatIOT(m.getValeur(),capteurService.getCapteurInfo().getRefMin(),capteurService.getCapteurInfo().getRefMax());
//					}
//				}
				
				Thread.sleep(capteurService.getCapteurInfo().getFrequenceMesures());
			}
		} catch (InterruptedException e) {
		}
	}
	
	// Change l'état de l'IOT en comparaison de seuils du capteur correspondant à la classe
	// plus tard, il faudra remonter au niveau de l'IOT ce changement d'état
//	
//	public void setEtatIOT(float valMesure, float valRefMin, float valRefMax) {
//		if (valMesure < valRefMin) {
//			System.out.println("INF");			
//			this.afficheurLedSimple.clignote(1, 2);
//			this.afficheurLedSimple.off();
//				} else if ((valMesure >= valRefMin) && ( valMesure <= valRefMax )) {
//					System.out.println("OK");	
//					this.afficheurLedSimple.off();
//					this.afficheurLedSimple.off();
//						} else if (valMesure > valRefMax) {
//							System.out.println("SUP");								
//							this.afficheurLedSimple.on();
//		    }
//	}

}
