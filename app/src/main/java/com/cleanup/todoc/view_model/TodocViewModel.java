package com.cleanup.todoc.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TodocRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TodocViewModel extends ViewModel {

    private TodocRepository mRepository;
    private final Executor executor;

    public TodocViewModel (TodocRepository todocRepository, Executor executor) {
        this.mRepository = todocRepository;
        this.executor = executor;
    }

    public LiveData<List<Project>> getProjects() {
        return mRepository.getProjects();
    }

    public LiveData<List<Task>> getTasks() {
        return mRepository.getTasks();
    }

    public void createProject(Project project) {
        executor.execute(() -> {
            mRepository.createProject(project);
        });
    }

    public LiveData<Task> getTaskById(long task_id) {
        return mRepository.getTaskById(task_id);
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            mRepository.createTask(task);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> mRepository.updateTask(task));
    }

    public void deleteTask(long task_id) {
        executor.execute(() -> mRepository.deleteTask(task_id));
    }
}
