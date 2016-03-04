/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import mgproject.ejb.TaskFacade;
import mgproject.entities.Task;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@RequestScoped
public class ManagedTaskBean {

    @EJB
    private TaskFacade taskFacade;
    private String name;
    private Long idproject;
    private String priority;
    private int time;
    private String timeType;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdproject() {
        return idproject;
    }

    public void setIdproject(Long idproject) {
        this.idproject = idproject;
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
    
    
    
    /**
     * Creates a new instance of ManagedTaskBean
     */
    public ManagedTaskBean() {
    }
    
    public String doAddTask(){
        Task addTask = new Task();
        addTask.setName(this.name);
        
        
        return "project";
    }
}
