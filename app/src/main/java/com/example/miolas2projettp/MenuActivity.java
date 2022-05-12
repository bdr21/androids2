package com.example.miolas2projettp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void goToEmploiAct(View v) {
        startActivity(new Intent(MenuActivity.this, EmploiActivity.class));
    }
    public void goToStudentListAct(View v) {
        startActivity(new Intent(MenuActivity.this, StudentListActivity.class));
    }
    public void goToInboxAct(View v) {
        startActivity(new Intent(MenuActivity.this, InboxActivity.class));
    }
}