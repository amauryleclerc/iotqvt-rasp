package fr.iotqvt.rasp.infra.capteur;

import java.util.Date;

import fr.iotqvt.rasp.modele.Mesure;

public class CapteurTest extends CapteurService{



	private static CapteurTest instance;
	
	private CapteurTest() {
		super();
	}

	@Override
	public Mesure getMesure() {
		Mesure m = new Mesure();
		m.setValeur((float)(Math.random()*10+15));
		m.setDate(new Date().getTime());
		return m;
	}
	public static CapteurService  getInstance(){
		if(instance==null){
			instance = new CapteurTest();
		}
		return instance;
	}




}
