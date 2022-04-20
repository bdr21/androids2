package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText fieldEmail, fieldPwd;
    private Button btn_login;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Log.d("myLoginDebugger", auth.getCurrentUser().toString());
        if (auth.getCurrentUser() != null) {
            // then User is logged in then redirect to home page
            Intent tent = new Intent(this, HomeActivity.class);
            startActivity(tent);
            Log.d("myLoginDebugger", "auth.getCurrentUser() != null \n "
                    + auth.getCurrentUser().getEmail());
        } else {
            Log.d("myLoginDebugger", "auth.getCurrentUser() == null");
        }

        fieldEmail = (EditText) findViewById(R.id.fieldEmailLogin);
        fieldPwd = (EditText) findViewById(R.id.fieldPwdLogin);
        btn_login = (Button) findViewById(R.id.btn_login);
    }

    public void goToSignUpAct(View vue) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
//    public void signOutUser(View vue) {
//        auth.signOut();
//    }
    public void signInUser(View vue) {
        String email = fieldEmail.getText().toString();
        String pwd = fieldPwd.getText().toString();
        Task<AuthResult> task = auth.signInWithEmailAndPassword(email, pwd);
        task.addOnSuccessListener(MainActivity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this,
                    "Authenticated successfully.",
                    Toast.LENGTH_SHORT)
                    .show();
                Intent tent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(tent);
            }
        });
        task.addOnFailureListener(MainActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }


}