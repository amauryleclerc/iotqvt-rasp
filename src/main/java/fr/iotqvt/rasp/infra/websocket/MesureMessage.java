package fr.iotqvt.rasp.infra.websocket;

import com.google.gson.Gson;

import fr.iotqvt.rasp.modele.Mesure;

public class MesureMessage implements WsMessage {

	private String type ;
	private Mesure mesure ;
	
	public MesureMessage(Mesure m) {
		this.type = "mesure" ;
		this.mesure = m ;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public String getType() {
		return type;
	}

	public Mesure getMesure() {
		return mesure;
	}
}
