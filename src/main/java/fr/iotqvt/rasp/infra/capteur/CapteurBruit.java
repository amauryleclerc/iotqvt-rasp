package fr.iotqvt.rasp.infra.capteur;

//http://www.lediouris.net/RaspberryPI/ADC/readme.html
//https://01509530127781966272.googlegroups.com/attach/a0451b42792b386/GPIO%20output.jpg?part=0.1&view=1&vt=ANaJVrFHhuxhOMV3XZYV9QBBEcyP_pOodXXRQR2RtPUhVUX_IF8LUsx0RDjEJVAyliGHZ2pTRScvZ7VEAf-NOIkKI_asPxon29luChuSdM2HbQOVlfMnZv0

import java.util.Date;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import fr.iotqvt.rasp.modele.Mesure;

public class CapteurBruit extends CapteurService {


	private static CapteurBruit instance;
	
	private CapteurBruit() {
		super();
	    this.initMCP3008();
	}

	public void finalize()
    {
	    System.out.println("Bye, freeing resources.");
	    this.shutdownMCP3008();  
    }
	
	private static Pin spiClk = RaspiPin.GPIO_00; // SPICLK : CLK
	// CLK = GPIO17 = GPIO_GEN0
	private static Pin spiMiso = RaspiPin.GPIO_04; // MISO : DIN
	// Din = GPIO23 = GPIO_GEN4
	private static Pin spiMosi = RaspiPin.GPIO_05; // MOSI : DOUT
	// Dout = GPIO24 = GPIO_GEN5
	private static Pin spiCs = RaspiPin.GPIO_06; // CS : CS
	// Cs = Chip Select = GPIO25 = GPIO_GEN6

	private static int ADC_CHANNEL = MCP3008_input_channels.CH0.ch();
	
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

	private static GpioController gpio;
	private static GpioPinDigitalInput misoInput = null;
	private static GpioPinDigitalOutput mosiOutput = null;
	private static GpioPinDigitalOutput clockOutput = null;
	private static GpioPinDigitalOutput chipSelectOutput = null;

	public static void initMCP3008() {
		gpio = GpioFactory.getInstance();

		mosiOutput = gpio.provisionDigitalOutputPin(spiMosi, "MOSI",PinState.LOW); 
		misoInput = gpio.provisionDigitalInputPin(spiMiso, "MISO");
		clockOutput = gpio.provisionDigitalOutputPin(spiClk, "CLK",	PinState.LOW); 
		chipSelectOutput = gpio.provisionDigitalOutputPin(spiCs, "CS",PinState.LOW); 
	}

	public static void shutdownMCP3008() {
		gpio.shutdown();
	}	
	
	
	@Override
	public Mesure getMesure() {
		
		Mesure m = new Mesure();
		
		m.setValeur((float)(readMCP3008(ADC_CHANNEL)));
		m.setDate(new Date().getTime());
		
		return m;
	}
	
	
	public static int readMCP3008(int channel)
	  {
		  
	    chipSelectOutput.high(); 	
	    
	    clockOutput.low();		
	    chipSelectOutput.low();
	  
	    int adccommand = channel;
	    
	    adccommand |= 0x18; // 0x18: 00011000
	    adccommand <<= 3;
	    for (int i=0; i<5; i++) 
	    {
	      if ((adccommand & 0x80) != 0x0) // 0x80 = 0&10000000
	        mosiOutput.high(); 
	      else
	        mosiOutput.low(); 
	      adccommand <<= 1;      
	      clockOutput.high(); 
	      clockOutput.low();  
	    }
	    int adcOut = 0;
	    for (int i=0; i<12; i++) // Read in one empty bit, one null bit and 10 ADC bits
	    {
		  clockOutput.high(); 
		  clockOutput.low();  
	      adcOut <<= 1;
	      if (misoInput.isHigh()) 
	      {
	        // Shift one bit on the adcOut
	        adcOut |= 0x1;
	      }
	    }
	    chipSelectOutput.high(); 
	    adcOut >>= 1; // Drop first bit
	    return adcOut;
	  }
	
	
	public static CapteurService  getInstance(){
		if(instance==null){
			instance = new CapteurBruit();
		}
		return instance;
	}

}
