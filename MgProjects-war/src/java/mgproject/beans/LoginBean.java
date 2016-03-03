/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private UsersFacade usersFacade;

    private String idUser;
    private String nickName;
    private String urlImage;
    private String payload;
    private String email;
    private boolean singIn = false;
    
    private String depuracion = " ";

    public UsersFacade getUsersFacade() {
        return usersFacade;
    }

    public void setUsersFacade(UsersFacade usersFacade) {
        this.usersFacade = usersFacade;
    }

    public String getDepuracion() {
        return depuracion;
    }

    public void setDepuracion(String depuracion) {
        this.depuracion = depuracion;
    }

    public boolean isSingIn() {
        return singIn;
    }

    public void setSingIn(boolean singIn) {
        this.singIn = singIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LoginBean() {
    }

    public void init() {
        if (this.idUser == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String doLogin(){
        Users user = usersFacade.find(this.idUser);
        
        if( user == null ){
            Users newUser = new Users();
            newUser.setIdUser(this.idUser);
            newUser.setNick(this.nickName);
            newUser.setUrlImage(this.urlImage);
            usersFacade.create(newUser);
        }else{
            user.setIdUser(this.idUser);
            user.setNick(this.nickName);
            user.setUrlImage(this.urlImage);
        }
        
        return "profile";
    }

}