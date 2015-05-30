package fr.iotqvt.rasp.infra.capteur;

import fr.iotqvt.rasp.modele.Capteur;
import fr.iotqvt.rasp.modele.Mesure;

public abstract class CapteurService extends Capteur {

	public abstract Mesure getMesure();
	public void setCapteurInfo(Capteur capteur){
	
		this.setFrequenceMesures(capteur.getFrequenceMesures());
		this.setId(capteur.getId());
		this.setTypeCapteur(capteur.getTypeCapteur());
		this.setModele(capteur.getModele());
		this.setRefMax(capteur.getRefMax());
		this.setRefMin(capteur.getRefMin());
	}
}
