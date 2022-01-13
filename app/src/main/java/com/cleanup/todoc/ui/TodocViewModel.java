package com.cleanup.todoc.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TodocRepository;

import java.util.List;

public class TodocViewModel extends AndroidViewModel {

    private TodocRepository mRepository;

    private final LiveData<List<Task>> mAllTasks;
    private final List<Project> mAllProjects;

    public TodocViewModel (Application application) {
        super(application);
        mRepository = new TodocRepository(application);
        mAllTasks = mRepository.getAllTasks();
        mAllProjects = mRepository.getAllProjects();
    }

    LiveData<List<Task>> getAllTasks() { return mAllTasks; }

    List<Project> getAllProjects() { return mAllProjects; }

    public void insert(Task task) { mRepository.insert(task); }

}
