package fr.iotqvt.rasp.infra.capteur;

public class CapteurFactory {
	private final static String TYPE_CAPTEUR_TEMPERATURE = "temperature";
	private final static String TYPE_CAPTEUR_TEST = "test";
	private static CapteurTemperature capteurTemperatureInstance;
	private static CapteurTest capteurTestInstance;

	public static Capteur getCapteur(String typeCapteur) {

		if (typeCapteur.equals(TYPE_CAPTEUR_TEMPERATURE)) {
			if (capteurTemperatureInstance == null) {
				capteurTemperatureInstance = new CapteurTemperature();
			}
			return capteurTemperatureInstance;
		} else if (typeCapteur.equals(TYPE_CAPTEUR_TEST)) {
			if (capteurTemperatureInstance == null) {
				capteurTestInstance = new CapteurTest();
			}
			return capteurTestInstance;

		}
		return null;

	}
}
