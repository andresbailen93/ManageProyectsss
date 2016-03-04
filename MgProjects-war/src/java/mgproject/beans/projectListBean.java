/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mgproject.ejb.ProjectFacade;
import mgproject.entities.Project;
import mgproject.entities.Users;

/**
 *
 * @author alejandroruiz
 */
@ManagedBean
@RequestScoped
public class projectListBean {

    @EJB
    private ProjectFacade projectFacade;

    private List<Project> list;

    public List<Project> getList() {
        return list;
    }

    public void setList(List<Project> list) {
        this.list = list;
    }
    
    @PostConstruct
    public void init(){
        
        Users u = new Users("1","alex");
        list = projectFacade.findByUser(u);
        
    }
    

    /**
     * Creates a new instance of projectListBean
     */
    public projectListBean() {
    }
    
}
