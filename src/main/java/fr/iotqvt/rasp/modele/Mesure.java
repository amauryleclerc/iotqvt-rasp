package fr.iotqvt.rasp.modele;

import com.google.gson.Gson;

public class Mesure {
	
	private Float valeur;
	private long date;
	private Capteur capteur;
	

	public Mesure(Float valeur, long date, Capteur capteur) {
		super();
		this.valeur = valeur;
		this.date = date;
		this.capteur = capteur;
	}

	public Mesure() {
		
	}
	
	public Capteur getCapteur() {
		return capteur;
	}

	public void setCapteur(Capteur capteur) {
		this.capteur = capteur;
	}

	public Float getValeur() {
		return valeur;
	}
	public void setValeur(Float valeur) {
		this.valeur = valeur;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
