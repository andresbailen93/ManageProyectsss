/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import mgproject.ejb.Mail;

/**
 *
 * @author Paloma
 */
@ManagedBean
@SessionScoped
public class SendEmailBean {
    @EJB
    private Mail mail;
    
    

    /**
     * Creates a new instance of sendEmailBean
     */
    public SendEmailBean() {
    }
    
    public String doSendMail() throws MessagingException {
        Properties props = new Properties();
        
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", mail.getServidorSMTP());
        props.put("mail.smtp.port", mail.getPuerto());
        
        Session session = Session.getInstance(props, null);
        
        
        
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getDestiny()));
        message.setSubject(mail.getSubject());
        message.setSentDate(new Date());
        message.setText(mail.getMensaje());
        
        Transport tr = session.getTransport("smtp");
        tr.connect(mail.getServidorSMTP(), mail.getUsuario(), mail.getPassword());
        message.saveChanges();
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
        
        
        
        
        return "";
        
    }


    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }
    
    
}
