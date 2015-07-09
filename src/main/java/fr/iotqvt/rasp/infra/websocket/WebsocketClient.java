package fr.iotqvt.rasp.infra.websocket;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import fr.iotqvt.rasp.modele.IOT;
import fr.iotqvt.rasp.modele.Mesure;

@ClientEndpoint
public class WebsocketClient {

	private static Logger LOG = LogManager.getLogger(WebsocketClient.class);

	private Session session = null;
	private URI endpointURI = null;
	private IOT cedec;

	public WebsocketClient(URI endpointURI, IOT cedec) {
		this.endpointURI = endpointURI;
		this.cedec = cedec;
	}

	private void connect() throws DeploymentException, IOException {
		LOG.info("Tentative de connexion au master "+endpointURI.toString());
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.connectToServer(this, endpointURI);
		// Identification à chaque connection
		this.sendIdentification();
	}

	@OnOpen
	public void onOpen(Session userSession) {
		LOG.info("Connexion au master établie.");
		this.session = userSession;
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		LOG.error("Connexion au master perdue ("+reason.getReasonPhrase()+") !");
		this.session = null;
	}

	@OnMessage
	public void onMessage(String message) {
		// LOG.warn("Message reçu !");
	}

	public synchronized boolean sendMesure(Mesure m) {
		MesureMessage msg = new MesureMessage(m);
		if (session == null) {
			try {
				connect();
				this.session.getAsyncRemote().sendObject(msg);
				return true;
			} catch (DeploymentException | IOException e) {
				LOG.error("Connexion au master impossible !", e);
				return false;
			}
		} else {
			this.session.getAsyncRemote().sendObject(msg);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Mesure envoyée : " + msg);
			}
			return true;
		}

	}

	public synchronized void sendIdentification() {
		IdentificationMessage msg = new IdentificationMessage(cedec);
		if (session == null) {
			try {
				connect();
				this.session.getAsyncRemote().sendObject(msg);
			} catch (DeploymentException | IOException e) {
				LOG.error("Connexion au master impossible !", e);
			}
		} else {
			this.session.getAsyncRemote().sendObject(msg);
			LOG.info("Identification envoyée : " + msg);
		}

	}

}