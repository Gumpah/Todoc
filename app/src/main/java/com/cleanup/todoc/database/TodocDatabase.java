package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    private final static String databaseName = "MyDatabase.db";

    // --- SINGLETON ---

    private static volatile TodocDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    // --- DAO ---

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();


    // --- INSTANCE ---

    public static TodocDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, databaseName)
                            .allowMainThreadQueries()
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {

        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);

                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().createProject(project));
                }
            }
        };
    }
}