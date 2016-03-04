/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgproject.beans;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import mgproject.ejb.TaskFacade;
import mgproject.ejb.UsersFacade;
import mgproject.entities.Task;
import mgproject.entities.Users;

/**
 *
 * @author andresbailen93
 */
@ManagedBean
@RequestScoped
public class ManagedTaskBean {

    @EJB
    private UsersFacade usersFacade;

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    
    @EJB
    private TaskFacade taskFacade;
    private String name;
    private String priority;
    private int time;
    private String timeType;
    private List<Task> task_list;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<Task> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<Task> task_list) {
        this.task_list = task_list;
    }
  
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
        List <Task> list_task = taskFacade.findTaskByProjectUser(this.loginBean.getProject());
        Users user = usersFacade.find(this.loginBean.getIdUser());
        this.task_list = list_task;
        
    }
    
    public String doAddTask() {

        Users user = usersFacade.find(this.userid);
        
        Task addTask = new Task();
        addTask.setName(this.name);
        addTask.setTime(this.time);
        addTask.setTimetype(this.timeType);
        addTask.setPriority(this.priority);
        addTask.setIdProject(loginBean.getProject());
        
        taskFacade.create(addTask);
        System.out.println(addTask.getIdTask());
        Collection<Users> collectionUser = addTask.getUsersCollection();
        collectionUser.add(user);
        taskFacade.edit(addTask);
        
        this.task_list.add(addTask);

        return "project";
    }
    
    public String doDeleteTask(Task task){
        Task eraseTask = taskFacade.find(task.getIdTask());
        taskFacade.remove(eraseTask);
        this.task_list.remove(eraseTask);
        return "project";
    }
}
