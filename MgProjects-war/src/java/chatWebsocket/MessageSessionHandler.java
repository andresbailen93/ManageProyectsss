/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatWebsocket;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import mgproject.ejb.Message;

@ApplicationScoped
public class MessageSessionHandler {
    private int messageId = 0;
    private final ArrayList<Session> sessions = new ArrayList<>();
    private final ArrayList<SocketSession> socketSessions = new ArrayList<>();
    private final ArrayList<Message> messages = new ArrayList<>();

    public ArrayList<Message> getMessages() {
        return messages;
    }
    
    @PostConstruct
    public void afterCreate() {
        System.out.println("MessageSessionHandler created");
    }        
    
    public void addSession(Session session) {
        sessions.add(session);
        for (Message message : messages) {
            JsonObject addMessage = createAddMessage(message);
            sendToSession(session, addMessage);
        }
    }
    
    public void addSocketSession(String idProject, Session session) {
        SocketSession ss = new SocketSession();
        ss.setIdproject(idProject);
        ss.setSession(session);
        socketSessions.add(ss);
        for (Message message : messages) {
            JsonObject addMessage = createAddMessage(message);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public void removeSocketSession(String idProject, Session session){
        SocketSession ss = new SocketSession();
        ss.setIdproject(idProject);
        ss.setSession(session);
        socketSessions.remove(ss);
    }

    public void addMessage(String idProject, Message message) {
        message.setId(messageId);
        messages.add(message);
        messageId++;
        JsonObject addMessage = createAddMessage(message);
//        sendToAllConnectedSessions(addMessage);
        sendToAllProjectSessions(idProject, addMessage);
    }

    private JsonObject createAddMessage(Message message) {
        JsonProvider provider = JsonProvider.provider();
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", message.getId())
                .add("description", message.getDescription())
                .add("user", message.getUser())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }
    
    protected void sendToAllProjectSessions(String idProject, JsonObject message) {
        for (SocketSession ss : socketSessions) {
            if(ss.getIdproject().equalsIgnoreCase(idProject)){
            sendToSession(ss.getSession(), message);               
            }
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(MessageSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
