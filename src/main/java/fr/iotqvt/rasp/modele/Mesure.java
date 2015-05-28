package fr.iotqvt.rasp.modele;

public class Mesure {
	
	private Float valeur;
	private long date;

	
	public Mesure(Float temp, long date) {
		super();
		this.valeur = temp;
		this.date = date;
	}
	public Mesure() {
		
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

}
