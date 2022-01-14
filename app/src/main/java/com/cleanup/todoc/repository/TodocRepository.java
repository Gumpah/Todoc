package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TodocRepository {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;

    private LiveData<List<Task>> mAllTasks;
    private LiveData<List<Project>> mAllProjects;

    public TodocRepository(TaskDao taskDao, ProjectDao projectDao) {
        this.mTaskDao = taskDao;
        this.mProjectDao = projectDao;
    }

    public void insertProject(Project project) { mProjectDao.insertProject(project); }

    public LiveData<List<Project>> getProjects() { return mProjectDao.getProjects(); }

    public LiveData<List<Task>> getTasks() { return mTaskDao.getTasks(); }

    public LiveData<Task> getTaskById(long taskId) { return mTaskDao.getTaskById(taskId); }

    public void insertTask(Task task) { mTaskDao.insertTask(task); }

    public void updateTask(Task task) { mTaskDao.updateTask(task); }

    public void deleteTask(long taskId) { mTaskDao.deleteTask(taskId);}

}
