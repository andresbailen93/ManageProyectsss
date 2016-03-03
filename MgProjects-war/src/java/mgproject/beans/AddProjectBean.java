/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Users;

/**
 *
 * @author alejandroruiz
 */
@ManagedBean
@RequestScoped
public class AddProjectBean {

    

    @EJB
    private ProjectFacade projectFacade;
    
    
    
    private String name;
    private String desc;
    private Users admin;
    private boolean error = false;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    
    
    
  
    public AddProjectBean() {
    }
    
    @PostConstruct
    public void doInit(){
        error = false;
        //PRUEBA
    admin = new Users("1","alex");
        
    }
    
    public String doAddProject(){
    
    List<Project> list = projectFacade.findByNameAndUser(name, admin);
        if (list.isEmpty()) {

            Project project = new Project();

            project.setName(name);
            project.setDescription(desc);
            project.setIdAdmin(admin);

            projectFacade.create(project);
             return "index";
        }else{
            error = true;
            return "addProject";
        }
   
}
    
    
}
