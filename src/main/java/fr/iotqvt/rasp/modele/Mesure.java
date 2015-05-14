package fr.iotqvt.rasp.modele;

public class Mesure {
	
	private int temp;
	private long date;

	
	public Mesure(int temp, long date) {
		super();
		this.temp = temp;
		this.date = date;
	}
	public Mesure() {
		
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}

}
