package fr.iotqvt.rasp.infra.capteur;

import fr.iotqvt.rasp.modele.Capteur;
import fr.iotqvt.rasp.modele.Mesure;

public abstract class CapteurService {
	private Capteur capteurInfo;
	public abstract Mesure getMesure();
	public Capteur getCapteurInfo() {
		return capteurInfo;
	}
	public void setCapteurInfo(Capteur capteurInfo) {
		this.capteurInfo = capteurInfo;
	}

}
