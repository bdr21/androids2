package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeTV;
    private FirebaseAuth auth;
    private FirebaseFirestore ff;
    private FirebaseAuth.AuthStateListener authListener;
//    public static Intent tent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        ff = FirebaseFirestore.getInstance();

        welcomeTV = (TextView) findViewById(R.id.welcome);
        welcomeTV.append(" " + auth.getCurrentUser().getEmail());
        // this listener will be called when there is change in firebase user session
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d("mylogindebugger","i'm invoked");
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) { // if it ain't already null we kill it
            auth.removeAuthStateListener(authListener);
        }
    }

    public void proceed(View v) {
        DocumentReference docRef = ff.collection("users").document(auth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) Log.d("role/of/user/query", "erreur : ", task.getException());
                else {
                    DocumentSnapshot user = task.getResult();
                    if (!user.exists()) Log.d("role/of/user/query", "No such document");
                    else {
                        String role = (String) user.get("role");
                        if (role.equals("PROFESSOR")) {
                            Log.d("role/of/user/query","role is prof");
                            startActivity(new Intent(HomeActivity.this, MenuActivity.class));
                        }
                        else if (role.equals("ADMIN")) {
                            Log.d("role/of/user/query","role is admin");
                            startActivity(new Intent(HomeActivity.this, AdminMenuActivity.class));
                        }
                        else Log.d("role/of/user/query", "role is neither");
                    }
                }
            }
        });
//        if (tent[0] == null) Log.d("role/of/user/query", "intent is null");
//        startActivity(tent[0]);
//        Log.d("role/of/user/query",auth.getCurrentUser().getEmail());
    }
    public void signOutUser(View vue) {
        auth.signOut();
    }

    public void testBackToLogin(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}