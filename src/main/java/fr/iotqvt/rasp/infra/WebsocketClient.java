package fr.iotqvt.rasp.infra;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import fr.iotqvt.rasp.modele.Mesure;
import fr.iotqvt.rasp.modele.MesureMessage;

@ClientEndpoint
public class WebsocketClient {

    Session session = null;
    
    public WebsocketClient(URI endpointURI) {
 
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        this.session.getAsyncRemote().sendObject(m);
    }




}