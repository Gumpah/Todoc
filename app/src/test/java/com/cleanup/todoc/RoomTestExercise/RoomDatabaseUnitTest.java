package com.cleanup.todoc.RoomTestExercise;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class RoomDatabaseUnitTest {

    private TodocDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {


        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

        Project[] projects = Project.getAllProjects();
        for (Project project : projects) {
            this.database.projectDao().insertProject(project);
        }

    }

    private static final Task TASK_DEMO1 = new Task(1, 1L, "Test1", new Date().getTime());
    private static final Task TASK_DEMO2 = new Task(2, 2L, "Test2", new Date().getTime());
    private static final Task TASK_DEMO3 = new Task(3, 3L, "Test3", new Date().getTime());
    private static final Task TASK_DEMO4 = new Task(4, 1L, "Test4", new Date().getTime());

    @Test
    public void getItemsWhenNoItemInserted() throws InterruptedException {

        List<Task> tasks = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTasks());

        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {

        this.database.taskDao().insertTask(TASK_DEMO1);
        this.database.taskDao().insertTask(TASK_DEMO2);
        this.database.taskDao().insertTask(TASK_DEMO3);

        Task task = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTaskById(1));

        assertEquals(task.getName(), TASK_DEMO1.getName());
        assertEquals(task.getId(), 1);
        assertEquals(task.getProject().getId(), 1L);

        List<Task> tasks = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTasks());

        assertEquals(3, tasks.size());
    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException {

        this.database.taskDao().insertTask(TASK_DEMO4);
        Task task = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTaskById(4));
        String expectedName = "New name";
        Task newTask = new Task(4, 1L, expectedName, new Date().getTime());
        String name = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTaskById(4)).getName();
        this.database.taskDao().updateTask(newTask);
        String actualName = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTaskById(4)).getName();

        assertEquals(actualName, expectedName);
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {

        this.database.taskDao().insertTask(TASK_DEMO4);
        int expectedNumberOfTasks = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTasks()).size() - 1;
        this.database.taskDao().deleteTask(4);
        int actualNumberOfTasks = RoomDatabaseTestUtil.getValue(this.database.taskDao().getTasks()).size();

        assertEquals(expectedNumberOfTasks, actualNumberOfTasks);
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
