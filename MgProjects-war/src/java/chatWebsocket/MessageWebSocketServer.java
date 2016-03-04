/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatWebsocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
//import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import mgproject.ejb.Message;

@ApplicationScoped
@ServerEndpoint("/actions")
public class MessageWebSocketServer {
    
    @Inject
    private MessageSessionHandler sessionHandler;
    
    @PostConstruct
    public void afterCreate() {
        System.out.println("MessageWebSocketServer created");
    }    

    @OnOpen
        public void open(Session session) {
            sessionHandler.addSession(session);
    }

    @OnClose
        public void close(Session session) {
            sessionHandler.removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {
            Logger.getLogger(MessageWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
        public void handleMessage(String message, Session session) {
            try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Message messageChat = new Message();
                messageChat.setDescription(jsonMessage.getString("description"));
                messageChat.setUser(jsonMessage.getString("user"));
                sessionHandler.addMessage(messageChat);
            }
        }
    }
}    
