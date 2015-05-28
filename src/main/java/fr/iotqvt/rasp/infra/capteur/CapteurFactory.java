package fr.iotqvt.rasp.infra.capteur;

public class CapteurFactory {
	private final static String TYPE_CAPTEUR_TEMPERATURE = "temperature";
	private final static String TYPE_CAPTEUR_TEST = "test";


	public static Capteur getCapteur(String typeCapteur) {

		if (typeCapteur.equals(TYPE_CAPTEUR_TEMPERATURE)) {
			return CapteurTemperature.getInstance();
		} else if (typeCapteur.equals(TYPE_CAPTEUR_TEST)) {
		
			return CapteurTest.getInstance();

		}
		return null;

	}
}
