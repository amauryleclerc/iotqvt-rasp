package fr.iotqvt.rasp.modele;

import java.util.List;

public class IOT {
	private String id;
	private List<Capteur> capteurs;
	private String master;
	private int persistance;
	private int pinmeteo ;
	
	public String getId() {
		return id;
	}
	public int getPinmeteo() {
		return pinmeteo;
	}
	public void setPinmeteo(int pinmeteo) {
		this.pinmeteo = pinmeteo;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Capteur> getCapteurs() {
		return capteurs;
	}
	public void setCapteurs(List<Capteur> capteurs) {
		this.capteurs = capteurs;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public int getPersistance() {
		return persistance;
	}
	public void setPersistance(int persistance) {
		this.persistance = persistance;
	}
	
}
