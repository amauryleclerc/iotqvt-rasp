package fr.iotqvt.rasp.infra.capteur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.infra.afficheur.*;

public class CapteurTemperature extends CapteurService{

	private Path filePath;
	private static CapteurTemperature instance;
	private AfficheurLedSimple afficheurLedSimple ;
	
	private CapteurTemperature() {
		super();
		this.runW1();
		this.filePath = getDeviceFile();
//		AfficheurLedSimple afficheurLedSimple = new AfficheurLedSimple();
		afficheurLedSimple = AfficheurLedSimple.getInstance();
	}
	public static CapteurTemperature getInstance() {
		if (instance == null) {
			instance = new CapteurTemperature();
		}
		return instance;
	}

	private void runW1() {
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(new String[] { "modprobe", "w1-gpio" });
			runtime.exec(new String[] { "modprobe", "w1-therm" });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Path getDeviceFile() {
		Path resultat = null;
		String baseDir = "/sys/bus/w1/devices/";
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			@Override
			public boolean accept(Path file) throws IOException {
				return ((Files.isDirectory(file)) && file.getFileName()
						.toString().startsWith("28"));
			}
		};
		Path dir = FileSystems.getDefault().getPath(baseDir);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
				filter)) {
			for (Path path : stream) {
				resultat = path.resolve("w1_slave");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultat;
	}

	private String readFile(Path path) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(path.toFile()));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				sb.append(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();

	}

	public Integer getTemperature() {
		Integer resultat = -1;
		String content = readFile(this.filePath);
		while (!content.contains("YES")) {
			continue;
		}
		int index = content.indexOf("t=");
		if (index != -1) {
			String stTemp = content.substring(index + 2);
			resultat = Integer.valueOf(stTemp) ;
		}
		return resultat;
	
	}

	@Override
	public Mesure getMesure() {
		Mesure resultat = new Mesure();
		resultat.setValeur((float) getTemperature() / 1000);
		// resultat.setValeur( (float)20.55);
		resultat.setDate(new Date().getTime());
		resultat.setCapteur(this.getCapteurInfo());
 
		System.out.println("TEMPERATURE °°°°°°°°°°°°°° :" + resultat.getValeur() );		
		
		this.setEtatIOT(resultat.getValeur(),this.getCapteurInfo().getRefMin(),this.getCapteurInfo().getRefMax());
		
		return resultat;
	}

	// Change l'état de l'IOT en comparaison de seuils du capteur correspondant à la classe
	// plus tard, il faudra remonter au niveau de l'IOT ce changement d'état
	
	public void setEtatIOT(float valMesure, float valRefMin, float valRefMax) {
		if (valMesure < valRefMin) {
			this.afficheurLedSimple.clignote(1, 5);
			this.afficheurLedSimple.off();
				} else if ((valMesure >= valRefMin) && ( valMesure <= valRefMax )) {
					this.afficheurLedSimple.off();
						} else if (valMesure > valRefMax) {
							this.afficheurLedSimple.on();
		    }
	}
}
