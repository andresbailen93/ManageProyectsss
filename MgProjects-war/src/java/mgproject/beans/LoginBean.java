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
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Task;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private ProjectFacade projectFacade;

    @EJB
    private UsersFacade usersFacade;
    
    private String idUser;
    private String nickName;
    private String urlImage;
    private String payload;
    private String email;
    private boolean singIn;
    private List<Project> project_list;
    private Project project;
    private Collection<Task> tasks;       
    private String taskAcu;
    private String taskRep;
    private String taskPln;
    private String taskAcc;
    private List<Project> list_colaborators;

    public List<Project> getList_colaborators() {
        return list_colaborators;
    }

    public void setList_colaborators(List<Project> list_colaborators) {
        this.list_colaborators = list_colaborators;
    }
    
    

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    public ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public void setProjectFacade(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }    

    

    
    

    
    public List<Project> getProject_list() {
        return project_list;
    }

    public void setProject_list(List<Project> project_list) {
        this.project_list = project_list;
    }

    public UsersFacade getUsersFacade() {
        return usersFacade;
    }

    public void setUsersFacade(UsersFacade usersFacade) {
        this.usersFacade = usersFacade;
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

    public String getTaskAcu() {
        return taskAcu;
    }

    public void setTaskAcu(String taskAcu) {
        this.taskAcu = taskAcu;
    }

    public String getTaskRep() {
        return taskRep;
    }

    public void setTaskRep(String taskRep) {
        this.taskRep = taskRep;
    }

    public String getTaskPln() {
        return taskPln;
    }

    public void setTaskPln(String taskPln) {
        this.taskPln = taskPln;
    }

    public String getTaskAcc() {
        return taskAcc;
    }

    public void setTaskAcc(String taskAcc) {
        this.taskAcc = taskAcc;
    }

    public LoginBean() {
    }

    public void init() {
        if (this.idUser == null) {
            this.singIn = false;
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
            usersFacade.edit(user);
        }
        this.singIn = true;
        this.project_list = projectFacade.findByUser(user);
        
        return "index";
    }
    public String doLogout() {
        this.singIn = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }	
    
    public String doRedirectToProject(Project project){
        this.project = project;
        this.tasks = project.getTaskCollection();
        this.taskAcu = getCountTaskAcu();
        this.taskRep = getCountTaskRep();
        this.taskPln = getCountTaskPln();
        this.taskAcc = getCountTaskAcc();
        return "project";
    }
    
    private String getCountTaskAcu(){
        int numTaskAcu = 0;
        String strTaskAcu;
        
        for (Task task : tasks) {
            if(task.getPriority().equals("acuciante")){
                numTaskAcu++;
            }
        }
        strTaskAcu = String.valueOf(numTaskAcu);
        
        return strTaskAcu;
    }
    
    private String getCountTaskRep(){
        int numTaskRep = 0;
        String strTaskRep;
        
        for (Task task : tasks) {
            if(task.getPriority().equals("repentino")){
                numTaskRep++;
            }
        }
        strTaskRep = String.valueOf(numTaskRep);
        
        return strTaskRep;
    }
    
    private String getCountTaskPln(){
        int numTaskPln = 0;
        String strTaskPln;
        
        for (Task task : tasks) {
            if(task.getPriority().equals("planificacion")){
                numTaskPln++;
            }
        }
        strTaskPln = String.valueOf(numTaskPln);
        
        return strTaskPln;
    }
    
    private String getCountTaskAcc(){
        int numTaskAcc = 0;
        String strTaskAcc;
        
        for (Task task : tasks) {
            if(task.getPriority().equals("accesorio")){
                numTaskAcc++;
            }
        }
        strTaskAcc = String.valueOf(numTaskAcc);
        
        return strTaskAcc;
    }
    

}
