package com.codtech.to_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private boolean isEditMode = false;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        Button saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("title")) {
            isEditMode = true;
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            editPosition = intent.getIntExtra("position", -1);

            titleEditText.setText(title);
            descriptionEditText.setText(description);
        }

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (!TextUtils.isEmpty(title)) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", title);
                resultIntent.putExtra("description", description);
                resultIntent.putExtra("isEditMode", isEditMode);
                resultIntent.putExtra("position", editPosition);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                titleEditText.setError("Title is required");
            }
        });
    }
}
