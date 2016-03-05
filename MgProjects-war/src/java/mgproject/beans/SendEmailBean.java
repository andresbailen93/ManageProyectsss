/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;
import mgproject.util.mail.Mail;

/**
 *
 * @author Paloma
 */
@ManagedBean
@RequestScoped
public class SendEmailBean {

    @EJB
    private Mail mail;

    private String message;
    private String subject;
    private String name;
    protected boolean exito = false;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }
    
    

    /**
     * Creates a new instance of sendEmailBean
     */
    public SendEmailBean() {
    }

    public void doSendMail() throws MessagingException {
        mail = new Mail(mail.getDestiny(), subject, "El mensaje :" + "" + message + "ha sido enviado por el usurio :" + "" + name);
        mail.sendMail();
        
        exito = true;
        name = "";
        message = "";
        subject = "";

    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

}
