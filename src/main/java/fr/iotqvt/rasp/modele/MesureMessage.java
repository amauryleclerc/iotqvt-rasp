package fr.iotqvt.rasp.modele;


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

}
