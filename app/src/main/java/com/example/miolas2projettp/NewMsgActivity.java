package com.example.miolas2projettp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class NewMsgActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore ff;
    EditText input_receiver, input_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);

        auth = FirebaseAuth.getInstance();
        ff = FirebaseFirestore.getInstance();

        input_receiver = (EditText) findViewById(R.id.input_receiver);
        input_content = (EditText) findViewById(R.id.input_content);
    }

    public void sendMsg(View v) {
        MessageBean msg = new MessageBean(input_receiver.getText().toString(),
                auth.getCurrentUser().getEmail(),
                input_content.getText().toString(),
                new Date());
        ff.collection("messages")
                .add(msg)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(NewMsgActivity.this,
                                "Message sent successfully.",
                                Toast.LENGTH_SHORT)
                                .show();
                        Log.d("newmsgdebug", "DocumentSnapshot written with ID: " + documentReference.getId());
                        startActivity(new Intent(NewMsgActivity.this,InboxActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("newmsgdebug", "Error adding document", e);
                    }
                });
    }
}