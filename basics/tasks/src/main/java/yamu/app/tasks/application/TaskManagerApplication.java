package yamu.app.tasks.application;

import android.app.Application;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yali on 2/14/2015.
 */
public class TaskManagerApplication extends Application
{
    private ArrayList<Task> tasks = Lists.newArrayList();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}
