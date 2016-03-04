/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import mgproject.ejb.TaskFacade;
import mgproject.entities.Task;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@RequestScoped
public class ManagedTaskBean {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    
    @EJB
    private TaskFacade taskFacade;
    private String name;
    private String priority;
    private int time;
    private String timeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
    

    /**
     * Creates a new instance of ManagedTaskBean
     */
    public ManagedTaskBean() {
    }

    @PostConstruct
    public void init(){
    }
    
    public String doAddTask() {
        Task addTask = new Task();
        addTask.setName(this.name);
        addTask.setTime(this.time);
        addTask.setTimetype(this.timeType);
        addTask.setPriority(this.priority);
        addTask.setIdProject(loginBean.getProject());
        
        taskFacade.create(addTask);
        
        return "project";
    }
}
