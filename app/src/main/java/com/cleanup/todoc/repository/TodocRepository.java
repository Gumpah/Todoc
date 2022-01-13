package com.cleanup.todoc.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.TodocDatabase;
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

    public void createProject(Project project) { mProjectDao.createProject(project); }

    public LiveData<List<Project>> getProjects() { return mProjectDao.getProjects(); }

    public LiveData<List<Task>> getTasks() { return mTaskDao.getTasks(); }

    public LiveData<Task> getTaskById(long task_id) { return mTaskDao.getTaskById(task_id); }

    public void createTask(Task task) { mTaskDao.createTask(task); }

    public void updateTask(Task task) { mTaskDao.updateTask(task); }

    public void deleteTask(long task_id) { mTaskDao.deleteTask(task_id);}

}
