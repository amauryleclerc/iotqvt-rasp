package fr.iotqvt.rasp.modele;

public class Capteur {
	private String id;
	private transient  IOT cdec;
	private TypeCapteur typeCapteur;
	private Integer frequenceMesures;
	private String modele;
	private Float refMin;
	private Float refMax;
	private String iot;
	private int pinmeteocapteur =0;

	public int getPinmeteocapteur() {
		return pinmeteocapteur;
	}

	public void setPinmeteocapteur(int pinmeteocapteur) {
		this.pinmeteocapteur = pinmeteocapteur;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TypeCapteur getTypeCapteur() {
		return typeCapteur;
	}

	public void setTypeCapteur(TypeCapteur typeCapteur) {
		this.typeCapteur = typeCapteur;
	}

	public Integer getFrequenceMesures() {
		return frequenceMesures;
	}

	public void setFrequenceMesures(Integer frequenceMesures) {
		this.frequenceMesures = frequenceMesures;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public Float getRefMin() {
		return refMin;
	}

	public void setRefMin(Float refMin) {
		this.refMin = refMin;
	}

	public Float getRefMax() {
		return refMax;
	}

	public void setRefMax(Float refMax) {
		this.refMax = refMax;
	}

	public String getIot() {
		return iot;
	}

	public void setIot(String iot) {
		this.iot = iot;
	}

	public IOT getCdec() {
		return cdec;
	}

	public void setCdec(IOT cdec) {
		this.cdec = cdec;
	}

}
