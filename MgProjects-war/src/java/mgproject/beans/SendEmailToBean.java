/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;



import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.mail.MessagingException;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Users;
import mgproject.util.mail.Mail;

/**
 *
 * @author Paloma
 */
@ManagedBean
@RequestScoped
public class SendEmailToBean {
    @EJB
    private ProjectFacade projectFacade;
    @EJB
    private UsersFacade usersFacade;
    
    
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    
    
    
    protected String message;
    protected String subject;
    protected String emailto;
    protected Users admin;
    protected String emailselect;
    protected Project p;
    protected List<Users> listusuarios = new ArrayList<Users>();
    protected boolean exito;
    
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

    public ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public void setProjectFacade(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    public UsersFacade getUsersFacade() {
        return usersFacade;
    }

    public void setUsersFacade(UsersFacade usersFacade) {
        this.usersFacade = usersFacade;
    }

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public String getEmailselect() {
        return emailselect;
    }

    public void setEmailselect(String emailselect) {
        this.emailselect = emailselect;
    }

    public Project getP() {
        return p;
    }

    public void setP(Project p) {
        this.p = p;
    }

    public List<Users> getListusuarios() {
        return listusuarios;
    }

    public void setListusuarios(List<Users> listusuarios) {
        this.listusuarios = listusuarios;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }
    
    @PostConstruct
    public void init() {
        admin = usersFacade.find(loginBean.getIdUser());
        listusuarios = (List<Users>) loginBean.getProject().getUsersCollection();
        listusuarios.remove(admin);
        exito = false;
    }
    
    
    public void doSendEmailTo() throws MessagingException {
        emailto = usersFacade.find(this.emailselect).getEmail();
        mail = new Mail(emailto, subject, "Tu compañero/a:"+ "<br>" + loginBean.getNickName() +"<br>del proyecto: " + loginBean.getProject().getName() +"<br> te ha enviado el siguiente mensaje:\n"+ ""+ message);
        mail.sendMailTo();
        
        exito = true;
        this.emailselect = "";
        message = "";
        subject = "";
    }
}
