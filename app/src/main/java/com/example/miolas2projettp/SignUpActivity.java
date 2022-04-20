package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private EditText fieldEmail, fieldPwd, fieldFullName;
    private Button btnSignUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.btn_signup2);
        fieldEmail = (EditText) findViewById(R.id.fieldEmail);
        fieldPwd = (EditText) findViewById(R.id.fieldPwd);
        fieldFullName = (EditText) findViewById(R.id.fieldFullName);
    }

    public void createUserInFirebase(View vue) {
        String email = fieldEmail.getText().toString().trim();
        String password = fieldPwd.getText().toString().trim();
        String full_name = fieldFullName.getText().toString().trim();
        Task<AuthResult> signupTask = auth.createUserWithEmailAndPassword(email, password);
        signupTask.addOnSuccessListener(SignUpActivity.this,
            new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    User user = new User(email,full_name, User.RoleEnum.PROFESSOR);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Task fs_task = db.collection("users").document(email).set(user.getInfo());
                    fs_task.addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this,
                                "New account created successfully.", Toast.LENGTH_SHORT).show();
                                // go to home page
                                Intent tent = new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(tent);
                            } else {
                                Toast.makeText(SignUpActivity.this,
                                "Couldn't persist changes to firestore.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
        signupTask.addOnFailureListener(SignUpActivity.this,
            new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this,
                            "Creating account failed.",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });
    }
}









































//                signupTask.addOnCompleteListener(SignUpActivity.this,
//                    new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(SignUpActivity.this,
//                                    "Authentication success.",
//                                    Toast.LENGTH_SHORT)
//                                    .show();
//                            } else {
//                                Toast.makeText(SignUpActivity.this,
//                                    "Authentication failed.",
//                                    Toast.LENGTH_SHORT)
//                                    .show();
//                            }
//                        }
//                    });




//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = fieldEmail.getText().toString().trim();
//                String password = fieldPwd.getText().toString().trim();
//                Task<AuthResult> signupTask = auth.createUserWithEmailAndPassword(email, password);
//                signupTask.addOnSuccessListener(SignUpActivity.this,
//                    new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            Toast.makeText(SignUpActivity.this,
//                                "Authentication success.",
//                                Toast.LENGTH_SHORT)
//                                .show();
//                        }
//                    });
//                signupTask.addOnFailureListener(SignUpActivity.this,
//                    new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(SignUpActivity.this,
//                                "Authentication failed.",
//                                Toast.LENGTH_SHORT)
//                                .show();
//                        }
//                    });
//
//            }
//        });