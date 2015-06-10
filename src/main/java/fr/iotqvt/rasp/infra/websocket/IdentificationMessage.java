package fr.iotqvt.rasp.infra.websocket;

import com.google.gson.Gson;

import fr.iotqvt.rasp.modele.IOT;
import fr.iotqvt.rasp.modele.Mesure;

public class IdentificationMessage implements WsMessage {

	private String type ;
	private IOT cedec ;
	
	public IdentificationMessage(IOT cedec) {
		this.type = "identification" ;
		this.cedec = cedec ;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public String getType() {
		return type;
	}

	public IOT getCedec() {
		return this.cedec ;
	}
}
