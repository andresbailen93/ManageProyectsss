/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author inftel22
 */
@ManagedBean
@RequestScoped
public class ManagedProjectBean {   
    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    
    /**
     * Creates a new instance of ManagedProjectBean
     */
    public ManagedProjectBean() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}
