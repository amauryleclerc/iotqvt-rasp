package fr.iotqvt.rasp.modele;

import java.util.List;

public class IOT {
	private String id;
	private List<Capteur> capteurs;
	private Piece piece;
	public String getId() {
		return id;
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
	public Piece getPiece() {
		return piece;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	
}
