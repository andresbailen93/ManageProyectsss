/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import mgproject.ejb.ProjectFacade;
import mgproject.ejb.TaskFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Project;
import mgproject.entities.Task;

/**
 *
 * @author inftel22
 */
@ManagedBean
@RequestScoped
public class ManagedProjectBean {   
    
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;
    @EJB
    private TaskFacade taskFacade;
    @EJB
    private ProjectFacade projectFacade;
    @EJB
    private UsersFacade usersFacade;
    
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

    public TaskFacade getTaskFacade() {
        return taskFacade;
    }

    public void setTaskFacade(TaskFacade taskFacade) {
        this.taskFacade = taskFacade;
    }

    public ProjectFacade getProjectFacade() {
        return projectFacade;
    }

    public void setProjectFacade(ProjectFacade projectFacade) {
        this.projectFacade = projectFacade;
    }
    
    public String doDeleteProject(Project project){
        Collection<Task> tasks = project.getTaskCollection();
        System.out.println(project.getName());
        
        for (Task task : tasks) {
            taskFacade.remove(task);
        }  
        projectFacade.remove(project);
        loginBean.setProject_list(projectFacade.findByUser(usersFacade.find(loginBean.getIdUser())));
        
        return ("profile");
    }
}
