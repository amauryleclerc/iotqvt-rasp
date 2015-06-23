package fr.iotqvt.rasp.infra.websocket;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import fr.iotqvt.rasp.modele.IOT;
import fr.iotqvt.rasp.modele.Mesure;

@ClientEndpoint
public class WebsocketClient {

    private Session session = null;
    private URI endpointURI = null;
    private IOT cedec ;
    
    public WebsocketClient(URI endpointURI, IOT cedec) {
    	this.endpointURI = endpointURI;
    	this.cedec = cedec ;
    }
    private void connect() throws DeploymentException, IOException{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, endpointURI);
        // Identification à chaque connection
        this.sendIdentification();
    }
  
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.session = userSession;
    }

   
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.session = null;
    }

    
    @OnMessage
    public void onMessage(String message) {
    	// System.out.println("message : "+message);
    }

 
    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }


    public synchronized  boolean   sendMesure(Mesure m) {
    	MesureMessage msg = new MesureMessage(m);
    	System.out.println(new MesureMessage(m));
    	if(session == null){
    		 try {
				connect() ;
				this.session.getAsyncRemote().sendObject(msg);
				return true;
			} catch (DeploymentException | IOException e) {
				System.out.println("connexion impossible "+e.getMessage());
				return false;
			}
    	}else{
    		this.session.getAsyncRemote().sendObject(msg);
    		return true;
    	}
    	
    }

    public synchronized  void sendIdentification() {
    	IdentificationMessage msg = new IdentificationMessage(cedec);
    	System.out.println(msg);
    	if(session == null){
    		 try {
				connect() ;
				this.session.getAsyncRemote().sendObject(msg);
			} catch (DeploymentException | IOException e) {
				System.out.println("connexion impossible "+e.getMessage());
			}
    	}else{
				this.session.getAsyncRemote().sendObject(msg);
    	}
    	
    }



}