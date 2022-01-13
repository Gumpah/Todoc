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
    private List<Project> mAllProjects;

    public TodocRepository(Application application) {
        TodocDatabase db = TodocDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mProjectDao = db.projectDao();
        mAllTasks = mTaskDao.getTasks();
        mAllProjects = mProjectDao.getProjects();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public List<Project> getAllProjects() {
        return mAllProjects;
    }

    public void insert(Task word) {
        TodocDatabase.databaseWriteExecutor.execute(() -> {
            mTaskDao.insertTask(word);
        });
    }
}
