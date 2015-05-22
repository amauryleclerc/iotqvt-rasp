package fr.iotqvt.rasp.infra;

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

import fr.iotqvt.rasp.modele.MesureMessage;

@ClientEndpoint
public class WebsocketClient {

    private Session session = null;
    private URI endpointURI = null;
    public WebsocketClient(URI endpointURI) {
    	this.endpointURI = endpointURI;
    }
    private void connect() throws DeploymentException, IOException{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, endpointURI);
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
    	System.out.println("message : "+message);
    }

 
    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }
    public void sendMesureMessage(MesureMessage m) {
    	if(session == null){
    		 try {
				connect() ;
				this.session.getAsyncRemote().sendObject(m);
			} catch (DeploymentException | IOException e) {
				System.out.println("connexion impossible "+e.getMessage());
			}
    	}else{
    		this.session.getAsyncRemote().sendObject(m);
    	}
    	
    }




}