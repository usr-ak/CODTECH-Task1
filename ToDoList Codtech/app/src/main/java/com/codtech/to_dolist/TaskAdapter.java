package com.codtech.to_dolist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList;
    private MainActivity mainActivity;

    public TaskAdapter(ArrayList<Task> taskList, MainActivity mainActivity) {
        this.taskList = taskList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        String taskNumber = (position + 1) + ".";
        holder.taskNumber.setText(taskNumber);

        holder.taskTitle.setText(task.getTitle());
        holder.taskDescription.setText(task.getDescription());

        holder.taskCompleted.setChecked(task.isCompleted());

        holder.taskCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCompleted(isChecked);
                TaskStorage.saveTasks(mainActivity, taskList);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.inflate(R.menu.task_menu);
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.edit) {
                    Intent intent = new Intent(mainActivity, AddTaskActivity.class);
                    intent.putExtra("title", task.getTitle());
                    intent.putExtra("description", task.getDescription());
                    intent.putExtra("position", position);
                    mainActivity.getEditTaskLauncher().launch(intent);
                    return true;
                } else if (id == R.id.delete) {
                    showDeleteConfirmationDialog(position);
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskList.remove(position);
                        notifyDataSetChanged();
                        TaskStorage.saveTasks(mainActivity, taskList);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskNumber;
        TextView taskTitle;
        TextView taskDescription;
        CheckBox taskCompleted;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNumber = itemView.findViewById(R.id.taskNumber);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            taskCompleted = itemView.findViewById(R.id.taskCompleted);
        }
    }

}
