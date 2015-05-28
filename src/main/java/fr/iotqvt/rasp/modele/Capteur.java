package fr.iotqvt.rasp.modele;

import java.util.List;

public class Capteur {
	private String id;
	private List<Mesure> mesures;
	private TypeCapteur type;
	private Long frequenceMesure;

	//Modele
	//REF MIN MAX
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Mesure> getMesures() {
		return mesures;
	}
	public void setMesures(List<Mesure> mesures) {
		this.mesures = mesures;
	}
	public TypeCapteur getType() {
		return type;
	}
	public void setType(TypeCapteur type) {
		this.type = type;
	}
	public Long getFrequenceMesure() {
		return frequenceMesure;
	}
	public void setFrequenceMesure(Long frequenceMesure) {
		this.frequenceMesure = frequenceMesure;
	}
	
}
