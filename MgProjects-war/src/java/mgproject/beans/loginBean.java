/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author andresbailen93
 */
@Named(value = "loginBean")
@SessionScoped
public class loginBean implements Serializable {

    /**
     * Creates a new instance of loginBean
     */
    public loginBean() {
    }
    
}
