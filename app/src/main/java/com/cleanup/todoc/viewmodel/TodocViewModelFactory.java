package com.cleanup.todoc.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repository.TodocRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TodocViewModelFactory implements ViewModelProvider.Factory {

   private final TodocRepository mTodocRepository;

   private final Executor mExecutor;

   private static TodocViewModelFactory factory;

    public static TodocViewModelFactory getInstance(Context context) {

        if (factory == null) {

            synchronized (TodocViewModelFactory.class) {

                if (factory == null) {

                    factory = new TodocViewModelFactory(context);

                }

            }

        }

        return factory;

    }



    private TodocViewModelFactory(Context context) {

        TodocDatabase database = TodocDatabase.getDatabase(context);

        this.mTodocRepository = new TodocRepository(database.taskDao(), database.projectDao());

        this.mExecutor = Executors.newSingleThreadExecutor();

    }

    @Override

    @NonNull
    public <T extends ViewModel>  T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(TodocViewModel.class)) {

            return (T) new TodocViewModel(mTodocRepository, mExecutor);

        }

        throw new IllegalArgumentException("Unknown ViewModel class");

    }

}
