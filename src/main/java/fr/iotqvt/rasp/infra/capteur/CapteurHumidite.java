package fr.iotqvt.rasp.infra.capteur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import fr.iotqvt.rasp.modele.Mesure;

public class CapteurHumidite extends CapteurService {

	private static final String LIB_NOT_PRESENT_MESSAGE = "Librairie Python non présent";
	private static final CharSequence ERROR_READING = "Failed to get reading. Try again!";
	private static final String ERROR_READING_MSG = "Erreur en lecture du capteur DHT11";
	
	private static CapteurHumidite instance;
	
	private Date dateDHT ;
	private float Temperature = 0;
	private float Humidite = 0;
	

	private CapteurHumidite() {
		super();
	}

	public void finalize()
    {
	    System.out.println("Bye, freeing resources.");
    }
	
	public static CapteurService  getInstance(){
		if(instance==null){
			instance = new CapteurHumidite();
		}
		return instance;
	}
	
	@Override
	public Mesure getMesure() {
		Mesure m = new Mesure();
		
		this.getMesureDHT11();
		
		m.setValeur(getHumidite());
		
		// Voir pourquoi le get sur la date ne renvoit pas le bon format)
		
		m.setDate(new Date().getTime());
		m.setCapteur(this.getCapteurInfo());
		
		System.out.println("HUMIDITE %%%%%%%%%%%%%% :" + m.getValeur() );		
		
		return m;
	}
	
	
	private void getMesureDHT11() {
	    String cmd = "sudo python ../DHT11/Adafruit_Python_DHT/examples/AdafruitDHT.py 11 27";
	    try {
	        String ret = "";
	        try {
	            String line ="";
	            String output ="";
	            
	            Process p = Runtime.getRuntime().exec(cmd);
	            p.waitFor();
	            
	            BufferedReader input = new BufferedReader
	                    (new InputStreamReader(p.getInputStream()));
	            while ((line = input.readLine()) != null) {
	                output = (line + '\n');
	                ret = output;
	            }
	            input.close();
	        }
	        catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        
	        ret.trim();
	        if (ret.length() == 0) // Library is not present
	            throw new RuntimeException(LIB_NOT_PRESENT_MESSAGE);
	        else{
	            // Error reading the the sensor, maybe is not connected. 
	            if(ret.contains(ERROR_READING)){
	                String msg = String.format(ERROR_READING_MSG,toString());
	                System.out.println("ERROR READING MSG :" + msg);	
	                throw new Exception(msg);
	            }
	            else{
	                // Read completed. Parse and update the values

	            	 // 24.0/33.0
	            	 
	                String[] vals = ret.split("/");
	                
	                float t = Float.parseFloat(vals[0]);
	                float h = Float.parseFloat(vals[1]);
	                
	                float lastTemp = 0;
					float lastHum = 0;
					if( (t != lastTemp) || (h != lastHum) ){
	                    Date lastUpdate = new Date();
	                    lastTemp = t;
	                    lastHum = h;
	                    
	                    this.setDateDHT(lastUpdate);
	                    this.setHumidite(lastHum);
	                    this.setTemperature(lastTemp);
	                }
	            }
	        }
	    } catch (Exception e) {
	    	System.out.println("Exception");
	        System.out.println(e.getMessage());
            // if( e instanceof RuntimeException)
	        // System.exit(-1);
	    }
	}
		
	
	public Date getDateDHT() {
		return dateDHT;
	}

	public void setDateDHT(Date dateDHT) {
		this.dateDHT = dateDHT;
	}
	
	
	public void setTemperature(float temperature) {
		Temperature = temperature;
	}

	public void setHumidite(float humidite) {
		Humidite = humidite;
	}

	public float getTemperature() {
		return Temperature;
	}

	public float getHumidite() {
		return Humidite;
	}
	
	
}
