package fr.iotqvt.rasp.infra.capteur;

import fr.iotqvt.rasp.modele.Capteur;

public class CapteurFactory {
	private final static String TYPE_CAPTEUR_TEMPERATURE = "temperature";
	private final static String TYPE_CAPTEUR_TEST = "test";
	private final static String TYPE_CAPTEUR_BRUIT = "bruit";
	private final static String TYPE_CAPTEUR_HUMIDITE = "humidite";
	private final static String TYPE_CAPTEUR_LUMINOSITE = "luminosite";
	
	public static CapteurService getCapteur(Capteur capteur) {

		CapteurService service = null;
		String typeCapteur = capteur.getTypeCapteur().getLibelle();
		if (typeCapteur.equals(TYPE_CAPTEUR_TEMPERATURE)) {
			service = CapteurTemperature.getInstance();
		} else if (typeCapteur.equals(TYPE_CAPTEUR_TEST)) {
			service = CapteurTest.getInstance();
			} else if (typeCapteur.equals(TYPE_CAPTEUR_BRUIT)) {
				service = CapteurBruit.getInstance();
					} else if (typeCapteur.equals(TYPE_CAPTEUR_HUMIDITE)) {
							service = CapteurHumidite.getInstance();
								} else if (typeCapteur.equals(TYPE_CAPTEUR_LUMINOSITE)) {
										service = CapteurLuminosite.getInstance();
		}
		if (service != null) {
			service.setCapteurInfo(capteur);
		}
		return service;

	}
}
