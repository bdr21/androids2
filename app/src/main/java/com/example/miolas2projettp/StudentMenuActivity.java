package com.example.miolas2projettp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StudentMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
    }

    public void goToEmploiAct(View v) {
        startActivity(new Intent(StudentMenuActivity.this, StudentEmploiActivity.class));
    }

    public void goToInboxAct(View v) {
        startActivity(new Intent(StudentMenuActivity.this, InboxActivity.class));
    }
}