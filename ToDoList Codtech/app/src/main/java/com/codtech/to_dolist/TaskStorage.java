package com.codtech.to_dolist;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class TaskStorage {
    private static final String PREFS_NAME = "task_prefs";
    private static final String TASKS_KEY = "tasks";

    public static void saveTasks(Context context, ArrayList<Task> tasks) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        editor.putString(TASKS_KEY, json);
        editor.apply();
    }

    public static ArrayList<Task> loadTasks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(TASKS_KEY, null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> tasks = gson.fromJson(json, type);
        return tasks != null ? tasks : new ArrayList<>();
    }
}
