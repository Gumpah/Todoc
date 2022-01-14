package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RoomDatabaseInstrumentedTest {

    // FOR DATA

    private TodocDatabase database;

    @Rule

    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before

    public void initDb() throws Exception {


        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),


                TodocDatabase.class)


                .allowMainThreadQueries()


                .build();

    }

    // DATA SET FOR TEST

    private static final long TASK_ID = 1;
    private static final long PROJECT_ID = 1L;
    private static final Project project = Project.getProjectById(PROJECT_ID);

    private static final Task TASK_DEMO = new Task(TASK_ID, 1L, "Test", new Date().getTime());

    @Test

    public void insertAndGetUser() throws InterruptedException {

        // BEFORE : Adding a new user

        this.database.taskDao().createTask(TASK_DEMO);

        // TEST

        Task task = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTaskById(TASK_ID));

        assertEquals(task.getName(), TASK_DEMO.getName());
        assertEquals(task.getId(), TASK_ID);
        assertEquals(task.getProject(), Project.getProjectById(PROJECT_ID));
    }

    @After

    public void closeDb() throws Exception {


        database.close();

    }

}
