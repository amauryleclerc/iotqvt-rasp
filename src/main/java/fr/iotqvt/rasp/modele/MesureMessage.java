package fr.iotqvt.rasp.modele;

import com.google.gson.Gson;


public class MesureMessage extends Mesure {
	private String id;
	
	
	public MesureMessage(Mesure m , String id) {
		super(m.getTemp(), m.getDate());
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
