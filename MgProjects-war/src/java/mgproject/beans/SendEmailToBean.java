/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;



import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;
import mgproject.util.mail.Mail;

/**
 *
 * @author Paloma
 */
@ManagedBean
@RequestScoped
public class SendEmailToBean {
    
    
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    
    
    
    String message;
    String subject;
    String emailto;
    
    Mail mail;

    /**
     * Creates a new instance of SendEmailToBean
     */
    public SendEmailToBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }
    

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

    public String getEmailto() {
        return emailto;
    }

    public void setEmailto(String emailto) {
        this.emailto = emailto;
    }
    
    
    
    
    public void doSendEmailTo() throws MessagingException {
        String messageTunned = "<html> <p>Tu compañero/a : loginBean.getNickName() </p> <img src=loginBean.getUrlImage()><br> <p>te ha enviado el siguiente mensaje: message </p></html>";
        mail = new Mail(emailto ,subject, messageTunned);
        System.out.println(mail);
        mail.sendMailTo();
        
        
        emailto = "";
        message = "";
        subject = "";
    }
}
