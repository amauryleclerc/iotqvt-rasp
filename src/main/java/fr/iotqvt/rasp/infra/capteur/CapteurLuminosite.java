package fr.iotqvt.rasp.infra.capteur;

import java.util.Date;

import fr.iotqvt.rasp.infra.capteur.CapteurBruit.MCP3008_input_channels;
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

	
	private static int ADC_CHANNEL_LUMI = MCP3008_input_channels.CH1.ch();
	
	public enum MCP3008_input_channels {
		CH0(0), CH1(1), CH2(2), CH3(3), CH4(4), CH5(5), CH6(6), CH7(7);

		private int ch;

		MCP3008_input_channels(int chNum) {
			this.ch = chNum;
		}

		public int ch() {
			return this.ch;
		}
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
		
		m.setValeur((float)(CapteurBruit.readMCP3008(ADC_CHANNEL_LUMI)));
		m.setDate(new Date().getTime());
		m.setCapteur(this.getCapteurInfo());
		
		System.out.println("LUMI ++++++++++++++++++ :" + m.getValeur() );		
		
		return m;
	}
	
	
	

}
