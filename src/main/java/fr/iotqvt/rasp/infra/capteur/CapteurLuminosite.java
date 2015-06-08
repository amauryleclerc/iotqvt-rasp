package fr.iotqvt.rasp.infra.capteur;

import java.util.Date;

import fr.iotqvt.rasp.modele.Mesure;

public class CapteurLuminosite extends CapteurService {

	private static CapteurLuminosite instance;
	
	private CapteurLuminosite() {
		super();
	}
	
	public void finalize()
    {
	    System.out.println("Bye, freeing resources.");
    }

	public static CapteurService  getInstance(){
		if(instance==null){
			instance = new CapteurLuminosite();
		}
		return instance;
	}

	
	@Override
	public Mesure getMesure() {
		Mesure m = new Mesure();
		m.setValeur((float)(Math.random()*10+15));
		m.setDate(new Date().getTime());
		m.setCapteur(this.getCapteurInfo());
		return m;
	}

}
