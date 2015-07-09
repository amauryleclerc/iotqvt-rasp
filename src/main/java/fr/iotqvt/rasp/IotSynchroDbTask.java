package fr.iotqvt.rasp;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import fr.iotqvt.rasp.infra.websocket.WebsocketClient;
import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.persistence.UseSQLiteDB;

public class IotSynchroDbTask implements Runnable {
	
	private static Logger  LOG = LogManager.getLogger(IotSynchroDbTask.class.getName());
	
	
	// Websocket pour la synchro des données avec le master
	
	private WebsocketClient wsc;
	private UseSQLiteDB connexion;
	private Mesure mesureFromDB;

	
	public IotSynchroDbTask(WebsocketClient wsc) {
		
		// task spécialisée pour la synchronisation des valeurs de l'IOT sauvegardées en local suite à une déconnexion
		// au début du lancement de l'application local, on lance une tache qui va scruter la base pour rechercher 
		// des mesures non sauvegardées sur le master
		// Deux usages : message json pour chaque mesure envoyé en ws / webservice, message json contenant l'ensemble des mesures
		
		 super();
		 this.wsc = wsc;
		
		 mesureFromDB = new Mesure();
		 
	 try {
			connexion = new UseSQLiteDB("iotqvt.db");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run()  {
		while (true) {
			try {

//				LOG.debug("Recherche d'une mesure a synchroniser");
				LOG.info("Recherche d'une mesure a synchroniser");
				mesureFromDB = connexion.findValueToSynchro(); 
				// Si l'objet n'est pas null, c'est a dire qu'il existe bien au moins une valeur capturé en mode déconnecté alors on l'envoi au master
				if ( mesureFromDB != null ) {
					// reconstitue une mesure à partir des valeurs en base
					LOG.info("Cette valeure doit etre synchronisée" + mesureFromDB.getCapteur().getModele() + "-" + mesureFromDB.getDate());
				    System.out.println("Valeur :" + mesureFromDB.getValeur());
					// si la synchro de mesure est ok, alors on met a jour l'indicateur et on recommence avec la mesure suivante  
					boolean res = wsc.sendMesure(mesureFromDB); 
					if (res ) {
						// la synchro a fonctionnée => mise a jour de la mesure en base en conséquence
						connexion.updateValue(mesureFromDB);
					}
				};
			
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
		}
		
		
	}
	
}
