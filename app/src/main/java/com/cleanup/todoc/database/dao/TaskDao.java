package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM Task WHERE task_id = :task_id")
    Task getTaskById(long task_id);

    @Insert
    void createTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("DELETE FROM Task WHERE task_id = :task_id")
    void deleteTask(long task_id);

}
