package com.example.rukminisflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String q1 = ((EditText) findViewById(R.id.QuestionField)).getText().toString();
                String a1 = ((EditText) findViewById(R.id.AnswerField)).getText().toString();
                data.putExtra("question1", q1);
                data.putExtra("answer1", a1);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
